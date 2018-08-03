package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA24530Test;

public class NA24530{

	@DataProvider(name = "NA24530")
	public static Object[][] Data24530() {
		return Common.getFactoryData(new Object[][] {
			{ "JP" }
		}, PropsUtils.getTargetStore("NA-24530"));
	}
	@Factory(dataProvider = "NA24530")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA24530Test(store);

		return tests;
	}
}
