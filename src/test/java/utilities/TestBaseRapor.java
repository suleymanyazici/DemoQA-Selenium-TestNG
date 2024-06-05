package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestBaseRapor {
	//ExtentReports sınıfından bir nesne oluşturulur
	protected static ExtentReports extentReports;
	//Aynı şekilde ExtentTest sınıfından bir nesne oluştururuz.
	//Test pass veya failed gibi bilgileri kaydeder. Bunun yanında ekran resmide alır.
	protected static ExtentTest extentTest;
	// Html raporu duzenlemesi için ExtentHtmlReporter sınıfından extentHtmlReporter nesnesi oluşturulur.
	protected static ExtentHtmlReporter extentHtmlReporter;


	//Her zaman çalışması için alwaysRun = true
	@BeforeTest(alwaysRun = true)
	public void setUpTest() {
		extentReports = new ExtentReports();

		//Rapor oluştuktan sonra raporun kayıt edilebilmesi için path ini veriyoruz(user.dir uygulamanın çalıştırıldığı dizindir.
		String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String filePath = System.getProperty("user.dir") + "/test-output/Rapor" + date + ".html";
		//Oluşturmak istediğimiz raporu (html formatında) başlatıyoruz, yukarıda belirlediğimiz filePath i burada kullanıyoruz..
		extentHtmlReporter = new ExtentHtmlReporter(filePath);
		//extentReports a extentHtmlReporter nesnesini ekler
		extentReports.attachReporter(extentHtmlReporter);

		//İstediğimiz bilgileri buraya ekeyebiliyoruz.
		//Bu satırda hangi environment de çalıştığımızın bilgisi verilir burada QA(Quality Assurance) ortamında çalışılacağı belirtilmiş
		extentReports.setSystemInfo("Enviroment", "QA");
		//Bu satırda tarayıcı bilgileri bulunmaktah
		extentReports.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
		//Bu satırda raporu hazırlayanın adı belirtilir
		extentReports.setSystemInfo("Automation Engineer", "slyzc");
		//Burada oluşturulan raporun başlığı atanır
		extentHtmlReporter.config().setDocumentTitle("Amazon Automation Documentation");
		//Burada ise HTML raporununn  ana sayfasındaki raporu adlandırmak için kullanılır.
		extentHtmlReporter.config().setReportName("Amazon Automation TestNG Report Documentation");
	}


	// Her test methodundan sonra eğer testte hata varsa, ekran görüntüsü alıp rapora ekliyor
	//alwaysRun = true eklendiğinde her test anotasyonundan sonra çalıştıılması gerektiğini belirtiyoruz
	@AfterMethod(alwaysRun = true)
	//Burada tearDownMethod adında bir metod tanımlanıyor ve test sonuçlarını tutması bekleniyor. Aynı zamanda hata fırlatması olası olduğu için throws IOException kullanılmış.
	public void tearDownMethod(ITestResult result) throws IOException {
		//Testin sonucunun başarısız olma ihtimali için işlemleri başlatmak için
		if (result.getStatus() == ITestResult.FAILURE) {
			//Burada ReusableMethod içerisinden ekran görüntüsü almak için yazdığımız metodu kullanarak
			// Testin adıyla bir ekran görüntüsü alır ve görüntünün dosya yolunu screenshotLocation değişkenine atarız
			String screenshotLocation = ReusableMethods.getScreenshot(result.getName());
			//Testin başarısız olduğunu rapora ekler
			extentTest.fail(result.getName());
			//Başarısız testin ekrran görüntüsünü rapora ekler
			extentTest.addScreenCaptureFromPath(screenshotLocation);
			//Testin neden başarısız olduğunu rapora ekler.
			extentTest.fail(result.getThrowable());

			//Eğer test çalıştırılmadan geçilmesi isteniyorsa ihtimali olan işlemleri bşatmak için kullanılır.
		} else if (result.getStatus() == ITestResult.SKIP) {
			//Testin adını rapora atlanmış olarak eklemek için kullanılır.
			extentTest.skip("Test Case is skipped: " + result.getName()); // Ignore olanlar
		}
		//Testin sonucı ne olursa olsun testin sonucunda driveri kapatmak için kullanılır.
		Driver.closeDriver();
	}


	//Tüm testler bittikten sonra raporlandırmayı sonlandırmak icin
	@AfterTest(alwaysRun = true)
	//
	public void tearDownTest() {
		//Raporu tamamla ve dosyayı yazması için kullanılır.
		extentReports.flush();
	}
}
