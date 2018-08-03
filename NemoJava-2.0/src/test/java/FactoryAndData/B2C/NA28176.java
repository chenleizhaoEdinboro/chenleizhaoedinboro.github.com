package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28176Test;


public class NA28176 {

	@DataProvider(name = "28176")
	public static Object[][] Data28176() {
		return Common.getFactoryData(new Object[][] { 
			{"AU","Australia" },
			{"US","United States of America" }
		},PropsUtils.getTargetStore("NA-28176"));
	}

	@Factory(dataProvider = "28176")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA28176Test(store,country);

		return tests;
	}

}
