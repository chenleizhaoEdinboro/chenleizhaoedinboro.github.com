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

public class SWAT1169Testv2 extends ServiceSuperTestClass {

	private String userType;
	private String userid;

	private String expectedDMU;
	private String expectedDMUID;
	private String tempDMU = "";
	private String tempDMUID = "";
	private String PKValue;
	private String expectedGroupID1;
	private String expectedGroupID2;
	private String expectedGroupID3;
	private String expectedGroupID4;
	private String expectedGroupType1;
	private String expectedGroupType2;
	private String expectedGroupType3;
	private String expectedGroupType4;
	private String tempGroupID;
	private String tempGroupType;
	private String urlParameter;
	private String storeType;
	private String businessUnit3;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1169Testv2(String store, String parastring, String userid,
			String userType, String GI1, String GT1,String GI2, String GT2,
			String GI3, String GT3, String GI4, String GT4) {
		this.Store = store;
		this.urlParameter = parastring;
		this.userid = userid;
		this.userType = userType;
		this. expectedGroupID1=GI1;
		this.expectedGroupID2=GI2;
		this. expectedGroupID3=GI3;
		this.expectedGroupID4=GI4;
		this. expectedGroupType1=GT1;
		this.expectedGroupType2=GT2;
		this.expectedGroupType3=GT3;
		this. expectedGroupType4=GT4;

		this.testName = "SWAT1169";
		super.serviceName = "UserAuthorizationv2";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "id=" + userid + "; user type =" + userType;
			String serviceURL = testData.envData
					.getAccountAuthorizationServiceDomainv2() + urlParameter;

			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;
			httpResult = hCommon.HttpRequest(serviceURL);

			
			
			
			

			
			
			JSONArray groupArray = JSONArray.fromObject(httpResult);
				
			if (!expectedGroupID1.equals("")) {
				for (int i = 0; i < groupArray.size(); i++) {
					tempGroupID = JCommon.getJSONArrayValue(httpResult, i,
							"id");	
					tempGroupType=JCommon.getJSONArrayValue(httpResult, i,
							"type");	

					if (tempGroupID.toUpperCase().equals(expectedGroupID1.toUpperCase())) {

						break;
					}
				}

				assert tempGroupID.toUpperCase().equals(expectedGroupID1.toUpperCase()) : " Groupid is not found: "
						+ expectedGroupID1;
				
				assert tempGroupType.toUpperCase().equals(expectedGroupType1.toUpperCase()) : "GroupType is not found: "
				+ expectedGroupType1;
			}
			
			if (!expectedGroupID2.equals("")) {
				for (int i = 0; i < groupArray.size(); i++) {
					tempGroupID = JCommon.getJSONArrayValue(httpResult, i,
							"id");	
					tempGroupType=JCommon.getJSONArrayValue(httpResult, i,
							"type");	

					if (tempGroupID.toUpperCase().equals(expectedGroupID2.toUpperCase())) {

						break;
					}
				}

				assert tempGroupID.toUpperCase().equals(expectedGroupID2.toUpperCase()) : " Groupid is not found: "
						+ expectedGroupID2;
				
				assert tempGroupType.toUpperCase().equals(expectedGroupType2.toUpperCase()) : "GroupType is not found: "
				+ expectedGroupType2;
			}
			
			
			if (!expectedGroupID3.equals("")) {
				for (int i = 0; i < groupArray.size(); i++) {
					tempGroupID = JCommon.getJSONArrayValue(httpResult, i,
							"id");	
					tempGroupType=JCommon.getJSONArrayValue(httpResult, i,
							"type");	

					if (tempGroupID.toUpperCase().equals(expectedGroupID3.toUpperCase())) {

						break;
					}
				}

				assert tempGroupID.toUpperCase().equals(expectedGroupID3.toUpperCase()) : " Groupid is not found: "
						+ expectedGroupID3;
				
				assert tempGroupType.toUpperCase().equals(expectedGroupType3.toUpperCase()) : "GroupType is not found: "
				+ expectedGroupType3;
			}
			
			if (!expectedGroupID4.equals("")) {
				for (int i = 0; i < groupArray.size(); i++) {
					tempGroupID = JCommon.getJSONArrayValue(httpResult, i,
							"id");	
					tempGroupType=JCommon.getJSONArrayValue(httpResult, i,
							"type");	

					if (tempGroupID.toUpperCase().equals(expectedGroupID4.toUpperCase())) {

						break;
					}
				}

				assert tempGroupID.toUpperCase().equals(expectedGroupID4.toUpperCase()) : " Groupid is not found: "
						+ expectedGroupID4;
				
				assert tempGroupType.toUpperCase().equals(expectedGroupType4.toUpperCase()) : "GroupType is not found: "
				+ expectedGroupType4;
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}