package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT1168Test;
import TestScript.API.SWAT1170Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1170 {
	@DataProvider(name = "SWAT1170")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "AU", "AUBUILDER@SHARKLASERS.COM",

				"?dmu=adobe_global", "adobe_global", "", "true", "1213654197",
						"9071705030660" },
				{ "AU", "aubuyer@sharklasers.com",

				"?dmu=adobe_global", "adobe_global", "", "true", "1213654197",
						"8966454575108" },
				{ "AU", "auapprover@sharklasers.com",

				"?dmu=adobe_global", "adobe_global", "", "true", "1213654197",
						"9071705063428" },
				{ "AU", "autelesales@sharklasers.com",

				"?dmu=adobe_global", "adobe_global", "", "true", "1213654197",
						"9071705128964" },
				{ "US", "USBUYER@SHARKLASERS.COM",

				"?dmu=1213348423", "1213348423", "", "true", "1213577815",
						"9071704834052" }, },
				PropsUtils.getTargetStore("SWAT1170"));
	}

	@Factory(dataProvider = "SWAT1170")
	public Object[] createTest(String countryValue, String userid,
			String paraString, String dmu, String SoldTo, String ifAccessible,
			String defaultUnit, String PK) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1170Test(countryValue, userid, paraString, dmu,
				SoldTo, ifAccessible, defaultUnit, PK);

		return tests;
	}
}
