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

public class SWAT802Test extends ServiceSuperTestClass {

	private String service1;
	private String ContextString;
	private String ContextParameter;
	private String TypeValue;
	private String codeValue;
	private String expectedCodeValue;
	private String productCode;
	private String expectedCostPice;
	private String expectedfloorPice;
	private String expectedcouponPice;
	private String expectedsalesPice;
	private String expectedlistPice;
	private String expectedwebPice;
	private String expectedtax;
	private String expectedSystemDiscount;
	private String tempcurrency;
	private String expectedCurrency;
	private String stackable;
	private String productType;
	private String storeValue;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT802Test(String store, String productNo, String context,
			String contextParam, String productT, String stackableValue,
			String costP, String floorP, String couponP, String salesP,
			String listP, String webP, String tax, String SD, String currency) {
		this.Store = store;
		this.productCode = productNo;
		this.ContextString = context;
		this.ContextParameter = contextParam;
		this.productType = productT;
		this.stackable = stackableValue;
		this.expectedCostPice = costP;
		this.expectedfloorPice = floorP;
		this.expectedcouponPice = couponP;
		this.expectedsalesPice = salesP;
		this.expectedlistPice = listP;
		this.expectedwebPice = webP;
		this.expectedtax = tax;
		this.expectedSystemDiscount = SD;
		this.expectedCurrency = currency;

		this.testName = "SWAT-802";
		super.serviceName = "getPrice";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "Product type is " + productType
					+ " ;Product Code=" + productCode + " ;context="
					+ ContextString;
			String serviceURL = testData.envData.getPriceServiceDomain()
					+ productCode + ContextParameter;

			// verify if the reponse comes back without issues

			super.serviceStatus = hCommon.verifyServiceStatus(serviceURL);
			assert super.serviceStatus.equals("200");
			String httpResult;

			httpResult = hCommon.HttpRequest(serviceURL);

			String productJSON = hCommon.getJsonValue(httpResult, productCode);
			String floorPriceJSON = hCommon.getJsonValue(productJSON,
					"FLOORPRICE");
			String costPriceJSON = hCommon.getJsonValue(productJSON,
					"COSTPRICE");

			String couponJSON = hCommon
					.getJsonValue(productJSON, "COUPONPRICE");
			String webPriceJSON = hCommon.getJsonValue(productJSON, "WEBPRICE");
			String salesPriceJSON = hCommon.getJsonValue(productJSON,
					"SALESPRICE");
			String listPriceJSON = hCommon.getJsonValue(productJSON,
					"LISTPRICE");
			String taxJSON = hCommon.getJsonValue(productJSON, "TAX");

			/*
			 * private String expectedCostPice; private String
			 * expectedfloorPice; private String expectedcouponPice; private
			 * String expectedsalesPice; private String expectedlistPice;
			 * private String expectedwebPice; private String expectedtax;
			 */

			if (!expectedCostPice.equals("")) {
				String tempCostPrice = hCommon.getJsonValue(costPriceJSON,
						"price");
				tempcurrency = hCommon.getJsonValue(costPriceJSON, "currency");

				tempCostPrice = format1(Double.parseDouble(tempCostPrice));

				assert tempCostPrice.equals(expectedCostPice) : "actual CostPrice is "
						+ tempCostPrice;
				// assert tempcurrency.equals(expectedCurrency);
			}
			if (!expectedfloorPice.equals("")) {
				String tempfloorPrice = hCommon.getJsonValue(floorPriceJSON,
						"price");
				tempcurrency = hCommon.getJsonValue(floorPriceJSON, "currency");
				tempfloorPrice = format1(Double.parseDouble(tempfloorPrice));
				assert tempfloorPrice.equals(expectedfloorPice) : "actual floorPrice is "
						+ tempfloorPrice;
				// assert tempcurrency.equals(expectedCurrency);
			}
			if (!expectedcouponPice.equals("")) {
				String tempCouponPrice = hCommon.getJsonValue(couponJSON,
						"price");
				tempcurrency = hCommon.getJsonValue(couponJSON, "currency");
				tempCouponPrice = format1(Double.parseDouble(tempCouponPrice));
				assert tempCouponPrice.equals(expectedcouponPice) : "actual  CouponPrice is "
						+ tempCouponPrice;
				assert tempcurrency.equals(expectedCurrency);

			}
			if (!expectedsalesPice.equals("")) {
				String tempSalesPrice = hCommon.getJsonValue(salesPriceJSON,
						"price");
				tempcurrency = hCommon.getJsonValue(salesPriceJSON, "currency");
				tempSalesPrice = format1(Double.parseDouble(tempSalesPrice));
				assert tempSalesPrice.equals(expectedsalesPice) : "actual  SalesPrice is "
						+ tempSalesPrice;
				assert tempcurrency.equals(expectedCurrency);

			}
			if (!expectedlistPice.equals("")) {
				String templistedPrice = hCommon.getJsonValue(listPriceJSON,
						"price");
				tempcurrency = hCommon.getJsonValue(listPriceJSON, "currency");
				templistedPrice = format1(Double.parseDouble(templistedPrice));
				assert templistedPrice.equals(expectedlistPice) : "actual listPice is "
						+ templistedPrice;
				// assert tempcurrency.equals(expectedCurrency);

			}

			if (!expectedwebPice.equals("")) {
				String tempWebPrice = hCommon.getJsonValue(webPriceJSON,
						"price");
				tempcurrency = hCommon.getJsonValue(webPriceJSON, "currency");
				tempWebPrice = format1(Double.parseDouble(tempWebPrice));
				assert tempWebPrice.equals(expectedwebPice) : "actual webPice is "
						+ tempWebPrice;
				// assert tempcurrency.equals(expectedCurrency);

			}

			if (!expectedtax.equals("")) {
				String tempTaxPrice = hCommon.getJsonValue(taxJSON, "price");
				tempcurrency = hCommon.getJsonValue(taxJSON, "currency");
				tempTaxPrice = format1(Double.parseDouble(tempTaxPrice));
				assert tempTaxPrice.equals(expectedtax) : "actual tax is "
						+ tempTaxPrice;
				// assert tempcurrency.equals(expectedCurrency);

			}
			if (productType.equals("Option")) {
				String SystemDiscountJSON = hCommon.getJsonValue(productJSON,
						"SYSTEMDISCOUNT");
				String priceRuleJSON = hCommon.getJsonValue(SystemDiscountJSON,
						"priceRule");

				String tempSystemDiscount = hCommon.getJsonValue(priceRuleJSON,
						"operationValue");
				tempcurrency = hCommon.getJsonValue(SystemDiscountJSON,
						"currency");

				tempSystemDiscount = format1(Double
						.parseDouble(tempSystemDiscount));
				assert tempSystemDiscount.equals(expectedSystemDiscount) : "actual SystemDiscount is "
						+ tempSystemDiscount;
				assert tempcurrency.equals(expectedCurrency);

			}

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public static String format1(double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.toString();
	}
}