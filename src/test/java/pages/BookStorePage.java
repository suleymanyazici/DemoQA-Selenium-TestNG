package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class BookStorePage {

	public BookStorePage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}

	@FindBy(id = "newUser")
	public WebElement newUserButton;

	@FindBy(id = "firstname")
    public WebElement firstName;

	@FindBy(id = "lastname")
    public WebElement lastName;

	@FindBy(id = "userName")
    public WebElement userName;

	@FindBy(id = "password")
    public WebElement password;

	@FindBy(xpath = "//button[text()='Register']")
	public WebElement registerButton;

	@FindBy(xpath = "//p[@id='name']")
	public WebElement warning;

	@FindBy(xpath = "//iframe[@title='reCAPTCHA']")
	public WebElement recaptchaFrame;

	@FindBy(xpath = "//div[@class=\"recaptcha-checkbox-border\"]")
	public WebElement recaptchaCheckbox;

	@FindBy(id = "gotologin")
    public WebElement gotologinButton;

	@FindBy(id = "login")
	public WebElement loginButton;

	@FindBy(id = "gotoStore")
    public WebElement gotoStoreButton;

	@FindBy(id = "searchBox")
	public WebElement searchBox;

}
