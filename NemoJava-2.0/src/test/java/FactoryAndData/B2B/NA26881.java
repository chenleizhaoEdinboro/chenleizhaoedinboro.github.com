package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA26881Test;


public class NA26881 {
	@DataProvider(name = "26881")
	public static Object[][] Data26681() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}
		},PropsUtils.getTargetStore("NA-26881"));
	}

	@Factory(dataProvider = "26881")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26881Test(store);

		return tests;
	}
}
