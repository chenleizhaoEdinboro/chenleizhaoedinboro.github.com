package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class B2BPage {
	public WebDriver PageDriver;

	public B2BPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}

	// Login
	@FindBy(how = How.ID, using = "login.email.id")
	public WebElement Login_EmailTextBox;
	@FindBy(how = How.ID, using = "login.password")
	public WebElement Login_PasswordTextBox;
	@FindBy(how = How.XPATH, using = "//button[contains(.,'Sign In')]")
	public WebElement Login_SignInButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//div/button[contains(.,'Create an Account')]")
	public WebElement Login_CreateAnAccountButton;
	@FindBy(how = How.XPATH, using = ".//a[contains(@href, 'ovp.lenovo.com')]")
	public WebElement MyAccount_OVPLink;
	@FindBy(how = How.ID, using = "queryButton")
	public WebElement OVP_CheckStatusButton;

	// Register
	@FindBy(how = How.XPATH, using = "(.//div[contains(@class,'expandable-hitarea')])[1]")
	public WebElement Register_AccessLevelExpand;
	@FindBy(how = How.ID, using = "register.email")
	public WebElement Register_EmailTextBox;
	@FindBy(how = How.ID, using = "register.chkEmail")
	public WebElement Register_ConfirmEmailTextBox;
	@FindBy(how = How.ID, using = "register.firstName")
	public WebElement Register_FirstNameTextBox;
	@FindBy(how = How.ID, using = "register.lastName")
	public WebElement Register_LastNameTextBox;
	@FindBy(how = How.ID, using = "password")
	public WebElement Register_PasswordTextBox;
	@FindBy(how = How.ID, using = "register.checkPwd")
	public WebElement Register_ConfirmPasswordTextBox;
	@FindBy(how = How.XPATH, using = "//input[@type='checkbox'][@name='optIn']")
	public WebElement Register_AgreeOptinCheckBox;
	@FindBy(how = How.XPATH, using = "//button[contains(.,'Create My Account')]")
	public WebElement Register_CreateAccountButton;

	// Monitor page
	@FindBy(how = How.XPATH, using = ".//*[@id='longscroll-subseries']/nav/span/a[last()]/span")
	public WebElement Monitor_NewNavigationSummaryLabel;
	@FindBy(how = How.XPATH, using = ".//*[@id='longscroll-subseries']//div[contains(@class,'titleSection')]")
	public WebElement Monitor_NewSummaryLabel;
	@FindBy(how = How.XPATH, using = ".//span[contains(@itemtype,'Breadcrumb') and @itemprop='child']/span")
	public WebElement Monitor_NavigationSummaryLabel;
	@FindBy(how = How.XPATH, using = ".//div[@class='accessoriesDetail-description']/p[1]")
	public WebElement Monitor_SummaryLabel;

	// added by gaopan
	@FindBy(how = How.XPATH, using = "//span[text()='Cart']/..")
	public WebElement HomePage_CartIcon;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Empty cart')]")
	public WebElement cartPage_emptyCartButton;
	@FindBy(how = How.XPATH, using = "//*[@id='quickOrderProductId']")
	public WebElement cartPage_quickOrder;
	@FindBy(how = How.XPATH, using = "//*[@id='quickAddInput']/a")
	public WebElement cartPage_addButton;
	@FindBy(how = How.XPATH, using = "//*[@id='savedcart_button_new']")
	public WebElement cartPage_saveCartButton;
	@FindBy(how = How.XPATH, using = "//*[@id='companySaveCart']")
	public WebElement cartPage_companySaveCartButton;
	@FindBy(how = How.XPATH, using = "//span[text()='Products']")
	public WebElement HomePage_productsLink;
	@FindBy(how = How.XPATH, using = "//*[@id='realsavecartname']")
	public WebElement cartPage_SaveCart_cartNameField;
	@FindBy(how = How.XPATH, using = ".//*[@id='addToCartButtonTop']")
	public WebElement cartPage_SaveCart_save;
	@FindBy(how = How.XPATH, using = "(//a/span[text()='Sign Out'])[1]")
	public WebElement homepage_Signout;
	@FindBy(how = How.XPATH, using = "(.//span[@class='link-title'])[contains(.,'My Account')]")
	public WebElement homepage_MyAccount;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//a[contains(.,'Carts History')]")
	public WebElement myAccountPage_viewCartHistory;

	@FindBy(how = How.XPATH, using = "//*[contains(@href,'quote')]")
	public WebElement myAccountPage_ViewQuotehistory;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']//a[contains(@href,'quote-approval')]")
	public WebElement myAccountPage_viewQuoteRequireApproval;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']//a[contains(@href,'/approval')]")

	public WebElement myAccountPage_viewOrderRequireApproval;
	@FindBy(how = How.XPATH, using = "//*[@id='openCartButton']")
	public WebElement cartDetailsPage_openCart;
	@FindBy(how = How.XPATH, using = ".//*[@id='quantity0']")
	public WebElement cartPage_Quantity;
	@FindBy(how = How.XPATH, using = ".//*[@id='QuantityProduct_0']")
	public WebElement cartPage_QuantityUpdate;
	@FindBy(how = How.XPATH, using = ".//*[@id='quantity1']")
	public WebElement cartPage_Quantity2;
	@FindBy(how = How.XPATH, using = ".//*[@id='QuantityProduct_1']")
	public WebElement cartPage_QuantityUpdate2;
	@FindBy(how = How.XPATH, using = ".//*[@id='quantity2']")
	public WebElement cartPage_Quantity3;
	@FindBy(how = How.XPATH, using = ".//*[@id='QuantityProduct_2']")
	public WebElement cartPage_QuantityUpdate3;
	@FindBy(how = How.XPATH, using = "//dl[@class='cart-summary-pricingTotal']/dd")
	public WebElement cartPage_TotalPrice;
	@FindBy(how = How.XPATH, using = "//*[@id='validateDateformatForCheckout']")
	public WebElement cartPage_LenovoCheckout;
	@FindBy(how = How.XPATH, using = "//dl[@class='order_totals']/dd[1]")
	public WebElement shippingPage_subtotalPrice;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Edit')]")
	public WebElement shippingPage_EditCart;
	@FindBy(how = How.XPATH, using = ".//*[@id='firstName']")
	public WebElement shippingPage_ShipFName;
	@FindBy(how = How.XPATH, using = ".//*[@id='lastName']")
	public WebElement shippingPage_ShipLName;
	@FindBy(how = How.XPATH, using = ".//*[@id='company']")
	public WebElement shippingPage_CompanyName;
	@FindBy(how = How.XPATH, using = ".//*[@id='line1']")
	public WebElement shippingPage_AddressLine1;
	@FindBy(how = How.XPATH, using = ".//*[@id='townCity']")
	public WebElement shippingPage_CityOrSuburb;
	@FindBy(how = How.XPATH, using = ".//*[@id='state']")
	public WebElement shippingPage_State;
	@FindBy(how = How.XPATH, using = ".//*[@id='postcode']")
	public WebElement shippingPage_PostCode;
	@FindBy(how = How.XPATH, using = ".//*[@id='GROUPSHIPPING']/input")
	public WebElement shippingPage_GroupShipping;
	@FindBy(how = How.XPATH, using = ".//*[@id='addressiframe']")
	public WebElement shippingPage_GroupShippingIframe;
	@FindBy(how = How.XPATH, using = ".//*[@id='groupShippingContinueButton']")
	public WebElement shippingPage_GroupShippingContinueButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='phone1']")
	public WebElement shippingPage_ShipContactNumber;
	@FindBy(how = How.XPATH, using = ".//*[@id='checkoutForm-shippingContinueButton']")
	public WebElement shippingPage_ContinueToPayment;
	@FindBy(how = How.ID, using = "checkout_validateFrom_ok")
	public WebElement shippingPage_validateFromOk;
	@FindBy(how = How.XPATH, using = "//label[contains(.,'Expedited Shipping')]/../input")
	public WebElement shippingPage_expeditedShipping;
	@FindBy(how = How.XPATH, using = "//dd[@class='summary-shipping-cost']")
	public WebElement shippingPage_deliveryPrice;
	@FindBy(how = How.XPATH, using = "//dd[@class='total checkout-orderSummary-pricingTotal-amount']")
	public WebElement shippingPage_totalPrice;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_CARD']")
	public WebElement paymentPage_creditCardRadio;
	@FindBy(how = How.XPATH, using = ".//*[@id='Paymetric_CreditCardType']")
	public WebElement paymentPage_CardType;
	@FindBy(how = How.ID, using = "creditcardiframe0")
	public WebElement creditCardFrame;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'Visa')]")
	public WebElement paymentPage_Visa;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cardnumber']")
	public WebElement paymentPage_CardNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='c-exmth']")
	public WebElement paymentPage_ExpiryMonth;
	@FindBy(how = How.XPATH, using = "//input[@id='c-exyr']")
	public WebElement paymentPage_ExpiryYear;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cvv']")
	public WebElement paymentPage_SecurityCode;
	@FindBy(how = How.XPATH, using = "//input[@id='card_nameOnCard']")
	public WebElement paymentPage_NameonCard;
	@FindBy(how = How.XPATH, using = "//input[@name='external.field.password']")
	public WebElement paymentPage_VisaPassword;
	@FindBy(how = How.XPATH, using = "//input[@name='UsernamePasswordEntry']")
	public WebElement paymentPage_VisaSubmit;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_PURCHASEORDER']")
	public WebElement paymentPage_PurchaseOrder;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderNumber']")
	public WebElement paymentPage_purchaseNum;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderDate']")
	public WebElement paymentPage_purchaseDate;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.firstName']")
	public WebElement paymentPage_FirstName;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.lastname']")
	public WebElement paymentPage_LastName;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.phoneNumber1']")
	public WebElement paymentPage_Phone;
	@FindBy(how = How.XPATH, using = "//*[@id='add-payment-method-continue']")
	public WebElement paymentPage_ContinueToPlaceOrder;
	@FindBy(how = How.XPATH, using = "//input[@id='resellerID' and @type!='hidden']")
	public WebElement placeOrderPage_ResellerID;
	@FindBy(how = How.XPATH, using = "//input[@id='Terms1']")
	public WebElement placeOrderPage_Terms;
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Place Order')]")
	public WebElement placeOrderPage_PlaceOrder;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.line1']")
	public WebElement paymentPage_addressLine1;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.line2']")
	public WebElement paymentPage_addressLine2;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.townCity']")
	public WebElement paymentPage_cityOrSuburb;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.region']")
	public WebElement paymentPage_addressState;
	@FindBy(how = How.XPATH, using = ".//*[@id='address.postcode']")
	public WebElement paymentPage_addressPostcode;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Accessories & Upgrades')]")
	public WebElement HomePage_AccessoriesLink;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Start Assisted Service Session')]")
	public WebElement MyAccountPage_StartAssSerSession;
	@FindBy(how = How.XPATH, using = "//*[@id='asmLoginForm']/fieldset/div[2]/input")
	public WebElement MyAccountPage_ASMPassword;
	@FindBy(how = How.XPATH, using = "(.//button[@class='ASM-btn ASM-btn-login'])[@type='submit'][contains(.,'Sign In')]")
	public WebElement MyAccountPage_ASMLogIn;
	@FindBy(how = How.XPATH, using = "//*[@id='customerFilter']")
	public WebElement MyAccountPage_CustomerIDBox;
	@FindBy(how = How.XPATH, using = "(.//button[@class='ASM-btn ASM-btn-start-session vAlignTop'])[@type='submit']")
	public WebElement MyAccountPage_StartSessionButton;
	@FindBy(how = How.XPATH, using = "(.//*[@id='specialH3ForCustomizedSolr'])[contains(.,'Contract & Agreement')]")
	public WebElement productPage_agreementsAndContract;
	@FindBy(how = How.XPATH, using = "//form/label[contains(.,'Agreement')]")
	public WebElement productPage_radioAgreementButton;
	@FindBy(how = How.XPATH, using = "(//label[contains(.,'Contract')]/input)[last()]")
	public WebElement productPage_raidoContractButton;
	@FindBy(how = How.XPATH, using = "(//div[@class='agreementContract'])[contains(.,'Agreement')]")
	public WebElement productPage_AgreementBtn;
	@FindBy(how = How.XPATH, using = "(.//div[@class='agreementContract'])[contains(.,'Contract')]")
	public WebElement productPage_ContractBtn;
	@FindBy(how = How.XPATH, using = "//div/button[contains(@class,'add-to-cart')]/span[contains(.,'Add to cart')]")
	public WebElement Agreement_agreementsAddToCart;
	@FindBy(how = How.CSS, using = "div>button.pricingSummary-button.button-called-out.button-full")
	public WebElement Agreement_addToCartAccessoryBtn;
	@FindBy(how = How.CSS, using = ".button-called-out.button-full.button-bottom-margin")
	public WebElement productPage_addToCart;
	@FindBy(how = How.XPATH, using = "(//aside/div[@class='addtoCartCTA']/button)[contains(.,'Add to Cart')][last()]")
	public WebElement productPage_AlertAddToCart;
	@FindBy(how = How.XPATH, using = "(//input[@class='cartDetails-overriddenPrice'])[@type='text']")
	public WebElement cartPage_PriceOverrideBox;
	@FindBy(how = How.XPATH, using = "//*[@id='reasonCode0']")
	public WebElement cartPage_PriceOverrideDropDown;
	@FindBy(how = How.XPATH, using = "//*[@id='reasonCode0']/option[2]")
	public WebElement cartPage_PriceOverrideDropDownSelection;
	@FindBy(how = How.XPATH, using = "//*[@id='reasonText']")
	public WebElement cartPage_PriceOverrideReason;
	@FindBy(how = How.XPATH, using = "//*[@id='updatePriceForm0']/input[2]")
	public WebElement cartPage_UpdatePriceOverride;
	@FindBy(how = How.XPATH, using = "//*[@id='quote_button']")
	public WebElement cartPage_RequestQuoteBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']/div[1]")
	public WebElement cartPage_PriceOverrideSuccessfulMsg;
	@FindBy(how = How.XPATH, using = "//*[@id='repId']")
	public WebElement cartPage_RepIDBox;
	@FindBy(how = How.XPATH, using = "//*[@id='submit-quote-button']")
	public WebElement cartPage_SubmitQuote;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']/div[1]/div/div[1]/ul/li[2]/span")
	public WebElement cartPage_QuoteNumber;
	@FindBy(how = How.XPATH, using = "//*[@id='_asm']//button[contains(@class,'close')]")
	public WebElement homePage_signout_tele;
	@FindBy(how = How.XPATH, using = ".//*[@id='_asmPersonifyForm']/fieldset/div[3]/input")
	public WebElement homePage_TransactionIDBox;
	@FindBy(how = How.XPATH, using = "//*[@id='quoteStatusChange']")
	public WebElement cartPage_ApproveRejectBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='quote-comment-textarea-id']")
	public WebElement cartPage_ApproverCommentBox;
	@FindBy(how = How.XPATH, using = "//*[@id='tele-quote-approve-btn']")
	public WebElement cartPage_ApproveButton;
	@FindBy(how = How.XPATH, using = "//*[@id='tele-quote-reject-btn']")
	public WebElement cartPage_RejectButton;
	@FindBy(how = How.XPATH, using = "//*[@id='convertoorderButton']")
	public WebElement cartPage_ConvertToOrderBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_IGF']")
	public WebElement paymentPage_IGF;
	@FindBy(how = How.XPATH, using = "//*[@id='checkoutForm-address-leasingBillTo2']")
	public WebElement paymentPage_billingAddress;
	@FindBy(how = How.XPATH, using = "//*[@id='checkoutForm-address-leasingBillTo2']/option[contains(.,'84602')]")
	public WebElement paymentPage_billingAddressDropDwn_US;
	@FindBy(how = How.XPATH, using = "//*[@id='checkoutForm-address-leasingBillTo2']/option[contains(.,'2319')]")
	public WebElement paymentPage_billingAddressDropDwn_AU;
	@FindBy(how = How.XPATH, using = "//*[@id='checkoutForm-address-leasingBillTo2']/option[contains(.,'5400005')]")
	public WebElement paymentPage_billingAddressDropDwn_JP;
	@FindBy(how = How.XPATH, using = "(//div[@class='checkout-confirm-orderNumbers']//tr[2]/td[2])[1]")
	public WebElement placeOrderPage_OrderNumber;
	@FindBy(how = How.XPATH, using = "//div[@class='checkout-confirm-orderNumbers']/ul/li[2]/span")
	public WebElement QuoteConfirmPage_QuoteID;
	@FindBy(how = How.CSS, using = "[id^='ui-id-']>a")
	public WebElement MyAccount_CustomerResult;
	@FindBy(how = How.CSS, using = "[id^='Q']>a")
	public WebElement MyAccount_QuoterResult;
	@FindBy(how = How.XPATH, using = ".//*[@id='resultList']/div/div[4]/a")
	public WebElement customize;
	@FindBy(how = How.XPATH, using = ".//*[@id='resultList']/div/div[4]/a")
	public List<WebElement> customizeButtons;
	@FindBy(how = How.XPATH, using = "//button[@id='b_alert_add_to_cart']")
	public WebElement warningAdd;
	@FindBy(how = How.XPATH, using = "//div[@class='pricingSummary-cta']/button")
	public WebElement getAddtoCartPB;
	@FindBy(how = How.XPATH, using = "//div/button[@class='add-to-cart']/span[contains(.,'Add to cart')]")
	public WebElement agreementsAddToCart;
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Add to cart')]")
	public WebElement accessoryTabAdd;
	@FindBy(how = How.CSS, using = ".add-to-cart")
	public WebElement CTOAddtoCartPDP;
	@FindBy(how = How.XPATH, using = "(//form[contains(@id,'addToCartForm')]/button)[1]")
	public WebElement addToCartBtn;
	@FindBy(how = How.XPATH, using = "//div[@id='cboxContent']//div[@class='addtoCartCTA']/button")
	public WebElement addtoCartPOP;
	@FindBy(how = How.XPATH, using = "(//*[@class='addtoCartCTA']/a[@class='goToCartCTA'])[last()]")
	public WebElement goToCartPop;
	@FindBy(how = How.ID, using = "validateDateformatForCheckout")
	public WebElement lenovoCheckout;
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber']")
	public WebElement cartPage_partnum;

	// 15490
	@FindBy(how = How.XPATH, using = "(.//*[@id='specialH3ForCustomizedSolr'][contains(.,'Contract & Agreement')])[last()]")
	public WebElement Laptops_contractAgreementFilter;
	@FindBy(how = How.XPATH, using = "(.//label[contains(.,'Agreement')])[last()]")
	public WebElement Laptops_agreementTxt;
	@FindBy(how = How.XPATH, using = "(//label[contains(.,'Agreement')]/input)[last()]")
	public WebElement Laptops_agreementChk;

	@FindBy(how = How.XPATH, using = "//input[@class='orderSelectChkbox']")
	public WebElement QuotePage_orderSelectChkbox;
	@FindBy(how = How.XPATH, using = "//*[@id='batchApproveBtn']")
	public WebElement QuotePage_clickApproveButton;
	@FindBy(how = How.XPATH, using = "(//div[@id='mainContent']//p/a[1])[1]")
	public WebElement QuotePage_ViewQuoteFirst;
	@FindBy(how = How.XPATH, using = "(//div[@id='mainContent']//p/a[1])[2]")
	public WebElement QuotePage_ViewQuoteSecond;
	@FindBy(how = How.XPATH, using = "//*[@id='batchRejectBtn']")
	public WebElement QuotePage_clickRejectButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='batchAssignBtn']")
	public WebElement QuotePage_clickAssignButton;
	@FindBy(how = How.XPATH, using = "//*[@id='approveIdSelector']")
	public WebElement QuotePage_approverDropDown;
	@FindBy(how = How.XPATH, using = "//*[@id='approveId']")
	public WebElement placeOrderPage_selectApprover;
	@FindBy(how = How.XPATH, using = "//*[@id='approvalButton']")
	public WebElement placeOrderPage_sendApproval;
	@FindBy(how = How.XPATH, using = "//*[@id='orderSummaryReview_placeOrder']")
	public WebElement placeOrderPage_sendForApproval;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']//label[contains(.,'Quote ID')]/../span")
	public WebElement CartPage_getQuoteNumber;
	@FindBy(how = How.XPATH, using = "(//tr/td[@data-title='Quote Status']/p)[1]")
	public WebElement QuotePage_FirstQuoteStatus;
	@FindBy(how = How.XPATH, using = "(//tr/td[@data-title='Quote Status']/p)[2]")
	public WebElement QuotePage_SecondQuoteStatus;

	@FindBy(how = How.XPATH, using = "//*[@id='email']")
	public WebElement shippingPage_email;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_BANKDEPOSIT']")
	public WebElement paymentPage_bankDeposit;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Order')]/../td[2]")
	public WebElement orderPage_orderNumber;
	@FindBy(how = How.XPATH, using = "//*[@id='Paymetric_CreditCardType']/option[contains(text(),'Mastercard')]")
	public WebElement paymentPage_MasterCard;

	// ADDED BY 16415
	@FindBy(how = How.ID, using = "Laptops & Ultrabooks")
	public WebElement HomePage_Laptop;
	@FindBy(how = How.ID, using = "Desktops & All-In-Ones")
	public WebElement HomePage_Destop;
	@FindBy(how = How.ID, using = "b_marning_customize")
	public WebElement CustomizeButtonInPopup;
	@FindBy(how = How.XPATH, using = "//div[@id='resultList'][1]//div[@class='agreementContract']")
	public WebElement HomePage_contractLable;
	@FindBy(how = How.XPATH, using = "//div[@id='resultList'][1]//form[contains(@id,'addToAccessorisForm')]/button")
	public WebElement ProductPage_addAccessories;
	@FindBy(how = How.XPATH, using = "//div[@id='resultList'][1]//p")
	public WebElement ProductPage_PriceOnPLP;
	@FindBy(how = How.XPATH, using = "//div[@id='resultList'][1]//b/dl[1]/dd")
	public WebElement ProductPage_PartNoOnPLP;
	@FindBy(how = How.XPATH, using = "//div[@id='resultList'][1]//a[contains(text(),'View Details')]")
	public WebElement ProductPage_details;
	@FindBy(how = How.XPATH, using = "//div[@class='cta']//p")
	public WebElement PDPPage_ProductPrice;
	@FindBy(how = How.XPATH, using = "//div[@class='price']/p/span")
	public WebElement PDPPage_ProductPrice_CTO;
	@FindBy(how = How.XPATH, using = "//form[contains(@id,'addToCartForm')]/button")
	public WebElement PDPPage_AddtoCart;
	@FindBy(how = How.XPATH, using = "//form[contains(@id,'addToAccessorisForm')]/button")
	public WebElement PDPPage_AddAccessories;
	@FindBy(how = How.LINK_TEXT, using = "Go to Cart")
	public WebElement ProductPage_AlertGoToCart;
	@FindBy(how = How.XPATH, using = "dd[@class='cart-summary-pricing-shipping-price']")
	public WebElement ShippingPage_PriceShipping;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Line Total')]/..//dl")
	public WebElement CartPage_Price1;
	@FindBy(how = How.XPATH, using = "//a[@class='breadcrumb-item']")
	public WebElement CartPage_HomeLink;
	@FindBy(how = How.XPATH, using = ".//*[@id='resultList']/div/div[4]/a")
	public WebElement HomePage_Customize;
	@FindBy(how = How.XPATH, using = "(.//*[@class='checkout-confirm-orderSummary-orderTotals-subTotal'])[1]")
	public WebElement OrderPage_subTotalPrice;
	@FindBy(how = How.CLASS_NAME, using = "checkout-confirm-orderSummary-orderTotals-total")
	public WebElement OrderPage_TotalPrice;
	@FindBy(how = How.XPATH, using = "(.//*[@class='checkout-confirm-orderSummary-orderTotals-subTotal'])[2]")
	public WebElement OrderPage_TaxPrice;

	@FindBy(how = How.XPATH, using = ".//a[contains(.,'View Details')]")
	public List<WebElement> PLPPage_viewDetails;

	// 15487
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Item Price')]/..//dl")
	public WebElement CartPage_ItemPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'modal-footer')]//button[contains(.,'Add to cart')]")
	public WebElement WarningAddtoCart;
	@FindBy(how = How.XPATH, using = "//span[@class='summary-webPrice']")
	public WebElement PDPPage_WebPrice;
	@FindBy(how = How.XPATH, using = "//span[@id='ctoYourPrice']")
	public WebElement PDPPage_OrderTotalPrice;
	@FindBy(how = How.XPATH, using = "//span[@id='bundle-alert-new-total']")
	public WebElement WarningNewTotalPrice;

	// 21299
	@FindBy(how = How.XPATH, using = "// *[contains(@id,'Alert')]//*[contains(@id,'_add_to_cart')]")
	public WebElement PDPPage_agreementAddToCartOnPopup;
	@FindBy(how = How.XPATH, using = "//*[contains(@title,'Add to Cart')]")
	public List<WebElement> PLPPage_addToCart;
	@FindBy(how = How.XPATH, using = "//*[@id='cboxContent']//button[contains(@class,'addToCart')]")
	public WebElement Product_contractAddToCartOnPopup;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_PURCHASEORDER']/../label")
	public WebElement Payment_purchaseOrderLabel;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_BANKDEPOSIT']/../label")
	public WebElement Payment_bankDepositLabel;

	// 21184
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'add_to_cart')]")
	public WebElement PLPPage_AddToCart;

	// register
	@FindBy(how = How.XPATH, using = "//input[@id='register.acceptterms']")
	public WebElement Register_acceptterms;

	// 21226
	@FindBy(how = How.XPATH, using = "//input[@id='downloadBtn']")
	public WebElement PLPPage_DownLoadBtn;
	@FindBy(how = How.XPATH, using = "(//div[@id='resultList']//div[@class='agreementContract'])[1]")
	public WebElement productPage_agreementContractBtn;

	// 22370
	@FindBy(how = How.XPATH, using = "//span[@id='email.errors']")
	public WebElement Register_emailError;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//font[contains(.,'Thank You')]")
	public WebElement Register_thankYouMessage;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']/div/div[contains(.,'Welcome to')]")
	public WebElement HomePage_welcome;

	// 17127
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//a[contains(@href,'profile')]")
	public WebElement MyAccountPage_updateDetails;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//a[contains(@href,'email')]")
	public WebElement MyAccountPage_updateEmail;
	@FindBy(how = How.XPATH, using = "//input[@id='profile.email']")
	public WebElement MyAccountPage_emailAddress;
	@FindBy(how = How.XPATH, using = "//input[@id='profile.checkEmail']")
	public WebElement MyAccountPage_reEnterEmail;
	@FindBy(how = How.XPATH, using = "//form[@id='updateEmailForm']/span/button")
	public WebElement MyAccountPage_saveButton;
	@FindBy(how = How.XPATH, using = "//select[@id='approveId']")
	public WebElement OrderApproverPage_approverDropDown;

	// the following is for metadata
	@FindBy(how = How.ID, using = "login.email.id")
	public WebElement IDcheckbox;
	@FindBy(how = How.ID, using = "login.password")
	public WebElement PWcheckbox;
	@FindBy(how = How.XPATH, using = ".//*[@id='loginForm']/button")
	public WebElement signin;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'createAccount')]")
	public WebElement createAnAccount;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'Accessories_Upgrades')]")
	public WebElement MenuAccessory;
	@FindBy(how = How.XPATH, using = "//*[@id='addressForm']/fieldset[1]/legend/a")
	public WebElement shippingEdit;
	@FindBy(how = How.XPATH, using = "//*[@id='checkoutForm-fieldset-shippingaddress']/span/a/span[1]")
	public WebElement shippingCarrotIcon;
	@FindBy(how = How.XPATH, using = "(//ul[@id='ui-id-1']/li)[1]")
	public WebElement shippingSelectAddress;
	@FindBy(how = How.XPATH, using = "//*[@id='company']")
	public WebElement companyInput;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'my-account') and @class='has-submenu']")
	public WebElement myAccount_link;
	@FindBy(how = How.XPATH, using = "//input[@id='firstName']")
	public WebElement FirstName;
	@FindBy(how = How.XPATH, using = "//input[@id='lastName']")
	public WebElement LastName;
	@FindBy(how = How.XPATH, using = "//input[@id='email']")
	public WebElement ShippingEmail;
	@FindBy(how = How.XPATH, using = "//input[@id='phone1']")
	public WebElement Mobile;

	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_PURCHASEORDER']")
	public WebElement purchaseOrder;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderNumber']")
	public WebElement purchaseNum;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderDate']")
	public WebElement purchaseDate;
	@FindBy(how = How.XPATH, using = "//input[@id='address.firstName']")
	public WebElement addressFirstName;
	@FindBy(how = How.XPATH, using = "//input[@id='address.lastname']")
	public WebElement addressLastName;
	@FindBy(how = How.XPATH, using = "//input[@id='address.phoneNumber1']")
	public WebElement addressPhone;
	@FindBy(how = How.XPATH, using = ".//*[@id='add-payment-method-continue']")
	public WebElement ContinueforPayment;

	@FindBy(how = How.XPATH, using = "//*[@id='ccEmailAddress']")
	public WebElement ccEmailAddress;
	@FindBy(how = How.XPATH, using = "//*[@id='commentArea']")
	public WebElement commentArea;
	@FindBy(how = How.XPATH, using = "//*[@id='shippingLabel']")
	public WebElement shippingLabel;
	@FindBy(how = How.XPATH, using = "//*[@id='invoiceInstruction']")
	public WebElement invoiceInstruction;
	@FindBy(how = How.ID, using = "orderSummaryReview_placeOrder")
	public WebElement OrderSummary_PlaceOrderButton;

	// 19804
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'update-password')]")
	public WebElement MyAccountPage_changeYourPassword;
	@FindBy(how = How.XPATH, using = "//input[@id='profile.currentPassword']")
	public WebElement UpdatePasswordPage_currentPassword;
	@FindBy(how = How.XPATH, using = "//input[@id='profile.newPassword']")
	public WebElement UpdatePasswordPage_newPassword;
	@FindBy(how = How.XPATH, using = "//input[@id='profile.checkNewPassword']")
	public WebElement UpdatePasswordPage_confirmNewPassword;
	@FindBy(how = How.XPATH, using = "//form[@id='updatePasswordForm']//button")
	public WebElement UpdatePasswordPage_updatePassword;
	@FindBy(how = How.XPATH, using = "//div[@class='alert positive']")
	public WebElement ProfilePage_successMsgBox;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'btn-primary') and @href='update-password']")
	public WebElement ProfilePage_changeYourPassword;

	// 21963
	@FindBy(how = How.XPATH, using = "//input[@id='privateSaveCart']")
	public WebElement cartPage_privateSaveCartn;
	@FindBy(how = How.XPATH, using = "//textarea[@id='realsavecartname']")
	public WebElement cartPage_realsavecartname;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//div[@class='savedCartBody']/h2")
	public WebElement cartPage_savedCartPage;
	@FindBy(how = How.XPATH, using = "//form[@id='editConfig']/p/span")
	public WebElement checkoutPage_partNo;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//a[contains(.,'Show all results')]")
	public WebElement savedCartPage_showAllResult;

	// NA-17401 Ankit

	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']/div//div[@class='signInModule-errorMessage']/div")
	public WebElement B2BLoginErrorMessage;
	// NA-19422
	@FindBy(how = How.XPATH, using = "//li/a[contains(@href,'cart')]")
	public WebElement Homepage_ClickCart;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Empty cart')]")
	public WebElement CartPage_EmptyCart;
	@FindBy(how = How.XPATH, using = "(.//*[@id='resultList']/div/div[4]/a)[2]")
	public WebElement Payment_Customize;
	@FindBy(how = How.XPATH, using = "//div[@id='bundleAlert'  and @style='display: block;']//button[contains(text(),'Customize')]")
	public WebElement Config_BundleAlert;
	@FindBy(how = How.XPATH, using = "((//form[contains(@id,'addToCartForm')]/button)[contains(.,'Add')])[1]")
	public WebElement AddForm_AddToCartBtn;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Edit')]")
	public WebElement shippingPage_EditAddress;
	@FindBy(how = How.XPATH, using = "//input[@value='ok']")
	public WebElement shippingPage_Addressvalidation;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_CARD']")
	public WebElement credit_cardPayment;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]")
	public WebElement Visa;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'AMEX') or contains(text(),'American Express')]")
	public WebElement AmericaExpress;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'Master Card') or contains(text(),'Mastercard')]")
	public WebElement MasterCard;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'Discover')]")
	public WebElement Discover;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cardnumber']")
	public WebElement CardNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='c-exmth']")
	public WebElement ExpiryMonth;
	@FindBy(how = How.XPATH, using = "//input[@id='c-exyr']")
	public WebElement ExpiryYear;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cvv']")
	public WebElement SecurityCode;
	@FindBy(how = How.XPATH, using = "//input[@id='card_nameOnCard']")
	public WebElement NameonCard;
	@FindBy(how = How.XPATH, using = "//input[@name='external.field.password']")
	public WebElement VisaPassword;
	@FindBy(how = How.XPATH, using = "//input[@name='UsernamePasswordEntry']")
	public WebElement VisaSubmit;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_IGF']")
	public WebElement leasing_IGF_payment;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_LFS']")
	public WebElement leasing_LFS_payment;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_BANKDEPOSIT']")
	public WebElement DirectDeposit;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_TWOCARDS']")
	public WebElement TwoCardRadioButton;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_PAYPAL']")
	public WebElement PaypalButton;
	@FindBy(how = How.XPATH, using = "//iframe [@title='PayPal - Log In']")
	public WebElement PaypalLoginFrame;
	@FindBy(how = How.XPATH, using = "//input[@id='email']")
	public WebElement PaypalEmail;
	@FindBy(how = How.XPATH, using = "//input[@id='password']")
	public WebElement PaypalPassword;
	@FindBy(how = How.XPATH, using = "//button[@id='btnLogin']")
	public WebElement PaypalSignin;
	@FindBy(how = How.XPATH, using = "//input[@id='confirmButtonTop']")
	public WebElement PaypalContinue;
	@FindBy(how = How.XPATH, using = "//input[@id='card_amount1']")
	public WebElement FirstCardAmount;
	@FindBy(how = How.XPATH, using = ".//*[@id='iframe1_info']/div[1]/label")
	public WebElement PaymentIndicator;
	@FindBy(how = How.ID, using = "creditcardiframe1")
	public WebElement FirstCardIframe;
	@FindBy(how = How.ID, using = "creditcardiframe2")
	public WebElement SecondCardIframe;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'VISA')]")
	public WebElement TwoCardsType;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cn']")
	public WebElement TwoCardsNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='c-exmth']")
	public WebElement TwocardsMonth;
	@FindBy(how = How.XPATH, using = "//input[@id='c-exyr']")
	public WebElement TwocardsYear;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cvv']")
	public WebElement TwocardsCV;
	@FindBy(how = How.XPATH, using = "//input[@id='card_nameOnCard1']")
	public WebElement TwocardsNameOnCard1;
	@FindBy(how = How.XPATH, using = "//input[@id='card_nameOnCard2']")
	public WebElement TwocardsNameOnCard2;
	// NA-19027
	@FindBy(how = How.XPATH, using = "(//div[@id='resultList']//div[@class='facetedResults-feature-list']/b//dd[1])[1]")
	public WebElement Laptop_SelectedLaptop1;
	@FindBy(how = How.XPATH, using = "(//a[contains(.,'View Details')])[1]")
	public WebElement Laptop_ViewDetail1;
	@FindBy(how = How.XPATH, using = "(//div[@id='resultList']//div[@class='facetedResults-feature-list']/b//dd[1])[2]")
	public WebElement Laptop_SelectedLaptop2;
	@FindBy(how = How.XPATH, using = "(//a[contains(.,'View Details')])[2]")
	public WebElement Laptop_ViewDetail2;
	@FindBy(how = How.XPATH, using = "//div/button[contains(@class,'add-to-cart')]")
	public WebElement PDP_AddToCart;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Email quote')]")
	public WebElement QuotePage_EmailQuoteAdd;
	@FindBy(how = How.XPATH, using = ".//*[@id='quote-email-to-addresses']")
	public WebElement QuotePage_EmailTextArea;
	@FindBy(how = How.XPATH, using = ".//*[@id='quote-email-description']")
	public WebElement QuotePage_EmailDescription;
	@FindBy(how = How.XPATH, using = ".//*[@id='quote-send-email']")
	public WebElement QuotePage_SendEmailButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxClose']")
	public WebElement QuotePage_CloseEmailButton;
	@FindBy(how = How.XPATH, using = "(//td[@data-title='Quote ID']/a)")
	public WebElement QuotePage_QuoteID;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'password-forgotten')]")
	public WebElement B2BLoginPage_ForgotPassLink;
	@FindBy(how = How.XPATH, using = ".//*[@id='forgottenPwd.email']")
	public WebElement B2BLoginPage_BuyerMailTextBox;
	@FindBy(how = How.XPATH, using = "//button[contains(.,'Send Email')]")
	public WebElement B2BLoginPage_SendForgotMailButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='validEmail']")
	public WebElement B2BLoginPage_Message;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxClose']")
	public WebElement B2BLoginPage_CloseIcon;

	@FindBy(how = How.XPATH, using = "//ul[@class='menu_masthead general_Menu']/li[7]/a[@class='has-submenu']/span")
	public WebElement MyCompany;
	@FindBy(how = How.XPATH, using = ".//div[@class='cust_acc']/div/ul/li/a")
	public WebElement MyCompany_EditOrDisableUser;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchForm']//input[@name='email']")
	public WebElement MyCompany_Email;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchForm']/input[@value='Search']")
	public WebElement MyCompany_SearchButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='b2BCustomerForm']//input[@value='buyerDefault']")
	public WebElement MyCompany_BuyerDefault;
	@FindBy(how = How.XPATH, using = ".//*[@id='b2BCustomerForm']//button")
	public WebElement MyCompany_SaveUpdate;
	@FindBy(how = How.XPATH, using = "//span[@class='country_selector_span']")
	public WebElement WelcomeMessage;

	@FindBy(how = How.XPATH, using = ".//ul[@class='manageUseractions']//a[contains(@class,'edit')]")
	public WebElement MyCompany_EditAccount;

	@FindBy(how = How.XPATH, using = ".//*[@id='text.company.aprovelStatus.title']")
	public WebElement MyCompany_ApprovalStatus;

	@FindBy(how = How.XPATH, using = ".//*[@id='text.company.aprovelStatus.title']/option[@value='ACTIVE']")
	public WebElement MyCompany_ApprovalStatusActive;

	@FindBy(how = How.XPATH, using = ".//*[@id='accessLevel_account']/li/input")
	public WebElement MyCompany_AdobeSystemPtyLtd;

	@FindBy(how = How.XPATH, using = ".//*[@id='defaultDmuSoldTo-table']//input[@name='defaultStore']")
	public WebElement MyCompany_DefaultStore;

	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//font")
	public WebElement SuccessfullyAccountCreated;

	// NA-17699
	@FindBy(how = How.ID, using = "profile.optin")
	public WebElement UpdateProfilePage_optInCheckBox;

	@FindBy(how = How.XPATH, using = ".//*[@id='updateProfileForm']/span/button")
	public WebElement UpdateProfilePage_saveProfileDetails;

	@FindBy(how = How.XPATH, using = ".//*[@id='marketingAgreement']")
	public WebElement ShippingPage_optInCheckbox;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'finalPrice-amount')]/dl")
	public WebElement ShippingPage_CountryproductPrice;
	
	// NA-18057
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'email')]/a")
	public WebElement CheckOutPage_EmailLink;

	@FindBy(how = How.XPATH, using = ".//*[@id='editConfig']/h4/div")
	public WebElement CheckOutPage_productName;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'finalPrice-amount')]/dl")
	public WebElement CheckOutPage_productPrice;

	@FindBy(how = How.XPATH, using = ".//*[@id='senderName']")
	public WebElement EmailCart_fullName;

	@FindBy(how = How.XPATH, using = ".//*[@id='senderAddress']")
	public WebElement EmailCart_emailTxtBox;

	@FindBy(how = How.XPATH, using = ".//*[@id='cartEmailSend']/button")
	public WebElement EmailCart_sendButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='bodywrapinner']/div/p")
	public WebElement EmailCart_sendSuccessMsg;

	// NA-18480
	@FindBy(how = How.XPATH, using = ".//div[contains(@class,'checkout-confirm')]/img[contains(@src,'Seal')]")
	public WebElement quoteConfirmation_countrySeal;
	@FindBy(how = How.XPATH, using = ".//label[contains(.,'Quote ID')]/../span")
	public WebElement quoteConfirmation_quoteID;

	// NA-18019
	@FindBy(how = How.XPATH, using = "(.//img[contains(@class,'cart-item-image')])[1]")
	public WebElement b2bCart_cartItem1Image;
	@FindBy(how = How.XPATH, using = "(.//img[contains(@class,'cart-item-image')])[2]")
	public WebElement b2bCart_cartItem2Image;
	@FindBy(how = How.XPATH, using = "(.//img[contains(@class,'cart-item-image')])[3]")
	public WebElement b2bCart_cartItem3Image;
	@FindBy(how = How.XPATH, using = ".//*[@id='quantity2']")
	public WebElement b2bCart_cartItem3Quantity;

	// NA-18036
	@FindBy(how = How.XPATH, using = "(.//a/div[contains(.,'Delete')])[1]")
	public WebElement cartHistory_deleteCart;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']")
	public WebElement cartHistory_searchCartByDropDown;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']/option[contains(.,'Cart Name')]")
	public WebElement cartHistory_searchCartByCartName;
	@FindBy(how = How.XPATH, using = "//div/input[@name='searchKeyWord']")
	public WebElement cartHistory_searchCartTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchKeyWord2']/input")
	public WebElement cartHistory_searchCartButton;
	@FindBy(how = How.XPATH, using = "(//*[contains(@action,'restfulAddToCart')]/button)[1]")
	public WebElement laptops_addToCartButton;
	@FindBy(how = How.XPATH, using = "(.//img[@title='Customize and buy'])[1]")
	public WebElement laptops_ctoCustomizeAndBuyButton;
	@FindBy(how = How.XPATH, using = ".//div[contains(@class,'modal-dialog')]//button[contains(.,'Add to cart')]")
	public WebElement laptops_addToCartOverlayForCTO;
	@FindBy(how = How.XPATH, using = ".//span[@id='CTO_addToCart']")
	public WebElement laptops_addToCartForCTO;
	@FindBy(how = How.XPATH, using = ".//button[contains(@class,'pricingSummary-button')]/span")
	public WebElement productBuilder_addToCartButton;
	@FindBy(how = How.XPATH, using = ".//td[@data-title='Cart Name']/h3")
	public WebElement savedCarts_cartName;
	@FindBy(how = How.XPATH, using = ".//a/div[contains(.,'View')]")
	public WebElement savedCarts_viewLink;
	@FindBy(how = How.XPATH, using = ".//a[@class='submit'][contains(.,'Edit')]")
	public WebElement savedCarts_editLink;
	@FindBy(how = How.XPATH, using = ".//a/div[contains(.,'Email')]")
	public WebElement savedCarts_emailLink;
	@FindBy(how = How.XPATH, using = ".//a/div[contains(.,'Delete')]")
	public WebElement savedCarts_deleteLink;
	@FindBy(how = How.XPATH, using = ".//a/div[contains(.,'Unshare')]")
	public WebElement savedCarts_unshareLink;
	@FindBy(how = How.XPATH, using = ".//div[@class='disableLinkText'][contains(.,'Edit')]")
	public WebElement savedCarts_disabledEditLink;
	@FindBy(how = How.XPATH, using = ".//div[@class='disableLinkText'][contains(.,'Delete')]")
	public WebElement savedCarts_disabledDeleteLink;
	@FindBy(how = How.XPATH, using = ".//div[@class='itemName']")
	public WebElement b2bCart_itemName;
	@FindBy(how = How.XPATH, using = ".//input[@id='quantity3']")
	public WebElement cartHistory_quantity;
	@FindBy(how = How.XPATH, using = ".//div[contains(@class,'finalPrice-amount')]/span")
	public WebElement cartHistory_productPrice;
	@FindBy(how = How.XPATH, using = ".//div[@class='linktext'][contains(.,'Share')]")
	public WebElement savedCarts_shareLink;
	@FindBy(how = How.XPATH, using = ".//p[contains(.,'You have no carts')]")
	public WebElement savedCarts_noCartAvailable;
	@FindBy(how = How.XPATH, using = ".//td[@data-title='Cart Name']/small")
	public WebElement savedCarts_savedCartType;
	@FindBy(how = How.XPATH, using = ".//h2/div[contains(.,'Personal saved cart')]")
	public WebElement savedCarts_personalSavedCartLabel;
	@FindBy(how = How.XPATH, using = ".//button[@id='editCartButton']")
	public WebElement savedCarts_editCartButton;

	// 18052
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber']/span")
	public WebElement CartPage_OnlyPartnum;
	@FindBy(how = How.XPATH, using = ".//*[@id='my-account-saved-quote-status']")
	public WebElement QuotePage_QuoteStatus;
	@FindBy(how = How.XPATH, using = "//label[contains(.,'Quote ID')]/../span")
	public WebElement QuotePage_QuoteNo;
	@FindBy(how = How.XPATH, using = "//td[@data-title='Line Price']")
	public WebElement QuotePage_LinePrice;
	@FindBy(how = How.XPATH, using = "//td[@data-title='Part Number']/span")
	public WebElement QuotePage_PartNo;
	@FindBy(how = How.XPATH, using = "//td[@data-title='Quantity']")
	public WebElement QuotePage_Quantity;
	@FindBy(how = How.XPATH, using = "//span[@class='country_selector_span']")
	public WebElement B2BHomePage_WelcomeUnitName;
	@FindBy(how = How.XPATH, using = "//h4/a[@class='store-selector-country']/span")
	public WebElement B2BHomePage_CountryName;
	@FindBy(how = How.XPATH, using = "(//div/a[contains(@onclick,'ChangeStore')][@class='clickable'])[1]")
	public WebElement B2BHomePage_SubUnitName;
	@FindBy(how = How.XPATH, using = ".//*[@id='amountOperator']")
	public WebElement QuoteHistoryPage_AmountOperator;
	@FindBy(how = How.XPATH, using = ".//*[@id='amountOperator']/option[@value='less']")
	public WebElement QuoteHistoryPage_AmntOpeIsLess;
	@FindBy(how = How.XPATH, using = ".//*[@id='amountOperator']/option[@value='equal']")
	public WebElement QuoteHistoryPage_AmntOpeIsEqual;
	@FindBy(how = How.XPATH, using = ".//*[@id='amountOperator']/option[@value='larger']")
	public WebElement QuoteHistoryPage_AmntOpeIsGreater;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchCriteria']/option[@value='UnitName']")
	public WebElement QuoteHistoryPage_StoreNameInSearchCriteria;

	// NA-18038
	@FindBy(how = How.XPATH, using = ".//*[@id='editConfig']/p/span")
	public WebElement CartPage_ProductPartNum;

	@FindBy(how = How.XPATH, using = "//li/a/span[contains(@class,'country_selector')]")
	public WebElement CartPage_ChangeSoldToDropdown;

	@FindBy(how = How.XPATH, using = "(//h4/a[contains(@class,'store-selector')]/span)[1]")
	public WebElement ChangeSoldTo_SelectCountry;

	@FindBy(how = How.XPATH, using = "(//div/a[@class='clickable'][contains(@onclick,'ChangeStore')]/span)[1]")
	public WebElement ChangeSoldTo_ChangeStore;

	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']/div[2]/h3")
	public WebElement CartPage_EmptyCartMessage;

	// NA-18025

	@FindBy(how = How.XPATH, using = "//li/a[contains(@href,'logout')]/span")
	public WebElement ShippingPage_SignOut;

	@FindBy(how = How.XPATH, using = "(//p[@class='checkout-shoppingCart-previewSubtitle'])[1]")
	public WebElement B2BShipPage_PartNo;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchCriteria']")
	public WebElement QuoteHistoryPage_SearchCriteria;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchCriteria']/option[@value='QuoteID']")
	public WebElement QuoteHistoryPage_QuoteIDInSearch;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchCriteria']/option[@value='TotalAmount']")
	public WebElement QuoteHistoryPage_TotalAmntInSearch;
	@FindBy(how = How.XPATH, using = ".//*[@name='searchValue']")
	public WebElement QuoteHistoryPage_SearchValue;
	@FindBy(how = How.XPATH, using = "//li/input[@value='Search']")
	public WebElement QuoteHistoryPage_SearchButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchCriteria']/option[@value='UnitID']")
	public WebElement QuoteHistoryPage_StoreIDInSearch;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchCriteria']/option[@value='ProductPartNumber']")
	public WebElement QuoteHistoryPage_PartNoInSearch;

	@FindBy(how = How.XPATH, using = "//span[@id='y_popupmessage_ok_label']")
	public WebElement PunchoutCredentials_OkButton;

	// NA-18108
	@FindBy(how = How.XPATH, using = ".//*[@id='order_code']")
	public WebElement ApprovalDashBoard_OrderNumber;

	@FindBy(how = How.XPATH, using = ".//*[@id='approvalDashboardPage_submit_button']")
	public WebElement ApprovalDashBoard_Search;

	@FindBy(how = How.XPATH, using = ".//*[@id='searchScopeSelect']")
	public WebElement ApprovalDashBoard_SearchFor;

	@FindBy(how = How.XPATH, using = ".//*[@id='searchScopeSelect']/option[@value='ALL']")
	public WebElement ApprovalDashBoard_OtherPendingOrder;

	@FindBy(how = How.XPATH, using = ".//*[@id='editConfig']//a[contains(.,'Edit')]")
	public WebElement CartPage_Editlink;

	@FindBy(how = How.XPATH, using = "//div[@class='cart-item-details']")
	public WebElement CartPage_AvailabilityOfItem;
	@FindBy(how = How.XPATH, using = "//input[@name='countryname' and contains(@class,'checkoutForm-formInput')]")
	public WebElement ShippingPage_Country;
	@FindBy(how = How.XPATH, using = ".//*[@id='checkoutForm-fieldset-shippingaddress']/span/a")
	public WebElement ShippingPage_AddDropDown;
	@FindBy(how = How.XPATH, using = ".//*[@id='b_marning_add_to_cart']")
	public WebElement ModalPopUp_AddToCart;

	@FindBy(how = How.XPATH, using = "//form/button[contains(.,'Add to Cart')]")
	public WebElement productPage_AddToCart;

	@FindBy(how = How.XPATH, using = ".//*[@id='configarea_scroller']//h1[@class='bar_3-heading']")
	public WebElement ShippingPage_thankyou;

	@FindBy(how = How.XPATH, using = ".//*[@id='globalMessages']/div")
	public WebElement OrderApprovalDashboard_message;

	// NA-17410
	@FindBy(how = How.XPATH, using = ".//div[contains(@id,'request-quote')]//h2")
	public WebElement cartPage_requestQuoteLabel;
	@FindBy(how = How.XPATH, using = ".//*[@id='quoteName']")
	public WebElement cartPage_requestQuoteName;
	@FindBy(how = How.XPATH, using = ".//*[@id='quoteDescription']")
	public WebElement cartPage_requestQuoteDescription;
	@FindBy(how = How.XPATH, using = ".//li/label[contains(.,'Quote Name')]")
	public WebElement quoteConfirmation_quoteNameLabel;
	@FindBy(how = How.XPATH, using = ".//li/label[contains(.,'Quote Description')]")
	public WebElement quoteConfirmation_quoteDescriptionLabel;
	@FindBy(how = How.XPATH, using = ".//li/label[contains(.,'Quote Name')]/../span")
	public WebElement quoteConfirmation_quoteNameValue;
	@FindBy(how = How.XPATH, using = ".//li/label[contains(.,'Quote Description')]/../div")
	public WebElement quoteConfirmation_quoteDescriptionValue;
	@FindBy(how = How.XPATH, using = ".//*[@id='editButton']")
	public WebElement quoteDetails_editQuote;
	@FindBy(how = How.XPATH, using = ".//input[@value='Save Quote']")
	public WebElement cart_saveQuote;
	@FindBy(how = How.XPATH, using = ".//*[@id='order_code']")
	public WebElement quoteApproval_searchQuoteTxtBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='approvalDashboardPage_submit_button']")
	public WebElement quoteApproval_searchQuote;
	@FindBy(how = How.XPATH, using = ".//td/input[@type='checkbox']")
	public WebElement quoteApproval_checkQuote;
	@FindBy(how = How.XPATH, using = ".//*[@id='batchApproveBtn']")
	public WebElement quoteApproval_approveQuoteButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='globalMessages']/div")
	public WebElement quoteApproval_quoteApprovedMessageBar;

	// NA-23913
	@FindBy(how = How.XPATH, using = ".//*[@id='_asm']/div")
	public WebElement asmMode_top;
	@FindBy(how = How.XPATH, using = ".//*[@id='asmLogoutForm']/fieldset/button")
	public WebElement asmMode_signOutButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='showCustomerSearch']/span")
	public WebElement asmMode_customerSearchIcon;
	@FindBy(how = How.XPATH, using = "//*[@id='advancedCustomerSearch']")
	public WebElement asmMode_advancedCustomerSearchButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='firstName']")
	public WebElement advancedSearch_FirstName;
	@FindBy(how = How.XPATH, using = ".//*[@id='advancedStoreSearchIcon']")
	public WebElement asmMode_storeIDSearchIcon;
	@FindBy(how = How.XPATH, using = ".//*[@id='startAdvStoreSearch']")
	public WebElement asmMode_advancedStoreSearchButton;
	@FindBy(how = How.XPATH, using = "//input[@name='advStoreName']")
	public WebElement advancedStore_FirstName;
	@FindBy(how = How.XPATH, using = ".//*[@id='advancedTransactionSearchIcon']")
	public WebElement asmMode_TransactionID;
	@FindBy(how = How.XPATH, using = ".//button[@id='startAdvTransactionSearch']")
	public WebElement asmMode_SearchButton;

	// NA-17710
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'qa_AddToCartButton')]")
	public WebElement PDP_addToCart;
	@FindBy(how = How.XPATH, using = ".//*[@id='tab-a-accessories_-_services']/span")
	public WebElement PDP_accessories;

	// 17689
	@FindBy(how = How.XPATH, using = "//div[@class='expandableHeading section-header'][1]")
	public WebElement PB_expandableSection;
	@FindBy(how = How.XPATH, using = "(//div[@class='option-textFrame']//div[text()])[1]")
	public WebElement PB_firstOption;
	@FindBy(how = How.XPATH, using = ".//div[@class='lnvmodal-tail']/div[2]/span[2]")
	public WebElement PB_optionID;
	
	// NA-18468
	@FindBy(how = How.XPATH, using = ".//*[@id='asmOrderReportButton']")
	public WebElement orderQuoteReportButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//select[@name='storeType']")
	public WebElement orderQuote_storeType;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//select[@name='appliedCoupon']")
	public WebElement orderQuote_appliedCoupon;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//select[@name='transType']")
	public WebElement orderQuote_TransactionType;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//input[@name='repId']")
	public WebElement orderQuote_RepID;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//input[@name='customerId']")
	public WebElement orderQuote_custoemrID;
	@FindBy(how = How.XPATH, using = "//input[@name='startDate']")
	public WebElement orderQuote_startDate;
	@FindBy(how = How.XPATH, using = "//input[@name='endDate']")
	public WebElement orderQuote_endDate;
	@FindBy(how = How.XPATH, using = "//input[@name='coupon']")
	public WebElement orderQuote_couPon;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//input[@name='storeId']")
	public WebElement orderQuote_storeID;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//input[@name='transId']")
	public WebElement orderQuote_transactionID;
	@FindBy(how = How.XPATH, using = ".//*[@id='cboxLoadedContent']//input[@name='limit']")
	public WebElement orderQuote_pageLimit;
	@FindBy(how = How.XPATH, using = "//button[text()='Search']")
	public WebElement searchButton;

	// NA-18029
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']/option[@value='code']")
	public WebElement SavedCartPage_SearchWithCartID;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']/option[@value='name']")
	public WebElement SavedCartPage_SearchWithCartName;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']/option[@value='ownersName']")
	public WebElement SavedCartPage_SearchWithOwnerName;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']/option[@value='unitID']")
	public WebElement SavedCartPage_SearchWithStoreID;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']/option[@value='unitName']")
	public WebElement SavedCartPage_SearchWithStoreName;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchType']/option[@value='dateRange']")
	public WebElement SavedCartPage_SearchWithDateRange;
	@FindBy(how = How.XPATH, using = "//div/select[@id='searchType']/option[@value='total']")
	public WebElement SavedCartPage_SearchWithTotalPriceRange;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchKeyWord']/input")
	public WebElement SavedCartPage_Search_InputTxtBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchKeyWord2']/input")
	public WebElement SavedCartPage_SearchButton;
	@FindBy(how = How.ID, using = "dateRangeStart")
	public WebElement SavedCartPage_StartDate;
	@FindBy(how = How.ID, using = "dateRangeOver")
	public WebElement SavedCartPage_EndDate;
	@FindBy(how = How.XPATH, using = "(//td/a[contains(@class,'state-active')]/../preceding-sibling::td/a)[1]")
	public WebElement SavedCartPage_DateSelection_BeforeToday;
	@FindBy(how = How.XPATH, using = "//td/a[contains(@class,'state-active')]")
	public WebElement SavedCartPage_DateSelection_Today;
	@FindBy(how = How.XPATH, using = ".//*[@id='operateType']")
	public WebElement SavedCartPage_TotalRange_Criteria;
	@FindBy(how = How.XPATH, using = ".//*[@id='operateType']/option[@value='less']")
	public WebElement SavedCartPage_TotalRange_LessThan;
	@FindBy(how = How.XPATH, using = "//*[@id='operateType']/option[@value='more']")
	public WebElement SavedCartPage_TotalRange_MoreThan;

	// 21952
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'ui-icon-triangle')]")
	public WebElement Shipping_showAllAddress;
	@FindBy(how = How.XPATH, using = "//li[@class='ui-menu-item']")
	public WebElement Shipping_addressItem;
	@FindBy(how = How.XPATH, using = ".//*[@id='builderCommit']")
	public WebElement summary_builderComment;
	@FindBy(how = How.XPATH, using = ".//*[@id='approverCommit']")
	public WebElement summary_approverComment;
	@FindBy(how = How.XPATH, using = ".//*[@id='comments']")
	public WebElement orderRequireApproval_approverComment;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'View Web Orders History')]")
	public WebElement myAccount_viewOrderHistory;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderCode']")
	public WebElement viewOrderHistory_orderCode;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderHistorySearchForm']/input[@class='button-called-out']")
	public WebElement viewOrderHistory_orderHistorySearch;

	// NA-16675
	@FindBy(how = How.XPATH, using = "//label[contains(.,'Excel')]/input[@name='downloadType']")
	public WebElement downloadExcelFormat;
	@FindBy(how = How.XPATH, using = "//label[contains(.,'CSV')]/input[@name='downloadType']")
	public WebElement downloadCSVFormat;
	@FindBy(how = How.XPATH, using = "//label[contains(.,'CIF')]/input[@name='downloadType']")
	public WebElement downloadCIFFormat;

	// NA-15484
	@FindBy(how = How.XPATH, using = ".//*[@id='accessLevel.errors']")
	public WebElement RegistrateAccount_AccessLevelErr;
	@FindBy(how = How.XPATH, using = ".//*[@id='defaultStore.errors']")
	public WebElement RegistrateAccount_DefaultStoreErr;
	@FindBy(how = How.XPATH, using = ".//*[@id='role.errors']")
	public WebElement RegistrateAccount_RoleErr;
	@FindBy(how = How.XPATH, using = ".//*[@id='nemob2bRegisterForm']/div/button")
	public WebElement RegistrateAccount_GotoMyAccount;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Sorry, you have not been approved by admin')]")
	public WebElement RegistrateAccount_LoginErrMsg;
	@FindBy(how = How.ID, using = "register.email")
	public WebElement RegistrateAccount_EmailIdTextBox;
	@FindBy(how = How.ID, using = "register.chkEmail")
	public WebElement RegistrateAccount_ConfirmEmailTextBox;
	@FindBy(how = How.ID, using = "register.firstName")
	public WebElement RegistrateAccount_FirstNameTextBox;
	@FindBy(how = How.ID, using = "register.lastName")
	public WebElement RegistrateAccount_LastNameTextBox;
	@FindBy(how = How.ID, using = "password")
	public WebElement RegistrateAccount_PasswordTextBox;
	@FindBy(how = How.ID, using = "register.checkPwd")
	public WebElement RegistrateAccount_ConfirmPasswordTextBox;
	@FindBy(how = How.ID, using = "register.optin")
	public WebElement RegistrateAccount_OptionCheckBox;
	@FindBy(how = How.ID, using = "register.acceptterms")
	public WebElement RegistrateAccount_AcceptTermsCheckBox;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'submitButton')]")
	public WebElement RegistrateAccount_CreateAccountButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//font")
	public WebElement RegistrateAccount_ThankYouMessage;
	@FindBy(how = How.ID, using = "email.errors")
	public WebElement RegistrateAccount_EmailError;
	@FindBy(how = How.ID, using = "chkEmail.errors")
	public WebElement RegistrateAccount_ConfirmEmailError;
	@FindBy(how = How.ID, using = "firstName.errors")
	public WebElement RegistrateAccount_FirstNameError;
	@FindBy(how = How.ID, using = "lastName.errors")
	public WebElement RegistrateAccount_LastNameError;
	@FindBy(how = How.ID, using = "pwd.errors")
	public WebElement RegistrateAccount_PasswordError;
	@FindBy(how = How.ID, using = "checkPwd.errors")
	public WebElement RegistrateAccount_ConfirmPasswordError;

	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'Item Price')]/..//dl)[1]")
	public WebElement cartPage_FirstItermPrice;
	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'Item Price')]/..//dl)[2]")
	public WebElement cartPage_SecondItermPrice;

	// 21891
	@FindBy(how = How.XPATH, using = "//button[@id='copy-transaction']")
	public WebElement assistedServiceMode_copyTransaction;
	@FindBy(how = How.XPATH, using = "//div[@id='cboxLoadedContent']//select[@name='advTransactionCopyType']")
	public WebElement assistedServiceMode_transactionType;
	@FindBy(how = How.XPATH, using = "//div[@id='cboxLoadedContent']//input[@name='advTransactionCopyId']")
	public WebElement assistedServiceMode_transactionID;
	@FindBy(how = How.XPATH, using = "//button[@id='startAdvTransactionCopySearch']")
	public WebElement assistedServiceMode_copyIt;
	@FindBy(how = How.XPATH, using = "//*[@id='cboxLoadedContent']/div//select/option[@id='QUOTE']")
	public WebElement assistedServiceMode_transactionType_QuoteType;
	@FindBy(how = How.XPATH, using = "//*[@id='cboxLoadedContent']//input")
	public WebElement assistedServiceMode_transactionType_QuoteIDTxtBox;
	@FindBy(how = How.ID, using = "editButton")
	public WebElement Quote_editButton;

	// 23046
	@FindBy(how = How.XPATH, using = "//div[@class='facetedResults-feature-list']/b/dl/dd[1]")
	public WebElement PLP_mtmID;
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'stepsItem-item stepsItem-item')]//span[text()!='Configuration']")
	public List<WebElement> PB_tabs;
	@FindBy(how = How.XPATH, using = "//div[@data-show='true']//div[contains(@class,'expandableHeading section-header')]")
	public List<WebElement> PB_sections;
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'tepsItem-item')]//span[text()='Accessories']")
	public WebElement PB_accessoryTab;
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'tepsItem-item')]//span[text()='Warranty']")
	public WebElement PB_warrantyTab;
	@FindBy(how = How.XPATH, using = "(//ul[@class='configuratorItem-optionList']//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']//span[@class='option-price-opt'])[1]")
	public WebElement PB_warrantyCalculateType;
	@FindBy(how = How.XPATH, using = "(//ul[@class='configuratorItem-optionList']//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']//span[@class='option-price'])[1]")
	public WebElement PB_warrantyPrice;
	@FindBy(how = How.XPATH, using = "(//ul[@class='configuratorItem-optionList']//div[@class='option-priceArea configuratorItem-optionList-option-priceDelta pb-price-symbol-before']/../input)[1]")
	public WebElement PB_warrantyRdo;

	// 17505
	@FindBy(how = How.XPATH, using = "//tr[@class='comp-item radio-item visible']//input[not(contains(@class,'checked')) and not(@checked='checked') ]/../..//label[not(contains(@class,'disabled'))]//span[@class='label-text']")
	public WebElement CTO_unselectedItemText;
	@FindBy(how = How.XPATH, using = "//tr[@class='comp-item radio-item visible']//input[not(contains(@class,'checked')) and not(@checked='checked') ]/../..//label[not(contains(@class,'disabled'))]//span[@class='label-text']/../../..//td[@class='price']")
	public WebElement CTO_unselectedItemPrice;
	@FindBy(how = How.XPATH, using = "//ul[@class='cart-item-configurationDetails']/li")
	public List<WebElement> Cart_configurationDetailsItems;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-item') and contains(@class,'visible') and contains(@class,'active')]//span[@class='label-text']")
	public List<WebElement> CTO_selectedCVText;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'expandableHeading') and contains(@class,'configurationDetails')]")
	public WebElement Cart_configurationDetails;
	
	@FindBy(how = How.XPATH, using = "//li/a[contains(@href,'activateASM')]")
	public WebElement telesales_startASM;

	//25381
	@FindBy(how = How.XPATH, using = "(//div[@class='option-textFrame']//div[text()]/../..//input[@value!=''])[1]")
	public WebElement PB_firstUnselectedOption;

	//18405
	@FindBy(how = How.XPATH, using = "//div[@class='yCmsComponent'][3]//h2/a")
	public WebElement Product_cables;
	@FindBy(how = How.XPATH, using = "//h3[@class='accessoriesListing-title']/a")
	public WebElement Product_list;
	@FindBy(how = How.XPATH, using = "//p[@class='accessoriesListing-pn']")
	public WebElement Product_partnumber;
	
	//19792
	@FindBy(how = How.XPATH, using = ".//img[@title='Customize and buy']")
	public WebElement Product_cto;
	@FindBy(how = How.XPATH, using = "//h4[@id='CTO_reviewSummaryBuy']")
	public WebElement Product_Review;
	@FindBy(how = How.XPATH, using = "//table[@class='genericListLayoutChip']/tbody/tr[@class='glcTR'][67]")
	public WebElement Product_toggle;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.togLPDisplay]]_spantrue']")
	public WebElement Product_toggle_yes;
	@FindBy(how = How.XPATH, using = "//*[@id='l-label']")
	public WebElement Product_lists;
	@FindBy(how = How.XPATH, using = "//*[@id='l-price']")
	public WebElement Product_list_price;
	@FindBy(how = How.XPATH, using = "//*[@id='Content/BooleanEditor[in Content/Attribute[B2BUnit.togLPDisplay]]_spanfalse']")
	public WebElement Product_toggle_no;
	
	//17748
	@FindBy(how = How.XPATH, using = "//div[@id='resultList'][1]//b/dl/dd[1]")
	public WebElement Product_MTMplp_part;
	@FindBy(how = How.XPATH, using = "//strong[@class='partNumber']")
	public WebElement Product_MTMpdp_part;
	
	//19854
	@FindBy(how = How.XPATH, using = "//span[@class='country_display_span']")
	public WebElement Country;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'logout')]/span")
	public WebElement Logout;
	
	@FindBy(how = How.XPATH, using = "//dl[contains(@class,'pricingTotal')]/dd")
	public WebElement Cart_ProductsPrice;
	@FindBy(how = How.XPATH, using = "//input[@id='companyTaxNumber']")
	public WebElement shipping_companyTaxNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='line2']")
	public WebElement shipping_line2;
	

	//19873
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'ui-button-icon-primary')]")
	public WebElement shipping_AddressDropdownButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='country_mix']")
	public WebElement payMentPage_Country;
	@FindBy(how = How.XPATH, using = "//div[@class='checkoutForm-fieldset-content']/input[@name='billTo_country']")
	public WebElement payMentPage_CountryAnother;
	@FindBy(how = How.XPATH, using = "//div[@class='vcard-adr']/span[@class='vcard-locality']")
	public WebElement summaryPage_city;
	@FindBy(how = How.XPATH, using = "//div[@class='item_container']/ul[@class='pad_none']/li[3]")
	public WebElement thankYoyPage_city;

	//10440
	@FindBy(how = How.XPATH, using = "//div[@class='addtoCartCTA']/a[contains(@href,'cart') and contains(@class,'goToCartCTA')]")
	public WebElement Gotocart;
	@FindBy(how = How.XPATH, using = "//div[@id='orderList']")
	public WebElement orderList;
	
	//28210
	@FindBy(how = How.XPATH, using = "//header[@id='Accessories']/..//span[@class='section-title']")
	public List<WebElement> PB_accessorySectionTitle;
	@FindBy(how = How.XPATH, using = "//header[@id='Accessories']/..//span[@class='section-title']/..")
	public List<WebElement> PB_accessorySectionTitlePreLevel;
	
	//28508
	@FindBy(how = How.XPATH, using = "(//header[@id='Warranty']/..//div[@class='group-title'])[last()]")
	public WebElement PB_lastWarranty;
	@FindBy(how = How.XPATH, using = "//header[@id='Warranty']/..//div[@class='group-title']")
	public List<WebElement> PB_warrantyTitle;
	
	//28214
	@FindBy(how = How.XPATH, using = "(.//*[@id='specialH3ForCustomizedSolr'])[contains(.,'Product Type')]")
	public WebElement facet_ProductType;
	@FindBy(how = How.XPATH, using = "")
	public WebElement facet_Price;
	@FindBy(how = How.XPATH, using = "(.//*[@id='specialH3ForCustomizedSolr'])[contains(.,'Product Type')]/..//form/label/input")
	public WebElement facet_ProductType_Selection;
	@FindBy(how = How.XPATH, using = "")
	public WebElement facet_Price_Selection;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-partNumber-select')]")
	public WebElement sortBy_PartNum;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-partNumber-select')]/option[contains(text(),'A-Z')]")
	public WebElement sortBy_PartNum_AZ;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-partNumber-select')]/option[contains(text(),'Z-A')]")
	public WebElement sortBy_PartNum_ZA;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-pageSize-select')]")
	public WebElement displayNum;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-pageSize-select')]/option[contains(text(),'5')]")
	public WebElement displayNum_Sort5;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-pageSize-select')]/option[contains(text(),'10')]")
	public WebElement displayNum_Sort10;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-pageSize-select')]/option[contains(text(),'20')]")
	public WebElement displayNum_Sort20;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-pageSize-select')]/option[contains(text(),'50')]")
	public WebElement displayNum_Sort50;
	@FindBy(how = How.XPATH, using = ".//select[contains(@class,'filter-pageSize-select')]/option[contains(text(),'ALL')]")
	public WebElement displayNum_SortALL;
	@FindBy(how = How.XPATH, using = "//div[@id='resultList']//b/dl[1]/dd[1]")
	public List<WebElement> PLP_PartNo;
	
	@FindBy(how = How.XPATH, using = "//*[@id='emptyCartItemsForm']/a")
	public WebElement CartPage_EmptyCartButton;
	@FindBy(how = How.XPATH, using = ".//*[@title='Close (Esc)']")
	public WebElement PromotionBanner;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='address.company']")
	public WebElement paymentPage_companyName;
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Input keywords here for address filter.']")
	public WebElement paymentPage_inputAddressFilter;
	
	//browse-201
	@FindBy(how = How.XPATH, using = ".//*[contains(@id,'addToAccessorisForm')]/a[contains(text(),'Custom')]")
	public WebElement PDP_CustomizeForCM;
	
	//SHPOE-344
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Edit')]")
	public List<WebElement> cartPage_EditButton;
	

	//Browse-417
	@FindBy(how = How.XPATH, using = ".//*[contains(@class,'accessoriesDetail-priceBlock')]//*[contains(@class,'accessoriesDetail-priceBlock-price')]")
	public WebElement PDP_Subscription_Price;
	@FindBy(how = How.XPATH, using = "//div[@id='cboxContent']//div[contains(@class,'webprice')]/p")
	public WebElement PDP_addToCartPOP_Price;
	@FindBy(how = How.XPATH, using = "(.//div[@id='mainContent']//a[contains(@class,'breadcrumb-item')]/span)[2]")
	public WebElement PDP_superCategoryLink;
	@FindBy(how = How.XPATH, using = "//div[@id='resultList']//*[contains(@class,'accessoriesDetail-priceBlock-price')]")
	public List<WebElement> PLP_Subscription_Prices;
	@FindBy(how = How.XPATH, using = ".//*[@id='product-builder-form']//span[text()='Software']")
	public WebElement PB_SoftwareTab;

	@FindBy(how = How.XPATH, using = "//*[text()='Continue']")
	public WebElement PB_ContinueButton;
	@FindBy(how = How.XPATH, using = "//*[@class='cart-item-addedItems-wrapper']/dl/dd[contains(@class,'price')]")
	public WebElement CartPage_PBSubPrice;
	@FindBy(how = How.XPATH, using = "(//*[contains(text(),'Item Price')]/..//div)[1]")
	public WebElement SaveCartPage_SubPrice;
	@FindBy(how = How.XPATH, using = "//*[@class='cart-item-addedItems-wrapper']//dl[contains(@class,'rice')]")
	public WebElement SaveCartPage_PBSubPrice;
	@FindBy(how = How.XPATH, using = "(//*[@class='cart-item-pricing-and-quantity-itemPrice-amount'])[1]")
	public WebElement SavedCartPage_FirstItemPrice;
	@FindBy(how = How.XPATH, using = "(//*[@class='cart-item-pricing-and-quantity-finalPrice-amount'])[1]/span")
	public WebElement SavedCartPage_FirstTotalPrice;
	@FindBy(how = How.XPATH, using = "//select[@id='searchScopeSelect']/option[text()='Other pending orders']")
	public WebElement OrderWaitingApprove_OthersPending;
	
	
	@FindBy(how = How.XPATH, using = "(//*[@class='checkout-shoppingCart-previewSubtitle'])[2]")
	public WebElement ShippingPage_FirstItemPrice;
	@FindBy(how = How.XPATH, using = "(//*[@class='cart-item-pricing-and-quantity-finalPrice-amount'])[1]")
	public WebElement ShippingPage_FirstTotalPrice;
	@FindBy(how = How.XPATH, using = "(//*[@class='checkout-shoppingCart-previewSubtitle'])[2]")
	public WebElement PaymentPage_FirstItemPrice;
	@FindBy(how = How.XPATH, using = "(//*[@class='cart-item-pricing-and-quantity-finalPrice-amount'])[1]")
	public WebElement PaymentPage_FirstTotalPrice;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'checkout-review-item-partNumber')]/strong/..")
	public WebElement SummaryPage_FirstItemPrice;
	@FindBy(how = How.XPATH, using = "(//*[@class='checkout-review-item-pricing-value'])[1]")
	public WebElement SummaryPage_FirstTotalPrice;
	@FindBy(how = How.XPATH, using = "//*[@class='checkout-confirm-orderSummary-table-content']//td[@data-title='Item Price']")
	public WebElement ThanksYouPage_FirstItemPrice;
	@FindBy(how = How.XPATH, using = "//*[@class='checkout-confirm-orderSummary-table-content']//td[@data-title='Total']")
	public WebElement ThanksYouPage_FirstTotalPrice;
	@FindBy(how = How.XPATH, using = "//select[@id='address_sel']")
	public WebElement shipping_validateSelect;
	@FindBy(how = How.XPATH, using = "(//select[@id='address_sel']/option)[2]")
	public WebElement shipping_validateSelectOne;
	@FindBy(how = How.XPATH, using = "(//*[@class='checkout-confirm-orderSummary-table-productPrice'])[1]")
	public WebElement QuoteConfirm_FirstItemPrice;
	@FindBy(how = How.XPATH, using = "(//*[@class='checkout-confirm-orderSummary-table-productPrice'])[2]")
	public WebElement QuoteConfirm_FirstTotalPrice;
	
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'itemPrice')]/dl)[2]")
	public WebElement CartPage_SecondPrice;
	@FindBy(how = How.XPATH, using = "(//div[contains(@class,'itemPrice')]/dl)[3]")
	public WebElement CartPage_ThirdPrice;
}
