package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21299Test;

public class NA21299 {

	@DataProvider(name = "21299")
	public static Object[][] Data21299() {

		return Common.getFactoryData( new Object[][] { 
				{ "AU" },
				{ "JP" },
				{ "US" }
				
		},PropsUtils.getTargetStore("NA-21299"));


	}

	@Factory(dataProvider = "21299")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21299Test(store);

		return tests;
	}

}
