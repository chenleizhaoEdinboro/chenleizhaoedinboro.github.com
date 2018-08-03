package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MailPage {
	public WebDriver PageDriver;

	public MailPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}

	public String homePageUrl = "https://www.guerrillamail.com/inbox";

	// @FindBy(how = How.ID, using = "publicinboxfield")
	// public WebElement Inbox_InboxTextBox;
	// @FindBy(how = How.XPATH, using = "//button[@title='Go!']")
	// public WebElement Inbox_GoButton;
	// @FindBy(how = How.ID, using = "loginEmail")
	// public WebElement Login_EmailTextBox;
	// @FindBy(how = How.ID, using = "loginPassword")
	// public WebElement Login_PasswordTextBox;
	// @FindBy(how = How.XPATH, using =
	// "//button[contains(@class,'btn-block')]")
	// public WebElement Login_LoginButton;
	// @FindBy(how = How.XPATH, using = "//a[contains(.,'Back to Login')]")
	// public WebElement Mail_BackToLoginLink;

	@FindBy(how = How.ID, using = "inbox-id")
	public WebElement Inbox_EditButton;
	@FindBy(how = How.XPATH, using = "//span[@id='inbox-id']/input")
	public WebElement Inbox_InputEmail;
	@FindBy(how = How.XPATH, using = "//span[@id='inbox-id']/button[contains(@class,'save button')]")
	public WebElement Inbox_SetButton;
	@FindBy(how = How.XPATH, using = "//tbody[@id='email_list']/tr[1]/td[3]")
	public WebElement Inbox_EmailSubject;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Back to Login')]")
	public WebElement Inbox_BackToLoginLink;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Back to Store')]")
	public WebElement Inbox_BackToStoreLink;

	@FindBy(how = How.ID, using = "inboxfield")
	public WebElement Mail_mailinator;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'btn-dark')]")
	public WebElement Mail_LoginButton;
	@FindBy(how = How.XPATH, using = "(//div[5][contains(@class,'outermail')]/div)[1]")
	public WebElement Mail_mailinatorCheckInbox;
	@FindBy(how = How.XPATH, using = "//div[contains(.,'This Inbox is currently Empty')]")
	public WebElement Mail_mailinatorEmptyInbox;
	@FindBy(how = How.ID, using = "publicshowmaildivcontent")
	public WebElement Mail_mailinatorIframe;
	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'Back to ')]")
	public WebElement Mail_backToStore;

	// 22370
	@FindBy(how = How.XPATH, using = "//input[@id='login']")
	public WebElement yopMail_login;
	@FindBy(how = How.XPATH, using = "//input[@class='sbut']")
	public WebElement yopMail_checkInbox;

	// 20149
	@FindBy(how = How.XPATH, using = "//div[@id='display_email']//div[@class='email']//tr[4]/td[1][contains(.,'Total:')]/../../../../../td[3]//tr[4]/td")
	public WebElement Mail_totalPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='display_email']//tr[1]/td[contains(.,'Forma de pago:')]/../../tr[3]/td")
	public WebElement Mail_paymentType;
	@FindBy(how = How.XPATH, using = "//div[@id='display_email']//tr[1]/td[contains(.,'Dirección de facturación:')]/../../tr[3]/td")
	public WebElement Mail_shippingInfo;
	@FindBy(how = How.XPATH, using = "//div[@id='display_email']//tr[1]/td[contains(.,'Dirección de entrega:')]/../../tr[3]/td")
	public WebElement Mail_paymentInfo;
	// 18406
	@FindBy(how = How.ID, using = "back_to_inbox_link")
	public WebElement Mail_backToInbox;
	//18817
	@FindBy(how = How.XPATH, using = "(//td[contains(.,'lenovo.orders@lenovo.com')])[1]")
	public WebElement Email_Subject;


	@FindBy(how = How.XPATH, using = "(//table[@align='center']//tr/td[@align='left']/span)[2]")
	public WebElement Email_Product;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='display_email']//table[@align='center']//td[@align='left']/p[2]/font")
	public WebElement Email_Content;
	
	@FindBy(how = How.XPATH, using = "(//table[@align='center']//tr/td[@align='left']/span)[5]")
	public WebElement Email_ProductPrice;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='display_email']//strong[@class='email_to']")
	public WebElement Email_address;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='display_email']//table[@align='center']//td[@align='left']/p[3]/font")
	public WebElement Email_Name;

	//17671
	@FindBy(how = How.XPATH, using = "//a/font[contains(.,'https://pre-c-hybris.lenovo.com')]")
	public WebElement Email_PasswordResetLink;	
    @FindBy(how = How.XPATH, using = ".//*[@id='updatePwd-pwd']")
	public WebElement Email_UpdatePassword;
    @FindBy(how = How.XPATH, using = ".//*[@id='updatePwd.checkPwd']")
	public WebElement Email_NewPassBox;
    @FindBy(how = How.XPATH, using = ".//*[@id='updatePwdForm']/span/button")
	public WebElement Email_ConfirmNewPass;
    @FindBy(how = How.XPATH, using = ".//*[@id='nemob2bRegisterForm']/div/button")
	public WebElement Email_GoToLogin;

	
	@FindBy(how = How.XPATH, using = ".//*[@id='display_email']//table[@align='center']//td[@align='left']/p[3]/font/a/font")
	public WebElement Email_Store;

	//NA-18480
	@FindBy(how = How.XPATH, using = ".//*[@id='display_email']//img[contains(@alt,'Seal')]")
	public WebElement email_countrySeal;
	//NA-18479
	@FindBy(how = How.XPATH, using = ".//*[@id='display_email']//img[contains(@alt,'Quote_with_Chop')]")
	public WebElement email_countrySeal_1;
	//NA-17640
	@FindBy(how = How.XPATH, using = "(//td[contains(.,'Cart From')])[1]")
  	public WebElement SavedCart_EmailSub17640;	
	
	//NA-17696
	@FindBy(how = How.XPATH, using = ".//*[@id='display_email']//tbody/tr[1]/td[@align='center'][contains(.,'Reseller')]")
  	public WebElement Mailbody_ResellerText;
	
	@FindBy(how = How.XPATH, using = "//strong[contains(text(),'Ship Group 1')]/../../../tr[2]/td/strong")
  	public WebElement GroupAddress1;
	@FindBy(how = How.XPATH, using = "//strong[contains(text(),'Ship Group 2')]/../../../tr[2]/td/strong")
  	public WebElement GroupAddress2;
	@FindBy(how = How.XPATH, using = "//strong[contains(text(),'Ship Group 3')]/../../../tr[2]/td/strong")
  	public WebElement GroupAddress3;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Shipping Address:')]/../../tr[3]/td")
  	public WebElement ShippingAddress;
	
	//17692
	@FindBy(how = How.ID, using = "show_original_link")
	public WebElement showOriginal;
	
	
}
