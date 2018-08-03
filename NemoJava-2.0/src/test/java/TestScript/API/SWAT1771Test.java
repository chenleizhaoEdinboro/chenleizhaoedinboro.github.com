package TestScript.API;

import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

public class SWAT1771Test extends ServiceSuperTestClass {

	private String service1;
	private String ContextString;
	private String ContextParameter;
	private String TypeValue;
	private String codeValue;
	private String expectedCodeValue;
	private String productCode;
	private String expectedCha1;
	private String expectedChaVal1;
	private String expectedcouponPice;
	private String expectedsalesPice;
	private String expectedlistPice;
	private String expectedwebPice;
	private String expectedtax;
	private String expectedSystemDiscount;
	private String cvListPrice;
	private String expectedCurrency;
	private String cvType;
	private String cvWebPrice;
	private String productType;
	private String storeValue;
	private String cha1JSON;
	private boolean isJSONExist = true;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1771Test(String store, String productNo, String context,
			String contextParam, String productT, String cvType, String cha1,
			String chavalue1, String couponP, String salesP, String listP,
			String webP, String tax, String SD, String currency) {
		this.Store = store;
		this.productCode = productNo;
		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.productType = productT;
		this.cvType = cvType;
		this.expectedCha1 = cha1;
		this.expectedChaVal1 = chavalue1;
		this.expectedcouponPice = couponP;
		this.expectedsalesPice = salesP;
		this.expectedlistPice = listP;
		this.expectedwebPice = webP;
		this.expectedtax = tax;
		this.expectedSystemDiscount = SD;
		this.expectedCurrency = currency;

		this.testName = "SWAT-1171";
		super.serviceName = "getCVPrice";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "Product type is " + productType
					+ " ;Product Code=" + productCode + ";CV type is: "
					+ cvType + " ;context=" + ContextString;
			String serviceURL = testData.envData.getPriceServiceDomain()
					+ productCode + ContextParameter;

			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;

			httpResult = hCommon.HttpRequest(serviceURL);

			String productJSON = hCommon.getJsonValue(httpResult, productCode);
			String ChaJSON = hCommon.getJsonValue(productJSON, "CHARVALPRICE");

			/*
			 * private String expectedCostPice; private String
			 * expectedfloorPice; private String expectedcouponPice; private
			 * String expectedsalesPice; private String expectedlistPice;
			 * private String expectedwebPice; private String expectedtax;
			 */

			if (!expectedCha1.equals("")) {
				String cvPriceJSON = hCommon.getJsonValue(ChaJSON, "cvPrices");
				try {
					cha1JSON = hCommon.getJsonValue(cvPriceJSON, expectedCha1
							+ ":" + expectedChaVal1);

				} catch (Exception e) {

					isJSONExist = false;

				}
				assert isJSONExist : "CV rule missing-" + expectedCha1 + ":"
						+ expectedChaVal1;

			}

			if (isJSONExist) {
				cvListPrice = hCommon.getJsonValue(cha1JSON, "cvListPrice");

				cvWebPrice = hCommon.getJsonValue(cha1JSON, "cvWebPrice");
				if (this.Store.equals("FR")) {

					cvListPrice = cvListPrice.replaceAll("\\.", "");
					cvListPrice = cvListPrice.replaceAll("\\,", "\\.");
					cvListPrice = cvListPrice.replaceAll("[^0-9.]", "");
					cvWebPrice = cvWebPrice.replaceAll("\\.", "");
					cvWebPrice = cvWebPrice.replaceAll("\\,", "\\.");
					cvWebPrice = cvWebPrice.replaceAll("[^0-9.]", "");

				} else if (this.Store.equals("MX")) {

					cvListPrice = cvListPrice.replaceAll("\\,", "");
					cvWebPrice = cvWebPrice.replaceAll("\\,", "");
				}

				assert (GetPriceValue(cvWebPrice) + "")
						.equals(GetPriceValue(cvListPrice) - 10 + "") : "actual cvwebprice is "
						+ cvWebPrice;

			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public float GetPriceValue(String Price) {

		Price = Price.replaceAll("[^0-9.]", "");
		float priceValue;

		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

	public static String format1(double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.toString();
	}
}