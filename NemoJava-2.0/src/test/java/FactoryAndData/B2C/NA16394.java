package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16394Test;

public class NA16394 {

	@DataProvider(name = "NA16394")
	public static Object[][] Ddata15492() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU", "aupremium_unit", "sharelenovo", "aupremium" }, 
			{ "NZ", "nzAffinity_unit", "nzAffinity_parent", "nzaffinity" } 
			}, PropsUtils.getTargetStore("NA-16394"));
	}

	@Factory(dataProvider = "NA16394")
	public Object[] createTest(String store, String childUnit, String parentUnit, String storeID) {

		Object[] tests = new Object[1];

		tests[0] = new NA16394Test(store, childUnit, parentUnit, storeID);

		return tests;
	}

}