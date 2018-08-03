package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class B2CPage {
	public WebDriver PageDriver;

	public B2CPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}

	// PassCode GateKeeper
	@FindBy(how = How.ID, using = "gatekeeper.passcode.id")
	public WebElement PasscodeGateKeeper_PasscodeTextBox;

	// SerialNumber GateKeeper
	@FindBy(how = How.ID, using = "gatekeeper.serialNumber.id")
	public WebElement SerialNumberGateKeeper_SerialNumberTextBox;
	@FindBy(how = How.ID, using = "gatekeeper.lastname.id")
	public WebElement SerialNumberGateKeeper_LastNameTextBox;

	// Registration GateKeeper
	@FindBy(how = How.ID, using = "gatekeeper.login.email.id")
	public WebElement RegistrationGateKeeper_LenovoIdTextBox;
	@FindBy(how = How.ID, using = "gatekeeper.login.password.id")
	public WebElement RegistrationGateKeeper_PasswordTextBox;

	// GateKeeper Common Login Button
	@FindBy(how = How.XPATH, using = "//*[@id='nemoGatekeeperForm']/button")
	public WebElement GateKeeper_LoginButton;
	// GateKeeper Common Login Button
	@FindBy(how = How.XPATH, using = "//*[@id='nemoLoginForm']//button")
	public WebElement RegisterGateKeeper_LoginButton;
	// GateKeeper Common Login Button
	@FindBy(how = How.XPATH, using = "//button[@class='mfp-close']")
	public WebElement HomePage_CloseAdvButton;

	// Account Elements
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'createAccount') or contains(@class,'register-button')] ")
	public WebElement Login_CreateAnAccountButton;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'register-button')]")
	public WebElement RegisterGateKeeper_CreateAnAccountButton;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'login.email.id')]")
	public WebElement Login_LenovoIDTextBox;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'login.password')]")
	public WebElement Login_LenovoPasswordTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='nemoLoginForm']//button")
	public WebElement Login_LogInButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='accErrorMsgs']/ul/li")
	public WebElement Login_ErrorMsg;
	@FindBy(how = How.XPATH, using = ".//div[@class='signInModule-content']//a[contains(@href,'my-account/orders')]")
	public WebElement MyAccount_ViewOrderHistoryLink;
	@FindBy(how = How.ID, using = "trackOrderStatus")
	public WebElement OrderHistory_TrackOrderStatus;
	@FindBy(how = How.XPATH, using = ".//*[@id='myAccount']//a[@class='linkLevel_2 link-hasNoChildren']")
	public WebElement MyAccount_LoginLink;

	// Create Account Page
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
	@FindBy(how = How.ID, using = "acceptTerms.errors")
	public WebElement RegistrateAccount_AcceptTermsError;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']/div[1]/div/div/div/div")
	public WebElement RegistrateAccount_AlreadyRegisEmailError;
	@FindBy(how = How.XPATH, using = "//button[@class='positive']")
	public WebElement RegistrateAccount_SigninToMyAccountButton;

	@FindBy(how = How.XPATH, using = "//a[contains(@href,'/contact/')]")
	public WebElement Homepage_ContactUsLink;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'emailUsPage')]")
	public WebElement ContactUs_EmailUsLink;
	@FindBy(how = How.XPATH, using = "//select[@id='drop-0']")
	public WebElement EmailUs_TypeDropdown;
	@FindBy(how = How.XPATH, using = "//select[@id='drop-1']")
	public WebElement EmailUs_SubjectDropdown;
	@FindBy(how = How.XPATH, using = "//input[@name='Email.userFirstName']")
	public WebElement EmailUs_FirstName;
	@FindBy(how = How.XPATH, using = "//input[@name='Email.userLastName']")
	public WebElement EmailUs_LastName;
	@FindBy(how = How.XPATH, using = "//input[@name='Email.FromAddress']")
	public WebElement EmailUs_EmailAddress;
	@FindBy(how = How.XPATH, using = "//input[@name='Email.userPhone']")
	public WebElement EmailUs_Phone;
	@FindBy(how = How.XPATH, using = "//input[@name='Email.orderId']")
	public WebElement EmailUs_OrderNum;
	@FindBy(how = How.XPATH, using = "//textarea[@name='Email.Comments']")
	public WebElement EmailUs_Message;
	@FindBy(how = How.XPATH, using = "//input[@class='shop button avg']")
	public WebElement EmailUs_SendButton;

	// Navigation bar
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'product')]/a/span")
	public WebElement Navigation_ProductsLink;
	@FindBy(how = How.XPATH, using = "//li[@id='cartIcon']/a")
	public WebElement Navigation_CartIcon;
	@FindBy(how = How.ID, using = "rollovercartViewCart")
	public WebElement Navigation_ViewCartButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='myAccount' or contains(@href,'my-account')]/a")
	public WebElement Navigation_MyAccountIcon;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'my-account') and not(contains(@class,'has-submenu'))]")
	public WebElement Navigation_SignInLink;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'logout')]/div[contains(@class,'link_text')]")
	public WebElement Navigation_SignOutLink;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'laptops') or contains(@href,'notebooks') or contains(@href,'Laptops')]")
	public WebElement Navigation_Laptop;
	@FindBy(how = How.XPATH, using = "(//ul[contains(@class,'menu general')]/li[contains(@class,'myaccount')]/a/../div//a)[2]/div")
	public WebElement Navigation_subMyAccountSpan;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'tablets') or contains(@href,'TABLETS')]")
	public WebElement Navigation_Tablets;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'desktops')]")
	public WebElement Navigation_Desktops;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'workstations')]")
	public WebElement Navigation_WorkStations;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'smartdevices') or contains(@herf,'Smartphones')]")
	public WebElement Navigation_SmartPhones;
	@FindBy(how = How.XPATH, using = "//a/div[contains(.,'CURRENT OFFERS')]")
	public WebElement Navigation_CurrentOffers;
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'deals')]/a")
	public WebElement Navigation_DEALS;
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'deals')]/div//a/div")
	public WebElement Navigation_DEALSFirstLink;
	@FindBy(how = How.XPATH, using = "//ol[@class='menu_3']")
	public WebElement Navigation_SubOffers;
	@FindBy(how = How.XPATH, using = "(.//a[@class='linkLevel_2']//h3)[1]")
	public WebElement Navigation_StuSale;
	@FindBy(how = How.XPATH, using = "(.//a[@class='linkLevel_2']//h3)[2]")
	public WebElement Navigation_QuickShip;
	@FindBy(how = How.XPATH, using = "//div[@class='content_top']//img")
	public WebElement Navigation_HeroImage;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'Servers')]")
	public WebElement Navigation_Servers;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'Warranty')]")
	public WebElement Navigation_Warranty;
	@FindBy(how = How.XPATH, using = "//img[@class='boletoPaymentMedia']")
	public WebElement BoletoForm;

	@FindBy(how = How.XPATH, using = "//a[@class='klarna-apply-now']")
	public WebElement Klarna_ApplyNow;
	@FindBy(how = How.ID, using = "lia-iframe")
	public WebElement Klarna_Frame;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'k-lia-submit')]")
	public WebElement Klarna_ContinueButton;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__pan']")
	public WebElement Klarna_CardNum;
	@FindBy(how = How.XPATH, using = ".//h2[contains(@class,'singleModelTitle')]")
	public WebElement PDP_Title;

	// Monitor page
	@FindBy(how = How.XPATH, using = ".//nav[@class='breadcrumb-wrapper']/span/span")
	public WebElement Monitor_NavigationSummaryLabel;
	@FindBy(how = How.XPATH, using = ".//*[@id='longscroll-subseries']//div[contains(@class,'titleSection')]")
	public WebElement Monitor_NewSummaryLabel;

	@FindBy(how = How.XPATH, using = ".//*[@id='longscroll-subseries']/nav/span/span")
	public WebElement Monitor_NewNavigationSummaryLabel;
	@FindBy(how = How.XPATH, using = "//div[@class='accessoriesDetail-description_1']/p[1]")
	public WebElement Monitor_SummaryLabel;
	@FindBy(how = How.XPATH, using = "//iframe[@name='MP-Checkout']")
	public WebElement MercadoFrame;
	@FindBy(how = How.ID, using = "use_account")
	public WebElement Mercado_UseAccount;
	@FindBy(how = How.ID, using = "user_id")
	public WebElement Mercado_UserID;
	@FindBy(how = How.ID, using = "password")
	public WebElement Mercado_Password;
	@FindBy(how = How.ID, using = "init")
	public WebElement Mercado_LoginButton;
	@FindBy(how = How.ID, using = "card_test")
	public WebElement Mercado_Card;
	@FindBy(how = How.ID, using = "credit_card")
	public WebElement Mercado_CreditCard;
	@FindBy(how = How.ID, using = "cardNumber")
	public WebElement Mercado_CardNumber;
	@FindBy(how = How.ID, using = "cardExpiration")
	public WebElement Mercado_CardExpiration;
	@FindBy(how = How.ID, using = "securityCode")
	public WebElement Mercado_SecurityCode;
	@FindBy(how = How.ID, using = "installments")
	public WebElement Mercado_Installments;
	@FindBy(how = How.ID, using = "submit")
	public WebElement Mercado_SubmitButton;
	@FindBy(how = How.ID, using = "next")
	public WebElement Mercado_NextButton;
	@FindBy(how = How.ID, using = "cardholderName")
	public WebElement Mercado_CardName;
	// @FindBy(how = How.XPATH, using =
	// "//a[contains(@href,'mpPayment/placeOrder')]")
	@FindBy(how = How.XPATH, using = "//div[@id='mppayment_error_msg']//a[contains(@href,'add-payment-method')]")
	public WebElement Mercado_BackToPayment;

	// Cart Page
	@FindBy(how = How.XPATH, using = "//div[@id='quickAddInput' or @id='inputOuter']/input")
	public WebElement Cart_QuickOrderTextBox;
	@FindBy(how = How.XPATH, using = "//div[@id='quickAddInput' or @id='inputOuter']/*[contains(@class,'cart-checkoutButtons')]")
	public WebElement Cart_AddButton;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'lenovo-checkout')]")
	public WebElement Cart_CheckoutButton;
	@FindBy(how = How.XPATH, using = "//*[@id='savedcart_button_new']")
	public WebElement Cart_saveCart;
	@FindBy(how = How.XPATH, using = "//*[@id='realsavecartname']")
	public WebElement Cart_nameTextBox;
	@FindBy(how = How.XPATH, using = "//*[@id='addToCartButtonTop']")
	public WebElement Cart_saveCartBtn;
	@FindBy(how = How.XPATH, using = "//tbody/tr[1]/td[8]/a/div")
	public WebElement Cart_viewCartHistory;
	@FindBy(how = How.XPATH, using = ".//*[@id='openCartButton']")
	public WebElement Cart_openCart;
	@FindBy(how = How.XPATH, using = "//div/input[@name='searchKeyWord']")
	public WebElement Cart_searchTextBox;
	@FindBy(how = How.XPATH, using = "//*[@id='searchType']")
	public WebElement Cart_searchCartDropDown;
	@FindBy(how = How.XPATH, using = "//*[@id='searchKeyWord2']/input")
	public WebElement Cart_searchButton;
	@FindBy(how = How.ID, using = "quote_button")
	public WebElement Cart_CheckoutDisable;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']/div[1]")
	public WebElement Cart_ErrorMsg;
	@FindBy(how = How.XPATH, using = ".//a[@id='rollovercartViewCart']")
	public WebElement Cart_OpenCartDropdown;

	// 21875
	@FindBy(how = How.XPATH, using = "//form[@id='emptyCartItemsForm']/a")
	public WebElement Cart_emptyCart;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'quantity')]")
	public WebElement Cart_quantity;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'QuantityProduct')]")
	public WebElement Cart_quantityProduct;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'totalPriceWithTax')]")
	public WebElement Cart_totalPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='confignumber']/div/strong[2]")
	public WebElement Cart_CartId;

	@FindBy(how = How.ID, using = "chk_all")
	public WebElement KCP_CheckAll;
	@FindBy(how = How.XPATH, using = ".//li[@name='Hyundai']")
	public WebElement KCP_Hyundai;
	@FindBy(how = How.XPATH, using = ".//a[@class='btn btn_prev']")
	public WebElement KCP_NextButton;
	@FindBy(how = How.XPATH, using = ".//div[@class='menu_box']//a")
	public WebElement KCP_StartPayButton;
	@FindBy(how = How.ID, using = "cardno1")
	public WebElement KCP_CardNo1;
	@FindBy(how = How.ID, using = "cardno2")
	public WebElement KCP_CardNo2;
	@FindBy(how = How.ID, using = "cardno3")
	public WebElement KCP_CardNo3;
	@FindBy(how = How.ID, using = "cardno4")
	public WebElement KCP_CardNo4;

	// Guest checkout Page
	@FindBy(how = How.XPATH, using = ".//*[@id='guestForm']/button")
	public WebElement Checkout_StartCheckoutButton;

	// Shipping Page
	@FindBy(how = How.ID, using = "firstName")
	public WebElement Shipping_FirstNameTextBox;
	@FindBy(how = How.ID, using = "lastName")
	public WebElement Shipping_LastNameTextBox;
	@FindBy(how = How.ID, using = "company")
	public WebElement Shipping_CompanyNameTextBox;
	@FindBy(how = How.ID, using = "line1")
	public WebElement Shipping_AddressLine1TextBox;
	@FindBy(how = How.ID, using = "townCity")
	public WebElement Shipping_CityTextBox;
	@FindBy(how = How.ID, using = "line3")
	public WebElement Shipping_AddressLine3TextBox;
	@FindBy(how = How.ID, using = "state")
	public WebElement Shipping_StateDropdown;
	@FindBy(how = How.ID, using = "postcode")
	public WebElement Shipping_PostCodeTextBox;
	@FindBy(how = How.XPATH, using = "//input[contains(@name,'phone')]")
	public WebElement Shipping_ContactNumTextBox;
	@FindBy(how = How.ID, using = "email")
	public WebElement Shipping_EmailTextBox;
	@FindBy(how = How.ID, using = "checkout_validateFrom_ok")
	public WebElement Shipping_AddressMatchOKButton;
	@FindBy(how = How.ID, using = "checkoutForm-shippingContinueButton")
	public WebElement Shipping_ContinueButton;
	@FindBy(how = How.CSS, using = ".textLink.checkout-shippingAddress-editLink")
	public WebElement Shipping_editAddress;
	@FindBy(how = How.ID, using = "copyAddressToBilling")
	public WebElement Shipping_copyAddressToBilling;
	@FindBy(how = How.ID, using = "checkout_validateFrom_ok")
	public WebElement Shipping_validateAddress;
	@FindBy(how = How.XPATH, using = "//input[contains(@value,'USE ADDRESS AS ENTERED')]")
	public WebElement Shipping_AddressVerifyButton;
	@FindBy(how = How.XPATH, using = "//li[@class='list-group-item']/input")
	public WebElement Shipping_validateAddressItem;
	@FindBy(how = How.ID, using = "consumerTaxNumber")
	public WebElement Shipping_consumerTaxNumber;
	@FindBy(how = How.ID, using = "personalNumber")
	public WebElement Shipping_personalNumber;
	@FindBy(how = How.XPATH, using = ".//*[@id='individualRGID']")
	public WebElement Shipping_RegistrationNO;
	@FindBy(how = How.ID, using = "companyTaxNumber")
	public WebElement Shipping_CompanyTaxNumber;
	@FindBy(how = How.ID, using = "building")
	public WebElement Shipping_building;
	@FindBy(how = How.ID, using = "line2")
	public WebElement Shipping_line2;
	@FindBy(how = How.ID, using = "district")
	public WebElement Shipping_district;
	@FindBy(how = How.ID, using = "address.idCustFiscalType")
	public WebElement Shipping_CustFiscalType;
	// Payment Page - address
	@FindBy(how = How.ID, using = "address.firstName")
	public WebElement Payment_FirstNameTextBox;
	@FindBy(how = How.ID, using = "address.lastname")
	public WebElement Payment_LastNameTextBox;
	@FindBy(how = How.ID, using = "address.line1")
	public WebElement Payment_AddressLine1TextBox;
	@FindBy(how = How.ID, using = "address.townCity")
	public WebElement Payment_CityTextBox;
	@FindBy(how = How.ID, using = "address.city")
	public WebElement Payment_City2TextBox;
	@FindBy(how = How.ID, using = "address.region")
	public WebElement Payment_StateDropdown;
	@FindBy(how = How.ID, using = "address.postcode")
	public WebElement Payment_PostCodeTextBox;
	@FindBy(how = How.XPATH, using = "//input[contains(@name,'phone') and @type!='hidden']")
	public WebElement Payment_ContactNumTextBox;
	@FindBy(how = How.ID, using = "address.consumerTaxNumber")
	public WebElement Payment_consumerTaxNumber;
	@FindBy(how = How.ID, using = "address.companyTaxNumber")
	public WebElement Payment_companyTaxNumber;
	@FindBy(how = How.ID, using = "address.company")
	public WebElement Payment_CompanyNameTextBox;

	@FindBy(how = How.XPATH, using = "//a[@class='loginHeaderLink noSocialLogin']")
	public WebElement Checkout_LoginLink;
	@FindBy(how = How.XPATH, using = "//input[@name='j_username']")
	public WebElement Checkout_LoginEmail;
	@FindBy(how = How.XPATH, using = "//input[@name='j_password']")
	public WebElement Checkout_LoginPassword;
	@FindBy(how = How.ID, using = "nemoAjaxLoginForm_BTN")
	public WebElement Checkout_LoginSignInButton;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'checkoutForm-submitButton')]")
	public WebElement Checkout_LoginSignInButton_oldUI;

	// Payment Page - payment methods
	@FindBy(how = How.ID, using = "PaymentTypeSelection_CARD")
	public WebElement Payment_CreditCardRadioButton;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_KLARNA")
	public WebElement Payment_KlarnaRadioButton;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_ZIBBY")
	public WebElement Payment_ZibbyRadioButton;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_KLARNA']/..//label[contains(@for,'KLARNA')]")
	public WebElement KlarnaRadioButton;
	@FindBy(how = How.XPATH, using = "//button[contains(@ng-click,'toggleDetails')]")
	public WebElement Zibby_Detail;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='phone.verification.phone']")
	public WebElement Zibby_Phone;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='phone.verification.privacyPolicy']")
	public WebElement Zibby_Terms;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='phone.verification.creditCheck']")
	public WebElement Zibby_Terms2;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='verification.verification.code']")
	public WebElement Zibby_DigitalCode;
	@FindBy(how = How.XPATH, using = "//button[@button-text='Select Address']")
	public WebElement Zibby_SelectAddressButton;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='verification.verification.lastFour']")
	public WebElement Zibby_DigitalCode2;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='checkout.payment.CardNumber']")
	public WebElement Zibby_CardNumber;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='checkout.payment.CardExpiration']")
	public WebElement Zibby_CardExpireDate;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='checkout.payment.CardCvv']")
	public WebElement Zibby_CardSecurityCode;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='checkout.Disclosure']")
	public WebElement Zibby_CardTerms;
	@FindBy(how = How.XPATH, using = "//input[@ng-model='checkout.checkId']")
	public WebElement Zibby_CardTerms2;
	@FindBy(how = How.XPATH, using = "//button[@class='vibrant button ng-isolate-scope not-loading']")
	public WebElement Zibby_Continue;
	@FindBy(how = How.XPATH, using = "//*[@class='agreement-link ng-scope']")
	public WebElement Zibby_AgreementLink;
	@FindBy(how = How.XPATH, using = "//*[@class='button vibrant button-agree ng-binding']")
	public WebElement Zibby_IAgreeButton;
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='vm.buttonAmount']")
	public WebElement Zibby_MakePaymentButton;
	@FindBy(how = How.XPATH, using = "//iframe[contains(@src,'zibby.com')]")
	public WebElement Zibby_Iframe;
	@FindBy(how = How.XPATH, using = "//button[contains(@ng-click,'header.close')]")
	public WebElement Zibby_CloseButton;
	@FindBy(how = How.ID, using = "zby-contract")
	public WebElement Zibby_Contract;
	// @FindBy(how = How.ID, using = "Paymetric_CreditCardType")
	@FindBy(how = How.ID, using = "c-ct")
	public WebElement Payment_CardTypeDropdown;
	// @FindBy(how = How.ID, using = "Paymetric_CreditCardNumber")
	@FindBy(how = How.ID, using = "c-cardnumber")
	public WebElement Payment_CardNumberTextBox;
	// @FindBy(how = How.ID, using = "Paymetric_Exp_Month")
	@FindBy(how = How.ID, using = "c-exmth")
	public WebElement Payment_CardMonthTextBox;
	// @FindBy(how = How.ID, using = "Paymetric_Exp_Year")
	@FindBy(how = How.ID, using = "c-exyr")
	public WebElement Payment_CardYearTextBox;
	// @FindBy(how = How.ID, using = "Paymetric_CVV")
	@FindBy(how = How.ID, using = "c-cvv")
	public WebElement Payment_SecurityCodeTextBox;
	@FindBy(how = How.ID, using = "card_nameOnCard")
	public WebElement Payment_CardHolderNameTextBox;
	@FindBy(how = How.XPATH, using = "//*[@id='add-payment-method-continue' or @class='billing_section_continue_button']")
	public WebElement Payment_ContinueButton;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'qa-configurator-sectionExpandButton')]")
	public WebElement CTO_ChangeWarrantyButton;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_PAYPAL")
	public WebElement Payment_paypalRadioBox;
	@FindBy(how = How.CSS, using = "#email")
	public WebElement Payment_paypalLoginID;
	@FindBy(how = How.CSS, using = "#password")
	public WebElement Payment_paypalLoginPwd;
	@FindBy(how = How.CSS, using = "#btnLogin")
	public WebElement Payment_paypalSignBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='confirmButtonTop']")
	public WebElement Payment_paypalContinueBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='btnNext']")
	public WebElement Payment_paypalNextBtn;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_TWOCARDS")
	public WebElement Payment_multipleCreditCards;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderNumber']")
	public WebElement payment_purchaseNum;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderDate']")
	public WebElement payment_purchaseDate;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_PURCHASEORDER']")
	public WebElement payment_PurchaseOrder;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_LFS']")
	public WebElement payment_LFS;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_PARTYLEASING")
	public WebElement payment_Party;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_BANKDEPOSIT']/..")
	public WebElement Payment_bankDepositLabel;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_PURCHASEORDER']/..")
	public WebElement Payment_purchaseOrderLabel;
	@FindBy(how = How.XPATH, using = "//input[@name='external.field.password']")
	public WebElement Payment_VisaVerifiedTextBox;
	@FindBy(how = How.XPATH, using = "//input[@name='UsernamePasswordEntry']")
	public WebElement Payment_VisaVerifiedSubmitButton;

	// SMB
	@FindBy(how = How.ID, using = "smb-login-button")
	public WebElement SMB_LoginButton;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'smbRegistration')]")
	public WebElement SMB_CreateAnAccountButton;
	@FindBy(how = How.ID, using = "firstName")
	public WebElement SMB_FirstName;
	@FindBy(how = How.ID, using = "lastName")
	public WebElement SMB_LastName;
	@FindBy(how = How.ID, using = "email")
	public WebElement SMB_Email;
	@FindBy(how = How.ID, using = "phoneticName")
	public WebElement SMB_Phone;
	@FindBy(how = How.ID, using = "companyName")
	public WebElement SMB_CompanyName;
	@FindBy(how = How.ID, using = "pwd")
	public WebElement SMB_Password;
	@FindBy(how = How.ID, using = "checkPwd")
	public WebElement SMB_ConfirmPassword;
	@FindBy(how = How.ID, using = "companyAddress1")
	public WebElement SMB_CompanyAddress1;
	@FindBy(how = How.ID, using = "companyAddress2")
	public WebElement SMB_CompanyAddress2;
	@FindBy(how = How.ID, using = "city")
	public WebElement SMB_City;
	@FindBy(how = How.ID, using = "state")
	public WebElement SMB_State;
	@FindBy(how = How.ID, using = "zipCode")
	public WebElement SMB_ZipCode;
	@FindBy(how = How.ID, using = "industry")
	public WebElement SMB_Industry;
	@FindBy(how = How.ID, using = "companySize")
	public WebElement SMB_CompanySize;
	@FindBy(how = How.ID, using = "department")
	public WebElement SMB_Department;
	@FindBy(how = How.XPATH, using = "//button[@value='Submit']")
	public WebElement SMB_SubmitButton;
	@FindBy(how = How.XPATH, using = "//button[@value='Submit']")
	public WebElement SMB_MyAccount;
	//*[@id="myAccount"]


	// Review Order page
	@FindBy(how = How.ID, using = "Terms1")
	public WebElement OrderSummary_AcceptTermsCheckBox;
	@FindBy(how = How.ID, using = "orderSummaryReview_placeOrder")
	public WebElement OrderSummary_PlaceOrderButton;

	// Order Thank you Page
	@FindBy(how = How.CSS, using = "div[class='checkout-confirm-orderNumbers'] tr:nth-child(2) td:nth-child(2)")
	public WebElement OrderThankyou_OrderNumberLabel;
	@FindBy(how = How.XPATH, using = "//div[@class='confirmation-items']/div[2]/span[2]")
	public WebElement OrderThankyou_OrderNumberLabelNew;
	@FindBy(how = How.XPATH, using = "//span[@ng-bind='transaction.order.referenceCode']")
	public WebElement OrderThankyou_OrderNumberLabelPayU;
	@FindBy(how = How.ID, using = "btnRegisterWithOrder")
	public WebElement OrderThankyou_CreateAccountButton;
	@FindBy(how = How.XPATH, using = "//span[@translate='tx_state.cc.PENDING']")
	public WebElement PayU_PendingStatus;
	@FindBy(how = How.XPATH, using = "//span[@translate='tx_state.DECLINED']")
	public WebElement PayU_DeclinedStatus;

	// PDP Page
	@FindBy(how = How.XPATH, using = ".//*[@id='builderPricingSummary']//button[@id='addToCartButtonTop']")
	public WebElement PDP_AddToCartButton;
	@FindBy(how = How.XPATH, using = ".//button[@id='addToCartButtonTop'][contains(@class,'button-standard-alt')]")
	public WebElement PDP_AddToCartButton_2;
	@FindBy(how = How.ID, using = "tab-li-nav-currentmodels")
	public WebElement PDP_ViewCurrentModelTab;

	// Series and SubSeries page
	@FindBy(how = How.XPATH, using = "//a[@alt='THINKPAD']")
	public WebElement Series_Thinkpad;
	@FindBy(how = How.XPATH, using = "//ol[@class='brandListings']/li/div/div[last()]/a")
	public WebElement Series_ViewBtn;
	@FindBy(how = How.XPATH, using = "//ol[@class='seriesListings']/li/div/div[last()]//a")
	public WebElement Series_LearmMoreBtn;

	// Product Detail and List page
	@FindBy(how = How.ID, using = "CTO_addToCart")
	public WebElement Product_AddToCartBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='product-builder-form']/descendant::div/button")
	public WebElement Product_Continue;
	@FindBy(how = How.XPATH, using = "//a[@id='view-customize']")
	public WebElement Product_viewModel;
	@FindBy(how = How.CSS, using = ".pricingSummary-button.button-called-out.button-full")
	public WebElement Product_Productbuilder_AddToCartBtn;
	@FindBy(how = How.XPATH, using = "//button[contains(.,'Customi')]")
	public WebElement Product_customizeBtn;

	// Request quote page
	@FindBy(how = How.XPATH, using = "//input[@id='quote_button']")
	public WebElement Quote_requestBtn;
	@FindBy(how = How.XPATH, using = "//tbody/tr[1]/td[2]/input[@id='repId']")
	public WebElement Quote_RepIDTextBox;
	@FindBy(how = How.CSS, using = "#submit-quote-button")
	public WebElement Quote_submitQuoteBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']/div[1]/div/div[1]/table/tbody/tr[2]/td[2]")
	public WebElement Quote_quoteNum;
	@FindBy(how = How.XPATH, using = "//*[@id='searchValue']")
	public WebElement Quote_searchTextBox;
	@FindBy(how = How.XPATH, using = "//*[@id='search-button']")
	public WebElement Quote_searchSubmitBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='accountQuote-content']/div[3]/table/tbody/tr[1]/td[8]/p/a")
	public WebElement Quote_viewMoreBtn;
	@FindBy(how = How.XPATH, using = "//*[@id='convertoorderButton']")
	public WebElement Quote_convertOrder;

	// mobile elements
	@FindBy(how = How.XPATH, using = "//a[@class='main_Menu_icon']")
	public WebElement mainMenu;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Accessories')]")
	public WebElement Navigation_Accessory;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'accessories') or contains(@href,'accessory')]")
	public WebElement Navigation_Accessories;
	@FindBy(how = How.XPATH, using = ".//*[@id='productGrid-target']//div[1]/div/div[2]/h2/a")
	public WebElement firstAccessoryType;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//ol/li[2]//div[4]/a")
	public WebElement firstAccessoryProduct;
	@FindBy(how = How.XPATH, using = ".//*[@id='resultsList']/div[1]/div[4]/a")
	public WebElement FirstAccessoryProduct_KR;
	@FindBy(how = How.ID, using = "addToCart")
	public WebElement accessoryAddtoCart;
	@FindBy(how = How.XPATH, using = ".//*[@id='guestForm']/button")
	public WebElement GuestCheckout;
	@FindBy(how = How.XPATH, using = "//fieldset/legend/a[contains(@class,'checkout-shippingAddress-editLink')]")
	public WebElement EditAddress;
	@FindBy(how = How.XPATH, using = "//input[@id='copyAddressToBilling']")
	public WebElement CopyAddress;
	@FindBy(how = How.XPATH, using = "//input[@name='phone']")
	public WebElement Mobile;
	@FindBy(how = How.XPATH, using = "//input[@id='STANDARD_SHIPPING']")
	public WebElement standardShipping;
	@FindBy(how = How.XPATH, using = "//input[@value='ok']")
	public WebElement addressValidation;

	@FindBy(how = How.CLASS_NAME, using = "billing_section_continue_button")
	public WebElement Payment_ContinueButonNew;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'Visa') or contains(text(),'VISA')]")
	public WebElement Visa;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'American Express') or contains(text(),'AMEX')]")
	public WebElement AmericaExpress;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'Master Card') or contains(text(),'Mastercard')]")
	public WebElement MasterCard;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'Discover')]")
	public WebElement Discover;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_MERCADOPAGO")
	public WebElement Payment_Mercado;
	@FindBy(how = How.ID, using = "OffAmazonPaymentsWidgets0")
	public WebElement Payment_Amazon;
	@FindBy(how = How.ID, using = "ap_email")
	public WebElement Amazon_Email;
	@FindBy(how = How.ID, using = "ap_password")
	public WebElement Amazon_Password;
	@FindBy(how = How.ID, using = "signInSubmit")
	public WebElement Amazon_SigninButton;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_Wire']")
	public WebElement Wire;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_BANKDEPOSIT']")
	public WebElement DirectDeposit;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_TWOCARDS']")
	public WebElement TwoCardRadioButton;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_KCP']")
	public WebElement KCPButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='kcp_pay_method']/option[contains(text(),'신용카드')]")
	public WebElement KCPOption;
	@FindBy(how = How.XPATH, using = "//input[@value='LEASING']")
	public WebElement Leasing;
	@FindBy(how = How.XPATH, using = ".//*[@id='PaymentTypeSelection_CHECK']")
	public WebElement ChequePayment;
	@FindBy(how = How.XPATH, using = "//input[@name='external.field.password']")
	public WebElement VisaPassword;
	@FindBy(how = How.XPATH, using = "//input[@name='UsernamePasswordEntry']")
	public WebElement VisaSubmit;
	@FindBy(how = How.XPATH, using = ".//*[@id='globalMessages']/div")
	public WebElement errormessage;
	@FindBy(how = How.XPATH, using = "//input[@id='Paymetric_CreditCardNumber']")
	public WebElement CardNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='Paymetric_Exp_Month']")
	public WebElement ExpiryMonth;
	@FindBy(how = How.XPATH, using = "//input[@id='Paymetric_Exp_Year']")
	public WebElement ExpiryYear;
	@FindBy(how = How.XPATH, using = "//input[@id='Paymetric_CVV']")
	public WebElement SecurityCode;
	@FindBy(how = How.XPATH, using = "//input[@id='card_nameOnCard']")
	public WebElement NameonCard;
	@FindBy(how = How.XPATH, using = ".//*[@id='add-payment-method-continue']")
	public WebElement ContinueforPayment;
	// PDP page
	@FindBy(how = How.XPATH, using = "//div[@class='expandableHeading section-header'][1]")
	public WebElement PDP_firstConfigraution;
	@FindBy(how = How.XPATH, using = "(//div[@data-show='true']//div[@class='option-textFrame'])[1]/div[1]")
	public WebElement PDP_firstAccessories;

	// 20315
	@FindBy(how = How.XPATH, using = "//button[@class='button-called-out']")
	public WebElement StartCheckOut;
	@FindBy(how = How.ID, using = "line2")
	public WebElement Shipping_AddressLine2TextBox;
	@FindBy(how = How.XPATH, using = "//div[@class='klarna-button']")
	public WebElement Payment_KlarnaButton;
	@FindBy(how = How.XPATH, using = "(//a[@class='klarna-apply-now'])[last()]")
	public WebElement Payment_KlarnaApplyNow;
	@FindBy(how = How.XPATH, using = "//div[@class='cui__dialog']")
	public WebElement Payment_KlarnaInfoForm;
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement Payment_KlarnaContinue;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'submit')]")
	public WebElement Payment_KlarnaSubmit;
	@FindBy(how = How.XPATH, using = "//div[@id='combined-account-variants']/div/label[1]")
	public WebElement Payment_KlarnaTerms;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__pan']")
	public WebElement Payment_KlarnaCreditCardNumber;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__pan']/span[1]")
	public WebElement Payment_KlarnaCreditCardNumber1;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__pan']/span[2]")
	public WebElement Payment_KlarnaCreditCardNumber2;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__pan']/span[3]")
	public WebElement Payment_KlarnaCreditCardNumber3;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__pan']/span[4]")
	public WebElement Payment_KlarnaCreditCardNumber4;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__detail']/span[2][@class='vcn__detail-info']")
	public WebElement Payment_KlarnaExpirationDate;
	@FindBy(how = How.XPATH, using = "//div[@class='vcn__detail']/span[4][@class='vcn__detail-info']")
	public WebElement Payment_KlarnaCVC;
	@FindBy(how = How.XPATH, using = "//button[@class='clipboard cui__button--primary k-lia-submit cui__button--full-width']")
	public WebElement Payment_KlarnaCopyToClipboard;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-review-payment-details']/dd[2]")
	public WebElement OrderSummary_checkoutReviewPaymentNum;

	// Product No retrieved
	@FindBy(how = How.CSS, using = "h1.bar_3-heading")
	public WebElement ProductCategory;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Next')]")
	public WebElement NextPage;
	@FindBy(how = How.XPATH, using = ".//*[@title='Close (Esc)']")
	public WebElement PromotionBanner;
	@FindBy(how = How.XPATH, using = ".//*[@id='addToCartButtonTop' and @onclick!='']")
	public WebElement LaptopPartNumberTag;
	@FindBy(how = How.XPATH, using = "//strong[contains(text(),'Sold Out')]")
	public WebElement SoldOutTag;
	@FindBy(how = How.XPATH, using = "(//strong[contains(text(),'Temporarily Unvailable')]/..)[1]")
	public WebElement TempNotAvailable;
	// 21832
	@FindBy(how = How.XPATH, using = "//div[@class='signInModule-content']//a[contains(@href,'activateASM')]")
	public WebElement MyAccount_Telesales;
	@FindBy(how = How.XPATH, using = "//input[@name='customerName']")
	public WebElement Tele_CustomerId;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'start-session')]")
	public WebElement Tele_StartSession;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_BANKDEPOSIT']")
	public WebElement Payment_bankDeposit;
	@FindBy(how = How.XPATH, using = "//select[@id='lang4TelSelect']")
	public WebElement OrderSummary_emailLanguage;
	@FindBy(how = How.XPATH, using = "//select[@id='lang4TelSelect']/option[@value='fr']")
	public WebElement OrderSummary_emailLanguage_fr;
	@FindBy(how = How.XPATH, using = "//textarea[@name='toAddresses']")
	public WebElement Thankyou_email;
	@FindBy(how = How.XPATH, using = "//input[@name='sendEmail']")
	public WebElement Thankyou_sendButton;
	@FindBy(how = How.XPATH, using = "//div[@class='manual-email-msg']")
	public WebElement Thankyou_sendMessage;

	// My account page
	@FindBy(how = How.XPATH, using = "//div[@id='accountBody']//a[contains(@href,'activateASM')]")
	public WebElement myAccountPage_startAssistedServiceSession;

	// Assisted Service Mode
	@FindBy(how = How.XPATH, using = "//input[@id='customerFilter']")
	public WebElement assistedServiceMode_customerID;
	@FindBy(how = How.CSS, using = "[id^='ui-id-']>a")
	public WebElement assistedServiceMode_customerResult;
	@FindBy(how = How.XPATH, using = "//form[@id='_asmPersonifyForm']/fieldset/button")
	public WebElement assistedServiceMode_startSession;
	@FindBy(how = How.XPATH, using = "//button[@id='copy-transaction']")
	public WebElement assistedServiceMode_copyTransaction;
	@FindBy(how = How.XPATH, using = "//div[@id='cboxLoadedContent']//select[@name='advTransactionCopyType']")
	public WebElement assistedServiceMode_transactionType;
	@FindBy(how = How.XPATH, using = "//div[@id='cboxLoadedContent']//input[@name='advTransactionCopyId']")
	public WebElement assistedServiceMode_transactionID;
	@FindBy(how = How.XPATH, using = "//button[@id='startAdvTransactionCopySearch']")
	public WebElement assistedServiceMode_copyIt;

	// 21875
	@FindBy(how = How.XPATH, using = "//div[@id='bodywrapinner']//input[@name='quantity']")
	public WebElement cartInfo_cartQuantity;
	@FindBy(how = How.XPATH, using = "//div[@id='bodywrapinner']//dl[contains(@class,'finalPrice-amount')]/span")
	public WebElement cartInfo_price;
	@FindBy(how = How.XPATH, using = "//div[@id='bodywrapinner']/div[3]/p")
	public WebElement cartInfo_subTotalPriceBefoEdit;
	@FindBy(how = How.XPATH, using = "//button[@id='editCartButton']")
	public WebElement cartInfo_editCart;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//input[@name='initialQuantity']")
	public WebElement cartInfo_cartQuantityAfterEdit;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//dt[@class='cartDetails-tsPrice']")
	public List<WebElement> cartInfo_toatlPriceArterEdit;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//span[contains(@class,'subTotalWithoutCoupon')]")
	public WebElement cartInfo_subTotalAftAnnProd;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//dd[contains(@class,'checkout_subtotal')]")
	public WebElement checkout_subTotalPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//dl[contains(@class,'pricingTotal')]/dd[1]")
	public WebElement cartInfo_discountPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//dl[contains(@class,'pricingTotal')]/dd[1]/span")
	public WebElement cartInfo_totalDiscounts;

	// ---Home Page Pop Up----
	@FindBy(how = How.XPATH, using = "//div/button[@title='Close (Esc)']")
	public WebElement popUp_closeHomePagePopUp;
	@FindBy(how = How.XPATH, using = "//div/button[contains(.,'X')]")
	public WebElement popUp_closeLeftSidePopUp;
	@FindBy(how = How.XPATH, using = ".//*[@id='full-sc']/a")
	public WebElement popUp_SlidePopUp;

	@FindBy(how = How.XPATH, using = "//a[@id='showCustomerSearch']")
	public WebElement Tele_CustomerSearch;
	@FindBy(how = How.XPATH, using = "//label[text()='Customer ID']/../input[@name='id']")
	public WebElement Tele_CustomerSearch_customerID;
	@FindBy(how = How.XPATH, using = "//button[@id='advancedCustomerSearch']")
	public WebElement Tele_CustomerSearch_Search;
	@FindBy(how = How.XPATH, using = "//tr[@class='b2cCustomer']")
	public WebElement Tele_CustomerSearch_SearchResult;

	@FindBy(how = How.XPATH, using = "//button[@id='asmDplReportButton']")
	public WebElement Tele_DPLReport;
	@FindBy(how = How.XPATH, using = "//input[@name='code']")
	public WebElement Tele_DPLReport_ID;
	@FindBy(how = How.XPATH, using = "//input[@name='store']")
	public WebElement Tele_DPLReport_StoreID;
	@FindBy(how = How.XPATH, using = "//button[contains(@onclick,'dplReportSearch()')]")
	public WebElement Tele_DPLReport_Search;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'glyphicon-remove')]")
	public WebElement Tele_DPLReport_Reject;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'glyphicon-ok')]")
	public WebElement Tele_DPLReport_Approve;
	@FindBy(how = How.XPATH, using = "//button[@id='cboxClose']")
	public WebElement Tele_DPLReport_Close;
	@FindBy(how = How.XPATH, using = "//span[@id='advancedTransactionSearchIcon']")
	public WebElement Tele_TransactionSearch;
	@FindBy(how = How.XPATH, using = "//input[@name='advTransactionId']")
	public WebElement Tele_TransactionSearch_TransactionId;
	@FindBy(how = How.XPATH, using = "//button[@id='startAdvTransactionSearch']")
	public WebElement Tele_TransactionSearch_Search;
	@FindBy(how = How.XPATH, using = "//tbody[@id='advTransactionSearchTable']/tr")
	public WebElement Tele_TransactionSearch_SearchResult;
	// 16390
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Start Assisted Service Session') or contains(text(),'サポート付きのサービスセッションを開始') or contains(text(),'지원 서비스 세션 시작') or contains(text(),'開始協助服務工作階段') or contains(text(),'Iniciar sesión de servicio asistido') or contains(text(),'Início de Sessão de Serviço Assistido')]")
	public WebElement StartASM;
	@FindBy(how = How.XPATH, using = ".//*[@id='customerFilter']")
	public WebElement ASM_SearchCustomer;
	@FindBy(how = How.CSS, using = "[id^='ui-id-']>a")
	public WebElement ASM_CustomerResult;
	@FindBy(how = How.XPATH, using = ".//*[@id='_asmPersonifyForm']/fieldset/button")
	public WebElement ASM_StartSession;
	@FindBy(how = How.XPATH, using = ".//*[@id='asmLoginForm']/fieldset/div[2]/input")
	public WebElement ASM_password;
	@FindBy(how = How.XPATH, using = ".//*[@id='nemoLoginForm']/button")
	public WebElement ASM_signin;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'menu-id=Accessories') or contains(@href,'accessories-and-upgrade') or @id='Accesorios']")
	public WebElement AccesoryCategory;
	@FindBy(how = How.XPATH, using = ".//*[@id='productGrid-target']//div[2]/div/div[2]/h2/a")
	public WebElement FirstAccessoryType;
	@FindBy(how = How.XPATH, using = "//span[@class='accessoriesCategoriesTitle searchBoxLables']")
	public WebElement browseAllCategories;
	@FindBy(how = How.XPATH, using = ".//*[@id='KeyboardsMice']/ul/li[1]/a")
	public WebElement keyBoardandMice;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//ol/li[3]//div[4]/a")
	public WebElement FirstAccessoryProduct;
	@FindBy(how = How.XPATH, using = "//button[contains(@id,'addToCart')]")
	public WebElement Add2Cart;
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Add to cart') or contains(text(),'Agregar al carrito')])")
	public WebElement PopUpAdd2Cart;
	@FindBy(how = How.XPATH, using = "(//a[contains(text(),'Go to Cart') or contains(text(),'Ir al carrito')])")
	public WebElement Go2Cart;
	@FindBy(how = How.XPATH, using = "//div[1]/div[2]/div[2]/div[1]/div/div[1]/button[1]")
	public WebElement NAPopUpAdd2Cart;
	@FindBy(how = How.XPATH, using = "//div[1]/div[2]/div[2]/div[1]/div/div[1]/a")
	public WebElement NAGo2Cart;
	@FindBy(how = How.XPATH, using = "(//td[contains(text(),'Line total') or contains(text(),'合計')]/../td)[3]")
	public WebElement SalesPrice;
	@FindBy(how = How.XPATH, using = "//input[contains(@name,'price')]")
	public WebElement OverrideValue;
	@FindBy(how = How.XPATH, using = ".//*[@id='reasonCode0']/option[2]")
	public WebElement OverrideDropdown;
	@FindBy(how = How.XPATH, using = ".//*[@id='reasonText']")
	public WebElement OverrideCheckbox;
	@FindBy(how = How.XPATH, using = ".//*[@id='updatePriceForm0']//input[@type='submit']")
	public WebElement OverrideUpdate;
	@FindBy(how = How.XPATH, using = ".//*[@id='quote_button'][not(contains(@class,'quote_button_disable'))]")
	public WebElement Override_RequestQuote;
	@FindBy(how = How.XPATH, using = ".//*[@id='submit-quote-button']")
	public WebElement Override_SubmitQuote;
	@FindBy(how = How.XPATH, using = "//input[@id='repId']")
	public WebElement Override_RepID;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']/div[1]//div[1]/table/tbody/tr[2]/td[2]")
	public WebElement Override_QuoteNumber;
	@FindBy(how = How.XPATH, using = ".//*[@id='asmLogoutForm']/fieldset/button")
	public WebElement SignoutASM;
	@FindBy(how = How.XPATH, using = ".//*[@id='stopEmulate']")
	public WebElement Tele_Endsession;

	@FindBy(how = How.XPATH, using = ".//*[@id='_asmPersonifyForm']/fieldset/div[3]/input")
	public WebElement TransactionID;
	@FindBy(how = How.CSS, using = "[id^='Q']>a")
	public WebElement ASM_QuoteResult;
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Approve/Reject') or contains(text(),'承認/却下') or contains(text(),'核准/拒絕') or contains(text(),'승인/거부')]")
	public WebElement ASM_ApproveOrReject;

	@FindBy(how = How.XPATH, using = "//button[@id='quoteStatusChange']")
	public WebElement ASM_ApproveButton;
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Reject')]")
	public WebElement ASM_RejectButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='quote-comment-textarea-id']")
	public WebElement ASM_ApproveComment;
	@FindBy(how = How.XPATH, using = ".//*[@id='tele-quote-approve-btn']")
	public WebElement ASM_PopupApprove;
	@FindBy(how = How.XPATH, using = ".//*[@id='tele-quote-reject-btn']")
	public WebElement ASM_PopupReject;
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Convert To Order') or contains(text(),'注文に切り替える') or contains(text(),'轉換至訂單') or contains(text(),'주문으로 변환')]")
	public WebElement ASM_ConvertToOrder;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Edit') or contains(text(),'編集') or contains(text(),'更改') or contains(text(),'수정') or contains(text(),'編輯')]")
	public WebElement ASM_EditAddress;
	@FindBy(how = How.XPATH, using = "//input[@id='townCity' or @id='district']")
	public WebElement ASM_City;
	@FindBy(how = How.XPATH, using = "//input[@name='address']")
	public WebElement Shipping_AddressOptionsList;
	@FindBy(how = How.XPATH, using = ".//*[@id='checkout_validateFrom_ok']")
	public WebElement Shipping_SuggestedAddress;
	@FindBy(how = How.ID, using = "creditcardiframe0")
	public WebElement Payment_CreditCardFrame;
	@FindBy(how = How.XPATH, using = ".//select[@id='invoiceTypeTW']/option[@value='PaperInvoice']")
	public WebElement Payment_invoiceType;
	@FindBy(how = How.XPATH, using = "//input[@id='carrierCodeTW']")
	public WebElement Payment_invoiceCode;
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Place Order') or contains(text(),'注文を確定する') or contains(text(),'完成訂單') or contains(text(),'결제 계속하기')]")
	public WebElement Payment_PlaceOrder;
	// 21875
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//div[contains(@class,'price-calculator')]/dl")
	public WebElement Cart_price;

	// 20149
	@FindBy(how = How.XPATH, using = "//input[@id='consumerTaxNumber']")
	public WebElement Shipping_taxNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_Wire']")
	public WebElement Payment_WireButton;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-shipping')]//span[@class='vcard-fn']")
	public WebElement Payment_verifyShippingName;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-shipping')]//div[1][contains(@class,'address')]")
	public WebElement Payment_verifyShippingAddress;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-shipping')]//span[contains(@class,'locality')]")
	public WebElement Payment_verifyShippingLocality;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-shipping')]//span[contains(@class,'region')]")
	public WebElement Payment_verifyShippingRegion;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-shipping')]//div[1][contains(@class,'phone')]")
	public WebElement Payment_verifyShippingPhone;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-shipping')]//a[contains(@class,'email')]")
	public WebElement Payment_verifyShippingEmail;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-shipping')]//span[contains(@class,'cardHoldName')]")
	public WebElement Payment_verifyShippingCardHoldName;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-billing')]//span[@class='vcard-fn']")
	public WebElement Payment_verifyPaymentName;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-billing')]//div[1][contains(@class,'address')]")
	public WebElement Payment_verifyPaymentAddress;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-billing')]//span[contains(@class,'locality')]")
	public WebElement Payment_verifyPaymentLocality;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-billing')]//span[contains(@class,'region')]")
	public WebElement Payment_verifyPaymentRegion;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-billing')]//div[1][contains(@class,'phone')]")
	public WebElement Payment_verifyPaymentPhone;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-billing')]//a[contains(@class,'email')]")
	public WebElement Payment_verifyPaymentEmail;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-review-billing')]//span[contains(@class,'cardHoldName')]")
	public WebElement Payment_verifyPaymentardHoldName;
	@FindBy(how = How.XPATH, using = "//dl[contains(@class,'checkout-review-payment-details')]//dd[1]")
	public WebElement Payment_verifyPaymentType;
	@FindBy(how = How.XPATH, using = "//dd[contains(@class,'checkout-orderSummaryReview-totals-totalValue')]")
	public WebElement OrderSummary_totalPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//div[@class='checkout-confirm-message']/h2")
	public WebElement OrderThankyou_confirmMessage;

	// Digital River
	@FindBy(how = How.XPATH, using = "(.//*[@id='addToCartButtonTop'])[1]")
	public WebElement DR_AddtoBasket;
	@FindBy(how = How.XPATH, using = ".//*[@id='email']")
	public WebElement DR_Email;
	@FindBy(how = How.XPATH, using = ".//*[@id='billingName1']")
	public WebElement DR_FirstName;
	@FindBy(how = How.XPATH, using = ".//*[@id='billingName2']")
	public WebElement DR_LastName;
	@FindBy(how = How.XPATH, using = ".//*[@id='billingAddress1']")
	public WebElement DR_AddressLine1;
	@FindBy(how = How.XPATH, using = ".//*[@id='billingPostalCode']")
	public WebElement DR_PostCode;
	@FindBy(how = How.XPATH, using = ".//*[@id='billingCity']")
	public WebElement DR_City;
	@FindBy(how = How.XPATH, using = ".//*[@id='billingCountry']/option[contains(text(),'Ireland')]")
	public WebElement DR_Country;
	@FindBy(how = How.XPATH, using = ".//*[@id='billingPhoneNumber']")
	public WebElement DR_PhoneNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='CreditCardMethod']")
	public WebElement DR_CreditCard;
	@FindBy(how = How.XPATH, using = ".//*[@id='ccNum']")
	public WebElement DR_CreditCardNumber;
	@FindBy(how = How.XPATH, using = ".//*[@id='ccMonth']/option[contains(text(),'January')]")
	public WebElement DR_CardExpirationMonth;
	@FindBy(how = How.XPATH, using = ".//*[@id='ccYear']/option[contains(text(),'2020')]")
	public WebElement DR_CardExpirationYear;
	@FindBy(how = How.XPATH, using = ".//*[@id='cardSecurityCode']")
	public WebElement DR_SecurityCode;
	@FindBy(how = How.XPATH, using = ".//*[@id='checkoutButton']")
	public WebElement DR_Continue;
	@FindBy(how = How.XPATH, using = ".//*[@id='vr_skipregistration']")
	public WebElement DR_SkipRegistration;
	@FindBy(how = How.XPATH, using = ".//*[@id='tosAccepted']")
	public WebElement DR_AgreetoTerms;
	@FindBy(how = How.XPATH, using = ".//*[@id='submitBottom']")
	public WebElement DR_BuyNow;
	@FindBy(how = How.XPATH, using = "//span[@id='dr_orderNumber']")
	public WebElement DR_OrderNumber;

	// 18406
	@FindBy(how = How.ID, using = "create-one-time-quote")
	public WebElement Quote_createOneTimeQuote;
	@FindBy(how = How.ID, using = "contactEmail")
	public WebElement Quote_contactEmail;
	@FindBy(how = How.ID, using = "editButton")
	public WebElement Quote_editButton;
	@FindBy(how = How.ID, using = "quantity0")
	public WebElement Quote_quantity0;
	@FindBy(how = How.ID, using = "QuantityProduct_0")
	public WebElement Quote_update;
	@FindBy(how = How.ID, using = "quantity3")
	public WebElement Quote_quantity3;
	@FindBy(how = How.ID, using = "stopEmulate")
	public WebElement ASM_endSession;

	// DR18
	@FindBy(how = How.XPATH, using = "//input[@id='email']")
	public WebElement DR_emailTxtBox;
	@FindBy(how = How.XPATH, using = "//input[@id='billingName1']")
	public WebElement DR_fNameTxtBox;
	@FindBy(how = How.XPATH, using = "//*[@id='billingName2']")
	public WebElement DR_lNameTxtBox;
	@FindBy(how = How.XPATH, using = "//input[@id='billingAddress1']")
	public WebElement DR_addLine1TxtBox;
	@FindBy(how = How.XPATH, using = "//input[@id='billingCity']")
	public WebElement DR_cityTxtBox;
	@FindBy(how = How.XPATH, using = "//select[@id='billingCountry']")
	public WebElement DR_countryDD;
	@FindBy(how = How.XPATH, using = "//*[@id='billingCountry']/option[contains(.,'United Kingdom')]")
	public WebElement DR_ukOption;
	@FindBy(how = How.XPATH, using = "//*[@id='billingCountry']/option[contains(.,'France')]")
	public WebElement DR_frOption;
	@FindBy(how = How.XPATH, using = "//input[@id='billingPhoneNumber']")
	public WebElement DR_phoneNumTxtBox;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'CreditCardMethod')]")
	public WebElement DR_ccChkBox;
	@FindBy(how = How.XPATH, using = "//input[@id='ccNum']")
	public WebElement DR_ccNumberTxtBox;
	@FindBy(how = How.XPATH, using = "//select[@id='ccMonth']")
	public WebElement DR_monthDD;
	@FindBy(how = How.XPATH, using = "//select[@id='ccYear']")
	public WebElement DR_yearDD;
	@FindBy(how = How.XPATH, using = "//option[@value='6']")
	public WebElement DR_selectedMonthOption;
	@FindBy(how = How.XPATH, using = "//option[@value='2018']")
	public WebElement DR_selectedYearOption;
	@FindBy(how = How.XPATH, using = "//input[@id='cardSecurityCode']")
	public WebElement DR_securityCodeTxtBox;
	@FindBy(how = How.XPATH, using = "//input[@id='checkoutButton']")
	public WebElement DR_continueBtn;
	@FindBy(how = How.XPATH, using = "//input[@id='tosAccepted']")
	public WebElement DR_tncChkBox;
	@FindBy(how = How.XPATH, using = "//input[@id='submitBottom']")
	public WebElement DR_submitBtn;
	@FindBy(how = How.XPATH, using = "//span[@id='dr_orderNumber']")
	public WebElement DR_orderNum;
	@FindBy(how = How.XPATH, using = "//input[@id='couponCode']")
	public WebElement couponCode;
	@FindBy(how = How.XPATH, using = "//input[@id='cart-summary-ecouponForm-button']")
	public WebElement couponApply;
	@FindBy(how = How.XPATH, using = "//dl[contains(@class,'webPrice')]")
	public WebElement afterEcouponPrice;
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'ctoYourPrice')]")
	public WebElement ctoPrice;
	@FindBy(how = How.XPATH, using = ".//*[contains(@class,'price-calculator')]")
	public WebElement cartPrices;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'qa_finalPrice')]")
	public WebElement IE_ctoPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='w-price']")
	public WebElement cto_WebPrice;
	@FindBy(how = How.XPATH, using = "//div[@class='qa_finalPrice']")
	public WebElement cto_AfterSavingPrice;
	@FindBy(how = How.XPATH, using = "//*[not(contains(@class,'hidden'))]/*[contains(@class,'you-save') and not(contains(@class,'hidden'))]/div[2]")
	public WebElement cto_YouSavePrice;
	@FindBy(how = How.XPATH, using = "//button[contains(@class, 'add-to-cart')]")
	public WebElement cto_AddToCartButton;

	@FindBy(how = How.XPATH, using = "//dd[contains(@class,'pricingSummary-priceList-value')]/span[@class='summary-webPrice']")
	public WebElement pb_WebPrice;
	// @FindBy(how = How.XPATH, using =
	// "//dd[contains(@class,'final-price')]/span[@class='summary-price']")
	@FindBy(how = How.XPATH, using = "//span[@class='summary-price']")
	public WebElement pb_AfterSavingPrice;
	@FindBy(how = How.XPATH, using = "//dd[contains(@class,'pricingSummary-priceList-value')]/span[@class='summary-price']")
	public WebElement pb_YouSavePrice;

	@FindBy(how = How.XPATH, using = "//div[starts-with(@class,'promotion-price')]/div[2]")
	public WebElement newCTO_YourPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'base-price-wrapper')]/div[2]")
	public WebElement newCTO_BasePrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'you-save')]/div[2]")
	public WebElement newCTO_SavePrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'qa_finalPrice')]")
	public WebElement newPB_YourPrice;
	@FindBy(how = How.XPATH, using = "//a[@id='CTO_expandCollapse']")
	public WebElement newCTO_ExpandAll;
	@FindBy(how = How.XPATH, using = ".//*[@id='Warranty']//button[contains(@class,'group-expand')]")
	public WebElement newCTO_ExpandWarranty;
	@FindBy(how = How.XPATH, using = ".//button[contains(@class,'qa-configurator-sectionExpandButton')]")
	public WebElement newCTO_StackableWarrantyChangeButton;

	// 22046
	@FindBy(how = How.XPATH, using = "//select[contains(@id,'invoiceTypeTW')]")
	public WebElement Payment_selectInvoice;
	@FindBy(how = How.XPATH, using = "//div[@id='error_payment.set.paymentDetail.needInvoiceTypeTW']/p")
	public WebElement Payment_invoiceErrorMessage;
	@FindBy(how = How.XPATH, using = "//input[@id='donationCodeTW']")
	public WebElement Payment_invoiceDonationCode;

	// 21912
	@FindBy(how = How.ID, using = "PaymentTypeSelection_IGF")
	public WebElement payment_IGF;
	@FindBy(how = How.ID, using = "savedcart_button_new_summary")
	public WebElement checkout_saveCart;
	@FindBy(how = How.XPATH, using = "//input[@name='advTransactionName']")
	public WebElement Tele_TransactionSearch_TransactionName;
	// 16353
	@FindBy(how = How.XPATH, using = "(//input[@onchange='menuItemClick(this)'])[2]")
	public WebElement Payment_FacetOption;
	@FindBy(how = How.XPATH, using = "(.//*[@id='resultsList']//div[4]/a)[1]")
	public WebElement Payment_FirstViewAfterFacet;
	@FindBy(how = How.XPATH, using = ".//*[@id='view-customize' or @id='addToCartButtonTop' or contains(text(),'添加到購物車')]")
	public WebElement Payment_ProductExists;
	@FindBy(how = How.XPATH, using = "//a[@class='bar_1-logo-link']")
	public WebElement Payment_LenovoBanner;
	@FindBy(how = How.XPATH, using = "//a[@class='close-sc']")
	public WebElement Payment_DontMissOut;
	@FindBy(how = How.XPATH, using = "//a[@class='cartlink has-submenu']")
	public WebElement Payment_CartIcon;
	@FindBy(how = How.XPATH, using = ".//*[@id='rollovercartViewCart']")
	public WebElement Payment_ViewCartButton;
	@FindBy(how = How.XPATH, using = "//input[@id='quickOrderProductId']")
	public WebElement Payment_QuickAddBox;
	@FindBy(how = How.XPATH, using = "//input[@value='expedite-shipping-gross']")
	public WebElement Payment_ExpeditedGrossShipping;
	@FindBy(how = How.XPATH, using = "//input[@value='expedite-shipping-net']")
	public WebElement Payment_ExpeditedNetShipping;
	@FindBy(how = How.XPATH, using = "//input[@value='CREDIT_CARD']")
	public WebElement Payment_CreditCard;
	@FindBy(how = How.XPATH, using = "//input[@id='card_amount1']")
	public WebElement FirstCardAmount;
	@FindBy(how = How.XPATH, using = ".//*[@id='iframe1_info']/div[1]/label")
	public WebElement PaymentIndicator;
	@FindBy(how = How.ID, using = "creditcardiframe1")
	public WebElement FirstCardIframe;
	@FindBy(how = How.ID, using = "creditcardiframe2")
	public WebElement SecondCardIframe;
	@FindBy(how = How.XPATH, using = ".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]")
	public WebElement TwoCardsType;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cardnumber']")
	public WebElement TwoCardsNumber;
	@FindBy(how = How.XPATH, using = "//*[@id='c-exmth']")
	public WebElement TwocardsMonth;
	@FindBy(how = How.XPATH, using = "//*[@id='c-exyr']")
	public WebElement TwocardsYear;
	@FindBy(how = How.XPATH, using = "//input[@id='c-cvv']")
	public WebElement TwocardsCV;
	@FindBy(how = How.XPATH, using = "//input[@id='card_nameOnCard1']")
	public WebElement TwocardsNameOnCard1;
	@FindBy(how = How.XPATH, using = "//input[@id='card_nameOnCard2']")
	public WebElement TwocardsNameOnCard2;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_PAYPAL']")
	public WebElement PaypalButton;
	@FindBy(how = How.XPATH, using = "//input[@id='PaymentTypeSelection_LENOVOCARD']")
	public WebElement LenovoCard;
	@FindBy(how = How.XPATH, using = "//input[@id='lenovoCardNumber-masked']")
	public WebElement LenovoCardNumber;
	@FindBy(how = How.XPATH, using = "//input[@id='consumerFinancingPaymentTerm_CFRV']")
	public WebElement LenovoCardResolving;
	@FindBy(how = How.XPATH, using = "//input[@id='lenovoCardHolder']")
	public WebElement LenovoCardHolderName;
	@FindBy(how = How.XPATH, using = "//input[@value='CHECK']")
	public WebElement PayByCheck;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderNumber']")
	public WebElement purchaseNum;
	@FindBy(how = How.XPATH, using = "//input[@id='purchase_orderDate']")
	public WebElement purchaseDate;
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
	@FindBy(how = How.XPATH, using = "(//td[contains(text(),'Order number') or contains(text(),'ご注文番号')]/../td)[2]")
	public WebElement orderNumber;
	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'Laptops & Ultrabooks')])[1]")
	public WebElement LaptopsUnderProduct;
	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'Laptops & Ultrabooks')])[2]")
	public WebElement ExploreAllLaptops;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Explore All Laptops & Ultrabooks')]")
	public WebElement ExploreAllLaptopsLink;
	@FindBy(how = How.XPATH, using = ".//*[@id='cartIcon']/a")
	public WebElement Payment_NewCart;
	// the following is for meta data
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'search_menu')]/a/span")
	public WebElement HomePage_SearchIcon;
	@FindBy(how = How.XPATH, using = "//div[@class='searchContainer']/form/input[1]")
	public WebElement HomePage_SearchTextArea;
	@FindBy(how = How.XPATH, using = "//ul[@class='menu prd_Menu']/li[contains(@class,'product')]/a/span")
	public WebElement Products_Link;
	@FindBy(how = How.XPATH, using = "//*[@id='extraOptions-chooseCategroy']/../div/button")
	public WebElement selectACategory;
	@FindBy(how = How.XPATH, using = "//li/a[@class='products_submenu' and contains(@style,'laptops')]")
	public WebElement laptops_subProduct;
	@FindBy(how = How.XPATH, using = "//div[@id='categoryWrapper']/div[contains(@id,'tab-content-thinkpad') or contains(@id,'tab-content-professional')]/article/div/h1/a")
	public WebElement thinkpad_brand;
	@FindBy(how = How.XPATH, using = "(//*[@id='product-']/div/div[contains(.,'X')]/../div[6]/a)[1]")
	public WebElement thinkpadX_series;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']/div/ol/li/div/div[contains(@class,'seriesListings-footer')]/a")
	public WebElement thinkpad_subSeries;
	@FindBy(how = How.XPATH, using = "//li/a[@class='products_submenu' and contains(@href,'ACCESSORY')]")
	public WebElement accessories_Link;
	@FindBy(how = How.XPATH, using = "//*[@id='view-customize']")
	public WebElement viewOrCustomize;
	@FindBy(how = How.XPATH, using = "(//div[@class='tabbedBrowse-productListing-footer-button-holder']/button[@id='addToCartButtonTop'])[1]")
	public WebElement customizeButton;
	@FindBy(how = How.XPATH, using = "//*[@id='render-summary']//button[contains(@class,'add-to-cart')]")
	public WebElement addTocart_configPage;
	@FindBy(how = How.XPATH, using = "//a[contains(@id,'lenovo-checkout')]")
	public WebElement lenovo_checkout;
	@FindBy(how = How.XPATH, using = "//*[@id='guestForm']/button")
	public WebElement start_checkout;
	@FindBy(how = How.XPATH, using = "//a[@class='products_submenu' and contains(@href,'Servers') or contains(@href,'server')]")
	public WebElement servers_storage_networking_link;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'my-account') and @class='has-submenu']")
	public WebElement myAccount_link;
	@FindBy(how = How.XPATH, using = "//*[@id='addressForm']/fieldset[1]/legend/a")
	public WebElement shippingEdit;

	// the following is for pageCheck
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'deals_menu')]/a/span")
	public WebElement Deals_Link;
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'support_menu')]/a/span")
	public WebElement Support_Link;

	@FindBy(how = How.XPATH, using = "//input[@id='repId' and not(@readonly)]")
	public WebElement OrderSummary_editableRepID;
	@FindBy(how = How.XPATH, using = "//*[@id='mainContent']//a[contains(@class,'createAccount')]")
	public WebElement createAccount;

	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_CPU']/tr[3]")
	public WebElement CPU_EnableOne;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_CPU']/tr[3]//input")
	public WebElement CPUCheckBox_EnableOne;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_CPU']/tr[4]")
	public WebElement CPU_EnableTwo;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_CPU']/tr[4]//label")
	public WebElement CPU_EnableTwo_Label;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_MEM']/tr[1]")
	public WebElement Memory_DisableOne;

	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_CPU']/div[3]")
	public WebElement CPU_EnableOne_New;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_CPU']/div[4]")
	public WebElement CPU_EnableTwo_New;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_NB_MEM']/div[1]")
	public WebElement Memory_DisableOne_New;

	// added by na-23037
	@FindBy(how = How.XPATH, using = ".//*[@id='builderPricingSummary']/dd[1]")
	public WebElement Product_WebPrice;
	@FindBy(how = How.XPATH, using = "//button[text()='CHANGE']")
	public WebElement Product_Change;
	@FindBy(how = How.XPATH, using = "//div[text()='Item Discount']/../div[2]")
	public WebElement Product_Discount;

	// NA-17702
	@FindBy(how = How.XPATH, using = ".//li//a[contains(@href,'emailProduct')]")
	public WebElement subseries_emailProductLink;
	@FindBy(how = How.XPATH, using = ".//li//a[contains(@href,'emailProduct')]")
	public List<WebElement> subseries_emailProductLink1;
	@FindBy(how = How.XPATH, using = ".//*[@id='senderName']")
	public WebElement emailProduct_yourFirstNameTxtBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='senderAddress']")
	public WebElement emailProduct_sendToTxtBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='baseButtonNoBg']")
	public WebElement emailProduct_sendToEmailButton;
	@FindBy(how = How.XPATH, using = ".//div[@class='emailHead']/h3")
	public WebElement emailProduct_emailSuccessfullySentMsgBar;
	@FindBy(how = How.XPATH, using = "(.//div[contains(@id,'tab-content')]//h1/a)[1]")
	public WebElement laptops_firstLaptopType;
	@FindBy(how = How.XPATH, using = "(.//h3[contains(@class,'brandListings-title')]/a)[1]")
	public WebElement laptopSeries_firstSeriesType;
	@FindBy(how = How.XPATH, using = ".//ol[@class='seriesListings']/li[contains(@class,seriesListings)]")
	public List<WebElement> laptopSeries_subseriesCount;
	@FindBy(how = How.XPATH, using = ".//*[@class='cd-products-columns']/*[contains(@class,'product')]")
	public List<WebElement> laptopSeries_subseriesCount1;
	
	// NA-18081
	@FindBy(how = How.XPATH, using = "//div[@class='signInModule-content']//a[contains(@href,'profile')]")
	public WebElement myAccount_updatePersonalDetails;
	@FindBy(how = How.XPATH, using = ".//a[contains(@href,'update-profile')]")
	public WebElement updateProfile_updatePersonalDetails;
	// Update personal details page
	@FindBy(how = How.XPATH, using = ".//*[@id='profile.title']")
	public WebElement updateProfile_profileTitleDD;
	@FindBy(how = How.XPATH, using = ".//*[@id='profile.title']/option[contains(@value,'mrs')]")
	public WebElement updateProfile_mrsOption;
	@FindBy(how = How.XPATH, using = ".//select[@id='profile.title']")
	public WebElement updateProfile_Title;
	@FindBy(how = How.XPATH, using = ".//*[@id='profile.firstName']")
	public WebElement updateProfile_fName;
	@FindBy(how = How.XPATH, using = ".//*[@id='profile.lastName']")
	public WebElement updateProfile_lName;
	@FindBy(how = How.XPATH, using = ".//*[@id='profile.optin']")
	public WebElement updateProfile_recieveFreeEmailChkBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='nemoUpdateProfileForm']//button[@type='submit']")
	public WebElement updateProfile_saveUpdatesButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='marketingAgreement']")
	public WebElement lenovoShipping_marketAgreementChkBox;
	@FindBy(how = How.XPATH, using = ".//*[@for='marketingAgreement']")
	public WebElement lenovoShipping_marketAgreementChkBox_New;
	@FindBy(how = How.XPATH, using = ".//*[@class='account-profile-data']")
	public WebElement updateProfile_profileInfoTable;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'smbcompany-dashboard')]")
	public WebElement myAccount_updateCompanyInfoNav;
	@FindBy(how = How.XPATH, using = ".//a[contains(@href,'smbCompanyProfile')]")
	public WebElement updateProfile_updateCompanyInfoButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='companyAddress1']")
	public WebElement updateCompanyProfile_Address1;
	@FindBy(how = How.XPATH, using = ".//*[@id='companyAddress2']")
	public WebElement updateCompanyProfile_Address2;
	@FindBy(how = How.XPATH, using = ".//*[@id='city']")
	public WebElement updateCompanyProfile_City;
	@FindBy(how = How.XPATH, using = ".//*[@id='zipCode']")
	public WebElement updateCompanyProfile_ZipCode;
	@FindBy(how = How.XPATH, using = ".//*[@id='companyYear']")
	public WebElement updateCompanyProfile_CompanyYear;
	@FindBy(how = How.XPATH, using = ".//*[@id='state']")
	public WebElement updateCompanyProfile_State;
	@FindBy(how = How.XPATH, using = ".//*[@id='industry']")
	public WebElement updateCompanyProfile_Industry;
	@FindBy(how = How.XPATH, using = ".//*[@id='department']")
	public WebElement updateCompanyProfile_Department;
	@FindBy(how = How.XPATH, using = ".//*[@id='companySize']")
	public WebElement updateCompanyProfile_CompanySize;
	@FindBy(how = How.XPATH, using = ".//div[@class='form-formGroup']/button[@class='positive']")
	public WebElement updateCompanyProfile_SaveUpdateButton;
	@FindBy(how = How.XPATH, using = ".//div[@class='field-table']")
	public WebElement updateProfile_CompanyInfoTable;
	@FindBy(how = How.XPATH, using = ".//div[@class='current_price_tier_level']")
	public WebElement SMB_CurrentPriceLevel;
	@FindBy(how = How.XPATH, using = ".//div[@class='current_price_tier_saving']")
	public WebElement SMB_CurrentPriceSaving;
	@FindBy(how = How.XPATH, using = ".//div[@class='price_tier_image']")
	public WebElement SMB_CurrentPriceLevelArrow;

	// Accessory Page
	@FindBy(how = How.XPATH, using = "//button[@data-id='extraOptions-chooseCategroy']")
	public WebElement accessory_SelectACategory;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Cables & Adapters') or contains(.,'ケーブル/アダプター')]")
	public WebElement accessory_CableOption;
	@FindBy(how = How.XPATH, using = "(//div[@class='pricingSummary']//dd)[1]")
	public WebElement accessory_CablePrice;
	@FindBy(how = How.XPATH, using = "//li[contains(.,'Your cart value has exceeded $100.00')]")
	public WebElement Promotion_Message;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'totalPriceWithTax')]")
	public WebElement CartPage_TotalPriceOnCart;
	@FindBy(how = How.XPATH, using = "(//input[@name='quantity'])[last()]")
	public WebElement CartPage_QuantityInputBox;
	@FindBy(how = How.XPATH, using = "(//input[contains(@class,'updateQuantity')])[last()]")
	public WebElement CartPage_QuantityUpdate;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'activateASM')]")
	public WebElement AssistedServiceLink;
	@FindBy(how = How.XPATH, using = "//input[@id='customerFilter']")
	public WebElement CustomerFilterbox;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'start-session')]")
	public WebElement StartSessionButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='quote_button']")
	public WebElement RequestQuoteBtn;
	@FindBy(how = How.XPATH, using = ".//*[@id='repId']")
	public WebElement Quote_RepID;
	@FindBy(how = How.XPATH, using = ".//*[@id='submit-quote-button']")
	public WebElement Quote_SubmitQuoteBtn;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']/div[1]/div/div[1]/table//tr[2]/td[2]")
	public WebElement QuoteConfirmPage_QuoteNo;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'checkout-confirm-orderNumbers')]/img")
	public WebElement CountrySeal;
	@FindBy(how = How.XPATH, using = ".//*[@id='window-close']")
	public WebElement Close_PopUpJP;
	@FindBy(how = How.XPATH, using = "//li/a[contains(@href,'ccessories')]/div")
	public WebElement Navigation_CommonAccessoryLink;
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Add to cart') or contains(text(),'Agregar al carrito')])[last()]")
	public WebElement PopUp_Add2Cart;
	@FindBy(how = How.XPATH, using = "(//a[contains(text(),'Go to Cart') or contains(text(),'Ir al carrito')])[last()]")
	public WebElement PopUp_Go2Cart;
	@FindBy(how = How.XPATH, using = "//div[@id='accErrorMsgs']/ul/li")
	public WebElement Login_TimeoutMesg;

	// JACCS Payment options NA-18253
	@FindBy(how = How.ID, using = "PaymentTypeSelection_JACCS")
	public WebElement Jaccs_PaymentMode;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_BOLETO")
	public WebElement Payment_Boleto;
	@FindBy(how = How.ID, using = "PaymentTypeSelection_CCAVENUE")
	public WebElement Payment_ccavenue;


	@FindBy(how = How.ID, using = "jaccsPost")
	public WebElement Jaccs_Post;

	@FindBy(how = How.ID, using = "TB_closeWindowButton6")
	public WebElement Jaccs_TB_closeWindowButton6;

	@FindBy(how = How.ID, using = "agree1")
	public WebElement Jaccs_agree1;

	@FindBy(how = How.ID, using = "agree2")
	public WebElement Jaccs_agree2;

	@FindBy(how = How.ID, using = "agree3")
	public WebElement Jaccs_agree3;

	@FindBy(how = How.XPATH, using = ".//*[@id='buttonNextEn']/a")
	public WebElement Jaccs_buttonNextEn;

	@FindBy(how = How.XPATH, using = ".//*[@id='RPAYMENTKBN_Kai_BunkatsuBHeiyouNashi']")
	public WebElement Jaccs_PaymentMethod;

	@FindBy(how = How.XPATH, using = ".//*[@id='SPAYMENTFREQ_EQUAL']")
	public WebElement Jaccs_dropdown;

	@FindBy(how = How.XPATH, using = ".//*[@id='SPAYMENTFREQ_EQUAL']/option[3]")
	public WebElement Jaccs_option;

	@FindBy(how = How.XPATH, using = ".//*[@id='content']/div[5]/div/a")
	public WebElement Jaccs_accept2;

	@FindBy(how = How.XPATH, using = ".//*[@id='lightboxForm']/div[2]/a[2]")
	public WebElement Jaccs_next;

	@FindBy(how = How.XPATH, using = ".//*[@id='content']/div[4]/div/a[2]")
	public WebElement Jaccs_next2;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_shimei_sei']")
	public WebElement Jaccs_firstName;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_shimei_mei']")
	public WebElement Jaccs_lastName;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_shimei_seiKana']")
	public WebElement Jaccs_namePhonetic;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_shimei_meiKana']")
	public WebElement Jaccs_surnamePhonetic;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_seibetsu_otoko']")
	public WebElement Jaccs_gender;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_seinengappi_nen']")
	public WebElement Jaccs_year;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_seinengappi_getsu']")
	public WebElement Jaccs_month;

	@FindBy(how = How.XPATH, using = ".//*[@id='honnninJoho_seinengappi_hi']")
	public WebElement Jaccs_day;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_yubinBango_icon']")
	public WebElement Jaccs_getDetails;

	@FindBy(how = How.XPATH, using = ".//*[@id='addresslink']")
	public WebElement Jaccs_addressDetails;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_banchiTatemono']")
	public WebElement Jaccs_buildingName;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_banchiTatemonoKana']")
	public WebElement Jaccs_buildingNamePhonetic;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_jitakuDenwaBango_1']")
	public WebElement Jaccs_phoneNo1;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_jitakuDenwaBango_2']")
	public WebElement Jaccs_phoneNo2;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_jitakuDenwaBango_3']")
	public WebElement Jaccs_phoneNo3;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_kyojuJokyo_jikoshoyu']")
	public WebElement Jaccs_ownOwnership;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_jutakuRon_ari']")
	public WebElement Jaccs_householdMortgage;

	@FindBy(how = How.XPATH, using = ".//*[@id='jitakuJusho_kyojuNensu']")
	public WebElement Jaccs_residenceLife;

	@FindBy(how = How.XPATH, using = ".//*[@id='shokugyoShunyu_shokugyoShunyu_shufu']")
	public WebElement Jaccs_houseWife;

	@FindBy(how = How.XPATH, using = "(.//*[@id='kazokuJouhou_setaiJoukyou_haiguusha'])[1]")
	public WebElement Jaccs_spouse;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiJoukyou_kodomo']")
	public WebElement Jaccs_childrenDetails;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiJoukyou_kodomo']/option[2]")
	public WebElement Jaccs_childrenDetailsOption;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiJoukyou_oyaKyoudai']")
	public WebElement Jaccs_familyDetails;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiJoukyou_oyaKyoudai']/option[3]")
	public WebElement Jaccs_familyDetailsOption;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiJoukyou_doukyoKazoku_ari']")
	public WebElement Jaccs_presenceOfFamily;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiNushiSeikeiIjisha_oya']")
	public WebElement Jaccs_headOfHouseHold;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiNushiKyojuuJouhou']")
	public WebElement Jaccs_livingSituation;

	@FindBy(how = How.XPATH, using = ".//*[@id='kazokuJouhou_setaiNushiKyojuuJouhou']/option[2]")
	public WebElement Jaccs_livingSituationOption;

	@FindBy(how = How.XPATH, using = ".//*[@id='content']/div[4]/div/a[2]")
	public WebElement Jaccs_next3;

	@FindBy(how = How.XPATH, using = ".//*[@id='content']/div[5]/div/a[2]")
	public WebElement Jaccs_next4;

	@FindBy(how = How.XPATH, using = ".//*[@id='thanks_message']/p[1]")
	public WebElement Jaccs_formCompeletionInfo;

	@FindBy(how = How.XPATH, using = ".//*[@id='thanks_message']/p[2]")
	public WebElement Jaccs_formCompeletionInfo2;

	@FindBy(how = How.XPATH, using = ".//*[@id='content']/div[4]/a")
	public WebElement Jaccs_submit;

	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//dl[@class='checkout-orderSummaryReview-subTotals']/dt[2]")
	public WebElement SubTotal;

	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//dl[@class='checkout-orderSummaryReview-subTotals']/dd[2]")
	public WebElement SubTotalPrice;

	@FindBy(how = How.XPATH, using = ".//*[@id='configarea_scroller']//div[@class='bar_3']//h1")
	public WebElement ThankyouMessage;

	// NA-18055
	@FindBy(how = How.XPATH, using = ".//*[@class='cart-actionLinks-email' or @class='cart-action-email']/a")
	public WebElement Cart_Emailcart;

	@FindBy(how = How.XPATH, using = "(.//*[@id='editConfig']//div[@class='itemName'])[1]")
	public WebElement Cart_ProductsName;

	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']//dl[@class='cart-summary-pricingTotal']//span")
	public WebElement Cart_ProductsPrice;

	// NA-17528
	@FindBy(how = How.XPATH, using = "(//input[contains(@id,'Email')])[1]")
	public WebElement SubscriptionPage_inputEmail;

	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
	public WebElement SubscriptionPage_goButton;

	@FindBy(how = How.XPATH, using = "//div/select[@id='Country_ISO2']")
	public WebElement SubscriptionPage_countrySelectDropdown;

	@FindBy(how = How.XPATH, using = ".//div/input[@name='FIRST_NAME']")
	public WebElement SubscriptionPage_inputFName;

	@FindBy(how = How.XPATH, using = ".//div/input[@name='LAST_NAME']")
	public WebElement SubscriptionPage_inputLName;

	@FindBy(how = How.XPATH, using = ".//div/input[@id='submit']")
	public WebElement SubscriptionPage_subscribeButton;

	@FindBy(how = How.XPATH, using = ".//div/h4[@id='header_initial']")
	public WebElement SubscriptionPage_subscribeSuccessMsg;

	@FindBy(how = How.XPATH, using = ".//div/input[@id='button_submit']")
	public WebElement SubscriptionPage_updateProfileButton;

	@FindBy(how = How.XPATH, using = ".//div[@id='header_thx']/h4")
	public WebElement SubscriptionPage_updateSuccessMsg;

	@FindBy(how = How.XPATH, using = ".//div/a[@id='a_shop']/span")
	public WebElement SubscriptionPage_startShopping;

	// NA-17669
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'pw/request')]")
	public WebElement LoginPage_forgotPassword;

	@FindBy(how = How.XPATH, using = ".//*[@id='forgottenPwd.email']")
	public WebElement ForgotPassword_emailIDTxtBox;

	@FindBy(how = How.XPATH, using = "//p/button[@type='submit']")
	public WebElement ForgotPassword_submitButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='profile-newPassword']")
	public WebElement ResetPassword_newPassword;

	@FindBy(how = How.XPATH, using = ".//*[@id='profile.checkNewPassword']")
	public WebElement ResetPassword_confirmNewPassword;

	@FindBy(how = How.XPATH, using = "//div/p[@class='successMessage-copy']")
	public WebElement ResetPassword_PasswordResetSuccMsg;

	@FindBy(how = How.XPATH, using = "//div/a[contains(@class,'successMessage')]")
	public WebElement ResetPassword_ContinueButton;

	@FindBy(how = How.XPATH, using = "//li/a[contains(@href,'update-password')]")
	public WebElement MyAccountPage_ChangePassword;

	@FindBy(how = How.XPATH, using = ".//*[@id='profile.currentPassword']")
	public WebElement UpdatePasswordPage_CurrentPassword;

	@FindBy(how = How.XPATH, using = ".//*[@id='profile.newPassword']")
	public WebElement UpdatePasswordPage_NewPassword;

	@FindBy(how = How.XPATH, using = ".//*[@id='profile.checkNewPassword']")
	public WebElement UpdatePasswordPage_ConfirmNewPassword;

	@FindBy(how = How.XPATH, using = "//div/button[@type='submit']")
	public WebElement UpdatePasswordPage_UpdatePasswordBtn;

	@FindBy(how = How.XPATH, using = ".//*[@id='resetPasswordForm']//div[contains(@class,'errorMessage')]")
	public WebElement ResetPassword_SamePasswordErrorMsg;

	// NA-18050
	@FindBy(how = How.XPATH, using = "//li/a[contains(@class,'cartlink')]")
	public WebElement HomePage_CartIcon;

	@FindBy(how = How.XPATH, using = "//form[@id='editConfig']/p/span[3]")
	public WebElement CartPage_PartNumberOfAddedProduct;
	@FindBy(how = How.XPATH, using = "//form[@id='editConfig']/p/span")
	public WebElement CartPage_PartNumberOfAddedProductOldUI;

	@FindBy(how = How.XPATH, using = ".//*[@id='countrySelector']")
	public WebElement CartPage_CountrySelectorDropdown;
	@FindBy(how = How.XPATH, using = ".//*[@id='senderName']")
	public WebElement EmailCart_SenderName;

	@FindBy(how = How.XPATH, using = ".//*[@id='senderAddress']")
	public WebElement EmailCart_SenderEmail;

	@FindBy(how = How.XPATH, using = ".//*[@id='cartEmailButtons']//input[@name='checkBoxSend']")
	public WebElement EmailCart_CheckBox;

	@FindBy(how = How.XPATH, using = ".//*[@id='sendEmail']")
	public WebElement EmailCart_SendButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='tab-a-nav-currentmodels']/span")
	public WebElement PLPPage_ViewCurrentModels;

	// NA-21941
	@FindBy(how = How.XPATH, using = ".//*[@id='WishlistSelect']")
	public WebElement productDetailPage_wishlistDropDown;
	@FindBy(how = How.XPATH, using = ".//*[@id='WishlistSelect']/option")
	public List<WebElement> productDetailPage_allWishlistOptions;
	@FindBy(how = How.XPATH, using = "(.//*[@id='WishlistSelect']/option)[3]")
	public WebElement productDetailPage_alreadyCreatedWishlistOption;
	@FindBy(how = How.XPATH, using = ".//*[@id='WishlistSelect']/option[contains(@value,'createAnotherWishList')]")
	public WebElement productDetailPage_createAnotherWishlistOption;
	// Clicking on create new wishlist opens a pop up.
	@FindBy(how = How.XPATH, using = ".//input[@placeholder='Enter a wishlist name']")
	public WebElement createWishlist_enterAWishlistNameTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='createWishlistBtn']")
	public WebElement createWishlist_createAWishlistButton;
	// Clicking on add to wishlist also opens a pop up.
	@FindBy(how = How.XPATH, using = ".//*[@id='viewWishListBtn']")
	public WebElement productDetailPage_viewWishlistButton;
	@FindBy(how = How.XPATH, using = ".//a[contains(@href,'shareByEmail')]")
	public WebElement wishlistHistory_shareWishlistLink;
	@FindBy(how = How.XPATH, using = ".//*[@id='senderName']")
	public WebElement shareWishlist_yourFirstNameTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='emailAddress']")
	public WebElement shareWishlist_sendToTextBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='btnWSEmailSend']")
	public WebElement shareWishlist_sendButton;
	@FindBy(how = How.XPATH, using = ".//p[contains(.,'Your wish list page was sent by Customer Service Team.')]")
	public WebElement shareWishlist_wishlistSentConfirmationMessageBar;
	@FindBy(how = How.XPATH, using = ".//p[contains(.,'お客様のウィッシュリストがカスタマーサービスチームによって送信されます')]")
	public WebElement shareWishlist_wishlistSentConfirmationMessageBarJP;
	@FindBy(how = How.XPATH, using = ".//div[@class='emailws-wrapper']/h4")
	public WebElement shareWishlist_wishlistNameLabel;

	// 22214
	@FindBy(how = How.XPATH, using = "//div[@class='partNumber']")
	public WebElement PLP_PartNumber;
	@FindBy(how = How.XPATH, using = "(//a[contains(.,'Shop Now')])[3]")
	public WebElement Deals_ShopNow;
	@FindBy(how = How.XPATH, using = "//form[@id='editConfig']/p/span")
	public WebElement CartInfo_PartNumber;
	@FindBy(how = How.XPATH, using = "//*[@id='cartEntrySummaryItem_0']/div[1]/p/text()[1]")
	public WebElement ShippingInfo_PartNumber;
	@FindBy(how = How.XPATH, using = "//p[contains(.,'Part No.')]/span")
	public WebElement PurchaseInfo_PartNumber;
	@FindBy(how = How.XPATH, using = "//div[@class='cta-group-action']//a[contains(.,'View Models')]")
	public WebElement Product_ViewModels;
	@FindBy(how = How.XPATH, using = "(//button[@id='addToCartButtonTop']//span[@class='icon-atc'])[last()]") // mtm
	public WebElement Product_AddToCart;
	@FindBy(how = How.XPATH, using = "//li[@class='cart-promotions-applied']")
	public WebElement CartPage_PromotionMessage;
	@FindBy(how = How.XPATH, using = "//a[@class='cartlink']")
	public WebElement CartPage_icon;
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber']")
	public WebElement CartPage_partbnumber;
	@FindBy(how = How.XPATH, using = ".//*[text()='Warranty']")
	public WebElement PB_WarrantyTab;
	@FindBy(how = How.XPATH, using = "//a[@tabcode='tab-Accessories']")
	public WebElement PB_AccessoriesTab;
	@FindBy(how = How.XPATH, using = ".//*[@id='cta-builder-skip']/button")
	public WebElement newPB_SkipBuilder;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ccessories')]/div/div[contains(@class,'section-h')][1]")
	public WebElement newPDP_firstSection;
	@FindBy(how = How.XPATH, using = "(.//*[@id='Accessories']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//div[contains(@class,'option-text ')]")
	public WebElement newPDP_firstAccessories;
	@FindBy(how = How.XPATH, using = "(.//*[@id='Accessories']//div[contains(@data-source-id,'section_')])[2]//ul/li[1]//div[contains(@class,'option-text ')]")
	public WebElement newPDP_firstAccOfSecSection;
	@FindBy(how = How.XPATH, using = "//*[@id='cartEntrySummaryItem_0']//p/text()[1]")
	public WebElement Shilping_pn;
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber qa_Product _PartNumber']")
	public WebElement Cart_number;
	@FindBy(how = How.XPATH, using = "//p[@class='checkout-shoppingCart-previewSubtitle']")
	public WebElement ShippingInfo_PartNumber_old;
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber']/span")
	public WebElement Cart_PartNumber;
	@FindBy(how = How.XPATH, using = "//p[@class='checkout-review-item-partNumber']")
	public WebElement Review_partnumber;
	@FindBy(how = How.XPATH, using = "//*[@id='PaymentTypeSelection_Wire']")
	public WebElement Payment_Behalf;
	@FindBy(how = How.XPATH, using = "//div[@class='purchase-item-info']/p/text()[1]")
	public WebElement Payment_partnumber;
	@FindBy(how = How.XPATH, using = "//*[@id=\"cboxLoadedContent\"]/div/div[3]/div[2]/div[5]/input")
	public WebElement Payment_close_address;
	@FindBy(how = How.XPATH, using = "//div[@class='confirmation-item'][2]/span[2]")
	public WebElement Orders;

	// Cart Checkout Reconstruct Testing
	@FindBy(how = How.XPATH, using = ".//*[@id='tab-li-models']/h2")
	public WebElement CC_MTMModel;
	@FindBy(how = How.XPATH, using = ".//*[@id='builderPricingSummary']/dd[1]")
	public WebElement CC_WebPrice;
	@FindBy(how = How.XPATH, using = "//div[@class='price-calculator-cart-items']/dl")
	public WebElement Cart_WebPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='editConfig']/h4/div")
	public WebElement Cart_MTMModel;
	// 17640
	@FindBy(how = How.XPATH, using = "//ul/li/a[contains(@href,'save-carts')]")
	public WebElement MyAccount_ViewSavedCartHistory;

	@FindBy(how = How.XPATH, using = "//div/a[contains(@href,'EmailCart')]")
	public WebElement SavedCart_EmailCart;
	@FindBy(how = How.ID, using = "senderName")
	public WebElement SavedCart_FirstName;
	@FindBy(how = How.ID, using = "senderAddress")
	public WebElement SavedCart_EmailInputBox;
	@FindBy(how = How.XPATH, using = "//*[@title='Clear']")
	public WebElement SavedCart_ClearBtn;

	@FindBy(how = How.XPATH, using = ".//*[@id='addToCartButton4Bundle']")
	public WebElement ConvenienceBundle_Add;
	@FindBy(how = How.XPATH, using = "(//dd[@class='saleprice pricingSummary-priceList-value priceDataHighlight'])[1]")
	public WebElement ConvenienceBundle_PBPrice;
	@FindBy(how = How.XPATH, using = "//span[@class='price-calculator-cart-summary-subTotalWithoutCoupon']")
	public WebElement ConvenienceBundle_SubTotal;

	@FindBy(how = How.XPATH, using = "(//dd[@class='instant instant']/span)[2]")
	public WebElement InstanSavingPrice;

	// NA-17639
	@FindBy(how = How.XPATH, using = "//td/input[@disabled = 'disabled']")
	public WebElement SavedCart_disableEmailInputBox;
	@FindBy(how = How.XPATH, using = "//tr/td/input[@name='checkBoxSend']")
	public WebElement SavedCart_inputCheckBox;
	@FindBy(how = How.ID, using = "sendEmail")
	public WebElement SavedCart_sendButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='bodywrapinner']/div/p[2]")
	public WebElement SavedCart_EmailSuccessMesg;
	@FindBy(how = How.ID, using = "cartEmailSelectMessage2")
	public WebElement SavedCart_ChkOutThisProd;

	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'general_Menu')]//a[contains(@class,'hasNoChildren')]/div[contains(.,'My Account')]")
	public WebElement MyAccount_myAccount;

	// added by 23007
	@FindBy(how = How.ID, using = "checkout_validateFrom_skip")
	public WebElement ValidateInfo_SkipButton;
	@FindBy(how = How.XPATH, using = "//div[@class='qas-address-check-msg-button']/input[@class='entered_button']")
	public WebElement ValidateInfo_UseSuggestButton;
	@FindBy(how = How.XPATH, using = "//dt[@class='cart-summary-pricing-webPrice-label']")
	public WebElement Cart_PriceSubLabel;
	@FindBy(how = How.XPATH, using = "//dl[@class='cart-summary-pricingTotal']/dt[contains(text(),'Total')]")
	public WebElement Cart_PriceTotalLabel;
	@FindBy(how = How.XPATH, using = "//dd[@class='cart-summary-pricing-webPrice-price']/span")
	public WebElement Cart_PriceSubTotal;
	@FindBy(how = How.XPATH, using = "(//dl[@class='cart-summary-pricingTotal']/dd/span)[last()]")
	public WebElement Cart_PriceTotal;
	@FindBy(how = How.XPATH, using = "//dt[@class='checkout-orderSummary-label' and contains(text(),'Sub')]")
	public WebElement Shipping_PriceSubLabel;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummary-pricingTotal']/dt")
	public WebElement Shipping_PriceTotalLabel;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummary-pricing']/dd[contains(@class,'checkout_subtotal')]/span")
	public WebElement Shipping_PriceSubTotal;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummary-pricingTotal']//span")
	public WebElement Shipping_PriceTotal;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummaryReview-subTotals']/dt[contains(text(),'Sub')]")
	public WebElement Review_PriceSubLabel;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummaryReview-totals']/dt[2]")
	public WebElement Review_PriceTotalLabel;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummaryReview-subTotals']/dd[2]")
	public WebElement Review_PriceSubTotal;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummaryReview-totals']/dd")
	public WebElement Review_PriceTotal;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderTotal']//td[contains(text(),'Sub')]")
	public WebElement Thank_PriceSubLabel;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderTotal']//*[contains(text(),'Total')]")
	public WebElement Thank_PriceTotalLabel;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderTotal']//td[contains(text(),'Sub')]/following-sibling::td[1]")
	public WebElement Thank_PriceSubTotal;
	@FindBy(how = How.XPATH, using = ".//*[@id='orderTotal']//*[contains(text(),'Total')]/../following-sibling::td[1]//span")
	public WebElement Thank_PriceTotal;

	@FindBy(how = How.XPATH, using = "//dd//span[contains(@class,'Total_Price')]")
	public WebElement NewCart_PriceTotal;
	@FindBy(how = How.XPATH, using = "//div[@class='summary-item' and not(@id)]/div[contains(text(),'Subtotal')]")
	public WebElement NewShipping_PriceSubLabel;
	@FindBy(how = How.XPATH, using = "//div[@id='summaryTotalPriceDiv']/div[1]")
	public WebElement NewShipping_PriceTotalLabel;
	@FindBy(how = How.XPATH, using = "//div[@class='summary-item total']/div/span[contains(text(),'Total')]")
	public WebElement NewReview_PriceTotalLabel;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Subtotal')]/following-sibling::div")
	public WebElement NewReview_PriceSubTotal;
	@FindBy(how = How.XPATH, using = "//div[@class='summary-item total']/div[2]")
	public WebElement NewReview_PriceTotal;

	// NA-17650
	@FindBy(how = How.XPATH, using = ".//*[@id='j_username']")
	public WebElement JPLogin_UserName;

	@FindBy(how = How.XPATH, using = ".//*[@id='j_password']")
	public WebElement JPLogin_Password;

	@FindBy(how = How.XPATH, using = ".//*[@id='cart-price']//td[contains(.,'お届希望時間帯')]/../td[3]")
	public WebElement OrderHistory_DeliveryTimezone;

	@FindBy(how = How.XPATH, using = ".//label[@for='DELIVERTIMEZONE1']")
	public WebElement OrderHistory_DeliveryTimezoneMorning;
	@FindBy(how = How.XPATH, using = ".//*[@id='deliverTimeZone1']")
	public WebElement OrderHistory_DeliveryTimezoneMorning_old;

	// added by 23009
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Tax')]/following-sibling::dd[1]")
	public WebElement Payment_Tax;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Tax')]/following-sibling::td[1]")
	public WebElement Thank_Tax;
	@FindBy(how = How.XPATH, using = "//dd/span[contains(@class,'totalDiscounts')]")
	public WebElement Cart_CartPromotion;
	@FindBy(how = How.XPATH, using = "//dt[contains(.,'Cart Promotion')]/following-sibling::dd")
	public WebElement Review_CartPromotion;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Promotion')]/following-sibling::td")
	public WebElement Thank_CartPromotion;
	@FindBy(how = How.XPATH, using = "//td[contains(.,'Shipping')]/../../../../following-sibling::td[1]")
	public WebElement Thank_ShippingPrice;
	@FindBy(how = How.XPATH, using = "//input[@id='STANDARD_SHIPPING' and @value='standard-net']")
	public WebElement Shipping_StandardShipping;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummaryReview-subTotals']/dt[contains(.,'Shipping')]/following-sibling::dd[1]")
	public WebElement Review_ShippingPrice;

	@FindBy(how = How.XPATH, using = "//div[@class='price-change-message']/span")
	public WebElement SavedCart_OutOfStockMesg;
	@FindBy(how = How.XPATH, using = "//div[@class='grayOut_item']")
	public WebElement SavedCart_GrayOutProduct;
	@FindBy(how = How.XPATH, using = "//div[@class='savedcarttotal']/p")
	public WebElement SavedCart_SubTotal;
	@FindBy(how = How.XPATH, using = ".//*[@id='openCartButton']")
	public WebElement SavedCart_OpenCart;
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber']/span")
	public WebElement CartPage_ProdNo;

	@FindBy(how = How.XPATH, using = "//dl[contains(@class,'promotion-price')]/dd/span[contains(@class,'totalDiscounts')]")
	public WebElement NewCart_CartPromotionPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='summaryShippingDiv']/div[2]")
	public WebElement NewShipping_ShippingPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='summaryTotalPriceDiv']/div/span[@id='noTaxTotal']")
	public WebElement NewShipping_ShippingTotalPrice;
	@FindBy(how = How.XPATH, using = "//label[contains(@for,'STANDARD_SHIPPING')]/following-sibling::label[1]")
	public WebElement NewShipping_StandardShipping;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'tax-related')]/div[contains(.,'Tax')]/following-sibling::div")
	public WebElement NewPayment_TaxPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='summaryTotalDiscountsDiv']/div[2]")
	public WebElement NewPayment_CartPromotionPrice;
	@FindBy(how = How.XPATH, using = "//div[@class='summary-item shipping']/div[2]")
	public WebElement NewReview_ShippingPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'shipping-value')]")
	public WebElement NewThank_ShippingPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Tax')]/following-sibling::div")
	public WebElement NewThank_TaxPrice;
	@FindBy(how = How.XPATH, using = "//div[@class='qas-address-check-msg-button']/input[@value='USE ADDRESS AS ENTERED*']")
	public WebElement NewShipping_AddressValidateButton;

	// NA-17667
	@FindBy(how = How.ID, using = "showCustomerSearch")
	public WebElement ASM_AdvanceCustomerSearchIcon;
	@FindBy(how = How.XPATH, using = "//*[@id='id']")
	public WebElement AdvanceCustomerSearch_CustomerID;
	@FindBy(how = How.XPATH, using = "//*[@id='advancedCustomerSearch']")
	public WebElement AdvanceCustomerSearch_SearchButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='cartEmailSend']/button[2]")
	public WebElement EmailCart_ClearButton;

	// NA-17361
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'productStatus')]/strong")
	public WebElement B2C_PLP_RedBannerTxt;
	@FindBy(how = How.XPATH, using = "//div[@class='subComingSoonMsg']/strong")
	public WebElement B2C_PLP_RedBannerTxtUSepp;

	// NA-17380
	@FindBy(how = How.XPATH, using = "//li/a[contains(@href,'quotes')]")
	public WebElement MyAccount_ViewSavedQuote;
	@FindBy(how = How.XPATH, using = "(//div[@class='checkout-confirm-orderNumbers']//tr[2]/td[2])[1]")
	public WebElement SavedQuote_QuoteNum;
	@FindBy(how = How.XPATH, using = ".//*[@id='quoteConvertToOrderConfirm']")
	public WebElement ViewSavedQuote_GotItButtonOnPopUp;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'productStatus')]")
	public WebElement ViewSavedQuote_RedBannerTxt;

	// NA-17382
	@FindBy(how = How.XPATH, using = ".//*[@id='validateDateformatForCheckoutSold']")
	public WebElement CartPage_SoldOutPopUP_ContinueBtn;

	@FindBy(how = How.XPATH, using = ".//*[@id='CTO_expandCollapse']")
	public WebElement B2C_CTO_expandCollapse;
	@FindBy(how = How.XPATH, using = "//div[@class='productStatus-models savedSoldOutImageLogo cartListSoldOutlogo']")
	public WebElement Cart_redbanner;
	@FindBy(how = How.XPATH, using = "//div[@class='productStatus-models savedSoldOutImageLogo savedCartSoldOut']")
	public WebElement Save_Cart_redbanner;
	@FindBy(how = How.XPATH, using = "//div[@class='productStatus-models savedSoldOutImageLogo savedQuoteSoldOut']")
	public WebElement Save_quote_redbanner;

	// NA-21596
	@FindBy(how = How.XPATH, using = "//input[contains(@class,'searchInput-text')]")
	public WebElement Global_search;
	@FindBy(how = How.XPATH, using = ".//*[@id='inputSearchText']")
	public WebElement Global_search2;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'search-submit')]")
	public WebElement Global_searchout;

	// added by 23698
	@FindBy(how = How.XPATH, using = ".//div[not( contains(@class,'newPBEhiddenByRule '))and contains(@id,'list_group') and contains(@class,'true')]//*[text()='CHANGE' or text()='変更'or text()='更改']")
	public WebElement CTOConfigurator_Change;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Range')]/following-sibling::input[1]")
	public WebElement NewCTOConfigurator_OverPrice;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Reason')]/following-sibling::input[1]")
	public WebElement NewCTOConfigurator_OverReason;
	@FindBy(how = How.XPATH, using = ".//select[@class='over_reason']/option[3]")
	public WebElement NewCTOConfigurator_OverReasonSelect;
	@FindBy(how = How.XPATH, using = ".//button[text()='UPDATE']")
	public WebElement NewCTOConfigurator_OverReasonSet;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Comment')]/following-sibling::span[3]/input")
	public WebElement CTOConfigurator_OverPrice;
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Comment')]/following-sibling::span[1]/input")
	public WebElement CTOConfigurator_OverReason;
	@FindBy(how = How.XPATH, using = ".//select[@class='over_reason']/option[3]")
	public WebElement CTOConfigurator_OverReasonSelect;
	@FindBy(how = How.XPATH, using = ".//button[text()='Set']")
	public WebElement CTOConfigurator_OverReasonSet;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'cart-summary')]//*[contains(@class,'totalPriceWithTax')]")
	public WebElement CartPage_totalPrice;

	@FindBy(how = How.XPATH, using = "(//div[@data-show='true']//div[@class='option-textFrame'])[2]/..//input[contains(@class,'option-priceOverridePriceInput')]")
	public WebElement PB_OverPrice;
	@FindBy(how = How.XPATH, using = "(//div[@data-show='true']//div[@class='option-textFrame'])[2]/..//input[contains(@class,'option-priceOverrideReasonText')]")
	public WebElement PB_OverReason;
	@FindBy(how = How.XPATH, using = "(//div[@data-show='true']//div[@class='option-textFrame'])[2]/..//select[@class='option-priceOverrideReasonCode']/option[3]")
	public WebElement PB_OverReasonSelect;
	@FindBy(how = How.XPATH, using = "(//div[@data-show='true']//div[@class='option-textFrame'])[2]/..//*[@class='option-priceOverrideButton']")
	public WebElement PB_OverReasonSet;
	@FindBy(how = How.XPATH, using = "(//div[@data-show='true']//div[@class='option-textFrame'])[2]/../input")
	public WebElement PB_OptionSelection;
	@FindBy(how = How.XPATH, using = "//*[text()='CHANGE' or text()='変更'or text()='更改']")
	public WebElement NewPB_Change;
	@FindBy(how = How.XPATH, using = "//div[@class='option-profitOverride']/a")
	public WebElement NewPB_PriceOverride;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/ul/li//div[not(contains(@class,'product-builder-hide'))]/div[@class='telesales-modal-content']//li[@class='priceOption']/input")
	public WebElement NewPB_OverPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/ul/li//div[not(contains(@class,'product-builder-hide'))]/div[@class='telesales-modal-content']//li[@class='reasonText']/input")
	public WebElement NewPB_OverReason;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/ul/li//div[not(contains(@class,'product-builder-hide'))]/div[@class='telesales-modal-content']//li[@class='reasonCode']/select/option[3]")
	public WebElement NewPB_OverReasonSelect;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/ul/li//div[not(contains(@class,'product-builder-hide'))]/div[@class='telesales-modal-content']//li[last()]/input")
	public WebElement NewPB_OverReasonSet;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/ul/li//div[not(contains(@class,'product-builder-hide'))]/div[@class='telesales-modal-content']/i")
	public WebElement NewPB_OverrideClose;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'qa-configurator-sectionCollapseButton')]")
	public WebElement NewPB_ChangeClose;

	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/div[@class='priceOverride_main']/div[contains(@style,'block')]/div[@class='telesales-modal-content']//li[@class='priceOption']/input")
	public WebElement NewPB_OverPrice2;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/div[@class='priceOverride_main']/div[contains(@style,'block')]/div[@class='telesales-modal-content']//li[@class='reasonText']/input")
	public WebElement NewPB_OverReason2;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/div[@class='priceOverride_main']/div[contains(@style,'block')]/div[@class='telesales-modal-content']//li[@class='reasonCode']/select/option[3]")
	public WebElement NewPB_OverReasonSelect2;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/div[@class='priceOverride_main']/div[contains(@style,'block')]/div[@class='telesales-modal-content']//li[last()]/input")
	public WebElement NewPB_OverReasonSet2;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group_')]/div[@class='priceOverride_main']/div[contains(@style,'block')]/div[@class='telesales-modal-content']/i")
	public WebElement NewPB_OverrideClose2;

	// added by 22761
	@FindBy(how = How.XPATH, using = ".//*[@id='PaymentTypeSelection_PAYU']")
	public WebElement paymentPage_payU_Method;

	@FindBy(how = How.XPATH, using = ".//*[@id='tab-li-currentmodels']//*[@id='addToCartButtonTop']/span[@class='icon-cus']")
	public WebElement B2C_PDP_CustomizeButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='tab-li-currentmodels']//*[@id='addToCartButtonTop']")
	public WebElement B2C_PDP_MTMAddToCartButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='tab-li-currentmodels']//*[@id='addToCartButtonTop']/span[@class='icon-atc']")
	public WebElement B2C_PDP_AddToCartButton;

	@FindBy(how = How.XPATH, using = "(.//ol[@class='categoryListing columnSlider-move']//h1[@class='seriesPreview-title'])")
	public WebElement B2C_PLP_Series;

	@FindBy(how = How.XPATH, using = "(.//*[@id='mainContent']//div[@class='seriesListings-footer']/a)")
	public WebElement B2C_PLP_Subseries;

	@FindBy(how = How.XPATH, using = ".//*[@id='cta-builder-skip']/button")
	public WebElement B2C_PDP_SkipAndAddToCart;

	@FindBy(how = How.XPATH, using = ".//*[@id='cta-builder-continue']/button")
	public WebElement B2C_PDP_AddToCart;

	@FindBy(how = How.XPATH, using = "//div[@class='accessoriesCategories']//span[2]")
	public WebElement B2C_Accessory_BrowseAllCategory;

	@FindBy(how = How.XPATH, using = "(.//*[@id='ChargersBatteries']//li/a)[1]")
	public WebElement B2C_Accessory_Charger;

	@FindBy(how = How.XPATH, using = "(.//*[@id='productGrid-target']//div[@class='thumb']//img)[1]")
	public WebElement B2C_Accessory_Audio;

	@FindBy(how = How.XPATH, using = "(.//*[@id='mainContent']//ol[contains(@class,'accessoriesListing')]/li//h3/a)[1]")
	public WebElement B2C_Accessory_SubAccessory;

	@FindBy(how = How.XPATH, using = ".//*[contains(@class,'addToCartPopupButton')]")
	public WebElement B2C_Accessory_SubAccessory_AddToCartPopup;

	// NA-19685
	@FindBy(how = How.XPATH, using = ".//span[text()='Software' or text()='軟件']")
	public WebElement PB_SoftwareTab;
	@FindBy(how = How.XPATH, using = "(.//*[@id='Software']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//div[contains(@class,'option-text')]")
	public WebElement newPDP_firstSoftware;
	@FindBy(how = How.XPATH, using = "(.//*[@id='Software']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//div[contains(@class,'option-priceArea')]")
	public WebElement newPDP_AddFirstSoftware;
	@FindBy(how = How.XPATH, using = "(.//*[@id='Software']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//span[contains(@class,'option-price-selected')]")
	public WebElement newPDP_CheckFirstSoftware;
	@FindBy(how = How.XPATH, using = "(.//*[@id='Accessories']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//div[contains(@class,'option-priceArea')]")
	public WebElement newPDP_AddFirstAccessories;
	@FindBy(how = How.XPATH, using = "(.//*[@id='Accessories']//div[contains(@data-source-id,'section_')])[1]//ul/li[1]//span[contains(@class,'option-price-selected')]")
	public WebElement newPDP_CheckFirstAccessories;
	@FindBy(how = How.XPATH, using = "//div[@id='mainContent']//span[@class='price-calculator-order-summary-deliveryCost']")
	public WebElement Payment_ShippingPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='cart-price']//td[contains(@class,'checkout2CartSubtotal')]")
	public WebElement OrderThankyou_SubtotalPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='cart-price']//td[contains(text(),'FREE') or contains(text(),'免費')]")
	public WebElement OrderThankyou_ShippingPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='cart-price']//tr[@class='checkout2SubHead'][3]/td[3]/span")
	public WebElement OrderThankyou_TotalPrice;

	// NA-19686
	@FindBy(how = How.XPATH, using = "//div[@class='configuratorItem-accordion-content software']")
	public WebElement newPDP_firstSoftwareText;
	@FindBy(how = How.XPATH, using = "(//div[@class='configuratorItem-accordion-content'])[2]")
	public WebElement oldPDP_softwareText;
	@FindBy(how = How.XPATH, using = "(//div[@data-show='true']//li[contains(@class,'configuratorItem')])[1]/input[1]")
	public WebElement oldPDP_firstAccessoriesText;
	@FindBy(how = How.XPATH, using = ".//*[@id='cart-price']//tr[@class='checkout2SubHead']/td[3]/span")
	public WebElement OrderThankyou_TotalPriceText;

	// NA-16656
	@FindBy(how = How.XPATH, using = "//*[@id='facet-area']")
	public WebElement FacetPanel;
	@FindBy(how = How.XPATH, using = "//nav/a[@class='breadcrumb-item']/../span/a[2]")
	public WebElement SeriesLevel3Link;
	@FindBy(how = How.XPATH, using = "//nav/a[@class='breadcrumb-item']/../span/a[1]")
	public WebElement SeriesLink;

	// NA-17709
	@FindBy(how = How.XPATH, using = ".//*[not(contains(@class,'hidden'))]/*[@id='ctoYourPrice' or @id='w-price']")
	public WebElement PDP_webPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='render-summary']//*[contains(text(),'Instant Savings')]/following-sibling::div")
	public WebElement PDP_instantPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='render-summary']//div[contains(@class,'promo-wrap coupon')]//*[contains(text(),'With eCoupon:')]/following-sibling::div")
	public WebElement PDP_eCouponPrice;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'qa_AddToCartButton')]")
	public WebElement PDP_AddToCartButton1;
	@FindBy(how = How.XPATH, using = "//div[@class='price-calculator-cart-items']//dl[@class='cart-summary-pricing-webPrice-label']")
	public WebElement Cart_itemPrice;
	@FindBy(how = How.XPATH, using = ".//a[@id='CTO_expandCollapse' and contains(text(),'+')]")
	public WebElement cto_expandCategories;

	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-orderSummary-pricing']/dd[contains(@class,'summary-shipping-cost')]/span")
	public WebElement Shipping_PriceShipping;

	@FindBy(how = How.XPATH, using = ".//a[contains(@class,'addedToCart')]")
	public WebElement B2C_Accessory_GoToCart;

	// NA-17696
	@FindBy(how = How.XPATH, using = "//p[contains(.,'Reseller')]")
	public WebElement B2C_SummaryPage_ResellerId;

	@FindBy(how = How.XPATH, using = ".//*[@id='resellerID']")
	public WebElement B2C_SummaryPage_ResellerIdTextBox;

	// New Cart page

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'Submit_Button')]")
	public WebElement NewCart_Submit;

	@FindBy(how = How.XPATH, using = ".//*[@id='quickOrderProductId']")
	public WebElement NewCart_PartNumberTextBox;
	@FindBy(how = How.XPATH, using = "//dt[contains(text(),'Line total') or contains(text(),'合計')]/../dd")
	public WebElement NewCart_SalesPrice;
	@FindBy(how = How.XPATH, using = "//input[@value='Update' or '更新する' and @type='submit']")
	public WebElement NewCart_UpdatePrice;

	// NA-23022 New Cart Page
	@FindBy(how = How.XPATH, using = "//dd/span[contains(@class,'subTotal')]")
	public WebElement NewCart_SubTotalPrice;
	@FindBy(how = How.XPATH, using = "//dd/span[contains(@class,'totalPrice')]")
	public WebElement NewCart_TotalPrice;
	@FindBy(how = How.XPATH, using = "//div/span[contains(@class,'totalTax')]")
	public WebElement NewCart_VATPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='emptyCartItemsForm']/a/img")
	public WebElement NewCart_DeleteButton;
	@FindBy(how = How.XPATH, using = "//div[@class='popup_arrow_box']/div/input[contains(@onclick,'submit')]")
	public WebElement NewCart_ConfirmDelete;

	// 18085
	@FindBy(how = How.XPATH, using = "//dd[@class='cart-summary-pricing-gst-price']/span[contains(@class,'totalTax')]")
	public WebElement NewCart_TaxPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='summaryTaxDiv']/div[2]")
	public WebElement NewShipping_TaxPrice;
	@FindBy(how = How.XPATH, using = "//div[@id='summaryTotalPriceDiv']/div/span[@id='withTaxTotal']")
	public WebElement NewShipping__TotalPrice;
	@FindBy(how = How.XPATH, using = "//div[@class='order-summary']/div[@class='summary-item']")
	public List<WebElement> NewSummary__ItemPrice;
	@FindBy(how = How.XPATH, using = "(//div[@class='summary-item item-no-margin']/div[2])[last()]")
	public WebElement NewThankPage__TotalTax;
	@FindBy(how = How.XPATH, using = "//div[@class='summary-item total']/div[2]")
	public WebElement NewThankPage__TotalPrice;

	@FindBy(how = How.XPATH, using = "//dl/dd[contains(@class,'cart-summary-pricing-gst-price')]")
	public WebElement Cart_TaxPrice;
	@FindBy(how = How.XPATH, using = "//div//dd//span[contains(@class,'totalTax')]")
	public WebElement Shipping_TaxPrice;
	@FindBy(how = How.XPATH, using = "//dl[contains(@class,'subTotals')]/dd[2]")
	public WebElement Review_SubTotalPrice;
	@FindBy(how = How.XPATH, using = "//dl[contains(@class,'subTotals')]/dd[4]")
	public WebElement Review_TaxPrice;
	@FindBy(how = How.XPATH, using = "//dl/dd[contains(@class,'totals-totalValue')]")
	public WebElement Review_TotalPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='cart-price']/table//tr[2]/td[contains(@class,'CartSubtotal')]")
	public WebElement OrderPage_SubTotalPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='cart-price']/table//tr[@class='checkout2SubHead']/td[3]")
	public List<WebElement> OrderPage_TaxTotalPrice;

	// NA-23911
	@FindBy(how = How.XPATH, using = "//*[@id='advancedStoreSearchIcon']")
	public WebElement asm_StoreIDIcon;
	@FindBy(how = How.XPATH, using = ".//*[@id='startAdvStoreSearch']")
	public WebElement asm_StoreSearchButton;
	@FindBy(how = How.XPATH, using = "//*[@id='advancedTransactionSearchIcon']")
	public WebElement asm_TransactionSearchIcon;
	@FindBy(how = How.XPATH, using = ".//*[@id='startAdvTransactionSearch']")
	public WebElement asm_TransactionSearchButton;

	// NA-21949
	@FindBy(how = How.XPATH, using = "//*[@id='asmDplReportButton']")
	public WebElement asm_DPLReportButton;
	@FindBy(how = How.XPATH, using = "//*[@id='cboxLoadedContent']/div")
	public WebElement asm_DPLReportPopUp;
	@FindBy(how = How.XPATH, using = "//input[@name='code']")
	public WebElement DPLReport_ID;
	@FindBy(how = How.XPATH, using = "//button[text()='Search']")
	public WebElement DPLReport_SearchButton;

	// NA-17643
	@FindBy(how = How.XPATH, using = "//*[@id='asmOrderReportButton']")
	public WebElement ASM_QuoteOrderReportButton;
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

	// NA-21876
	@FindBy(how = How.XPATH, using = "//td/dl[contains(@class,'finalPrice-amount')]")
	public WebElement CartPage_FinalPrice;
	@FindBy(how = How.XPATH, using = "//td/input[contains(@class,'overriddenPrice')]")
	public WebElement CartPage_PriceOverRide_TxtBox;
	@FindBy(how = How.XPATH, using = ".//*[@id='reasonCode0']")
	public WebElement CartPage_PriceOverRide_ReasonDropDown;
	@FindBy(how = How.XPATH, using = "//*[@id='cboxLoadedContent']/div//select/option[@id='QUOTE']")
	public WebElement assistedServiceMode_transactionType_QuoteType;
	@FindBy(how = How.XPATH, using = "//*[@id='cboxLoadedContent']//input")
	public WebElement assistedServiceMode_transactionType_QuoteIDTxtBox;
	@FindBy(how = How.XPATH, using = ".//input[contains(@id,'quantity')]")
	public WebElement QuoteDetails_Quote_Quantity;

	// NA-17457
	@FindBy(how = How.XPATH, using = "//li/h3[contains(.,'ABOUT LENOVO')]")
	public WebElement HomePage_AboutLenovoLinks;

	@FindBy(how = How.XPATH, using = "(.//*[@id='addToCartButtonTop']/span[@class='icon-cus'])[1]/../../..//div[@class='promotedOptions'][1]/div[@class='checkboxOptions']/input")
	public WebElement PDP_PromotedOptionInputBox;
	@FindBy(how = How.XPATH, using = "(.//*[@id='addToCartButtonTop']/span[@class='icon-cus'])[1]/../../..//div[@class='promotedOptions'][1]/div[@class='checkboxText']")
	public WebElement PDP_PromotedOptionText;

	// NA-21786
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'accessoriesCategoriesTitle')]")
	public WebElement Accessories_browseAllCategory;
	@FindBy(how = How.XPATH, using = "//*[@id='Monitors']/div/h3/a")
	public WebElement Accessories_category_Monitor;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'updateQuantity')]/input")
	public WebElement Accessories_cart_popup;
	@FindBy(how = How.XPATH, using = "//div[@class='addtoCartCTA']/button[contains(.,'Add')]")
	public WebElement Accessories_cart_popup_AddToCart;
	@FindBy(how = How.XPATH, using = "//input[@id='quantity0']")
	public WebElement cartPage_Quantity;
	@FindBy(how = How.XPATH, using = "//input[@id='QuantityProduct_0']")
	public WebElement cartPage_Quantity_update;
	@FindBy(how = How.XPATH, using = "(//input[contains(@id,'quantity')])[2]")
	public WebElement cartPage_Quantity1;
	@FindBy(how = How.XPATH, using = "(//input[contains(@id,'QuantityProduct')])[2]")
	public WebElement cartPage_Quantity_update1;
	@FindBy(how = How.XPATH, using = "(//input[contains(@id,'quantity')])[3]")
	public WebElement cartPage_Quantity2;
	@FindBy(how = How.XPATH, using = "(//input[contains(@id,'QuantityProduct')])[3]")
	public WebElement cartPage_Quantity_update2;


	// NA-17938
	@FindBy(how = How.XPATH, using = ".//*[@id='isDisplayEmailMsg']/span")
	public WebElement cartPage_emailIncorrectError;
	@FindBy(how = How.XPATH, using = ".//*[@id='contactEmail']")
	public WebElement cartPage_quoteContactEmail;
	@FindBy(how = How.XPATH, using = ".//*[@id='create-one-time-quote']/../a/button")
	public WebElement cartPage_quoteLoginButton;

	// 20136
	@FindBy(how = How.ID, using = "card_nameOnCard")
	public WebElement Payment_CardNameTextBox;

	// NA-17126
	@FindBy(how = How.ID, using = "card_nameOnCard")
	public WebElement LenovoHome_closeHomePagePopUp;
	@FindBy(how = How.ID, using = "card_nameOnCard")
	public WebElement LenovoHome_closeLeftSidePopUp;
	@FindBy(how = How.ID, using = "card_nameOnCard")
	public WebElement LenovoShoppingCart_quickOrderProId;
	@FindBy(how = How.ID, using = "card_nameOnCard")
	public WebElement LenovoShoppingCart_resultText;
	@FindBy(how = How.ID, using = "card_nameOnCard")
	public WebElement LenovoShoppingCart_partNo;

	// NA-17455
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-item') and contains(@class,'visible') and not(contains(@class,'active'))]//span[@class='label-text']")
	public WebElement CTO_unselectedItem;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-item') and contains(@class,'visible') and not(contains(@class,'active'))]//span[contains(@class,'label-text')]")
	public WebElement CTO_unselectedItemText;
	@FindBy(how = How.XPATH, using = "//tr[@class='comp-item radio-item visible']//input[not(contains(@class,'checked')) and not(@checked='checked') ]/../..//label[not(contains(@class,'disabled'))]//span[@class='label-text']")
	public WebElement CTO_unselectedItemTextOld;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-item') and contains(@class,'visible') and not(contains(@class,'active'))]//div[contains(@class,'price')]")
	public WebElement CTO_unselectedItemPrice;
	@FindBy(how = How.XPATH, using = "//input[not(contains(@class,'checked'))]/../..//label[not(contains(@class,'disabled'))]/../../td[@class='price']")
	public WebElement CTO_unselectedItemPriceOld;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'Warranty')]//button[contains(@class,'sectionExpand')]")
	public WebElement PB_warrantyChange;
	@FindBy(how = How.XPATH, using = "//div[@id='Warranty']//span[@class='option-price-opt']")
	public WebElement PB_addWarrantyItem;
	@FindBy(how = How.XPATH, using = "//div[@id='Warranty']//span[@class='option-price-opt']/../input")
	public WebElement PB_addWarrantyItemID;
	@FindBy(how = How.XPATH, using = "(//li[contains(@class,'stackableWarranty-optionList-option') and not(contains(@class,'selected'))])[1]")
	public WebElement PB_stackableWarrantyItem;
	@FindBy(how = How.XPATH, using = "(//li[contains(@class,'stackableWarranty-optionList-option') and contains(@class,'selected')])[1]//input")
	public WebElement PB_stackableWarrantyItemID;
	@FindBy(how = How.XPATH, using = "//div[@id='Software']//label[@class='option-price-label']/input[not(@checked)]/..")
	public WebElement PB_softwareItem;
	@FindBy(how = How.XPATH, using = "//div[@id='Software']//label[@class='option-price-label']/input[not(@checked)]")
	public WebElement PB_addSoftwareItemID;
	@FindBy(how = How.XPATH, using = "//div[@id='Accessories']//p[not(contains(@class,'hide'))]//span[@class='option-price-opt']")
	public WebElement PB_accessoryItem;
	@FindBy(how = How.XPATH, using = "//div[@id='Accessories']//p[not(contains(@class,'hide'))]//span[@class='option-price-opt']/../../../input")
	public WebElement PB_addAccessoryItemID;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'expandableHeading') and contains(@class,'configurationDetails')]")
	public WebElement Cart_configurationDetails;
	@FindBy(how = How.XPATH, using = "//ul[@class='cart-item-configurationDetails']/li")
	public List<WebElement> Cart_configurationDetailsItems;
	@FindBy(how = How.XPATH, using = "//dd[@class='cart-item-addedItem-partNumber']")
	public List<WebElement> Cart_addedItem;
	@FindBy(how = How.XPATH, using = "//*[@class='qa_Edit_link' or @id='editlink_0' or text()='編集' or text()='Edit']")
	public WebElement Cart_edit;
	@FindBy(how = How.XPATH, using = "//h3[@class='configuratorHeader qa-configurator-configuratorHeader']")
	public WebElement CTO_newconfiguratorHeader;
	@FindBy(how = How.XPATH, using = "//li[contains(@data-tabcode,'Warranty') or contains(@data-nav-type,'Warranty') or contains(@data-tabname,'Warranty')]")
	public WebElement PB_warrantyTab;
	@FindBy(how = How.XPATH, using = "//li[@data-tabcode='Software' or @data-nav-type='Software' or @data-tabname='Software']")
	public WebElement PB_softwareTab;
	@FindBy(how = How.XPATH, using = "//li[@data-tabcode='Accessories' or @data-nav-type='Accessories' or @data-tabname='Accessories']")
	public WebElement PB_accessoriesTab;
	@FindBy(how = How.XPATH, using = "//*[@id='Warranty']//div[contains(@class,'option_check')]/input")
	public WebElement PB_selectedWarrantyID;
	@FindBy(how = How.XPATH, using = "//div[@data-tabname='Warranty']//input[@checked='checked']")
	public WebElement PB_selectedWarrantyID_Old;
	@FindBy(how = How.XPATH, using = "(//li[contains(@class,'stackableWarranty-optionList-option') and contains(@class,'selected')])[1]//input")
	public WebElement PB_selectedWarrantyID_stackable;
	@FindBy(how = How.XPATH, using = "//*[@id='Software']//div[contains(@class,'option_check')]/..//input")
	public WebElement PB_selectedSoftwareID;
	@FindBy(how = How.XPATH, using = "//div[@data-tabname='Software']//div[contains(@class,'product-builder-hide')]//span[text()='add']/../..//input")
	public WebElement PB_selectedSoftwareID_Old;
	@FindBy(how = How.XPATH, using = "//*[@id='Accessories']//div[contains(@class,'option_check')]/..//input")
	public WebElement PB_selectedAccessoriesID;
	@FindBy(how = How.XPATH, using = "//div[@data-tabname='Accessories']//div[contains(@class,'product-builder-hide')]//span[text()='add']/../..//input")
	public WebElement PB_selectedAccessoriesID_Old;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-item') and contains(@class,'visible') and contains(@class,'active')]//span[@class='label-text']")
	public List<WebElement> CTO_selectedCVText;
	@FindBy(how = How.XPATH, using = "//li[@group-id='Depot Warranty']")
	public List<WebElement> PB_newWarrantyItems;

	// 16687
	@FindBy(how = How.XPATH, using = "//div[@id='Accessories']//div[@class='expandableHeading section-header']")
	public WebElement PB_firstExpandAccessory;

	// 16863
	@FindBy(how = How.XPATH, using = "//button[contains(.,'Customize')]")
	public WebElement PDP_AddToCartLastButton;
	@FindBy(how = How.ID, using = "ctoYourPrice")
	public WebElement newCTO_PBYourPrice;
	@FindBy(how = How.XPATH, using = ".//*[@id='list_group_EPMEMORY_SELECTION']/div[1]/div[2]/button")
	public WebElement newCTO_MemoryChangeButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_EPMEMORY_SELECTION']/div//button")
	public WebElement newCTO_MemoryChangeQtyButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_EPMEMORY_SELECTION']//div[1]/div//ul//a[contains(.,'2')]")
	public WebElement newCTO_MemoryChangeQty2;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_EPMEMORY_SELECTION']//div[1]/div//ul//a[contains(.,'1')]")
	public WebElement newCTO_MemoryChangeQty1;
	@FindBy(how = How.XPATH, using = "(.//*[@id='group_EPMEMORY_SELECTION']/div/div[@class='group-item comp-item radio-item visible   qa-configurator-groupItem']/div[@class='price qa-configurator-groupItem-price'])[1]")
	public WebElement newCTO_MemoryChanedOption;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_EPMEMORY_SELECTION']/div/div[contains(@class,'default')]/div[@class='price qa-configurator-groupItem-price']")
	public WebElement newCTO_MemoryDefaultOptionPrice;

	@FindBy(how = How.XPATH, using = ".//*[@id='group_EPMEMORY_SELECTION']/tr/td[1]/div/button")
	public WebElement ctoPage_MemoryChangeQtyButton;
	@FindBy(how = How.XPATH, using = ".//*[@id='group_EPMEMORY_SELECTION']/tr/td[1]/div//ul/li[2]/a")
	public WebElement ctoPage_MemoryChangeQty2;
	@FindBy(how = How.XPATH, using = "(//tr[@class='comp-item radio-item visible']/td/input[contains(@id,'EPMEMORY_SELECTION')])[2]")
	public WebElement ctoPage_MemoryChanedOption;
	@FindBy(how = How.XPATH, using = "(//tr[@class='comp-item radio-item visible']/td/input[contains(@id,'EPMEMORY_SELECTION')]/../../.)[1]/td[6]")
	public WebElement ctoPage_MemoryDefaultOptionPrice;
	@FindBy(how = How.XPATH, using = "(//tr[@class='comp-item radio-item visible']/td/input[contains(@id,'EPMEMORY_SELECTION')]/../../.)[2]/td[6]")
	public WebElement ctoPage_MemoryChanedOptionPrice;

	// NA-25376
	@FindBy(how = How.XPATH, using = "//div[@class='cart-summary-ecouponForm-wrapper']/div/div/span")
	public WebElement B2CPriceRules_ecouponMessage;

	// NA-26087
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'ASM_close')]")
	public WebElement ASM_signout;

	// NA18461
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'stepsItem-item')][last()-1]") // applied for both old and
																							// new UI
	public WebElement PB_unVisitedTab;// last-1
	@FindBy(how = How.XPATH, using = "//*[@class='checkoutForm-formLabel checkoutForm-formLabel-required']")
	public List<WebElement> PB_shippingLabel;
	@FindBy(how = How.XPATH, using = "//div[@id='billingAddressForm']//label[@class='checkoutForm-formLabel checkoutForm-formLabel-required']")
	public List<WebElement> PB_billingLabel;
	@FindBy(how = How.XPATH, using = "//form[contains(@id,'addressForm')]//*[contains(@class,'field_placeholder checkoutForm-formLabel-required')]")
	public List<WebElement> PB_shippingLabel_new;
	@FindBy(how = How.XPATH, using = "//div[@class='billing_section']//label[contains(@class,'field_placeholder checkoutForm-formLabel-required')]")
	public List<WebElement> PB_billingLabel_new;
	@FindBy(how = How.XPATH, using = "//*[@class='unchecked-icon']")
	public WebElement PB_isDifferentBillingAddress;
	@FindBy(how = How.ID, using = "billTo_firstName")
	public WebElement Payment_FirstNameTextBox_new;

	// 18082
	@FindBy(how = How.XPATH, using = "(.//button[@id='addToCartButtonTop' and text()='Customize'or text()='カスタマイズ' or text()='Customise'])[last()]")
	public WebElement PDP_AddToCartOrCustomize;

	// 23109
	@FindBy(how = How.XPATH, using = "//div[@class='pricingSummary']//button")
	public WebElement PB_addToCart_old;

	// 17830
	@FindBy(how = How.XPATH, using = "//*[@id='lang-selector']/option[@value='en_HK']")
	public WebElement HKZF_ChangeLanguageToggle;

	// 23914
	@FindBy(how = How.XPATH, using = "//a[text()='CREATE ACCOUNT']")
	public WebElement SMB_createAccount;
	@FindBy(how = How.ID, using = "firstName")
	public WebElement SMB_firstName;
	@FindBy(how = How.ID, using = "lastName")
	public WebElement SMB_lastName;
	@FindBy(how = How.ID, using = "email")
	public WebElement SMB_email;
	@FindBy(how = How.ID, using = "companyName")
	public WebElement SMB_companyName;
	@FindBy(how = How.ID, using = "pwd")
	public WebElement SMB_pwd;
	@FindBy(how = How.ID, using = "checkPwd")
	public WebElement SMB_checkPwd;
	@FindBy(how = How.XPATH, using = "//button[@value='Submit']")
	public WebElement SMB_createAccount2;
	@FindBy(how = How.XPATH, using = "//font[contains(text(),'Thank You For Creating An Account')]")
	public WebElement SMB_successMsg;
	@FindBy(how = How.XPATH, using = "//button[@type='submit' and @class='positive']")
	public WebElement SMB_signIntoAccount;
	@FindBy(how = How.ID, using = "smb-login-button")
	public WebElement SMB_login;
	@FindBy(how = How.ID, using = "login.email.id")
	public WebElement SMB_uName;
	@FindBy(how = How.ID, using = "login.password")
	public WebElement SMB_uPwd;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'signInForm-submitButton')]")
	public WebElement SMB_signIn;

	// 21134
	@FindBy(how = How.XPATH, using = "//*[@class='errorHeading' or contains(text(),'Debug Info')]")
	public WebElement Home_errorHeading;
	@FindBy(how = How.XPATH, using = "(//span[@itemprop='title'])[3]")
	public WebElement LaptopsL1_title;
	@FindBy(how = How.XPATH, using = "//div[@class='hero-productDescription-body']/h2")
	public WebElement LaptopsL1_tagline;
	@FindBy(how = How.XPATH, using = "(//ul[@class='menu prd_Menu']//a/span)[1]")
	public WebElement Home_productTitle;

	// 25939
	@FindBy(how = How.XPATH, using = "//dt[contains(text(),'Recycling Fee')]/following-sibling::dd[1]")
	public WebElement Payment_RecyclingFee;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Recycling Fee')]/following-sibling::td[1]")
	public WebElement Thank_RecyclingFee;
	@FindBy(how = How.XPATH, using = "//div[text()='Recycling Fee']/following-sibling::div")
	public WebElement NewPayment_RecyclingFee;
	@FindBy(how = How.XPATH, using = "//span[contains(.,'Recycling Fee')]/../following-sibling::div")
	public WebElement NewReview_RecyclingFee;
	@FindBy(how = How.XPATH, using = "//div[text()='Recycling Fee:']/following-sibling::div")
	public WebElement NewThank_RecyclingFee;

	// 18237
	@FindBy(how = How.XPATH, using = "(//ul[@class=' menu_2']//a[@class='has-submenu brandlinks']/h3[@class='megaMenu_child'])[1]")
	public WebElement Think;
	@FindBy(how = How.XPATH, using = "//li/h2/span[@class='tabbedBrowse-currentTab']")
	public WebElement Compare;
	@FindBy(how = How.XPATH, using = "//div/div/div[5]/p[1]/button[@class='button-called-out compare-landingpage-action']/span")
	public WebElement Compare_items;
	@FindBy(how = How.XPATH, using = "//div/div/div[@class='productSummaryInfo'][1]/div[1]/span")
	public WebElement Remove_icon;
	@FindBy(how = How.XPATH, using = "//div/div[@class='rci-esm']")
	public WebElement Remove;
	@FindBy(how = How.XPATH, using = "//div[@class='compareBtn']")
	public WebElement CompareBtn;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'compare-removeall')]")
	public WebElement Clear;
	@FindBy(how = How.XPATH, using = "//div[@class='megaMenu_mainSection active']")
	public WebElement Products_series;
	@FindBy(how = How.XPATH, using = "//div[@class='com_product com_culumnwidth']")
	public WebElement Products_clear;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'thinkpad13')]")
	public WebElement View_think13;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'thinkpad-13-series')]")
	public WebElement View_think13_us;

	// 18342
	@FindBy(how = How.XPATH, using = ".//*[contains(@class,'bv-rating-ratio')]/a/span[contains(text(),'☆☆☆☆☆')]")
	public WebElement Reviews_Title;
	@FindBy(how = How.XPATH, using = ".//*[contains(@class,'bv-rating-ratio-number')]//a/span")
	public WebElement Score_Title;
	@FindBy(how = How.XPATH, using = ".//td[contains(@class,'bv-rating-ratio')]/span/span[contains(text(),'★★★★★')]")
	public WebElement Reviews;
	@FindBy(how = How.XPATH, using = ".//td[contains(@class,'bv-rating-ratio')]/span/span[contains(text(),'★★★★★')]/../../span[2]")
	public WebElement Score;
	@FindBy(how = How.XPATH, using = "//div[@class='bv-write-review-container bv-write-container']")
	public WebElement Write_review;
	@FindBy(how = How.XPATH, using = "//*[@id='viewmodel-container']//li[1]//img")
	public WebElement Product_thinkpad_us;
	@FindBy(how = How.XPATH, using = "//h1[@class='categoryDescription-heading columnSlider-heading']/a[contains(@href,'thinkpad')]")
	public WebElement Product_thinkpad_us1;
	@FindBy(how = How.XPATH, using = "//div[@id='THINKPAD']//div[@class='model']//a[@class='vam-subseries']")
	public WebElement Product_thinkpad_anyone;
	@FindBy(how = How.XPATH, using = "//article[@class='categoryDescription hide-tab-content']//h1[contains(@class,'categoryDescription-heading columnSlider-heading')]")
	public WebElement Product_thinkpad_au;
	@FindBy(how = How.XPATH, using = "//div[@class='yCmsContentSlot']//a[@class='red category-button active-cat tabs_1']/div[1]")
	public WebElement Product_thinkpad_hk;
	@FindBy(how = How.XPATH, using = "//div[@class='brandListings-footer']//span")
	public WebElement Product_thinkpad_proau;
	@FindBy(how = How.XPATH, using = "//button[@class='button add-to-cart qa_AddToCartButton']")
	public WebElement Product_custiome_add;
	@FindBy(how = How.XPATH, using = "//span[@id='CTO_addToCartSkip']")
	public WebElement Product_custiome_skip;

	// 18378
	@FindBy(how = How.XPATH, using = "//div/ul/li[6]/div/div[1]/ul/li[1]/a/h3")
	public WebElement Navigation_Monitors;
	@FindBy(how = How.XPATH, using = "//*[@id='facet-area']/div[1]")
	public WebElement Search_button;
	@FindBy(how = How.XPATH, using = "//*[@id='accessoryProductListing']/ol")
	public WebElement Search_product;
	@FindBy(how = How.XPATH, using = "(//h3[@class='accessoriesList-title qa_product_title']/a)[1]")
	public WebElement Product_title;
	@FindBy(how = How.XPATH, using = "//*[@id=\"j_username\"]")
	public WebElement Sign_username;
	@FindBy(how = How.XPATH, using = "//*[@id=\"j_password\"]")
	public WebElement Sign_password;
	@FindBy(how = How.XPATH, using = "//*[@id=\"nemoLoginForm\"]/button")
	public WebElement Sign_button;
	@FindBy(how = How.XPATH, using = "//*[@id=\"checkoutForm-shippingContinueButton\"]")
	public WebElement Continu;
	@FindBy(how = How.XPATH, using = "//*[@id=\"add-payment-method-continue\"]")
	public WebElement Pay_Continu;
	@FindBy(how = How.XPATH, using = "//*[@id=\"silentOrderPostForm\"]/div[1]/fieldset[1]/div/div[1]/label")
	public WebElement Pay_Continu_one;
	@FindBy(how = How.XPATH, using = "//*[@id=\"orderSummaryReview_placeOrder\"]")
	public WebElement Place_order;
	@FindBy(how = How.XPATH, using = "//*[@id=\"placeOrderForm1\"]/label")
	public WebElement Terms_conditions;
	@FindBy(how = How.XPATH, using = "//*[@id=\"mainContent\"]/div[1]/div[1]/div[1]/table/tbody/tr[2]/td[2]")
	public WebElement Order_number;
	@FindBy(how = How.XPATH, using = "//*[@id=\"mainContent\"]/div[1]/div[1]/div[1]/table/tbody/tr[3]/td[2]")
	public WebElement Order_date;
	@FindBy(how = How.XPATH, using = "//span[@class='accessoriesCategoriesTitle searchBoxLables']")
	public WebElement Browse_category;
	@FindBy(how = How.XPATH, using = "//div[@id='ChargersBatteries']/ul[@class='SearchTabData']/li[1]//a")
	public WebElement Browse_category_select;
	@FindBy(how = How.XPATH, using = "//*[@id='js-ResultsArea']/span[2]")
	public WebElement Browse_category_title;
	@FindBy(how = How.XPATH, using = "//h3[@class='accessoriesList-title qa_product_title']")
	public WebElement Browse_product;
	@FindBy(how = How.XPATH, using = "//button[@id='addToCart']")
	public WebElement Browse_product_add;

	// 18404
	@FindBy(how = How.XPATH, using = "//div/div[@class='sidebarNav-heading']")
	public WebElement Servers_small;
	@FindBy(how = How.XPATH, using = "//*[@id=\"mainContent\"]/div[1]/ol")
	public WebElement Servers_node_product;
	@FindBy(how = How.XPATH, using = "//a[@class='seriesListings-footer-button button-called-out button-full']")
	public WebElement Servers_Learn;
	@FindBy(how = How.XPATH, using = "//*[@id=\"addToCartButtonTop\"]")
	public WebElement Servers_Customise;
	@FindBy(how = How.XPATH, using = "//h3[@class='cart-items-heading']")
	public WebElement Cart_empty;
	@FindBy(how = How.XPATH, using = "//*[@id=\"tab-a-nav-currentmodels\"]")
	public WebElement Current_models;

	// 17627
	@FindBy(how = How.XPATH, using = "//a[@class='seriesListings-footer-button button-called-out-alt']")
	public WebElement Learnmore;
	@FindBy(how = How.XPATH, using = "//*[@id=\"cboxLoadedContent\"]/div/div")
	public WebElement popup;

	// 17628
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/div/div/a[contains(@href,'tablets') or contains(@href,'TABLETS')]/h3")
	public WebElement Tablets_First;
	@FindBy(how = How.XPATH, using = "//div[contains(@onclick,'Tablets')]/div[@class='megaMenu_explore']")
	public WebElement Tablets_First_Last;
	@FindBy(how = How.XPATH, using = "//div[@class='brandListings-footer']/a[@class='brandListings-footer-button button-called-out button-full']")
	public WebElement View_serices;
	@FindBy(how = How.XPATH, using = "//div[@class='seriesListings-footer']/a[@class='seriesListings-footer-button button-called-out button-full']")
	public WebElement Learn_more;
	@FindBy(how = How.XPATH, using = "//*[@id=\"mainContent\"]/div[5]/ol")
	public WebElement Product_list;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li//div[@class='megaMenu_mainSection active']/ul[@class='megaMenu_mainSection_list']")
	public WebElement Tablets_all;

	// 17360
	@FindBy(how = How.XPATH, using = "//input[@id='footerSignUp']")
	public WebElement Footer_signup;
	@FindBy(how = How.XPATH, using = "//dl[@class='cta-price']/dt[@class='cta-extra-info']")
	public WebElement Starting;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-nav-currentmodels']/span[@class='js-navText']")
	public WebElement Models;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-customize']/span")
	public WebElement Models_old;
	@FindBy(how = How.XPATH, using = "//h2[@class='tabbedBrowse-title singleModelTitle']")
	public WebElement Models_other;
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-productListing']/div[@class='tabbedBrowse-productListing-header']")
	public WebElement Models_sum;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-nav-reviews']/span[@class='js-navText']")
	public WebElement Product_Reviews;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-nav-features']/span[@class='js-navText']")
	public WebElement Product_Features;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-nav-techspec']/span[@class='js-navText']")
	public WebElement Product_Tech_specs;
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-features-featureText']")
	public WebElement Product_Features_content;
	@FindBy(how = How.XPATH, using = "//*[@id='tab-features']/div/div/section/div[2]/figure/img")
	public WebElement Product_Features_img;
	@FindBy(how = How.XPATH, using = "//li[@id='tab-li-techspec']//h2/span")
	public WebElement Product_Tech_specs_title;
	@FindBy(how = How.XPATH, using = "//table[@class='techSpecs-table']")
	public WebElement Product_Tech_specs_content;
	@FindBy(how = How.XPATH, using = "//*[@id='tab-customize']/h2/span")
	public WebElement Product_model_title;
	@FindBy(how = How.XPATH, using = "//li[@class='tabbedBrowse-productListing-container only-allow-small-pricingSummary']")
	public WebElement Product_model_columns;
	@FindBy(how = How.XPATH, using = "//dd[contains(@class,'saleprice')]")
	public WebElement Product_model_price;
	@FindBy(how = How.XPATH, using = "//input[@class='20JACTO1WWENAU0']")
	public WebElement Product_model_op;
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-productListing-featureList featureList-bulleted featureList-linedRows']")
	public WebElement Product_model_deals;
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-productListing-footer']")
	public WebElement Product_custiome;
	@FindBy(how = How.XPATH, using = ".//*[@id='my-account-saved-quote-status']")
	public WebElement quotePage_QuoteStatus;
	@FindBy(how = How.XPATH, using = ".//*[@ data-title='Part Number']/span")
	public WebElement quotePage_PartNumber;
	@FindBy(how = How.XPATH, using = ".//*[@ data-title='Quantity']")
	public WebElement quotePage_Quantity;
	@FindBy(how = How.XPATH, using = ".//*[@ data-title='Line Price']")
	public WebElement quotePage_TotalPrice;
	@FindBy(how = How.XPATH, using = "//span[@class='icon-cus']")
	public WebElement Custiome;

	// 18058

	@FindBy(how = How.XPATH, using = "(//ul[contains(@class,'general_Menu')]//a[contains(@class,'hasNoChildren') and contains(@href,'my-account')]/div)[2]")
	public WebElement saveCart_MyAccount;

	// 18570
	@FindBy(how = How.XPATH, using = "//div[@class='hero-productDescription-body mediaGallery-productDescription-body']")
	public WebElement Product_description;
	@FindBy(how = How.XPATH, using = "//section[1]//div[@class='tabbedBrowse-features-featureText']/p")
	public WebElement Product_feature;
	@FindBy(how = How.XPATH, using = "//*[@id=\"BVRRContainer\"]//div[1]/h2")
	public WebElement Product_reviewdesc;
	@FindBy(how = How.XPATH, using = "//*[@id=\"tab-techspec\"]//tr[27]/td[2]/div")
	public WebElement Product_tech;
	@FindBy(how = How.XPATH, using = "//*[@id=\"CTO_expandCollapse\" and @class='expand-all qa_configuratorExpand/Collapse collapsed']")
	public WebElement Product_customize_add;
	@FindBy(how = How.XPATH, using = "//*[@id=\"CTO_expandCollapse\"]")
	public WebElement Product_customize_plus;
	@FindBy(how = How.XPATH, using = "//h4[@class='footer-paymentOptions-title']")
	public WebElement Product_customize_bottom;
	@FindBy(how = How.XPATH, using = "//dl[@class='cta-price']/dd")
	public WebElement Product_price;
	@FindBy(how = How.XPATH, using = "//*[@id=\"tab-features\"]//section[1]/div[2]/figure/img")
	public WebElement Product_img;
	@FindBy(how = How.XPATH, using = "//div[@class='checkboxText']")
	public WebElement Product_checktext;

	@FindBy(how = How.XPATH, using = "//button[@class='button-called-out signInForm-submitButton']")
	public WebElement Product_USEPPtton;

	// 17665
	@FindBy(how = How.XPATH, using = "(//a[@class='breadcrumb-item'])[last()]")
	public WebElement PDP_previousLevel;
	@FindBy(how = How.XPATH, using = "//dd[contains(@class,'pricingSummary')]/../../../../div[1][not(contains(.,'Out of Stock'))]/..//a[contains(@class,'button')]")
	public WebElement series_validLearnMore;
	@FindBy(how = How.XPATH, using = "(//div[@class='promotedOptions']//input[not(contains(@value,':'))])[1]")
	public WebElement PLP_firstPromotedOption;
	@FindBy(how = How.XPATH, using = "(//div[@class='promotedOptions'])[1]/../..//button")
	public WebElement PLP_firstAddToCartWithProOpt;
	@FindBy(how = How.XPATH, using = "(//button[contains(@class,'product_detail_pages_models_form_submit')])[1]")
	public WebElement PLP_firstAddToCartWithoutProOpt;
	@FindBy(how = How.XPATH, using = "//meta[@name='subseriesPHcode']")
	public WebElement Meta_subseriesPHcode;

	// 18078
	@FindBy(how = How.XPATH, using = "//div[@class='price-calculator-cart-items' or @class='cart-item-pricing-and-quantity']//*[contains(@class,'cart-summary-pricing-webPrice-label') or @class='' or not(@class) and text()]")
	public List<WebElement> Cart_itemPriceList;
	@FindBy(how = How.XPATH, using = "//p[contains(@class,'cart-item-partNumber')]//span[not(@class)]")
	public List<WebElement> Cart_itemNumberList;

	// 18077
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'price-change-message')]/span")
	public WebElement savedCart_priceChangeMessage;
	@FindBy(how = How.XPATH, using = "//*[@id='bodywrapinner']/div[2]/div[1]/div/div[2]/dl[2]")
	public WebElement savedCart_oldPrice;

	// 18046
	@FindBy(how = How.XPATH, using = "//ul[@class='checkout-review-item-configurationDetails']/li")
	public List<WebElement> Summary_ConfigDetailsList;
	@FindBy(how = How.XPATH, using = "//table[@class='mtmFeature']/tbody/tr")
	public List<WebElement> ThankYou_ConfigDetailsList;
	// "//table[@calss='mtmFeature']/tbody/tr/td")
	@FindBy(how = How.XPATH, using = "//div[@id='mobileConfig']/h3/span[2]")
	public List<WebElement> Cart_ConfigDetailsList;
	@FindBy(how = How.XPATH, using = "//h3[contains(@class,'cart-item-configurationDetails-heading')]")
	public List<WebElement> Cart_ConfigDetailsListOldUI;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'expandableConDetailsBtn')]")
	public WebElement Summary_ConfigDetailsNewUI;
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber']/span")
	public WebElement CartPage_OnlyPartnum;
	@FindBy(how = How.XPATH, using = "//dl[@class='checkout-review-item-partNumber']/span")
	public WebElement Checkout_AddedItemPartnum;
	@FindBy(how = How.XPATH, using = "//div[@class='item-partNo']")
	public WebElement Checkout_AddedItemPartnumNewUI;
	@FindBy(how = How.XPATH, using = "//tbody[@class='checkout-confirm-orderSummary-table-content']/tr[1]/td/a")
	public WebElement ViewOrders_OrderNumber;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'cart-item-configurationDetails-wrapper')]/ul")
	public List<WebElement> OrdersDetail_ConfigDetailsList;
	@FindBy(how = How.XPATH, using = "//span[@class='continue_to_review']")
	public WebElement Pay_ContinuNewUI;
	@FindBy(how = How.XPATH, using = "//li[@class='redesign-header-mobile-icon cart gen_menu']/a")
	public WebElement Payment_NewCartNewUI;

	// 18380
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'subComingSoonMsg')]/strong")
	public WebElement Commingsoon;
	@FindBy(how = How.XPATH, using = "//button[@class='cta-group-action notifyMeBtn']")
	public WebElement Notifyme;

	// 22337
	@FindBy(how = How.XPATH, using = "//label[@for='PaymentTypeSelection_IGF']")
	public WebElement IGF_payment;

	// partsales
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']/div[3]/div[3]/div[4]/ul/li[2]")
	public WebElement OrderDetails_Payment;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']/div[3]/div[3]/div[3]/ul/li[2]")
	public WebElement OrderDetails_BillingAddress;
	@FindBy(how = How.XPATH, using = ".//*[@id='mainContent']/div[3]/div[3]/div[1]/ul/li[2]")
	public WebElement OrderDetails_DeliverAddress;
	@FindBy(how = How.XPATH, using = "(.//*[contains(@class,'orderDetailSummery')]/div)[1]/br")
	public WebElement OrderDetails_DeliveryDate;
	@FindBy(how = How.XPATH, using = ".//*[contains(text(),'Trace my order in OVP')]")
	public WebElement OrderDetails_OVPLink;
	@FindBy(how = How.XPATH, using = "(//td[@class='total'])[1]")
	public WebElement OrderDetails_Total;
	@FindBy(how = How.XPATH, using = "(//td[@class='total'])[2]")
	public WebElement OrderDetails_Availablity;
	// 16968
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Save up to')]")
	public WebElement Save_upto;
	@FindBy(how = How.XPATH, using = ".//*[@id='editCartInSummayPage']")
	public WebElement partSales_EditCart;

	// 19418
	@FindBy(how = How.XPATH, using = "//a[@id='view-buyfromreseller']")
	public WebElement Buybuyfromreseller;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-nav-customTab-resellerTab']")
	public WebElement Buybuyfromresellertab;

	// 25941
	@FindBy(how = How.XPATH, using = "//dd[@class='checkout-orderSummary-price checkout-orderSummary-highlighted summary-shipping-cost']")
	public List<WebElement> Payment_CostPrice;
	@FindBy(how = How.XPATH, using = "//dt[contains(text(),'FEE')]/following-sibling::dd[1]")
	public WebElement Review_FEEPrice;
	@FindBy(how = How.XPATH, using = "//dt[contains(text(),'PST')]/following-sibling::dd[1]")
	public WebElement Review_PSTPrice;
	@FindBy(how = How.XPATH, using = "//dt[contains(text(),'GST')]/following-sibling::dd[1]")
	public WebElement Review_GSTPrice;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'GST')]/following-sibling::td[1]")
	public WebElement Thank_GSTPrice;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'FEE')]/following-sibling::td[1]")
	public WebElement Thank_FEEPrice;
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'PST')]/following-sibling::td[1]")
	public WebElement Thank_PSTPrice;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'FEE')]/following-sibling::div")
	public WebElement NewPayment_FEEPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'PST')]/following-sibling::div")
	public WebElement NewPayment_PSTPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'GST')]/following-sibling::div")
	public WebElement NewPayment_GSTPrice;
	@FindBy(how = How.XPATH, using = "//div/span[contains(text(),'FEE')]/../following-sibling::div")
	public WebElement NewReview_FEEPrice;
	@FindBy(how = How.XPATH, using = "//div/span[contains(text(),'PST')]/../following-sibling::div")
	public WebElement NewReview_PSTPrice;
	@FindBy(how = How.XPATH, using = "//div/span[contains(text(),'GST')]/../following-sibling::div")
	public WebElement NewReview_GSTPrice;

	// NA-27284

	@FindBy(how = How.ID, using = "useDeliveryAddress")
	public WebElement Payment_UseShippingAddress;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Parts & Accessories')]")
	public WebElement partSales_AccessoryMenu;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Parts Lookup')]/..//a[@href='/au/en/partslookup']")
	public WebElement partSales_DropDownLookUp;

	// 19762 locators for wlh site only
	@FindBy(how = How.XPATH, using = "//div[@class='brandListings-footer']//a")
	public List<WebElement> brandListings_viewSeries;
	@FindBy(how = How.XPATH, using = "//div[@class='seriesListings-footer']//a")
	public List<WebElement> seriesListings_mehrInfos;
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-productListing-footer']//div[@class='tabbedBrowse-productListing-button-container'][1]//button[@id='addToCartButtonTop']")
	public List<WebElement> PLP_addToCartButton;
	@FindBy(how = How.ID, using = "purchase_bicCode")
	public WebElement Payment_bicCode;
	@FindBy(how = How.ID, using = "purchase_IBAN")
	public WebElement Payment_IBAN;
	@FindBy(how = How.ID, using = "useDeliveryAddress")
	public WebElement Payment_useDeliveryAddress;
	@FindBy(how = How.XPATH, using = "//p[@class='cart-item-partNumber qa_Product _PartNumber']//span[last()]")
	public WebElement Cart_wlhCartItem;

	// 23977
	@FindBy(how = How.XPATH, using = "//li[@class='footer-navigation-links-list'][1]")
	public WebElement About_Lenovo;
	@FindBy(how = How.XPATH, using = "//li[@class='footer-navigation-links-list'][1]/ul/li[1]/a")
	public WebElement About_Lenovo_Unser;

	// 20654
	@FindBy(how = How.XPATH, using = "//input[@id='sb_form_q")
	public WebElement From_q;

	// 18598
	@FindBy(how = How.XPATH, using = "//img[@class='rollovercartItemImg no-margin']/@src")
	public WebElement Product_hreoimg;
	@FindBy(how = How.XPATH, using = "//div[@class='hero-productDescription-body mediaGallery-productDescription-body']")
	public WebElement Product_herodescription;
	@FindBy(how = How.XPATH, using = "//dt[@class='cta-extra-info']")
	public WebElement Product_start;
	@FindBy(how = How.XPATH, using = "//a[@id='view-customize']")
	public WebElement Product_cus;
	@FindBy(how = How.XPATH, using = "//div[@class='simple_disp-img']/img/@src")
	public WebElement Product_intellogo;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-customize']/span")
	public WebElement Product_model;
	@FindBy(how = How.XPATH, using = "//button[@id='addToCartButtonTop']")
	public WebElement Product_model_addtocart;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-reviews']/span")
	public WebElement Product_reviews;
	@FindBy(how = How.XPATH, using = "//*[@id=\"tab-reviews\"]/h2/span")
	public WebElement Product_reviews_title;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-features']/span")
	public WebElement Product_features;
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-features-featureText']")
	public WebElement Product_features_text;
	@FindBy(how = How.XPATH, using = "//*[@id=\"tab-features\"]/h2/span")
	public WebElement Product_features_title;
	@FindBy(how = How.XPATH, using = "//a[@id='tab-a-tech_specs']/span")
	public WebElement Product_tech_specs;
	@FindBy(how = How.XPATH, using = "//table[@class='techSpecs-table']")
	public WebElement Product_tech_content;
	@FindBy(how = How.XPATH, using = "//*[@id=\"tab-tech_specs\"]/h2/span")
	public WebElement Product_tech_title;
	@FindBy(how = How.XPATH, using = "//input[@id='footerSignUp']")
	public WebElement Product_sign_up;
	@FindBy(how = How.XPATH, using = "//li[@class='footer-navigation-links-list']")
	public WebElement Product_foot_about;
	@FindBy(how = How.XPATH, using = "//div[@class='footer-bottomBar-nav']")
	public WebElement Product_foot_print;

	// 23895
	@FindBy(how = How.XPATH, using = "//*[@id='longscroll-subseries']/div[1]/nav/span/span")
	public WebElement Product_serriesname;
	@FindBy(how = How.XPATH, using = "//h1[@class='desktopHeader']")
	public WebElement Product_sertitle;
	@FindBy(how = How.XPATH, using = "//div[@class='hero-productDescription-body mediaGallery-productDescription-body']")
	public WebElement Product_serdescription;

	@FindBy(how = How.XPATH, using = "//a[@id='signIn']")
	public WebElement order_Singin;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'signInForm-submitButton')]")
	public WebElement order_Singin_button;
	@FindBy(how = How.XPATH, using = "//div[@class='qas-address-check-msg-right-part']//input[@value='Individual' and @name='customertype']")
	public WebElement ValidateInfo_customertype;

	// 28038
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'activateASM')]")
	public WebElement Myaccount_telesale;
	@FindBy(how = How.XPATH, using = "//a[contains(@onclick,'window.print()')]")
	public WebElement Myaccount_print;

	// 26638
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'bv-rating-stars bv-rating-stars-off')]")
	public WebElement PlP_reveiew;
	@FindBy(how = How.XPATH, using = "(//span[contains(@class,'bv-rating-stars bv-rating-stars-off')])[1]")
	public WebElement Product_pdp_rate;
	@FindBy(how = How.XPATH, using = "//*[@id='tab-a-nav-reviews']")
	public WebElement PDP_review;
	@FindBy(how = How.XPATH, using = "//a[@class='bv-write-review-label bv-text-link bv-focusable bv-submission-button']")
	public WebElement PDP_review_jumplink;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'bv-mbox-breadcrumb-item')]")
	public WebElement PDP_popup;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'bv-rating-label')]")
	public WebElement PlP_ratcount;
	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'bv-content-secondary-ratings-bars')]")
	public WebElement PlP_redrating;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'bv-rating-label bv-text-link bv-focusable')]")
	public WebElement PlP_reviewcount;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'bv-filter-control-icon')]")
	public WebElement PlP_sort;
	@FindBy(how = How.XPATH, using = "(//span[contains(@class,'bv-dropdown-title')])[2]")
	public WebElement PlP_sort_rating;
	@FindBy(how = How.XPATH, using = "(//span[contains(@class,'bv-dropdown-title')])[1]")
	public WebElement PlP_sort_rating2;
	@FindBy(how = How.XPATH, using = "//li[@class='bv-dropdown-item bv-focusable '][2]/span")
	public WebElement PlP_sort_starts;
	@FindBy(how = How.XPATH, using = "//li[@class='bv-dropdown-item bv-focusable'][2]")
	public WebElement PlP_sort_starts2;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'bv-active-filter-button bv-focusable')]")
	public WebElement PlP_starts;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'bv-rating bv-text-link bv-popup-target bv-focusable')]/span")
	public WebElement PlP_Count;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'bv-read-review bv-focusable')]")
	public WebElement PlP_Count_read;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'bv-write-review bv-focusable bv-submission-button')]")
	public WebElement PlP_write_review;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'ChargersBatteries') or contains(@id,'Monitors') or contains(@id,'KeyboardsMice')]")
	public List<WebElement> Product_category;

	// 27440
	@FindBy(how = How.XPATH, using = "//input[@name='NB_CPU'][@checked='checked']/../label//img")
	public WebElement CTOConfig_CPUImage;

	// 28040
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'professional-laptops')]")
	public WebElement Professional_laptops;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'desktops')]")
	public WebElement Desktops;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'accessories')]")
	public WebElement Accessories;
	@FindBy(how = How.XPATH, using = "(//div[@class='title'])[1]")
	public WebElement Accessories_title;
	@FindBy(how = How.XPATH, using = "//div[@class='lnv-col no-padding']/div[@class='title']/h4")
	public WebElement Accessories_description;
	@FindBy(how = How.XPATH, using = "(//a[@class='link wd-fullSpecs'])[1]")
	public WebElement Accessories_fullSpecs;
	@FindBy(how = How.XPATH, using = "//table[@class='techSpecs-table']//th[1]")
	public WebElement Accessories_popup;
	@FindBy(how = How.XPATH, using = "//li[@class='minPrice']/span")
	public List<WebElement> Accessories_price;
	@FindBy(how = How.XPATH, using = "//div[@class='title']/h3/a")
	public List<WebElement> Accessories_titlePrice;
	@FindBy(how = How.XPATH, using = "//dl[@class='cta-price']/dd")
	public WebElement Accessories_pdptitlePrice;
	@FindBy(how = How.XPATH, using = "//span[@class='bv-rating-stars-container']")
	public WebElement Accessories_rate;
	@FindBy(how = How.XPATH, using = "//a[@onclick='shopNow(this)']")
	public WebElement Accessories_shopnow;
	@FindBy(how = How.XPATH, using = "//dd[@class='bv-rating-ratio']")
	public WebElement Accessories_pdprate;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'icon-atc')]")
	public WebElement Accessories_addTocart;
	@FindBy(how = How.XPATH, using = "//span[@class='button-text']")
	public WebElement Accessories_continue;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button add-to-cart qa_AddToCartButton')]")
	public WebElement Accessories_custiomz_AddToCart;
	@FindBy(how = How.XPATH, using = "//div[@class='acc-center']")
	public WebElement Accessories_Merchandising;
	@FindBy(how = How.XPATH, using = "//li[@class='feature']")
	public WebElement Accessories_Inventory;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'legal-footer-cta-link')]")
	public WebElement Accessories_legal;
	@FindBy(how = How.XPATH, using = "//a[@title='Close this']")
	public WebElement Accessories_legal_close;
	@FindBy(how = How.XPATH, using = "//a[@class='dock four-width'][2]/div")
	public WebElement Accessories_tablets;

	@FindBy(how = How.XPATH, using = "//dd[@class='saleprice aftercoupon pricingSummary-details-final-price qa_webPrice_after_eCoupon']")
	public WebElement Deals_price;
	@FindBy(how = How.XPATH, using = "//dd[@class='saleprice pricingSummary-details-final-price qa_webPrice_after_instantSavings']")
	public WebElement Deals_usprice;
	@FindBy(how = How.XPATH, using = "//a[contains(.,'Shop Now')]")
	public WebElement Deals_shopnowbutton;

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'newDealsBox')]")
	public WebElement Deals_PartOneSelectOne;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'deals')][contains(@class,'item')]//a")
	public WebElement Deals_PartOneSelectTwo;
	@FindBy(how = How.XPATH, using = "")
	public WebElement Deals_PartOneSelectThree;

	// 25411
	@FindBy(how = How.XPATH, using = "//div[@class='eCoupon-wrapper']/div[@class='eCoupon-message']")
	public WebElement HomePage_EcouponMessage;
	@FindBy(how = How.XPATH, using = "//span[@class='price-calculator-cart-summary-couponCode']")
	public WebElement NewCart_Ecoupon;
	@FindBy(how = How.XPATH, using = "//span[@class='price-calculator-cart-summary-couponSaved']")
	public WebElement NewCart_EcouponSavedPrice;

	// 26649
	@FindBy(how = How.XPATH, using = "//div[@class='merch-tagLabel-ribbon modelCust__ribbon tagLabel-orange']")
	public WebElement Label;
	@FindBy(how = How.XPATH, using = "//div[@class='merch-tagLabel-ribbon tagLabel-orange']")
	public WebElement Product_Label;
	@FindBy(how = How.XPATH, using = "//div[@class='merch-tagLabel-ribbon modelCust__ribbon tagLabel-blue']")
	public WebElement Label_blue;
	@FindBy(how = How.XPATH, using = "//div[@class='merch-tagLabel-ribbon tagLabel-blue']")
	public WebElement Product_Label_blue;

	@FindBy(how = How.XPATH, using = "//dd[@class='aftercoupon pricingSummary-details-final-price']")
	public WebElement Deals_nzprice;

	// 26709
	@FindBy(how = How.XPATH, using = "//input[@id='serial-number']")
	public WebElement Warranty_input;
	@FindBy(how = How.XPATH, using = "//div[@id='warrantyContinueButton']")
	public WebElement Warranty_continue;
	@FindBy(how = How.XPATH, using = "//a[@class='textLink search-modelFinder-trigger']")
	public WebElement Warranty_numberlink;
	@FindBy(how = How.XPATH, using = "//h2[contains(@class,'tabbedBrowse-title')]")
	public WebElement Warranty_numberlinkresult;
	@FindBy(how = How.XPATH, using = "//div[@id='serialNumberLabel']")
	public WebElement Warranty_serialNumberLabel;
	@FindBy(how = How.XPATH, using = "(//h1[@class='categoryDescription-heading columnSlider-heading'])[1]")
	public WebElement Warranty_business;
	@FindBy(how = How.XPATH, using = "(//h1[@class='categoryDescription-heading columnSlider-heading'])[2]")
	public WebElement Warranty_business2;
	@FindBy(how = How.XPATH, using = "//button[@class='warrantylookup-cta']")
	public WebElement Warranty_inputbutton;
	@FindBy(how = How.XPATH, using = "//div[@class='accordionHeading']")
	public WebElement Warranty_popup;
	@FindBy(how = How.XPATH, using = "//div[@id='banner-upgrade']")
	public WebElement Warranty_buttoncajp;
	@FindBy(how = How.XPATH, using = "//input[@id='serialNumber']")
	public WebElement Warranty_serialNumber;

	// 28209
	@FindBy(how = How.XPATH, using = "//div[@id='Accessories' or @data-tabname='Accessories']//span[@class='section-title']")
	public List<WebElement> PB_accessorySectionTitle;
	@FindBy(how = How.XPATH, using = "//div[@id='Accessories' or @data-tabname='Accessories']//span[@class='section-title']/..")
	public List<WebElement> PB_accessorySectionTitlePreLevel;
	@FindBy(how = How.XPATH, using = "//header[@id='stackableWarranty']")
	public WebElement PB_stackableWarrantyHeader;
	@FindBy(how = How.XPATH, using = "//header[@id='Warranty']//h3[contains(@class,'configuratorHeader')]")
	public WebElement PB_newWarrantyHeader;
	@FindBy(how = How.XPATH, using = "//*[@id='Warranty' or @id='stackableWarranty']//button[contains(@class,'sectionExpandButton')]")
	public WebElement PB_newWarrantyChangeBtn;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'warranty')]//div[@class='group-title']")
	public List<WebElement> PB_newWarrantyGroup;
	@FindBy(how = How.XPATH, using = "//div[@class='stackableWarrantyContainer']//div[@class='sectionHeader']//span[not(@class)]")
	public List<WebElement> PB_newWarrantySectionHeader;
	@FindBy(how = How.XPATH, using = "//section[contains(@id,'warrServices')]//div[contains(@class,'stackableSection') and not(@id) and @style]//div[@class='stackableHeader']/span")
	public List<WebElement> PB_stackableWarrServicesGroup;
	@FindBy(how = How.XPATH, using = "//section[contains(@id,'upgradeWarranty')]//div[contains(@class,'stackableSection') and not(@id)]//div[@class='stackableHeader']/span")
	public List<WebElement> PB_stackableUpgradeWarrantyGroup;// groupText
	@FindBy(how = How.XPATH, using = "//section[contains(@id,'upgradeWarranty')]//div[contains(@class,'stackableSection') and not(@id)]//div[@class='stackableHeader']/span/../..")
	public List<WebElement> PB_stackableUpgradeWarrantyGroupCode;// groupCode
	@FindBy(how = How.ID, using = "warrServices")
	public WebElement PB_stackableWarrServices;
	@FindBy(how = How.ID, using = "upgradeWarranty")
	public WebElement PB_stackableUpgradeWarranty;

	// 17695
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-item') and contains(@class,'visible') and not(contains(@class,'active'))]//span[@class='label-text']/../span[@class='debug-id']")
	public List<WebElement> CTO_unselectedCV;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'group-item') and contains(@class,'visible') and contains(@class,'active')]//span[@class='label-text']/../span[@class='debug-id']")
	public List<WebElement> CTO_selectedCV;
	@FindBy(how = How.XPATH, using = "//div[@id='Accessories']//div[@class='expandableHeading section-header']")
	public List<WebElement> PB_expandableSectionHeader;
	@FindBy(how = How.XPATH, using = ".//*[@id='Accessories']//div[contains(@data-source-id,'section_')]//ul/li[1]//div[contains(@class,'option-text ')]")
	public List<WebElement> PB_optionText;

	// na-27240
	@FindBy(how = How.XPATH, using = "")
	public WebElement Facet_RangeName;
	@FindBy(how = How.XPATH, using = "//h3[contains(text(),'Price') or contains(text(),'価格')]")
	public WebElement Facet_PriceFacet;
	@FindBy(how = How.XPATH, using = "//ol[@data-name='Price' or @data-name='価格' ]/li[1]//input")
	public WebElement Facet_PriceFirstRange;

	// 19976
	@FindBy(how = How.ID, using = "requieroFacturaFiscal")
	public WebElement Payment_RequieroFacturaFiscal;
	@FindBy(how = How.XPATH, using = "//div[@class='orderBox billing']")
	public WebElement orderDetail_RFC;

	// 27059
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group')]/div/div/div/ul/li/label/div/p[@class='del-price product-builder-hide']/../../input[not (@checked) and (@type='checkbox')]")
	public List<WebElement> NewPB_OptionCheckboxWithoutDiscount;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group')]/div/div/div/ul/li/label/div/p[@class='del-price']/../../input[not (@checked) and (@type='checkbox')]")
	public List<WebElement> NewPB_OptionCheckboxWithDiscount;
	@FindBy(how = How.XPATH, using = "//div[@class='configure-price-wrapper']/div[@id='w-price']")
	public WebElement NewPB_AsConfiguredPrice;
	@FindBy(how = How.XPATH, using = "//dd[@class='saleprice pricingSummary-priceList-value']/span[@class='summary-price']")
	public WebElement PB_SavedPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group')]/ul/li/div/del[@class='option-origin-price product-builder-hide']/../../input[not (@checked) and (@type='checkbox')]")
	public List<WebElement> PB_OptionCheckboxWithoutDiscount;
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'group')]/ul/li/div/del[@class='option-origin-price']/../../input[not (@checked) and (@type='checkbox')]")
	public List<WebElement> PB_OptionCheckboxWithDiscount;

	// 25040
	@FindBy(how = How.XPATH, using = ".//span[contains(@class,'FindYourSystemLabel')]")
	public WebElement AccessoriesPage_FindForMySystem;
	@FindBy(how = How.XPATH, using = ".//*[@id='machinetypeForm']/div/button")
	public WebElement AccessoriesPage_SelectProduct;
	@FindBy(how = How.XPATH, using = ".//*[@id='machinetypeForm']/div/div/ul/li[2]/a/span")
	public WebElement AccessoriesPage_FirstProduct;
	@FindBy(how = How.XPATH, using = ".//*[@id='extraOptions-subSelection']/div/button")
	public WebElement AccessoriesPage_SelectMT;
	@FindBy(how = How.XPATH, using = ".//*[@id='extraOptions-subSelection']/div/div/ul/li[2]/a")
	public WebElement AccessoriesPage_FirstMT;
	@FindBy(how = How.XPATH, using = ".//*[@id='resultsList']/div[contains(@class,'facetedResults-item')]")
	public List<WebElement> AccessoriesPage_AccessoryFilterList;

	// 19794
	@FindBy(how = How.XPATH, using = ".//*[@id='addToCartFormTop']//a[contains(@class,'cta-button')]")
	public WebElement Product_CTALink;
	@FindBy(how = How.XPATH, using = ".//*[contains(@class,'subseriesHeader')]//a[contains(@class,'cta-button')]")
	public WebElement Product_CTAButtonlink;
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'View current models')]")
	public WebElement Product_ViewCurrentModelTab;

	// 27223
	@FindBy(how = How.XPATH, using = "//a[@class='qas-address-check-msg-editLink']")
	public WebElement ValidateInfo_Edit;
	@FindBy(how = How.XPATH, using = "//span[@class='qas-address-check-msg-title-text1']")
	public WebElement ValidateInfo_message1;
	@FindBy(how = How.XPATH, using = "//div[@class='qas-address-check-msg-address-links']/a[1]")
	public WebElement ValidateInfo_AddressCheckLink;
	@FindBy(how = How.XPATH, using = "//input[@class='address_button']")
	public WebElement ValidateInfo_ConfirmAddressButton;
	@FindBy(how = How.XPATH, using = "//div[@class='qas-address-check-msg-interaction-required']/div/span[@class='text-style-2']/label")
	public List<WebElement> ValidateInfo_SuggestedAddress;
	@FindBy(how = How.XPATH, using = "//div[@class='qas-address-check-msg-street-matches-lines']/a")
	public List<WebElement> ValidateInfo_MatchesAddress;
	@FindBy(how = How.XPATH, using = "//input[@id='inputLine1AndLine2Street']")
	public WebElement ValidateInfo_InputAddress;

	// 28743
	@FindBy(how = How.XPATH, using = "//meta")
	public List<WebElement> MetaList;
	@FindBy(how = How.XPATH, using = "//meta[@name='description']")
	public WebElement DescriptionMeta;

	@FindBy(how = How.XPATH, using = "//*[@id='new-test-url-input']")
	public WebElement GEOTool_InputURL;
	@FindBy(how = How.XPATH, using = "//*[@id='new-test-submit-button']")
	public WebElement GEOTool_Runtest;
	@FindBy(how = How.XPATH, using = "//*[@id='results-cell']//span[contains(.,'Product')]")
	public WebElement GEOTool_Product;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'preview-0')]")
	public WebElement GEOTool_Product_Review;
	@FindBy(how = How.XPATH, using = "//div[text()='Price']")
	public WebElement GEOTool_Product_Price;
	@FindBy(how = How.XPATH, using = "//div[text()='Availability']")
	public WebElement GEOTool_Product_Avaliable;

	// 19762
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'seriesPreview-viewLink seriesPreview-fakeLink')]")
	public WebElement gointoPDP;
	@FindBy(how = How.XPATH, using = "//*[@id=\"order_history\"]/tbody/tr/td[2]")
	public WebElement orderNumbers;
	@FindBy(how = How.XPATH, using = "//div[@class='signInModule signInModule-signIn'][5]/div[@class='signInModule-content']")
	public WebElement adminOrderHistory;
	@FindBy(how = How.XPATH, using = "//button[@id='approverDecisionReject']")
	public WebElement orderReject;
	@FindBy(how = How.XPATH, using = "//input[@id='approvalPoDate']")
	public WebElement approvalPoDate;
	@FindBy(how = How.XPATH, using = "//input[@id='poNumber']")
	public WebElement poNumber;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'legal-footer')]")
	public WebElement LegalFooter;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'afiinity_welcomemessge')]")
	public WebElement Affinity_WelcomeMessage;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'afiinity_welcomemessge')]/span")
	public WebElement Affinity_UnitMessage;
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'afiinity_graymemessge')]")
	public WebElement Affinity_GrayWelcomeMessage;
	@FindBy(how = How.XPATH, using = "//*[@id='configuratorItem-mtmTable-text']/h4[text()='Processor']/../p")
	public WebElement PDPPage_ProcessClassficationMessage;
	@FindBy(how = How.XPATH, using = ".//*[@id='tab-li-models']/h2")
	public WebElement PDPPage_SummaryOverrideMessage;

	@FindBy(how = How.XPATH, using = "//span[contains(@class,'notPassDisplayRule-redirectMsg-content')]")
	public WebElement Product_EOLMessage;
	@FindBy(how = How.XPATH, using = ".//*[@id='accessoryProductListing']/ol/li")
	public List<WebElement> Accessory_ResultList;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'subseries-show-diff')]/span[1]")
	public WebElement FeaturesDiffButton;
	@FindBy(how = How.XPATH, using = ".//div[contains(@class,'tabbedBrowse-productListing-featureList')]/dl/dd[contains(@style,'rgb(245, 240, 208)')]")
	public List<WebElement> FeaturesDiffColor;
	
	//DEALS
	@FindBy(how = How.XPATH, using = ".//li[contains(@class,'seriesListings-itemContainer')]")
	public List<WebElement> DEALS_ProductsList;
	@FindBy(how = How.XPATH, using = ".//ul[contains(@class,'promotedOptions')]/li/div[contains(@class,'checkbox')]")
	public List<WebElement> DEALS_ProductsPlusBounsOffer;
	@FindBy(how = How.XPATH, using = ".//*[@id='tab-li-currentmodels']//ol/li")
	public List<WebElement> Subseries_ProductList;
	


	// NA28051
	@FindBy(how = How.ID, using = "CTO_orderTotal")
	public WebElement CTO_yourPriceLabel;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'you-save-label')]")
	public WebElement CTO_youSavaLabel;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'you-save-price')]")
	public WebElement CTO_youSavaPrice;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'useCoupon-label')]")
	public WebElement CTO_couponLabel;
	@FindBy(how = How.XPATH, using = "//lable[contains(@class,'useCoupon-code')]")
	public WebElement CTO_couponCode;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'useCoupon-price')]")
	public WebElement CTO_couponPrice;
	@FindBy(how = How.ID, using = "w-label")
	public WebElement CTO_asConfigLabel;

	
	@FindBy(how = How.XPATH, using = "//input[@id='pinCode']")
	public WebElement cart_pincode;
	@FindBy(how = How.XPATH, using = "//div[@id='ajaxCallResponseMessage']")
	public WebElement cart_pincodeMessage;
	@FindBy(how = How.XPATH, using = "//input[@id='cart-summary-pinCode-button']")
	public WebElement cart_pincodeCheck;


	//NA28180
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'PaymentTypeSelection_COD')]")
	public WebElement payment_COD;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'cod_payment_pop_confirm_button')]")
	public WebElement payment_CODAccept;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'cod_payment_pop_cancel_button')]")
	public WebElement payment_CODCancel;
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'PaymentTypeSelection')][1]")
	public WebElement payment_MethodList1;
	@FindBy(how = How.XPATH, using = "//input[@id = 'codTermsCheckBox']")
	public WebElement payment_CODCheckbox;

	//23077
	@FindBy(how = How.XPATH, using = "//select[@id='manualprice.calculator.customerprofile']")
	public WebElement CartPage_PriceProfile;
	@FindBy(how = How.XPATH, using = "//select[@id='manualprice.calculator.customerstate']")
	public WebElement CartPage_PriceState;
	@FindBy(how = How.XPATH, using = "//select[@id='customerFiscalType']")
	public WebElement NewShippingPage_PriceProfile;
	@FindBy(how = How.XPATH, using = "//select[@id='address.idCustFiscalType']")
	public WebElement ShippingPage_PriceProfile;
	@FindBy(how = How.XPATH, using = "//select[@id='state']")
	public WebElement ShippingPage_PriceState;
	

	//28145
	@FindBy(how = How.XPATH, using = "//input[@id='partNumQuery']")
	public WebElement partNumQuery;

	@FindBy(how = How.XPATH, using = "//*[@id='quote_button' and contains(@class,'checkout')]")
	public WebElement Payment_RequestQuoteBtn;

	@FindBy(how = How.XPATH, using = "//p[@class='checkout-shoppingCart-previewSubtitle'])[1]")
	public WebElement PartNumberOnPayment;
	@FindBy(how = How.XPATH, using = "//dd[contains(@class,'orderSummary-pricingTotal')]")
	public WebElement PriceOnPayment;
	
	//ACCT26
	@FindBy(how = How.XPATH, using = ".//*[@id='loginForm']//div[contains(@class,'register')]")
	public WebElement login_Register;
	
	//ACCT34
	@FindBy(how = How.XPATH, using = "//button[@name='asmcheckButton']")
	public WebElement asm_check;
	
	//Shop223
	@FindBy(how = How.XPATH, using = "//td[@class='checkout-confirm-orderSummary-table-productPrice'][1]")
	public WebElement QuoteConfirmation_subPrice;
	@FindBy(how = How.ID, using = "quoteApprover")
	public WebElement requestQuote_quoteApprover;
	@FindBy(how = How.ID, using = "emailQuoteButton")
	public WebElement ASM_emailQuote;
	@FindBy(how = How.ID, using = "quote-email-to-addresses")
	public WebElement ASM_emailTo;
	@FindBy(how = How.ID, using = "quote-send-email")
	public WebElement ASM_sendEmail;
	@FindBy(how = How.XPATH, using = "//div[@class='manual-email-msg']")
	public WebElement ASM_emailQuoteMsg;
	

	@FindBy(how = How.XPATH, using = "//h1[contains(@class,'desktopHeader') and @itemprop='name']")
	public WebElement PDP_mktName;
	@FindBy(how = How.XPATH, using = ".//div[contains(@class,'hero-productDescription-body')]")
	public WebElement PDP_DesLong;
	@FindBy(how = How.XPATH, using = ".//*[contains(@class,'tabbedBrowse-productListing-merchandising-label')]/div")
	public WebElement PDP_merchandisingTag;
	
	//Comm175
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'softwareTab')]/a")
	public WebElement PBP_SoftwareTag;
	@FindBy(how = How.XPATH, using = "//input[contains(@value,'PK')]")
	public WebElement Cart_PK;
	@FindBy(how = How.XPATH, using = "//a[contains(@href,'paypal')]")
	public WebElement Cart_paypal;
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'Amazon')]")
	public WebElement Cart_Amazon;
	
	//content193
	@FindBy(how = How.XPATH, using = "//div[@class='country-list']//a[contains(.,'Ireland')]")
	public WebElement Ireland;
	@FindBy(how = How.XPATH, using = "//p[@class='selected-country']/span[@class='icon-l-down']")
	public WebElement SelectedCountry;
	@FindBy(how = How.XPATH, using = "//p[@class='selected-language']/span[@class='icon-l-down']")
	public WebElement SelectedLanguage;
	@FindBy(how = How.XPATH, using = "//div[@class='language-list']//a[contains(.,'English')]")
	public WebElement English;
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-default dropdown-toggle part-search-select']/span[@class='caret']")
	public WebElement Caret;
	@FindBy(how = How.XPATH, using = "//a[@id='aPartsInfo']/span[@class='select-option-long']")
	public WebElement APartsInfo;
	@FindBy(how = How.XPATH, using = "//input[@id='partNumQuery']")
	public WebElement PartNumQuery;
	@FindBy(how = How.XPATH, using = "//span[@id='partNumberSearchbutton']")
	public WebElement PartNumberSearchbutton;
	@FindBy(how = How.XPATH, using = "//span[@class='span-9']/label[@class='icon-cart cart-blue']")
	public WebElement IconCart;
	@FindBy(how = How.XPATH, using = "//button[@class='btn-green']")
	public WebElement Viewmycart;
	@FindBy(how = How.XPATH, using = "//div[@class='btn-Checkout']/span")
	public WebElement BtnCheckout;
	@FindBy(how = How.XPATH, using = "//h3[contains(@class,'cart-summary-heading qa_OrderSummarySection')]")
	public WebElement CartSummary;
	@FindBy(how = How.XPATH, using = "//*[@id='digital-river-form']")
	public WebElement Digitalriver;
	@FindBy(how = How.XPATH, using = "//a[@id='dr-lenovo-checkout']")
	public WebElement Drlenovocheckout;
	@FindBy(how = How.XPATH, using = "//*[@id='dr_cartLink']/a/span")
	public WebElement DrcartLink;
	@FindBy(how = How.XPATH, using = "//*[@id='email']")
	public WebElement EmailAddress;
	@FindBy(how = How.XPATH, using = "//*[@id='billingName1']")
	public WebElement FirstName;
	@FindBy(how = How.XPATH, using = "//*[@id='billingName2']")
	public WebElement LastName;
	@FindBy(how = How.XPATH, using = "//*[@id='billingAddress1']")
	public WebElement AddressLine1;
	@FindBy(how = How.XPATH, using = "//*[@id='billingCity']")
	public WebElement BillingCity;
	@FindBy(how = How.XPATH, using = "//*[@id='billingPostalCode']")
	public WebElement ZipCode;
	@FindBy(how = How.XPATH, using = "//*[@id='billingPhoneNumber']")
	public WebElement PhoneNumber;
	@FindBy(how = How.XPATH, using = "//*[@id='dr_creditCardRadioSelect']/label/strong")
	public WebElement drcreditCardRadioSelect;
	@FindBy(how = How.XPATH, using = "//*[@id='ccNum']")
	public WebElement DRCardNumber;
	@FindBy(how = How.XPATH, using = "//*[@id='ccMonth']/option[5]")
	public WebElement Month;
	@FindBy(how = How.XPATH, using = "//*[@id='ccYear']/option[6]")
	public WebElement Year;
	@FindBy(how = How.XPATH, using = "//*[@id='checkoutButton']")
	public WebElement DRcheckoutButton;
	@FindBy(how = How.XPATH, using = "//*[@id='rtavContinueButton']")
	public WebElement rtavContinueButton;
	@FindBy(how = How.XPATH, using = "//*[@id='dr_orderNumber']")
	public WebElement drorderNumber;
	@FindBy(how = How.XPATH, using = "//*[@id='cardSecurityCode']")
	public WebElement cardSecurityCode;
	@FindBy(how = How.XPATH, using = "//*[@id='continueShopping']/a")
	public WebElement continueShopping;
	@FindBy(how = How.XPATH, using = "//input[@id='shoplogin_email']")
	public WebElement shoplogin_email;
	@FindBy(how = How.XPATH, using = "//input[@id='shoplogin_pwd']")
	public WebElement shoplogin_pwd;
	@FindBy(how = How.XPATH, using = "//div[@class='country-list']//a[contains(.,'France')]")
	public WebElement France;
	@FindBy(how = How.XPATH, using = "//div[@class='language-list']//a[contains(.,'Français')]")
	public WebElement Français;
	@FindBy(how = How.XPATH, using = "//div[@class='country-list']//a[contains(.,'Germany')]")
	public WebElement Germany;
	@FindBy(how = How.XPATH, using = "//div[@class='language-list']//a[contains(.,'Deutsch')]")
	public WebElement Deutsch;
	
	
	@FindBy(how = How.XPATH, using = "//div[@class='first-line']/span[@class='span-3']")
	public WebElement partNumber;
	@FindBy(how = How.XPATH, using = "//button[@class='btn-blue']")
	public WebElement btnBlueShopping;
	@FindBy(how = How.XPATH, using = "//select[@id='type']/option[@value=1]")
	public WebElement updateQuantity;
	@FindBy(how = How.XPATH, using = "//input[@id='partid']")
	public WebElement partid;
	@FindBy(how = How.XPATH, using = "//input[@id='country']")
	public WebElement country;
	@FindBy(how = How.XPATH, using = "//input[@id='quantity']")
	public WebElement quantity;
	@FindBy(how = How.XPATH, using = "//*[@id='updateBtn']")
	public WebElement updateBtn;
	@FindBy(how = How.XPATH, using = "//input[@id='quantity0']")
	public WebElement quantity0;
	@FindBy(how = How.XPATH, using = "//input[@id='QuantityProduct_0']")
	public WebElement QuantityProduct_0;
	@FindBy(how = How.XPATH, using = "//div[@class='cart-checkoutButtons']")
	public WebElement checkoutButtons;
	@FindBy(how = How.XPATH, using = "//select[@id='type']/option[@value=0]")
	public WebElement searchQuantity;
	@FindBy(how = How.XPATH, using = "//*[@id='searchBtn']")
	public WebElement searchBtn;
	@FindBy(how = How.XPATH, using = "//input[@type='password']")
	public WebElement threeDSpassword;
	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
	public WebElement threeDSsubmit;
	
	
	//content307
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'smb-hero-cta-content')]")
	public WebElement createAccountSmb;
	@FindBy(how = How.XPATH, using = "//input[@id='companyName']")
	public WebElement companyName;
	@FindBy(how = How.XPATH, using = "//input[@id='repId']")
	public WebElement repId;
	@FindBy(how = How.XPATH, using = "//input[@id='firstName']")
	public WebElement firstName;
	@FindBy(how = How.XPATH, using = "//input[@id='lastName']")
	public WebElement lastName;
	@FindBy(how = How.XPATH, using = "//input[@id='email']")
	public WebElement email;
	@FindBy(how = How.XPATH, using = "//input[@id='pwd']")
	public WebElement pwd;
	@FindBy(how = How.XPATH, using = "//input[@id='checkPwd']")
	public WebElement checkPwd;
	@FindBy(how = How.XPATH, using = "//select[@id='industry']/option[contains(text(),'Agriculture')]")
	public WebElement industryUs;
	@FindBy(how = How.XPATH, using = "//select[@id='companySize']/option[contains(text(),'1-9 employees')]")
	public WebElement companySizeUs;
	@FindBy(how = How.XPATH, using = "//input[@id='phoneticName']")
	public WebElement phoneticName;
	@FindBy(how = How.XPATH, using = "//select[@id='industry']//option[contains(text(),'不動産業')]")
	public WebElement industryJp;
	@FindBy(how = How.XPATH, using = "//select[@id='companySize']//option[contains(text(),'1-49')]")
	public WebElement companySizeJp;
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement submit;
	@FindBy(how = How.XPATH, using = "//a[@id='smb-login-button']")
	public WebElement loginSmb;
	@FindBy(how = How.XPATH, using = "//input[@id='login.email.id']")
	public WebElement loginEmail;
	@FindBy(how = How.XPATH, using = "//input[@id='login.password']")
	public WebElement loginPassword;
	@FindBy(how = How.XPATH, using = "//button[@class='button-called-out signInForm-submitButton']")
	public WebElement submitButton;
	@FindBy(how = How.XPATH, using = "//a[@class='cartlink']")
	public WebElement cartLink;
	@FindBy(how = How.XPATH, using = "//input[@id='quickOrderProductId']")
	public WebElement quickOrderProductId;
	@FindBy(how = How.XPATH, using = "//button[@class='cart-checkoutButtons-checkout button-called-out-positive qa_Submit_Button']")
	public WebElement submitProduct;
	@FindBy(how = How.XPATH, using = "//a[@id='lenovo-checkout-sold-out']")
	public WebElement checkoutSmb;
	@FindBy(how = How.XPATH, using = "//label[@class='redesign-term-check redesign-unchecked-icon']")
	public WebElement redesignUnchecked;
	@FindBy(how = How.XPATH, using = "//button[@id='orderSummaryReview_placeOrder']")
	public WebElement placeOrder;
	
	//NA-15481
	@FindBy(how = How.XPATH, using = "//div[@class='viewmodeltabs-row qa-splitter-viewmodel-tabs']/ul[@class='vam-tab tab1 vam-active']")
	public WebElement Home_activeLaptops;
	
	//content-481
	@FindBy(how = How.XPATH, using = "//input[@id='inputSearchText']")
	public WebElement inputSearchText;
	@FindBy(how = How.XPATH, using = "//button[@class='search-submit fa fa-search Qa_Search_icon']")
	public WebElement searchIcon;
	@FindBy(how = How.XPATH, using = "//div[@class='resourceSection search-analytics']")
	public WebElement searchanalytics;
	@FindBy(how = How.XPATH, using = "//div[@class='product-card__thumbnail']")
	public WebElement cardthumbnail;
	@FindBy(how = How.XPATH, using = "//div[@id='shopTab']")
	public WebElement shopTab;
	@FindBy(how = How.XPATH, using = "//div[@id='discoverTab']")
	public WebElement discoverTab;
	
	//comm-354
	@FindBy(how = How.XPATH, using = "//input[@id='SAVE_IN_ADDRESS_BOOK']")
	public WebElement Shipping_SaveAddressToBook;
	@FindBy(how = How.XPATH, using = "//button[@id='viewSavedPayments']")
	public WebElement payment_viewSavedPayments;
	
	//browse-244
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'FILTER BY SPECS')]")
	public WebElement filter_by_specs;
	@FindBy(how = How.XPATH, using = "//*[text()='Screen Size']")
	public WebElement screen_size;
	@FindBy(how = How.XPATH, using = "//*[text()='Screen Size']/../../ol/li[@data-jsid='11.6in']")
	public WebElement select_screen_size;

	//shope293
	@FindBy(how = How.XPATH, using = "(//a[@class='facetedResults-cta bundleResults-red'])[last()]")
	public WebElement CB_leadingProductNumber;
	@FindBy(how = How.XPATH, using = "(//a[@class='facetedResults-cta bundleResults-blue'])[last()]")
	public WebElement CB_referenceProductNumber;
	

	//browse-540
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'subseriesHeader')]/nav/span/span")
	public WebElement productSummaryOnPDP;
	@FindBy(how = How.XPATH, using = "//div[@class='titleSection']")
	public WebElement accesssoryTitleSection;
	@FindBy(how = How.XPATH, using = "//div[@class='overviewSection']/div")
	public WebElement accesssoryOverview;
	@FindBy(how = How.XPATH, using = "//div[@class='galleryContent']/img")
	public WebElement accesssoryImage;
	@FindBy(how = How.XPATH, using = "//div[@class='productStatus-deals']")
	public WebElement accesssoryStatus;
	
	//SHOPE423
	@FindBy(how = How.XPATH, using = "//dd[@class='cta-price']")
	public WebElement PDP_bundlewebprice;
	@FindBy(how =How.XPATH, using = "//button[@submitfromid='addToCartForm4Bundle']")
	public WebElement PDP_bundleAddtocart;
	@FindBy(how =How.XPATH, using = "//span[@class='base-price']")
	public WebElement CTO_bundlebaseprice;
	@FindBy(how =How.XPATH, using = "//span[@class='your-price-value price-value']")
	public WebElement CTO_bundleyourprice;
	@FindBy(how =How.XPATH, using = "//span[@class='item-price price-value']")
	public WebElement CTO_bundlediscountprice;
	@FindBy(how =How.XPATH, using = "//span[contains(@class,'subTotalWithoutCoupon')]")
	public WebElement CART_bundlesubtotalprice;
	@FindBy(how =How.XPATH, using = "//span[contains(@class,'qa_estimatedTotal_Price')]")
	public WebElement CART_bundleesttotalprice;
	@FindBy(how =How.XPATH, using = "//span[contains(@class,'totalDiscounts')]")
	public WebElement CART_bundlediscountprice;
	
	//comm-629
	@FindBy(how = How.XPATH, using = "//input[contains(@class,'cbCartItemOption-button cbCartItemOption-button-no')]")
	public WebElement cart_edit_no;
	@FindBy(how = How.XPATH, using = "//input[contains(@class,'cbCartItemOption-button cbCartItemOption-button-yes')]")
	public WebElement cart_edit_yes;
	@FindBy(how = How.XPATH, using = "//span[contains(@class,'price-calculator-cart-summary-totalDiscounts qa_Saving_Price')]")
	public WebElement cart_saving_price;


	//COMM633
	@FindBy(how = How.XPATH, using = "(//div[@class='cart-item-details'])[2]//div[@class='popup-itemlimit']")
	public WebElement CART_popup_itemlimit;
	@FindBy(how = How.XPATH, using = "//input[contains(@class,'cbCartItemOption') and @value='Yes']")
	public WebElement CART_edit_yes;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'alert') and contains(text(),'Unfortunately')]")
	public WebElement CART_alert;

	//ACCT584
	@FindBy(how = How.XPATH, using = "//input[@id='inactivity_ok']")
	public WebElement sessionStayLoggedIn;

	@FindBy(how = How.XPATH, using = "//input[@id='logoutButton']")
	public WebElement sessionlogOutButton;
	//
    //$x("(//div[contains(text(),'Laptops')])[1]")

}
