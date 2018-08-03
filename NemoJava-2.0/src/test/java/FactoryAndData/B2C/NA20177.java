package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20177Test;

public class NA20177 {

	@DataProvider(name = "20177")
	public static Object[][] Data20177() {
		return Common.getFactoryData(new Object[][] { 
			{ "CO" }
		},PropsUtils.getTargetStore("NA-20177"));
	}

	@Factory(dataProvider = "20177")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA20177Test(store);

		return tests;
	}

}
