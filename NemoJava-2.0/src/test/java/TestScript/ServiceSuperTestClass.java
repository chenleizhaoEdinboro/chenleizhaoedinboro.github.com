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

public abstract class ServiceSuperTestClass {

	public TestData testData;
	public String testName;
	public String Environment;
	public int RunID;
	public String Store;
	public String paraString;
	public String serviceName;
	public String serviceStatus;
	private String tempEnv;
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

	@BeforeClass
	public void setup() {
		this.Environment = System.getProperty("environment");
		this.RunID = Integer.parseInt(System.getProperty("runId"));
		this.Organization = System.getProperty("organization");

		testStartTime = df.format(new Date());
		testData = new TestData(this.Environment, this.getClass().getPackage()
				.getName().replace(".", "/").split("/")[1], this.Store);
		Dailylog.maplog.remove(this.Store + this.testName);// Initialization log
															// about store
	}

	public void prepareTest() throws MalformedURLException {
		testResult = 0;
		log = "";

	}

	@AfterClass
	public void close() {
		testEndTime = df.format(new Date());
		logResult();

	}

	public void handleThrowable(Throwable e, ITestContext ctx) {
		if (testResult != 6)
			testResult = 1; // If not assertion from 'No test data', then test
							// fail
		if (inRerunFlag) {
			this.pictureId = this.testName + testData.Store
					+ String.valueOf(ScreenShot.next());
			Dailylog.logInfo(testData.Store + " PictureID: " + pictureId);
			Dailylog.logInfo(testData.Store + " TestStartTime: "
					+ testStartTime);

		}
		if (Browser != "Safari") {

		}
		ctx.setAttribute("Store", this.testName + " " + this.Store);
		inRerunFlag = true;
		if (e instanceof Exception) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			if (Dailylog.maplog.get(Store + testName) != null) {
				log = log + Dailylog.maplog.get(Store + testName) + Store
						+ " Fail:" + sw.toString();
			} else {
				log = log + Store + " Fail:" + sw.toString();
			}
			e.printStackTrace();
			Dailylog.maplog.remove(Store + testName);
			Verify.verify(false);
		} else {
			if (Dailylog.maplog.get(Store + testName) != null) {
				log = log + Dailylog.maplog.get(Store + testName) + Store
						+ " Fail:" + e.getMessage();
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
			testCaseService = AutoReportImpl.creatInstance(PropsUtils
					.getParameter(Environment));
		}
		if (testResult == 0) {
			if (Dailylog.maplog.get(Store + testName) != null) {
				log = log + Dailylog.maplog.get(Store + testName);
			}

			Dailylog.maplog.remove(Store + testName);
		}

		if (this.Organization.toLowerCase().equals("service")) {
			System.out.println(log);
			if(testData.Environment.equals("PreU")){
				tempEnv="SIT";
			}else if(testData.Environment.equals("PreC")){
				tempEnv="PRE";
			}else{tempEnv="DIT";}
			Dailylog.logInfo("Service: sql="
					+ "insert into service_test_result(test_runid,service_name,run_time,result,log,environment,parameter,service_status) values('"
					+ this.RunID + "','" + this.serviceName + "','"
					+ testStartTime + "','" + Integer.toString(testResult)
					+ "',?,'" + tempEnv + "','"
					+ this.paraString + "','" + this.serviceStatus + "');");
			testCaseService.update(
					"insert into service_test_result(test_runid,service_name,run_time,result,log,environment,parameter,service_status) values('"
							+ this.RunID + "','" + this.serviceName + "','"
							+ testStartTime + "','" + Integer.toString(testResult)
							+ "',?,'" + tempEnv + "','"
							+ this.paraString + "','" + this.serviceStatus + "');", log);
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

}