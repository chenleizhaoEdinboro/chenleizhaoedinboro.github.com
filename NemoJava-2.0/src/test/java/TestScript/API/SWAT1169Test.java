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

public class SWAT1169Test extends ServiceSuperTestClass {

	private String userType;
	private String userid;

	private String expectedDMU;
	private String expectedDMUID;
	private String tempDMU = "";
	private String tempDMUID = "";
	private String PKValue;
	private String expectedB2CUnit1;
	private String expectedB2CUnit2;
	private String businessUnit1;
	private String businessUnit2;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1169Test(String store, String userid, String userType,
			String dmu, String dmuid, String b2cunit1, String b2cunit2,
			String PK) {
		this.Store = store;

		this.userid = userid;
		this.userType = userType;
		this.expectedDMU = dmu;
		this.expectedDMUID = dmuid;
		this.expectedB2CUnit1 = b2cunit1;
		this.expectedB2CUnit2 = b2cunit2;
		this.PKValue = PK;

		this.testName = "SWAT1169";
		super.serviceName = "UserAuthorization";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "id=" + userid + "; user type =" + userType;
			String serviceURL = testData.envData
					.getAccountAuthorizationServiceDomain() + PKValue;

			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;
			httpResult = hCommon.HttpRequest(serviceURL);

			// String ChildrenJSON=hCommon.getJsonValue(httpResult, "children");

			String GroupsJSON = hCommon.getJsonValue(httpResult, "groups");
			String unitsJSON = hCommon.getJsonValue(httpResult, "units");
			JSONArray unitsArray = JSONArray.fromObject(unitsJSON);
			for (int i = 0; i < unitsArray.size(); i++) {
				tempDMU = JCommon.getJSONArrayValue(unitsJSON, i, "id");
				if (tempDMU.equals(expectedDMU)) {

					break;
				}
			}
			assert tempDMU.equals(expectedDMU) : "Actual dmu is" + tempDMU;
			for (int i = 0; i < unitsArray.size(); i++) {
				tempDMUID = JCommon.getJSONArrayValue(unitsJSON, i, "id");

				if (tempDMUID.equals(expectedDMUID)) {

					break;
				}
			}

			assert tempDMUID.equals(expectedDMUID) : "Actual DMUID is"
					+ tempDMUID;

			if (!expectedB2CUnit1.equals("")) {
				for (int i = 0; i < unitsArray.size(); i++) {
					businessUnit1 = JCommon.getJSONArrayValue(unitsJSON, i,
							"id");

					if (businessUnit1.equals(expectedB2CUnit1)) {

						break;
					}
				}

				assert businessUnit1.equals(expectedB2CUnit1) : "Actual businessUnit is"
						+ businessUnit1;

			}
			if (!expectedB2CUnit2.equals("")) {
				for (int i = 0; i < unitsArray.size(); i++) {
					businessUnit2 = JCommon.getJSONArrayValue(unitsJSON, i,
							"id");

					if (businessUnit2.equals(expectedB2CUnit2)) {

						break;
					}
				}

				assert businessUnit2.equals(expectedB2CUnit2) : "Actual businessUnit is"
						+ businessUnit2;

			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}