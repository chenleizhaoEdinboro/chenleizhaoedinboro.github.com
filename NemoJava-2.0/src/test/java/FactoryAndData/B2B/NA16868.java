package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA16868Test;

public class NA16868 {

	@DataProvider(name = "16868")
	public static Object[][] Data16868() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" }
		},PropsUtils.getTargetStore("NA-16868"));
	}

	@Factory(dataProvider = "16868")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16868Test(store);

		return tests;
	}

}

