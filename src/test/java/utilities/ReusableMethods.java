package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class ReusableMethods {


	public static String getScreenshot(String name) throws IOException {
		// Hata alınan yerde tekrar senaryo koşumu yapılmasını engellemek adına ekran görüntüsü almak için kullan yardımcı metod

		// Tarih formatı ile isimlendirmek için kullanılır
		String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		// Ekran görüntüsü almamızı sağlayan bir selenium interface sidir
		TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
		File source = ts.getScreenshotAs(OutputType.FILE);
		// Dosya adını ve yolunu vererek nereye kaydedilmesi gerektiğini belirtiriz.
		String target = System.getProperty("user.dir") + "/target/Screenshots/" + name + date + ".png";
		File finalDestination = new File(target);
		FileUtils.copyFile(source, finalDestination);
		//Geriye target değişkeninin değerini döndürürüz
		return target;
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Switching Window Penceler arasında geçmemizi sağlayan yardmcı metod
	public static void switchToWindow(String targetTitle) {
		//Geçerli olan ekranın handle sini saklarız
		String origin = Driver.getDriver().getWindowHandle();
		//Açık olan tüm pencerelerin handle lerini for döngüsü ile kontrol ederiz
		for (String handle : Driver.getDriver().getWindowHandles()) {
			//Her ekranın handlesi sayesinde o ekrana geçiş yap Tüm ekranlara geçiş yapacaktır.
			Driver.getDriver().switchTo().window(handle);
			//Geçerli ekranın handlesini kontrol et eğer hedef lenen handle ile aynıysa o değeri dön
			if (Driver.getDriver().getTitle().equals(targetTitle)) {
				return;
			}
		}
		//Eğer hedef ekranla aynı olan bir handle bulunmazsa ana ekrana geri dön
		Driver.getDriver().switchTo().window(origin);
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Hover Over Web elementinin üzerine fare ile gelmemizi sağlar.
	public static void hover(WebElement element) {
		//Actin tarafından bir aksiyon nesnesi oluşturulur.
		Actions actions = new Actions(Driver.getDriver());
		//Verilen elementin üzerine fareyi getirir ve işlemi gerçekleştirir.
		actions.moveToElement(element).perform();
	}
//---------------------------------------------------------------------------------------------------------------------------
//Hover and click işlemi için kullanılır.
	public static void hoverAndClick(WebDriver driver, WebElement element) {
		// Action tarafından bir aksiyon nesnesi oluşturulur.
		Actions actions = new Actions(driver);
		// Verilen elementin üzerine fareyi getirir ve işlemi gerçekleştirir.
		actions.moveToElement(element).click().perform();
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Return a list of string given a list of Web Element - Web element kullanılarak liste halinde verilen
	//öğelerin isimlerini alarak bunları Stringe çevirere bir list nesnesine döner
	public static List<String> stringToList(List<WebElement> list) {
		//String değerlerini tutmak için bir list oluştururuz
		List<String> elemTexts = new ArrayList<>();
		//For döngüsü sayesinde Web elementleri liste haline getiririz
		for (WebElement el : list) {
			//Web elementin metnini ontrol eder boşmu dolumu
			if (!el.getText().isEmpty()) {
				//Boş olmadığı okeylenirse bunu elemTexts listine ekler
				elemTexts.add(el.getText());
			}
		}
		//Geriye elemTexts listesini döner.
		return elemTexts;
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Returns the Text of the element given an element locator - WebDriver kullanarak konumlandırıcı ile bulunan tüm
	//Web elementlerin metinlerini alır ve bunları string listesine çevirir
	//yukarıdakine benzer ama parametre tipleri farklı kullanım şekilleri farklı
	public static List<String> getElementsText(By locator) {
		//Belirtilen locatore göre web elementleri bulunur.
		List<WebElement> elems = Driver.getDriver().findElements(locator);
		List<String> elemTexts = new ArrayList<>();
		//For döngüsü sayesinde driver ile bulduğumuz Web elementleri liste haline getiririz
		for (WebElement el : elems) {
			//Web elementin metnini ontrol eder boşmu dolumu
			if (!el.getText().isEmpty()) {
				//Boş olmadığı okeylenirse bunu elemTexts listine ekler
				elemTexts.add(el.getText());
			}
		}
		//Geriye elemTexts listesini döner.
		return elemTexts;
	}

//---------------------------------------------------------------------------------------------------------------------------

	//   HARD WAIT WITH THREAD.SLEEP - Verilen süre boyundan saniye cinsinden beklemeyi sağlar
	//   waitFor(5);  => waits for 5 second
	//	 Thread.Sleep(x) yerine kullanılır Thread sleep kod kirliliğine yol açar bu yüzden pek sevilmez.
	public static void waitFor(int second) {
		//Thread.sleep metodu, InterruptedException adı verilen kontrol edilen (checked) bir istisna (exception) fırlatabilir
		//bu yüzden bunu yakalamak adına Try Catch kullanımı yapılır.
		try {
			//Belirtilen süre bounca saniye cinsinden beklememizi sağlar.
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// InterruptedException durumunda hata ayıklama bilgilerini yazdır
			e.printStackTrace();
		}
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Explicit Wait - Bir elementin belirli bir süre sonra görünür omasını bu metodla bekleriz
	public static WebElement waitForVisibility(WebElement element, int timeout) {
		//Bir adet wait nesnesi oluşturulur. belirtilen timeout süresine kadar bekler
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
		//Öğenin görünür olması beklenir ve beklenen süre sonunda değer webelement olarak geri döndürülür
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Yukardakinin aynı ismiyle kullanılmıştır burda hangisinin seçimi yapılacağı içeride kullanılan element mi locatormu bunun seçimi ile netleşir
	public static WebElement waitForVisibility(By locator, int timeout) {
		// WebDriverWait nesnesi oluşturulur, belirtilen süre (timeout) kadar bekleyecek şekilde ayarlanır
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
		// Öğenin görünür olması beklenir ve beklenen süre sonunda değer webelement olarak geri döndürülür
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

//---------------------------------------------------------------------------------------------------------------------------


	public static Alert waitForAlert(WebDriver driver, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Yukardakinin aynısına benzer. Fakat burda seçilebilirliği bekleriz
	public static WebElement waitForClickablility(WebElement element, int timeout) {
		// WebDriverWait nesnesi oluşturulur, belirtilen süre (timeout) kadar bekleyecek şekilde ayarlanır
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
		// Öğenin tıklanabilir olmasını bekler ve tıklanabilir hale geldiğinde WebElement döner
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	//---------------------------------------------------------------------------------------------------------------------------
	//Yukardakinin aynısıdır. Fakat burda seçilebilirliği locator kulanarak bekleriz.
	public static WebElement waitForClickablility(By locator, int timeout) {
		// WebDriverWait nesnesi oluşturulur, belirtilen süre (timeout) kadar bekleyecek şekilde ayarlanır
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
		// Öğenin tıklanabilir olmasını bekler ve tıklanabilir hale geldiğinde WebElement döner
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

//---------------------------------------------------------------------------------------------------------------------------

	//clickWithTimeOut metodu, belirli bir WebElement öğesine tıklama işlemini gerçekleştirir. eğer çğe tıklanabilir değilse
	//belirli bir süre daha beklemeye olanak sağlar.
	//Dinamik olarak yüklenen içeriklerde öğe etkileşime hazır hale gelene kadar beklemek için kullanılır.
	public static void clickWithTimeOut(WebElement element, int timeout) {
		//For döngüsü ile beklee süresi timeouta kadar arttırılır
		for (int i = 0; i < timeout; i++) {
			try {
				//Elemente tıklanır
				element.click();
				return;
			} catch (WebDriverException e) {
				//Alınan hata sonucu  WaitFor Metodunu kullanarak tekrar beıkler
				waitFor(1);
			}
		}
	}

//---------------------------------------------------------------------------------------------------------------------------


	//bir sayfanın yüklenmesini beklemek için kullanırız.
	//sayfa içeriği tamamen yüklendiğinde veya hazır hale geldiğinde devam etmek istediğimiz durumlarda tercih ederiz.
	public static void waitForPageToLoad(long timeout) {
		// beklenen koşul boolean olarak tanımlanır. Sayfanın yüklenmesi tamamlandığında true döndürsün diye
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				//Java ile kontrol edilmediği için javascript ile sayfanın yüklenip yüklenmediği kontol edilir
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			// Kullanıcıya sayfanın yüklenmesi bekleniyor mesajı yazdırır
			System.out.println("Waiting for page to load...");
			// WebDriver nesnesi üzerinden belirtilen süreye kadar bekleyecek WebDriverWait nesnesi oluştur
			WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
			// Bekleme süresince belirtilen koşulun gerçekleşmesini bekleyerek sayfanın yüklenmesini sağlar
			wait.until(expectation);
		} catch (Throwable error) {
			// Belirtilen zaman aşımı süresince sayfanın yüklenmediği durumunda hata mesajını yazdır
			System.out.println(
					"Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
		}
	}

//---------------------------------------------------------------------------------------------------------------------------

	//Fluent Wait - bir web elementinin bulunmasını beklemek için kullanılır
	//bir belirteç bulunana kadar belirli aralıklarla belirli bir zaman aşımı süresi içinde sayfanın yüklenmesini bekletir.
	public static WebElement fluentWait(final WebElement webElement, int timeout) {
		/*FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver()).withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS);*/
		// FluentWait nesnesi oluşturulur
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
				.withTimeout(Duration.ofSeconds(3))// Her seferinde 3 saniye bekler
				.pollingEvery(Duration.ofSeconds(1));// Her saniye belirtilen web elementini kontrol eder


		// Bekleme süresince belirtilen web elementini arar ve bulunca döndürür

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return webElement;
			}
		});

		return element;
	}

//----------------------------------------------------------------------------------------------------------------


	//İstediğimiz kordinata gitmek için kullanılır
	public static void scrollToCoordinates(WebDriver driver, int x, int y) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(arguments[0], arguments[1]);", x, y);
	}

	//Sayfanın yarısına inmek için kullanılır.
	public static void scrollToMiddle(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
	}

	//Sayfanın en altına inmek için kullanılır
	public static void scrollToPageDown(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	//Sayfanın en sağına gitmek için kullanılır
	public static void scrollToRight(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(document.body.scrollToWidth,0);");
	}

	public static void clickCheckBox(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

}
