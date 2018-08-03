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

public class SWAT1170Testv2 extends ServiceSuperTestClass {

	private String userType;
	private String userid;

	private String expectedDMU;
	private String expectedDMUID;
	private String tempDMU = "";
	private String tempDMUID = "";
	private String PKValue;
	private String expectedB2CUnit1;
	private String expectedB2CUnit2;
	private String expectedB2CUnit3;
	private String businessUnit1;
	private String businessUnit2;
	private String urlParameter;
	private String storeType;
	private String businessUnit3;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1170Testv2(String store, String parastring, String userid,
			String userType, String dmu, String dmuid,String storeT, String b2cunit1,
			String b2cunit2,String b2cunit3, String PK) {
		this.Store = store;
		this.urlParameter = parastring;
		this.userid = userid;
		this.userType = userType;
		this.expectedDMU = dmu;
		this.expectedDMUID = dmuid;
		this.storeType=storeT;
		this.expectedB2CUnit1 = b2cunit1;
		this.expectedB2CUnit2 = b2cunit2;
		this.expectedB2CUnit3=b2cunit3;
		this.PKValue = PK;

		this.testName = "SWAT1170";
		super.serviceName = "B2BUserAccess2";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "id=" + userid + "; user type =" + userType;
			String serviceURL = testData.envData
					.getAccountB2BAccessDomainv2() + urlParameter;

			// verify if the reponse comes back without issues
     
			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;
			httpResult = hCommon.HttpRequest(serviceURL);

			
			
			
			

			
			String unitsJSON = hCommon.getJsonValue(httpResult, "units");
			String ChildrenJSON=hCommon.getJsonValue(unitsJSON, "children");
			JSONArray childrenArray = JSONArray.fromObject(ChildrenJSON);
			tempDMU= hCommon.getJsonValue(httpResult, "defaultDMU");
			tempDMUID=hCommon.getJsonValue(httpResult, "defaultB2BUnit");
			assert tempDMU.equals(expectedDMU):"Actual dmu is "+tempDMU;
			assert tempDMUID.equals(expectedDMUID):"Actual dmuID is "+tempDMUID;
			
			if (!expectedB2CUnit1.equals("")) {
				for (int i = 0; i < childrenArray.size(); i++) {
					businessUnit1 = JCommon.getJSONArrayValue(ChildrenJSON, i,
							"code");	

					if (businessUnit1.equals(expectedB2CUnit1)) {

						break;
					}
				}

				assert businessUnit1.equals(expectedB2CUnit1) : "Actual businessUnit1 is"
						+ businessUnit1;

			}
			if (!expectedB2CUnit2.equals("")) {
				for (int i = 0; i < childrenArray.size(); i++) {
					businessUnit2 = JCommon.getJSONArrayValue(ChildrenJSON, i,
							"code");

					if (businessUnit2.equals(expectedB2CUnit2)) {

						break;
					}
				}

				assert businessUnit2.equals(expectedB2CUnit2) : "Actual businessUnit2 is"
						+ businessUnit2;

			}
			if (!expectedB2CUnit3.equals("")) {
				for (int i = 0; i < childrenArray.size(); i++) {
					businessUnit3 = JCommon.getJSONArrayValue(ChildrenJSON, i,
							"code");

					if (businessUnit3.equals(expectedB2CUnit3)) {

						break;
					}
				}

				assert businessUnit3.equals(expectedB2CUnit3) : "Actual businessUnit3 is"
						+ businessUnit3;

			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}