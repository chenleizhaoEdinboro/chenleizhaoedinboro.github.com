package TestScript;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.base.Verify;

import CommonFunction.Common;
import Dao.impl.AutoReportImpl;
import Logger.Dailylog;
import Logger.ScreenShot;
import TestData.PropsUtils;
import TestData.TestData;

public abstract class SuperTestClass {
	public WebDriver driver;
	public TestData testData;
	public String testName;
	public String Environment;
	public int RunID;
	public String Store;
	private int testResult; // 5=Not Run; 0=Pass; 1=Fail; 4=Need Manual Check;
							// 6=No Data
	public String log = "";
	private String testStartTime;
	private String testEndTime;
	private SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
	private String pictureId = "";
	private boolean inRerunFlag = false;
	private static Dao.inter.AutoReportDao testCaseService;
	private String Organization = "";
	public String Browser = "";

	@BeforeClass(alwaysRun = true)
	public void setup() {
		this.Environment = System.getProperty("environment");
		this.RunID = Integer.parseInt(System.getProperty("runId"));
		this.Organization = System.getProperty("organization");
		this.Browser = System.getProperty("browser");
		testStartTime = df.format(new Date());

		String packageName=this.getClass().getPackage().getName().replace(".", "/").split("/")[1];
		if(packageName.equals("FE")) {
			packageName="B2C";
		}

		testData = new TestData(this.Environment,packageName, this.Store);
		Dailylog.maplog.remove(this.Store + this.testName);// Initialization log
															// about store
	}

