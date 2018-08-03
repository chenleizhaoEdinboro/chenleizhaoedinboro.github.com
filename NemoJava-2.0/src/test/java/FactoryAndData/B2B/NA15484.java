package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA15484Test;

public class NA15484 {

	@DataProvider(name = "15484")
	public static Object[][] Data15484() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }, 
				{ "US" }, 
				{ "JP" } 
				},PropsUtils.getTargetStore("NA-15484"));
	}

	@Factory(dataProvider = "15484")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15484Test(store);

		return tests;
	}

}
