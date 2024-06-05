package test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BookStorePage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;


public class BookStoreTest {
	BookStorePage bookStorePage = new BookStorePage();


	@Test(priority = 1)
	public void reCaptchaRegisterTest() {
		Driver.getDriver().get(ConfigReader.getProperty("bookStoreApplicationRegisterURL"));
		bookStorePage.newUserButton.click();

		ReusableMethods.scrollToMiddle(Driver.getDriver());
		bookStorePage.firstName.sendKeys(ConfigReader.getProperty("firstName"));
		bookStorePage.lastName.sendKeys(ConfigReader.getProperty("lastName"));
		bookStorePage.userName.sendKeys(ConfigReader.getProperty("userName"));
		bookStorePage.password.sendKeys(ConfigReader.getProperty("badPassword"));
		ReusableMethods.waitFor(3);
		bookStorePage.registerButton.click();


		Assert.assertEquals(ConfigReader.getProperty("reCaptchaWarning"), bookStorePage.warning.getText());

	}

	@Test(priority = 2, dependsOnMethods = "reCaptchaRegisterTest")
	public void badRegissterTest() {


		// reCAPTCHA iframe sinin görünür olmasını bekle ve geçiş yap
		ReusableMethods.waitForVisibility(bookStorePage.recaptchaFrame, 10);
		Driver.getDriver().switchTo().frame(bookStorePage.recaptchaFrame);

		// reCAPTCHA onay kutusunun tıklanabilir olmasını bekle ve tıkla
		ReusableMethods.waitForClickablility(bookStorePage.recaptchaCheckbox, 20);
		bookStorePage.recaptchaCheckbox.click();

		// Ana çerçeveye geri dön
		Driver.getDriver().switchTo().defaultContent();
		ReusableMethods.waitFor(10);

		bookStorePage.registerButton.click();

		ReusableMethods.waitFor(3);
		Assert.assertEquals(ConfigReader.getProperty("badPasswordWarning"), bookStorePage.warning.getText());


	}

	@Test(priority = 3, dependsOnMethods = "badRegissterTest")
	public void registerTest() {

		Driver.getDriver().navigate().refresh();

		bookStorePage.firstName.sendKeys(ConfigReader.getProperty("firstName"));
		bookStorePage.lastName.sendKeys(ConfigReader.getProperty("lastName"));
		bookStorePage.userName.sendKeys(ConfigReader.getProperty("userName"));
		bookStorePage.password.sendKeys(ConfigReader.getProperty("password"));


		// reCAPTCHA iframe sinin görünür olmasını bekle ve geçiş yap
		ReusableMethods.waitForVisibility(bookStorePage.recaptchaFrame, 10);
		Driver.getDriver().switchTo().frame(bookStorePage.recaptchaFrame);

		// reCAPTCHA onay kutusunun tıklanabilir olmasını bekle ve tıkla
		ReusableMethods.waitForClickablility(bookStorePage.recaptchaCheckbox, 20);
		bookStorePage.recaptchaCheckbox.click();

		// Ana çerçeveye geri dön
		Driver.getDriver().switchTo().defaultContent();
		ReusableMethods.waitFor(10);
		bookStorePage.registerButton.click();

		ReusableMethods.waitFor(5);
		Assert.assertEquals(ConfigReader.getProperty("registerSuccessText"), Driver.getDriver().switchTo().alert().getText());

		Driver.getDriver().switchTo().alert().accept();

		Driver.getDriver().switchTo().defaultContent();


	}


	@Test(priority = 4, dependsOnMethods = "registerTest")
	public void loginTest() {

		bookStorePage.gotologinButton.click();
		bookStorePage.userName.sendKeys(ConfigReader.getProperty("userName"));
		bookStorePage.password.sendKeys(ConfigReader.getProperty("password"));
		bookStorePage.loginButton.click();


	}

	@Test(priority = 5, dependsOnMethods = "loginTest")
	public void addBook() {

		bookStorePage.gotoStoreButton.click();
		bookStorePage.searchBox.sendKeys("Git");

	}
}