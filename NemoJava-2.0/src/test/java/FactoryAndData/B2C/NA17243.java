package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestScript.B2C.NA17243Test;
import TestData.PropsUtils;

public class NA17243 {

	@DataProvider(name = "17243")
	public static Object[][] Data17243() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "NZ" }, 
			{ "US" },
			{ "USEPP" },
			{ "CA" },
			{ "CA_AFFINITY" },
			{ "US_OUTLET" },
			{ "HK" },
			{ "HKZF" },
			{ "SG" },
			{ "JP" },
			{ "GB" },
			{ "IE" }},
			PropsUtils.getTargetStore("NA-17243"));
	}

	@Factory(dataProvider = "17243")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17243Test(store);

		return tests;
	}

}
