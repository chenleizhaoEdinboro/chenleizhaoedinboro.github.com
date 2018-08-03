package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23818Test;

public class NA23818 {

	@DataProvider(name = "NA23818")
	public static Object[][] Ddata15492() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_SMB"} 
			}, PropsUtils.getTargetStore("NA-23818"));
	}

	@Factory(dataProvider = "NA23818")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23818Test(store);

		return tests;
	}

}