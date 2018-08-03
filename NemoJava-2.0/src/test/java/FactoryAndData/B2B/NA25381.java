package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA25381Test;

public class NA25381 {

	@DataProvider(name = "NA25381")
	public static Object[][] Ddata25381() {
		return Common.getFactoryData(new Object[][] { 
			{ "US", "United States","Canada" }
			}, PropsUtils.getTargetStore("NA-25381"));
	}

	@Factory(dataProvider = "NA25381")
	public Object[] createTest(String store, String country1, String country2) {

		Object[] tests = new Object[1];

		tests[0] = new NA25381Test(store, country1, country2);

		return tests;
	}

}
