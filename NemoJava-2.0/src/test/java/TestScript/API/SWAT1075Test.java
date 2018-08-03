package TestScript.API;

import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

public class SWAT1075Test extends ServiceSuperTestClass {

	private String ContextString;
	private String productNo;
	private String ContextParameter;
	private String name;
	private String expectedStatusCode;
	private String shortName;
	private String expectedShortName;
	private String Weight;
	private String ExpectedWeight;
	private String Memory;
	private String ExpectedMemory;
	private String DisplayType;
	private String ExpectedDisplayType;
	private String Processor;
	private String ExpectedProcessor;
	private String HardDrive;
	private String ExpectedHardDrive;
	private String Ports;
	private String ExpectedPorts;
	private String Battery;
	private String ExpectedBattery;
	private String OperatingSystem;
	private String ExpectedOperatingSystem;
	private String codeValue;
	private String expectedCodeValue;
	private String productCode;

	private String storeValue;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1075Test(String store, String productNo, String context,
			String contextParam, String status, String shortNamevalue,
			String Memoryvalue, String DisplayTypevalue, String Weightvalue,
			String Processorvalue, String HardDrivevalue, String Portsvalue,
			String Batteryvalue, String OperatingSystemvalue) {
		this.Store = store;
		this.productNo = productNo;
		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.expectedStatusCode = status;
		this.expectedShortName = shortNamevalue;
		this.ExpectedMemory = Memoryvalue;
		this.ExpectedDisplayType = DisplayTypevalue;
		this.ExpectedWeight = Weightvalue;
		this.ExpectedProcessor = Processorvalue;
		this.ExpectedHardDrive = HardDrivevalue;
		this.ExpectedPorts = Portsvalue;
		this.ExpectedBattery = Batteryvalue;
		this.ExpectedOperatingSystem = OperatingSystemvalue;
		this.testName = "SWAT-1075";
		super.serviceName = "ProductValidateFalse";
	}

	// tests[0] = new NA28797Test(store,productNo,contextString,
	// contexPara,name,shortName,Memory, DisplayType, Weight, Processor,
	// HardDrive, Ports, Battery, OperatingSystem);

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "context=" + ContextString + "; Product Number="
					+ productNo;
			String serviceURL = testData.envData.getProductServiceDomain()
					+ ContextParameter;
			// verify if the reponse comes back without issues
			if(expectedStatusCode.equals("200")){
				super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
				assert super.serviceStatus.equals("200");
				String httpResult = hCommon.HttpRequest(serviceURL);
				assert httpResult.equals("{}");
			}else{
				super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
				assert super.serviceStatus.equals("500");
				
			}
				
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public String trimString(String a) {
		String result;
		result = a.toUpperCase().replaceAll(" ", "");
		return result;

	}

}