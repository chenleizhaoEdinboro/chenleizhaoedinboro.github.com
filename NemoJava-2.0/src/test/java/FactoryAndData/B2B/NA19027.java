package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19027Test;

public class NA19027 {

	@DataProvider(name = "19027")
	public static Object[][] Data19027() {
		return Common.getFactoryData(new Object[][] {
			{ "US"},
			{ "AU"},
			{ "JP"}
			 
		}, PropsUtils.getTargetStore("NA-19027"));
	}

	@Factory(dataProvider = "19027")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19027Test(store);

		return tests;
	}

}
