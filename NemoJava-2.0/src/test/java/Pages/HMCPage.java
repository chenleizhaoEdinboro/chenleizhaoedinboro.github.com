package Pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HMCPage {
	public WebDriver PageDriver;

	public HMCPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}

	// Login page
	@FindBy(how = How.ID, using = "Main_user")
	public WebElement Login_IDTextBox;
	@FindBy(how = How.ID, using = "Main_password")
	public WebElement Login_PasswordTextBox;
	@FindBy(how = How.ID, using = "Main_rememberme")
	public WebElement Login_RememberCheckBox;
	@FindBy(how = How.ID, using = "Main_label")

	public WebElement Login_LoginButton;
	@FindBy(how = How.XPATH, using = "//table[contains(@class,'listtable selecttable')]/tbody/tr[2]")
	public WebElement B2BUnit_DeliveryMode_SettingValue;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.enableCompleteShipping]]_true")
	public WebElement SiteAttribute_CompleteShipping_Yes;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.enableGroupShipping]]_true")
	public WebElement SiteAttribute_GroupShipping_Yes;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.enableLineItemShipping]]_true")
	public WebElement SiteAttribute_LineItemShipping_Yes;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.enableCRAD]]_true")
	public WebElement SiteAttribute_EnableCARDShipping_Yes;
	@FindBy(how = How.ID, using = "Content/IntegerEditor[in Content/Attribute[B2BUnit.groupNumberRange]]_input")
	public WebElement SiteAttribute_MaxGroupShipping;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.disablePaymentMessageFallback]]_false")
	public WebElement SiteAttribute_DisablePaymentMsgFallback_No;

	@FindBy(how = How.XPATH, using = "//a[contains(.,'B2B Commerce')]")
	public WebElement Home_B2BCommerceLink;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[B2BCustomer]_label")
	public WebElement Home_B2BCustomer;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/GenericCondition[B2BCustomer.uid]]_input']")
	public WebElement B2BCustomer_SearchIDTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[B2BCustomer]_searchbutton']")
	public WebElement B2BCustomer_SearchButton;
	@FindBy(how = How.XPATH, using = "//div/a[@title='Open Editor']/span/img")
	public WebElement B2BCustomer_1stSearchedResult;
	@FindBy(how = How.XPATH, using = "//a/span[contains(.,'Password')]")
	public WebElement B2BCustomer_PasswordTab;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[B2BCustomer.b2BUserStatus]]_select']")
	public WebElement B2BCustomer_ActiveUserDropdown;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/ImageToolbarAction[organizer.editor.save.title]_img']")
	public WebElement Common_SaveButton;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'B2BCustomer.b2BUserStatus')]/option[contains(.,'ACTIVE')]")
	public WebElement B2BCustomer_ActiveAccountDropdownValue;
	@FindBy(how = How.ID, using = "Tree/GenericExplorerMenuTreeNode[system]_label")
	public WebElement Home_System;
	@FindBy(how = How.XPATH, using = ".//a[contains(text(),'rediscacheclean')]")
	public WebElement Home_RadisCacheCleanLink;
	@FindBy(how = How.XPATH, using = ".//*[@id='ps_product']")
	public WebElement Radis_CleanProductTextBox;
	@FindBy(how = How.ID, using = "ps_button")
	public WebElement Radis_CleanButton;
	@FindBy(how = How.XPATH, using = "//td/a[contains(text(),'B2C Commerce')]")
	public WebElement Home_B2CCommercelink;
	@FindBy(how = How.XPATH, using = "(//a[contains(text(),'b2cunit') or contains(text(),'B2C Unit')])[1]")
	public WebElement Home_B2CUnitLink;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[B2CUnit.uid]]_input")
	public WebElement B2CUnit_IDTextBox;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2CUnit.sMBPriceTireeToggle]]_true")
	public WebElement B2CUnit_SMBToggleYes;
	@FindBy(how = How.ID, using = "Content/NemoSearchConfigurable[B2CUnit]_searchbutton")
	public WebElement B2CUnit_SearchButton;
	@FindBy(how = How.XPATH, using = "(//div/div/table/tbody/tr/td[2]/a)[1]")
	public WebElement B2CUnit_FirstSearchResultItem;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Site Attributes')]")
	public WebElement B2CUnit_SiteAttributeTab;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2CUnit.hideThankYouPageRegistrationToggle]]_false")
	public WebElement B2CUnit_NoHideThankyouPageRadioButton;
	@FindBy(how = How.ID, using = "Toolbar/ImageToolbarAction[closesession]_img")
	public WebElement Home_EndSessionLink;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Pricing Tier')]")
	public WebElement PricingCockpit_PricingTierTab;
	@FindBy(how = How.XPATH, using = "//button[contains(@class, 'updateTier')]")
	public WebElement PricingCockpit_UpdateTierButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericExplorerMenuTreeNode[catalog]_label']")
	public WebElement Home_CatalogLink;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[Product]_label']")
	public WebElement Home_ProductsLink;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/GenericCondition[Product.code]]_input']")
	public WebElement Catalog_ArticleNumberTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[Product]_searchbutton']")
	public WebElement Catalog_SearchButton;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'masterMultiCountryProductCatalog') and contains(@id,'Online') and @class='olecEntry']")
	public WebElement Catalog_MultiCountryCatOnlineLinkInSearchResult;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'masterMultiCountryProductCatalog') and contains(@id,'Staged') and @class='olecEntry']")
	public WebElement Catalog_MultiCountryCatStagedLinkInSearchResult;
	@FindBy(how = How.ID, using = "Content/EditorTab[OptionCompatibility.tab.attributeoverride]_span")
	public WebElement Catalog_AttributeOverrideTabLink;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'remove')]")
	public WebElement Catalog_RemoveMenu;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_ProductAttributeOverrideRules_label')]")
	public WebElement Catalog_CreateNewRuleMenu;

	// added by gaopan
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[B2BUnit]_label']")
	public WebElement Home_B2BUnitLink;
	@FindBy(how = How.XPATH, using = "(//table[@class='stringLayoutChip']/tbody/tr/td/input)[1]")
	public WebElement B2BUnit_SearchIDTextBox;
	@FindBy(how = How.XPATH, using = "//a[@title='Search']/span[contains(.,'Search')]")
	public WebElement B2BUnit_SearchButton;
	@FindBy(how = How.XPATH, using = "//div/div/table/tbody/tr/td[2]/a")
	public WebElement B2BUnit_ResultItem;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EditorTab[B2BUnit.tab.siteattributes]_span']")
	public WebElement B2BUnit_siteAttribute;
	@FindBy(how = How.XPATH, using = "(//th[contains(.,'Delivery Mode Identifiers')])")
	public WebElement B2BUnit_siteAttribute_DeliveryModeIdentifiers;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/NemoB2BDeliveryModeFlatRate[in Content/Attribute[B2BUnit.deliveryModeAndFlatRate]]_!add_true_label']")
	public WebElement B2BUnit_siteAttribute_AddDeliveryModeAndFlatRate;
	@FindBy(how = How.XPATH, using = ".//*[@id='StringEditor[in GenericCondition[DeliveryMode.code]]_input']")
	public WebElement B2BUnit_DeliveryMode_TextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='ModalGenericFlexibleSearchOrganizerSearch[DeliveryMode]_searchbutton']")
	public WebElement B2BUnit_DeliveryMode_SearchButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='ModalGenericItemSearchList[DeliveryMode][1]_use']")
	public WebElement B2BUnit_DeliveryMode_UseButton;
	@FindBy(how = How.XPATH, using = "//table[contains(@class,'listtable selecttable')]/tbody/tr[2]")
	public WebElement B2BUnit_Mode_SettingValue;
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Use')]")
	public WebElement B2BUnit_DeliveryMode_SettingUse;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'expedite-shipping-gross')]")
	public WebElement B2BUnit_SiteAttribute_expediteRate;
	@FindBy(how = How.XPATH, using = "(//td/div[@class='label'])[3]")
	public WebElement B2BUnit_Save;
	@FindBy(how = How.ID, using = "Tree/GenericExplorerMenuTreeNode[order]_label")
	public WebElement Home_Order;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[Order]_label")
	public WebElement Home_Order_Orders;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[Order.code]]_input")
	public WebElement Home_Order_OrderID;
	@FindBy(how = How.ID, using = "Content/NemoSearchConfigurable[Order]_searchbutton")
	public WebElement Home_Order_OrderSearch;
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[Order]_innertable']//tr[3]/td[8]/div")
	public WebElement Home_Order_OrderStatus;
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[Order]_innertable']//tr[3]//img")
	public WebElement Home_Order_OrderDetail;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Incl. Tax:')]/../../td[5]//input")
	public WebElement Home_Order_TaxPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Total Price:')]/../../td[5]//input")
	public WebElement Home_Order_ProductPrice;
	@FindBy(how = How.XPATH, using = "//div[@class='organizereditortab']/ul/li[last()]//a")
	public WebElement Home_Order_OrderAdmin;
	@FindBy(how = How.XPATH, using = ".//*[@id='Toolbar/ImageToolbarAction[closesession]_img']")
	public WebElement Home_closeSession;

	// 15487
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'pricesettings')]")
	public WebElement Home_PriceSettings;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Pricing Cockpit')]")
	public WebElement Home_PricingCockpit;
	@FindBy(how = How.XPATH, using = "//iframe[@src='/europe1novarto/index.jsp?lang=en']")
	public WebElement PricingCockpit_iframe;
	@FindBy(how = How.XPATH, using = "//div[1]/input[@name='j_username']")
	public WebElement PricingCockpit_username;
	@FindBy(how = How.XPATH, using = "//div[2]/input[@name='j_password']")
	public WebElement PricingCockpit_password;
	@FindBy(how = How.XPATH, using = "//div[4]/button[contains(.,'Sign in')]")
	public WebElement PricingCockpit_Login;
	@FindBy(how = How.XPATH, using = "//div/a[contains(@href,'b2b/priceSimulate')]")
	public WebElement PricingCockpit_B2BpriceSimulate;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select Country...')]")
	public WebElement B2BpriceSimulate_CountryButton;
	@FindBy(how = How.XPATH, using = "//div[@id='select2-drop']/div/input")
	public WebElement B2BpriceSimulate_CountryText;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select Store...')]")
	public WebElement B2BpriceSimulate_StoreButton;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select Catalog...')]")
	public WebElement B2BpriceSimulate_CatalogButton;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select B2B Unit...')]")
	public WebElement B2BpriceSimulate_B2BUnitButton;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'b2b_unit') and not (contains(@class,'select2-container-disabled'))]")
	public WebElement B2BpriceSimulate_B2BUnitDiv;
	@FindBy(how = How.ID, using = "Content/EditorTab[Order.payment_and_delivery]_span")
	public WebElement PaymentAndDeliveryTab;
	@FindBy(how = How.ID, using = "Content/GenericReferenceEditor[in Content/Attribute[Order.paymentInfo]]_div")
	public WebElement PaymentInfoLabel;

	@FindBy(how = How.XPATH, using = "//*[@id='ps_date']")
	public WebElement B2BpriceSimulate_DateButton;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select Product...')]")
	public WebElement B2BpriceSimulate_ProductButton;
	@FindBy(how = How.XPATH, using = "//td[@class='web']/samp[@id='value']")
	public WebElement B2BpriceSimulate_webPrice;
	@FindBy(how = How.XPATH, using = "//*[@id='ps_button']")
	public WebElement B2BpriceSimulate_DebugButton;
	@FindBy(how = How.XPATH, using = "//td/a[@id='Tree/GenericExplorerMenuTreeNode[group.nemo]_label']")
	public WebElement Home_Nemo;
	@FindBy(how = How.XPATH, using = "//td/a[@id='Tree/GenericLeafNode[AbstractWebForm]_label']")
	public WebElement Home_WebFormANZ;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[AbstractWebForm.emailAddress]]_input']")
	public WebElement WebFormANZ_Email;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/NemoSearchConfigurable[AbstractWebForm]_searchbutton']")
	public WebElement WebFormANZ_SearchButton;
	@FindBy(how = How.XPATH, using = "//*[@id='Tree/GenericLeafNode[Contract]_label']")
	public WebElement Home_Contract;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Contract.productCode')]")
	public WebElement Contract_productCode;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement Contract_searchbutton;
	@FindBy(how = How.XPATH, using = "(//tbody/tr[contains(@id,'Content/OrganizerListEntry')])[last()]")
	public WebElement Contract_searchResult;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Exchange Rate')]")
	public WebElement Home_ExchangeRate;
	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'ExchangeRate.fromCurrency')]")
	public WebElement ExchangeRate_fromCurrency;
	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'ExchangeRate.toCurrency')]")
	public WebElement ExchangeRate_toCurrency;
	@FindBy(how = How.XPATH, using = "(//td[contains(@id,'DoubleDisplay')]/..)[1]")
	public WebElement ExchangeRate_rateRow;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'ExchangeRate.rate')]")
	public WebElement ExchangeRate_rate;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'validFrom')]")
	public WebElement ExchangeRate_validFrom;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NemoZibbySetting.zThresholds')]")
	public WebElement Zibby_Thresholds;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NemoZibbySetting.zItemPriceLimit')]")
	public WebElement Zibby_PriceLimit;

	// NA-15492
	@FindBy(how = How.XPATH, using = "//a[contains(text(),' B2C Price Rules')]")
	public WebElement PricingCockpit_B2CPriceRules;
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Rule type')]")
	public WebElement B2CPriceRules_RuleType;
	@FindBy(how = How.XPATH, using = "//*[@id='rulesTables']/a")
	public WebElement B2CPriceRules_CreateNewGroup;
	@FindBy(how = How.XPATH, using = "//span[text()='Select Rule type...']")
	public WebElement B2CPriceRules_SelectRuleType;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Floor Prices')]")
	public WebElement B2CPriceRules_FloorPriceOption;
	@FindBy(how = How.XPATH, using = "//span[text()='Select Group Type...']")
	public WebElement B2CPriceRules_SelectGroupType;
	@FindBy(how = How.XPATH, using = "//button[text()='Continue']")
	public WebElement B2CPriceRules_Continue;
	@FindBy(how = How.NAME, using = "title")
	public WebElement B2CPriceRules_PriceRuleName;
	@FindBy(how = How.ID, using = "validFrom")
	public WebElement B2CPriceRules_ValidFrom;
	@FindBy(how = How.XPATH, using = "(//span[text()='Select Country...'])[2]")
	public WebElement B2CPriceRules_CountrySelect;
	@FindBy(how = How.XPATH, using = "//span[text()='Select Material...']")
	public WebElement B2CPriceRules_MaterialSelect;
	@FindBy(how = How.XPATH, using = "//div[@id='select2-drop']/div/input")
	public WebElement B2CPriceRules_SearchInput;
	@FindBy(how = How.XPATH, using = "//span[text()='Select Catalog Version...']")
	public WebElement B2CPriceRules_CatalogSelect;
	@FindBy(how = How.XPATH, using = "(.//*[text()='Select Unit...'])[2]")
	public WebElement B2CPriceRules_B2CunitSelect;
	@FindBy(how = How.XPATH, using = ".//*[@id='s2id_autogen42_search']")
	public WebElement B2CPriceRules_B2CunitSearchBox;
	@FindBy(how = How.LINK_TEXT, using = "master store unit")
	public WebElement B2CPriceRules_MasterUnit;
	@FindBy(how = How.XPATH, using = "(//input[@placeholder='Search...'])[2]")
	public WebElement B2CPriceRules_UnitSearch;
	@FindBy(how = How.NAME, using = "operationValue")
	public WebElement B2CPriceRules_PriceValue;
	@FindBy(how = How.XPATH, using = "//button[text()=' Add Price Rule to Group']")
	public WebElement B2CPriceRules_AddPriceRuleToGroup;
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary createGroup']")
	public WebElement B2CPriceRules_CreateGroup;
	@FindBy(how = How.XPATH, using = ".//*[@data-validation='You need to add at least one Rule to create Group!']")
	public WebElement B2CPriceRules_NoRuleError;
	@FindBy(how = How.XPATH, using = "//div[@class='modal-footer']//button[text()='Close']")
	public WebElement B2CPriceRules_CloseBtn;
	@FindBy(how = How.XPATH, using = "//button[text()='Filter']")
	public WebElement B2CPriceRules_FilterBtn;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[Type]_label")
	public WebElement B2CPriceRules_Types;
	@FindBy(how = How.XPATH, using = "//div[text()='Identifier']/../..//input")
	public WebElement Types_Identifier;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'_searchbutton')]")
	public WebElement Types_Search;
	@FindBy(how = How.XPATH, using = "//div[text()='PriceB2CRule']")
	public WebElement Types_ResultPriceB2CRule;
	@FindBy(how = How.XPATH, using = "//a[@title='Open Editor in new window']")
	public WebElement Types_OpenEditorNewWindow;
	@FindBy(how = How.XPATH, using = "//a[@title='Open Organizer']")
	public WebElement Types_OpenOrganizer;
	@FindBy(how = How.XPATH, using = "//table[@class='stringLayoutChip']//input")
	public WebElement Types_SearchValueInput;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'[status]:')]/../..//input")
	public WebElement Types_PriceB2CRuleStatus;
	@FindBy(how = How.XPATH, using = "//*[@class ='tb-button-blue chip-event']//div[contains(text(),'ave')]")
	public WebElement Types_SaveBtn;
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Confirm check text...']")
	public WebElement B2CPriceRules_deleteInput;
	@FindBy(how = How.XPATH, using = "//button[text()='Confirm']")
	public WebElement B2CPriceRules_deleteConfirm;

	// 15490
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Lenovo Price Approvers:')]/../..//table[contains(@id,'resultTable')]")
	public WebElement B2BUnit_priceApprovers;
	@FindBy(how = How.XPATH, using = ".//a[contains(.,'Dashboard')]")
	public WebElement PricingCockpit_dashboard;
	@FindBy(how = How.XPATH, using = ".//div[@class='links b2b-links']/a[contains(@href,'listRules')]")
	public WebElement PricingCockpit_b2bPriceRules;
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Rule type')]")
	public WebElement B2BPriceRules_RuleType;
	@FindBy(how = How.XPATH, using = ".//a[contains(@class,'createNewGroup')]/b")
	public WebElement B2BPriceRules_CreateNewGroup;
	@FindBy(how = How.XPATH, using = "//span[text()='Select Rule type...']")
	public WebElement B2BPriceRules_SelectRuleType;
	@FindBy(how = How.XPATH, using = ".//div[text()='Floor Prices']")
	public WebElement B2BPriceRules_FloorPriceOption;
	@FindBy(how = How.XPATH, using = ".//span[text()='Select Group Type...']")
	public WebElement B2BPriceRules_SelectGroupType;
	@FindBy(how = How.XPATH, using = "//button[text()='Continue']")
	public WebElement B2BPriceRules_Continue;
	@FindBy(how = How.NAME, using = "title")
	public WebElement B2BPriceRules_PriceRuleName;
	@FindBy(how = How.ID, using = "validFrom")
	public WebElement B2BPriceRules_ValidFrom;
	@FindBy(how = How.ID, using = "validTo")
	public WebElement B2BPriceRules_ValidTo;
	@FindBy(how = How.XPATH, using = ".//div[@class='modal-content']//span[text()='Select Country...']")
	public WebElement B2BPriceRules_CountrySelect;
	@FindBy(how = How.XPATH, using = ".//div[@class='modal-content']//span[text()='Select Material...']")
	public WebElement B2BPriceRules_MaterialSelect;
	@FindBy(how = How.XPATH, using = ".//div[@id='select2-drop']//input")
	public WebElement B2BPriceRules_SearchInput;
	@FindBy(how = How.XPATH, using = ".//div[@class='modal-content']//span[text()='Select Catalog Version...']")
	public WebElement B2BPriceRules_CatalogSelect;
	@FindBy(how = How.XPATH, using = ".//div[@class='modal-content']//span[text()='Select Unit...']")
	public WebElement B2BPriceRules_B2BunitSelect;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'ruleApprover')]")
	public WebElement B2BPriceRules_ApproverSelect;
	@FindBy(how = How.NAME, using = "operationValue")
	public WebElement B2BPriceRules_PriceValue;
	@FindBy(how = How.XPATH, using = ".//button[contains(.,'Add Price Rule to Group')]")
	public WebElement B2BPriceRules_AddPriceRuleToGroup;
	@FindBy(how = How.XPATH, using = ".//button[contains(.,'Create New Group')]")
	public WebElement B2BPriceRules_CreateGroup;
	@FindBy(how = How.XPATH, using = ".//*[@data-validation='You need to add at least one Rule to create Group!']")
	public WebElement B2BPriceRules_NoRuleError;
	@FindBy(how = How.XPATH, using = "//div[@class='modal-footer']//button[text()='Close']")
	public WebElement B2BPriceRules_CloseBtn;
	@FindBy(how = How.XPATH, using = "//button[text()='Filter']")
	public WebElement B2PriceRules_FilterBtn;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[Type]_label")
	public WebElement B2BPriceRules_Types;
	@FindBy(how = How.XPATH, using = "//div[text()='Identifier']/../..//input")
	public WebElement B2BPriceRules_deleteInput;
	@FindBy(how = How.XPATH, using = "//button[text()='Confirm']")
	public WebElement B2BPriceRules_deleteConfirm;
	@FindBy(how = How.XPATH, using = ".//button[@id='btnApprove']")
	public WebElement Dashoboard_approveBtn;
	@FindBy(how = How.XPATH, using = ".//a[contains(.,'Facet search')]")
	public WebElement Home_facetSearch;
	@FindBy(how = How.XPATH, using = ".//a[contains(.,'Indexer hot-update wizard')]")
	public WebElement Home_indexerHotUpdWiz;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'solrFacetSearchConfig')]")
	public WebElement IndexerHotUpdate_solrConfigName;
	@FindBy(how = How.XPATH, using = ".//option[contains(.,'mcnemob2bIndex')]")
	public WebElement IndexerHotUpdate_mcnemob2bIndex;
	@FindBy(how = How.XPATH, using = ".//div[text()='Next']")
	public WebElement IndexerHotUpdate_nextBtn;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'indexType')]")
	public WebElement IndexerHotUpdate_indexTyeDD;
	@FindBy(how = How.XPATH, using = ".//option[contains(.,'Product')]")
	public WebElement IndexerHotUpdate_productIndexType;
	@FindBy(how = How.XPATH, using = ".//span[contains(.,'Update Index')]")
	public WebElement IndexerHotUpdate_updateIndexRadioBtn;
	@FindBy(how = How.XPATH, using = ".//span[contains(.,'Delete from Index')]")
	public WebElement IndexerHotUpdate_DeleteFromIndexRadioBtn;
	@FindBy(how = How.XPATH, using = ".//td[contains(.,'Add Product')]")
	public WebElement IndexerHotUpdate_addProductOption;
	@FindBy(how = How.XPATH, using = ".//td[@class='disabled']/div")
	public WebElement IndexerHotUpdate_emptyRowToAddProduct;
	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'span')])[1]")
	public WebElement IndexerHotUpdate_articleNum;
	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'start')]")
	public WebElement IndexerHotUpdate_startJobBtn;
	@FindBy(how = How.XPATH, using = ".//td[@class='message']/div")
	public WebElement IndexerHotUpdate_indexSuccessMsgBox;
	@FindBy(how = How.XPATH, using = ".//div[contains(.,'Done')][@class='label']")
	public WebElement IndexerHotUpdate_doneBtn;
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'searchbutton')]")
	public WebElement IndexerHotUpdate_searchBtn;
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'use')]")
	public WebElement IndexerHotUpdate_useBtn;
	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'masterMultiCountryProductCatalog - Online')]")
	public WebElement IndexerHotUpdate_articleNoSearchedResult;
	@FindBy(how = How.XPATH, using = "(//select[contains(@id,'Product.catalogVersion')])[2]")
	public WebElement IndexerHotUpdate_catalogSelect;
	@FindBy(how = How.XPATH, using = "//option[contains(text(),'masterMultiCountryProductCatalog - Online')]")
	public WebElement IndexerHotUpdate_multiCountryOption;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'edit')]")
	public WebElement IndexerHotUpdate_searchResult;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Product.code')]")
	public WebElement IndexerHotUpdate_articleNumber;
	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'CronJobs')]")
	public WebElement Home_cronJob;
	@FindBy(how = How.XPATH, using = "//a/span[contains(.,'Search')]")
	public WebElement CronJob_searchButton;
	@FindBy(how = How.XPATH, using = "(//input[contains(@id,'CronJob.code')])[1]")
	public WebElement CronJob_jobName;
	@FindBy(how = How.XPATH, using = "//td/div[contains(@id,'action.performcronjob')]")
	public WebElement CronJob_startCronJob;
	@FindBy(how = How.XPATH, using = ".//*[@id='outerTD']//td/textarea")
	public WebElement CronJob_cronJobSuccessMsg;
	@FindBy(how = How.XPATH, using = "//a/span[contains(@id,'cronjob.common')]")
	public WebElement CronJob_taskTab;
	@FindBy(how = How.XPATH, using = "//table[@title='status']//td/select/option[@selected='']")
	public WebElement CronJob_status;
	@FindBy(how = How.XPATH, using = "//td/div[contains(@id,'reset.title')]")
	public WebElement CronJob_reloadButton;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'rediscacheclean')]")
	public WebElement Home_rediscacheclean;
	@FindBy(how = How.XPATH, using = "//input[@id='ps_product']")
	public WebElement Rediscacheclean_productCode;
	@FindBy(how = How.XPATH, using = "//input[@id='ps_button']")
	public WebElement Rediscacheclean_clean;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[Order.code]]_input")
	public WebElement OrderID;
	@FindBy(how = How.XPATH, using = ".//*[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/GenericCondition[Order.user]]']")
	public WebElement CustomerID;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/DateEditor[in Content/GenericCondition[Order.date]]_date']")
	public WebElement OrderDate;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'1 -')]")
	public WebElement NumberofOrders;
	@FindBy(how = How.ID, using = "Tree/GenericExplorerMenuTreeNode[order]_label")
	public WebElement Order;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[Order]_label")
	public WebElement Orders;
	@FindBy(how = How.ID, using = "Content/NemoSearchConfigurable[Order]_searchbutton")
	public WebElement OrderSearch;

	// 19782
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'group.nemo.payment') and contains(@class,'icon')]")
	public WebElement Home_payment;
	@FindBy(how = How.XPATH, using = ".//a[contains(@id,'PaymentLeasingSetting')]")
	public WebElement Home_paymentLeasingSetting;
	@FindBy(how = How.XPATH, using = ".//a[contains(@id,'Tree/GenericLeafNode[NemoZibbySetting]_label')]")
	public WebElement Home_zibbySetting;
	@FindBy(how = How.XPATH, using = "// select[contains(@id,'paymentLeasingType') and not(contains(@id,'operator'))]")
	public WebElement PaymentLeasing_leasingType;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'.country') and not(contains(@id,'operator'))]")
	public WebElement PaymentLeasing_country;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'searchbutton')]")
	public WebElement PaymentLeasing_searchbutton;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'No results')]")
	public WebElement PaymentLeasing_noResults;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'reate_PaymentLeasingSetting_label')]")
	public WebElement PaymentLeasing_create;
	@FindBy(how = How.XPATH, using = "(//*[contains(@id,'OrganizerListEntry')]//*[@title='Open Editor'])[1]")
	public WebElement PaymentLeasing_openEditor1;
	@FindBy(how = How.XPATH, using = "// *[contains(@id,'.laUrl')]")
	public WebElement PaymentLeasing_laUrl;
	@FindBy(how = How.XPATH, using = "// *[contains(@id,'.minimum')]")
	public WebElement PaymentLeasing_minimum;
	@FindBy(how = How.XPATH, using = "// *[contains(@id,'.maximum')]")
	public WebElement PaymentLeasing_maximum;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'save')]")
	public WebElement PaymentLeasing_saveAndCreate;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'.delete.title')]")
	public WebElement PaymentLeasing_delete;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'B2CLeasingPaymentSetting')]")
	public WebElement Home_paymentLeasingB2C;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'.billToNumber') and not(contains(@id,'operator'))]")
	public WebElement B2CLeasing_billToNumber;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'.NALeasing')]")
	public WebElement B2CLeasing_NALeasing;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_B2CLeasingPaymentSetting') and contains(@class,'name')]")
	public WebElement B2CLeasing_createB2CLeasing;
	@FindBy(how = How.XPATH, using = "//*[contains(@name,'_open_search')]")
	public WebElement B2CLeasing_leasingSetting;
	@FindBy(how = How.XPATH, using = "(//tr[contains(@id,'edit')])[1]")
	public WebElement B2CLeasing_searchedResult1;
	@FindBy(how = How.XPATH, using = "// *[contains(@id,'_use')]")
	public WebElement B2CLeasing_use;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NALeasing') and contains(@id,'false')]")
	public WebElement B2CLeasing_NALeasingNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NALeasing') and contains(@id,'true')]")
	public WebElement B2CLeasing_NALeasingYes;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'GenericResortableItemList')]")
	public WebElement B2CLeasing_billtoList;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'add_true')]")
	public WebElement B2CLeasing_add;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'billingAddress')]")
	public WebElement B2CLeasing_billingAddress;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'streetname') and not(contains(@id,'operator'))]")
	public WebElement B2CLeasing_streetname;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Payment Type:')]/../..//table[@class='genericItemListChip']/tbody")
	public WebElement B2CUnit_paymentTypeTable;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'CheckoutPaymentType.code') and not(contains(@id,'operator'))]")
	public WebElement B2CUnit_paymentTypeInput;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'B2C Leasing Settings:')]/../..//table[@class='genericItemListChip']/tbody")
	public WebElement B2CUnit_B2CLeasingTable;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'select_visible_true_labe')]")
	public WebElement B2CLeasing_selectAll;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'remove_true_label')]")
	public WebElement B2CLeasing_remove;

	// 20315
	@FindBy(how = How.XPATH, using = "//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.enableKlarnaPayment]]_true']")
	public WebElement B2CUnit_DisplayEnableKlarnaPayment;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.enableKlarnaPayment]]_null']")
	public WebElement B2CUnit_NotDisplayEnableKlarnaPayment;

	// added by qianqian
	@FindBy(how = How.XPATH, using = "//input[@value='true'][contains(@id,'enableQuoteApproval')]")
	public WebElement B2BUnit_enableQuoteApprove;
	@FindBy(how = How.XPATH, using = "//input[@value='true'][contains(@id,'enableQuoteConvert')]")
	public WebElement B2BUnit_enableQuoteConvert;
	@FindBy(how = How.XPATH, using = "//input[@value='true'][contains(@id,'enableResellerId')]")
	public WebElement B2BUnit_enableQuoteResId;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'isQuoteAvailable')][@value='true']")
	public WebElement B2BUnit_isQuoteAvailable;
	@FindBy(how = How.XPATH, using = "//table[@class='textAreaLayoutChip']//textarea[contains(@id,'xmlContentShow')]")
	public WebElement Orders_OrderXML;

	// 21297
	@FindBy(how = How.XPATH, using = "//*[@id='Tree/GenericExplorerMenuTreeNode[group.nemo.payment.type.customize]_label']")
	public WebElement Home_paymentCustomize;
	@FindBy(how = How.XPATH, using = "//*[@id='Tree/GenericLeafNode[PaymentTypeProfile]_label']")
	public WebElement Home_paymentProfile;
	@FindBy(how = How.XPATH, using = "//*[@id='Tree/GenericLeafNode[PaymentTypeProfile]_!create_PaymentTypeProfile_label']")
	public WebElement Home_paymentProfileCreate;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'name')]")
	public WebElement paymentProfile_name;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'code')]")
	public WebElement paymentProfile_code;
	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'checkoutPaymentType')]")
	public WebElement paymentProfile_PaymentType;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'checkoutPaymentType')]/option[contains(text(),'Direct Deposit')]")
	public WebElement paymentProfile_PaymentTypeDd;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'checkoutPaymentType')]/option[contains(text(),'Card Payment')]")
	public WebElement paymentProfile_PaymentTypeCard;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'checkoutPaymentType')]/option[contains(text(),'Cheque Payment')]")
	public WebElement paymentProfile_PaymentTypeCheque;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'configLevel')]")
	public WebElement paymentProfile_configLevel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'configLevel')]/option[contains(text(),'SPECIFIC')]")
	public WebElement paymentProfile_configLevelS;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'configLevel')]/option[contains(text(),'GLOBAL')]")
	public WebElement paymentProfile_configLevelGlobal;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]")
	public WebElement paymentProfile_channel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]/option[contains(text(),'B2B')]")
	public WebElement paymentProfile_channelB2B;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'active') and @value='true']")
	public WebElement paymentProfile_activeTrue;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'priority')]")
	public WebElement paymentProfile_priority;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableHelpLink') and @value='false']")
	public WebElement paymentProfile_disableHelpLinkNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'helpLinkLabel')]")
	public WebElement paymentProfile_helpLinkLabel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'helpLinkType')]")
	public WebElement paymentProfile_helpLinkType;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'helpLinkType')]/option[contains(text(),'NEWPAGE')]")
	public WebElement paymentProfile_helpLinkTypeNew;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'pageLink')]")
	public WebElement paymentProfile_pageLink;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disablePaymentImage') and @value='false']")
	public WebElement paymentProfile_disablePaymentImageNo;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'paymentImgage')]")
	public WebElement paymentProfile_paymentImgage;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement paymentProfile_paymentImgageSearch;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Media.mime')]")
	public WebElement paymentProfile_paymentImgageMedia;
	@FindBy(how = How.XPATH, using = "//table[contains(@class,'selecttable')]")
	public WebElement paymentProfile_paymentImgageResult;
	@FindBy(how = How.XPATH, using = "(//table[contains(@class,'selecttable')]/tbody/tr[contains(@id,'edit')])[1]")
	public WebElement paymentProfile_paymentImgageResultFirst;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'saveandcreate')]")
	public WebElement paymentProfile_paymentCreate;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringDisplay[Deposit ANZ ASM]_span']")
	public WebElement paymentProfile_SearchResultANZASM;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'b2c.b2b')]")
	public WebElement paymentProfile_paymentB2BB2C;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'displayName')]")
	public WebElement paymentProfile_paymentB2BB2CDisplayName;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'global')]")
	public WebElement paymentProfile_global;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'visibilityForASM')][@value='true']")
	public WebElement paymentProfile_ASMVisiblity;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'visibilityForASM')][@value='false']")
	public WebElement paymentProfile_ASMVisiblityNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableMixedOrder') and @value='true']")
	public WebElement paymentProfile_globalMixedOrderYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableDcgOrder') and @value='true']")
	public WebElement paymentProfile_globalMixedDcgYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableDcgOrder') and @value='false']")
	public WebElement paymentProfile_globalMixedPcgNo;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'countries')]")
	public WebElement paymentProfile_countries;
	@FindBy(how = How.XPATH, using = "//table[contains(@oncontextmenu,'Country')]//tbody[contains(@id,'Content')]")
	public WebElement paymentProfile_countriesAddCountryTable;
	@FindBy(how = How.XPATH, using = "//td[text()='Add Country']")
	public WebElement paymentProfile_countriesAddCountry;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'isocode')]")
	public WebElement paymentProfile_countriesIsocode;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'attributeselect')]")
	public WebElement paymentProfile_attributeselect;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'attributeselect')]/option[@value='name']")
	public WebElement paymentProfile_attributeselectName;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'PaymentTypeProfile.name')]")
	public WebElement paymentProfile_nameInput;
	@FindBy(how = How.XPATH, using = "//select[contains(@oncontextmenu,'checkoutPaymentType')]")
	public WebElement paymentProfile_paymentType;
	@FindBy(how = How.XPATH, using = "//select[contains(@oncontextmenu,'checkoutPaymentType')]/option[contains(text(),'Direct Deposit')]")
	public WebElement paymentProfile_paymentTypeDD;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement paymentProfile_paymentSearch;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'Direct Deposit')]")
	public WebElement paymentProfile_ddResult;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'remove_true')]")
	public WebElement paymentProfile_remove;
	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'checkoutPaymentType')]/option[contains(.,'Cheque Payment')]")
	public WebElement paymentProfile_ChequePayment;
	// 21299
	@FindBy(how = How.XPATH, using = "//table[contains(@id,'B2BUnit.paymentTypeAndPayerId')]")
	public WebElement B2BUnit_paymentTypeTable;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'.paymentTypeAndPayerId') and contains(@id,'PURCHASE')]")
	public WebElement B2BUnit_purchaseOrderPayment;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disablePaymentProfileFallback') and contains(@id,'false')]")
	public WebElement B2BUnit_disablePaymentProfileFallbackNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disablePaymentProfileFallback') and contains(@id,'true')]")
	public WebElement B2BUnit_disablePaymentProfileFallbackYes;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'add_true')]")
	public WebElement B2BUnit_add;
	@FindBy(how = How.XPATH, using = "//div[text()='Identifier']/../..//input")
	public WebElement B2BUnit_identifier;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'resultlist')]//tr[contains(@id,'edit')]")
	public WebElement B2BUnit_searchedPayment;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'_use')]")
	public WebElement B2BUnit_use;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'checkoutPaymentType')]/option[contains(text(),'Purchase')]")
	public WebElement paymentProfile_PaymentTypePo;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]/option[contains(text(),'ALL')]")
	public WebElement paymentProfile_channelALL;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'.b2bunits')]")
	public WebElement paymentTypeProfile_b2bunits;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'resultlist')]/table")
	public WebElement paymentTypeProfile_unitsTable;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'add_true')]")
	public WebElement paymentTypeProfile_add;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Unit.uid')]")
	public WebElement paymentTypeProfile_unitId;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'searchbutton')]")
	public WebElement paymentTypeProfile_search;
	@FindBy(how = How.XPATH, using = "(//*[contains(@id,'edit')])[1]")
	public WebElement paymentTypeProfile_firstSearchResult;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'_use')]")
	public WebElement paymentTypeProfile_use;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'Purchase Order')]")
	public WebElement paymentProfile_poResult;
	@FindBy(how = How.XPATH, using = "//select[contains(@oncontextmenu,'checkoutPaymentType')]/option[contains(text(),'urchase')]")
	public WebElement paymentProfile_paymentTypePo;

	// 20654
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.brandLeapToggle]]_true']")
	public WebElement B2CUnit_BrandleapToggleYes;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.brandLeapToggle]]_null']")
	public WebElement B2CUnit_BrandleapToggleNA;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/Attribute[B2CUnit.customizedButtonColor]]_input']")
	public WebElement B2CUnit_CustomizedButtonColor;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/LocalizableAttribute[B2CUnit.customizedButtonLabel]]_input']")
	public WebElement B2CUnit_CustomizedButtonLabel;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'PMI Attribute Override')]")
	public WebElement Catalog_PMIAttributeOverride;
	@FindBy(how = How.XPATH, using = "//td[@class='label']/div[contains(.,'Country PMI Attribute Override:')]/../..//td[last()]//tr[2]")
	public WebElement Catalog_CountryrPMIAttributeOverride;
	@FindBy(how = How.XPATH, using = "//td[@class='label']/div[contains(.,'Country PMI Attribute Override:')]/../..//td[last()]//tr//td[5]")
	public WebElement Catalog_AllCountriesOfCountryrPMIAttributeOverride;
	@FindBy(how = How.XPATH, using = "//div[@class='dropdown-main']//tr[3]")
	public WebElement Catalog_CreatePMIAttributeOverride;
	@FindBy(how = How.XPATH, using = "//div[@class='dropdown-main']//tr[4]")
	public WebElement Catalog_RemovePMIAttributeOverride;
	@FindBy(how = How.XPATH, using = "//select[@id='AttributeOverrideSelectEditor[in Attribute[.descriptorAttribute]]_select']")
	public WebElement Catalog_PMIAttribute;
	@FindBy(how = How.XPATH, using = "//input[@id='StringEditor[in LocalizableAttribute[.stringValue]]_input']")
	public WebElement Catalog_PMIValue;
	@FindBy(how = How.XPATH, using = "//select[@id='AllInstancesSelectEditor[in Attribute[.country]]_select']")
	public WebElement Catalog_PMICountry;
	@FindBy(how = How.XPATH, using = "//input[@id='BooleanEditor[in Attribute[.active]]_true']")
	public WebElement Catalog_PMIActive;
	@FindBy(how = How.XPATH, using = "//img[@id='DateTimeEditor[in Attribute[.startDate]]_img']")
	public WebElement Catalog_PMIStartDateButton;
	@FindBy(how = How.XPATH, using = "//img[@id='DateTimeEditor[in Attribute[.endDate]]_img']")
	public WebElement Catalog_PMIEndDateButton;
	@FindBy(how = How.XPATH, using = "//table[@class='listtable']/tbody/tr[2]/td[1]/a")
	public WebElement Catalog_PMIStartDate;
	@FindBy(how = How.XPATH, using = "//table[@class='listtable']/tbody/tr[last()]/preceding-sibling::tr[1]/td[last()]/a")
	public WebElement Catalog_PMIEndDate;
	@FindBy(how = How.XPATH, using = "//img[@id='ImageToolbarAction[organizer.editor.save.title]_img']")
	public WebElement Catalog_PMISaveButton;
	@FindBy(how = How.XPATH, using = "//img[@id='Content/ImageToolbarAction[organizer.editor.save.title][1]_img']")
	public WebElement Catalog_SaveButton;

	// 21257
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'b2cunits')]")
	public WebElement paymentProfile_B2CUnits;
	@FindBy(how = How.XPATH, using = "//table[contains(@oncontextmenu,'Add B2C Unit')]")
	public WebElement paymentProfile_B2CUnitsAddUnit;
	@FindBy(how = How.XPATH, using = "//td[text()='Add B2C Unit']")
	public WebElement paymentProfile_B2CUnitsCreateUnit;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.uid')]")
	public WebElement paymentProfile_B2CUnitsIsocode;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]/option[contains(text(),'B2C')]")
	public WebElement paymentProfile_channelB2C;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disablePaymentProfileFallback') and contains(@id,'false')]")
	public WebElement B2CUnit_disablePaymentProfileFallbackNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disablePaymentProfileFallback') and contains(@id,'true')]")
	public WebElement B2CUnit_disablePaymentProfileFallbackYes;

	//ACCT584
	@FindBy(how = How.XPATH, using = "//input[@id='Content/IntegerEditor[in Content/Attribute[B2CUnit.zSesssionTimerAlertMins]]_input']")
	public WebElement B2CUnit_SessionTimerAlert_TextField;

	@FindBy(how = How.XPATH, using = "//input[@id=\"Content/StringEditor[in Content/NemoLocalizableAttribute[B2CUnit.ZSesssionTimerMeassage]]_input\"]")
	public WebElement B2CUnit_SessionTimerMsg_TextField;


	// 15492
	@FindBy(how = How.XPATH, using = "//div[@class='links b2c-links']/a[contains(@href,'priceSimulate')]")
	public WebElement PricingCockpit_B2CPriceSimulator;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'country')]")
	public WebElement B2CPriceSimulator_country;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'store')]")
	public WebElement B2CPriceSimulator_store;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'catalog')]")
	public WebElement B2CPriceSimulator_catalogVersion;
	@FindBy(how = How.XPATH, using = ".//*[@id='ps_date']")
	public WebElement B2CPriceSimulator_priceDate;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'product')]")
	public WebElement B2CPriceSimulator_product;
	@FindBy(how = How.XPATH, using = ".//*[@id='ps_button']")
	public WebElement B2CPriceSimulator_debug;
	@FindBy(how = How.XPATH, using = "//td[@class='floor']//*[@id='container']//i")
	public WebElement B2CPriceSimulator_floorGroup;
	@FindBy(how = How.XPATH, using = "//td[@class='web']//*[@id='container']//i")
	public WebElement B2CPriceSimulator_webGroup;
	@FindBy(how = How.XPATH, using = "//div[@data-name='priceType']/div")
	public WebElement B2CPriceRules_ruleType;
	@FindBy(how = How.XPATH, using = "//div[@data-name='group']/div")
	public WebElement B2CPriceRules_group;
	@FindBy(how = How.XPATH, using = "//*[@class='btn btn-warning clearAll']")
	public WebElement B2CPriceRules_clearAll;
	@FindBy(how = How.XPATH, using = "//*[@id='ps_results']")
	public WebElement B2CPriceSimulator_simulatorResults;

	// 21184
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'Content/EditorTab[NemoMTMVariantProduct.administration]')]")
	public WebElement Catalog_Administration;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableQuickOrder') and @value ='true']")
	public WebElement Catalog_enableQuickOrderTrue;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableQuickOrder') and @value ='false']")
	public WebElement Catalog_enableQuickOrderFalse;

	// 21226
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/Attribute[B2BUnit.expireMessage]]_input']")
	public WebElement B2BUnit_expireMessage;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/IntegerEditor[in Content/Attribute[B2BUnit.expireDays]]_input']")
	public WebElement B2BUnit_expireDays;
	@FindBy(how = How.XPATH, using = "//input[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/ExtendedGenericCondition[Contract.b2bUnit]]']")
	public WebElement Contract_partnerId;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[NemoAgreement]_label']")
	public WebElement Home_Agreement;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NemoAgreement.productCode')]")
	public WebElement Agreement_productCode;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NemoAgreement.b2bUnit')]")
	public WebElement Agreement_partnerId;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[NemoAgreementEntry.endDate]]_date']")
	public WebElement Agreement_endDate;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[ContractEntry.endDate]]_date']")
	public WebElement Contrac_endDate;
	@FindBy(how = How.XPATH, using = "//a[@id='Content/OrganizerComponent[organizersearch][Contract]_togglelabel']")
	public WebElement Contrac_searchToggleLabel;
	@FindBy(how = How.XPATH, using = "//a[@id='Content/OrganizerComponent[organizersearch][NemoAgreement]_togglelabel']")
	public WebElement Agreement_searchToggleLabel;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicShowLanguage') and @value ='true']")
	public WebElement B2CUnit_ShowLanguage;

	// product file processing
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericExplorerMenuTreeNode[system]_treeicon']")
	public WebElement System_Menu;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[CronJob]_label']")
	public WebElement System_CronJobs;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[CronJob.code]]_input']")
	public WebElement CronJobs_JobName;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/GenericCondition[CronJob.code][operator]_select']/option[contains(text(),'contains')]")
	public WebElement CronJobs_OptionContainsforJobs;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/GenericCondition[Media.code][operator]_select']/option[contains(text(),'contains')]")
	public WebElement MultiMedia_OptionContainsforMedia;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[CronJob]_searchbutton']")
	public WebElement CronJobs_Search;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[NemoProductGenerationCronJob.tab.cronjob.processas]_span']")
	public WebElement CronJobs_RunAS;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/IntegerEditor[in Content/Attribute[NemoProductGenerationCronJob.nodeID]]_input']")
	public WebElement CronJobs_ServerNode;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[NemoProductGenerationCronJob.administration]_span']")
	public WebElement CronJobs_Administration;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[NemoProductGenerationCronJob.isSendftp]]_false']")
	public WebElement CronJobs_IsSendftpfalse;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/Attribute[NemoProductGenerationCronJob.unituid]]_input']")
	public WebElement CronJobs_unituid;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/GenericItemChip$1[action.performcronjob]_label']")
	public WebElement CronJobs_StartConronJobs;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericExplorerMenuTreeNode[medias]_treeicon']")
	public WebElement MultiMedia_Menu;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[Media]_label']")
	public WebElement MultiMedia_MediaOption;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/OrganizerItemChip$1[organizer.editor.reset.title]_label']")
	public WebElement CronJobs_Reload;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[NemoProductGenerationCronJob.status]]_select']//option[@selected='']")
	public WebElement CronJobs_CurrentStatus;

	@FindBy(how = How.XPATH, using = ".//*[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[Media.folder]]_select']/option[contains(text(),'productGeneration')]")
	public WebElement Media_Folder;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[Media]_searchbutton']")
	public WebElement Media_Search;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/MediaFileUploadEditor_download_div']")
	public WebElement Media_Download;
	@FindBy(how = How.XPATH, using = "//pre")
	public WebElement Media_Download_Content;
	@FindBy(how = How.XPATH, using = ".//*[@id='MC87x447_next_page_img' and@src='images/icons/footer_next.gif']")
	public WebElement Media_NextPage;

	@FindBy(how = How.XPATH, using = "//a[contains(@id,'multicountry')]")
	public WebElement Catalog_multiCountry;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'.save.title')]")
	public WebElement Catalog_save;

	// 22370
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'[B2BUnit.emailDomainValidatoinToggle]]_true')]")
	public WebElement B2BUnit_siteAttribute_emailDomainValidatoinToggleYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'[B2BUnit.emailDomainValidatoinToggle]]_false')]")
	public WebElement B2BUnit_siteAttribute_emailDomainValidationToggleNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'GenericEditableListEntry')]")
	public List<WebElement> B2BUnit_siteAttribute_emailDomainValidationList;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/GenericResortableList_!remove_true_label']")
	public List<WebElement> B2BUnit_siteAttribute_emailDomainValidationListRemove;
	@FindBy(how = How.XPATH, using = "//div[@id='resultlist_Content/GenericResortableList']//div[contains(.,'The list is empty')]")
	public WebElement B2BUnit_siteAttribute_emailDomainValidationEmptyList;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/GenericResortableList_!add_true_label']")
	public WebElement B2BUnit_siteAttribute_emailDomainValidationListCreate;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Content/StringEditor[in Content/GenericResortableList]')]")
	public WebElement B2BUnit_siteAttribute_emailDomainValidationListInput;
	@FindBy(how = How.XPATH, using = "//div[@id='resultlist_Content/GenericResortableList']//input[@class='header'][@type='checkbox']")
	public WebElement B2BUnit_siteAttribute_emailDomainValidationListCheckbox;

	// added by 13181
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[B2BCustomer]_!create_B2BCustomer_label")
	public WebElement Home_CreateB2BCustomer;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[.uid]')]")
	public WebElement B2BCustomer_IDInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[.name]')]")
	public WebElement B2BCustomer_NameInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[.email]')]")
	public WebElement B2BCustomer_EmailInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[B2BCustomer.uid]')]")
	public WebElement B2BCustomer_IDEdit;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[B2BCustomer.name]')]")
	public WebElement B2BCustomer_NameEdit;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[B2BCustomer.email]')]")
	public WebElement B2BCustomer_EmailEdit;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[.accessLevel]')]")
	public WebElement B2BCustomer_AccessLevelInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[.defaultB2BUnit]')]")
	public WebElement B2BCustomer_DefaultUnitInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[.defaultDMU]')]")
	public WebElement B2BCustomer_DefaultDMUInput;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'saveandcreate')]")
	public WebElement B2BCustomer_CreateButton;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[B2BCustomer.accessLevel]')]")
	public WebElement B2BCustomer_AccessLevelEdit;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[B2BCustomer.defaultB2BUnit]')]")
	public WebElement B2BCustomer_DefaultUnitEdit;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[B2BCustomer.defaultDMU]')]")
	public WebElement B2BCustomer_DefaultDMUEdit;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[.b2BUserStatus]')]/option[contains(text(),'ACTIVE')]")
	public WebElement B2BCustomer_ActiveStatus;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Groups:')]/../..//tbody/tr[last()]/td[3]//div//input")
	public WebElement B2BCustomer_GroupsInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Access Level')]")
	public WebElement B2BCustomer_AccessLevelTab;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Addresses')]")
	public WebElement B2BCustomer_AddressesTab;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Punch Out')]")
	public WebElement Home_Nemo_Punchout;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[B2BCustomerPunchOutCredentialMapping]_label")
	public WebElement Home_Punchout_Credential;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[PunchOutCustomerProfile]_label")
	public WebElement Home_Punchout_CustomerProfile;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/ExtendedGenericCondition[PunchOutCustomerProfile.profileName')]")
	public WebElement PunchoutProfile_NameSearch;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/ExtendedGenericCondition[PunchOutCustomerProfile.profileName][operator]_select']/option[contains(text(),'is equal')]")
	public WebElement PunchoutProfile_NameEqual;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer')]")
	public WebElement PunchoutCredential_CustomerSearch;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'PunchOut Customer Profile')]")
	public WebElement CustomerProfile_SearchButton;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[B2BCustomerPunchOutCredentialMapping]_!create_B2BCustomerPunchOutCredentialMapping_label")
	public WebElement Home_CreatePunchoutCredential;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer')]")
	public WebElement PunchoutCredential_CustomerInput;
	@FindBy(how = How.XPATH, using = "(.//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//div[contains(text(),'The list is empty')])[1]")
	public WebElement PunchoutCredential_AribaCredential;
	@FindBy(how = How.XPATH, using = ".//*[text()='Add PunchOut Credential']")
	public WebElement PunchoutCredential_AddAribaCredential;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'StringEditor[in GenericCondition[PunchOutCredential.code]')]")
	public WebElement PunchoutAribaCredential_CodeSearchInput;
	@FindBy(how = How.XPATH, using = ".//*[text()='Create PunchOut Credential']")
	public WebElement PunchoutCredential_CreateAribaCredential;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.code]]_input")
	public WebElement PunchoutAribaCredential_CodeInput;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.domain]]_input")
	public WebElement PunchoutAribaCredential_DomainInput;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.identity]]_input")
	public WebElement PunchoutAribaCredential_IdentityInput;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.sharedsecret]]_input")
	public WebElement PunchoutAribaCredential_SharedSecretInput;
	@FindBy(how = How.XPATH, using = ".//*[text()='Create']")
	public WebElement PunchoutCredential_CreateButton;
	@FindBy(how = How.XPATH, using = ".//*[text()='Remove']")
	public WebElement PunchoutCredential_RemoveAribaCredential;
	@FindBy(how = How.XPATH, using = ".//*[text()='Select all']")
	public WebElement PunchoutCredential_SelectAllAribaCredential;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[PunchOutCustomerProfile]_!create_PunchOutCustomerProfile_label")
	public WebElement Home_CreatePunchoutProfile;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/Attribute[.profileName]]_input")
	public WebElement PunchoutProfile_NameInput;
	@FindBy(how = How.XPATH, using = "Content/BooleanEditor[in Content/Attribute[.isActivate]]_checkbox")
	public WebElement PunchoutProfile_Active;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'B2B Customers:')]/../../td[last()]//div[text()='The list is empty.']")
	public WebElement PunchoutProfile_CustomerField;
	@FindBy(how = How.ID, using = "ajaxinput_AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]")
	public WebElement PunchoutProfile_CustomerInput;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[.cxmlActive]]_checkbox")
	public WebElement PunchoutProfile_ActiveOxml;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[.isCustomCart]]_checkbox")
	public WebElement PunchoutProfile_ActiveCustomCart;

	// 18406
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Base Commerce')]")
	public WebElement Home_baseCommerce;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[BaseStore]_label']")
	public WebElement Home_baseStore;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'BaseStore.uid')]")
	public WebElement baseStore_id;
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'_searchbutton')]")
	public WebElement baseStore_search;
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'administration')]")
	public WebElement baseStore_administration;
	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'organizer.editor.save.title')]")
	public WebElement baseStore_save;
	@FindBy(how = How.XPATH, using = "(.//input[contains(@id,'BaseStore.isQuoteAvailable')])[1]")
	public WebElement baseStore_isQuoteAvailable;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2CUnit.enableGuestQuote')][@value='true']")
	public WebElement B2CUnit_enableGuestQuoteYes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2CUnit.enableGuestQuote')][@value='false']")
	public WebElement B2CUnit_enableGuestQuoteNo;

	// 27832
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/Attribute[BaseStore.gtcEmailList]]_input")
	public WebElement baseStore_gtcEmailList;

	// DR-18
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'isDigitalRiverCountry]]_true')]")
	public WebElement B2CUnit_siteAttribute_isDigitalRiverCountry;
	@FindBy(how = How.XPATH, using = "//*[@id='Tree/GenericExplorerMenuTreeNode[group.nemo.digitalRiver]_label']")
	public WebElement NEMO_digitalRiver;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'DigitalRiverTraceLog') and contains(@id,'Tree')]")
	public WebElement NEMO_digitalRiver_tracelog;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'customerID')]")
	public WebElement NEMO_digitalRiver_tracelogID;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'DigitalRiverTraceLog.country')]")
	public WebElement NEMO_digitalRiver_tracelogCountry;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'DigitalRiverTraceLog.country')]/option[contains(text(),'United Kingdom')]")
	public WebElement NEMO_digitalRiver_tracelogCountry_GB;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'DigitalRiverTraceLog.country')]/option[contains(text(),'France')]")
	public WebElement NEMO_digitalRiver_tracelogCountry_FR;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'DigitalRiverTraceLog.country')]/option[contains(text(),'Ireland')]")
	public WebElement NEMO_digitalRiver_tracelogCountry_IE;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement NEMO_digitalRiver_search;
	@FindBy(how = How.XPATH, using = "(//table[contains(@id,'innertable')]//tr[contains(@id,'search')])[1]/td[6]/div/div")
	public WebElement NEMO_digitalRiver_search_firstResultItem;
	@FindBy(how = How.XPATH, using = "//textarea[contains(@id,'cartXml]')]")
	public WebElement NEMO_digitalRiver_search_xml;

	@FindBy(how = How.XPATH, using = "//a[contains(@id,'creationtime_sort')]")
	public WebElement NEMO_digitalRiver_creationtime_sort;

	@FindBy(how = How.XPATH, using = "//a[contains(@id,'marketing') and contains(@id,'Tree')]")
	public WebElement marketing;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'orderstatistics') and contains(@id,'Tree')]")
	public WebElement marketing_orderStatistics;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'Tree/GenericLeafNode[Cart]_label')]")
	public WebElement marketing_orderStatistics_carts;
	@FindBy(how = How.XPATH, using = "//td/input[contains(@id,'Cart.code')]")
	public WebElement marketing_os_carts_orderNumTxtBox;
	@FindBy(how = How.XPATH, using = "//a/span[contains(@id,'searchbutton')]")
	public WebElement marketing_os_carts_search;
	@FindBy(how = How.XPATH, using = "//a/span[contains(@id,'Cart.administration')]")
	public WebElement marketing_os_carts_administration;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'isDigitalRiverCart]]_true')]")
	public WebElement marketing_os_carts_isDigitalRiverCart;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Cart.dRCartHasPlacedOrder]]_true')]")
	public WebElement marketing_os_carts_dRCartHasPlacedOrder;

	// 22046
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[B2CUnit.administration]_span']")
	public WebElement B2CUnit_administration;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.InvoiceToggle]]_false']")
	public WebElement B2CUnit_administration_invoiceToggle_false;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.InvoiceToggle]]_true']")
	public WebElement B2CUnit_administration_invoiceToggle_true;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'Content/EditorTab[Order.administration]')]")
	public WebElement Order_ordersAdministration;
	@FindBy(how = How.XPATH, using = "//textarea[@id='Content/TextAreaEditor[in Content/Attribute[Order.xmlContentShow]]_textarea']")
	public WebElement Order_textArea;

	// 21817
	@FindBy(how = How.XPATH, using = "(//table[contains(@id,'table_Content/GenericItemList')]//tr[2])[2]")
	public WebElement PunchoutCredential_OCICredential;
	@FindBy(how = How.XPATH, using = "//td[text()='Create OCI PunchOut Credential']")
	public WebElement PunchoutCredential_CreatOCICredential;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'StringEditor[in Attribute[.code]')]")
	public WebElement PunchoutOCIOracleCredential_CodeInput;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'StringEditor[in Attribute[.username]')]")
	public WebElement PunchoutOCICredential_UserNameInput;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'StringEditor[in Attribute[.password]')]")
	public WebElement PunchoutOCIOracleCredential_PasswordInput;
	@FindBy(how = How.XPATH, using = "(//table[contains(@id,'table_Content/GenericItemList')]//tr[2])[3]")
	public WebElement PunchoutCredential_OracleCredential;
	@FindBy(how = How.XPATH, using = "//td[text()='Create Oracle PunchOut Credential']")
	public WebElement PunchoutCredential_CreatOracleCredential;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'StringEditor[in Attribute[.name]')]")
	public WebElement PunchoutOracleCredential_NameInput;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.isActivate]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActive;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.cxmlActive]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActiveCxml;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[.aribaContentType]]_select']")
	public WebElement PunchoutProfile_ContentTypeSelectCxml;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[PunchOutCustomerProfile.aribaContentType]')]")
	public WebElement PunchoutProfile_ContentTypeSelectCxmlEdit;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[PunchOutCustomerProfile.tab.punchoutcustomerprofile.oci]_span']")
	public WebElement PunchoutProfile_OCITab;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.ociActive]]_checkbox']")
	public WebElement PunchoutProfile_ActiveOCI;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.ociActive]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActiveOCI;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[.ociContentType]]_select']")
	public WebElement PunchoutProfile_ContentTypeSelectOCI;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[PunchOutCustomerProfile.ociContentType]')]")
	public WebElement PunchoutProfile_ContentTypeSelectOCIEdit;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[PunchOutCustomerProfile.tab.punchoutcustomerprofile.oracle]_span']")
	public WebElement PunchoutProfile_OracleTab;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.oracleActive]]_checkbox']")
	public WebElement PunchoutProfile_ActiveOracle;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.oracleActive]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActiveOracle;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[.oracleContentType]]_select']")
	public WebElement PunchoutProfile_ContentTypeSelectOracle;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[PunchOutCustomerProfile.oracleContentType]')]")
	public WebElement PunchoutProfile_ContentTypeSelectOracleEdit;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[PunchOutTraceLog]_label")
	public WebElement Home_Punchout_Tracelog;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Content/StringEditor[in Content/GenericCondition[PunchOutTraceLog.userId]')]")
	public WebElement Home_Punchout_Tracelog_UserID;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/NemoSearchConfigurable[PunchOutTraceLog]_searchbutton']")
	public WebElement Home_Punchout_Tracelog_Search;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/GenericCondition[PunchOutTraceLog.startDate][operator]_select']")
	public WebElement Home_Punchout_Tracelog_StartDateSelect;
	@FindBy(how = How.XPATH, using = "//img[@id='Content/DateTimeEditor[in Content/GenericCondition[PunchOutTraceLog.startDate]]_img']")
	public WebElement Home_Punchout_Tracelog_StartDateImg;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/DateTimeEditor[in Content/GenericCondition[PunchOutTraceLog.startDate]]_time']")
	public WebElement Home_Punchout_Tracelog_StartDateTime;
	@FindBy(how = How.XPATH, using = "//*[@id='outerTD']//table[@id='Content/McSearchListConfigurable[PunchOutTraceLog]_innertable']/tbody/tr[3]/td[11]/div/div")
	public WebElement Home_Punchout_Tracelog_DateTime;
	@FindBy(how = How.XPATH, using = "//*[@id='outerTD']//table[@id='Content/McSearchListConfigurable[PunchOutTraceLog]_innertable']/tbody/tr")
	public List<WebElement> Home_Punchout_Tracelog_ResultNum;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/ExtendedGenericCondition[PunchOutCustomerProfile.profileName]]_input']")
	public WebElement PunchoutProfile_ProfileName;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/NemoSearchConfigurable[PunchOutCustomerProfile]_searchbutton']")
	public WebElement PunchoutProfile_ProfileSearch;
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[B2BCustomerPunchOutCredentialMapping]_innertable']/tbody/tr[3]/td[4]")
	public WebElement PunchoutCredential_1stSearchedResult;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/ExtendedGenericCondition[PunchOutCustomerProfile.profileName][operator]_select']")
	public WebElement PunchoutProfile_SelectProfileNameScope;
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[PunchOutCustomerProfile]_innertable']/tbody/tr[3]/td[4]")
	public WebElement PunchoutProfile_1stSearchedResult;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/McSearchListConfigurable[PunchOutCustomerProfile]_!remove_true_label']")
	public WebElement PunchoutProfile_removeResult;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/NemoSearchConfigurable[B2BCustomerPunchOutCredentialMapping]_searchbutton']")
	public WebElement PunchoutCredential_SearchButton;
	@FindBy(how = How.XPATH, using = ".//*[text()='Add OCI PunchOut Credential']")
	public WebElement PunchoutCredential_AddOCICredential;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'StringEditor[in GenericCondition[OciPunchOutCredential.code]')]")
	public WebElement PunchoutOCICredential_CodeSearchInput;
	@FindBy(how = How.XPATH, using = ".//*[text()='Add Oracle PunchOut Credential']")
	public WebElement PunchoutCredential_AddOracleCredential;
	@FindBy(how = How.XPATH, using = "//input[@id='StringEditor[in GenericCondition[OraclePunchOutCredential.code]]_input']")
	public WebElement PunchoutOracleCredential_CodeSearchInput;

	// 13426
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.isCustomCart]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActiveCustomCartCxml;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.isCustomCart]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActiveCustomCartCxmlEdit;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.isCustomCart]]_checkbox']")
	public WebElement PunchoutProfile_CustomCartCxml;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.isCustomCart]]_checkbox']")
	public WebElement PunchoutProfile_CustomCartCxmlEdit;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.isOracleCustomCart]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActivCustomCartOracle;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.isOracleCustomCart]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_WhetherActivCustomCartOracleEdit;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.isOracleCustomCart]]_checkbox']")
	public WebElement PunchoutProfile_CustomCartOracle;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.isOracleCustomCart]]_checkbox']")
	public WebElement PunchoutProfile_CustomCartOracleEdit;
	@FindBy(how = How.XPATH, using = "//div[text()='Target']")
	public WebElement PunchoutProfile_cXMLOutboundRule1;
	@FindBy(how = How.XPATH, using = "//td[text()='Create Standard Field']")
	public WebElement PunchoutProfile_cXMLCreateOutboundRule1;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/CreateItemListEntry[n/a]]_select']")
	public WebElement PunchoutProfile_cXMLOutboundRule1FiledNameSelect;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/CreateItemListEntry[n/a]]_input']")
	public WebElement PunchoutProfile_cXMLOutboundRule1TargetInput;
	@FindBy(how = How.XPATH, using = "//div[text()='Extrinsic Name']")
	public WebElement PunchoutProfile_cXMLOutboundRule4;
	@FindBy(how = How.XPATH, using = "//td[text()='Create Variable or Constant Extrinsic']")
	public WebElement PunchoutProfile_cXMLCreateOutboundRule4;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/CreateItemListEntry[n/a][1]]_select']")
	public WebElement PunchoutProfile_cXMLOutboundRule4FiledSelect;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/CreateItemListEntry[n/a][1]]_input']")
	public WebElement PunchoutProfile_cXMLOutboundRule4ExtrinsicNameInput;
	@FindBy(how = How.XPATH, using = "//div[@class='gilcEntry-mandatory'][text()='Category']")
	public WebElement PunchoutProfile_cXMLCommodityMapping;
	@FindBy(how = How.XPATH, using = "//td[text()='Create UNSPSC Mapping Rule']")
	public WebElement PunchoutProfile_cXMLCreateCommodityMapping;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Content/StringEditor[in Content/CreateItemListEntry[n/a]')]")
	public List<WebElement> PunchoutProfile_CreateMappingRulesEntry;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/McSearchListConfigurable[PunchOutCustomerProfile]_!open_true_label']")
	public WebElement PunchoutProfile_EditProfile;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[PunchOutCustomerProfile.tab.punchoutcustomerprofile.cxml]_span']")
	public WebElement PunchoutProfile_cXMLTab;

	// 9382
	@FindBy(how = How.XPATH, using = "//div[text()='Group ID']")
	public WebElement PunchoutProfile_cXMLSoldTo;
	@FindBy(how = How.XPATH, using = "//td[text()='Create Sold To Determination']")
	public WebElement PunchoutProfile_cXMLCreateSoldTo;
	@FindBy(how = How.XPATH, using = "//input[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/CreateItemListEntry[n/a]]']")
	public WebElement PunchoutProfile_cXMLSoldToIDInput;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'delete_entry_img')]")
	public WebElement PunchoutProfile_cXMLDeleteImg;

	@FindBy(how = How.XPATH, using = "(//div[text()='Field'])[1]")
	public WebElement PunchoutProfile_cXMLOutboundRule2;
	@FindBy(how = How.XPATH, using = "//td[text()='Create Constant Value']")
	public WebElement PunchoutProfile_cXMLCreateOutboundRule2;

	@FindBy(how = How.XPATH, using = "//div[text()='Key Name']")
	public WebElement PunchoutProfile_cXMLOutboundRule3;
	@FindBy(how = How.XPATH, using = "//td[text()='Create Variable or Constant Key/Value Pair']")
	public WebElement PunchoutProfile_cXMLCreateOutboundRule3;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Content/EnumerationValueSelectEditor[in Content/CreateItemListEntry[n/a]')]")
	public WebElement PunchoutProfile_cXMLOutboundRuleFiledSelect;

	// 21963
	@FindBy(how = How.XPATH, using = "//a[@id='Content/OrganizerComponent[organizersearch][B2BCustomer]_togglelabel']")
	public WebElement B2BCustomer_SearchToggle;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/NemoPasswordEditor_new']")
	public WebElement B2BCustomer_NewPassword;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/NemoPasswordEditor_repeat']")
	public WebElement B2BCustomer_PasswordRepeat;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.enablePunchoutCompanyCart]]_false']")
	public WebElement B2BUnit_PunchoutCompanyCarFalse;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.enablePunchoutCompanyCart]]_true']")
	public WebElement B2BUnit_PunchoutCompanyCarTrue;

	// NA-20000 by Ankit

	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'[customAdminControlled]')])[1]")
	public WebElement B2BUnit_SA_SelectAdminRole;

	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'[approverDefault]')])[1]")
	public WebElement B2BUnit_SA_SelectApproverRole;

	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'[buyerDefault]')])[1]")
	public WebElement B2BUnit_SA_SelectBuyerRole;

	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'[builderDefault]')])[1]")
	public WebElement B2BUnit_SA_SelectBuilderRole;

	@FindBy(how = How.XPATH, using = ".//table[contains(@id,'B2BUnit.needApproveUserRoles')]//div[contains(.,'ID')]")
	public WebElement B2BUnit_SA_UserApproval;

	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'add_true_tr')]/td[contains(.,'Add User')]")
	public WebElement B2BUnit_SA_AddRole;

	@FindBy(how = How.XPATH, using = "//td/input[contains(@id,'B2BCustomer.email')]")
	public WebElement B2BCustomer_EmailSearch;

	@FindBy(how = How.XPATH, using = ".//*[@id='ModalGenericFlexibleSearchOrganizerSearch[PrincipalGroup]_searchbutton']")
	public WebElement B2BCustomer_Search;

	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'[nemob2b_quote_reviewer]')]")
	public WebElement B2BCustomer_NemoB2BQuoteReview;

	@FindBy(how = How.XPATH, using = "(.//table[@class='genericItemListChip']//div[contains(@id,'resultlist')]//img[contains(@id,'[in Content/GenericItemList')])[2]")
	public WebElement B2BCustomer_SearchIcon;

	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'PrincipalGroup.uid')]")
	public WebElement B2BCustomer_PrincipalGroupId;

	// 10324
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[DataUpload]_label']")
	public WebElement Nemo_DataUpload;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[DataUpload.code]]_input']")
	public WebElement DataUpload_inputIdentifier;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/NemoSearchConfigurable[DataUpload]_searchbutton']")
	public WebElement DataUpload_searchButton;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/DataUploadEditor_upload_div']")
	public WebElement DataUpload_UploadButton;
	@FindBy(how = How.XPATH, using = "//input[@class='modalMediaFileUploadChip']")
	public WebElement UploadPage_ChooseFileButton;
	@FindBy(how = How.XPATH, using = ".//td[contains(text(),'Upload')]")
	public WebElement UploadPage_UploadButton;
	@FindBy(how = How.XPATH, using = "//td[@id='outerTD']/div")
	public WebElement UploadPage_UploadInformation;

	// added by 17711 qianqian
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AllInstancesSelectEditor')]//option[contains(text(),'masterMultiCountryProductCatalog - Staged')]")
	public WebElement Cataglog_CatalogVersionStaged;
	@FindBy(how = How.XPATH, using = "//div[contains(.,'version')]/../../td[@class='condition']//select")
	public WebElement Cataglog_CatalogVersionSel;
	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'Display to')]/../..//input[@type='text'])[1]")
	public WebElement Product_DisplayTo;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'.syncitem')]")
	public WebElement Product_SynchronizationBtn;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'targetversionselection')]")
	public WebElement Product_SyncTargetBtn;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'[sync_NA') ]")
	public WebElement Product_SyncExeJobNA;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'[sync_AP')]")
	public WebElement Product_SyncExeJobAP2;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'[sync_EMEA') ]")
	public WebElement Product_SyncExeJobEMEA;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'[sync_ANZ')]")
	public WebElement Product_SyncExeJobANZ;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'[sync_LA') ]")
	public WebElement Product_SyncExeJobLA;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'wizard.footer.start')]")
	public WebElement Product_SyncStart;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Synchronization finished.')]")
	public WebElement Product_SyncFinishMsg;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'resultlist_GenericItemList')]//td[4]")
	public WebElement Product_SyncStatus;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'needRunMappingRule') and contains(@id,'true')]")
	public WebElement Product_NeedRunMapping;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'resultlist_GenericItemList')]//td[5]")
	public WebElement Product_SyncResult;
	// 13165
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.isActivate]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_ActiveProfile;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.oracleActive]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_OracleActive;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.isOracleCustomCart]]_checkbox']/preceding-sibling::*")
	public WebElement PunchoutProfile_CustomCart;

	// added by 23037
	@FindBy(how = How.XPATH, using = ".//div[text()='Instant Savings']")
	public WebElement B2CPriceRules_InstantSavingOption;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Complex')]")
	public WebElement B2CPriceRules_Complex;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Add Threshold')]")
	public WebElement B2CPriceRules_AddThreshold;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']//input[@type='text'])[1]")
	public WebElement B2CPriceRules_Threshold1;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']//input[@type='text'])[3]")
	public WebElement B2CPriceRules_Threshold2;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']//input[@type='text'])[2]")
	public WebElement B2CPriceRules_Value1;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']//input[@type='text'])[4]")
	public WebElement B2CPriceRules_Value2;
	@FindBy(how = How.XPATH, using = ".//*[@id='complex-temp']/div[2]/div[1]/div/div[1]/label[2]")
	public WebElement B2CPriceRules_Fix1;
	@FindBy(how = How.XPATH, using = ".//*[@id='complex-temp']/div[2]/div[2]/div/div[1]/label[2]")
	public WebElement B2CPriceRules_Fix2;
	@FindBy(how = How.XPATH, using = ".//*[@id='complex-temp']/div[2]/div[1]/div/div[2]/label[2]")
	public WebElement B2CPriceRules_Unit1;
	@FindBy(how = How.XPATH, using = ".//*[@id='complex-temp']/div[2]/div[2]/div/div[2]/label[2]")
	public WebElement B2CPriceRules_Unit2;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/GenericItemList[1]_!add_true_label']")
	public WebElement AddCheckoutPaymentType;

	@FindBy(how = How.XPATH, using = ".//tr[contains(@id,'B2BUnit.needApproveUserRoles]]_builderDefault')]/td[contains(.,'builderDefault')]/../td/input[contains(@id,'[B2BUnit.needApproveUserRoles]]_true')]")
	public WebElement BuilderAccountApproverYes;

	// NA-17401

	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2BUnit.lenovoAdminEmail]]_input')]")
	public WebElement B2BUnit_SA_LenovoAdminEmail;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2CUnit.zSwitchSMBProfile]]_true")
	public WebElement SMB_SwitchSMBProfile_Yes;

	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[B2BUnit.adminEmail]]_input')]")
	public WebElement B2BUnit_SA_AdminEmail;

	@FindBy(how = How.XPATH, using = ".//table[@id='table_Content/GenericItemList[1]_table']/tbody/tr")
	public WebElement paymentTypeOptions;

	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoAvailableUserGroup[in Content/Attribute[B2BUnit.needApproveUserRoles]]_buyerDefault_tr']/td[4]/input[1]")
	public WebElement B2BUnit_SA_BuyerApproverTrue;

	// NA-17702
	@FindBy(how = How.XPATH, using = ".//table[@title='emailProductToggle']//input[@value='true']")
	public WebElement siteAttribute_emailProdToggleYes;
	@FindBy(how = How.XPATH, using = ".//table[@title='emailProductToggle']//input[@value='false']")
	public WebElement siteAttribute_emailProdToggleNo;
	@FindBy(how = How.XPATH, using = "//tr/td/img[contains(@id,'closesession')]")
	public WebElement hmcHome_hmcSignOut;

	// NA-18081, User tab
	@FindBy(how = How.XPATH, using = ".//a[contains(@id,'[user]_label')]")
	public WebElement home_userTab;
	@FindBy(how = How.XPATH, using = ".//a[contains(@id,'[Customer]_label')]")
	public WebElement userTab_customerTab;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Customer.uid')]")
	public WebElement customer_customerIDTextBox;
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'[Customer]_searchbutton')]")
	public WebElement customer_customerSearchButtonn;
	@FindBy(how = How.XPATH, using = ".//div[@class='olecIcon']//img")
	public WebElement customer_searchedResultImage;
	// Customer Search page, under the administration tab once the customer has
	// been searched
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'Customer.administration')]")
	public WebElement customerSearch_administrationTab;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Customer.optin')][@value='false']")
	public WebElement customerSearch_optInNo;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Customer.optin')][@value='true']")
	public WebElement customerSearch_optInYes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'verifiedInLenovoID')][@value='true']")
	public WebElement customerSearch_verifyInLenovoYesChkBox;
	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'save.title')]")
	public WebElement customerSearch_saveButton;

	// NA-21258
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[PaymentTypeMessage]_label']")
	public WebElement NemoPayment_NemoPaymentMessage;
	@FindBy(how = How.XPATH, using = "(//td[contains(@id,'create_PaymentTypeMessage')])[2]")
	public WebElement NemoPayment_CreatePaymentMessage;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'name')]")
	public WebElement PaymentMessage_DescInputName;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'code')]")
	public WebElement PaymentMessage_DescInputCode;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'checkoutPaymentType')]")
	public WebElement PaymentMessage_DescpaymentType;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'checkoutPaymentType')]/option[contains(.,'Card Payment')]")
	public WebElement PaymentMessage_DescCardPaymentOption;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'configLevel')]")
	public WebElement PaymentMessage_DescConfigLevel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'configLevel')]/option[3]")
	public WebElement PaymentMessage_DescSpecificConfig;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]")
	public WebElement PaymentMessage_DescChannel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]/option[2]")
	public WebElement PaymentMessage_DescAllChannel;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/BooleanEditor[in Content/Attribute[.active]]_true']")
	public WebElement PaymentMessage_DescActiveYes;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'message')]")
	public WebElement PaymentMessage_Messagetab;
	@FindBy(how = How.XPATH, using = "(//th[@class='checkbox gilcCheckbox'])[1]")
	public WebElement PaymentMessage_MesgThresholdMaxTable;
	@FindBy(how = How.XPATH, using = "(//th[@class='checkbox gilcCheckbox'])[2]")
	public WebElement PaymentMessage_MesgThresholdMinTable;
	@FindBy(how = How.XPATH, using = "//tr/td[contains(.,'Create new')]")
	public WebElement PaymentMessage_MesgCreateNew;
	@FindBy(how = How.XPATH, using = "(//tr/td[contains(.,'Promotion price row')])[last()]")
	public WebElement PaymentMessage_MesgPromotionPriceRow;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'priority')]")
	public WebElement PaymentMessage_MesgPriority;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'tier')]")
	public WebElement PaymentMessage_MesgTier;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'interestRate')]")
	public WebElement PaymentMessage_MesgInterestRate;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/TextAreaEditor[in Content/Attribute[.termsConditionslink]]_textarea']")
	public WebElement PaymentMessage_MesgTermsAndCond;
	@FindBy(how = How.XPATH, using = "//textarea[contains(@id,'content')]")
	public WebElement PaymentMessage_MesgContentBox;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'b2cunits')]")
	public WebElement PaymentMessage_B2CUnitTab;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'b2bunits')]")
	public WebElement PaymentMessage_B2BUnitTab;
	@FindBy(how = How.XPATH, using = "//input[@name='SELECTOR']/..")
	public WebElement PaymentMessage_B2CUnitTableHead;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Add B2C Unit')]")
	public WebElement PaymentMessage_AddB2CUnit;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Add B2B Unit')]")
	public WebElement PaymentMessage_AddB2BUnit;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.uid')]")
	public WebElement PaymentMessage_IdCodeInputBox;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2BUnit.uid')]")
	public WebElement PaymentMessage_IdCodeInputBox_B2B;
	@FindBy(how = How.XPATH, using = ".//span[contains(.,'Search')]")
	public WebElement PaymentMessage_Search;
	@FindBy(how = How.XPATH, using = "//a[@title='Use']")
	public WebElement PaymentMessage_Use;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'save.title')]")
	public WebElement HMC_Save;
	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'checkoutPaymentType')]")
	public WebElement PaymentMessage_PaymentTypeDropDown;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'PaymentTypeMessage]_!remove_true_label')]")
	public WebElement PaymentMessage_RemoveCardName;
	@FindBy(how = How.XPATH, using = "//table[@title='isQuoteDisplay']//input[contains(@id,'BooleanEditor')][@value='true']")
	public WebElement HMC_IsQuoteDisplay;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'closesession')]")
	public WebElement HMC_Logout;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2BUnit.enableB2BEmailQuote]]_true')]")
	public WebElement SiteAttribute_EnableQuote;

	// NA-17699
	@FindBy(how = How.XPATH, using = "//div/input[contains(@id,'optin')][@checked='']")
	public WebElement CustomerAccount_selectedOptIn;

	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EditorTab[B2BCustomer.password]_span']")
	public WebElement CustomerAccount_passwordTab;

	// NA-17697
	@FindBy(how = How.XPATH, using = "//ul/li/div[contains(.,'eCoupon Discounts')]")
	public WebElement B2CPriceRules_eCouponDiscountOption;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enter title...']")
	public WebElement B2CPriceRules_couponTitle;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enter description...']")
	public WebElement B2CPriceRules_description;

	@FindBy(how = How.XPATH, using = ".//div[@class='modal-content']//span[text()='Select Catalog Version...']")
	public WebElement B2CPriceRules_catalogVersionDD;

	@FindBy(how = How.XPATH, using = ".//div[@class='modal-content']//span[text()='Select Material...']")
	public WebElement B2CPriceRules_materialDD;

	@FindBy(how = How.XPATH, using = ".//ul/li/a[contains(.,'Dynamic')]")
	public WebElement B2CPriceRules_dynamicRate;

	@FindBy(how = How.XPATH, using = ".//*[@id='dynamic-temp']/div[2]/input")
	public WebElement B2CPriceRules_dynamicValue;

	@FindBy(how = How.XPATH, using = "//div/div/button[contains(.,'Add Price Rule to Group')]")
	public WebElement B2CPriceRules_addToGroupButton;

	@FindBy(how = How.XPATH, using = "//div/button[contains(.,'Create New Group')]")
	public WebElement B2CPriceRules_createNewGroupButton;

	@FindBy(how = How.XPATH, using = "(//table//tr/td[@class='name no-border'])[1]")
	public WebElement B2CPriceRules_couponName;

	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'[CronJob.job]')]")
	public WebElement CronJob_selectCronJob;

	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'[CronJob.job]')]/option[contains(.,'singleCouponJob')]")
	public WebElement selectCronJob_SingleCoupon;

	@FindBy(how = How.XPATH, using = "//div/input[contains(@id,'SingleCouponCronJob.active')]")
	public WebElement CronJob_enableStartCronJobCheckBox;

	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'Multimedia')]")
	public WebElement Multimedia_menu;

	@FindBy(how = How.XPATH, using = "//td/a[contains(@id,'[Media]_label')]")
	public WebElement Multimedia_media;

	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'Media.folder')]")
	public WebElement Multimedia_clickSelectFolder;

	@FindBy(how = How.XPATH, using = "//td/select[contains(@id,'Media.folder')]/option[contains(.,'singlecoupons')]")
	public WebElement Multimedia_selectFolder;

	@FindBy(how = How.XPATH, using = "//table[@title='Last page (Alt+L)']//td/img")
	public WebElement Multimedia_selectLastPage;

	@FindBy(how = How.XPATH, using = "//div/input[contains(@placeholder,'Confirm check')]")
	public WebElement B2CPriceRules_deleteConfirmationTxtBox;

	@FindBy(how = How.XPATH, using = "//div/button[contains(.,'Confirm')]")
	public WebElement B2CPriceRules_confirmButtonOnConfPopUp;

	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EditorTab[SingleCouponCronJob.tab.cronjob.processas]_span']")
	public WebElement SingleCouponCronJob_RunAsTab;

	@FindBy(how = How.XPATH, using = ".//*[@id='Content/IntegerEditor[in Content/Attribute[SingleCouponCronJob.nodeID]]_input']")
	public WebElement SingleCouponCronJob_ServerNode;

	@FindBy(how = How.XPATH, using = ".//*[@id='isSingleEcouponId']")
	public WebElement B2CPriceRules_TypeSingleCoupon;

	@FindBy(how = How.XPATH, using = ".//*[@id='idECouponUpperLimit']")
	public WebElement B2CPriceRules_count;

	@FindBy(how = How.XPATH, using = ".//*[@id='Content/McSearchListConfigurable[Media]_code_sort']")
	public WebElement Multimedia_SortIdentifier;

	// NA-18480
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'enableCountrySeal')][@value='true']")
	public WebElement siteAttribute_EnableCountrySealYes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'enableCountrySeal')][@value='false']")
	public WebElement siteAttribute_EnableCountrySealNo;

	// NA-21941
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2CUnit.emailWishListToggle]]_true')]")
	public WebElement siteAttribute_emailWishListToggleYes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2CUnit.emailWishListToggle]]_false')]")
	public WebElement siteAttribute_emailWishListToggleNo;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[B2CUnit.isWishListDisplay]]_true')]")
	public WebElement siteAttribute_isWishlistDisplayToggleYes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[B2CUnit.isWishListDisplay]]_false')]")
	public WebElement siteAttribute_isWishlistDisplayToggleNo;
	@FindBy(how = How.XPATH, using = "//input[@value='isQuoteAvailable']/ancestor::td/following-sibling::td//input[@value='true']")
	public WebElement B2BAdministration_isQuoteAvailable;
	@FindBy(how = How.XPATH, using = "(//a[@title='Open Editor']//img)[1]")
	public WebElement BaseStore_SearchResult;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'BaseStore.properties')]")
	public WebElement BaseStore_PropertiesTab;
	@FindBy(how = How.XPATH, using = "(//div[contains(.,'Delivery Countries:')])[2]")
	public WebElement PropertiesTab_DeliveryCountryTable;
	@FindBy(how = How.XPATH, using = "//table[@title='deliveryCountries']//td[5]//tr[1]/th[1]")
	public WebElement PropertiesTab_DeliveryCountryTableHead;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Add Country')]")
	public WebElement PropertiesTab_AddCountryLink;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'isocode')]")
	public WebElement AddCountry_ISOCodeInputBox;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Search')]")
	public WebElement AddCountry_Search;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Use')]")
	public WebElement AddCountry_Use;

	// added by 23007
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[BaseStore.taxType')]/option[contains(text(),'GST')]")
	public WebElement basestore_GST;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/BooleanEditor[in Content/Attribute[BaseStore.net')]/../input[1]")
	public WebElement basestore_NetValue;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/BooleanEditor[in Content/Attribute[BaseStore.net')]")
	public WebElement basestore_NetCheck;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.enableGeoLink') and contains(@id,'false')]")
	public WebElement B2CUnit_DisableGEOLink;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicTaxToggle') and contains(@id,'true')]")
	public WebElement B2CUnit_EnableTax;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/DoubleEditor[in Content/Attribute[B2CUnit.taxValue')]")
	public WebElement B2CUnit_TaxValue;
	@FindBy(how = How.XPATH, using = ".//*[@id='total']/samp")
	public WebElement B2CpriceSimulate_TotalPrice;

	// ADDED BY 23009
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[BaseStore.taxType')]/option[contains(text(),'NO Tax')]")
	public WebElement basestore_NoTax;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.enableGeoLink') and contains(@id,'true')]")
	public WebElement B2CUnit_EnableGEOLink;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.useSabrixEngine') and contains(@id,'true')]")
	public WebElement B2CUnit_EnableSabrixEngine;

	// -------------------
	@FindBy(how = How.XPATH, using = "")
	public WebElement Home_Order_SubPrice;
	// NA-17650
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[B2CUnit.enableDeliveryTimeZone]]_true')]")
	public WebElement B2CSiteAttribute_DeliveryTimeZoneYes;

	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[B2CUnit.enableDeliveryTimeZone]]_false')]")
	public WebElement B2CSiteAttribute_DeliveryTimeZoneNo;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Tax:')]/following-sibling::*")
	public WebElement B2CpriceSimulate_TaxPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='list']/samp")
	public WebElement B2CpriceSimulate_ListPrice;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'GST:')]/following-sibling::*")
	public WebElement B2CpriceSimulate_ListAndGstPrice;

	@FindBy(how = How.XPATH, using = "//td/input[contains(@id,'BaseStore.stockChangeMessage')]")
	public WebElement BaseStoreAdministrator_StockMessage;
	@FindBy(how = How.XPATH, using = "(//table[@title='warehouses']//tbody[contains(@id,'ItemList')])//td[3]/div/div")
	public WebElement BaseStoreProperties_WarehouseData;

	@FindBy(how = How.XPATH, using = "//a[contains(@id,'StockLevel')]")
	public WebElement BaseCommerce_StockLevel;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_StockLevel_label')]")
	public WebElement BaseCommerce_CreateStockLevel;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'productCode')]")
	public WebElement StockLevel_ProductCode;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'warehouse')]")
	public WebElement StockLevel_WarehouseIcon;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Warehouse.code')]")
	public WebElement WarehouseIcon_Warehousecode;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'inStockStatus')]")
	public WebElement StockLevel_InStockStatus;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'inStockStatus')]/option[contains(.,'forceOutOfStock')]")
	public WebElement InStockStatus_ForceOutOFStock;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'inStockStatus')]/option[contains(.,'forceInStock')]")
	public WebElement InStockStatus_ForceInStock;

	@FindBy(how = How.XPATH, using = "//img[contains(@id,'saveandcreate')]")
	public WebElement HMC_CreateAndSave;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[StockLevel.productCode]]_input']")
	public WebElement StockLevel_PartNoTextBox;
	@FindBy(how = How.XPATH, using = "(//a[@title='Open Editor']/span/img)[1]")
	public WebElement StockLevel_Result;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'remove')][@class='icon']")
	public WebElement HMC_RemoveResult;

	@FindBy(how = How.XPATH, using = "//td/input[contains(@id,'B2CUnit.unSoldMsg')]")
	public WebElement B2CUnit_SiteAttributeTab_SoldOutMessage;
	@FindBy(how = How.XPATH, using = "//td/input[contains(@id,'B2CUnit.tempUnaviMeg')]")
	public WebElement B2CUnit_SiteAttributeTab_TempUnavailableMessage;
	@FindBy(how = How.XPATH, using = "//table[@class = 'allInstancesSelectEditorChip']//select")
	public WebElement Catalog_CatalogVersion;
	@FindBy(how = How.XPATH, using = "//table[@class = 'listtable']//select/option[contains(.,'masterMultiCountryProductCatalog - Online')]")
	public WebElement Catalog_MasterMultiCountryProductCatalogOnline;
	@FindBy(how = How.XPATH, using = "//table[@class = 'listtable']//select/option[contains(.,'masterMultiCountryProductCatalog - Staged')]")
	public WebElement Catalog_MasterMultiCountryProductCatalogStaged;
	@FindBy(how = How.XPATH, using = "(//span[contains(.,'PMI')])[2]")
	public WebElement Catalog_PMITab;
	@FindBy(how = How.XPATH, using = "//table[@title='productStatus']//select")
	public WebElement Catalog_Products_MarketingStatus;
	@FindBy(how = How.XPATH, using = "//option[contains(.,'SoldOut')]")
	public WebElement Catalog_ProductStatus_SoldOut;
	@FindBy(how = How.XPATH, using = "//option[contains(.,'TempUnavailable')]")
	public WebElement Catalog_ProductStatus_TempUnavailable;
	@FindBy(how = How.XPATH, using = "//td[@class = 'label']/div[contains(.,'Product Marketing Status')]/../../td[5]//select/option[@value='-1']")
	public WebElement Catalog_ProductStatus_InIt;

	// NA-22333
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.hideBillingAddressToggle') and contains(@id,'true')]")
	public WebElement HideBillingAddressToggle;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.hideBillingAddressToggle') and contains(@id,'false')]")
	public WebElement BillingAddressToggle;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'countryId')]//span[contains(.,'Select Country...')]")
	public WebElement B2CpriceSimulate_CountryButton;

	// NA-22337
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'[B2CUnit.pendingBpctoOrderToggle]]_true')]")
	public WebElement PendingBPCTOOrderToggle;

	// NA-21596
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'[B2CUnit.toggleSLI]]_true')]")
	public WebElement SLIToggle;

	// NA-17709
	@FindBy(how = How.XPATH, using = "//table[@title='baseProduct']//input[contains(@id,'baseProduct')]")
	public WebElement Products_baseProduct;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'open_editor_internal_true_label')]")
	public WebElement Products_edit;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicOptionTemplate')]")
	public WebElement Products_activeTemplate;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Category System')]")
	public WebElement Products_categorySystem;
	@FindBy(how = How.XPATH, using = "(//*[contains(text(),'Supercategories:')]/../..//div[contains(@id,'StringDisplay')]/div[text()])[1]")
	public WebElement Products_firstSupercategoriesID;
	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'Product Builder')]")
	public WebElement Nemo_productBuilder;
	@FindBy(how = How.XPATH, using = "//tr[@title='Template']//a")
	public WebElement Nemo_template;
	@FindBy(how = How.XPATH, using = "//*[.='Tab Title']")
	public WebElement Template_tabTitle;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Create Product Option')]")
	public WebElement Template_createProductOption;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'.title]')]")
	public WebElement Template_titleInput;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'.code]')]")
	public WebElement Template_codeInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'.ctoFlag')]")
	public WebElement Template_ctoFlagCheckbox;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Sections')]/../..//table[contains(@id,'resultTable')]")
	public WebElement Template_sectionsTable;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Groups')]/../..//table[contains(@id,'resultTable')]")
	public WebElement Template_groupsTable;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Categories')]/../..//table[contains(@id,'resultTable')]")
	public WebElement Template_categoriesTable;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Add [Hierarchy]')]")
	public WebElement Template_addHierarchy;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Identifier')]/../..//input")
	public WebElement Template_identifier;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'ItemSearchList')]//tr[2]")
	public WebElement Template_searchedHierarchy;
	@FindBy(how = How.XPATH, using = "(.//select[contains(@id,'catalogVersion') and contains(@id,'AllInstancesSelect')])[1]")
	public WebElement Template_catalogVersion;
	@FindBy(how = How.XPATH, using = "(//select[contains(@id,'AllInstancesSelectEditor')]//option[contains(text(),'masterMultiCountryProductCatalog - Online')])[1]")
	public WebElement Template_selCatalogVersionOnline;
	@FindBy(how = How.XPATH, using = "(//select[contains(@id,'AllInstancesSelectEditor')]//option[contains(text(),'masterMultiCountryProductCatalog - Staged')])[1]")
	public WebElement Template_selCatalogVersionStaged;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Use')]")
	public WebElement Template_useBtn;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'save.title')]")
	public WebElement Template_saveBtn;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement Template_searchBtn;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'reset.title')]")
	public WebElement Template_reloadBtn;
	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'Batch Unassign Option')]")
	public WebElement ProductBuilder_batchUnassign;
	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'Batch Assign Option')]")
	public WebElement ProductBuilder_batchAssign;
	@FindBy(how = How.ID, using = "catalogVersion")
	public WebElement ProductBuilder_clickSelCatalogVersion;
	@FindBy(how = How.XPATH, using = "//div//option[contains(.,'masterMultiCountryProductCatalog-Staged')]")
	public WebElement ProductBuilder_selCatalogVersionStaged;
	@FindBy(how = How.XPATH, using = "//div//option[contains(.,'masterMultiCountryProductCatalog-Online')]")
	public WebElement ProductBuilder_selCatalogVersionOnline;
	@FindBy(how = How.ID, using = "countryCode")
	public WebElement ProductBuilder_countryCode;
	@FindBy(how = How.ID, using = "channelCode")
	public WebElement ProductBuilder_channelCode;
	@FindBy(how = How.XPATH, using = "//option[@value='B2C']")
	public WebElement ProductBuilder_channelB2C;
	@FindBy(how = How.XPATH, using = "//option[@value='B2B']")
	public WebElement ProductBuilder_channelB2B;
	@FindBy(how = How.ID, using = "showSearchOptionDialog")
	public WebElement ProductBuilder_showSearchOptionDialog;
	@FindBy(how = How.ID, using = "optionCodeInput")
	public WebElement ProductBuilder_optionCodeInput;
	@FindBy(how = How.ID, using = "searchOptionBtn2")
	public WebElement ProductBuilder_searchOptionBtn2;
	@FindBy(how = How.ID, using = "selectOptionBtn")
	public WebElement ProductBuilder_selectOptionBtn;
	@FindBy(how = How.XPATH, using = "(//select[contains(@id,'machineTypesSelectNamems') and contains(@id,'sx')]//option)[1]")
	public WebElement ProductBuilder_mtList;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Search: ')]//input")
	public WebElement ProductBuilder_searchInput;
	@FindBy(how = How.XPATH, using = "//*[@title='Add Selected']")
	public WebElement ProductBuilder_addSelected;
	@FindBy(how = How.ID, using = "submit")
	public WebElement ProductBuilder_submit;

	// NA-22685
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[Contract]_searchbutton']")
	public WebElement ContaractID_Search;
	@FindBy(how = How.XPATH, using = "//img[@class='icon' and contains(@id,'Content/OrganizerListEntry')]")
	public WebElement Result_OpenEditor;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringDisplay[4X20E50574]_span']")
	public WebElement Product_Code;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[ExchangeRate]_searchbutton']")
	public WebElement ExchageRate_SearcheButton;
	@FindBy(how = How.ID, using = "Content/OrganizerListEntry[8797118385696]_img")
	public WebElement Rate_OpenEditor;

	// NA-17410
	@FindBy(how = How.XPATH, using = ".//a[contains(@id,'[organizersearch][B2BCustomer]_togglelabel')]")
	public WebElement b2bCustomer_navigationSearchBar;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'B2BCustomer.b2BUserStatus')]")
	public WebElement B2BCustomer_ActiveUserDropdownCommon;

	// NA-18936
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericExplorerMenuTreeNode[cms2]_label']")
	public WebElement hmcHome_WCMS;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[CMSSite]_label']")
	public WebElement wcmsPage_websites;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[AbstractPage]_label']")
	public WebElement wcmsPage_pages;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/GenericCondition[AbstractPage.name]]_input']")
	public WebElement wcmsPagesPage_pageNameTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[AbstractPage.catalogVersion]]_select']")
	public WebElement wcmsPagesPage_catalogVersionDD;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[AbstractPage]_searchbutton']")
	public WebElement wcmsPagesPage_searchButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringDisplay[page_00006ZJL]_span']")
	public WebElement wcmsPagesPage_searchedResult;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EditorTab[ContentPage.administration]_span']")
	public WebElement wcmsPagesPage_administrationTab;
	@FindBy(how = How.XPATH, using = ".//textarea[contains(@id,'TextAreaEditor[in Attribute[CssJsOverridePageComponent.Content]')]")
	public WebElement wcmsPagesPageAdministration_BodyTextBox;
	@FindBy(how = How.XPATH, using = ".//a[contains(@onclick,'open_editor')]")
	public WebElement cssjsOverridePage_mediaFileLink;
	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'preview')]")
	public WebElement cssjsOverridePage_previewMediaFileButton;

	// NA-25385
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/GenericCondition[AbstractPage.uid]]_input']")
	public WebElement wcmsPagesPage_pageIDTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringDisplay[WebFormPage]_span']")
	public WebElement wcmsPagesPage_uniqueSearchResult;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/McSearchListConfigurable[AbstractPage]_!open_true_label']")
	public WebElement wcmsPagesPage_popupEdit;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EditorTab[ContentPage.tab.page.contentslots]_span']")
	public WebElement wcmsPagesPage_contentTab;
	@FindBy(how = How.XPATH, using = ".//*[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/EditableItemListEntry[FinePrint]]']")
	public WebElement wcmsPagesPage_contentTab_finePrintCon;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/AutocompleteReferenceEditor[in Content/EditableItemListEntry[FinePrint]]_!open_editor_true_label']")
	public WebElement wcmsPagesPage_contentTab_finePrintCon_EditNew;
	@FindBy(how = How.XPATH, using = ".//*[@id='StringDisplay[FinePrintContent]_span']")
	public WebElement wcmsPagesPage_contentTab_finePrintCon_Editer_Contentslot;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/GenericResortableItemList_!open_true_label']")
	public WebElement wcmsPagesPage_contentTab_finePrintCon_Editer_EditNew;
	@FindBy(how = How.XPATH, using = ".//*[@id='tinymce']")
	public WebElement wcmsPagesPage_contentTab_finePrintCon_tinyMce;
	@FindBy(how = How.XPATH, using = ".//*[@id='ImageToolbarAction[organizer.editor.save.title][1]_label']")
	public WebElement wcmsPagesPage_contentTab_finePrintCon_tinyMce_save;

	// NA-21905
	@FindBy(how = How.XPATH, using = ".//a[contains(@id,'group.nemo.payment')]")
	public WebElement nemoPage_payment;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'Direct Deposit')]")
	public WebElement paymentType_directDepositSearchedResult;
	@FindBy(how = How.XPATH, using = ".//table[@title='b2CUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]")
	public WebElement paymentProfile_availableB2CUnits;
	@FindBy(how = How.XPATH, using = ".//table[@title='b2BUnits']//td[contains(@style,'border-right')]//div[contains(@id,'span')]")
	public WebElement paymentProfile_availableB2BUnits;
	@FindBy(how = How.XPATH, using = ".//table[@title='countries']//td[contains(@style,'border-right')]//div[contains(@id,'span')]")
	public WebElement paymentProfile_availableCountries;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'PaymentTypeProfile.configLevel')]")
	public WebElement paymentProfileGlobalTab_configurationLevelDD;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'PaymentTypeProfile.channel')]")
	public WebElement paymentProfileGlobalTab_channelDD;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[PaymentTypeProfile.active]]_true')]")
	public WebElement paymentProfileGlobalTab_activeRadioButtonYes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[PaymentTypeProfile.active]]_false')]")
	public WebElement paymentProfileGlobalTab_activeRadioButtonNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'visibilityForASM]]_true')]")
	public WebElement paymentProfileGlobalTab_visibilityForASMYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'visibilityForASM]]_false')]")
	public WebElement paymentProfileGlobalTab_visibilityForASMNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableMixedOrder]]_true')]")
	public WebElement paymentProfileGlobalTab_disableMixedOrderYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableMixedOrder]]_false')]")
	public WebElement paymentProfileGlobalTab_disableMixedOrderNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disablePcgOrder]]_true')]")
	public WebElement paymentProfileGlobalTab_disablePCGOrderYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disablePcgOrder]]_false')]")
	public WebElement paymentProfileGlobalTab_disablePcgOrderNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableDcgOrder]]_true')]")
	public WebElement paymentProfileGlobalTab_disableDcgOrderYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableDcgOrder]]_false')]")
	public WebElement paymentProfileGlobalTab_disableDcgOrderNo;
	@FindBy(how = How.XPATH, using = ".//td[contains(@id,'[B2BUnit.paymentTypeAndPayerId]]_BANKDEPOSIT')]")
	public WebElement b2bUnitSiteAttribute_bankDepositPaymentType;
	@FindBy(how = How.XPATH, using = ".//table[contains(@id,'B2BUnit.paymentTypeAndPayerId]]_table')]//th[1]")
	public WebElement paymentProfileGlobalTab_paymentTypeAndPayerIDTop;
	@FindBy(how = How.XPATH, using = ".//td[contains(@id,'[B2BUnit.paymentTypeAndPayerId]]_!add_true_label')]")
	public WebElement b2bUnitSiteAttribute_addPaymentTypePayerID;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'[CheckoutPaymentType.code]')]")
	public WebElement checkoutPaymentType_identifierTxtBox;
	// When a new payment Type is added and pop up window is opened
	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'resultlist')]//div[contains(@id,'StringDisplay')])[1]")
	public WebElement checkoutPaymentType_searchedResult;
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'use')]")
	public WebElement checkoutPaymentType_useButton;

	@FindBy(how = How.XPATH, using = "//table[contains(@oncontextmenu,'Checkout Payment Type')]//input[@name='SELECTOR']")
	public WebElement B2CSiteAttribute_PayTypeHead;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Add Checkout Payment Type')]")
	public WebElement B2CSiteAttribute_AddPayType;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'CheckoutPaymentType.code')]")
	public WebElement PaymentType_CheckoutCode;
	@FindBy(how = How.XPATH, using = "//div/div[contains(@id,'Cheque Payment')]")
	public WebElement PaymentType_SearchResultCheque;
	@FindBy(how = How.XPATH, using = "//textarea[contains(@id,'xmlContentShow')]")
	public WebElement Order_XmlContent;
	@FindBy(how = How.XPATH, using = "//div/div[contains(@id,'Direct Deposit')]")
	public WebElement PaymentType_SearchResultDD;

	// NA-22978
	@FindBy(how = How.XPATH, using = "//input[@name='title']")
	public WebElement eCoupon_Discounts;
	@FindBy(how = How.XPATH, using = "//input[@id='validFrom']")
	public WebElement eCoupon_ValidFrom;
	@FindBy(how = How.XPATH, using = "//input[@id='stackableId']")
	public WebElement eCoupon_Stackable;
	@FindBy(how = How.XPATH, using = "//input[@id='idECouponUpperLimit']")
	public WebElement eCoupon_Count;
	@FindBy(how = How.XPATH, using = "//*[@id='idCartCoupon']")
	public WebElement eCoupon_cart_Checkout;
	@FindBy(how = How.XPATH, using = "//div[@id='s2id_material']/a/span[contains(.,'Select Material')]")
	public WebElement eCoupon_material;
	@FindBy(how = How.XPATH, using = "//div[@id='select2-drop']/div/input")
	public WebElement eCoupon_material_search;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'addRuleToGroup')]")
	public WebElement eCoupon_addPriceRuleToGroup;
	@FindBy(how = How.XPATH, using = "//*[@id='dynamic_div']/a")
	public WebElement eCoupon_Dynamic;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'createGroup')]")
	public WebElement eCoupon_createGroup;

	// NA-17696
	@FindBy(how = How.XPATH, using = "//input[@value='false'][contains(@id,'enableResellerId')]")
	public WebElement B2BUnit_disableQuoteResId;

	// 23046
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'organizer.editor.save.title')]")
	public WebElement SaveButton;

	// 17689
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/McSearchListConfigurable[Product]_innertable']/tbody/tr[3]/td[3]")
	public WebElement products_resultItem;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'ProductOptionGroupItem.unit')]")
	public WebElement Products_groupUnit;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'AutocompleteReferenceEditor[in Attribute[ProductOptionGroupItem.unit]]')]")
	public WebElement Products_searchedUnit;
	@FindBy(how = How.ID, using = "showSearchUnitDialog")
	public WebElement batchAssignOption_searchUnit;
	@FindBy(how = How.ID, using = "unitCodeInput")
	public WebElement batchAssignOption_unitCodeInput;
	@FindBy(how = How.ID, using = "searchUnitBtn2")
	public WebElement batchAssignOption_searchUnitBtn2;
	@FindBy(how = How.ID, using = "selectUnitBtn")
	public WebElement batchAssignOption_selectUnitBtn;

	// NA-17455
	@FindBy(how = How.XPATH, using = "//table[@title='enableProductBuilder']//div/input[contains(@id,'true')]")
	public WebElement B2C_SiteAttribute_EnableProductBuilder;

	// NA-21786
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'CMSSite.uid')]")
	public WebElement WCMS_Website_ID;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'CMSSite.administration')]")
	public WebElement WCMS_Website_Administration;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'CMSSite.quantityLimit')]")
	public WebElement WCMS_Website_MaximumLineItemQuantity;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'organizer.editor.save.title')]")
	public WebElement WCMS_Website_Save;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.asmCartQuantity')]")
	public WebElement B2CUnit_SiteAttribute_ASM_Cart_Quantity;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.totalCartQuantity')]")
	public WebElement B2CUnit_SiteAttribute_ASM_Total_Cart_Quantity;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.lineItemQuantity')]")
	public WebElement B2CUnit_SiteAttribute_ASM_Line_Item_Quantity;

	@FindBy(how = How.XPATH, using = "//*[contains(@id,'PaymentTypeProfile.disablePayment4Customer')][@value='false']")
	public WebElement paymentProfile_disableForCustomerNo;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement WCMS_Website_SearchButton;
	// 20136
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[Installments]_label")
	public WebElement Nemo_Installments;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[Installments]_!create_Installments_label")
	public WebElement NemoInstallments_CreateInstallments;
	@FindBy(how = How.XPATH, using = "//div/table[@class='stringLayoutChip']/tbody/tr/td/input[contains(@id,'code')]")
	public WebElement NemoInstallments_AttributeCode;
	@FindBy(how = How.XPATH, using = "//div/table[@class='stringLayoutChip']/tbody/tr/td/input[contains(@id,'number')]")
	public WebElement NemoInstallments_AttributeNumber;
	@FindBy(how = How.XPATH, using = "//div/table[@class='stringLayoutChip']/tbody/tr/td/input[contains(@id,'serialNumber')]")
	public WebElement NemoInstallments_AttributeSerialNumber;
	@FindBy(how = How.XPATH, using = "//div/table[@class='stringLayoutChip']/tbody/tr/td/input[contains(@id,'installComments')]")
	public WebElement NemoInstallments_AttributeInstallComments;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'saveandcreate')]")
	public WebElement Nemo_InstallmentsCreate;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[Installments.code]]_input")
	public WebElement NemoInstallments_Code;

	@FindBy(how = How.ID, using = "Content/NemoSearchConfigurable[Installments]_searchbutton")
	public WebElement NemoInstallments_SearchButton;
	@FindBy(how = How.ID, using = "Content/McSearchListConfigurable[Installments][delete]_img")
	public WebElement NemoINstallments_DeleteAllResult;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[CreditCardInstallmentSetting]_label")
	public WebElement Nemo_CreditCardInstallmentSetting;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[CreditCardInstallmentSetting]_!create_CreditCardInstallmentSetting_label")
	public WebElement Nemo_CreditCardInstallmentSetting_Create;
	@FindBy(how = How.XPATH, using = "//div/table[@class='enumerationValueSelectEditorChip']/tbody/tr/td/select[contains(@id,'cardType')]")
	public WebElement Nemo_CreditCardInstallmentSetting_CardType;
	@FindBy(how = How.XPATH, using = "//div[@class='booleanEditorChip']/span[contains(text(),'Yes')]")
	public WebElement Nemo_CreditCardInstallmentSetting_DisableShowPriceYes;
	@FindBy(how = How.XPATH, using = "//div[@class='booleanEditorChip']/span[contains(text(),'No')]")
	public WebElement Nemo_CreditCardInstallmentSetting_DisableShowPriceNo;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'[code]')]")
	public WebElement Nemo_CreditCardInstallmentSetting_Code;
	@FindBy(how = How.XPATH, using = "//div[@class='dropdown-main']/table/tbody/tr/td[contains(text(),'Add [Installments]')]")
	public WebElement Nemo_CreditCardInstallmentSetting_AddInstallments;
	@FindBy(how = How.XPATH, using = "//table[@class='stringLayoutChip']/tbody/tr/td/input[contains(@id,'Installments.code')]")
	public WebElement CreditCardInstallmentSetting_InstallmentsCode;
	@FindBy(how = How.XPATH, using = "//div[@class='xp-button chip-event']/a/span[contains(@id,'searchbutton')]")
	public WebElement Installments_SearchButton;
	@FindBy(how = How.XPATH, using = "//div[@class='xp-button chip-event']/a/span[contains(@id,'use')]")
	public WebElement Installments_Use;

	@FindBy(how = How.ID, using = "Content/OrganizerCreatorSaveAndCopyToolbarAction[saveandcreate]_img")
	public WebElement Installments_Create;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2CUnit.paymentByInstallmentDisplay]]_true")
	public WebElement B2CUnit_SiteAttributeTab_PaymentByInstallmentYes;
	@FindBy(how = How.XPATH, using = "//table[@title='creditCardinstallments']/tbody/tr/td/div/table/tbody/tr/td/div/table/tbody/tr/td[3]/div/div")
	public WebElement B2CUnit_SA_ListCreditCardinstallmCodeName;
	// 18085
	@FindBy(how = How.XPATH, using = ".//*[@id='resultlist_Content/GenericAtomicTypeList[1]']/table//tr[2]/td[2]/div")
	public WebElement Home_Order_TaxValue;

	// 21842
	@FindBy(how = How.XPATH, using = "//input[@name='title']")
	public WebElement createNewGroup_title;
	@FindBy(how = How.XPATH, using = "//select[@id='countryId']/../div/a/span[@class='select2-chosen']")
	public WebElement createNewGroup_country_select;
	@FindBy(how = How.XPATH, using = "//div[@id='select2-drop']/div[@class='select2-search']/input")
	public WebElement createNewGroup_country_select_input;
	@FindBy(how = How.XPATH, using = "//div[@id='s2id_delegationRole']/a/span[@class='select2-chosen']")
	public WebElement createNewGroup_Delegation_Role;
	@FindBy(how = How.XPATH, using = "//div[@id='select2-drop']/div[@class='select2-search']/input")
	public WebElement createNewGroup_Delegation_Role_input;

	// 23036
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Web Prices')]")
	public WebElement B2CPriceRules_WebPriceOption;
	@FindBy(how = How.XPATH, using = "//span[text()='Select Base Product...']")
	public WebElement B2CPriceRules_BaseProduceSelect;
	@FindBy(how = How.XPATH, using = ".//*[@id='s2id_baseProduct']/a/abbr")
	public WebElement B2CPriceRules_RemoveBaseProduct;
	@FindBy(how = How.XPATH, using = "//div/button[contains(text(),'Add Restriction to Group')]")
	public WebElement B2CPriceRules_AddRestrictionToGroup;

	// 17126
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Catalog')]")
	public WebElement HMCHome_Catalog;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/GenericExplorerMenuTreeNodeContentChip$TreeIcon[Products]_div']")
	public WebElement HMCCatalog_products;
	@FindBy(how = How.XPATH, using = "//*[@class='listtable']/tbody/tr[2]/td[4]/div/div/table/tbody/tr/td/input")
	public WebElement HMCB2CUnitSearch_IdValue;
	@FindBy(how = How.XPATH, using = "//table[@class = 'allInstancesSelectEditorChip']//select")
	public WebElement HMCCatalog_catalogVersion;
	@FindBy(how = How.XPATH, using = "//table[@class = 'listtable']//select/option[contains(.,'masterMultiCountryProductCatalog - Online')]")
	public WebElement HMCCatalog_masterMultiCountryProductCatalog;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Search')]")
	public WebElement HMCBaseCommerce_searchButton;
	@FindBy(how = How.XPATH, using = "(//table[@class='listtable selecttable']/descendant::td[@class='sorted'])[1]")
	public WebElement HMCBaseCommerce_searchResult;
	@FindBy(how = How.XPATH, using = "//table[@title='baseProduct']//input[contains(@id,'baseProduct')]")
	public WebElement HMCCatalog_getMT;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/StaticContentLeaf[1]_label']")
	public WebElement HMCHome_rediscacheclean;
	@FindBy(how = How.XPATH, using = "//input[@id='ps_product']")
	public WebElement HMCHome_productCode;
	@FindBy(how = How.XPATH, using = "//input[@id='ps_button']")
	public WebElement HMCHome_clean;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericExplorerMenuTreeNode[group.basecommerce]_label']")
	public WebElement HMCBaseCommerce_baseCommerce;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[BaseStore]_label']")
	public WebElement HMCBaseCommerce_baseStore;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Properties')]")
	public WebElement HMCB2CUnitSearch_properties;

	@FindBy(how = How.XPATH, using = "(//table[@title='warehouses']//tbody[contains(@id,'ItemList')])//td[3]/div/div")
	public WebElement HMCBaseCommerce_propertiesWareHouse;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[StockLevel]_label']")
	public WebElement HMCBaseCommerce_stockLevel;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/GenericCondition[StockLevel.productCode]]_input']")
	public WebElement HMCBaseCommerce_productCode;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'The search was finished. No results were found')]")
	public WebElement HMCBaseCommerce_noResultText;
	@FindBy(how = How.XPATH, using = ".//*[@id='resultlist_Content/McSearchListConfigurable[StockLevel]']")
	public WebElement HMCBaseCommerce_stockLevelResult;
	@FindBy(how = How.XPATH, using = "//td[text()='Remove']")
	public WebElement HMCB2CUnitSiteAttributes_Remove;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[StockLevel]_!create_StockLevel_label']")
	public WebElement HMCBaseCommerce_createStockLevel;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/Attribute[.productCode]]_input']")
	public WebElement HMCBaseCommerce_partNumber;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Warehouse:')]/../../td/div/table/tbody/tr/td[2]")
	public WebElement HMCBaseCommerce_wareHouse;
	@FindBy(how = How.XPATH, using = "(//table/tbody/tr/td/input)[1]")
	public WebElement HMCBaseCommerce_wareHouseCode;
	@FindBy(how = How.XPATH, using = "//table[@class='listtable selecttable singleselect']/tbody/tr[2]")
	public WebElement HMCB2CUnitSiteAttributes_SettingValue;
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Use')]")
	public WebElement HMCB2CUnitSiteAttributes_SettingUse;

	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[.inStockStatus]]_select']")
	public WebElement HMCBaseCommerce_inStockStatus;
	@FindBy(how = How.XPATH, using = "//option[contains(.,'forceInStock')]")
	public WebElement HMCBaseCommerce_stockStatusIn;
	@FindBy(how = How.XPATH, using = "//option[contains(.,'forceOutOfStock')]")
	public WebElement HMCBaseCommerce_stockStatusOut;
	@FindBy(how = How.XPATH, using = "(//td/div[@class='label'])[3]")
	public WebElement HMCB2CUnitSiteAttributes_Save;
	@FindBy(how = How.XPATH, using = "//td/a[contains(@id,'[system]')]")
	public WebElement HMCHome_system;
	@FindBy(how = How.XPATH, using = "//*[@class ='tb-button-blue chip-event']//div[contains(text(),'Delete')]")
	public WebElement HMCB2CUnitSiteAttributes_deleteBtn;

	// NA-21842
	@FindBy(how = How.XPATH, using = ".//a[contains(@class,'createNewGroup')]/b")
	public WebElement B2CPriceRules_CreateNewGroup1;

	// NA-23892
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Cart.user')]")
	public WebElement marketing_orderStatistics_carts_user;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Cart.store')]")
	public WebElement marketing_orderStatistics_carts_Store;

	@FindBy(how = How.XPATH, using = "//span/img[contains(@id,'Cart.user')]")
	public WebElement marketing_orderStatistics_carts_user_icon;
	@FindBy(how = How.XPATH, using = "//span/img[contains(@id,'Cart.store')]")
	public WebElement marketing_orderStatistics_carts_Store_icon;

	// NA25376
	@FindBy(how = How.XPATH, using = ".//*[@id='displayCouponMessageId']")
	public WebElement B2CPriceRules_ecouponMessageDisplay;
	@FindBy(how = How.XPATH, using = "//div/a[contains(.,'Edit Coupon Message')]")
	public WebElement B2CPriceRules_ecouponMessageEdit;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Choose a language')]/../span[2]")
	public WebElement B2CPriceRules_ChooseCountry;
	@FindBy(how = How.XPATH, using = ".//input[@id='tmpMsg']")
	public WebElement B2CPriceRules_ecouponMessageInput;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'addCouponMsg') and contains(.,'Add')]")
	public WebElement B2CPriceRules_ecouponMessageAdd;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'msgSave')]")
	public WebElement B2CPriceRules_ecouponMessageSave;
	@FindBy(how = How.XPATH, using = ".//*[@id='featuredId']")
	public WebElement B2CPriceRules_ecouponFeatured;
	@FindBy(how = How.XPATH, using = "//span[@class='minplus glyphicon glyphicon-minus']/..")
	public WebElement B2CPriceRules_DynamicMinusButton;
	@FindBy(how = How.XPATH, using = "//span[@class='bigger glyphicon glyphicon-usd']/..")
	public WebElement B2CPriceRules_ecouponDollorButton;
	@FindBy(how = How.XPATH, using = "//input[@name='priority']")
	public WebElement B2CPriceRules_ecouponPriority;

	// NA-17710
	@FindBy(how = How.XPATH, using = "//td[text()='Edit']")
	public WebElement products_PB_edit;
	@FindBy(how = How.XPATH, using = "//div[@title='Group Code']")
	public WebElement products_PB_table;
	@FindBy(how = How.XPATH, using = "//div[@title='Code']")
	public WebElement products_PB_optionsTable;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.code')]")
	public WebElement products_PB_code;
	@FindBy(how = How.XPATH, using = "//td[text()='Create new']")
	public WebElement products_PB_createNew;
	@FindBy(how = How.XPATH, using = "//td[text()='Edit in new window']")
	public WebElement products_PB_editInNewWindow;
	@FindBy(how = How.XPATH, using = "//td[text()='Create Group Option Item']")
	public WebElement products_PB_createGroupOptionItem;
	@FindBy(how = How.XPATH, using = "//td[text()='Add Product Option']")
	public WebElement products_PB_addProductOption;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'roductOption.catalogVersion') and contains(@id,'AllInstancesSelectEditor')]")
	public WebElement products_PB_catalogVersionSel;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement products_PB_searchBtn;
	@FindBy(how = How.XPATH, using = ".//span[contains(@id,'use')]")
	public WebElement products_PB_useBtn;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'.save.title')]")
	public WebElement products_PB_saveBtn;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/NemoSearchConfigurable[StockLevel]_searchbutton']")
	public WebElement stockLevel_search;
	// input[contains(@id,'StockLevel.available')]
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'StockLevel.available')]")
	public WebElement stockLevel_availableAmount;

	@FindBy(how = How.XPATH, using = ".//a[contains(@id,'organizer.editor.save.title')]")
	public WebElement stockLevel_save;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/GenericReferenceEditor[in Content/Attribute[BaseStore.defaultDeliveryOrigin]]_div']")
	public WebElement baseStore_DeliveryOrigin;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'OrganizerSearch')]")
	public WebElement stockLevel_searchWareHouse;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'PriceChangeMessage:')]/../..//input")
	public WebElement baseStore_priceChangeMessageInput;

	// NA-18461
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2CUnit.switchAddress]]_true')]")
	public WebElement b2cUnit_switchAddressYes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'B2CUnit.switchAddress]]_false')]")
	public WebElement b2cUnit_switchAddressNo;
	@FindBy(how = How.XPATH, using = "//div[@title='Address Field' and @class='gilcEntry-mandatory']")
	public WebElement b2cUnit_addressFieldAttibutesTable;
	@FindBy(how = How.XPATH, using = "(//td[contains(@id,'create')])[last()]")
	public WebElement b2cUnit_create;
	@FindBy(how = How.XPATH, using = "//select[contains(@name,'_setvalue') and contains(@id,'CreateItemListEntry')]")
	public WebElement b2cUnit_createB2CAddressFieldSel;
	@FindBy(how = How.XPATH, using = "//input[@value='false']/..//input[contains(@id,'CreateItemListEntry')]")
	public List<WebElement> b2cUnit_createB2CAddressFieldUnselectedChk;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'CreateItemListEntry') and @type='text']")
	public WebElement b2cUnit_createB2CAddressFieldInput;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'EditableItemListEntry')]")
	public WebElement b2cUnit_existedAddressField;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'select_visible_true_label')]")
	public WebElement b2cUnit_selectAll;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'remove_true_label')]")
	public WebElement b2cUnit_remove;
	@FindBy(how = How.XPATH, using = "//div[@title='Address Field' and @class='gilcEntry']")
	public WebElement b2cUnit_addressLabelNameTable;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Address Label Name:')]/../..//div[text()='The list is empty.']")
	public WebElement b2cUnit_addressNameEmpty;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Address Field Attributes:')]/../..//div[text()='The list is empty.']")
	public WebElement b2cUnit_addressFieldEmpty;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'.addressField')]")
	public WebElement b2cUnit_labelName_addressField;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Internationalization')]")
	public WebElement hmc_internationalization;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'AddressValidatorRule')]")
	public WebElement hmc_addressValidatorRule;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'firstnameMaxLength')]")
	public WebElement addressValidatorRule_firstnameMaxLength;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'firstnameMinLength')]")
	public WebElement addressValidatorRule_firstnameMinLength;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Address Field Attributes:')]/../..//select")
	public List<WebElement> hmc_addressFieldItems;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Address Label Name:')]/../..//img")
	public List<WebElement> hmc_addressLabelItems;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Address Field Attributes:')]/../..//*[@value='false']/../input[2]")
	public List<WebElement> hmc_addressLabelUnselectChk;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Address Field Attributes:')]/../..//input[contains(@id,'Content/StringEditor')]")
	public List<WebElement> hmc_addressLabelSeq;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Web Prices')]")
	public WebElement B2BPriceRules_WebPrices;
	@FindBy(how = How.XPATH, using = "//input[@name='title']")
	public WebElement webPricesGroup_title;
	@FindBy(how = How.XPATH, using = "//td[@class='day active today']")
	public WebElement webPricesGroup_ValidTo;

	// 18082
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'CV Rules')]")
	public WebElement PricingCockpit_CVRules;
	@FindBy(how = How.XPATH, using = "//a/span[text()='B2C CV Rules']")
	public WebElement PricingCockpit_B2CCVRules;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'CV List Price Override')]")
	public WebElement B2CPriceRules_CVOption;
	@FindBy(how = How.XPATH, using = "//span[text()='Select characteristic...']")
	public WebElement B2CPriceRules_CharacteristicSelect;
	@FindBy(how = How.XPATH, using = "//span[text()='Characteristics Values...']")
	public WebElement B2CPriceRules_CharacteristicValue;
	@FindBy(how = How.XPATH, using = ".//*[@id='cvPrices']/tbody/tr/td[text()='NB_CPU']/../td[2]")
	public WebElement B2CPriceRules_DefaultCPU;
	@FindBy(how = How.XPATH, using = ".//*[@id='cvPrices']/tbody/tr/td[text()='NB_CPU']/../td[7]")
	public WebElement B2CPriceRules_DefaultCPUPriceWithTax;
	@FindBy(how = How.XPATH, using = ".//*[@id='cvPrices']/tbody/tr/td[text()='NB_CPU']/../td[5]")
	public WebElement B2CPriceRules_CPUOverridePrice;

	// 23818
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'.storeType')]")
	public WebElement B2CUnit_storeType;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'.storeType')]//option[@selected]")
	public WebElement B2CUnit_selectedStoreType;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.sMBGatekeeperToggle') and contains(@id,'true')]")
	public WebElement B2CUnit_sMBGatekeeperToggleTrue;
	@FindBy(how = How.ID, using = "y_popupmessage_ok_label")
	public WebElement B2CUnit_popupMessageOK;
	@FindBy(how = How.XPATH, using = "//div[@id='y_popupmessage']//td[@class='text']")
	public WebElement B2CUnit_popupMessageText;

	// 19760
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Aging Inventory Discounts')]")
	public WebElement B2CPriceRules_AgingInventoryDiscountsRule;
	@FindBy(how = How.XPATH, using = ".//*[@id='fix-temp']/div/input")
	public WebElement B2BPriceRules_FixTempPrice;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),' Add Time Period')]")
	public WebElement B2CPriceRules_AddTimePeriod;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']/div[2]/div)[last()]/div/input[@placeholder='Day...']")
	public WebElement B2CPriceRules_Thresholds;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']/div[2]/div)[last()]/div/input[@placeholder='Value...']")
	public WebElement B2CPriceRules_ThresholdsValue;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']/div[2]/div)[last()]/div/div//span[@class='minplus glyphicon glyphicon-minus']/..")
	public WebElement B2CPriceRules_ThresholdsMinusIcon;
	@FindBy(how = How.XPATH, using = "(.//*[@id='complex-temp']/div[2]/div)[last()]/div/div//span[text()='%']/..")
	public WebElement B2CPriceRules_ThresholdsPercentIcon;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EditorTab[StockLevel.administration]_span']")
	public WebElement StockLevel_AdministratorTab;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/DateTimeEditor[in Content/Attribute[StockLevel.introductionTime]]_date']")
	public WebElement StockLevel_AdministratorIntroductionTime;

	// NA-24092
	@FindBy(how = How.ID, using = "Tree/GenericExplorerMenuTreeNode[group.smb]_label")
	public WebElement Home_SMB;
	@FindBy(how = How.ID, using = "Tree/StaticContentLeaf_label")
	public WebElement SMB_QuickSetup;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[NemoSMBCustomerGroup]_label")
	public WebElement SMB_CustomerGroup;
	@FindBy(how = How.XPATH, using = "//select[@id='template']/option[@value='ussmb_unit' or @value='us_smb_unit']")
	public WebElement SMB_QuickSetup_UnitSelection;
	@FindBy(how = How.XPATH, using = "//input[@id='uid']")
	public WebElement SMB_QuickSetup_IdentifyCode;
	@FindBy(how = How.XPATH, using = "//input[@id='name']")
	public WebElement SMB_QuickSetup_Name;
	@FindBy(how = How.XPATH, using = ".//*[@id='selectMedia']")
	public WebElement SMB_QuickSetup_SiteLogo_SelectMedia;
	@FindBy(how = How.XPATH, using = ".//*[@id='s_searchButton']")
	public WebElement SMB_QuickSetup_SelectMedia_Search;
	@FindBy(how = How.XPATH, using = ".//*[@id='datagrid-row-r2-2-0']/td[1]/div")
	public WebElement SMB_QuickSetup_SelectMedia_FirstResult;
	@FindBy(how = How.XPATH, using = ".//*[@id='s_selectButton']")
	public WebElement SMB_QuickSetup_SelectMedia_UseMedia;
	@FindBy(how = How.XPATH, using = "//input[@id='companyName']")
	public WebElement SMB_QuickSetup_CompanyName;
	@FindBy(how = How.XPATH, using = "//input[@id='address1']")
	public WebElement SMB_QuickSetup_CompanyAddress1;
	@FindBy(how = How.XPATH, using = "//input[@id='address2']")
	public WebElement SMB_QuickSetup_CompanyAddress2;
	@FindBy(how = How.XPATH, using = "//input[@id='city']")
	public WebElement SMB_QuickSetup_City;
	@FindBy(how = How.XPATH, using = "//input[@id='zipCode']")
	public WebElement SMB_QuickSetup_Zipcode;
	@FindBy(how = How.XPATH, using = "//input[@id='country']")
	public WebElement SMB_QuickSetup_StateInput;
	@FindBy(how = How.XPATH, using = "//select[@id='state']/option[@value='US-CA']")
	public WebElement SMB_QuickSetup_StateSelect;
	@FindBy(how = How.XPATH, using = "//select[@id='industry']/option[text()='Education']")
	public WebElement SMB_QuickSetup_Industry;
	@FindBy(how = How.XPATH, using = "//select[@id='companySize']/option[text()='500-999 employees']")
	public WebElement SMB_QuickSetup_CompanySize;
	@FindBy(how = How.XPATH, using = "//input[@id='companyYear']")
	public WebElement SMB_QuickSetup_CompanyYear;
	@FindBy(how = How.ID, using = "validateButton")
	public WebElement SMB_QuickSetup_CreateButton;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/GenericCondition[NemoSMBCustomerGroup.uid')]")
	public WebElement SMB_CustomerGroup_IdentifyCode;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/GenericCondition[NemoSMBCompany.zCompanyName')]")
	public WebElement SMB_SMBCompany_CompanyName;
	@FindBy(how = How.ID, using = "Content/NemoSearchConfigurable[NemoSMBCustomerGroup]_searchbutton")
	public WebElement SMB_CustomerGroup_Srarch;
	@FindBy(how = How.ID, using = "Content/OrganizerComponent[organizersearch][NemoSMBCustomerGroup]_togglelabel")
	public WebElement SMB_CustomerGroup_SearchToggle;
	@FindBy(how = How.ID, using = "Content/NemoSearchConfigurable[NemoSMBCompany]_searchbutton")
	public WebElement SMB_SMBCompany_Search;
	@FindBy(how = How.ID, using = "Content/OrganizerComponent[organizersearch][NemoSMBCompany]_togglelabel")
	public WebElement SMB_SMBCompany_SearchToggle;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/OrganizerItemChip$2[organizer.editor.delete')]")
	public WebElement SMB_SMBCompanyAndCustomer_Delete;

	// 23943
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group.smb')]")
	public WebElement Home_smb;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'SMB Group Upload')]")
	public WebElement SMB_smbGroupUpload;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'upload')]")
	public WebElement SMBGroupUpload_upload;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'ModalMediaFileUpload')]")
	public WebElement SMBGroupUpload_folderInput;
	@FindBy(how = How.XPATH, using = "//input[@name='file']")
	public WebElement SMBGroupUpload_selectFile;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'xpBtn_save') and contains(@id,'left')]")
	public WebElement SMBGroupUpload_uploadBtn;
	@FindBy(how = How.XPATH, using = "//a[@title='[update]']")
	public WebElement SMBGroupUpload_update;
	@FindBy(how = How.XPATH, using = "//a[@title='[confirm]']")
	public WebElement SMBGroupUpload_confirm;
	@FindBy(how = How.XPATH, using = "//pre[contains(.,'Import Result:Success')]")
	public WebElement SMBGroupUpload_successResult;
	@FindBy(how = How.XPATH, using = "//a[contains(@title,'Close')]")
	public WebElement SMBGroupUpload_close;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'SMB Customer Group')]")
	public WebElement SMB_SMBCustomerGroup;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'SMB Company')]")
	public WebElement SMB_SMBCompany;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NemoSMBCustomerGroup.uid')]")
	public WebElement SMBCustomerGroup_uid;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'searchbutton')]")
	public WebElement SMB_searchbutton;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.locname')]")
	public WebElement SMB_locname;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCompanyName')]")
	public WebElement SMB_zCompanyName;

	// 17627
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'ItemDisplay[USD]')]")
	public WebElement contract_ContractPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='Tree/GenericLeafNode[SolrIndexerHotUpdateWizard]_label']")
	public WebElement home_Hot_Update;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'PriceRow.price')]")
	public WebElement contract_EditPrice;

	@FindBy(how = How.XPATH, using = "//div[contains(@id,'organizer.editor.save.title')]")
	public WebElement contractPrice_Save;

	@FindBy(how = How.XPATH, using = ".//*[@id='ImageToolbarAction[organizer.editor.save.title]_label']")
	public WebElement contract_Save;

	// 18080

	@FindBy(how = How.XPATH, using = ".//*[@id='table_GenericItemList_table']/tbody/tr[2]/td")
	public WebElement hotUpdate_ItemsToIndex;
	@FindBy(how = How.XPATH, using = ".//*[@id='ItemDisplay[masterMultiCountryProductCatalog - Online]_div']")
	public WebElement hotUpdate_ArticleResultOnline;

	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Start')]")
	public WebElement hotUpdate_StartHotUpdate;
	@FindBy(how = How.XPATH, using = "//input[contains(@placeholder,'Confirm check')]")
	public WebElement pricing_DeletePriceRules;

	// 23914
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_NemoSMBCustomerGroup_label')]")
	public WebElement SMBCustomerGroup_create;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.uid')]")
	public WebElement SMBCustomerGroup_zUid;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.locname')]")
	public WebElement SMBCustomerGroup_locname;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'.tab.groups')]")
	public WebElement SMBCustomerGroup_organisation;
	@FindBy(how = How.XPATH, using = "//table[@title='groups']//input[@class='enabled']")
	public WebElement SMBCustomerGroup_memberOf;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'saveandcreate')]")
	public WebElement SMB_createBtn;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'.save.title')]")
	public WebElement SMB_saveBtn;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_NemoSMBCompany_label')]")
	public WebElement SMBCompany_create;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCompanyName')]")
	public WebElement SMBCompany_zCompanyName;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCustomerGroup')]")
	public WebElement SMBCompany_zCustomerGroup;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCompanyAddress1')]")
	public WebElement SMBCompany_zCompanyAddress1;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCompanyAddress2')]")
	public WebElement SMBCompany_zCompanyAddress2;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCompanyCity')]")
	public WebElement SMBCompany_zCompanyCity;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zZipCode')]")
	public WebElement SMBCompany_zZipCode;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCompanyState')]")
	public WebElement SMBCompany_zCompanyState;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'displayedMajorIndustry')]")
	public WebElement SMBCompany_zMajorIndustry;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'displayedCompanySize')]")
	public WebElement SMBCompany_zCompanySize;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zCompanyYear')]")
	public WebElement SMBCompany_zCompanyYear;

	// 17830
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicShowLanguage') and contains(@value,'true')]")
	public WebElement B2CUnit_SiteAttribute_ShowLanguageYes;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicShowLanguage') and contains(@value,'false')]")
	public WebElement B2CUnit_SiteAttribute_ShowLanguageNo;

	// 21134
	@FindBy(how = How.XPATH, using = "//table[@title='languages']//table")
	public WebElement BaseStore_languagesTable;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'select_visible_true')]")
	public WebElement BaseStore_selectAll;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'remove_true_label')]")
	public WebElement BaseStore_remove;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'add_true_label')]")
	public WebElement HMC_add;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Language.isocode')]")
	public WebElement HMC_isoCode;
	@FindBy(how = How.XPATH, using = "//a[@title='Use']")
	public WebElement HMC_use;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[Language]_label']")
	public WebElement Internationalization_languages;
	@FindBy(how = How.XPATH, using = "//table[@title='fallbackLanguages']//table")
	public WebElement Languages_fallbackLanguages;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[ContentSlot]_label']")
	public WebElement WCMS_contentSlot;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'ContentSlot.uid')]")
	public WebElement ContentSlot_uid;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'meaContentCatalog') and contains(@id,'Online')]")
	public WebElement HMC_searchResultOnlie;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.navigationNode')]")
	public WebElement ContentSlot_navigationNode;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'open_editor_internal_true_label')]")
	public WebElement HMC_internalEdit;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'CMSNavigationNode.title')]")
	public WebElement CMSNavigationNode_title;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'CMSNavigationNode.title')]")
	public List<WebElement> CMSNavigationNode_titleInput;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en')]/../..//input[contains(@id,'CMSNavigationNode.title')]")
	public WebElement CMSNavigationNode_titleInputEN;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en_AE')]/../..//input[contains(@id,'CMSNavigationNode.title')]")
	public WebElement CMSNavigationNode_titleInputEN_AE;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en_GB')]/../..//input[contains(@id,'CMSNavigationNode.title')]")
	public WebElement CMSNavigationNode_titleInputEN_GB;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[Category]_label']")
	public WebElement Home_category;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Category.code')]")
	public WebElement Category_code;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'catalogVersion') and contains(@id,'AllInstancesSelectEditor')]")
	public WebElement Category_catalogVersion;
	@FindBy(how = How.XPATH, using = "(//a[@title='Search'])[2]")
	public WebElement Category_search;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'McCategory.name')]")
	public WebElement Category_name;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'.pmi')]")
	public WebElement Category_pmi;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'.mkt_tagline')]")
	public WebElement Category_mkt_tagline;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.mkt_tagline')]")
	public List<WebElement> Category_mkt_taglineInput;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en')]/../..//input[contains(@id,'mkt_tagline')]")
	public WebElement Category_mkt_taglineInputEN;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en_GB')]/../..//input[contains(@id,'mkt_tagline')]")
	public WebElement Category_mkt_taglineInputEN_GB;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en_AE')]/../..//input[contains(@id,'mkt_tagline')]")
	public WebElement Category_mkt_taglineInputEN_AE;

	@FindBy(how = How.XPATH, using = "//table[@title='languages']//div[contains(text(),'The list is empty.')]")
	public WebElement BaseStore_languagesEmptyMsg;

	// 16394
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicPasscodeGatekeeper']//table[@class='genericAtomicTypeListChip']")
	public WebElement B2CUnit_passcodeGatekeeperTable;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'select_visible_true_label')]")
	public WebElement GateKeeper_selectAll;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicPasscodeGatekeeper']//div[contains(text(),'The list is empty.')]")
	public WebElement GateKeeper_listEmptyMsg;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicPasscodeGatekeeperToggle') and contains(@id,'true')]")
	public WebElement B2CUnit_passcodeGatekeeperYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicPasscodeGatekeeperToggle') and contains(@id,'false')]")
	public WebElement B2CUnit_passcodeGatekeeperNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicPasscodeGatekeeperToggle') and contains(@id,'null')]")
	public WebElement B2CUnit_passcodeGatekeeperNa;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicRegistrationGatekeeperToggle') and contains(@id,'false')]")
	public WebElement B2CUnit_registrationGatekeeperNo;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicSerialNumberGatekeeperToggle') and contains(@id,'false')]")
	public WebElement B2CUnit_serialNumberGatekeeperNo;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicPasscodeGatekeeper']//input[@type='text'and@value='']")
	public WebElement PasscodeGatekeeper_newPasscode;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicPasscodeGatekeeper']//input[@type='text'and@value!=''and@value!='true']")
	public List<WebElement> PasscodeGatekeeper_oldPasscode;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicPasscodeGatekeeperToggle']//table[@class='attributeChip']//td")
	public WebElement PasscodeGatekeeper_toggleInheritedInfo;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicPasscodeGatekeeper']//table[@class='attributeChip']//td")
	public WebElement PasscodeGatekeeper_tableInheritedInfo;

	// 24295
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select SMB Price Tier')]")
	public WebElement B2BpriceSimulate_SMBPriceTierButton;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select Tiers...')]")
	public WebElement B2BpriceSimulate_SMBPriceTierSelect;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Pricing Tier')]")
	public WebElement PricingCockpit_PriceTierRules;
	@FindBy(how = How.XPATH, using = ".//*[@id='tiersTables']/a")
	public WebElement B2CPriceRules_CreateNewTier;
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enter name...']")
	public WebElement B2BPriceRules_PriceTierName;
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enter description...']")
	public WebElement B2BPriceRules_PriceTierDescription;
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enter min...']")
	public WebElement B2BPriceRules_PriceTierMin;
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enter max...']")
	public WebElement B2BPriceRules_PriceTierMax;
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Enter day...']")
	public WebElement B2BPriceRules_PriceTierDay;
	@FindBy(how = How.XPATH, using = "//a/span[contains(text(),'Select Store Unit...')]")
	public WebElement B2BPriceRules_PriceTierStoreUnit;
	@FindBy(how = How.XPATH, using = "//button[contains(.,'Save') and contains(@class,'Tier')]")
	public WebElement B2BPriceRules_PriceTierSave;
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-danger save-button']")
	public WebElement B2BPriceRules_PriceRuleSave;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'CustomerGroupsId')]/ul")
	public WebElement B2BPriceRules_PriceTierGroup;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'CustomerGroupsId')]/ul/li/input")
	public WebElement B2BPriceRules_PriceTierInput;

	// 21905
	// 21905
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_PaymentTypeProfile') and contains(@class,'name')]")
	public WebElement createPaymentProfile;

	// 17612
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Hide Facet')]")
	public WebElement HideFacet;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.hideFacetNav]]_spantrue']")
	public WebElement Radio1;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.hideFacetNav]]_spanfalse']")
	public WebElement Radio2;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.hideFacetNav]]_spannull']")
	public WebElement Radio3;
	@FindBy(how = How.XPATH, using = "//div/h2/span[@class='text long-label']")
	public WebElement NarrowResults;
	@FindBy(how = How.XPATH, using = "(//table[@class='tree']/tbody/tr/td/a)[3]")
	public WebElement B2CUnit_SecondSearchResultItem;
	@FindBy(how = How.XPATH, using = "//table[@class='tree']//div[@class='icon plus']")
	public WebElement B2CUnit_SecondSearchResultItem_icon;

	//ACCT584
	@FindBy(how= How.XPATH, using = "(//table[@class='tree']/tbody/tr/td/a)")
	public WebElement B2CUnit_SearchResultItem;


	// 17627
	@FindBy(how = How.XPATH, using = "(//table[@class='allInstancesSelectEditorChip']//select/option[@value=-1])[1]")
	public WebElement Products_region;
	@FindBy(how = How.XPATH, using = "(//table[@class='allInstancesSelectEditorChip']//select/option[@value=-1])[2]")
	public WebElement Products_country;
	@FindBy(how = How.XPATH, using = "(//div[@class='gilcEntry']//input[contains(@id,'Content/BooleanEditor')])[1]")
	public WebElement Products_checkbox;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[Product.catalogVersion]]_select']")
	public WebElement CatalogVersion;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[NemoMachineTypeVariantProduct.showEOL]]_spantrue']")
	public WebElement Catalog_yes;
	@FindBy(how = How.XPATH, using = "//table[@class='enumerationValueSelectEditorChip']//select/option[contains(.,'alternative order unit')]")
	public WebElement Products_Typecontent;
	@FindBy(how = How.XPATH, using = "(//div[@class=\"gilcEntry\"]//input[contains(@id,'Content/BooleanEditor')])[2]")
	public WebElement Products_Active;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[NemoMachineTypeVariantProduct.showEOL]]_spanfalse']")
	public WebElement Catalog_no;
	@FindBy(how = How.XPATH, using = "//table[@class='enumerationValueSelectEditorChip']//select/option[contains(.,'RECOMMENDED')]")
	public WebElement Products_Typecontent_back;

	// 17628
	@FindBy(how = How.XPATH, using = "//*[@id=\"table_Content/GenericItemList[17]_table\"]//tr[2]")
	public WebElement Pmi_boolean;
	@FindBy(how = How.XPATH, using = "(//td[@id='Content/GenericItemList[17]_!create_PmiBooleanAttributeOverrideRules_label'])[1]")
	public WebElement Pmi_add;
	@FindBy(how = How.XPATH, using = "//*[@id='AttributeOverrideSelectEditor[in Attribute[.descriptorAttribute]]_select']/option[2]")
	public WebElement Pmi_attritube;
	@FindBy(how = How.XPATH, using = "//span[@id='BooleanEditor[in Attribute[.booleanValue]]_spantrue']")
	public WebElement Pmi_yes;
	@FindBy(how = How.XPATH, using = "//*[@id=\"AllInstancesSelectEditor[in Attribute[.country]]_select\"]/option[13]")
	public WebElement Pmi_country;
	@FindBy(how = How.XPATH, using = "//div[@id='ImageToolbarAction[organizer.editor.save.title]_label']")
	public WebElement Pmi_save;
	@FindBy(how = How.XPATH, using = "//span[@id='BooleanEditor[in Attribute[.active]]_spantrue']")
	public WebElement Pmi_active_true;

	// 17360
	@FindBy(how = How.XPATH, using = "//select[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[AbstractPage.catalogVersion]]_select']/option[contains(.,'auContentCatalog - Online')]")
	public WebElement Wcms_category;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Restrictions')]")
	public WebElement Wcms_restrictions;
	@FindBy(how = How.XPATH, using = "(//*[@id='Content/GenericResortableItemList_tbody'])[1]")
	public WebElement Wcms_page_restrictions;
	@FindBy(how = How.XPATH, using = "//table[@id='table_GenericItemList[2]_table']//tr[2]/td[3]/div/div")
	public WebElement Wcms_page_parnum;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[AbstractPage.uid]]_input']")
	public WebElement Wcms_page_id;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/OrganizerListEntry[Long Scroll Product Details Page][search 2]_tr']")
	public WebElement Wcms_page_searchresult;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[AbstractPage.catalogVersion]]_select']/option[contains(.,'masterContentCatalog - Online')]")
	public WebElement Wcms_page_master;
	@FindBy(how = How.XPATH, using = "//a[@id='Content/OrganizerComponent[organizersearch][AbstractPage]_togglelabel']")
	public WebElement Wcms_page_serach;

	// 17664
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Content/BooleanEditor[in Content/Attribute[B2CUnit.enableQas') and contains(@id,'true')]")
	public WebElement B2CUnit_EnableQAS;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.enableQasNew') and contains(@id,'true')]")
	public WebElement B2CUnit_EnableQASNew;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.enableQasNew') and contains(@id,'false')]")
	public WebElement B2CUnit_EnableQASNewNo;

	// change status 17361
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[NemoSubSeriesProduct.productStatus]]_select']//option[1]")
	public WebElement Product_none;

	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'Content/StringEditor[in Content/GenericCondition[Media.code]]_input')]")
	public WebElement Multimedia_Media_Identifier;

	// 18842
	@FindBy(how = How.XPATH, using = "//*[@id='Content/StringEditor[in Content/GenericCondition[Media.code]]_input']")
	public WebElement Media_id;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/NemoSearchConfigurable[Media]_searchbutton']")
	public WebElement Media_search;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[Media.catalogVersion]]_select']/option[contains(.,'auContentCatalog - Online')]")
	public WebElement Media_categoryversion;
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[Media]_innertable']//tr[3]")
	public WebElement Media_search_result;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/MediaFileUploadEditor_preview_div']")
	public WebElement Media_preview;
	@FindBy(how = How.XPATH, using = "//body/pre")
	public WebElement Media_pre;

	// NA-18055
	@FindBy(how = How.XPATH, using = ".//table[@title='enableEmailCartOnWeb']//input[@value='true']")
	public WebElement siteAttribute_emailCartToggleYes;
	@FindBy(how = How.XPATH, using = ".//table[@title='enbaleEmailCartInput']//input[@value='true']")
	public WebElement siteAttribute_emailCartInputToggleYes;
	// NA-27834
	@FindBy(how = How.XPATH, using = ".//table[@title='enableGuestEmailCart']//input[@value='true']")
	public WebElement siteAttribute_emailCartGuestToggleYes;

	// 18402
	@FindBy(how = How.XPATH, using = "//*[@id='Content/OrganizerListEntry[Frequently Asked Questions FAQ Page][search 2]_tr']")
	public WebElement Wcms_faq;
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and @title='cssJsOverridenComponents']//td[2]/div")
	public WebElement Wcms_css;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/GenericResortableItemList[5]_tbody']/tr/td/div")
	public WebElement Wcms_csslist;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/GenericResortableItemList[5]_tbody']/tr[1]")
	public WebElement Wcms_csslist1;
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and@title='Content']//td[2]/div")
	public WebElement Wcms_cssbody;
	@FindBy(how = How.XPATH, using = "//textarea[@id='TextAreaEditor[in Attribute[CssJsOverridePageComponent.Content]]_textarea']")
	public WebElement Wcms_cssbody_copy;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[SimpleCMSComponent]_label']")
	public WebElement Wcms_component;
	@FindBy(how = How.XPATH, using = "//td[@id='Tree/GenericLeafNode[SimpleCMSComponent]_!create_CssJsOverridePageComponent_label']")
	public WebElement Wcms_cssjsCreate;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/Attribute[.uid]]_input']")
	public WebElement Wcms_id;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/Attribute[.name]]_input']")
	public WebElement Wcms_name;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/AllInstancesSelectEditor[in Content/Attribute[.catalogVersion]]_select']/option[contains(.,'auContentCatalog - Online')]")
	public WebElement Wcms_category_version;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[.OverrideType]]_select']/option[contains(.,'CSS')]")
	public WebElement Wcms_css_change;
	@FindBy(how = How.XPATH, using = "//textarea[@id='Content/TextAreaEditor[in Content/Attribute[.Content]]_textarea']")
	public WebElement Wcms_body;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[.OverridePosition]]_select']/option[contains(.,'Head')]")
	public WebElement Wcms_position;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/OrganizerCreatorSaveAndCopyToolbarAction[saveandcreate][1]_label']")
	public WebElement Wcms_create;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/GenericResortableItemList[5]_!add_true_label']")
	public WebElement Wcms_create_js;
	@FindBy(how = How.XPATH, using = "//input[@id='StringEditor[in GenericCondition[CssJsOverridePageComponent.uid]]_input']")
	public WebElement Wcms_create_jsid;
	@FindBy(how = How.XPATH, using = "//span[@id='ModalGenericFlexibleSearchOrganizerSearch[CssJsOverridePageComponent]_searchbutton']")
	public WebElement Wcms_create_jssearch;
	@FindBy(how = How.XPATH, using = "//div[@id='StringDisplay[autotest-18402]_span']")
	public WebElement Wcms_create_jssuse;
	@FindBy(how = How.XPATH, using = "//html")
	public WebElement Faq_check;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Administration')]")
	public WebElement Product_admin;
	@FindBy(how = How.XPATH, using = "//td[@id='Tree/GenericLeafNode[SimpleCMSComponent]_null_null_label']")
	public WebElement Component_create;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/OrganizerCreatorSaveAndCopyToolbarAction[saveandcreate]_label']")
	public WebElement Component_save;
	@FindBy(how = How.XPATH, using = "//tbody[@id='Content/GenericResortableItemList[5]_tbody']//td[@class='disabled']/div")
	public WebElement Component_emptyjscss;

	// 25381
	@FindBy(how = How.XPATH, using = ".//tr[@title='Batch Assign By MT']")
	public WebElement ProductBuidler_batchAssignByMT;
	@FindBy(how = How.ID, using = "showSelectMachineTypeDialog")
	public WebElement ProductBuidler_selectMachineType;
	@FindBy(how = How.ID, using = "machineTypeCodeInput")
	public WebElement ProductBuidler_machineTypeCodeInput;
	@FindBy(how = How.ID, using = "searchMachineTypeBtn2")
	public WebElement ProductBuidler_searchMachineTypeBtn2;
	@FindBy(how = How.ID, using = "selectMachineTypeBtn")
	public WebElement ProductBuidler_selectMachineTypeBtn;
	@FindBy(how = How.ID, using = "channelCode")
	public WebElement ProductBuidler_channelCode;
	@FindBy(how = How.ID, using = "loadingDiv")
	public WebElement ProductBuidler_loadingDiv;
	@FindBy(how = How.XPATH, using = "//div[@class='gilcEntry']//td")
	public List<WebElement> ProductBuidler_groupItemsDetail;
	@FindBy(how = How.XPATH, using = "//div[@class='gilcEntry' and contains(@id,'ProductOptionsDisplay')]")
	public List<WebElement> ProductBuidler_groupItems;

	// 17665
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'PromotedProductOptions')]")
	public WebElement ProductBuidler_promotedProductOptions;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'name')]")
	public WebElement ProductBuidler_proOptName;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'_searchbutton')]")
	public WebElement ProductBuidler_searchBtn;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'.catalogVersion')]")
	public WebElement ProductBuidler_catalogVersionSel;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'delete.title')]")
	public WebElement ProductBuidler_deleteBtn;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'save.title')]")
	public WebElement ProductBuidler_saveBtn;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_PromotedProductOptions')]")
	public WebElement ProductBuidler_createProOpt;
	@FindBy(how = How.XPATH, using = "//table[@title='options']//table[@class='listtable selecttable']//*[@title='Code']")
	public WebElement ProductBuidler_optionsTable;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'ProductBuilderContainer.promotedOptions')]")
	public WebElement Product_promotedOptions;

	// 18380
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and @title='notifyMeToggle']//td[2]/div")
	public WebElement NotifyMeToggle;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.notifyMeToggle]]_spantrue']")
	public WebElement NotifyMeToggle_yes;
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and @title='notifyMe']//td[2]/div")
	public WebElement Admin_notify;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[NemoSubSeriesProduct.notifyMe]]_spantrue']")
	public WebElement Admin_yes;
	@FindBy(how = How.XPATH, using = "//option[contains(.,'ComingSoon')]")
	public WebElement Catalog_ProductStatus_ComingSoon;
	@FindBy(how = How.XPATH, using = "//table[@title='productStatus']//select/option[1]")
	public WebElement Catalog_ProductStatus_empty;

	// 17691
	@FindBy(how = How.XPATH, using = "//td[@id='sales']/samp[@id='value']")
	public WebElement B2BpriceSimulate_salesPrice;
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary updateGroup']")
	public WebElement B2CPriceRules_EditRuleSaveButton;
	@FindBy(how = How.XPATH, using = "//div[@class='dynamic-value']/input")
	public WebElement B2CPriceRules_EditRuleDynamicValue;

	// 17748
	@FindBy(how = How.XPATH, using = "//*[@id='Content/McSearchListConfigurable[Product]_innertable']/tbody/tr[3]/td[4]")
	public WebElement Catalog_partnbum;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/McSearchListConfigurable[Product]_innertable']/tbody/tr[3]/td[5]")
	public WebElement Catalog_identifler;

	// 21734
	@FindBy(how = How.XPATH, using = "//div[@id='s2id_currency']/a")
	public WebElement B2BpriceSimulate_CurrencyButton;
	@FindBy(how = How.XPATH, using = "//input[@id='stackableId']")
	public WebElement B2CPriceRules_StackableCheckBox;
	@FindBy(how = How.XPATH, using = "//input[@id='idCartCoupon']")
	public WebElement B2CPriceRules_NumberOfCartCheckedout;
	@FindBy(how = How.XPATH, using = "//td[@id='promo']/samp[@id='value']")
	public WebElement B2BpriceSimulate_promoPrice;

	// 16411
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'storeType')]//option[@selected]")
	public WebElement SiteAttribute_storeTypeOption;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'code')]")
	public WebElement SiteAttribute_codeInput;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'registrationGatekeeperToggle') and contains(@id,'true')]")
	public WebElement SiteAttribute_registrationGatekeeperToggleYes;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'currencies')]")
	public WebElement B2BUnit_currenciesTab;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'defaultCurrency')]")
	public WebElement B2BUnit_defaultCurrency;

	// 18078
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'warehouse')]")
	public WebElement StockLevel_warehouseInput;

	// 24530
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2BUnit.AcslProjectId') and contains(@id,'input')]")
	public WebElement AcslProjectId;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'fulfillmentType')]")
	public WebElement fulfillmentType;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'fulfillmentType')]/option[contains(text(),'CRM')]")
	public WebElement fulfillmentType_CRM;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'fulfillmentType')]/option[contains(text(),'ACSL')]")
	public WebElement fulfillmentType_ACSL;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'fulfillmentType')]/option[contains(text(),'Email')]")
	public WebElement fulfillmentType_EMAIL;

	// 16863
	@FindBy(how = How.XPATH, using = "//td[@class='savings']//*[@id='container']//i")
	public WebElement B2CPriceSimulator_instantGroup;
	@FindBy(how = How.XPATH, using = "//td[@class='promo']//*[@id='container']//i")
	public WebElement B2CPriceSimulator_promoGroup;

	// 18078
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'StockLevel.warehouse')]")
	public WebElement WarehouseIcon;
	// 19418
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[CustomTab]_label']")
	public WebElement Nemo_customtab;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'[CustomTab]_select')]/option[@value='name']")
	public WebElement CustomTab_attributeselectname;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'[CustomTab.name]]_input')]")
	public WebElement CustomTab_nameinput;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'[CustomTab]_searchbutton')]")
	public WebElement CustomTab_search;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'No results were found')]")
	public WebElement CustomTab_noresult;
	@FindBy(how = How.XPATH, using = "//table[contains(@id,'SearchListConfigurable[CustomTab]_innertable')]//a[contains(@title,'Open Edit')]")
	public WebElement CustomTab_resultEdit;
	@FindBy(how = How.XPATH, using = "//td[@id='Tree/GenericLeafNode[CustomTab]_!create_CustomTab_label']")
	public WebElement Nemo_customtab_label;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/PKEditor[in Content/GenericCondition[CustomTab.pk]]_input']")
	public WebElement Nemo_customtab_pk;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/NemoSearchConfigurable[CustomTab]_searchbutton']")
	public WebElement Nemo_customtab_search;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.active]]_checkbox')]")
	public WebElement Nemo_customtab_checkbox;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.buyfromresellerflag]]_checkbox')]")
	public WebElement Nemo_customtab_flag;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'LocalizableAttribute[.name]]_input')]")
	public WebElement Nemo_customtab_name;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'LocalizableAttribute[.description]]_input')]")
	public WebElement Nemo_customtab_description;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/OrganizerCreatorSaveAndCopyToolbarAction[saveandcreate]_label']")
	public WebElement Nemo_customtab_create;
	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'masterMultiCountryProductCatalog - Online')])[1]")
	public WebElement Nemo_customtab_result;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/StringDisplay[BuyFromResellerTab]_span']")
	public WebElement Nemo_pmi_show;
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and @title='customTabs']//td//table[@class='genericItemListChip']//tbody/tr[1]")
	public WebElement Nemo_pmi_show1;
	@FindBy(how = How.XPATH, using = "//table[@title='customTabs']//td[5]")
	public WebElement Nemo_pmi_showempty;
	@FindBy(how = How.XPATH, using = "(//td[contains(@id,'Content/GenericResortableItemList') and contains(@class,'name')])[3]")
	public WebElement Nemo_pmi_addtab;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'PKEditor[in GenericCondition[CustomTab.pk]')]")
	public WebElement Nemo_pmi_inputpk;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'ModalGenericFlexibleSearchOrganizerSearch[CustomTab]')]")
	public WebElement Nemo_pmi_search;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'PKDisplay')]")
	public WebElement Nemo_pmi_searchresult;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/PKEditor[in Content/Attribute[.pk][1]]_input']")
	public WebElement Nemo_admitration_pknumber;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/BooleanEditor[in Content/Attribute[NemoProductGenerationCronJob.active]]_checkbox']")
	public WebElement enabledCronJob;

	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[NemoSubSeriesProduct.showCustomTabs]]_checkbox']")
	public WebElement Nemo_showcustom_check;
	@FindBy(how = How.XPATH, using = "//*[@id=\"resultlist_Content/GenericResortableItemList[9]\"]//tr[1]")
	public WebElement Nemo_showcustom_tabs;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'BooleanEditor[in Attribute[CustomTab.active]')]")
	public WebElement Nemo_showcustom_active;
	@FindBy(how = How.XPATH, using = "//table[@title='active']//input[contains(@id,'BooleanEditor[in Attribute[CustomTab.active]')]")
	public WebElement Nemo_showcustom_active1;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'BooleanEditor[in Attribute[CustomTab.buyfromresellerflag]')]")
	public WebElement Nemo_showcustom_buy;
	@FindBy(how = How.XPATH, using = "//table[@title='buyfromresellerflag']//input[contains(@id,'BooleanEditor[in Attribute[CustomTab.buyfromresellerflag]')]")
	public WebElement Nemo_showcustom_buy1;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[CustomTab.properties]_span']")
	public WebElement Nemo_properties;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'Content/GenericItemListEntry')]")
	public WebElement Nemo_lastchange;
	@FindBy(how = How.XPATH, using = "//div[@class='mandatory']")
	public WebElement Nemo_desprition;
	@FindBy(how = How.XPATH, using = "//textarea[@id='TextAreaEditor[in Attribute[SavedValues.modifiedItemDisplayString]]_textarea']")
	public WebElement Nemo_desprition_pk;
	@FindBy(how = How.XPATH, using = "//div[@id='GenericItemChip$5[organizer.editor.delete.title]_label']")
	public WebElement Nemo_pmi_delete;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.zTeleShowChatIp') and contains(@id,'true')]")
	public WebElement chatIP_yes;

	// 23977
	@FindBy(how = How.XPATH, using = "//*[@id=\"Content/StringEditor[in Content/GenericCondition[SimpleCMSComponent.name]]_input\"]")
	public WebElement Wcms_Name;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'weContentCatalog - Online')]")
	public WebElement Wcms_Name_searchResult;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'LocalizableAttribute[CMSLinkComponent.urlLocalized]_languagetoggle')]")
	public WebElement Wcms_Name_Maxmine;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'LocalizableAttribute[CMSLinkComponent.urlLocalized]][9]_input')]")
	public WebElement Wcms_Name_Localized;

	// 19873
	@FindBy(how = How.XPATH, using = "//*[@id='table_Content/GenericItemList[3]_table']/tbody/tr[1]/th[4]/div")
	public WebElement baseStore_ISOCode;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/GenericItemList[3]_!add_true_label']")
	public WebElement baseStore_addCountry;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'StringEditor[in GenericCondition[Country.isocode]')]")
	public WebElement baseStore_ISOCodeValue;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'ModalGenericFlexibleSearchOrganizerSearch[Country]') and contains(@id,'_searchbutton')]")
	public WebElement baseStore_ISOCodeSearch;
	@FindBy(how = How.XPATH, using = "//table[@class='listtable selecttable']")
	public WebElement baseStore_ISOCodeSearchResult;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'B2BUnit.addresses')]")
	public WebElement B2Bunit_Address;

	// 18598
	@FindBy(how = How.XPATH, using = "//select[@id='Content/AbstractGenericConditionalSearchToolbarChip$2[typeselect][AbstractPage]_select']/option[contains(.,'Product Page')]")
	public WebElement Type_select;
	@FindBy(how = How.XPATH, using = "//*[@id=\"Content/NemoSearchConfigurable[AbstractPage]_searchbutton\"]")
	public WebElement Type_search;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/StringDisplay[Long Scroll Product Details Page]_span']")
	public WebElement Type_result;
	@FindBy(how = How.XPATH, using = "//*[@id=\"Content/EnumerationValueSelectEditor[in Content/Attribute[ProductPage.approvalStatus]]_select\"]/option[1]")
	public WebElement ApprovalStatus;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[ProductPage.approvalStatus]]_select']/option[2]")
	public WebElement Approval;

	// 23895
	@FindBy(how = How.XPATH, using = "//table[@title='regionPmiAttributeOverrides']")
	public WebElement RegionPmi;
	@FindBy(how = How.XPATH, using = "//table[@title='regionPmiAttributeOverrides']//div[contains(@class,'gilcEntry') and contains(@id,'Content/ItemDisplay[mkt_name]')]")
	public WebElement RegionPmi_name;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'LocalizableAttribute[.stringValue]]_input')]")
	public WebElement RegionPmi_stringvalue;
	@FindBy(how = How.XPATH, using = "//table[@title='regionPmiAttributeOverrides']//div[@class='gilcEntry-mandatory']")
	public WebElement RegionPmi_none;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'!create_PmiAttributeOverrideRules_label')]")
	public WebElement RegionPmi_create;
	@FindBy(how = How.XPATH, using = "(//select[@id='AttributeOverrideSelectEditor[in Attribute[.descriptorAttribute]]_select']/option[contains(.,'mkt_name')])[1]")
	public WebElement RegionPmi_create_att;
	@FindBy(how = How.XPATH, using = "//*[@id=\"AllInstancesSelectEditor[in Attribute[.region]]_select\"]/option[@value='0']")
	public WebElement RegionPmi_region;
	@FindBy(how = How.XPATH, using = "//*[@id=\"BooleanEditor[in Attribute[.active]]_spantrue\"]")
	public WebElement RegionPmi_active;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[PmiAttributeOverrideRules.startDate]]_date']")
	public WebElement RegionPmi_date;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[PmiAttributeOverrideRules.endDate]]_date']")
	public WebElement RegionPmi_endDate;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'!remove_true_label')]")
	public WebElement RegionPmi_remove;
	@FindBy(how = How.XPATH, using = "//table[@title='countryPmiAttributeOverrides']//div[@class='gilcEntry-mandatory']")
	public WebElement CountryPmi;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'!create_PmiAttributeOverrideRules_label')]")
	public WebElement CountryPmi_add;
	@FindBy(how = How.XPATH, using = "//select[@id='AllInstancesSelectEditor[in Attribute[.country]]_select']/option[@value='11']")
	public WebElement CountryPmi_country;
	@FindBy(how = How.XPATH, using = "//table[@id='table_Content/GenericItemList[8]_table']")
	public WebElement Channel_text;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/GenericItemList[8]_!create_PmiTextAttributeOverrideRules_label']")
	public WebElement Channel_textadd;
	@FindBy(how = How.XPATH, using = "//select[@id='AttributeOverrideSelectEditor[in Attribute[.descriptorAttribute][1]]_select']/option[@value='1']")
	public WebElement Channel_textattr;
	@FindBy(how = How.XPATH, using = "//select[@id='AllInstancesSelectEditor[in Attribute[.country][2]]_select']/option[@value='11']")
	public WebElement Channel_textacountry;
	@FindBy(how = How.XPATH, using = "//select[@id='AllInstancesSelectEditor[in Attribute[.channel][2]]_select']/option[@value='1']")
	public WebElement Channel_textchannel;
	@FindBy(how = How.XPATH, using = "//*[@id='BooleanEditor[in Attribute[.active][2]]_spantrue']")
	public WebElement Channel_textactive;
	@FindBy(how = How.XPATH, using = "//*[@id='tinymce']")
	public WebElement Channel_textbody;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/ItemDisplay[mkt_desc_long][2]_div']")
	public WebElement Channel_textdesc;
	@FindBy(how = How.XPATH, using = "//*[@id='DateTimeEditor[in Attribute[PmiTextAttributeOverrideRules.startDate][1]]_img']")
	public WebElement Channel_textdescstartdate;
	@FindBy(how = How.XPATH, using = "//*[@id='DateTimeEditor[in Attribute[PmiTextAttributeOverrideRules.endDate][1]]_img']")
	public WebElement Channel_textdescenddate;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/GenericItemList[8]_!remove_true_label']")
	public WebElement Channel_textdescremove;
	@FindBy(how = How.XPATH, using = "//table[@id='table_Content/GenericItemList[9]_table']")
	public WebElement Group_add;
	@FindBy(how = How.XPATH, using = "//td[@id='Content/GenericItemList[9]_!create_PmiTextAttributeOverrideRules_label']")
	public WebElement Group_addtext;
	@FindBy(how = How.XPATH, using = "//input[@id='ajaxinput_AutocompleteReferenceEditor[in Attribute[.group][1]]']")
	public WebElement Group_addgroup;
	@FindBy(how = How.XPATH, using = "//table[@id='table_Content/GenericItemList[10]_table']")
	public WebElement Region_collection;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_PmiCollectionAttributeOverrideRules_label')]")
	public WebElement Region_collectionadd;
	@FindBy(how = How.XPATH, using = "//select[@id='AttributeOverrideSelectEditor[in Attribute[.descriptorAttribute]]_select']/option[@value='9']")
	public WebElement Region_collectionattr;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/ItemDisplay[merchandisingTag][1]_div']")
	public WebElement Region_collectiontag;
	@FindBy(how = How.XPATH, using = "//*[@id='DateTimeEditor[in Attribute[.startDate]]_img']")
	public WebElement Region_collectiontagstartdate;
	@FindBy(how = How.XPATH, using = "//*[@id=\"DateTimeEditor[in Attribute[.endDate]]_img\"]")
	public WebElement Region_collectiontagenddate;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'remove_true_label')]")
	public WebElement Region_collectionremove;
	@FindBy(how = How.XPATH, using = "//table[@id='table_Content/GenericItemList[15]_table']")
	public WebElement Group_collectionremove;
	@FindBy(how = How.XPATH, using = "//input[@id='ajaxinput_AutocompleteReferenceEditor[in Attribute[.group]]']")
	public WebElement Group_collectionadd;
	@FindBy(how = How.XPATH, using = "//table[@id='table_Content/GenericItemList[17]_table']")
	public WebElement Country_mediaadd;
	@FindBy(how = How.XPATH, using = "//select[@id='AttributeOverrideSelectEditor[in Attribute[PmiMediaAttributeOverrideRules.descriptorAttribute]]_select']/option[@value='2']")
	public WebElement Country_mediaattr;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/ItemDisplay[processor_logo]_div']")
	public WebElement Country_mediaaprocess;
	@FindBy(how = How.XPATH, using = "//*[@id='table_Content/GenericItemList[18]_table']")
	public WebElement Region_boolean;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_PmiBooleanAttributeOverrideRules_label')]")
	public WebElement Region_booleanadd;
	@FindBy(how = How.XPATH, using = "//select[@id='AttributeOverrideSelectEditor[in Attribute[.descriptorAttribute]]_select']/option[@value='3']")
	public WebElement Region_booleanaddatt;
	@FindBy(how = How.XPATH, using = "//*[@id='BooleanEditor[in Attribute[.booleanValue]]_spantrue']")
	public WebElement Region_booleanvalue;
	@FindBy(how = How.XPATH, using = "//input[@contains(@id,'LocalizableAttribute[PmiAttributeOverrideRules.stringValue]]_input')]")
	public WebElement Region_editstringvalue;

	// 28038
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/Attribute[Customer.repId]]_input']")
	public WebElement repId;

	// 20059
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'dynamicOrderType')]")
	public WebElement orderType;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'dynamicfulfillmentType')]")
	public WebElement fulfillmentType_select;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'dynamicfulfillmentType')]/option[contains(text(),'CRM')]")
	public WebElement fulfillmentType_select_CRM;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'dynamicfulfillmentType')]/option[contains(text(),'Email')]")
	public WebElement fulfillmentType_select_email;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'businessToEmailAddress')]")
	public WebElement businessToEmailAddress;

	// 26949
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'sortType')]")
	public WebElement Products_sortTypeSel;
	@FindBy(how = How.XPATH, using = "//tr[contains(@class,'draggable')][last()]//div[@class='gilcEntry']")
	public WebElement Products_optionsLast;
	@FindBy(how = How.XPATH, using = "//tr[contains(@class,'draggable')][1]")
	public WebElement Products_optionsFirst;
	@FindBy(how = How.XPATH, using = "//tr/td[3]//div[contains(@id,'StringDisplay') and not(@class)]")
	public List<WebElement> Products_optionsAll;

	// 28156
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2BUnit.orderType') and contains(@id,'input')]")
	public WebElement B2B_orderType;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2BUnit.businessToEmailAddress') and contains(@id,'input')]")
	public WebElement B2B_businessToEmailAddress;

	// 25411
	@FindBy(how = How.XPATH, using = "//input[@id='idSingleEcouponPerUseUpperLimit']")
	public WebElement B2CPriceRules_PeruseLimit;
	@FindBy(how = How.XPATH, using = "//input[@id='idProdCoupon']")
	public WebElement B2CPriceRules_NUmberProductsOrder;
	@FindBy(how = How.XPATH, using = "//span[@id='remainingQuantity']")
	public WebElement B2CPriceRules_RemainingQuantity;
	@FindBy(how = How.XPATH, using = "//a[@id='downloadEcouponButtonId']")
	public WebElement B2CPriceRules_DownloadEcouponButton;

	// 26649
	@FindBy(how = How.XPATH, using = "(//table[@title='regionPmiCollectionAttributeOverrides']//div[contains(.,'Attribute')])[3]")
	public WebElement regionPmiCollectionAttributeOverrides;
	@FindBy(how = How.XPATH, using = "//tr[contains(@id,'!create_PmiCollectionAttributeOverrideRules_tr')]")
	public WebElement Create_pmi;
	@FindBy(how = How.XPATH, using = "//table[@title='merchandisingTag']//td[5]//.//td[1]")
	public WebElement Create_Merchandisingtag;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'!create_MerchandisingTag_label')]")
	public WebElement Create_Create_Merchandisingtaglabel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'EnumerationValueSelectEditor')]/option[@value=1]")
	public WebElement Create_Create_Merchandisingtaglabel_color;
	@FindBy(how = How.XPATH, using = "//td[@class='localized']//input[contains(@id,'StringEditor')]")
	public WebElement Create_Create_Merchandisingtaglabel_label;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AttributeOverrideSelectEditor')]/option[@value=9]")
	public WebElement Create_merchandisingTag;
	@FindBy(how = How.XPATH, using = "//select[@id='AllInstancesSelectEditor[in Attribute[.region]]_select']/option[@value=0]")
	public WebElement Create_country;
	@FindBy(how = How.XPATH, using = "//table[@title='regionPmiCollectionAttributeOverrides']//div[contains(.,'Region PMI Collection Attribute Override')]")
	public WebElement Create_region;
	@FindBy(how = How.XPATH, using = "//span[@id='BooleanEditor[in Attribute[.active]]_spantrue']")
	public WebElement active;
	@FindBy(how = How.XPATH, using = "//table[@title='regionPmiCollectionAttributeOverrides']//div[contains(@id,'merchandisingTag')]")
	public WebElement EditMerchandisingtaglabel;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'!open_true_label')]")
	public WebElement EditMerchandising;
	@FindBy(how = How.XPATH, using = "//table[@title='merchandisingTag']//td[5]//table")
	public WebElement EditMerchandising_table;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'!open_editor_true_label')]")
	public WebElement EditmerchandisingTag;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[PmiCollectionAttributeOverrideRules.startDate]]_date']")
	public WebElement MerchandisingTagstart;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[PmiCollectionAttributeOverrideRules.endDate]]_date']")
	public WebElement MerchandisingTagend;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[PmiCollectionAttributeOverrideRules.startDate]]_time']")
	public WebElement MerchandisingTagstarttime;
	@FindBy(how = How.XPATH, using = "//input[@id='DateTimeEditor[in Attribute[PmiCollectionAttributeOverrideRules.endDate]]_time']")
	public WebElement MerchandisingTagendttime;
	@FindBy(how = How.XPATH, using = "//select[@id='AllInstancesSelectEditor[in Attribute[PmiCollectionAttributeOverrideRules.region]]_select']/option[@value=0]")
	public WebElement Edit_country;
	@FindBy(how = How.XPATH, using = "//span[@id='BooleanEditor[in Attribute[PmiCollectionAttributeOverrideRules.active]]_spantrue']")
	public WebElement Edit_active;

	// 28209
	@FindBy(how = How.XPATH, using = "//table[@title='sectionOrders']//table//table")
	public WebElement Template_sectionOrders;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'ProductOptionSectionOrder') and contains(@class,'name')]")
	public WebElement Template_ProductOptionSectionOrder;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'country')]")
	public WebElement Template_country;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]")
	public WebElement Template_channel;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'machineType')]")
	public WebElement Template_machineType;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'sortOrderNumber')]")
	public WebElement Template_sortOrderNumber;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'expanded') and contains(@id,'true')]")
	public WebElement Template_expandedTrue;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'expanded') and contains(@id,'false')]")
	public WebElement Template_expandedFalse;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'actived')]")
	public WebElement Template_actived;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'machineType')]")
	public WebElement Template_machineTypeSearch;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Article Number')]/../..//input")
	public WebElement Template_articleNumber;
	@FindBy(how = How.XPATH, using = "//table[@title='groupOrders']//table//table")
	public WebElement Template_groupOrders;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'ProductOptionGroupOrder') and contains(@class,'name')]")
	public WebElement Template_ProductOptionGroupOrder;

	// 25536
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.requestAmount')]")
	public WebElement siteAttribute_RequestAmount;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.webFormToEmailAddress')]")
	public WebElement siteAttribute_WebformToEmails;

	// 17695
	@FindBy(how = How.XPATH, using = "(//select[contains(@id,'catalogVersion')]//option[contains(@value,'masterMultiCountryProductCatalog-Online')])[1]")
	public WebElement ShowPromotedOptions_catalogVersionOnline;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'catalogVersion')]")
	public WebElement ShowPromotedOptions_catalogVersionSel;
	@FindBy(how = How.XPATH, using = "//tr[@title='Show Promoted Items']//a")
	public WebElement PB_showPromotedOptions;
	@FindBy(how = How.XPATH, using = "//input[@id='product-code']")
	public WebElement ShowPromotedOptions_productCode;
	@FindBy(how = How.XPATH, using = "//input[@id='submit']")
	public WebElement ShowPromotedOptions_search;
	@FindBy(how = How.XPATH, using = "//div[@class='moveItem']/p[1]")
	public List<WebElement> ShowPromotedOptions_promotedCV;
	@FindBy(how = How.XPATH, using = "//li[@class='option-item']/p[1]")
	public List<WebElement> ShowPromotedOptions_promotedOptions;
	@FindBy(how = How.XPATH, using = "//span[@id='y_popupmessage_ok_label']")
	public WebElement CreatePromotedOptions_popOK;
	@FindBy(how = How.XPATH, using = "//div[@id='y_popupmessage']//td[@class='text']")
	public WebElement CreatePromotedOptions_popText;
	@FindBy(how = How.XPATH, using = "//input[@id='save']")
	public WebElement ShowPromotedOptions_save;
	@FindBy(how = How.XPATH, using = "//iframe[contains(@src,'listPromotedItems')]")
	public WebElement ShowPromotedOptions_iframe;

	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'PaymentTypeProfile.tab.b2c.b2b.properties')]")
	public WebElement PaymentProfile_B2CB2BProperties;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'in Content/LocalizableAttribute')]")
	public WebElement PaymentProfile_DisplayName;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'PaymentTypeProfile.tab.countries')]")
	public WebElement PaymentProfile_Countries;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Attribute[.code]')]")
	public WebElement PaymentProfile_codeValue;

	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'ISO Code')]")
	public WebElement PaymentProfile_addCountriesList;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Add Country')]")
	public WebElement PaymentProfile_addCountry;
	@FindBy(how = How.XPATH, using = ".//*[@id='StringEditor[in GenericCondition[Country.isocode]]_input']")
	public WebElement PaymentProfile_ISOCode;
	@FindBy(how = How.XPATH, using = ".//*[@id='ModalGenericFlexibleSearchOrganizerSearch[Country]_searchbutton']")
	public WebElement PaymentProfile_ISOCodeSearch;

	// na-27240
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[SolrValueRangeSet]_label")
	public WebElement Home_facet_range;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[SolrValueRangeSet.name]]_input")
	public WebElement Range_name;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'Content/OrganizerListEntry[nemoPriceRange')]")
	public WebElement Range_editor;
	@FindBy(how = How.XPATH, using = "//*[@class='genericItemListChip']//table/tbody/tr[2]//a")
	public WebElement Range_FirstRangeEditor;
	@FindBy(how = How.XPATH, using = "//*[@class='genericItemListChip']//table/tbody/tr[2]//a")
	public WebElement Range_FirstRangeName;
	@FindBy(how = How.XPATH, using = "//*[@src='images/icons/button_language.gif']")
	public WebElement FirstRange_Language;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en')][not(contains(text(),'_'))]/../..//table//input")
	public WebElement RangeUSInput;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'ja')][not(contains(text(),'_'))]/../..//table//input")
	public WebElement RangeJPInput;

	// na-26780
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Availability Rule')]/../..//table//table//img[@src='images/icons/t_undefined.gif']")
	public WebElement Product_AVR_ListEdit;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Availability Rule')]/../..//table//table/tbody/tr/th[1]")
	public WebElement Product_AVR;
	@FindBy(how = How.XPATH, using = "//*[text()='Select all']")
	public WebElement Product_AVR_selectAll;
	@FindBy(how = How.XPATH, using = "//*[text()='Remove']")
	public WebElement Product_AVR_remove;
	@FindBy(how = How.XPATH, using = "//*[text()='Create Availability Rules']")
	public WebElement Product_AVR_create;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/DateTimeEditor[in Content/CreateItemListEntry[n/a]]_date']")
	public WebElement Product_AVR_fromDate;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/DateTimeEditor[in Content/CreateItemListEntry[n/a]][1]_date']")
	public WebElement Product_AVR_endDate;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.hideThankYouPageRegistrationToggle') and contains(@id,'false')]")
	public WebElement B2CUnit_NoHideThankyouPageRadioButton1;

	// 10323
	@FindBy(how = How.XPATH, using = "//div[@id='Content/GenericItemChip$1[com.nemo.core.hmc.punchoutcustomerprofilecloneaction][1]_label']")
	public WebElement Clonepunchoutprofile;
	@FindBy(how = How.XPATH, using = "//textarea[@class='messageOK']")
	public WebElement Clonepunchoumessage;
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[PunchOutCustomerProfile]_innertable']/tbody/tr[4]/td[4]")
	public WebElement Clonepunchoumessage_remove;
	@FindBy(how = How.XPATH, using = "//a[@id='Content/OrganizerComponent[organizersearch][PunchOutCustomerProfile]_togglelabel']")
	public WebElement editSearch;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'Content/EditorTab[PunchOutCustomerProfile.tab.punchoutcustomerprofile.oci]')]")
	public WebElement PunchoutProfile_OCITabedit;

	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[PunchOutCustomerProfile]_innertable']/tbody/tr[3]/td[@id='Content/StringDisplay[na-8939-001#AU#SoldTo_07-copy-1]_td3']")
	public WebElement PunchoutProfile_copy1;

	// 28444
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'StringDisplay')]")
	public WebElement serach_result;
	@FindBy(how = How.XPATH, using = "//td[text()='Clone']")
	public WebElement result_clone;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zId')]")
	public WebElement management_id;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'zAddressValidatorRule')]")
	public WebElement AddressValidatorRule;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'B2B')]")
	public WebElement serach_result_B2B;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'lastnameMaxLength')]")
	public WebElement addressValidatorRule_lastnameMaxLength;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'lastnameMinLength')]")
	public WebElement addressValidatorRule_lastnameMinLength;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'B2C')]")
	public WebElement serach_result_B2C;

	// 27159
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'emailPattern')]")
	public WebElement addressValidatorRule_EmailPattern;

	// 10440
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'!create_OciOutBoundRule_label')]")
	public WebElement create_OciOutBoundRule_label;

	// 17762
	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'Batch Assign MTM')]")
	public WebElement ProductBuilder_batchAssignMTM;
	@FindBy(how = How.XPATH, using = "//div[@title='Article Number']")
	public WebElement products_PB_mtmsTable;
	@FindBy(how = How.XPATH, using = "//td[text()='Add NemoMTMVariantProduct']")
	public WebElement products_PB_addMTMOption;
	@FindBy(how = How.XPATH, using = "//ul/li/div[contains(.,'System Discounts')]")
	public WebElement B2CPriceRules_SystemDiscountOption;
	@FindBy(how = How.XPATH, using = "//span[text()='Select Triggers...']")
	public WebElement B2CPriceRules_TriggersSelect;
	@FindBy(how = How.XPATH, using = "//input[@id='BooleanEditor[in Attribute[.mtmFlag]]_checkbox']")
	public WebElement PBTemplate_MTMFlag;
	@FindBy(how = How.XPATH, using = "//tr/td[6]/div[contains(@id,'McCategoryAttributeDisplay')]/div[text()='n/a']/../../../td[3]/div/div[contains(@id,'StringDisplay')]")
	public WebElement Products_SupercategoriesIdentifier;
	@FindBy(how = How.XPATH, using = "//table[@title='supercategories']//tr/td/div/table//tr/td/div[contains(text(),'The list is empty')]")
	public WebElement Products_SupercategoriesEmpty;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'NemoMTMVariantProduct.catalogVersion') and contains(@id,'AllInstancesSelectEditor')]")
	public WebElement products_MTM_catalogVersionSel;

	// 13379
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.oracleActive]]_checkbox']/preceding-sibling::*")
	public WebElement PunchOutCustomerProfile_oracleActive;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[PunchOutCustomerProfile.oracleActive]]_checkbox']")
	public WebElement PunchOutCustomeroracleActive;

	// 27059
	@FindBy(how = How.XPATH, using = "//table[@title='productBuilder']//td/div/table[@class='genericItemListChip']")
	public WebElement product_productBuilderTable;
	@FindBy(how = How.XPATH, using = "//tr/td[text()='Create [ProductBuilderCTOContainer]']")
	public WebElement product_CreateProductBuilder;
	@FindBy(how = How.XPATH, using = "//tr/td[text()='Create [ProductBuilderMTMContainer]']")
	public WebElement product_CreateProductBuilderMTM;
	@FindBy(how = How.XPATH, using = "//table[@title='preselectedOptions']//td/table[@class='genericItemListChip']")
	public WebElement product_preselectedOptionsTable;
	@FindBy(how = How.XPATH, using = "//tr/td[text()='Create [PreselectedProductOptions]']")
	public WebElement product_CreateProductOptions;
	@FindBy(how = How.XPATH, using = "//input[@class='enabled' and contains(@id,'CreateItemListEntry')]/../../td/div")
	public WebElement product_preselecteOptionsSearch;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Attribute[.country')]")
	public WebElement preselectedOptions_Country;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Attribute[.channel')]")
	public WebElement preselectedOptions_Chanel;
	@FindBy(how = How.XPATH, using = "//select/option[contains(text(),'B2C')]")
	public WebElement preselectedOptions_channelB2C;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Attribute[.channel]]_select')]/option[contains(text(),'B2B')]")
	public WebElement preselectedOptions_channelB2B;
	
	// 19794
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/AutocompleteReferenceEditor[in Content/Attribute[NemoMTMVariantProduct.ctaLink')][contains(@id,'img')]")
	public WebElement Catalog_ctaLinksearch;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'StringEditor[in GenericCondition[NemoLinkComponent.uid]')]")
	public WebElement Catalog_ctaLink_id;
	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'StringDisplay[L470 CTA url test]')]")
	public WebElement Catalog_ctaLink_result;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'NemoMTMVariantProduct.ctaLink')]")
	public WebElement Products_PMI_CTALinkInput;
	@FindBy(how = How.XPATH, using = ".//td[contains(@id,'create_NemoLinkComponent_label')]")
	public WebElement Products_PMI_CTALinkCreate;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.uid]]_input")
	public WebElement CTALink_ID;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.name]]_input")
	public WebElement CTALink_Name;
	@FindBy(how = How.XPATH, using = ".//*[@id='AllInstancesSelectEditor[in Attribute[.catalogVersion]]_select']/option[contains(text(),'	ap-inContentCatalog - Online')]")
	public WebElement CTALink_Catalog;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Product.ctaLink') and contains(@id,'_img')]")
	public WebElement CTALink_Search;
	@FindBy(how = How.ID, using = "BooleanEditor[in Attribute[.external]]_checkbox")
	public WebElement CTALink_External;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.url]]_input")
	public WebElement CTALink_URL;
	@FindBy(how = How.ID, using = "StringEditor[in Attribute[.url]]_input")
	public WebElement CTALink_CreateButton;
	@FindBy(how = How.XPATH, using = ".//*[text()='page_00007SVC']")
	public WebElement Wcms_longScrollPage;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[AbstractPage.catalogVersion]]_select']/option[contains(text(),'ap-inContentCatalog - Online')]")
	public WebElement Wcms_in_category;
	@FindBy(how = How.XPATH, using = ".//div[text()='longScrollCTATempFix']/../../../td[last()]/div/div")
	public WebElement Pages_longScrollCTATempFix_Visible;
	@FindBy(how = How.XPATH, using = ".//div[text()='longScrollCTATempFix']/../../../td[2]//img")
	public WebElement Pages_longScrollCTATempFix_edit;
	@FindBy(how = How.ID, using = "BooleanEditor[in Attribute[CssJsOverridePageComponent.visible]]_checkbox")
	public WebElement Pages_longScrollCTATempFix_changeVisible;
	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Group PMI Collection Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIOverride_GroupCollection;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'create_PmiCollectionAttributeOverrideRules_label')]")
	public WebElement PMIOverride_GroupCollection_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'descriptorAttribute')]/option[contains(text(),'ctaButtonLink')]")
	public WebElement PMIOverride_CTAButtonLink_Attribute;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'descriptorAttribute')]/option[contains(text(),'zNemoModelsSortingRule')]")
	public WebElement PMIOverride_ModalSorting_Attribute;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Models Sorting Rule')]/../../td[last()]//td[1]")
	public WebElement PMIOverride_ModalSorting_Rule;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'create_NemoModelsSortingRule_label')]")
	public WebElement PMIOverride_ModalSorting_Create;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'[.zPriceType')]/option[contains(text(),'Web Price')]")
	public WebElement PMIOverride_ModalSorting_SortByWebPrice;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'zEnableCouponCode')]")
	public WebElement PMIOverride_ModalSorting_SortByEcoupon;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'.zPriceSortingSequence')]/option[contains(text(),'Ascending')]")
	public WebElement PMIOverride_ModalSorting_SortByAscend;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'.zPriceSortingSequence')]/option[contains(text(),'Descending')]")
	public WebElement PMIOverride_ModalSorting_SortByDescend;
	@FindBy(how = How.XPATH, using = ".//table[contains(@class,'selecttable')]//td[3]/div")
	public WebElement PMIOverride_ModalSorting_Result;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'zNemoModelsSortingRule')]")
	public WebElement PMIOverride_ModalSorting_Search;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'.zEnableCouponCode')]/option[contains(text(),'Yes')]")
	public WebElement PMIOverride_ModalSorting_EcouponEnable;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'.zEnableCouponCode')]/option[contains(text(),'No')]")
	public WebElement PMIOverride_ModalSorting_EcouponDisable;

	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'BooleanEditor[in Attribute[.active]')][contains(@id,'true')]")
	public WebElement PMIOverride_CTAButtonLink_Active;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'ctaButtonLink]')]")
	public WebElement PMI_CTAButtonLink_search;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'AutocompleteReferenceEditor[in Attribute[.group]')]")
	public WebElement PMIOverride_CTAButtonLink_GroupSearch;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_AutocompleteReferenceEditor[in Attribute[.ctaButtonLink]')]")
	public WebElement PMIOverride_CTAButtonLink_Input;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_AutocompleteReferenceEditor[in Attribute[.group]')]")
	public WebElement PMIOverride_CTAButtonLink_GroupInput;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'select_visible_true_label')]")
	public WebElement PMIOverride_GroupCollection_SelectAll;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'remove_true_label')]")
	public WebElement PMIOverride_GroupCollection_Remove;
	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Group PMI Collection Attribute Override')]/../../td[last()]/table//table/tbody/tr[2]//img[contains(@id,'Content/GenericItemListEntry')]")
	public WebElement PMIOverride_GroupCollection_Result;

	@FindBy(how = How.XPATH, using = "//td[text()='Actions']")
	public WebElement DigitalRiverTraceLog_actions;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'exportdrtracelogcsv') and contains(@class,'name')]")
	public WebElement DigitalRiverTraceLog_exportdrtracelogcsv;
	@FindBy(how = How.XPATH, using = ".//textarea[contains(@id,'VariantProduct.seo_description')]")
	public WebElement Catalog_Products_SEODescription;
	
	@FindBy(how = How.XPATH, using = "//*[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[B2CUnit.dynamicSaleType]]_select']/option[@value='1']")
	public WebElement Indirect_site;
	@FindBy(how = How.XPATH, using = "//*[@id=\"Content/StringEditor[in Content/Attribute[B2CUnit.ctaBackgroundColors]]_input\"]")
	public WebElement CTA_backgroundcolor;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/NemoLocalizableAttribute[B2CUnit.ctaLabelName]]_input']")
	public WebElement CTA_label;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'SiteListPriceDisplay')][contains(text(),'No')]")
	public WebElement ListPriceDisplay_No;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[NemoMTMVariantProduct.hideModels]]_checkbox']")
	public WebElement Product_hide_model;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[NemoSubSeriesProduct.hideModels]]_checkbox']")
	public WebElement Suberies_hide_model;

	// 9417
	@FindBy(how = How.XPATH, using = "//table[@title='cxmlStandardFields']")
	public WebElement rule1;
	@FindBy(how = How.XPATH, using = "//table[@title='cxmlConstantValues']")
	public WebElement rule2;
	@FindBy(how = How.XPATH, using = "//table[@title='cxmlVariableKeyValues']")
	public WebElement rule3;
	@FindBy(how = How.XPATH, using = "//table[@title='cxmlVariableExtrinsics']")
	public WebElement rule4;
	@FindBy(how = How.XPATH, using = "//table[@title='soldToDeterminations']")
	public WebElement soldToDeterminations;

	// 27225
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/Attribute[B2BUnit.uid]]_input']")
	public WebElement unitid;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.enableProductBuilder]]_spantrue']")
	public WebElement EnableProductBuilder;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.accesoryAddToggle]]_spantrue']")
	public WebElement AccessoryAddToggle;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.zHideAddToCart]]_spanfalse']")
	public WebElement HideAddToCart;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.accesoryAddToggle]]_spanfalse']")
	public WebElement AccessoryAddToggleFalse;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.zHideAddToCart]]_spantrue']")
	public WebElement HideAddToCartTrue;
	@FindBy(how = How.XPATH, using = "//div[@id='y_popupmessage']/table/tbody/tr[1]/th[@class='title']")
	public WebElement ErrorMessage;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.enableProductBuilder]]_spanfalse']")
	public WebElement EnableProductBuilderFalse;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'y_popupmessage_ok_label')]")
	public WebElement ErrorMessageok;

	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[NemoMTMVariantProduct.screenSize]')]")
	public WebElement Products_Sceensize;
	@FindBy(how = How.ID, using = "Tree/NemoOutletTreeNode_label")
	public WebElement Products_Upload;
	@FindBy(how = How.XPATH, using = ".//input[contains(@class,'modalMediaFileUploadChip')]")
	public WebElement Products_Upload_UploadPath;
	@FindBy(how = How.ID, using = "Content/NemoOutletDataFileUploadEditor_upload_div")
	public WebElement Products_Upload_OpenUpload;
	@FindBy(how = How.XPATH, using = ".//a[@href='#']//td[contains(.,'Upload')]")
	public WebElement Products_Upload_UploadButton;
	@FindBy(how = How.XPATH, using = ".//a[@title='[update]']/span")
	public WebElement Products_Upload_UploadConfirmButton;
	@FindBy(how = How.ID, using = "Content/OrganizerComponent[organizersearch][Product]_togglelabel")
	public WebElement Products_SearchPartOpen;

	// 27967
	@FindBy(how = How.XPATH, using = ".//*[@id='ps_skeleton']/span")
	public WebElement B2CpriceSimulate_ctoBasePrice;
	@FindBy(how = How.XPATH, using = "//td[@id='list']//div[@id='groups']//div[@id='container']/samp/i")
	public WebElement B2CpriceSimulate_listRuleGroup;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'List Price Override')]")
	public WebElement B2CPriceRules_listPriceOption;
	@FindBy(how = How.XPATH, using = "//table[@id='cvPrices']//td[5]")
	public List<WebElement> B2CpriceSimulate_cvListOverridePrice;
	@FindBy(how = How.XPATH, using = "//table[@id='cvPrices']//td[4]")
	public List<WebElement> B2CpriceSimulate_cvListPrice;

	// 13518
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.profileName]]_input']")
	public WebElement soldToId;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.profileName][operator]_select']")
	public WebElement soldToIdSelect;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.punchOutUser][operator]_select']")
	public WebElement punchOutUser;
	@FindBy(how = How.XPATH, using = "//input[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.punchOutUser]]']")
	public WebElement punchOutUserInput;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.oracleSoldTo][operator]_select']")
	public WebElement oracleSoldTo;
	@FindBy(how = How.XPATH, using = "//input[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.oracleSoldTo]]']")
	public WebElement oracleSoldToInput;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.oracleStoreName][operator]_select']")
	public WebElement oracleStoreName;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.oracleStoreName]]_input']")
	public WebElement oracleStoreNameInput;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.oracleName][operator]_select']")
	public WebElement oracleName;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[NemoB2BPunchOutCustomerProfileSearch.oracleName]]_input']")
	public WebElement oracleNameInput;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[NemoB2BPunchOutCustomerProfileSearch]_label")
	public WebElement searchProfile;
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']/tbody/tr[3]/td[4]")
	public WebElement searchResult;

	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[B2BCustomer]_innertable']/tbody/tr[contains(@id,'Content/OrganizerListEntry')]")
	public WebElement searchUser;

	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/NemoLocalizableAttribute[B2CUnit.locname]]_input")
	public WebElement B2CUnit_name;
	@FindBy(how = How.ID, using = "Tree/GenericExplorerMenuTreeNode[c2l]_label")
	public WebElement home_internationalization;
	@FindBy(how = How.ID, using = "Tree/GenericExplorerMenuTreeNode[c2l.nemomessage]_label")
	public WebElement home_messages;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[NemoMessage]_label")
	public WebElement home_message;
	@FindBy(how = How.ID, using = "Tree/StaticContentLeaf[2]_label")
	public WebElement home_messageRefresh;
	@FindBy(how = How.XPATH, using = ".//a[contains(@class,'refreshAll-wrapper-link')]")
	public WebElement messageRefresh_refreshAll;
	@FindBy(how = How.ID, using = "Content/OrganizerComponent[organizersearch][NemoMessage]_togglelabel")
	public WebElement message_searchPart;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[NemoMessage.zKey]]_input")
	public WebElement message_searchKey;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[NemoMessage.zValue]]_input")
	public WebElement message_searchValue;
	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[NemoMessage]_!create_NemoMessage_label")
	public WebElement message_rightCreate;
	@FindBy(how = How.ID, using = "Content/McSearchListConfigurable[NemoMessage]_!remove_true_label")
	public WebElement message_rightRemove;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[.zKey]')]")
	public WebElement message_Key;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Content/StringEditor[in Content/Attribute[.zValue]')]")
	public WebElement message_Value;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[.zChannel]')]/option[contains(.,'B2C')]")
	public WebElement message_channel;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'Content/AllInstancesSelectEditor[in Content/Attribute[.zLanguage]')]/option[contains(text(),'English')]")
	public WebElement message_language;
	@FindBy(how = How.XPATH, using = ".//select[contains(@id,'Content/EnumerationValueSelectEditor[in Content/Attribute[.zBaseName]')]/option[contains(.,'Site')]")
	public WebElement message_baseName;
	@FindBy(how = How.XPATH, using = ".//*[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[.zSite]]']")
	public WebElement message_Site;

	@FindBy(how = How.ID, using = "Content/EditorTab[NemoCTOVariantProduct.tab.classattributeoverride]_span")
	public WebElement Products_ClassficationTab;
	@FindBy(how = How.ID, using = "Content/EditorTab[NemoCTOVariantProduct.tab.attributeoverride]_span")
	public WebElement Products_AttributeOverrideTab;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Country Attribute Override:')]/../../td/table//div[contains(text(),'Attribute')]")
	public WebElement AttributeOverride_countryOverride;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Country Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement AttributeOverride_countryResult;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Region Attribute Overrides:')]/../../td/table//div[contains(text(),'Attribute')]")
	public WebElement AttributeOverride_regionOverride;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Region Attribute Overrides:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement AttributeOverride_regionResult;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Channel Attribute Override:')]/../../td/table//div[contains(text(),'Attribute')]")
	public WebElement AttributeOverride_channelOverride;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Channel Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement AttributeOverride_channelResult;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'create_ProductAttributeOverrideRules_label')]")
	public WebElement AttributeOverride_rightCreate;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AttributeOverrideSelectEditor[in Attribute[.descriptorAttribute]')]/option[contains(text(),'summary')]")
	public WebElement AttributeOverride_attribute;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Country Class Attribute Override:')]/../../td/table//div[contains(text(),'Attribute')]")
	public WebElement Classfication_countryOverride;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Country Class Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement Classfication_countryResult;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'create_ClassificationAttributeOverrideRules_label')]")
	public WebElement Classfication_rightCreate;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'ClassAttributeOverrideSelectEditor[in Attribute[.featureClassAttributeAssignment]')]/option[contains(text(),'Processor - System Common Attributes')]")
	public WebElement Classfication_attribute;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'remove_true_label')]")
	public WebElement hmcOverride_rightRemove;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'LocalizableAttribute[.stringValue]')]")
	public WebElement hmcOverrid_Value;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AllInstancesSelectEditor[in Attribute[.channel]')]/option[contains(text(),'B2C')]")
	public WebElement hmcOverrid_b2cChannel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AllInstancesSelectEditor[in Attribute[.channel]')]/option[contains(text(),'B2B')]")
	public WebElement hmcOverrid_b2bChannel;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'BooleanEditor[in Attribute[.active]')][contains(@id,'true')]")
	public WebElement hmcOverride_Active;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'select_visible_true_label')]")
	public WebElement hmcOverride_SelectAll;

	// 20390
	@FindBy(how = How.XPATH, using = "//table[@id='Content/McSearchListConfigurable[Order]_innertable']/tbody/tr[contains(@id,'Content/OrganizerListEntry')]")
	public WebElement orederResult;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[Quote.administration]_span']")
	public WebElement orderAdministration;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[Quote.quoteStatus]]_select']")
	public WebElement quoteStatusSelect;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[Quote.quoteStatus]]_select']/option[@value=4]")
	public WebElement quoteStatusSelectOption;
	@FindBy(how = How.XPATH, using = "//div[2]/div[@id='orderList']/button[@id='editBtn']")
	public WebElement editOrder;
	@FindBy(how = How.XPATH, using = "//a[@class='has-nosubmenu']/span")
	public WebElement exitSimulation;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[Quote.quoteStatus]]_select']/option[@value=8]")
	public WebElement quoteStatusSelectConverted;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[NemoMTMVariantProduct.administration]_span']")
	public WebElement catelogAdministration;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Status:')]")
	public WebElement status;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/LocalizableAttribute[NemoMTMVariantProduct.status]]_input']")
	public WebElement statusInput;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'[webDisplayFlag]:')]")
	public WebElement webDisplayFlag;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[NemoMTMVariantProduct.webDisplayFlag]]_select']/option[@value='1']")
	public WebElement webDisplayFlagSelect;
	@FindBy(how = How.XPATH, using = "//button[@id='inspectBtn']")
	public WebElement inspectBtn;
	@FindBy(how = How.XPATH, using = "//select[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[NemoMTMVariantProduct.webDisplayFlag]]_select']/option[@value='0']")
	public WebElement webDisplayFlagChecked;

	// COMM-52
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableB2BQuoteApprovedEmail') and contains(@id,'true')]")
	public WebElement enableB2BQuoteApprovedEmail;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableB2BEmailQuote') and contains(@id,'true')]")
	public WebElement enableB2BEmailQuote;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableB2BQuoteSendEmail') and contains(@id,'true')]")
	public WebElement enableB2BQuoteSendEmail;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableQuoteApproval') and contains(@id,'true')]")
	public WebElement enableQuoteApproval;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableQuoteConvert') and contains(@id,'true')]")
	public WebElement enableQuoteConvert;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableB2BQuoteReviewerRemindSendEmail') and contains(@id,'true')]")
	public WebElement enableB2BQuoteReviewerRemindSendEmail;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enableB2BQuoteReviewerReassignedRemindSendEmail') and contains(@id,'true')]")
	public WebElement enableB2BQuoteReviewerReassignedRemindSendEmail;

	// BROWSE-61
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2CUnit.showFeaturesDifferences]]_true")
	public WebElement B2CUnit_SiteAttribute_HideFeatureDiff_Yes;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2CUnit.showFeaturesDifferences]]_false")
	public WebElement B2CUnit_SiteAttribute_HideFeatureDiff_No;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[CMSSite.showDifferences]]_true")
	public WebElement Websites_Administor_HideFeatureDiff_Yes;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[CMSSite.showDifferences]]_false")
	public WebElement Websites_Administor_HideFeatureDiff_No;

	@FindBy(how = How.XPATH, using = "//span[@id='Content/EditorTab[B2BUnit.administration]_span']")
	public WebElement administration;

	// SHOP-34
	@FindBy(how = How.XPATH, using = "//tr[@title='Product Builder Validate']//a")
	public WebElement productBuilder_productBuilderValidate;
	@FindBy(how = How.ID, using = "catalogVersion")
	public WebElement PBValidate_catalogVersion;
	@FindBy(how = How.ID, using = "siteType")
	public WebElement PBValidate_siteType;
	@FindBy(how = How.ID, using = "machineTypes")
	public WebElement PBValidate_machineTypes;
	@FindBy(how = How.ID, using = "partNumber")
	public WebElement PBValidate_optionCode;
	@FindBy(how = How.ID, using = "store")
	public WebElement PBValidate_store;
	@FindBy(how = How.ID, using = "b2bunit")
	public WebElement PBValidate_b2bunit;
	@FindBy(how = How.ID, using = "validateButton")
	public WebElement PBValidate_validateButton;
	@FindBy(how = How.ID, using = "downloadButton")
	public WebElement PBValidate_downloadButton;
	@FindBy(how = How.XPATH, using = "//div[text()='Processing, please wait …']")
	public WebElement PBValidate_processingMsg;
	@FindBy(how = How.XPATH, using = "//iframe[contains(@src,'showPBValidate')]")
	public WebElement PBValidate_iframe;
	@FindBy(how = How.XPATH, using = "//td[@field='store']/div")
	public List<WebElement> PBValidate_storeColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='unit']/div")
	public List<WebElement> PBValidate_unitColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='machineType']/div")
	public List<WebElement> PBValidate_machineTypeColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='optionPartNum']/div")
	public List<WebElement> PBValidate_optionPartNumColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='optionDesc']/div")
	public List<WebElement> PBValidate_optionDescColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='optionType']/div")
	public List<WebElement> PBValidate_optionTypeColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='baseWarrantyKey']/div")
	public List<WebElement> PBValidate_baseWarrantyKeyColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='supportOS']/div")
	public List<WebElement> PBValidate_supportOSColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='templateName']/div")
	public List<WebElement> PBValidate_templateNameColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='groupName']/div")
	public List<WebElement> PBValidate_groupNameColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='assignedStatus']/div")
	public List<WebElement> PBValidate_assignedStatusColumn;
	@FindBy(how = How.XPATH, using = "//td[@field='validateRuleStatus']/div")
	public List<WebElement> PBValidate_validateRuleStatusColumn;

	// NA-23077
	@FindBy(how = How.XPATH, using = "//td/a[contains(.,'Brazil Price Import')]")
	public WebElement PriceSettings_BrazilPriceImport;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select State')]")
	public WebElement B2BpriceSimulate_StateButton;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select Customer Type')]")
	public WebElement B2BpriceSimulate_CusromerTypeButton;
	@FindBy(how = How.XPATH, using = "//pre[contains(.,'ONSUMER_PUBLIC PriceB2CRuleGroupModel SUCCESS')]")
	public WebElement BrPriceUpload_successResult;

	// 27312
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'EnablePinCode') and contains(@id,'true')]")
	public WebElement EnablePinCode_true;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'group.nemo.cartcheckout')]")
	public WebElement Home_Nemo_cartcheckout;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'NemoIndiaPincode')]")
	public WebElement Home_Nemo_cartcheckout_pincode;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'NemoIndiaPincode')]")
	public WebElement Pincode_search;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zPincode')]")
	public WebElement Pincode_input;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zEnableCashOnDeliveryPayment') and contains(@id,'false')]")
	public WebElement Pincode_EnableCashOnDeliveryPayment_no;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zEnableCashOnDeliveryPayment') and contains(@id,'true')]")
	public WebElement Pincode_EnableCashOnDeliveryPayment_yes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zDeliveryDays')]")
	public WebElement Pincode_zDeliveryDays;
	@FindBy(how = How.XPATH, using = "//a[@title='Results']")
	public WebElement Results;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'addressBookDisplayInCheckout') and contains(@id,'false')]")
	public WebElement addressFromCRM;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),' Price Feed')]")
	public WebElement priceFeed;
	@FindBy(how = How.XPATH, using = "//input[contains(@name,'salesOrg')]")
	public WebElement salesOrg;
	@FindBy(how = How.XPATH, using = "//input[contains(@name,'material')]")
	public WebElement material;
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-success']")
	public WebElement filterBtn;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/NemoOutletTreeNode_label']")
	public WebElement ProductOutletUpload;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/NemoOutletDataFileUploadEditor_upload_div']")
	public WebElement Upload;
	@FindBy(how = How.XPATH, using = "//span[@class='label']")
	public WebElement update;
	@FindBy(how = How.XPATH, using = "//a[@class='controller']")
	public WebElement controller;

	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericExplorerMenuTreeNode[system]_label']")
	public WebElement system;
	@FindBy(how = How.XPATH, using = "//a[@id='Tree/GenericLeafNode[Type]_label']")
	public WebElement types;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/StringEditor[in Content/GenericCondition[Type.code]]_input']")
	public WebElement typeCode;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/StringDisplay[PriceB2C]_span']")
	public WebElement typeCodeResult;
	@FindBy(how = How.XPATH, using = "//img[@id='Content/ImageToolbarAction[open_editor_newwindow]_img']/@src")
	public WebElement open_newwindow;
	@FindBy(how = How.XPATH, using = "//img[@id='GenericItemChip$6[openorganizer]_img']/@src")
	public WebElement open_openorganizer;
	@FindBy(how = How.XPATH, using = "//select[@id='AbstractGenericConditionalSearchToolbarChip$3[attributeselect][PriceB2C]_select']/option[@value='material']")
	public WebElement material_select;
	@FindBy(how = How.XPATH, using = "//select[@id='AbstractGenericConditionalSearchToolbarChip$3[attributeselect][PriceB2C]_select']/option[@value='salesOrg']")
	public WebElement salesOrg_select;
	@FindBy(how = How.XPATH, using = "//select[@id='AbstractGenericConditionalSearchToolbarChip$3[attributeselect][PriceB2C]_select']/option[@value='priceType']")
	public WebElement priceType;
	@FindBy(how = How.XPATH, using = "//input[@id='StringEditor[in GenericCondition[PriceB2C.material]]_input']")
	public WebElement priceB2Cmaterial;
	@FindBy(how = How.XPATH, using = "//input[@id='StringEditor[in GenericCondition[PriceB2C.salesOrg]]_input']")
	public WebElement priceB2CsalesOrg;
	@FindBy(how = How.XPATH, using = "(//table[@id='McSearchListConfigurable[PriceB2C]_innertable']/tbody/tr)[3]")
	public WebElement resultOne;
	@FindBy(how = How.XPATH, using = "//td[@id='McSearchListConfigurable[PriceB2C]_!select_visible_true_label']")
	public WebElement selectAll;
	@FindBy(how = How.XPATH, using = "//img[@id='McSearchListConfigurable[PriceB2C][delete]_img']/@src")
	public WebElement deleteAll;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Select channel...')]")
	public WebElement channelOutlet;
	@FindBy(how = How.XPATH, using = "//div[@class='select2-search']/input")
	public WebElement channelOutletInput1;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.ssoEnabled') and contains(@id,'true')]")
	public WebElement eServiceToggle;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'B2CUnit.enableSocialLogin') and contains(@id,'true')]")
	public WebElement socialLogin;

	@FindBy(how = How.ID, using = "Tree/GenericLeafNode[NemoTransactionLog]_label")
	public WebElement Nemo_transactionlog;
	@FindBy(how = How.ID, using = "Content/StringEditor[in Content/GenericCondition[NemoTransactionLog.orderId]]_input")
	public WebElement Transactionlog_OrderID;
	@FindBy(how = How.ID, using = "Content/NemoSearchConfigurable[NemoTransactionLog]_searchbutton")
	public WebElement Transactionlog_search;

	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Content/AbstractGenericConditionalSearchToolbarChip$3[attributeselect][NemoTransactionLog]_select')]/option[@value='orderId']")
	public WebElement SearchCriteria_OrderID;
	@FindBy(how = How.XPATH, using = "//div[@id='Content/StringDisplay[CYBERSOURCE]_span']")
	public WebElement firstItemInTransactionLog;
	@FindBy(how = How.XPATH, using = "//textarea[contains(@id,'NemoTransactionLog.configSummary')]")
	public WebElement configSummary;

	@FindBy(how = How.XPATH, using = "//select/option[contains(.,'masterMultiCountryProductCatalog - Online')]")
	public WebElement catalog_PMI_MasterMultiCountryProductCatalogOnline;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'zSpecificationImage')][contains(@src,'search')]")
	public WebElement catalog_PMI_specificationImage_search;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'colorSwatchImage')][contains(@src,'search')]")
	public WebElement catalog_PMI_colorSwatchImage_search;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'variantThumbnailImage')][contains(@src,'search')]")
	public WebElement catalog_PMI_variantThumbnailImage_search;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'mkt_image_list')][contains(@src,'search')]")
	public WebElement catalog_PMI_mkt_image_list_search;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'mkt_image_hero')][contains(@src,'search')]")
	public WebElement catalog_PMI_mkt_image_hero_search;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'mkt_video_hero')][contains(@src,'search')]")
	public WebElement catalog_PMI_mkt_video_hero_search;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_GalleryVideoContainer_label')]")
	public WebElement catalog_PMI_mkt_video_hero_Create;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'processor_logo')][contains(@src,'search')]")
	public WebElement catalog_PMI_processor_logo_search;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'_img') and contains(@id,'merchandisingTag')][contains(@src,'search')]")
	public WebElement catalog_PMI_merchandisingTag_search;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_MerchandisingTag_label')]")
	public WebElement catalog_PMI_merchandisingTag_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Product Features')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_ProductFeatures;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_Feature_label')]")
	public WebElement catalog_PMI_ProductFeatures_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Marketing Hero Banner Images')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_MarketingHeroBannerImages;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_GalleryPhotoContainer_label')]")
	public WebElement catalog_PMI_MarketingHeroBannerImages_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Awards')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_Awards;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_Award_label')]")
	public WebElement catalog_PMI_Awards_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Gallery Images')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_GalleryImages;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_GalleryPhotoContainer_label')]")
	public WebElement catalog_PMI_GalleryImages_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Gallery Videos')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_GalleryVideos;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_GalleryVideoContainer_label')]")
	public WebElement catalog_PMI_GalleryVideos_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Whats In The Box')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_WhatsInTheBox;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Custom Tabs')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_CustomTabs;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_CustomTab_label')]")
	public WebElement catalog_PMI_CustomTabS_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'PMI Resources')]/../../td[last()]//table//table/thead//th[3]")
	public WebElement catalog_PMI_PMIResources;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_PMIResourcesComponent_label')]")
	public WebElement catalog_PMI_PMIResources_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Product.ctaButtonLink') and contains(@id,'_img')]")
	public WebElement catalog_PMI_CTAButtonLink_Search;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_NemoLinkComponent_label')]")
	public WebElement catalog_PMI_CTAButtonLink_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Product.productOptiontemplate') and contains(@id,'_img')]")
	public WebElement catalog_PMI_productOptiontemplate_Search;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_ProductOptionTemplate_label')]")
	public WebElement catalog_PMI_productOptiontemplate_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Product.promotedOptions') and contains(@id,'_img')]")
	public WebElement catalog_PMI_promotedOptions_Search;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_PromotedProductOptions_label')]")
	public WebElement catalog_PMI_promotedOptions_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Create new') and contains(@id,'null_null_label')]")
	public WebElement catalog_PMI_TableCreate;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_Media_label')]")
	public WebElement catalog_PMI_TableCreateMedia;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!add_true_labe')]")
	public WebElement catalog_PMI_Add;

	// shop223
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'uid')]")
	public WebElement customers_uid;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'name')]")
	public WebElement customers_name;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'repId')]")
	public WebElement customers_repId;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zTeleUserEmail')]")
	public WebElement customers_zTeleUserEmail;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'save')]")
	public WebElement customers_saveAndCreate;
	// comm-53
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_Address_label')]")
	public WebElement Address_CreateAddress;
	@FindBy(how = How.XPATH, using = ".//*[@id='AdvanceBooleanEditor[in Attribute[Address.billingAddress]]_true']")
	public WebElement Address_setBillingAddress;
	@FindBy(how = How.ID, using = "Content/ConfirmImageToolbarAction[organizer.editor.save.title]_label")
	public WebElement Address_SaveAddress;
	@FindBy(how = How.XPATH, using = ".//*[@id='BooleanEditor[in Attribute[Address.visibleForIndirectSales]]_null']")
	public WebElement Address_visibleforIndirect;
	@FindBy(how = How.XPATH, using = "//input[@id='StringEditor[in Attribute[Address.company]]_input']")
	public WebElement Address_CompanyName;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'Address.region')]")
	public WebElement Address_regionSetting;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Region.isocode')]")
	public WebElement Address_regionISO;

	@FindBy(how = How.ID, using = "ModalGenericFlexibleSearchOrganizerSearch[Region]_searchbutton")
	public WebElement Address_RegionSearch;
	@FindBy(how = How.XPATH, using = ".//*[@id='StringDisplay[South Australia]_span']")
	public WebElement Address_FirstRegion;

	// content-42
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicRegistrationGatekeeperToggle]]_spantrue']")
	public WebElement RegistrationGatekeeperToggleYES;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicReferralURLGatekeeperToggle]]_spanfalse']")
	public WebElement ReferralUrlGateKeeperToggleNO;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.dynamicReferralURLGatekeeperToggle]]_spantrue']")
	public WebElement ReferralUrlGateKeeperToggleYES;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicReferralURLGatekeeper']//table[@class='listtable']//tr[contains(.,'The list is empty.')]")
	public WebElement ReferralURLGatekeeper;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicReferralURLGatekeeper']//table[@class='listtable']//tr[contains(.,'Value')]")
	public WebElement tableValue;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Select all')]")
	public WebElement Selectall;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Remove')]")
	public WebElement Remove;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Create new')]")
	public WebElement Createnew;
	@FindBy(how = How.XPATH, using = "//table[@title='dynamicReferralURLGatekeeper']//table[@class='listtable']//tr//input[contains(@id,'Content/StringEditor[in Content/GenericResortableList')]\n"
			+ "")
	public WebElement ReferralURLGatekeeperinput;

	// browse-62
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!create_NemoManualSubSeriesProduct_label')]")
	public WebElement catalog_Products_manualSub_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Create') and contains(@id,'null_null_label')]")
	public WebElement catalog_Products_rightCreate;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/StringEditor[in Content/Attribute[.code]]_input']")
	public WebElement catalog_Products_articleNum;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'LocalizableAttribute[.name]]_input')]")
	public WebElement catalog_Products_identifier;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/AllInstancesSelectEditor[in Content/Attribute[.catalogversion]]_select']/option[contains(text(),'masterMultiCountryProductCatalog - Online')]")
	public WebElement catalog_Products_CatalogOnlineVersion;
	@FindBy(how = How.XPATH, using = ".//*[@id='Content/EnumerationValueSelectEditor[in Content/Attribute[.approvalStatus]]_select']/option[contains(text(),'approved')]")
	public WebElement catalog_Products_Approval;
	@FindBy(how = How.XPATH, using = ".//*[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[.variantType]]']")
	public WebElement catalog_Products_VariantsType;

	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Group PMI Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIOverride_GroupAttribute;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'create_PmiAttributeOverrideRules_label')]")
	public WebElement PMIOverride_GroupAttribute_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'descriptorAttribute')]/option[contains(text(),'mkt_name')]")
	public WebElement PMIOverride_MktName_Attribute;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'LocalizableAttribute[.stringValue]')]")
	public WebElement PMIOverride_MktName_StringValue;

	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Group PMI Text Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIOverride_GroupText;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'create_PmiTextAttributeOverrideRules_label')]")
	public WebElement PMIOverride_GroupText_Create;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'descriptorAttribute')]/option[contains(text(),'mkt_desc_long')]")
	public WebElement PMIOverride_DesLong_Attribute;
	@FindBy(how = How.XPATH, using = ".//*[@id='tinymce']")
	public WebElement PMIOverride_DesLong_TextValue;

	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'descriptorAttribute')]/option[contains(text(),'merchandisingTag')]")
	public WebElement PMIOverride_merchandisingTag_Attribute;
	@FindBy(how = How.XPATH, using = ".//img[contains(@id,'merchandisingTag')]")
	public WebElement PMIOverride_merchandisingTag_Search;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'MerchandisingTag.pk')]")
	public WebElement PMIOverride_merchandisingTag_PK;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'MerchandisingTag.pk')]")
	public WebElement PMIOverride_merchandisingTag_Result;

	@FindBy(how = How.XPATH, using = "//span[contains(.,'Category System')]")
	public WebElement Catalog_CategoryTab;
	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Supercategories:')]/../../td[last()]//table//div[text()='Identifier']")
	public WebElement Catalog_Category_SuperCategories;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!copy_true_label')]")
	public WebElement Catalog_copy;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'!paste_true_label')]")
	public WebElement Catalog_paste;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Administration')]")
	public WebElement Catalog_AdminTab;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Properties')]")
	public WebElement Catalog_PropertiesTab;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'.mkt_baseProduct')]")
	public WebElement Catalog_mktBaseProduct;

	// shop117
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'systemtools')]")
	public WebElement System_tools;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'ImpExImportWizard')]")
	public WebElement Tools_import;
	@FindBy(how = How.XPATH, using = "//a[@title='Upload']//img[contains(@id,'UploadToImpExMediaEditor')]")
	public WebElement Import_importImpexIcon;
	@FindBy(how = How.XPATH, using = "//input[@name='file']")
	public WebElement ImportImpex_selectFile;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Upload')]")
	public WebElement ImportImpex_uploadBtn;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'start')]")
	public WebElement ImportImpex_startBtn;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'resultlist_GenericItemList')]//td[5]")
	public WebElement ImportImpex_status;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'resultlist_GenericItemList')]//td[6]")
	public WebElement ImportImpex_result;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'ProductReference')]")
	public WebElement Marketing_productReference;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'ProductReference.source')]")
	public WebElement ProductReference_source;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'ProductReference.target')]")
	public WebElement ProductReference_target;
	@FindBy(how = How.XPATH, using = "//a[@title='Search']/span[contains(@id,'ProductReference')]")
	public WebElement ProductReference_search;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Product.code')]")
	public WebElement ProductReference_articleNumber;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'Product.catalogVersion') and contains(@id,'AllInstancesSelectEditor')]")
	public WebElement ProductReference_catalogVersion;
	@FindBy(how = How.XPATH, using = "//a[@title='Search']")
	public WebElement ProductReference_productSearch;
	@FindBy(how = How.XPATH, using = "//a[@title='Use']")
	public WebElement ProductReference_productUse;
	@FindBy(how = How.XPATH, using = "//td[@class='olcEmpty']")
	public WebElement ProductReference_noResults;
	@FindBy(how = How.XPATH, using = "//a[@title='Open Editor']")
	public WebElement ProductReference_openEditor;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'.delete')]")
	public WebElement ProductReference_editorDelete;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Result of import')]")
	public WebElement ImportImpex_finishMsg;

	// shop 220
	@FindBy(how = How.XPATH, using = "//td[@class='list']//*[@id='container']//i")
	public WebElement B2CPriceSimulator_listGroup;

	// Browse 103
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_true")
	public WebElement B2BUNIT_CustomiseForCM_Yes;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_true")
	public WebElement B2BUNIT_CustomiseBuy_Yes;
	@FindBy(how = How.ID, using = "Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyForCMToggle]]_false")
	public WebElement B2BUNIT_CustomiseForCM_No;

	// COMM175
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'zEnableCheckoutRedesignForBackgroundLogic]]_true') ]")
	public WebElement CheckoutNew_yes;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'zEnableCheckoutRedesignForBackgroundLogic]]_false') ]")
	public WebElement CheckoutNew_no;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'attributeselect')]/option[@value='pk']")
	public WebElement WCMS_PAGE_PK;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'AbstractPage.pk')]")
	public WebElement WCMS_PAGE_PKInput;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'masterTemplate')]")
	public WebElement PageTemplate;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'PageTemplate.name')]")
	public WebElement PageTemplate_name;
	@FindBy(how = How.XPATH, using = "//table[@class = 'listtable']//select/option[contains(.,'masterContentCatalog - Online')]")
	public WebElement PageTemplate_catalog;
	@FindBy(how = How.XPATH, using = "(//*[contains(@id,'resultlist')]//td[5])[1]")
	public WebElement PageTemplate_firstResult;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'masterTemplate')]")
	public WebElement PageTemplate_input;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableSubscriptionOrder') and @value='true']")
	public WebElement paymentProfile_globalSubscriptionYes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'disableSubscriptionOrder') and @value='false']")
	public WebElement paymentProfile_globalSubscriptionNo;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'attributeselect')]/option[@value='name']")
	public WebElement paymentProfile_Select_name;

	// shope-344
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.customiseBuyToggle]]_spantrue']")
	public WebElement SiteAttribute_CustomiseBuyToggle;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'B2BUnit.customiseBuyForCMToggle')]")
	public WebElement SiteAttribute_CustomiseBuyForCMToggle;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.enableProductBuilder]]_true']")
	public WebElement SiteAttribute_enableProductBuilder;

	// shop 215
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'SubscriptionOption.billingCycle')]")
	public WebElement Products_billingCycle;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'SubscriptionOption.billingCycle')]//option[@selected]")
	public WebElement Products_billingCycleSelected;
	@FindBy(how = How.XPATH, using = "//td[@class='condition']//select[contains(@id,'zChannel')]")
	public WebElement Message_zChannel;
	@FindBy(how = How.XPATH, using = "//td[@class='condition']//select[contains(@id,'zLanguage')]")
	public WebElement Message_zLanguage;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NemoMessage.zValue')]")
	public WebElement Message_zValue;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'.administration')]")
	public WebElement Products_administration;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'newPBExperience') and contains(@id,'false')]")
	public WebElement WCMS_newPBExperienceFalse;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'newPBExperience') and contains(@id,'true')]")
	public WebElement WCMS_newPBExperienceTrue;

	// shope-343
	@FindBy(how = How.XPATH, using = "//div/a[contains(.,'Price Feed')]")
	public WebElement PriceSetting_PriceFeed;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'C&V prices') and @role='tab']")
	public WebElement PrieFeed_CVListPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='cvTableTab']/div//div/input[@name='material']")
	public WebElement CVListPrice_Material;
	@FindBy(how = How.XPATH, using = "//div[@id='cvTableTab']/div//div/input[@name='characteristic']")
	public WebElement CVListPrice_CInput;
	@FindBy(how = How.XPATH, using = "//div[@id='cvTableTab']/div//div/input[@name='charValue']")
	public WebElement CVListPrice_VInput;
	@FindBy(how = How.XPATH, using = "//div[@id='cvTableTab']/div[@class='table-holder']/table//tr/td[@class='price web']")
	public List<WebElement> CVListPrice_WebPrice;

	// shope-206
	@FindBy(how = How.XPATH, using = "//a/span[text()='Select zUnit...']")
	public WebElement PriceTier_SelectzUnit;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Small Business Portal Japan')]/following-sibling::td/a[contains(.,'Delete')]")
	public List<WebElement> PriceTier_DeleteTier;
	@FindBy(how = How.XPATH, using = "//div/a[contains(.,'Add New Customer Groups')]")
	public WebElement PriceTier_AddCustomerGroup;
	@FindBy(how = How.XPATH, using = "//div/a[contains(@class,'deleteCustomerGroups')]")
	public WebElement PriceTier_DeleteCustomerGroup;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'saveNewCustomerGroup')]")
	public WebElement PriceTier_SaveAddCustomerGroup;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'saveNewCustomerGroup')]/../button[text()='Close']")
	public WebElement PriceTier_CloseAddCustomerGroup;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Upload ') and @role='tab']")
	public WebElement PriceTier_UploadCustomerGroup;
	@FindBy(how = How.XPATH, using = "//input[@id='customergroupsFile']")
	public WebElement PriceTier_ChooseFile;
	@FindBy(how = How.XPATH, using = "//form[@id='deleteCustomerGroupsForm']/input")
	public WebElement PriceTier_ChooseFileDele;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'validateGroup')]")
	public WebElement PriceTier_DelValidate;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'confirmDelete')]")
	public WebElement PriceTier_DelConfirm;
	@FindBy(how = How.XPATH, using = "//select[@id='customerGroupEntry']")
	public WebElement PriceTier_ShowEntrys;
	@FindBy(how = How.XPATH, using = "//tr[@class='jsgrid-header-row']/th[text()='CUSTOMER GROUP']")
	public WebElement PriceTier_CompanyGroup;
	@FindBy(how = How.XPATH, using = "//tr[@class='jsgrid-header-row']/th[text()='COMPANY NAME']")
	public WebElement PriceTier_CompanyName;
	@FindBy(how = How.XPATH, using = "//tr[@class='jsgrid-filter-row']/td[1]/input")
	public WebElement PriceTier_GroupSearchBox;
	@FindBy(how = How.XPATH, using = "//tr[@class='jsgrid-filter-row']/td[2]/input")
	public WebElement PriceTier_NameSearchBox;
	@FindBy(how = How.XPATH, using = "//tr[@class='jsgrid-filter-row']/td[3]/input[contains(@class,'search-button')]")
	public WebElement PriceTier_CompanySearch;
	@FindBy(how = How.XPATH, using = "//tr[@class='jsgrid-filter-row']/td[3]/input[contains(@class,'clear-filter-button')]")
	public WebElement PriceTier_CompanyClearFiter;

	// COMM366
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'enable3DSecure') and @value='true']")
	public WebElement enable3DSecure_yes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'bypass3DSecure') and @value='true']")
	public WebElement bypass3DSecure_yes;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'bypass3DSecure') and @value='false']")
	public WebElement bypass3DSecure_no;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zPostJaccsData')]")
	public WebElement zPostJaccsData;

	// content409
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip']//div[contains(.,'Show Subscription:')]")
	public WebElement showSubscription;
	@FindBy(how = How.XPATH, using = "//td[@id='Tree/GenericLeafNode[B2BUnit]_!create_B2BUnit_label']")
	public WebElement create_B2BUnit;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/BooleanEditor[in Content/Attribute[.zB2BShowSubscription]]_false']")
	public WebElement zB2BShowSubscription;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.zB2BShowSubscription]]_spantrue']")
	public WebElement zB2BShowSubscriptionTrue;
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and @title='zB2BShowSubscription']//label[contains(.,'Blank-out Inheritance')]")
	public WebElement zB2BShowSubscriptionCheckbox;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.zB2BShowSubscription]]_spannull']")
	public WebElement zB2BShowSubscriptionNull;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'It is inherited from \"1213348423\"')]")
	public WebElement zB2BShowSubscriptionText;
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and @title='zB2BShowSubscription']//input[@type='checkbox']")
	public WebElement zB2BShowSubscriptionType;
	@FindBy(how = How.XPATH, using = "//td[@id='Tree/GenericLeafNode[B2BUnit]_null_null_label']")
	public WebElement create;
	@FindBy(how = How.XPATH, using = "//table[@class='attributeChip' and @title='zB2BShowSubscription']//input[@type='hidden']")
	public WebElement hidden;

	// content402
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'NemoSMBCustomerGroup.repId')]")
	public WebElement repIdSMBCustomerGroup;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Rep. Id:')]")
	public WebElement repIdSMB;
	@FindBy(how = How.XPATH, using = "//select[@id='template']/option[@value='jp_smb_unit']")
	public WebElement sMBGeneralUnit;
	@FindBy(how = How.XPATH, using = "//*[@id='repId']")
	public WebElement repIdSmbSetUp;
	@FindBy(how = How.XPATH, using = "//div[@class='panel window']/div[contains(@class,'messager-body panel-body panel-body-noborder window-body')]")
	public WebElement panelWindow;

	// browse-244
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'SubSeriesProduct.tab.product.PMI]_span')]")
	public WebElement subseries_PMItab;
	@FindBy(how = How.XPATH, using = "//option[contains(.,'available')]")
	public WebElement productStatus_available;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'Order.zPayload')]")
	public WebElement order_Administration_zPayload;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'zPayloadContent')]")
	public WebElement order_Administration_zPayloadContent;

	// shope 293
	@FindBy(how = How.XPATH, using = "//td[@id='Tree/GenericLeafNode[Product]_null_null_label']")
	public WebElement Products_create;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_NemoConvenienceBundle_label')]")
	public WebElement Products_create_NemoConvenienceBundle;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.code') or contains(@id,'.uid')]")
	public WebElement Products_articleNumber;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.name')]")
	public WebElement Products_Identifier;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'.approvalStatus')]")
	public WebElement Products_approval;
	@FindBy(how = How.XPATH, using = "(//select[contains(@id,'.catalogversion') or contains(@id,'.catalogVersion')])[last()]")
	public WebElement Products_catalogversion;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.leadingProduct]')]")
	public WebElement Products_leadingProduct;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.leadingProductQuantity')]")
	public WebElement Products_leadingProductQuantity;
	@FindBy(how = How.XPATH, using = "//table[contains(@oncontextmenu,'Create Product reference')]//thead")
	public WebElement Products_productReference;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_ProductReference_label')]")
	public WebElement Products_createProductReference;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.target')]")
	public WebElement CreateProductReference_targetProduct;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'.referenceType')]")
	public WebElement CreateProductReference_referenceType;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'.active')]")
	public WebElement CreateProductReference_active;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'.target')]")
	public WebElement CreateProductReference_targetProductSearch;
	@FindBy(how = How.XPATH, using = "//table[@title='allowedUnit - Display to']//a[contains(@name,'open_search')]")
	public WebElement Products_displayToSearch;
	@FindBy(how = How.XPATH, using = "//table[@title='baseProduct']//a[contains(@name,'open_search')]")
	public WebElement Products_baseProductSearch;
	@FindBy(how = How.XPATH, using = "//table[contains(@title,'leadingProduct')]//a[contains(@name,'open_search')]")
	public WebElement Products_leadingProductSearch;
	@FindBy(how = How.XPATH, using = "//a[contains(@title,'tab.multicountry ')]")
	public WebElement Products_multicountry;
	@FindBy(how = How.XPATH, using = "//table[@title='availability']")
	public WebElement Products_availabilityTable;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'create_AvailabilityRules_label')]")
	public WebElement Products_createAvailabilityRules;
	@FindBy(how = How.XPATH, using = "//table[@title='availability']//option[contains(text(),'B2C')]")
	public WebElement Availability_channelB2C;
	@FindBy(how = How.XPATH, using = "//table[@title='availability']//option[contains(text(),'APPROVED')]")
	public WebElement Availability_approvalApproved;
	@FindBy(how = How.XPATH, using = "(//table[@title='availability']//input[contains(@id,'date')])[1]")
	public WebElement Availability_onlineDate;
	@FindBy(how = How.XPATH, using = "(//table[@title='availability']//input[contains(@id,'date')])[2]")
	public WebElement Availability_offlineDate;
	@FindBy(how = How.XPATH, using = "//a[@title='Use']")
	public WebElement Products_useBtn;
	@FindBy(how = How.XPATH, using = "//a[@title='Search']")
	public WebElement Products_searchBtn;

	// browse-540
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'Product.tab.attributeoverride]')]")
	public WebElement product_AttributeOverrideTab;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'classattributeoverride]')]")
	public WebElement ClassificationAttributeOverrideTab;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'tab.product.multimedia')]")
	public WebElement MultimediaTab;
	@FindBy(how = How.XPATH, using = "//td[contains(@id,'clear_true_label')]")
	public WebElement Multimedia_clear;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'NemoLocalizableAttribute')]")
	public WebElement stringValue_Expland;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'ko')]/../../td[last()]//input")
	public WebElement KO_StringValue;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'StringEditor[in NemoLocalizableAttribute')]")
	public WebElement EN_StringValue;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'NemoLocalizableAttribute')]")
	public WebElement stringValue_Collapse;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'ajaxinput_AutocompleteReferenceEditor[in Attribute[.group]')]")
	public WebElement select_JPGroup;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'channel')]/option[contains(text(),'B2C')]")
	public WebElement select_JPChannel;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'country')]/option[contains(text(),'JP')]")
	public WebElement select_JPCountry;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'region')]/option[contains(text(),'Global')]")
	public WebElement select_GlobalRegion;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'[.active]')][contains(text(),'Yes')]")
	public WebElement Active;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'product.properties')]")
	public WebElement product_PropertiesTab;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'OptionCompatibility.description')]")
	public WebElement description_Expland;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en')]/../../td[last()]//iframe")
	public WebElement description_enFrame;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'en_GB')]/../../td[last()]//iframe")
	public WebElement description_enGBFrame;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'ja')]/../../td[last()]//iframe")
	public WebElement description_jaFrame;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'ja_JP')]/../../td[last()]//iframe")
	public WebElement description_jaJPFrame;
	@FindBy(how = How.XPATH, using = "//body[contains(@id,'tinymce')]")
	public WebElement description_value;

	@FindBy(how = How.XPATH, using = "//div[contains(@id,'Content/ItemDisplay[jppublic_unit]')]")
	public WebElement AttributeOverride_groupResult;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'descriptorAttribute')]/option[contains(text(),'summary')]")
	public WebElement AttributeOverride_attribute_Summary;

	@FindBy(how = How.XPATH, using = "//div[contains(@id,'Content/ItemDisplay[B2C]')]")
	public WebElement Classification_channelResult;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'Content/ItemDisplay[Global]')]")
	public WebElement Classfication_regionResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Region Class Attribute Override:')]/../..//div[text()='Classification Attribute']")
	public WebElement Classfication_regionOverride;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'ClassAttribute')]/option[contains(text(),'Height - Accessory Attributes')]")
	public WebElement Classification_attribute_Height;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Group PMI Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Overrides_groupResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Channel PMI Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Overrides_channelResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Country PMI Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Overrides_countryResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Region PMI Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Overrides_regionResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Region PMI Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIOverride_RegionAttribute;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Group PMI Text Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Text_groupResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Channel PMI Text Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Text_channelResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Country PMI Text Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Text_countryResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'ko')]/../../td[last()]//iframe")
	public WebElement KO_PMITextValue;
	@FindBy(how = How.XPATH, using = "//iframe[contains(@id,'setvalue_ifr')]")
	public WebElement EN_PMITextValue;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Channel PMI Text Attribute Override:')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIText_ChannelAttribute;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Country PMI Text Attribute Override:')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIText_CountryAttribute;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Group PMI Media Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Media_groupResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Channel PMI Media Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Media_channelResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Group PMI Media Attribute Override:')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIOverride_GroupMedia;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'create_PmiMediaAttributeOverrideRules_label')]")
	public WebElement PMIOverride_GroupMedia_Create;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Channel PMI Media Attribute Override:')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIOverride_ChannelMedia;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AttributeOverrideSelectEditor[in Attribute')]/option[contains(text(),'mkt_image_list')]")
	public WebElement PMIOverride_Mktimgist_Attribute;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'ko')]/../../td[last()]//a")
	public WebElement KO_PMIMediaValue;
	@FindBy(how = How.XPATH, using = "//img[contains(@id,'MediaEditor[in NemoLocalizableAttribute[.mediaValue]')]")
	public WebElement EN_PMIMediaValue;
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Use')]")
	public WebElement PMIMediaUseButton;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Group PMI Collection Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Collection_groupResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Channel PMI Collection Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Collection_channelResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Country PMI Collection Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Collection_countryResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Region PMI Collection Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Collection_regionResult;
	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Channel PMI Collection Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMICollection_channelAttribute;
	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Region PMI Collection Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMICollection_regionAttribute;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AttributeOverrideSelectEditor')]/option[contains(text(),'productStatus')]")
	public WebElement PMICollection_productStatus_Attribute;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'[.productStatus]')]/option[contains(text(),'SoldOut')]")
	public WebElement PMICollection_productStatus_soldout;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'[.productStatus]')]/option[contains(text(),'available')]")
	public WebElement PMICollection_productStatus_available;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Group PMI Boolean Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Boolean_groupResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Channel PMI Boolean Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Boolean_channelResult;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Country PMI Boolean Attribute Override:')]/../../td/table//a[contains(@title,'Open Editor')]")
	public WebElement PMI_Boolean_countryResult;
	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Channel PMI Boolean Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIBoolean_channelAttribute;
	@FindBy(how = How.XPATH, using = ".//div[contains(text(),'Country PMI Boolean Attribute Override')]/../../td[last()]/table//table/tbody/tr[1]//div[text()='Attribute']")
	public WebElement PMIBoolean_countryAttribute;
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AttributeOverrideSelectEditor[in Attribute')]/option[contains(text(),'showTechSpecs')]")
	public WebElement PMIBoolean_showTechSpecs_Attribute;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'booleanValue')][contains(text(),'No')]")
	public WebElement PMICollection_showTechSpecs_No;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'booleanValue')][contains(text(),'Yes')]")
	public WebElement PMICollection_showTechSpecs_Yes;
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'descriptorAttribute')]/option[contains(text(),'mkt_desc_short')]")
	public WebElement PMIOverride_DesShort_Attribute;

	// content-464
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enable ATP Service')]")
	public WebElement atpService;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.zEnableATPService]]_spantrue']")
	public WebElement zEnableATPService;
	@FindBy(how = How.XPATH, using = "//input[@id='Content/IntegerEditor[in Content/Attribute[B2CUnit.zTimeSpanForATP]]_input']")
	public WebElement zTimeSpanForATP;
	@FindBy(how = How.XPATH, using = "//span[@id='Content/BooleanEditor[in Content/Attribute[B2CUnit.zEnableATPService]]_spanfalse']")
	public WebElement zEnableATPServiceNo;
	
	//comm-631
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'Attribute[.reserved]]_input') or contains(@id,'Attribute[StockLevel.reserved]]_input')]")
	public WebElement reserveWareHouseAmount;
	
	
}