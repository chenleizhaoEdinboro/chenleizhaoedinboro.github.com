package TestScript.API;

import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.HttpCommon;
import CommonFunction.JSONCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.ServiceSuperTestClass;
import TestScript.SuperTestClass;

public class SWAT1170Test extends ServiceSuperTestClass {

	private String parameter;
	private String userid;

	private String dmuParameter;
	private String soldtoParameter;
	private String expectedAccessibleValue;
	private String expectedDefaultUnit;
	private String PKValue;

	@Autowired
	private RestTemplate restTemplate;

	public SWAT1170Test(String store, String userid, String paraString,
			String dmu, String SoldTo, String ifAccessible, String defaultUnit,
			String PK) {
		this.Store = store;

		this.userid = userid;
		this.parameter = paraString;
		this.dmuParameter = dmu;
		this.soldtoParameter = SoldTo;
		this.expectedAccessibleValue = ifAccessible;
		this.expectedDefaultUnit = defaultUnit;
		this.PKValue = PK;

		this.testName = "SWAT1170";
		super.serviceName = "B2BUserAccess";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "id=" + userid + "; dmu =" + dmuParameter+"soldto ="+soldtoParameter;
			String serviceURL = testData.envData
					.getB2BUserAccountServiceDomain() + PKValue + parameter;

			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;
			httpResult = hCommon.HttpRequest(serviceURL);

			// String ChildrenJSON=hCommon.getJsonValue(httpResult, "children");
			String tempAccessibility = hCommon.getJsonValue(httpResult,
					"access");

			assert tempAccessibility.equals(expectedAccessibleValue) : "Actual access value is"
					+ tempAccessibility;
			
			String tempDMUNumber = hCommon.getJsonValue(httpResult,
					"defaultB2BUnit");

			assert tempDMUNumber.equals(expectedDefaultUnit) : "Actual default unit is"
					+ tempDMUNumber;
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}