package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28151Test;
import TestScript.API.SWAT1121Test;
import TestScript.API.SWAT809Test;
import TestScript.B2C.NA15492Test;

public class SWAT1121 {
	@DataProvider(name = "SWAT1121")
	public static Object[][] DataServiceDemo() {
		return Common
				.getFactoryData(
						new Object[][] {

								{
										"AU",
										"testau@sharklasers.com",

										"testau%40sharklasers.com&password=1q2w3e4r&mode=8861584424964",
										"8861584424964",
										"Please_input_Firstname Please_input_Lastname",
										"B2CUSER", "2802734720" },
								{
										"US",
										"testus@sharklasers.com",

										"testus%40sharklasers.com&password=1q2w3e4r&mode=8796097577042",
										"8865835122692", "Please_input_Firstname Please_input_Lastname",
										"B2CUSER", "2802806029" },
								{
										"US",
										"lixe1",

										"lixe1&password=Lenovo1234&mode=9076587233284",
										"9076587233284", "lixe",
										"HYBRIS", "" },
								{
										"AU",
										"AUBUILDER@SHARKLASERS.COM",

										"aubuilder%40sharklasers.com&password=1q2w3e4r&mode=8796097740882",
										"8796097740882", "builder",
										"B2BUSER", "2802114812" },
								{
										"AU",
										"AUBUYER@SHARKLASERS.COM",

										"aubuyer%40sharklasers.com&password=1q2w3e4r&mode=8796097740882",
										"8796097740882", "zheng zhengpx",
										"B2BUSER", "2805831419" },
								{
										"AU",
										"AUAPPROVER@SHARKLASERS.COM",

										"auapprover%40sharklasers.com&password=1q2w3e4r&mode=8796097740882",
										"8796097740882", "approver",
										"B2BUSER", "2802114811" },
								{
										"AU",
										"AUTELESALES@SHARKLASERS.COM",

										"autelesales%40sharklasers.com&password=1q2w3e4r&mode=8796097740882",
										"8796097740882", "Assisted B2B Service Agent",
										"B2BUSER", "2802114813" },

						}, PropsUtils.getTargetStore("SWAT1121"));
	}

	@Factory(dataProvider = "SWAT1121")
	public Object[] createTest(String countryValue, String userid,
			String paraString, String mode, String name, String type,
			String consumerId) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1121Test(countryValue, userid, paraString, mode,
				name, type, consumerId);

		return tests;
	}
}
