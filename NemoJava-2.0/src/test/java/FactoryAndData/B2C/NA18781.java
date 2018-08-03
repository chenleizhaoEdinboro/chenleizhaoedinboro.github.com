package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18781Test;

public class NA18781 {

	@DataProvider(name = "18781")
	public static Object[][] Data17711() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("NA-18781"));
	}

	@Factory(dataProvider = "18781")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18781Test(store);

		return tests;
	}

}
