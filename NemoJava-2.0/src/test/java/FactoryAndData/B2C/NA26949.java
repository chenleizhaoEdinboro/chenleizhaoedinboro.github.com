package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26949Test;

public class NA26949 {

	@DataProvider(name = "NA26949")
	public static Object[][] Ddata26949() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP", "Japan"},
			{ "US", "United States"},
			{ "GB", "United Kingdom"}
			}, PropsUtils.getTargetStore("NA-26949"));
	}

	@Factory(dataProvider = "NA26949")
	public Object[] createTest(String store, String country1) {

		Object[] tests = new Object[1];

		tests[0] = new NA26949Test(store, country1);

		return tests;
	}

}