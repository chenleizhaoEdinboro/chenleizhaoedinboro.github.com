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

public class SWAT1113Test extends ServiceSuperTestClass {

	private String ContextString;
	private String productNo;
	private String ContextParameter;
	private String Resolution;
	private String seoname;
	private String name;
	private String expectedName;
	private String expectedSEOTitle;
	private String description;
	private String expectedDescription;
	private String connector;
	private String Expectedconnection;
	private String Warranty;
	private String ExpectedWarranty;
	private String DisplayType;
	private String ExpectedResolution;
	private String Height;
	private String ExpectedDisplayType;
	private String ContrastRatio;
	private String ExpectedContrastRatio;
	private String Width;
	private String ExpectedHeight;
	private String ExpectedSalesPrice;
	private String ExpectedWidth;
	private String OperatingSystem;
	private String ExpectedWebprice;
	private String ExpectedAvailability;
	private String ExpectedStockStatus;
	private String codeValue;
	private String expectedCodeValue;
	private String productCode;
	// seoTitle, Description, Warranty, Resolution, connection, DisplayType,
	// ContrastRatio, Height, Width, Webprice, available, StockStatus
	private String storeValue;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1113Test(String store, String productNo, String context,
			String contextParam, String name, String seoTitle,
			String Description, String Warranty, String Resolution,
			String connection, String DisplayType, String ContrastRatio,
			String Height, String Width, String Webprice, String available,
			String StockStatus, String Sales) {
		this.Store = store;
		this.productNo = productNo;
		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.expectedName = name;
		this.expectedSEOTitle = seoTitle;
		this.expectedDescription = Description;
		this.ExpectedWarranty = Warranty;
		this.ExpectedResolution = Resolution;
		this.Expectedconnection = connection;
		this.ExpectedDisplayType = DisplayType;
		this.ExpectedContrastRatio = ContrastRatio;
		this.ExpectedHeight = Height;
		this.ExpectedWidth = Width;
		this.ExpectedWebprice = Webprice;
		this.ExpectedAvailability = available;
		this.ExpectedAvailability = StockStatus;
		this.ExpectedSalesPrice = Sales;
		this.testName = "NA-28797";
		super.serviceName = "Product(Accessory)";
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
			if (ContextString.equals("")) {

				super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
				assert super.serviceStatus.equals("200");
				String httpResult = hCommon.HttpRequest(serviceURL);
				assert httpResult.equals("{}");
			} else {

				super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);

				assert super.serviceStatus.equals("200");
				String httpResult;

				httpResult = hCommon.HttpRequest(serviceURL);

				String productJSON = hCommon
						.getJsonValue(httpResult, productNo);

				String modelJSON = hCommon.getJsonValue(productJSON, "model");
				if (!expectedName.equals("")) {
					seoname = hCommon.getJsonValue(modelJSON, "name");
					assert seoname.equals(expectedName) : "Actual name is " + name;
				}
				if (!expectedSEOTitle.equals("")) {
					seoname = hCommon.getJsonValue(modelJSON, "seoTitle");
					assert seoname.equals(expectedSEOTitle) : "Actual seoTitle is "
							+ seoname;
				}
				if (!expectedDescription.equals("")) {
					description = hCommon.getJsonValue(modelJSON,
							"description");
					assert description.contains(expectedDescription) : "Actual Description is "
							+ description;
				}
				// Memory, DisplayType, Weight, Processor, HardDrive, Ports,
				// Battery, OperatingSystem

				String attributeJSON = hCommon.getJsonValue(productJSON,
						"classAttributes");
				String priceJSON = hCommon.getJsonValue(productJSON, "price");
				String stockDataJSON = hCommon.getJsonValue(productJSON,
						"stockData");
				if (!ExpectedWarranty.equals("")) {
					String WarrantyJSON = hCommon.getJsonValue(attributeJSON,
							"200001:LOIS_ACC_WARRANTYTYPE");
					if (!WarrantyJSON.contains("{")) {
						assert trimString(WarrantyJSON).contains(
								trimString(ExpectedWarranty)) : "Actual Warranty is "
								+ WarrantyJSON;
					} else {
						Warranty = hCommon.getJsonValue(WarrantyJSON, "value");

						assert trimString(Warranty).contains(
								trimString(ExpectedWarranty)) : "Actual memory is "
								+ Warranty;
					}

				}

				if (!ExpectedResolution.equals("")) {
					String ResolutionJSON = hCommon.getJsonValue(attributeJSON,
							"9000002:LOIS_FACEACC_MAXRESOLUTION");
					if (!ResolutionJSON.contains("{")) {
						assert trimString(ResolutionJSON).contains(
								trimString(ExpectedResolution)) : "Actual Resolution is "
								+ ResolutionJSON;

					} else {
						Resolution = hCommon.getJsonValue(ResolutionJSON,
								"value");

						assert trimString(Resolution).contains(
								trimString(ExpectedResolution)) : "Actual Resolution is "
								+ Resolution;
					}

				}
				if (!Expectedconnection.equals("")) {
					String connectionJSON = hCommon.getJsonValue(attributeJSON,
							"200001:LOIS_ACC_CONNECTOR");
					if (!connectionJSON.contains("{")) {
						assert trimString(connectionJSON).contains(
								trimString(Expectedconnection)) : "Actual connector is "
								+ connectionJSON;
					} else {
						connector = hCommon.getJsonValue(connectionJSON,
								"value");
						assert trimString(connector).contains(
								trimString(Expectedconnection)) : "Actual connector is "
								+ connector;
					}

				}
				if (!ExpectedDisplayType.equals("")) {
					String DisplayTypeJSON = hCommon.getJsonValue(
							attributeJSON, "9000002:LOIS_FACEACC_DISPLAYTYPE");
					if (!DisplayTypeJSON.contains("{")) {
						assert trimString(DisplayTypeJSON).contains(
								trimString(ExpectedDisplayType)) : "Actual DisplayType is "
								+ DisplayTypeJSON;
					} else {
						DisplayType = hCommon.getJsonValue(DisplayTypeJSON,
								"value");

						assert trimString(DisplayType).contains(
								trimString(ExpectedDisplayType)) : "Actual DisplayType is "
								+ DisplayType;
					}

				}
				if (!ExpectedContrastRatio.equals("")) {
					String ContrastRatioJSON = hCommon.getJsonValue(
							attributeJSON, "200001:LOIS_ACC_CONTRASTRATIO");
					if (!ContrastRatioJSON.contains("{")) {
						assert trimString(ContrastRatioJSON).contains(
								trimString(ExpectedContrastRatio)) : "Actual ContrastRatio is "
								+ ContrastRatioJSON;
					} else {
						ContrastRatio = hCommon.getJsonValue(ContrastRatioJSON,
								"value");

						assert trimString(ContrastRatio).contains(
								trimString(ExpectedContrastRatio)) : "Actual ContrastRatio is "
								+ ContrastRatio;
					}

				}

				if (!ExpectedHeight.equals("")) {
					String HeightJSON = hCommon.getJsonValue(attributeJSON,
							"200001:LOIS_ACC_HEIGHT_US");
					if (!HeightJSON.contains("{")) {
						assert trimString(HeightJSON).contains(
								trimString(ExpectedHeight)) : "Actual Height is "
								+ HeightJSON;
					} else {
						Height = hCommon.getJsonValue(HeightJSON, "value");
						// bugs!!
						assert trimString(Height).contains(
								trimString(ExpectedHeight)) : "Actual Height is "
								+ Height;
					}

				}
				if (!ExpectedWidth.equals("")) {
					String WidthJSON = hCommon.getJsonValue(attributeJSON,
							"200001:LOIS_ACC_WIDTH_US");
					if (!WidthJSON.contains("{")) {
						assert trimString(WidthJSON).contains(
								trimString(ExpectedWidth)) : "Actual Width is "
								+ WidthJSON;
					} else {
						Width = hCommon.getJsonValue(WidthJSON, "value");

						assert trimString(Width).contains(
								trimString(ExpectedWidth)) : "Actual Width is "
								+ Width;
					}

				}

				if (!ExpectedWebprice.equals("")) {
					String Webprice = hCommon.getJsonValue(priceJSON,
							"webPrice");

					assert trimString(Webprice).contains(
							trimString(ExpectedWebprice)) : "Actual Webprice is "
							+ Webprice;

				}
				if (!ExpectedSalesPrice.equals("")) {
					String salesprice = hCommon.getJsonValue(priceJSON,
							"salesPrice");
					// salesprice is null bug
					assert trimString(salesprice).contains(
							trimString(ExpectedSalesPrice)) : "Actual salesprice is "
							+ salesprice;

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