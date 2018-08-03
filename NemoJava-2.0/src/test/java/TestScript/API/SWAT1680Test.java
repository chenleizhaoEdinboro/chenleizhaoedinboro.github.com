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

public class SWAT1680Test extends ServiceSuperTestClass {

	private String parameter;
	private String userid;
	private String contextString;
	private String contextPara;
	private String orderNO;
	private String updatedAmount;
	private String updatedByEmail;
	private String expectedtype;
	private String expectedconsumerId;
	private String modelParameter;
	private String VisibilityParameter;
	private String tempCode;
	private String tempID;
	private String tempToken;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1680Test(String store, String userid,
			String paraString, String orderNumber, String changedAmount,
			String updatedBy, String consumerId) {
		this.Store = store;

		this.userid = userid;
		this.parameter = paraString;
		this.orderNO = orderNumber;
		this.updatedAmount = changedAmount;
		this.updatedByEmail=updatedBy;
		this.expectedconsumerId = consumerId;
	
		this.testName = "SWAT1680";
		super.serviceName = "updateQuantity";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {
		
			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			
			String getOriginalOrderAPI="http://us-zuul-pre.lmp.xpaas.lenovo.com/subscription/customers/hybris?lenovoId="+userid;
			String getTokenAPI="http://us-zuul-pre.lmp.xpaas.lenovo.com/recurring-revenue/token";
					
	
			tempToken=hCommon.getJsonValue(hCommon.AddBodyHttpRequest(getTokenAPI), "accessToken");
			String[] RRResultJSON=hCommon.EncodedHttpRequest("Bearer "
					+ tempToken, getOriginalOrderAPI);
			String RROrderJSON=hCommon.getJsonValue(RRResultJSON[1],"orders");
			String RROrderNumber=JCommon.getJSONArrayValue(RROrderJSON, 0, "ORDERNUMBER");
			String HybrisOrderNumber=JCommon.getJSONArrayValue(RROrderJSON, 0, "ORIGINALORDERNUMBER");
			String RRItemsJSON=JCommon.getJSONArrayValue(RROrderJSON, 0, "items");
			String RRQuantity=JCommon.getJSONArrayValue(RRItemsJSON, 0, "QUANTITY");
			String HybrisProductNo=JCommon.getJSONArrayValue(RRItemsJSON, 0, "LENOVOBASICSKU");
			super.paraString = "Order Number is "+HybrisOrderNumber+"; quantity updated is ="+updatedAmount+";Updated by "+updatedByEmail;
			
			String orderserviceURL = testData.envData.getSubOrderBySubIDDomain()
			+ RROrderNumber;
			
			String httpResult = hCommon.HttpRequest(orderserviceURL);
			String MSGetOrderContent=hCommon.getJsonValue(httpResult, "content");
			String MSItemID=JCommon.getJSONArrayValue(MSGetOrderContent, 0, "subscriptionItemId");
			String uptateQuantityURL=testData.envData.getSubUpdateQuantityDomain()+MSItemID+"&quantity="+updatedAmount+parameter;
		
			super.serviceStatus = hCommon.verifyServiceStatusPOST(uptateQuantityURL);
			assert super.serviceStatus.equals("200"):"wrong status  code";
			httpResult = hCommon.HttpRequest(orderserviceURL);
			 MSGetOrderContent=hCommon.getJsonValue(httpResult, "content");
			 
			 if(Integer.parseInt(updatedAmount)>=0){
				 String MSItemQuantity=JCommon.getJSONArrayValue(MSGetOrderContent, 0, "quantity");
				 assert MSItemQuantity.equals(updatedAmount):"Actual quantity is "+MSItemQuantity;
				 String MSupdatedBy=JCommon.getJSONArrayValue(MSGetOrderContent, 0, "updateBy");
				 assert MSupdatedBy.equals(updatedByEmail):"Actual quantity is "+MSupdatedBy; 
			 }else{
				 
				 String MSItemQuantity=JCommon.getJSONArrayValue(MSGetOrderContent, 0, "quantity");
				 assert !MSItemQuantity.equals(updatedAmount):"Actual quantity is negative";
				 
			 }
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}