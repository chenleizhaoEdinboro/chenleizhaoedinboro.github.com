package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT1680Test;
import TestScript.API.SWAT1716Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1716 {
	@DataProvider(name = "SWAT1716")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "AU", "zhengpx1@lenovo.com",

				"&updateby=lixe1%40lenovo.com", "18050903801", "Suspend01",
						"lixe1@lenovo.com", "Suspend" },
				{ "AU", "zhengpx1@lenovo.com",

				"&updateby=lixe1%40lenovo.com", "18050903801", "Reactivate01",
						"lixe1@lenovo.com", "Active" },
				

		}, PropsUtils.getTargetStore("SWAT2680"));
	}

	@Factory(dataProvider = "SWAT1716")
	public Object[] createTest(String countryValue, String userid,
			String paraString, String orderNumber, String changedStatus,
			String updatedBy, String expectedStatus) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1716Test(countryValue, userid, paraString,
				orderNumber, changedStatus, updatedBy, expectedStatus);

		return tests;
	}
}
