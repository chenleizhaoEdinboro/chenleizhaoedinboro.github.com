package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.NA21222Test;

public class NA21222 {
	@DataProvider(name = "test")
	public static Object[][] Data21222() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("NA21222"));
	}


	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];
		
		tests[0] = new NA21222Test(store);
		return tests;
	}
}
