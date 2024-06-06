package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class Alert_Frame_WindowPages {
	public Alert_Frame_WindowPages(){

		PageFactory.initElements(Driver.getDriver(), this);

	}



//---------------Browser window screen-----------------------
	@FindBy(id = "tabButton")
	public WebElement newTabButton;

	@FindBy(id = "windowButton")
    public WebElement newWindowButton;

	@FindBy(id = "messageWindowButton")
    public WebElement newMessageWindowButton;

	@FindBy(id = "sampleHeading")
	public WebElement tabMessage;

//----------------------------------------------------------

//---------------Browser Alerts ----------------------------

	@FindBy(id = "alertButton")
	public WebElement alertButton;

	@FindBy(id = "timerAlertButton")
	public WebElement timerAlertButton;

	@FindBy(id = "confirmButton")
    public WebElement confirmButton;

	@FindBy(id = "promtButton")
    public WebElement promptButton;

	@FindBy(id = "confirmResult")
	public WebElement confirmResult;

	@FindBy(id = "promptResult")
    public WebElement promptResult;

//----------------------------------------------------------

//---------------Browser Alerts ----------------------------

	@FindBy(xpath = "//iframe[@id='frame2']")
	public WebElement frame2;

	@FindBy(id = "sampleHeading")
	public WebElement frame2Message;



}
