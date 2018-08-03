package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17612Test;

public class NA17612 {
	@DataProvider(name = "17612")
	public static Object[][] Data17612() {
		return Common.getFactoryData(new Object[][] { 
			{ "MY","Malaysia"}},
				PropsUtils.getTargetStore("NA-17612"));
	}

	@Factory(dataProvider = "17612")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA17612Test(store,country);

		return tests;
	}
}
