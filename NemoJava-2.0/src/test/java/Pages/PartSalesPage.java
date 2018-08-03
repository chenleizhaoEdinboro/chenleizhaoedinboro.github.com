package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PartSalesPage {
	public WebDriver PageDriver;

	public PartSalesPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}

	// PassCode GateKeeper
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'My Account')]")
	public WebElement myAccount;
	@FindBy(how = How.XPATH, using = "(//span[@class='icon-cart'])[1]")
	public WebElement cartIcon;
	@FindBy(how = How.XPATH, using = "(.//*[contains(text(),'Sign in with Lenovo ID')])[3]")
	public WebElement LenovoIDSignin;
	@FindBy(how = How.XPATH, using = "//input[@class='username jsUsername']")
	public WebElement LenovoID;
	@FindBy(how = How.XPATH, using = "//input[@class='password jsPassword']")
	public WebElement password;
	@FindBy(how = How.XPATH, using = "//input[@class='jsSubBtn']")
	public WebElement signinButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='partNumQuery']")
	public WebElement partNumber;
	@FindBy(how = How.XPATH, using = ".//*[@id='partNumberSearchbutton']")
	public WebElement partLookUp;
	@FindBy(how = How.XPATH, using = "//button[@title='View my cart']")
	public WebElement viewMyCart;
	
	@FindBy(how = How.XPATH, using = ".//*[@class='icon-cart cart-blue']")
	public WebElement addToCart;
	@FindBy(how = How.XPATH, using = "((//span[contains(text(),'Price')]/../../li)[2]//span)[10]")
	public WebElement partSalesPrice;
	@FindBy(how = How.XPATH, using = "//button[@title='Checkout']")
	public WebElement partSalesCheckOut;
	@FindBy(how = How.XPATH, using = "(//a[@href='/au/en/products/parts'])[1]")
	public WebElement partSales_AccessoryMenu;
	
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Parts Lookup')]/..//a[@href='/au/en/partslookup']")
	public WebElement partSales_DropDownLookUp;
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Continue to guest checkout')]")
	public WebElement partSales_GuestCheckOut;
	@FindBy(how = How.XPATH, using = "//p[@class='selected-country']")
	public WebElement partSales_SelectCountry;

	//NA-27990
	@FindBy(how = How.XPATH, using = "//input[@id = 'shoplogin_email']")
	public WebElement partSales_LoginID;
	@FindBy(how = How.XPATH, using = "//input[@id = 'shoplogin_pwd']")
	public WebElement partSales_Password;
	@FindBy(how = How.XPATH, using = "//span[@class = 'btn btn-primary'][1]")
	public WebElement partSales_Signin;
	@FindBy(how = How.XPATH, using = "//p[@class='selected-language']")
	public WebElement partSales_SelectLanguage;
	@FindBy(how = How.XPATH, using = "//div[@class='first-line']/span[11]/label")
	public WebElement addToCartNew;
	@FindBy(how = How.XPATH, using = "//li[@class='header-account']/div/button")
	public WebElement partSales_Account;
	@FindBy(how = How.XPATH, using = "//li[@class='wps-cart-box']/a/span[1]")
	public WebElement partSales_IconCart;
	@FindBy(how = How.XPATH, using = "//*[@id='wps-top-cart']/section/button")
	public WebElement partSales_ViewCart;
	@FindBy(how = How.XPATH, using = "//div[@class='btn-Checkout']")
	public WebElement partSales_GuestCheckout;
	
	
	

	
}
