package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HACPage {
	public WebDriver PageDriver;

	public HACPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}
	// HAC Login
		@FindBy(how = How.NAME, using = "j_username")
		public WebElement HacLogin_UserName;
		@FindBy(how = How.NAME, using = "j_password")
		public WebElement HacLogin_UserPassword;
		@FindBy(how = How.ID, using = "_spring_security_remember_me")
		public WebElement HacLogin_RememberMe;
		@FindBy(how = How.XPATH, using = "//*[@id='logincontrols']//button")
		public WebElement HacLogin_LoginButton;
		@FindBy(how = How.ID, using = "platform")
		public WebElement HacHome_PlatformBtn;
        @FindBy(how = How.XPATH, using = "//*[@data-menuitem='configuration']/a")
		public WebElement HacPlatform_ConfigurationBtn;
        @FindBy(how = How.XPATH, using = "//*[@id='props_filter']/input")
		public WebElement HacCinfiguration_SearchInput;
        @FindBy(how = How.XPATH, using = "//*[@id='default.session.timeout']//input")
		public WebElement HacCinfiguration_TimeOutValue;
        @FindBy(how = How.XPATH, using = "//*[@class='applyIcon']")
		public WebElement HacCinfiguration_ApplyIcon;
        @FindBy(how = How.ID, using = "applyAllBtn")
		public WebElement HacCinfiguration_ApplyAllBtn;
		@FindBy(how = How.ID, using = "monitoring")
		public WebElement HacHome_Monitoring;
		@FindBy(how = How.XPATH, using = "//a[contains(@href,'monitoring/sessions')]")
		public WebElement HacMonitoring_Session;
		@FindBy(how = How.ID, using = "sessionTimeout")
		public WebElement HacMonitorSession_SessionTimeout;
		@FindBy(how = How.XPATH, using = "//input[@value='logout']")
		public WebElement HacHome_Logout;
		@FindBy(how = How.XPATH, using = "//li[@data-menuitem='console']/a")
		public WebElement HacHome_console;
		@FindBy(how = How.XPATH, using = "//li[@data-menuitem='impex import']/a")
		public WebElement HacHome_impexImport;
		@FindBy(how = How.XPATH, using = "(//div[@class='CodeMirror-linenumber CodeMirror-gutter-elt']/../..//pre)[1]")
		public WebElement ImpexImport_content1;
		@FindBy(how = How.XPATH, using = "(//div[@class='CodeMirror-linenumber CodeMirror-gutter-elt']/../..//pre)[2]")
		public WebElement ImpexImport_content2;
		@FindBy(how = How.XPATH, using = "(//div[@class='CodeMirror-linenumber CodeMirror-gutter-elt']/../..//pre)[3]")
		public WebElement ImpexImport_content3;
		@FindBy(how = How.XPATH, using = "//p/input[@type='submit'][1]")
		public WebElement ImpexImport_importBtn;
		@FindBy(how = How.XPATH, using = "//div[@id='notification']/div[@id='msg']")
		public WebElement ImpexImport_notificationMsg;
		@FindBy(how = How.ID, using = "configKey")
		public WebElement configKey;
		@FindBy(how = How.ID, using = "configValue")
		public WebElement configValue;
		@FindBy(how = How.ID, using = "addButton")
		public WebElement addKey;
		@FindBy(how = How.ID, using = "applyAllBtn")
		public WebElement applyAll;
		
		
		
		
}