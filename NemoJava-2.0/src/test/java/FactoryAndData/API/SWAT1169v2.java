package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT1168Test;
import TestScript.API.SWAT1169Test;
import TestScript.API.SWAT1169Testv2;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1169v2 {
	@DataProvider(name = "SWAT1169")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "AU", "?id=AUBUILDER%40SHARKLASERS.COM&type=B2BUSER",
						"AUBUILDER@SHARKLASERS.COM",

						"B2B", "ADOBE_GLOBAL", "LE", "1213654197", "LE",
						"B2BCUSTOMERGROUP", "USERGROUP", "BUILDERDEFAULT",
						"USERAVAILABLEGROUP" },

				{ "AU", "?id=aubuyer%40sharklasers.com&type=B2BUSER",
						"aubuyer@sharklasers.com",

						"B2B", "ADOBE_GLOBAL", "LE", "1213654197", "LE",
						"B2BCUSTOMERGROUP", "USERGROUP", "BUYERDEFAULT",
						"USERAVAILABLEGROUP" },

				{ "AU", "?id=auapprover%40sharklasers.com&type=B2BUSER",
						"auapprover@sharklasers.com",

						"B2B", "ADOBE_GLOBAL", "LE", "1213654197", "LE",
						"B2BCUSTOMERGROUP", "USERGROUP", "APPROVERDEFAULT",
						"USERAVAILABLEGROUP" },

				{ "AU", "?id=autelesales%40sharklasers.com&type=B2BUSER",
						"autelesales@sharklasers.com",

						"B2B", "ADOBE_GLOBAL", "LE", "1213654197", "LE",
						"B2BCUSTOMERGROUP", "USERGROUP", "TELESALESDEFAULT",
						"USERAVAILABLEGROUP" },
				{ "US", "?id=testus%40sharklasers.com&type=LENOVOID",
						"testus@sharklasers.com",

						"B2C", "US_SMB_UNIT", "SMB", "PUBLIC_GLOBAL_B2C_UNIT",
						"PUBLIC_CONSUMER", "USWEB_UNIT", "PUBLIC_CONSUMER",
						"USPARTSALES_UNIT", "SERVICE_CUSTOMER" },
				{ "JP", "?id=testjp%40sharklasers.com&type=LENOVOID",
						"testjp@sharklasers.com",

						"B2C", "JPPUBLIC_UNIT", "PUBLIC_CONSUMER",
						"PUBLIC_GLOBAL_B2C_UNIT", "PUBLIC_CONSUMER",
						"DEFAULTNEMOB2CCUSTOMERGROUP", "USERGROUP", "", "" },

		}, PropsUtils.getTargetStore("SWAT1169"));
	}

	@Factory(dataProvider = "SWAT1169")
	public Object[] createTest(String countryValue, String paraString,
			String userid, String userType, String groupid1, String grouptype1,
			String groupid2, String grouptype2, String groupid3,
			String grouptype3, String groupid4, String grouptype4) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1169Testv2(countryValue, paraString, userid,
				userType, groupid1, grouptype1, groupid2, grouptype2, groupid3,
				grouptype3, groupid4, grouptype4);

		return tests;
	}
}
