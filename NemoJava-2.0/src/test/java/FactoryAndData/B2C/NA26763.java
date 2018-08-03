package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26763Test;

public class NA26763 {

	@DataProvider(name = "26763")
	public static Object[][] Data26763() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB" }, 
		},PropsUtils.getTargetStore("NA-26763"));
	}

	@Factory(dataProvider = "26763")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26763Test(store);

		return tests;
	}

}
