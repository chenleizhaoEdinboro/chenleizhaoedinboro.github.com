package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26980Test;

public class NA26980 {

	@DataProvider(name = "26980")
	public static Object[][] Data26980() {
		return Common.getFactoryData(new Object[][] { 
			{ "MX" }
			},PropsUtils.getTargetStore("NA-26980"));
	}

	@Factory(dataProvider = "26980")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26980Test(store);

		return tests;
	}

}
