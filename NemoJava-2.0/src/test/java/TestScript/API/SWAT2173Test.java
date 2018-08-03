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

public class SWAT2173Test extends ServiceSuperTestClass {

	private String ContextString;
	private String productNo;
	private String ContextParameter;
	private String name;
	private String expectedProductName;
	private String subType;
	private String expectedSubType;
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

	public SWAT2173Test(String store, String productNo, String context,
			String contextParam, String namevalue, String SubTypevalue,
			String Memoryvalue, String DisplayTypevalue, String Weightvalue,
			String Processorvalue, String HardDrivevalue, String Portsvalue,
			String Batteryvalue, String OperatingSystemvalue) {
		this.Store = store;
		this.productNo = productNo;
		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.expectedProductName = namevalue;
		this.expectedSubType = SubTypevalue;
		this.ExpectedMemory = Memoryvalue;
		this.ExpectedDisplayType = DisplayTypevalue;
		this.ExpectedWeight = Weightvalue;
		this.ExpectedProcessor = Processorvalue;
		this.ExpectedHardDrive = HardDrivevalue;
		this.ExpectedPorts = Portsvalue;
		this.ExpectedBattery = Batteryvalue;
		this.ExpectedOperatingSystem = OperatingSystemvalue;
		this.testName = "SWAT-2173";
		super.serviceName = "MobileProduct";
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
			String serviceURL = testData.envData.getMobileGetProductDomain()
					+ ContextParameter;
			// verify if the reponse comes back without issues
			if (ContextString.equals("")) {
				super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
				assert super.serviceStatus.equals("500");
				/*String httpResult = hCommon.HttpRequest(serviceURL);
				assert httpResult.equals("{}");*/
			} else {
				super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
				assert super.serviceStatus.equals("200");
				String httpResult;

				httpResult = hCommon.HttpRequest(serviceURL);

				
				try {
					name = hCommon.getJsonValue(httpResult, "name");
				} catch (Exception e) {
					name = hCommon.getJsonValue(httpResult, "seoHeading");
				}

				assert name.contains(expectedProductName) : "Actual name is "
						+ name;
				try {
					subType = hCommon.getJsonValue(httpResult, "subType");
				} catch (Exception e) {
					Dailylog.logInfoDB(1,"No subtype displayed", Store, testName);
				}

				assert subType.contains(expectedSubType) : "Actual subType is "
						+ subType;
				
				
				// Memory, DisplayType, Weight, Processor, HardDrive, Ports,
				// Battery, OperatingSystem

				String attributeJSON = hCommon.getJsonValue(httpResult,
						"classAttributes");
				/*String priceJSON = hCommon.getJsonValue(productJSON, "price");
				String stockDataJSON = hCommon.getJsonValue(productJSON,
						"stockData");*/
				if (!ExpectedMemory.equals("")) {
					String memoryJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:LOIS_SCA_MEM");
					if (!memoryJSON.contains("{")) {
						assert trimString(memoryJSON).contains(
								trimString(ExpectedMemory)) : "Actual memory is "
								+ memoryJSON;
					} else {
						Memory = hCommon.getJsonValue(memoryJSON, "value");

						assert trimString(Memory).contains(
								trimString(ExpectedMemory)) : "Actual memory is "
								+ Memory;
					}

				}

				if (!ExpectedDisplayType.equals("")) {
					String displayJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:LOIS_SCA_DPY");
					if (!displayJSON.contains("{")) {
						assert trimString(displayJSON).contains(
								trimString(ExpectedDisplayType)) : "Actual display is "
								+ displayJSON;

					} else {
						DisplayType = hCommon
								.getJsonValue(displayJSON, "value");

						assert trimString(DisplayType).contains(
								trimString(ExpectedDisplayType)) : "Actual Display is "
								+ DisplayType;
					}

				}
				if (!ExpectedWeight.equals("")) {
					String weightJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:MNL_SCA_MKT_WEIGHT");
					if (!weightJSON.contains("{")) {
						assert trimString(weightJSON).contains(
								trimString(ExpectedWeight)) : "Actual weight is "
								+ weightJSON;
					} else {
						Weight = hCommon.getJsonValue(weightJSON, "value");
						assert trimString(Weight).contains(
								trimString(ExpectedWeight)) : "Actual weight is "
								+ Weight;
					}

				}
				if (!ExpectedProcessor.equals("")) {
					String CPUJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:LOIS_SCA_CPU");
					if (!CPUJSON.contains("{")) {
						assert trimString(CPUJSON).contains(
								trimString(ExpectedProcessor)) : "Actual processor is "
								+ CPUJSON;
					} else {
						Processor = hCommon.getJsonValue(CPUJSON, "value");

						assert trimString(Processor).contains(
								trimString(ExpectedProcessor)) : "Actual processor is "
								+ Processor;
					}

				}
				if (!ExpectedHardDrive.equals("")) {
					String HardDriveJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:LOIS_SCA_HDD");
					HardDrive = hCommon.getJsonValue(HardDriveJSON, "value");

					assert trimString(HardDrive).contains(
							trimString(ExpectedHardDrive)) : "Actual hardDrive is "
							+ HardDrive;

				}
				if (!ExpectedPorts.equals("")) {
					String PortsJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:MNL_SCA_MKT_ATTR1");
					Ports = hCommon.getJsonValue(PortsJSON, "value");
					// bugs!!
					// assert
					// trimString(Ports).contains(trimString(ExpectedPorts));
				}
				if (!ExpectedBattery.equals("")) {
					String BatteryJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:MNL_SCA_MKT_ATTR2");
					Battery = hCommon.getJsonValue(BatteryJSON, "value");

					// assert
					// trimString(Battery).contains(trimString(ExpectedBattery));
				}
				if (!ExpectedOperatingSystem.equals("")) {
					String OSJSON = hCommon.getJsonValue(attributeJSON,
							"1000001:LOIS_SCA_OPSYS");
					if (!OSJSON.contains("{")) {
						assert trimString(OSJSON).contains(
								trimString(ExpectedOperatingSystem)) : "Actual OS is "
								+ OSJSON;
					} else {
						OperatingSystem = hCommon.getJsonValue(OSJSON, "value");

						assert trimString(OperatingSystem).contains(
								trimString(ExpectedOperatingSystem)) : "Actual operating system is "
								+ OperatingSystem;
					}

				}
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