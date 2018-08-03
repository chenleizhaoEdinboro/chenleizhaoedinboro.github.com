package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21906Test;


public class NA21906 {
	@DataProvider(name = "21906")
	public static Object[][] Data21906() {
		return Common.getFactoryData(new Object[][] {				
				{ "AU" }				
		},PropsUtils.getTargetStore("NA-21906"));
	}

	@Factory(dataProvider = "21906")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21906Test(store);

		return tests;
	}

}
