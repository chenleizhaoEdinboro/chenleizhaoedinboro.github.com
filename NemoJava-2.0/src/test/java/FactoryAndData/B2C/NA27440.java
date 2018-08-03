package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27440Test;
import TestScript.B2C.NA20177Test;

public class NA27440 {

	@DataProvider(name = "27440")
	public static Object[][] Data27440() {
		return Common.getFactoryData(new Object[][] { 
			{ "CA","Canada" },
			{"TW","Taiwan" }
		},PropsUtils.getTargetStore("NA-27440"));
	}

	@Factory(dataProvider = "27440")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA27440Test(store,country);

		return tests;
	}

}
