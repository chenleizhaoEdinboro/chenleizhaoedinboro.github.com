package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CRMPage {
	
	public WebDriver pageDriver;
	public CRMPage(WebDriver driver){
		this.pageDriver = driver;
		PageFactory.initElements(pageDriver, this);
	}
	
	
	// added by gaopan
	@FindBy(how= How.ID,using="sap-user")
    public WebElement NameInput;
    @FindBy(how= How.ID,using = "sap-password")
    public WebElement PasswordInput;
    @FindBy(how= How.ID,using = "LOGON_BUTTON")
    public WebElement SinIn;
    @FindBy(how = How.ID,using = "sap-language-dropdown")
    public WebElement EnglishDropDown;
    @FindBy(how = How.XPATH,using = "//td[text()='EN']")
    public WebElement English;
    @FindBy(how = How.ID,using = "Z_INTRNALALL")
    public WebElement Role;
    @FindBy(how = How.XPATH,using = ".//*[contains(@id,'_ZSL-CYCLE')]")
    public WebElement SalesCycle;
    @FindBy(how = How.XPATH,using = ".//div[@id='thtmlbHomeBox']//a[contains(@id,'_SLS-SLO-SR')]")
    public WebElement SalesOrder;
    @FindBy(how = How.XPATH,using = ".//*[contains(@id,'_btqslsord_parameters[2].VALUE1')]")
    public WebElement OrderIDInput;
    @FindBy(how = How.XPATH,using = ".//div[@id='scrollArea']//a[contains(@id,'_Searchbtn')]/span")
    public WebElement OrderSearch;
    @FindBy(how = How.XPATH,using = ".//*[contains(@id,'.object_id')]")
    public WebElement ResultId;
    @FindBy(how = How.XPATH,using = ".//*[contains(@id,'.concatstatuser')]")
    public WebElement ResultStatus;
    @FindBy(how = How.XPATH,using = "//div[@id='th-mes-inf-cont']/div/span[2]")
    public WebElement ErrorNumber;
    @FindBy(how = How.XPATH,using = ".//*[contains(@id,'CRMMessageLine')]")
    public WebElement ErrorMesage;
    @FindBy(how = How.XPATH,using = ".//span[contains(@id,'_btcumulath_struct.net_value')]")
    public WebElement PriceNotTax;
    @FindBy(how = How.XPATH,using = ".//span[contains(@id,'_btcumulath_struct.tax_amount')]")
    public WebElement PriceOfTax;
    @FindBy(how = How.XPATH,using = ".//span[contains(@id,'btcumulath_struct.gross_value')]")
    public WebElement PriceWithTax;
	

	
	
	
	
	
	
	
}
