package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26821Test;

public class NA26821 {

	@DataProvider(name = "26821")
	public static Object[][] Data26821() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB" }, 
			{ "JP_SMB" },
		},PropsUtils.getTargetStore("NA-26821"));
	}

	@Factory(dataProvider = "26821")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26821Test(store);

		return tests;
	}

}
