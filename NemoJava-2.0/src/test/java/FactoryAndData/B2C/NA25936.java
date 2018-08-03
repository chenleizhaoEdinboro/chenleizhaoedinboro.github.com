package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25936Test;

public class NA25936 {

	@DataProvider(name = "25936")
	public static Object[][] Data25936() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
			},PropsUtils.getTargetStore("NA-25936"));
	}

	@Factory(dataProvider = "25936")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25936Test(store);

		return tests;
	}

}
