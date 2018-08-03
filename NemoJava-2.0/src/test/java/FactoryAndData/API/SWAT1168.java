package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT1168Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1168 {
	@DataProvider(name = "SWAT1168")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "US", "testus@sharklasers.com",

				"?url=http%3A%2F%2Fpre-c-hybris.lenovo.com%2Fus%2Fen",
						"http://pre-c-hybris.lenovo.com/us/en", "true",
						"8865835122692" },
				{ "AU", "testau@sharklasers.com",

				"?url=http%3A%2F%2Fpre-c-hybris.lenovo.com%2Fau%2Fen",
						"http://pre-c-hybris.lenovo.com/au/en", "true",
						"8861584424964" },
				{ "AU", "testau@sharklasers.com",

				"?url=http%3A%2F%2Fpre-c-hybris.lenovo.com%2Fbr%2Fpt",
						"http://pre-c-hybris.lenovo.com/br/pt", "true",
						"8861584424964" },
				{ "JP", "testjp@sharklasers.com",

				"?url=http%3A%2F%2Fpre-c-hybris.lenovo.com%2Fjp%2Fja",
						"http://pre-c-hybris.lenovo.com/jp/ja", "true",
						"8844117409796" },
				{ "JP", "testjp@sharklasers.com",

				"?url=http%3A%2F%2Fpre-c-hybris.lenovo.com%2Fus%2Fen",
						"http://pre-c-hybris.lenovo.com/us/en", "false",
						"8844117409796" },

		}, PropsUtils.getTargetStore("SWAT1168"));
	}

	@Factory(dataProvider = "SWAT1168")
	public Object[] createTest(String countryValue, String userid,
			String paraString, String url, String ifAccessible, String PK) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1168Test(countryValue, userid, paraString, url,
				ifAccessible, PK);

		return tests;
	}
}
