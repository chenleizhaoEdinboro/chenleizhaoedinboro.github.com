package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19873Test;

public class NA19873{

	@DataProvider(name = "NA19873")
	public static Object[][] Data19873() {
		return Common.getFactoryData(new Object[][] {
			{ "AU", "Australia" }
		}, PropsUtils.getTargetStore("NA-19873"));
	}
	@Factory(dataProvider = "NA19873")
	public Object[] createTest(String store, String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA19873Test(store,country);

		return tests;
	}
}
