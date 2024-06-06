package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Alert_Frame_WindowPages;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.util.Set;

public class Alert_Frame_WindowTest {

	Alert_Frame_WindowPages alertFrameWindowTest = new Alert_Frame_WindowPages();

	@Test(priority = 1)
	public void windowTest(){
		Driver.getDriver().get(ConfigReader.getProperty("windowURL"));

		String originalWindow = Driver.getDriver().getWindowHandle();
		alertFrameWindowTest.newTabButton.click();

		Set<String> allTabs = Driver.getDriver().getWindowHandles();

		for (String windowHandle : allTabs){
			if(!windowHandle.equals(originalWindow)){
				Driver.getDriver().switchTo().window(windowHandle);
			}
		}
		Assert.assertEquals(ConfigReader.getProperty("newTabMessage"),alertFrameWindowTest.tabMessage.getText());
		Driver.getDriver().close();
		Driver.getDriver().switchTo().window(originalWindow);


		alertFrameWindowTest.newWindowButton.click();

		Set<String> allTabsFor2Time = Driver.getDriver().getWindowHandles();

		for (String windowHandle : allTabsFor2Time){
			if(!windowHandle.equals(originalWindow)){
				Driver.getDriver().switchTo().window(windowHandle);
			}
		}
		Assert.assertEquals(ConfigReader.getProperty("newTabMessage"),alertFrameWindowTest.tabMessage.getText());
		Driver.getDriver().close();
		Driver.getDriver().switchTo().window(originalWindow);

	}

	@Test(priority = 2)
	public void alertTest(){
		Driver.getDriver().get(ConfigReader.getProperty("alertURL"));

		alertFrameWindowTest.alertButton.click();
		Assert.assertEquals(ConfigReader.getProperty("alert"),Driver.getDriver().switchTo().alert().getText());
		Driver.getDriver().switchTo().alert().accept();
		Driver.getDriver().switchTo().defaultContent();

		alertFrameWindowTest.timerAlertButton.click();

		ReusableMethods.waitForAlert(Driver.getDriver(),10);
		Assert.assertEquals(ConfigReader.getProperty("timerAlert"),Driver.getDriver().switchTo().alert().getText());
		Driver.getDriver().switchTo().alert().accept();
		Driver.getDriver().switchTo().defaultContent();

		alertFrameWindowTest.confirmButton.click();
		Assert.assertEquals(ConfigReader.getProperty("confirmBoxMessage"),Driver.getDriver().switchTo().alert().getText());
		Driver.getDriver().switchTo().alert().dismiss();
		Assert.assertEquals(ConfigReader.getProperty("confirmBoxCancelResult"),alertFrameWindowTest.confirmResult.getText());
		alertFrameWindowTest.confirmButton.click();
		Driver.getDriver().switchTo().alert().accept();
		Assert.assertEquals(ConfigReader.getProperty("confirmBoxAcceptResult"),alertFrameWindowTest.confirmResult.getText());


		alertFrameWindowTest.promptButton.click();
		Assert.assertEquals(ConfigReader.getProperty("promptAlertMessage"),Driver.getDriver().switchTo().alert().getText());
		Driver.getDriver().switchTo().alert().sendKeys(ConfigReader.getProperty("promptName"));
		Driver.getDriver().switchTo().alert().accept();
		System.out.println(alertFrameWindowTest.promptResult.getText());
		Assert.assertEquals(ConfigReader.getProperty("promptResult"),alertFrameWindowTest.promptResult.getText());

	}

	@Test(priority = 3)
	public void frameTest(){
		Driver.getDriver().get(ConfigReader.getProperty("frameURL"));

		ReusableMethods.waitForVisibility(alertFrameWindowTest.frame2,10);
		Driver.getDriver().switchTo().frame(alertFrameWindowTest.frame2);
		System.out.println(alertFrameWindowTest.frame2Message.getText());
		ReusableMethods.scrollToPageDown(Driver.getDriver());
		ReusableMethods.scrollToRight(Driver.getDriver());

		Driver.getDriver().switchTo().defaultContent();


	}

}
