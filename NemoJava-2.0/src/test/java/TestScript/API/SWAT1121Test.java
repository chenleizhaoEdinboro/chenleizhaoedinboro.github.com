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

public class SWAT1121Test extends ServiceSuperTestClass {

	private String parameter;
	private String userid;
	private String contextString;
	private String contextPara;
	private String mode;
	private String expectedname;
	private String expectedProductNumber;
	private String expectedtype;
	private String expectedconsumerId;
	private String modelParameter;
	private String VisibilityParameter;
	private String tempCode;
	private String tempID;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1121Test(String store, String userid,
			String paraString, String mode, String name,
			String type, String consumerId) {
		this.Store = store;

		this.userid = userid;
		this.parameter = paraString;
		this.mode = mode;
		this.expectedname = name;
		this.expectedtype=type;
		this.expectedconsumerId = consumerId;
	
		this.testName = "SWAT1121";
		super.serviceName = "UserAuthenticate";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {
		
			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "id="+userid+"; mode ="+mode+";user type is "+expectedtype;
			String serviceURL = testData.envData.getAccountAuthenticateServiceDomain()
					+ parameter;
	
			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;
			httpResult = hCommon.HttpRequest(serviceURL);
			
			//String ChildrenJSON=hCommon.getJsonValue(httpResult, "children");
			String tempLoginname=hCommon.getJsonValue(httpResult, "loginName");
			String tempName=hCommon.getJsonValue(httpResult, "name");
			String tempType=hCommon.getJsonValue(httpResult, "type");
		
			assert tempLoginname.equals(userid):"Actual login name is"+tempLoginname;
			assert tempName.equals(expectedname):"Actual  name is"+tempName;
			assert tempType.equals(expectedtype):"Actual Type is"+tempType;
			if(!expectedconsumerId.equals("")){
				String tempConsumerId=hCommon.getJsonValue(httpResult, "consumerId");
				assert tempConsumerId.equals(expectedconsumerId):"Actual ConsumerId is"+tempConsumerId;
			}
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}