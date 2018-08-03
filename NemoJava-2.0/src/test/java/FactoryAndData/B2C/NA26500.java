package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26500Test;

public class NA26500 {

	@DataProvider(name = "26500")
	public static Object[][] Data26500() {
		return  Common.getFactoryData(new Object[][] { 
				{"US"}
		},PropsUtils.getTargetStore("NA-26500"));
	}

	@Factory(dataProvider = "26500")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26500Test(store);

		return tests;
	}
}
