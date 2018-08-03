package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.SWAT1121v2Test;
import TestScript.API.SWAT1704Test;

public class SWAT1704 {
	@DataProvider(name = "SWAT1704")
	public static Object[][] DataServiceDemo() {
		return Common.getFactoryData(new Object[][] {

				{ "US", "testus@sharklasers.com",

				"type=LENOVOID", "1q2w3e4r",
						"Please_input_Firstname Please_input_Lastname",
						"LENOVOID", "2802734720", "" },
				{ "AU", "testau@sharklasers.com",

				"type=LENOVOID", "1q2w3e4r",
						"Please_input_Firstname Please_input_Lastname",
						"LENOVOID", "2802734720", "" },

				//{ "US", "lixe1",

						
				//"type=HYBRIS", "9076488896516", "lixe", "HYBRIS", "",
				//		"bGl4ZTE6TGVub3ZvMTIzNA" },
				{ "AU", "AUBUILDER@SHARKLASERS.COM",

				"type=B2BUSER", "1q2w3e4r", "builder", "B2BUSER",
						"2802114812",
						"QVVCVUlMREVSQFNIQVJLTEFTRVJTLkNPTToxcTJ3M2U0cg" },

				{ "AU", "AUBUYER@SHARKLASERS.COM",

				"type=B2BUSER", "1q2w3e4r", "zheng zhengpx", "B2BUSER",
						"2805831419",
						"QVVCVVlFUkBTSEFSS0xBU0VSUy5DT006MXEydzNlNHI" },

				{ "AU", "AUAPPROVER@SHARKLASERS.COM",

				"type=B2BUSER", "1q2w3e4r", "approver", "B2BUSER",
						"2802114811",
						"QVVBUFBST1ZFUkBTSEFSS0xBU0VSUy5DT006MXEydzNlNHI" },

				{ "AU", "AUTELESALES@SHARKLASERS.COM", "type=B2BUSER",
						"1q2w3e4r", "Assisted B2B Service Agent",
						"B2BUSER", "2802114813",
						"QVVURUxFU0FMRVNAU0hBUktMQVNFUlMuQ09NOjFxMnczZTRy" },

		}, PropsUtils.getTargetStore("SWAT1704"));
	}

	@Factory(dataProvider = "SWAT1704")
	public Object[] createTest(String countryValue, String userid,
			String paraString, String pw, String name, String type,
			String consumerId, String encodedPW) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1704Test(countryValue, userid, paraString, pw,
				name, type, consumerId, encodedPW);

		return tests;
	}
}
