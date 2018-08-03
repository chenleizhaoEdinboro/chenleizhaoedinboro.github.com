package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.DR19Test;

public class DR19 {

	@DataProvider(name = "DR-19")
	public static Object[][] Data19() {
		return Common.getFactoryData(new Object[][] { 
			{ "GB"},
			{ "IE" } 
		}, PropsUtils.getTargetStore("DR-19"));
	}

	@Factory(dataProvider = "DR-19")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];
		tests[0] = new DR19Test(store);

		return tests;
	}

}