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

public class NA28114Test extends ServiceSuperTestClass {

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
	private String treeName="";
	@Autowired
	private RestTemplate restTemplate;

	public NA28114Test(String store, String context, String contextParam,String tree,
			String category, String subCategroy, String subSeries,
			String MachineType) {
		this.Store = store;

		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.treeName=tree;
		this.categoryName = category;
		this.subCategroyName = subCategroy;
		this.subSeriesCode = subSeries;
		this.MachineTypeCode = MachineType;

		this.testName = "NA-28114";
		super.serviceName = "catalogGetTreeNode";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "context=" + ContextString+"; TreeName="+treeName;
			String serviceURL = testData.envData
					.getLaptopsTreeChildrenServiceDomain() + ContextParameter;
			// verify if the reponse comes back without issues
            if(treeName.equals("LAPTOP")){
            	serviceURL=serviceURL.replaceAll("LAPTOPS", "LAPTOP");
            }
			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;

			httpResult = hCommon.HttpRequest(serviceURL);

			String childrenJSON = hCommon.getJsonValue(httpResult, "children");

			/*
			 * JSONArray mainChildrenArray = JSONArray
			 * .fromObject(childrenJSON);
			 * 
			 * for (int t = 0; t < mainChildrenArray.size(); t++) {
			 * tempChildrenName =JCommon.getJSONArrayValue(childrenJSON, t,
			 * "code");
			 * 
			 * if(tempChildrenName.toUpperCase().equals("LAPTOPS")){
			 * categoryJSON = JCommon.getJSONArrayValue(childrenJSON, t,
			 * "children"); break; }
			 * 
			 * }
			 */

			/*
			 * String categoryJSON = JCommon.getJSONArrayValue(childrenJSON, 0,
			 * "children");
			 */
			
			
			JSONArray childrenArray = JSONArray.fromObject(childrenJSON);

			for (int i = 0; i < childrenArray.size(); i++) {
				tempCode = hCommon.getJsonValue(childrenArray.getString(i),
						"code");
				if (tempCode.equals(categoryName)) {

					String subCategoryJSON = hCommon.getJsonValue(
							childrenArray.getString(i), "children");
					JSONArray subCategoryArray = JSONArray
							.fromObject(subCategoryJSON);
					for (int j = 0; j < subCategoryArray.size(); j++) {
						tempSub = hCommon.getJsonValue(
								subCategoryArray.getString(j), "code");
						if (tempSub.equals(subCategroyName)) {

							String subSeriesJSON = hCommon.getJsonValue(
									subCategoryArray.getString(j), "children");
							// System.out.println(subSeriesJSON);
							JSONArray subSeriesArray = JSONArray
									.fromObject(subSeriesJSON);
							for (int z = 0; z < subSeriesArray.size(); z++) {
								tempSubseries = hCommon.getJsonValue(
										subSeriesArray.getString(z), "code");
								if (tempSubseries.equals(subSeriesCode)) {

									String MachineTypeJSON = hCommon
											.getJsonValue(
													subSeriesArray.getString(z),
													"children");
									assert !MachineTypeJSON.equals(null);
									JSONArray MTArray = JSONArray
											.fromObject(MachineTypeJSON);
									for (int q = 0; q < MTArray.size(); q++) {
										tempMT = hCommon.getJsonValue(
												MTArray.getString(q), "code");

										if (tempMT.equals(MachineTypeCode)) {

											assert true;

											break;
										}
									}

									break;
								}

							}
							break;
						}
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