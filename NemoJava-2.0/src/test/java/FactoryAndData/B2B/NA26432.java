package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA26432Test;

public class NA26432 {

	@DataProvider(name = "26432")
	public static Object[][] Data26432() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" }, 
			{ "JP" }, 
			{ "US" } 
			},
				PropsUtils.getTargetStore("NA-26432"));
	}

	@Factory(dataProvider = "26432")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26432Test(store);

		return tests;
	}

}