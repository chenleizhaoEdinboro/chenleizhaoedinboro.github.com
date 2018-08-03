package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT1168Test;
import TestScript.API.SWAT1169Test;
import TestScript.API.SWAT1374Test;
import TestScript.API.SWAT1400Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1400 {
	@DataProvider(name = "SWAT1400")
	public static Object[][] DataServiceDemo() {
		return Common
				.getFactoryData(
						new Object[][] {

								{
										"US",
										"80X7008JUS",

										"US|B2C|USWEB|EN",
										"2",
										"?codes=80X7008JUS__1&contextString=US%7CB2C%7CUSWEB%7CEN",
										"?codes=80X7008JUS&contextString=US%7CB2C%7CUSWEB%7CEN",
										"80X7008JUS__1:1", },

								{
										"US",
										"80X7008JUS",

										"US|B2C|USWEB|EN",
										"0",
										"?codes=80X7008JUS__0&contextString=US%7CB2C%7CUSWEB%7CEN",
										"?codes=80X7008JUS&contextString=US%7CB2C%7CUSWEB%7CEN",
										"80X7008JUS__0:1", },
								{
										"US",
										"80X7008JUS",

										"US|B2C|USWEB|EN",
										"1000",
										"?codes=80X7008JUS__500&contextString=US%7CB2C%7CUSWEB%7CEN",
										"?codes=80X7008JUS&contextString=US%7CB2C%7CUSWEB%7CEN",
										"80X7008JUS__500:1", },
								{
										"US",
										"80X7008JUS",

										"US|B2C|USWEB|EN",
										"-1000",
										"?codes=80X7008JUS__-500&contextString=US%7CB2C%7CUSWEB%7CEN",
										"?codes=80X7008JUS&contextString=US%7CB2C%7CUSWEB%7CEN",
										"80X7008JUS__-500:1", },
								{
										"AU",
										"81B0003KAU",

										"AU|B2C|AUWEB|EN",
										"2",
										"?codes=81B0003KAU__1&contextString=AU%7CB2C%7CAUWEB%7CEN",
										"?codes=81B0003KAU&contextString=AU%7CB2C%7CAUWEB%7CEN",
										"81B0003KAU__1:1", },
								{
										"JP",
										"80VV00BYJP",

										"JP|B2C|JPWEB|JA",
										"2",
										"?codes=80VV00BYJP__1&contextString=JP%7CB2C%7CJPWEB%7CJA",
										"?codes=80VV00BYJP&contextString=JP%7CB2C%7CJPWEB%7CJA",
										"80VV00BYJP__1:1", },
								{
										"MX",
										"20H9000TLM",

										"MX|B2C|MXWEB|ES",
										"2",
										"?codes=20H9000TLM__1&contextString=MX%7CB2C%7CMXWEB%7CES",
										"?codes=20H9000TLM&contextString=MX%7CB2C%7CMXWEB%7CES",
										"20H9000TLM__1:1", },

						/*
						 * { "US", "80X7008JUS",
						 * 
						 * "US|B2C|USWEB|EN", "-2000",
						 * "?codes=80X7008JUS__-1000&contextString=US%7CB2C%7CUSWEB%7CEN"
						 * ,
						 * "?codes=80X7008JUS&contextString=US%7CB2C%7CUSWEB%7CEN"
						 * , "80X7008JUS__-1000:1", },
						 */

						}, PropsUtils.getTargetStore("SWAT1400"));
	}

	@Factory(dataProvider = "SWAT1400")
	public Object[] createTest(String countryValue, String productNo,
			String context, String modifiedAmount, String parameter,
			String parameter2, String updateResult) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1400Test(countryValue, productNo, context,
				modifiedAmount, parameter, parameter2, updateResult);

		return tests;
	}
}
