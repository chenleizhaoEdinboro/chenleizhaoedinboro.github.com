package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17697Test;

public class NA17697 {
	@DataProvider(name = "17697")
	public static Object[][] Data17697() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU","Australia" }, 
				{ "US", "United States" },
				{ "HK", "Hong Kong S.A.R. of China" },
				{ "JP","Japan" },
				{ "GB", "United Kingdom" }}
				,PropsUtils.getTargetStore("NA-17697"));
	}

	@Factory(dataProvider = "17697")
	public Object[] createTest(String store, String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA17697Test(store,country);

		return tests;
	}
}
