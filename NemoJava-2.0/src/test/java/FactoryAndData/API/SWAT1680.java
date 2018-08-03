package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT1680Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1680 {
	@DataProvider(name = "SWAT1680")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "AU", "zhengpx1@lenovo.com",

				"&updateby=lixe1%40lenovo.com", "18050903801", "8",
						"lixe1@lenovo.com", "2802734720" },
				{ "AU", "zhengpx1@lenovo.com",

				"&updateby=lixe1%40lenovo.com", "18050903801", "0",
						"lixe1@lenovo.com", "2802734720" },
				{ "AU", "zhengpx1@lenovo.com",

				"&updateby=lixe1%40lenovo.com", "18050903801", "-3",
						"lixe1@lenovo.com", "2802734720" },

		}, PropsUtils.getTargetStore("SWAT2680"));
	}

	@Factory(dataProvider = "SWAT1680")
	public Object[] createTest(String countryValue, String userid,
			String paraString, String orderNumber, String changedAmount,
			String updatedBy, String consumerId) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1680Test(countryValue, userid, paraString,
				orderNumber, changedAmount, updatedBy, consumerId);

		return tests;
	}
}
