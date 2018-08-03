package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23911Test;

public class NA23911 {

	@DataProvider(name = "23911")
	public static Object[][] Data23911() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }
			
		},PropsUtils.getTargetStore("NA-23911"));
	}

	@Factory(dataProvider = "23911")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23911Test(store);

		return tests;
	}

}
