package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CTOPage {
	public WebDriver PageDriver;

	public CTOPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}

	@FindBy(how = How.ID, using = "login-username")
	public WebElement Login_IDTextBox;
	@FindBy(how = How.ID, using = "login-password")
	public WebElement Login_PasswordTextBox;
	@FindBy(how = How.ID, using = "login_button")
	public WebElement Login_LoginButton;

	@FindBy(how = How.XPATH, using = "//a[@href='#nsearch']")
	public WebElement Home_SearchButton;
	@FindBy(how = How.XPATH, using = "//h4[contains(text(),'Search by')]/..//input")
	public WebElement SearchPage_SearchText;
	@FindBy(how = How.XPATH, using = "//tr[contains(@href,'WW###')]")
	public WebElement SearchPage_SearchResult;
	@FindBy(how = How.XPATH, using = "//input[contains(@class,'model-search')]")
	public WebElement SearchPage_ModalSearchInput;
	@FindBy(how = How.XPATH, using = "(//*[@class='search-row'])[1]")
	public WebElement SearchPage_FirstSearchResult;
	@FindBy(how = How.XPATH, using = ".//*[@id='create-from-me-btn']")
	public WebElement ModalPage_CreateFrom;
	@FindBy(how = How.XPATH, using = ".//*[@id='create-from-me-modal']//button[@title='Select']")
	public WebElement ModalPage_SelectCountry;
	@FindBy(how = How.XPATH, using = "//*[@id='create-from-me-modal']//div[@class='bs-searchbox']/input")
	public WebElement ModalPage_CountryInput;
	@FindBy(how = How.XPATH, using = ".//*[@id='add-selected-models']")
	public WebElement ModalPage_BundleAdd;
	@FindBy(how = How.XPATH, using = ".//*[@id='confirm-create-from-me']")
	public WebElement ModalPage_AddConfirm;
	@FindBy(how = How.ID, using = "large-enterprize")
	public WebElement BundlePage_B2BAudience;
	@FindBy(how = How.ID, using = "public")
	public WebElement BundlePage_B2CAudience;
	@FindBy(how = How.XPATH, using = "//label[text()='Start at']")
	public WebElement BundlePage_StartAt;
	@FindBy(how = How.XPATH, using = "//label[text()='Nomenclature']")
	public WebElement BundlePage_Nomenclature;
	@FindBy(how = How.ID, using = "componentINTEL_CORE_I5_7300U_VPRO_MB")
	public WebElement BundlePage_I5CPU;
	@FindBy(how = How.ID, using = "componentINTEL_CORE_I7_7500U_MB")
	public WebElement BundlePage_I7CPU;
	@FindBy(how = How.XPATH, using = ".//*[@id='groups-selector']/tbody[6]/tr[1]/td[1]/span")
	public WebElement BundlePage_CPUCollapse;
	@FindBy(how = How.XPATH, using = ".//*[@id='groups-selector']/tbody[15]/tr[1]/td[1]/span")
	public WebElement BundlePage_MemoryCollapse;
	@FindBy(how = How.ID, using = "publish-btn")
	public WebElement BundlePage_Publish;
	@FindBy(how = How.CSS, using = ".save-product-btn")
	public WebElement BundlePage_Save;
	@FindBy(how = How.ID, using = "confirm-publish")
	public WebElement BundlePage_PublishConfirm;
	@FindBy(how = How.XPATH, using = "//div[text()='Configuration successfully published']")
	public WebElement BundlePage_PublishSuccMsg;
	@FindBy(how = How.ID, using = "link-from-me-btn")
	public WebElement LinkFromMe;

	// 19008
	@FindBy(how = How.XPATH, using = "(//td[contains(@class,'helper-hide yes') and @property='summary']/../td[contains(@class,'yes')])[1]")
	public WebElement BundlePage_firstOpen;
	@FindBy(how = How.XPATH, using = "//td[contains(@class,'helper-hide yes') and @property='summary']/../td[contains(@class,'yes')]/../td[@class='char-title']/span[@class='char-id']")
	public WebElement BundlePage_firstTitleID;
	@FindBy(how = How.XPATH, using = "//p[@class='value-label']/../../../../td[contains(@class,'value-open clickable yes')]/../td[@class='value-title']//span[@class='value-id']")
	public WebElement BundlePage_secondeTitleID;
	@FindBy(how = How.XPATH, using = "//button[@id='translate-btn']")
	public WebElement BundlePage_translationsButton;
	@FindBy(how = How.XPATH, using = "//h3[contains(text(),'Translations')]")
	public WebElement BundlePage_translateTitle;
	@FindBy(how = How.XPATH, using = "//button[@id='btn-save']")
	public WebElement BundlePage_translateSaveButton;
	@FindBy(how = How.XPATH, using = "//button[@id='btn-back']")
	public WebElement BundlePage_translateBackButton;
	@FindBy(how = How.XPATH, using = "//button[@title='Language']")
	public WebElement BundlePage_languageSel;
	@FindBy(how = How.ID, using = "btn-change-locale")
	public WebElement BundlePage_changeLocale;
	@FindBy(how = How.XPATH, using = "//table[contains(@id,'DataTables_Table')]/tbody/tr[1]/td[1]")
	public WebElement HomePage_firstResult;
	@FindBy(how = How.XPATH, using = ".//*[@id='delta-data-sync-btn']")
	public WebElement HomePage_syncRuleButton;
	@FindBy(how = How.XPATH, using = "//button[@data-id='rule_types_select']")
	public WebElement HomePage_ruleType;
	@FindBy(how = How.XPATH, using = "//span[text()='ValueOptionText']")
	public WebElement HomePage_valueOptionText;
	@FindBy(how = How.XPATH, using = "//span[text()='CharacterOptionText']")
	public WebElement HomePage_characterOptionText;
	@FindBy(how = How.XPATH, using = ".//*[@id='confirmRuleTypes']")
	public WebElement HomePage_confirmRuleTypes;
	@FindBy(how = How.XPATH, using = ".//*[@id='action_confirm']")
	public WebElement HomePage_confirmYes;
	@FindBy(how = How.XPATH, using = ".//*[@id='refresh-log-btn']")
	public WebElement HomePage_refreshButton;

	// NA-17709
	@FindBy(how = How.ID, using = "add-options-section")
	public WebElement ModalPage_addOptionsBtn;
	@FindBy(how = How.XPATH, using = "//button[@title='Select options...']")
	public WebElement ModalPage_selectOptions;
	@FindBy(how = How.ID, using = "save-options-section")
	public WebElement ModalPage_addOptionsOK;
	@FindBy(how = How.ID, using = "public")
	public WebElement ModalPage_publicChk;
	@FindBy(how = How.ID, using = "large-enterprize")
	public WebElement ModalPage_enterpriseChk;
	@FindBy(how = How.ID, using = "save-configuration")
	public WebElement ModalPage_save;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Configuration updated successfully')]")
	public WebElement ModalPage_saveMsg;
	@FindBy(how = How.ID, using = "invalid-modal")
	public WebElement BundlePage_invalidModal;
	@FindBy(how = How.XPATH, using = "//*[@id='invalid-modal']//button[@class='close']")
	public WebElement BundlePage_closeInvalidModal;
	@FindBy(how = How.XPATH, using = "//*[@id='show-pending-rules-btn']")
	public WebElement BundlePage_approveRulesBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='show-pending-rules']")
	public WebElement BundlePage_rulesPanel;
	@FindBy(how = How.XPATH, using = "//*[@id='approve-rules']")
	public WebElement BundlePage_approvebtn;
	@FindBy(how = How.XPATH, using = "//div[@class='bootstrap-dialog-message']")
	public WebElement BundlePage_dialogMsg;
	@FindBy(how = How.XPATH, using = "//div[@class='modal-dialog']//*[contains(text(),' Ok')]")
	public WebElement BundlePage_okOnDialog;
	@FindBy(how = How.XPATH, using = "//span[@class='glyphicon glyphicon-floppy-save ']/..")
	public WebElement BundlePage_saveBtn;
	
	//na-18781
	@FindBy(how = How.XPATH, using = "//*[contains(@groupid,'DUM_OPT_AutoTest_')][contains(@class,'delete-opt-section')]")
	public WebElement ModalPage_deleteOptionsBtn;
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary'][text()='OK']")
	public WebElement ModalPage_deleteOptionsOK;
	
	//18082
	@FindBy(how = How.XPATH, using = "//button/b[text()='PREVIEW OPTIONS']/..")
	public WebElement BundlePage_PreviewOptions;
	@FindBy(how = How.XPATH, using = "//button/span[text()='Select audience']")
	public WebElement PreviewOption_SelectAudience;
	@FindBy(how = How.XPATH, using = "//a/span[text()='Public']")
	public WebElement PreviewOption_AudiencePublic;
	@FindBy(how = How.ID, using = "select_b2c_unit")
	public WebElement PreviewOption_SelectB2Cunit;
	@FindBy(how = How.XPATH, using = "//li[@id='PUBLIC_GLOBAL_B2C_UNIT']/i")
	public WebElement PreviewOption_GloablB2Cunit;	
	@FindBy(how = How.XPATH, using = "//li[@id='Region-ANZ']/i")
	public WebElement PreviewOption_RegionANZ;
	@FindBy(how = How.XPATH, using = "//li[@id='Australia']/i")
	public WebElement PreviewOption_Australia;
	@FindBy(how = How.XPATH, using = "//li[@id='auPublic_parent']/i")
	public WebElement PreviewOption_AuPublicParent;
	@FindBy(how = How.XPATH, using = "//li[@id='auPublic_unit']/i")
	public WebElement PreviewOption_AuPublicUnit;
	@FindBy(how = How.ID, using = "select-b2c")
	public WebElement PreviewOption_SelectButton;
	@FindBy(how = How.ID, using = "debug_view")
	public WebElement PreviewOption_DebugViewCheck;
	@FindBy(how = How.ID, using = "preview-btn")
	public WebElement BundlePage_PreviewButton;	
	@FindBy(how = How.XPATH, using = "//div[@id='debug_container']/div/p/strong[contains(.,'List override price:')]/../span")
	public WebElement Debug_ListOverridePrice;
	

	@FindBy(how = How.XPATH, using = "//input[@name='NB_CPU'][@already-checked='true']/../../../../../tr[1]/td[1]/span")
	public WebElement BundlePage_firstCPUCollapse;
	@FindBy(how = How.XPATH, using = "//input[contains(@class,'value-image')]")
	public WebElement BundlePage_CPUImageUrl;
	@FindBy(how = How.XPATH, using = "//button[text()='×' and @data-value='FR']")
	public WebElement BundlePage_FRDelete;
	@FindBy(how = How.XPATH, using = "//button[text()='×' and @data-value='JA']")
	public WebElement BundlePage_JPDelete;
	@FindBy(how = How.XPATH, using = "//button[text()='×' and @data-value='ZH']")
	public WebElement BundlePage_CHDelete;
	
	//28176
	@FindBy(how = How.XPATH, using = "//tr[@groupid='NB_CPU']/td/span[@class='icon fa fa-caret-square-o-down']")
	public WebElement BundlePage_FirstCPUCollapse;
	
	
}
