package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28209Test;

public class NA28209 {

	@DataProvider(name = "NA28209")
	public static Object[][] Ddata28209() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP", "Japan"},
			{ "US", "United States"}
			}, PropsUtils.getTargetStore("NA-28209"));
	}

	@Factory(dataProvider = "NA28209")
	public Object[] createTest(String store, String country1) {

		Object[] tests = new Object[1];

		tests[0] = new NA28209Test(store, country1);

		return tests;
	}

}