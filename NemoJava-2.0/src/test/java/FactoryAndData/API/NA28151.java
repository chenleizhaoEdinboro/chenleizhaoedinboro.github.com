package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.B2C.NA15492Test;

public class NA28151 {
	@DataProvider(name = "NA28151")
	public static Object[][] DataServiceDemo() {
		return Common
				.getFactoryData(
						new Object[][] {

								{
										"US",
										"8981822210049",
										"US|B2C|USWEB|EN",
										"?contextString=US%7CB2C%7CUSWEB%7CEN",
										"Model", "LE NA LA", "20HES0FA1R_TEST",
										"LIXEUS0001" },
								{
										"CA",
										"8893584408577",
										"CA|B2C|CAWEB|EN",
										"?contextString=CA%7CB2C%7CCAWEB%7CEN",
										"Model", "LE USOUTLET", "20HES0FA1R_TEST",
										"LIXECA0001" },
								{
										"CO",
										"8956068757505",
										"CO|B2C|COWEB|EN",
										"?contextString=CO%7CB2C%7CCOWEB%7CES",
										"Model", "LE CO", "20HES0FA1R_TEST",
										"LIXECO0001" },
							{
										"MX",
										"8892058894337",
										"MX|B2C|MXWEB|ES",
										"?contextString=MX%7CB2C%7CMXWEB%7CES",
										"Model", "LE NA LA", "20HES0FA1R_TEST",
										"LIXE20180304" },
								{
										"AU",
										"8893584408577",
										"AU|B2C|AUWEB|EN",
										"?contextString=AU%7CB2C%7CAUWEB%7CEN",
										"Model", "LE PUBLIC_GLOBAL_B2C_UNIT",
										"20HES0FA1R_TEST", "LIXEAU0001" },
								{
										"NZ",
										"8893580869633",
										"NZ|B2C|NZWEB|EN",
										"?contextString=NZ%7CB2C%7CNZWEB%7CEN",
										"Model", "LE AUSTRALIA NEW_ZEALAND",
										"20HES0FA1R_TEST", "LIXENZ0001" },
								{
										"JP",
										"8974005043201",
										"JP|B2C|JPWEB|JA",
										"?contextString=JP%7CB2C%7CJPWEB%7CJA",
										"Model", "LE JAPAN", "20HES0FA1R_TEST",
										"LIXEJP0001" },
								{
										"SG",
										"8947743850497",
										"SG|B2C|SGWEB|EN",
										"?contextString=SG%7CB2C%7CSGWEB%7CEN",
										"CTO",
										"LE,SINGAPORE",
										"20HES0FA1R_TEST", "LIXESG0001" },
								{
										"HK",
										"8997527158785",
										"HK|B2C|HKWEB|EN",
										"?contextString=HK%7CB2C%7CHKWEB%7CEN",
										"CTO", "HONGKONG", "20HES0FA1R_TEST",
										"LIXEHK0001" },
							
								{
										"GB",
										"8950790848513",
										"GB|B2C|GBWEB|EN",
										"?contextString=GB%7CB2C%7CGBWEB%7CEN",
										"Model", "LE,UKI", "20HES0FA1R_TEST",
										"LIXEGB0001" },

						}, PropsUtils.getTargetStore("NA28151"));
	}

	@Factory(dataProvider = "NA28151")
	public Object[] createTest(String countryValue, String parentID,
			String contextString, String contextParameter, String modelName,
			String visibility, String productNo, String productID) {

		Object[] tests = new Object[1];

		tests[0] = new NA28151Test(countryValue, parentID, contextString,
				contextParameter, modelName, visibility, productNo, productID);

		return tests;
	}
}
