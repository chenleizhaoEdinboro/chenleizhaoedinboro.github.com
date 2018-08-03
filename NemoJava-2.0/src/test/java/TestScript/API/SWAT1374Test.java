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

public class SWAT1374Test extends ServiceSuperTestClass {

	private String ContextString;
	private String productNumber;

	private String urlPara;
	private String availableAmount;
	private String tempDMU = "";
	private String tempDMUID = "";
	private String PKValue;
	private String reserveAmount;
	private String inStockStatus;
	private String businessUnit1;
	private String businessUnit2;
	private String Type;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1374Test(String store, String productNo, String context,
			String parameter, String productType,String availableAmount, String reserved,
			String inStockStatus) {
		this.Store = store;

		this.productNumber = productNo;
		this.ContextString = context;
		this.urlPara = parameter;
		this.Type=productType;
		this.availableAmount = availableAmount;
		this.reserveAmount = reserved;
		this.inStockStatus = inStockStatus;

		this.testName = "SWAT1374";
		super.serviceName = "getStock";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString ="Product Type is "+Type+ "Product Number=" + productNumber
					+ "; contextString=" + ContextString;
			String serviceURL = testData.envData.getStockServiceDomain()
					+ urlPara;

			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200") : "actual status code is "
					+ serviceStatus;
			String httpResult;
			httpResult = hCommon.HttpRequest(serviceURL);

			String stockJSON = hCommon.getJsonValue(httpResult, productNumber);
			String tempAvailableAmount = hCommon.getJsonValue(stockJSON,
					"available");
			String tempReservedAmount = hCommon.getJsonValue(stockJSON,
					"reserved");

			String tempStockStatus = hCommon.getJsonValue(stockJSON,
					"inStockStatus");

		
			if(Type.equals("CTO")){
				
				assert tempAvailableAmount.equals("0") : "actual available amount is"
						+ tempAvailableAmount;

				assert tempReservedAmount.equals("0") : "actual reserved amount is"
						+ tempReservedAmount;

				assert tempStockStatus.equals("") : "actual stock status  is"
						+ tempStockStatus;
			}else{
				
			
				assert tempAvailableAmount.equals(availableAmount) : "actual available amount is"
						+ tempAvailableAmount;

				assert tempReservedAmount.equals(reserveAmount) : "actual reserved amount is"
						+ tempReservedAmount;

				assert tempStockStatus.equals(inStockStatus) : "actual stock status  is"
						+ tempStockStatus;
			}
		

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}