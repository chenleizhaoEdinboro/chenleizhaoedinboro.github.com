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
import TestScript.API.SWAT1170Testv2;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1170v2 {
	@DataProvider(name = "SWAT1170")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "AU", "?id=AUBUILDER%40SHARKLASERS.COM&type=B2BUSER","AUBUILDER@SHARKLASERS.COM",
					
				"B2B", "ADOBE_GLOBAL", "1213654197","LE","1212322033", "1213654197", "", "9076588085252" },

				{ "AU","?id=aubuyer%40sharklasers.com&type=B2BUSER", "aubuyer@sharklasers.com",

				"B2B", "ADOBE_GLOBAL", "1213654197", "LE","1212322033","", "", "8966454575108" },

				{ "AU", "?id=auapprover%40sharklasers.com&type=B2BUSER", "auapprover@sharklasers.com",

				"B2B", "ADOBE_GLOBAL", "1213654197","LE","1212322033", "1213654197", "", "9071705063428" },

				{ "AU","?id=autelesales%40sharklasers.com&type=B2BUSER",  "autelesales@sharklasers.com",

				"B2B", "ADOBE_GLOBAL", "1213654197", "LE","","1212322033", "1213654197", "9071705128964" },
				/*{ "US","",  "testus@sharklasers.com",

				"B2C", "US_SMB_UNIT", "USLENOVOUSEPP_UNIT","","", "USWEB_UNIT",
						"USPARTSALES_UNIT", "8865835122692" },
				{ "JP","",  "testjp@sharklasers.com",

				"B2C", "", "MXWEB","","", "JPPUBLIC_UNIT",
						"", "8844117409796" },*/

		}, PropsUtils.getTargetStore("SWAT1170"));
	}

	@Factory(dataProvider = "SWAT1170")
	public Object[] createTest(String countryValue,String paraString, String userid,
			String userType, String dmu, String dmuid,String storeType,String b2cUnit1, String b2cUnit2,
			String b2cUnit3, String PK) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1170Testv2(countryValue,paraString, userid, userType, dmu, dmuid,storeType,
				b2cUnit1, b2cUnit2,b2cUnit3, PK);

		return tests;
	}
}
