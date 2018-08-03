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

public class SWAT2171Test extends ServiceSuperTestClass {

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
	private String rootType;
	private String rootNo;
	private String childrenType;
	private String childrenNo;
	private String tempChildrenName;
	private String categoryJSON = "";
	private String treeDepth = "";
	private String grandChildrenNo;
	private String grandChildrenType;
	private String grandChildrenAmount;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT2171Test(String store, String context, String contextParam,
			String depth, String rootType, String rootNo, String childrenType,
			String childrenNo, String grandChildrenType,
			String grandChildrenNo, String grandChildrenAmount) {
		this.Store = store;

		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.treeDepth = depth;
		this.rootType = rootType;
		this.rootNo = rootNo;
		this.childrenType = childrenType;
		this.childrenNo = childrenNo;
		this.grandChildrenType = grandChildrenType;
		this.grandChildrenNo = grandChildrenNo;
		this.grandChildrenAmount = grandChildrenAmount;

		this.testName = "SWAT-2171";
		super.serviceName = "MobileProductTree";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "context=" + ContextString + "; TreeDepth="
					+ treeDepth + "; RootNode=" + rootType + rootNo
					+ "; ChildrenNode=" + childrenType + childrenNo
					+ "; GrandChildrenNode=" + grandChildrenType
					+ grandChildrenNo;
			String serviceURL = testData.envData.getMobileProductTreeDomain()
					+ rootNo + ContextParameter;
			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;

			httpResult = hCommon.HttpRequest(serviceURL);

			String childrenJSON = hCommon.getJsonValue(httpResult, "children");

			JSONArray childrenArray = JSONArray.fromObject(childrenJSON);

			for (int i = 0; i < childrenArray.size(); i++) {

				tempCode = hCommon.getJsonValue(childrenArray.getString(i),
						"code");
				if (tempCode.equals(childrenNo)) {

					assert hCommon.getJsonValue(childrenArray.getString(i),
							"subType").equals(childrenType);

					if (!grandChildrenNo.equals("")) {
						String subCategoryJSON = hCommon.getJsonValue(
								childrenArray.getString(i), "children");

						JSONArray subCategoryArray = JSONArray
								.fromObject(subCategoryJSON);
						for (int j = 0; j < subCategoryArray.size(); j++) {
							assert grandChildrenAmount.equals(subCategoryArray
									.size() + "") : "actual amount of grandchildren is"
									+ subCategoryArray.size();
							tempSub = hCommon.getJsonValue(
									subCategoryArray.getString(j), "code");
							if (tempSub.equals(grandChildrenNo)) {
								assert hCommon.getJsonValue(
										subCategoryArray.getString(j),
										"subType").equals(grandChildrenType);
								break;
							}
						}
						assert tempSub.equals(grandChildrenNo) : "grand children not exist";
					}

					break;
				}
			}

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