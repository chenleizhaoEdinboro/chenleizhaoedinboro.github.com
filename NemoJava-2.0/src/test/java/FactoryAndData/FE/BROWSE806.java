package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.BROWSE806Test;

public class BROWSE806 {

	@DataProvider(name = "test")
	public static Object[][] Data806() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("BROWSE806"));
	}


	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];
		tests[0] = new BROWSE806Test(store);
		return tests;
	}
}
