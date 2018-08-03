package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16659Test;

public class NA16659 {

	@DataProvider(name = "16659")
	public static Object[][] Data16659() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
		 	{ "NZ" }
			},PropsUtils.getTargetStore("NA-16659"));
	}

	@Factory(dataProvider = "16659")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16659Test(store);

		return tests;
	}

}
