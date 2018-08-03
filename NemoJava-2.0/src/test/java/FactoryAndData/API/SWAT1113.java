package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.NA28797Test;
import TestScript.API.SWAT1113Test;
import TestScript.B2C.NA15492Test;

public class SWAT1113 {
	@DataProvider(name = "SWAT1113")
	public static Object[][] Data1113() {
		return Common
				.getFactoryData(
						new Object[][] {

								{
										"US",
										"60DFAAR1US",
										"US|B2C|USWEB|EN",
										"?codes=60DFAAR1US&contextString=US%7CB2C%7CUSWEB%7CEN%20",
										"", "ThinkVision E2054 19.5\" Monitor",
										"environmental and budget-friendly",
										"1 Year", "1440x900", "VGA", "IPS",
										"1000:1", "14.65", "17.95", "119.00",
										"0", "forceOutOfStock", "" },
								{
										"CA",
										"60DFAAR1US",
										"CA|B2C|WEBCA|EN",
										"?codes=60DFAAR1US&contextString=CA%7CB2C%7CWEBCA%7CEN",
										"", "ThinkVision E2054 19.5\" Monitor",
										"environmental and budget-friendly",
										"1 Year", "1440x900", "VGA", "IPS",
										"1000:1", "14.65", "17.95", "164.00",
										"0", "forceOutOfStock", "144.99" },
								{
										"JP",
										"4X20E53339",
										"JP|B2C|JPWEB|JA",
										"?codes=4X20E53339&contextString=JP%7CB2C%7CJPWEB%7CJA",
										"ThinkCentre Tiny用65W ACアダプター", "", "",
										"1 Year", "", "", "", "", "", "",
										"4,320", "0", "", "" },
								{
										"AU",
										"65C5KAC1AU",
										"AU|B2C|AUWEB|EN",
										"?codes=65C5KAC1AU&contextString=AU%7CB2C%7CAUWEB%7CEN",
										"LI2264d-21.5\" Monitor", "", "", "",
										"1920 x 1080", "", "IPS", "1000:1", "",
										"", "159.00", "0", "", "" },
								{
										"HK",
										"60DFAAR1WW",
										"HK|B2C|HKWEB|ZF_HK",
										"?codes=60DFAAR1WW&contextString=HK%7CB2C%7CHKWEB%7CZF_HK",
										"E2054(E2054A)-19.5 inch Monitor", "", "LCD 顯示器", "1 年",
										"1440 x 900", "VGA", "IPS", "1000:1", "14.65in",
										"17.95in", "890.00", "0", "", "" },
								{
											"FR",
											"61B1JAT1EU",
											"HK|B2C|HKWEB|ZF_HK",
											"?codes=60DFAAR1WW&contextString=HK%7CB2C%7CHKWEB%7CZF_HK",
											"Backlit LCD Monitor", "", "Le moniteur Lenovo ThinkVision", "1 年",
											"1440 x 900", "VGA", "IPS", "1000:1", "14.65in",
											"17.95in", "890.00", "0", "", "" },			

						}, PropsUtils.getTargetStore("SWAT1113"));
	}

	@Factory(dataProvider = "SWAT1113")
	public Object[] createTest(String store, String productNo,
			String contextString, String contexPara, String name,
			String seoTitle, String Description, String Warranty,
			String Resolution, String connection, String DisplayType,
			String ContrastRatio, String Height, String Width, String Webprice,
			String available, String StockStatus, String SalesPrice) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1113Test(store, productNo, contextString,
				contexPara, name, seoTitle, Description, Warranty, Resolution,
				connection, DisplayType, ContrastRatio, Height, Width,
				Webprice, available, StockStatus, SalesPrice);

		return tests;
	}
}
