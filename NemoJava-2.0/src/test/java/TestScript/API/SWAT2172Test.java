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

public class SWAT2172Test extends ServiceSuperTestClass {

	private String stockString;
	private String priceString;
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
	private String rootType;
	private String rootNo;
	private String childrenType;
	private String childrenNo;
	private String tempChildrenName;
	private String categoryJSON = "";
	private String numberOfNode = "";
	private String availableAmount;
	private String childrenSummary;
	private String WebPrice;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT2172Test(String store, String context, String contextParam,
			String numberOfNode, String rootType, String rootNo,
			String childrenType, String childrenNo, String childrenSummary,
			String availableAmount, String WebPrice) {
		this.Store = store;

		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.numberOfNode = numberOfNode;
		this.rootType = rootType;
		this.rootNo = rootNo;
		this.childrenType = childrenType;
		this.childrenNo = childrenNo;
		this.childrenSummary = childrenSummary;
		this.availableAmount = availableAmount;
		this.WebPrice = WebPrice;

		this.testName = "SWAT-2172";
		super.serviceName = "MobileProductVariants";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "context=" + ContextString + "; RootNode="
					+ rootType + rootNo + "; ChildrenNode=" + childrenType
					+ childrenNo + "; childrenSummary=" + childrenSummary
					+ "; availableAmount=" + availableAmount + ";Webprice ="
					+ WebPrice;
			String serviceURL = testData.envData
					.getMobileProductVariantDomain()
					+ rootNo
					+ ContextParameter;
			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;

			httpResult = hCommon.HttpRequest(serviceURL);

			JSONArray childrenArray = JSONArray.fromObject(httpResult);
			assert numberOfNode.equals(childrenArray.size() + "") : "Number of children not correct";
			for (int i = 0; i < childrenArray.size(); i++) {

				tempCode = hCommon.getJsonValue(childrenArray.getString(i),
						"code");

				if (tempCode.equals(childrenNo)) {

					assert hCommon.getJsonValue(childrenArray.getString(i),
							"subType").equals(childrenType) : "actual type is"
							+ hCommon.getJsonValue(childrenArray.getString(i),
									"subType");

					assert hCommon.getJsonValue(childrenArray.getString(i),
							"summary").equals(childrenSummary) : "actual summary is "
							+ hCommon.getJsonValue(childrenArray.getString(i),
									"summary");
					stockString = hCommon.getJsonValue(
							childrenArray.getString(i), "stockData");

					priceString = hCommon.getJsonValue(
							childrenArray.getString(i), "price");

					assert hCommon.getJsonValue(stockString, "available")
							.equals(availableAmount) : "actual amount is"
							+ hCommon.getJsonValue(stockString, "available");
					assert hCommon.getJsonValue(priceString, "webPrice")
							.equals(WebPrice) : "actual webPrice is"
							+ hCommon.getJsonValue(priceString, "webPrice");

					break;
				}

			}
			assert tempCode.equals(childrenNo) : "children not found";
			/*
			 * TypeValue = JCommon.getJSONArrayValue(httpResult, 1, "type"); if
			 * (ContextString.equals("")) { assert TypeValue.equals(""); } else
			 * { assert !TypeValue.equals(""); }
			 */
			// TypeValue = hCommon.getJsonValue(httpResult, "type");

			/*
			 * Assert.assertTrue(storeID.equals(expectedID));
			 * Assert.assertTrue(codeValue.equals(expectedCodeValue));
			 */

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}