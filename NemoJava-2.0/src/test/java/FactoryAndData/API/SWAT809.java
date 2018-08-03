package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT809 {
	@DataProvider(name = "SWAT809")
	public static Object[][] DataServiceDemo() {
		return Common
				.getFactoryData(
						new Object[][] {

								{
										"US",
										"8981822210049",
										"US|B2C|USWEB|EN",
										"?contextString=US%7CB2C%7CUSWEB%7CEN",
										"Model", "LE SINGAPORE", "20K70005SG",
										"9010902859777" },
								{
										"CA",
										"8893584408577",
										"CA|B2C|CAWEB|EN",
										"?contextString=CA%7CB2C%7CCAWEB%7CEN",
										"Model", "LE TAIWAN", "80Q1003RTW",
										"8958064623617" },
								{
										"CO",
										"8956068757505",
										"CO|B2C|COWEB|EN",
										"?contextString=CO%7CB2C%7CCOWEB%7CES",
										"Model", "LE KOREA", "80Y7001VKR",
										"9001242296321" },
								{
										"MX",
										"8892058894337",
										"MX|B2C|MXWEB|ES",
										"?contextString=MX%7CB2C%7CMXWEB%7CES",
										"Model",
										"LE,NA,SINGAPORE,JAPAN,KOREA,USOUTLET,WE,INDIA",
										"22TP2WPWP4020GQ", "8892058927105" },
								{
										"AU",
										"8893584408577",
										"AU|B2C|AUWEB|EN",
										"?contextString=AU%7CB2C%7CAUWEB%7CEN",
										"Model", "LE USOUTLET", "80Q1000EUS",
										"8898316468225" },
								{
										"NZ",
										"8893580869633",
										"NZ|B2C|NZWEB|EN",
										"?contextString=NZ%7CB2C%7CNZWEB%7CEN",
										"Model", "LE AUSTRALIA NEW_ZEALAND",
										"80TY001KCF", "8921802506241" },
								{
										"JP",
										"8974005043201",
										"JP|B2C|JPWEB|JA",
										"?contextString=JP%7CB2C%7CJPWEB%7CJA",
										"Model", "LE NETHERLANDS",
										"80XH01H7MH", "9002584834049" },
								{
										"SG",
										"8947743850497",
										"SG|B2C|SGWEB|EN",
										"?contextString=SG%7CB2C%7CSGWEB%7CEN",
										"Model",
										"LE,SWITZERLAND",
										"20JH002MMZ", "8978532990977" },
								{
										"HK",
										"8997527158785",
										"HK|B2C|HKWEB|EN",
										"?contextString=HK%7CB2C%7CHKWEB%7CEN",
										"CTO", "UK", "20LFCTO1WWENGB0",
										"9021769580545" },
							
								{
										"GB",
										"8950790848513",
										"GB|B2C|GBWEB|EN",
										"?contextString=GB%7CB2C%7CGBWEB%7CEN",
										"Model", "LE,USOUTLET", "80XC0047US",
										"9002875715585" },

						}, PropsUtils.getTargetStore("SWAT809"));
	}

	@Factory(dataProvider = "SWAT809")
	public Object[] createTest(String countryValue, String parentID,
			String contextString, String contextParameter, String modelName,
			String visibility, String productNo, String productID) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT809Test(countryValue, parentID, contextString,
				contextParameter, modelName, visibility, productNo, productID);

		return tests;
	}
}
