package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19794Test;

public class NA19794 {

	@DataProvider(name = "NA19794")
	public static Object[][] Data19794() {
		return Common.getFactoryData(new Object[][] {
				{ "IN","India"} 
				},PropsUtils.getTargetStore("NA-19794"));
	}

	@Factory(dataProvider = "NA19794")
	public Object[] createTest(String store,String unit) {

		Object[] tests = new Object[1];

		tests[0] = new NA19794Test(store, unit);

		return tests;
	}

}
