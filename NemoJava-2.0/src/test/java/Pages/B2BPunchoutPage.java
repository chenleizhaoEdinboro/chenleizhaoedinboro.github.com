package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class B2BPunchoutPage {
	public WebDriver PageDriver;

	public B2BPunchoutPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}
	
	// Common
	@FindBy(how = How.XPATH, using = ".//a[@class='bar_1-logo-link']")
	public WebElement Comm_Logo;
	@FindBy(how= How.ID, using="punchoutUserName")
	public WebElement Ariba_Username;
	@FindBy(how=How.ID,using="punchoutPassword")
	public WebElement Ariba_Password;
	@FindBy(how=How.XPATH, using=".//*[@id='punchoutloginform']/input[3]")
	public WebElement Ariba_Login;

	
	// Ariba
	@FindBy(how = How.ID, using = "punchoutBtn")
	public WebElement Ariba_PunchoutButton;
	@FindBy(how = How.ID, using = "fromDomain")
	public WebElement Ariba_DomainTextBox;
	@FindBy(how = How.ID, using = "fromIdentity")
	public WebElement Ariba_IdentityTextBox;
	@FindBy(how = How.ID, using = "sharedSecret")
	public WebElement Ariba_SharedSecretTextBox;
	@FindBy(how = How.ID, using = "extrinsicKey1")
	public WebElement Ariba_InboundKeyTextBox;
	@FindBy(how = How.ID, using = "extrinsicValue1")
	public WebElement Ariba_InboundValueTextBox;
	@FindBy(how = How.ID, using = "displayFrame")
	public WebElement Ariba_Frame;
	@FindBy(how = How.ID, using = "addExtrinsicBtn")
	public WebElement Ariba_AddExtrinsicButton;

	// OCI
	@FindBy(how = How.NAME, using = "USERNAME")
	public WebElement OCI_UserNameTextBox;
	@FindBy(how = How.NAME, using = "PASSWORD")
	public WebElement OCI_PasswordTextBox;
	@FindBy(how = How.ID, using = "catViewName")
	public WebElement OCI_InboundKeyTextBox;
	@FindBy(how = How.ID, using = "catViewValue")
	public WebElement OCI_InboundValueTextBox;
	@FindBy(how = How.ID, using = "proceedBtn")
	public WebElement OCI_PunchoutButton;
	@FindBy(how = How.ID, using = "orderList")
	public WebElement OCI_OrderList;


	// Oracle
	@FindBy(how = How.ID, using = "loginRequest")
	public WebElement Oracle_LoginRequestTextBox;
	@FindBy(how = How.ID, using = "punchoutBtn")
	public WebElement Oracle_PunchoutButton;
	@FindBy(how = How.ID, using = "displayFrame")
	public WebElement Oracle_Frame;

	// added by qianqian
	@FindBy(how = How.XPATH, using = "//a[text()='Lenovo']")
	public WebElement Lenovo_Link;
	@FindBy(how = How.ID, using = "suppCode")
	public WebElement Ariba_SuppCode;
	@FindBy(how = How.ID, using = "editBtn")
	public WebElement Ariba_EditButton;
	@FindBy(how = How.ID, using = "inspectBtn")
	public WebElement Ariba_InspectButton;

	// 13426
	@FindBy(how = How.XPATH, using = "//a[@class='has-nosubmenu']/span[text()='Cart']/..")
	public WebElement Ariba_CartButton;
	@FindBy(how = How.XPATH, using = "//input[@id='quickOrderProductId']")
	public WebElement Ariba_QuickOrderProduct;
	@FindBy(how = How.XPATH, using = "//div[@id='quickAddInput']/a")
	public WebElement Ariba_AddButton;
	@FindBy(how = How.XPATH, using = "//a[@id='validateDateformatForCheckout']")
	public WebElement Ariba_CheckOutButton;
	@FindBy(how = How.XPATH, using = "//div[@id='orderList']/xmp[1]")
	public WebElement Ariba_OrderListCartMessage;

	// 13819
	@FindBy(how = How.XPATH, using = "//li[@class='diff_menu _no_small_screen']//span[contains(text(),'Saved Carts')]")
	public WebElement Ariba_savedCarts;
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'signout_menu')]//span[contains(text(),'Exit')]")
	public WebElement Ariba_exit;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'dropdown-menu store-selector')]")
	public WebElement Ariba_blockChangeStore;
	@FindBy(how = How.XPATH, using = "//li[@class='diff_menu _no_small_screen']")
	public List<WebElement> Ariba_diffMenu;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderList']/h3[text()='Order returned from Nemo Punchout']")
	public WebElement Ariba_returnedMsg;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderList']/h3[text()='Exit -- Return from Hybris with blank form']")
	public WebElement Oci_returnedBlankMsg;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderList']/h3[text()='Form returned from Nemo Punchout']")
	public WebElement Oci_returnedMsg;
	@FindBy(how = How.XPATH, using = "//div[@id='orderList']")
	public WebElement Oci_OrderListCartMessage;
	
	//21963
	@FindBy(how = How.XPATH, using = "//a[@id='savedcart_button_new']")
	public WebElement Ariba_SaveCart;
	@FindBy(how = How.XPATH, using = "//input[@id='privateSaveCart']")
	public WebElement Ariba_privateSaveCartn;
	@FindBy(how = How.XPATH, using = "//input[@id='companySaveCart']")
	public WebElement Ariba_companySaveCartn;
	@FindBy(how = How.XPATH, using = "//textarea[@id='realsavecartname']")
	public WebElement Ariba_realsavecartname;
	@FindBy(how = How.XPATH, using = "//input[@id='addToCartButtonTop']")
	public WebElement Ariba_saveButton;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//div[@class='savedCartBody']/h2")
	public WebElement Ariba_savedCartPage;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//tbody[@class='data-table-content']/tr[1]/td[2]/h3")
	public WebElement Ariba_cartName;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//a[contains(.,'Show all results')]")
	public WebElement Ariba_showAllResult;
	@FindBy(how = How.XPATH, using = "//button[@id='openCartButton']")
	public WebElement Ariba_openCart;
	@FindBy(how = How.XPATH, using = "//div[@id='orderList']/h3")
	public WebElement Ariba_orderList;
	
	//19767
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Services & Warranty')]")
	public WebElement B2Bsite_service;
}
