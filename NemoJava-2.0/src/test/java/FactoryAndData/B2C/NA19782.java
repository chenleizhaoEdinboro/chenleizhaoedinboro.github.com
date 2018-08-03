package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19782Test;

public class NA19782 {

	@DataProvider(name = "NA19782")
	public static Object[][] Data19782() {
		return Common.getFactoryData(new Object[][] {
				{ "US", "United States","15" },
				{ "AU", "Australia","16" } 
				},PropsUtils.getTargetStore("NA-19782"));
	}

	@Factory(dataProvider = "NA19782")
	public Object[] createTest(String store, String testUrl,String billNumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA19782Test(store, testUrl,billNumber);

		return tests;
	}

}
