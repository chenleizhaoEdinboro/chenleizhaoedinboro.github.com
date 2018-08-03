package TestScript;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import Dao.impl.AutoReportImpl;
import TestData.PropsUtils;
import TestData.TestData;

public abstract class MobileSetup {
	public WebDriver driver;
	public TestData testData;
	public String testName;
	public String Environment;
	public int RunID;
	public String Store;
	private Boolean isPassed = true;
	private Boolean isManualValidateNeeded = false;
	private String log = "";
	private String testStartTime;
	private String testEndTime;
	private SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");

	public static final String USERNAME = "songli3";
	public static final String AUTOMATE_KEY = "JoLS9nmfqucjZ2GmTCdp";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY
			+ "@hub-cloud.browserstack.com/wd/hub";
    private static Dao.inter.AutoReportDao testCaseService;
    
	@BeforeClass(alwaysRun = true)
//	@Parameters({ "Environment", "RunID" })
	public void setup(String Environment, int RunID) {
		this.Environment = System.getProperty("environment");
		this.RunID = Integer.parseInt(System.getProperty("runId"));
//		this.Organization = System.getProperty("organization");
//		this.Browser = System.getProperty("browser");
		testStartTime = df.format(new Date());
	}

	public void SetupBrowser() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", "android");
		caps.setCapability("platform", "ANDROID");
		caps.setCapability("device", "Samsung Galaxy S5");
		driver = new RemoteWebDriver(new URL(URL), caps);
	}

	public void prepareTest(ITestContext ctx) throws MalformedURLException {
		testData = new TestData(this.Environment, this.getClass().getPackage()
				.getName().replace(".", "/").split("/")[1], this.Store);
		ctx.setAttribute("Store", this.testName + " " + this.Store);
		isPassed = true;
		SetupBrowser();
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		driver.quit();
		testEndTime = df.format(new Date());
		logResult();
	}

	public void handleThrowable(Throwable e) {
		isPassed = false;
		driver.manage().deleteAllCookies();
		driver.quit();
		if (e instanceof Exception) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log = sw.toString();
			System.out.println(this.Store + " Exception: ");
			e.printStackTrace();
			Assert.fail(this.Store + " Exception: " + e.getMessage());
		} else {
			log = "Assert Failed: " + e.getMessage();
			Assert.fail(this.Store + " Assert Failed: " + e.getMessage());
		}
	}

	public void logResult() {
		int resultFlag;
		if (isPassed) {
			if (isManualValidateNeeded) {
				// Need manual validation
				resultFlag = 4;
			} else {
				// Pass
				resultFlag = 0;
				log = "";
			}
		} else {
			// Fail
			resultFlag = 1;
		}
		
		if(testCaseService==null){
			testCaseService=AutoReportImpl.creatInstance(PropsUtils.getParameter(Environment));
		}
		testCaseService.update("UPDATE test_case SET start_time = '"
				+ testStartTime + "', end_time = '" + testEndTime
				+ "', result = " + resultFlag + ", log = ?, environment = '"
				+ testData.Environment + "' WHERE test_runid = " + this.RunID
				+ " and case_name = '" + this.testName + "' and store = '"
				+ testData.Store + "'", log);
	}

	public void setManualValidateLog(String info) {
		this.log = log + info + "/r/n";
		this.isManualValidateNeeded = true;
	}
}
