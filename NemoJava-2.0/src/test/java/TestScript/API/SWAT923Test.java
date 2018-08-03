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

public class SWAT923Test extends ServiceSuperTestClass {

	private String service1;
	private String ContextString;
	private String ContextParameter;
	private String TypeValue;
	private String codeValue;
	private String expectedCodeValue;
	private String productCode;
	private String tempCode;
	private String tempSub;
	private String tempSubseries;
	private String tempMT;
	private String storeValue;
	private String categoryName;
	private String subCategroyName;
	private String subSeriesCode;
	private String MachineTypeCode;
	private String tempChildrenName;
	private String categoryJSON = "";
	@Autowired
	private RestTemplate restTemplate;

	public SWAT923Test(String store, String context, String contextParam,
			String category, String subCategroy, String subSeries,
			String MachineType) {
		this.Store = store;

		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.categoryName = category;
		this.subCategroyName = subCategroy;
		this.subSeriesCode = subSeries;
		this.MachineTypeCode = MachineType;

		this.testName = "SWAT-923";
		super.serviceName = "catalogAll";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "context=" + ContextString;
			String serviceURL = testData.envData.getCategoryAllServiceDomain()
					+ ContextParameter;
			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;

			httpResult = hCommon.HttpRequest(serviceURL);

			JSONArray childrenArray = JSONArray.fromObject(httpResult);
			for (int i = 0; i < childrenArray.size(); i++) {
				tempMT = hCommon.getJsonValue(childrenArray.getString(i),
						"code");
				
				if (tempMT.equals(MachineTypeCode)) {
					assert true;
					break;
				}
			}
			for (int j = 0; j < childrenArray.size(); j++) {
				subSeriesCode = hCommon.getJsonValue(childrenArray.getString(j),
						"code");
				
				if (tempMT.equals(subSeriesCode)) {
					assert true;
					break;
				}
			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}