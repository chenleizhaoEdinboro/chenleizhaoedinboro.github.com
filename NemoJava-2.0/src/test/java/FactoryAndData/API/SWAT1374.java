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
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1374 {
	@DataProvider(name = "SWAT1374")
	public static Object[][] DataServiceDemo() {
		return Common
				.getFactoryData(
						new Object[][] {

								{
										"US",
										"80X7008JUS",

										"US|B2C|USWEB|EN",
										"?codes=80X7008JUS&contextString=US%7CB2C%7CUSWEB%7CEN",
										"MTM", "1000", "22", "notSpecified" },
								{
										"US",
										"20GQCTO1WWENUS0",

										"US|B2C|USWEB|EN",
										"?codes=20GQCTO1WWENUS0&contextString=US%7CB2C%7CUSWEB%7CEN",
										"CTO", "", "", "" },

								{
										"US_OUTLET",
										"80EC00N4US",

										"US|B2C|OUTLETUS|EN",
										"?codes=80EC00N4US&contextString=US%7CB2C%7Coutletus%7CEN",
										"MTM", "0", "0", "" },
								{
										"GB",
										"80XC003WUK",

										"GB|B2C|GBWEB|EN",
										"?codes=80XC003WUK&contextString=GB%7CB2C%7CGBWEB%7CEN",
										"MTM", "0", "0", "Out Of Stock" },
								{
										"JP",
										"80VV00BYJP",

										"JP|B2C|JPWEB|JA",
										"?codes=80VV00BYJP&contextString=JP%7CB2C%7CJPWEB%7CJA",
										"MTM", "1000", "2", "forceInStock" },
								{
										"NZ",
										"81B0003KAU",

										"NZ|B2C|NZWEB|EN",
										"?codes=81B0003KAU&contextString=NZ%7CB2C%7CNZWEB%7CEN",
										"MTM", "1000", "55", "forceInStock" },

						}, PropsUtils.getTargetStore("SWAT1374"));
	}

	@Factory(dataProvider = "SWAT1374")
	public Object[] createTest(String countryValue, String productNo,
			String context, String parameter,String producttype, String availableAmount,
			String reserved, String inStockStatus) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1374Test(countryValue, productNo, context,
				parameter,producttype, availableAmount, reserved, inStockStatus);

		return tests;
	}
}
