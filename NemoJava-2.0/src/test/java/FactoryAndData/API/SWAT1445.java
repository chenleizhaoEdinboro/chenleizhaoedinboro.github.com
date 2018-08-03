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
import TestScript.API.SWAT1443Test;
import TestScript.API.SWAT1445Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1445 {
	@DataProvider(name = "SWAT1445")
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
										"AU",
										"81B0003KAU",

										"AU|B2C|AUWEB|EN",
										"2",
										"?codes=81B0003KAU__1&contextString=AU%7CB2C%7CAUWEB%7CEN",
										"?codes=81B0003KAU&contextString=AU%7CB2C%7CAUWEB%7CEN",
										"81B0003KAU__1:1", },
								{
										"JP",
										"81BF0005JP",

										"JP|B2C|JPWEB|JA",
										"2",
										"?codes=81BF0005JP__1&contextString=JP%7CB2C%7CJPWEB%7CJA",
										"?codes=81BF0005JP&contextString=JP%7CB2C%7CJPWEB%7CJA",
										"81BF0005JP__1:1", },
								{
										"MX",
										"80WK0104LM",

										"MX|B2C|MXWEB|ES",
										"2",
										"?codes=80WK0104LM__1&contextString=MX%7CB2C%7CMXWEB%7CES",
										"?codes=80WK0104LM&contextString=MX%7CB2C%7CMXWEB%7CES",
										"80WK0104LM__1:1", },

						}, PropsUtils.getTargetStore("SWAT1445"));
	}

	@Factory(dataProvider = "SWAT1445")
	public Object[] createTest(String countryValue, String productNo,
			String context, String purchasedAmount, String parameter,
			String parameter2, String updateResult) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1445Test(countryValue, productNo, context,
				purchasedAmount, parameter, parameter2, updateResult);

		return tests;
	}
}
