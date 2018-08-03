package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA25659Test;

public class NA25659{

	@DataProvider(name = "25659")
	public static Object[][] Data25658() {
		return Common.getFactoryData(new Object[][] {
			{ "US" }
		}, PropsUtils.getTargetStore("NA-25659"));
	}
	@Factory(dataProvider = "25659")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25659Test(store);

		return tests;
	}
}
