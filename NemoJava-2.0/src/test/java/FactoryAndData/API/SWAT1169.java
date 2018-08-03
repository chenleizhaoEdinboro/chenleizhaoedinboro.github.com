package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT1168Test;
import TestScript.API.SWAT1169Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1169 {
	@DataProvider(name = "SWAT1169")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "AU", "AUBUILDER@SHARKLASERS.COM",
					
				"B2B", "ADOBE_GLOBAL", "1213654197", "", "", "9076588085252" },

				{ "AU", "aubuyer@sharklasers.com",

				"B2B", "ADOBE_GLOBAL", "1213654197", "", "", "8966454575108" },

				{ "AU", "auapprover@sharklasers.com",

				"B2B", "ADOBE_GLOBAL", "1213654197", "", "", "9071705063428" },

				{ "AU", "autelesales@sharklasers.com",

				"B2B", "ADOBE_GLOBAL", "1213654197", "", "", "9071705128964" },
				{ "US", "testus@sharklasers.com",

				"B2C", "US_SMB_UNIT", "USLENOVOUSEPP_UNIT", "USWEB_UNIT",
						"USPARTSALES_UNIT", "8865835122692" },
				{ "JP", "testjp@sharklasers.com",

				"B2C", "", "MXWEB", "JPPUBLIC_UNIT",
						"", "8844117409796" },

		}, PropsUtils.getTargetStore("SWAT1169"));
	}

	@Factory(dataProvider = "SWAT1169")
	public Object[] createTest(String countryValue, String userid,
			String userType, String dmu, String dmuid, String b2cUnit1,
			String b2cUnit2, String PK) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1169Test(countryValue, userid, userType, dmu, dmuid,
				b2cUnit1, b2cUnit2, PK);

		return tests;
	}
}