	public void SetupBrowser() throws MalformedURLException {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				handleAlert(driver);
				Dailylog.logInfo("SetupBrowser has a trouble");
			}
		}
		URL browserStackUrl = new URL("https://songli3:Pus8ewKsvMEbWXSySZQ6@hub-cloud.browserstack.com/wd/hub");
		if (Browser != "") {
			switch (Browser.toLowerCase()) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
				driver = new ChromeDriver();
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
				driver = new FirefoxDriver();
				break;
			case "ie11":
				System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				break;
//			case "firefox":
//				DesiredCapabilities capsFF58 = new DesiredCapabilities();
//				capsFF58.setCapability("os", "Windows");
//				capsFF58.setCapability("os_version", "10");
//				capsFF58.setCapability("browser", "Firefox");
//				capsFF58.setCapability("browser_version", "59.0");
//				capsFF58.setCapability("resolution", "1680x1050");
//				capsFF58.setCapability("project", "NemoCompatibility");
//				capsFF58.setCapability("browserstack.local", "true");
//				capsFF58.setCapability("browserstack.debug", "true");
//				capsFF58.setCapability("browserstack.selenium_version", "3.10.0");
//				driver = new RemoteWebDriver(browserStackUrl, capsFF58);
//				break;
//			case "ie11":
//				DesiredCapabilities capsIE11 = new DesiredCapabilities();
//				capsIE11.setCapability("os", "Windows");
//				capsIE11.setCapability("os_version", "10");
//				capsIE11.setCapability("browser", "IE");
//				capsIE11.setCapability("browser_version", "11.0");
//				capsIE11.setCapability("resolution", "1680x1050");
//				capsIE11.setCapability("project", "NemoCompatibility");
//				capsIE11.setCapability("browserstack.local", "true");
//				capsIE11.setCapability("browserstack.debug", "true");
//				capsIE11.setCapability("browserstack.selenium_version", "3.10.0");
//				driver = new RemoteWebDriver(browserStackUrl, capsIE11);
//				break;
			case "ie10":
				DesiredCapabilities capsIE10 = new DesiredCapabilities();
				capsIE10.setCapability("os", "Windows");
				capsIE10.setCapability("os_version", "8");
				capsIE10.setCapability("browser", "IE");
				capsIE10.setCapability("browser_version", "10.0");
				capsIE10.setCapability("resolution", "1680x1050");
				capsIE10.setCapability("project", "NemoCompatibility");
				capsIE10.setCapability("browserstack.local", "true");
				capsIE10.setCapability("browserstack.debug", "true");
//				capsIE10.setCapability("browserstack.ie.driver", "2.50");
				capsIE10.setCapability("browserstack.selenium_version", "3.10.0");
				driver = new RemoteWebDriver(browserStackUrl, capsIE10);
				break;
			case "edge":
				if(!Browser.toLowerCase().equals("edge")){
					 driver.manage().deleteAllCookies();
					}
				
			case "edge16":
				DesiredCapabilities capsEdge = new DesiredCapabilities();
				capsEdge.setCapability("os", "Windows");
				capsEdge.setCapability("os_version", "10");
				capsEdge.setCapability("browser", "Edge");
				capsEdge.setCapability("browser_version", "16.0");
				capsEdge.setCapability("resolution", "1680x1050");
				capsEdge.setCapability("project", "NemoCompatibility");
				capsEdge.setCapability("browserstack.local", "true");
				capsEdge.setCapability("browserstack.debug", "true");
				capsEdge.setCapability("browserstack.selenium_version", "3.10.0");
				driver = new RemoteWebDriver(browserStackUrl, capsEdge);
				break;
			case "safari11":
				DesiredCapabilities capsSafari = new DesiredCapabilities();
				capsSafari.setCapability("os", "OS X");
				capsSafari.setCapability("os_version", "High Sierra");
				capsSafari.setCapability("browser", "Safari");
				capsSafari.setCapability("browser_version", "11.0");
				capsSafari.setCapability("resolution", "1600x1200");
				capsSafari.setCapability("project", "NemoCompatibility");
				capsSafari.setCapability("browserstack.local", "true");
				capsSafari.setCapability("browserstack.debug", "true");
				capsSafari.setCapability("browserstack.selenium_version", "3.7.0");
				SafariOptions option = new SafariOptions();
				option.setUseTechnologyPreview(true);
				capsSafari.setCapability(SafariOptions.CAPABILITY, option);
				driver = new RemoteWebDriver(browserStackUrl, capsSafari);
				break;
			case "chromemobile":
				DesiredCapabilities capsChromeMobile = new DesiredCapabilities();
				capsChromeMobile.setCapability("os_version", "7.0");
				capsChromeMobile.setCapability("device", "Samsung Galaxy S8");
				capsChromeMobile.setCapability("real_mobile", "true");
				capsChromeMobile.setCapability("project", "NemoCompatibility");
				capsChromeMobile.setCapability("browserstack.local", "true");
				capsChromeMobile.setCapability("browserstack.debug", "true");
				driver = new RemoteWebDriver(browserStackUrl, capsChromeMobile);
				break;
			case "safarimobile":
				DesiredCapabilities capsSafariMobile = new DesiredCapabilities();
				capsSafariMobile.setCapability("os_version", "11.0");
				capsSafariMobile.setCapability("device", "iPhone 8");
				capsSafariMobile.setCapability("real_mobile", "true");
				capsSafariMobile.setCapability("project", "NemoCompatibility");
				capsSafariMobile.setCapability("browserstack.local", "true");
				capsSafariMobile.setCapability("browserstack.debug", "true");
				driver = new RemoteWebDriver(browserStackUrl, capsSafariMobile);
				break;
			default:
				System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
				driver = new ChromeDriver();
				Dailylog.logInfo("default:ChromeDriver");
			}
		}
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		if (!Browser.toLowerCase().equals("safarimobile") && !Browser.toLowerCase().equals("chromemobile"))
			driver.manage().window().maximize();
		if(!Browser.toLowerCase().equals("edge")){
			 driver.manage().deleteAllCookies();
			}
	}

	public void prepareTest() throws MalformedURLException {
		testResult = 0;
		log = "";
		SetupBrowser();
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		testEndTime = df.format(new Date());
		logResult();

		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				handleAlert(driver);
				Dailylog.logInfo("driver has a trouble");
			}
		}
	}

	public void handleThrowable(Throwable e, ITestContext ctx) {
		if (testResult != 6)
			testResult = 1; // If not assertion from 'No test data', then test
							// fail
		if (inRerunFlag) {
			this.pictureId = this.testName + testData.Store + String.valueOf(ScreenShot.next());
			Dailylog.logInfo(testData.Store + " PictureID: " + pictureId);
			Dailylog.logInfo(testData.Store + " TestStartTime: " + testStartTime);
			if (driver != null) {
				try {
					ScreenShot.storeImage(driver, pictureId, driver.getCurrentUrl(), testStartTime,
							PropsUtils.getParameter(Environment));
				} catch (Exception e1) {
					Dailylog.logInfo("driver has a trouble");
				}
			}
		}
		if (Browser != "Safari") {
			if (driver != null) {
				try {
					driver.manage().deleteAllCookies();
					driver.quit();
				} catch (Exception e1) {
					handleAlert(driver);
					Dailylog.logInfo("driver has a trouble");
				}
			}
		}
		ctx.setAttribute("Store", this.testName + " " + this.Store);
		inRerunFlag = true;
		if (e instanceof Exception) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			if (Dailylog.maplog.get(Store + testName) != null) {
				log = log + Dailylog.maplog.get(Store + testName) + Store + " Fail:" + sw.toString();
			} else {
				log = log + Store + " Fail:" + sw.toString();
			}
			e.printStackTrace();
			Dailylog.maplog.remove(Store + testName);
			Verify.verify(false);
		} else {
			if (Dailylog.maplog.get(Store + testName) != null) {
				log = log + Dailylog.maplog.get(Store + testName) + Store + " Fail:" + e.getMessage();
			} else {
				log = log + Store + " Fail:" + e.getMessage();
			}
			Dailylog.maplog.remove(Store + testName);
			// Assertion.verifyEquals(this.Store + " Assert Failed: " +
			// e.getMessage(), "false","screenShot");
			Verify.verify(false);
		}
	}

	public void logResult() {
		if (testCaseService == null) {
			testCaseService = AutoReportImpl.creatInstance(PropsUtils.getParameter(Environment));
		}
		if (testResult == 0) {
			if (Dailylog.maplog.get(Store + testName) != null) {
				log = log + Dailylog.maplog.get(Store + testName);
			}

			Dailylog.maplog.remove(Store + testName);
		}

		// Compatibility add by XianEn
		if (this.Organization.toLowerCase().equals("compatibility")) {
			Dailylog.logInfo("Compatibility: sql=" + "UPDATE test_case SET start_time = '" + testStartTime
					+ "', end_time = '" + testEndTime + "', result = " + Integer.toString(testResult)
					+ ", log = ?, environment = '" + testData.Environment + "' ,picture_id ='" + this.pictureId
					+ "' WHERE test_runid = " + this.RunID + " and case_name = '" + this.testName + "' and store = '"
					+ this.Store + "' and organization='Compatibility' and " + "category='"
					+ System.getProperty("browser") + "'");
			testCaseService.update("UPDATE test_case SET start_time = '" + testStartTime + "', end_time = '"
					+ testEndTime + "', result = " + Integer.toString(testResult) + ", log = ?, environment = '"
					+ testData.Environment + "' ,picture_id ='" + this.pictureId + "' WHERE test_runid = " + this.RunID
					+ " and case_name = '" + this.testName + "' and store = '" + this.Store + "' and organization='Compatibility' and " 
					+ "category='" + System.getProperty("browser") + "'", log);
		} else {
			Dailylog.logInfo("sql=" + "UPDATE test_case SET start_time = '" + testStartTime + "', end_time = '"
					+ testEndTime + "', result = " + Integer.toString(testResult) + ", log = ?, environment = '"
					+ testData.Environment + "' ,picture_id ='" + this.pictureId + "' WHERE test_runid = " + this.RunID
					+ " and case_name = '" + this.testName + "' and store = '" + this.Store + "' and organization <> 'Compatibility'");
			testCaseService.update(
					"UPDATE test_case SET start_time = '" + testStartTime + "', end_time = '" + testEndTime
							+ "', result = " + Integer.toString(testResult) + ", log = ?, environment = '"
							+ testData.Environment + "' ,picture_id ='" + this.pictureId + "' WHERE test_runid = "
							+ this.RunID + " and case_name = '" + this.testName + "' and store = '" + this.Store + "' and organization <> 'Compatibility'",
					log);
			Dailylog.logInfo("casename:" + this.testName + " store:" + this.Store + " result:" + testResult);
		}

	}

	public void setManualValidateLog(String info) {
		log = log + Dailylog.maplog.get(Store + testName) + info + "\r\n";
		this.testResult = 4;
	}

	public void setNoDataLog(String info) {
		this.testResult = 6;
		Assert.fail(this.log + info + "\r\n");
	}

	public void markNAforPayment() {
		this.testResult = 7;
	}

	public void handleAlert(WebDriver driver) {
		try {
			Boolean flag = Common.isAlertPresent(driver);
			if (flag == true) {
				try {
					driver.switchTo().alert().accept();
					driver.quit();
				} catch (Exception e) {
					System.out.println("handleAlert Trouble:" + e.toString());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("handleAlert Trouble:" + e.toString());
		}
	}

	public void setupMobileBrowser() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Nexus 5X");
		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		driver = new ChromeDriver(capabilities);
	}
}