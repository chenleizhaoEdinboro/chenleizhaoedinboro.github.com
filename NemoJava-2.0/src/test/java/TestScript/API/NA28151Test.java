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

public class NA28151Test extends ServiceSuperTestClass {

	private String service1;
	private String parentID;
	private String contextString;
	private String contextPara;
	private String ProductNumber="";
	private String ProductID="";
	private String expectedProductNumber;
	private String expectedProductID;
	private String storeValue;
	private String modelParameter;
	private String VisibilityParameter;
	private String tempCode;
	private String tempID;
	@Autowired
	private RestTemplate restTemplate;

	public NA28151Test(String store, String parentIDValue, String contextValue,
			String contextParaValue, String modelName,String visibility,
			String productNumberValue, String productNumberIDValue) {
		this.Store = store;

		this.parentID = parentIDValue;
		this.contextString = contextValue;
		this.contextPara = contextParaValue;
		this.modelParameter = modelName;
		this.VisibilityParameter=visibility;
		this.expectedProductNumber = productNumberValue;
		this.expectedProductID = productNumberIDValue;
		this.testName = "NA-28151";
		super.serviceName = "catalogGetChildren";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {
			// API1

			// API2
			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "type = "+modelParameter+"; visibility ="+VisibilityParameter+" ;parentID=" + parentID + ";contextString="
					+ contextString + ";";
			String serviceURL = testData.envData
					.getCategorygetChildrenServiceDomain()
					+ parentID
					+ contextPara;
           
			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;
			httpResult = hCommon.HttpRequest(serviceURL);
		
			//String ChildrenJSON=hCommon.getJsonValue(httpResult, "children");
			JSONArray childrenArray = JSONArray.fromObject(httpResult); 
			
			
			for(int i=0;i<childrenArray.size();i++){
				tempCode=JCommon.getJSONArrayValue(httpResult, i, "code");
				if(tempCode.equals(expectedProductNumber)){
					ProductNumber=tempCode;
					ProductID=JCommon.getJSONArrayValue(httpResult, i, "id");
					break;
				}
			}
			
			assert ProductNumber.equals(expectedProductNumber);
			assert ProductID.equals(expectedProductID);
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}