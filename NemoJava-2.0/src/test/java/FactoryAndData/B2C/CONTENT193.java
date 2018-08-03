package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT193Test;

public class CONTENT193 {
	@DataProvider(name = "CONTENT193")
	public static Object[][] Data193() {
		return Common.getFactoryData(new Object[][] { 
				{"IE"},
				{"FR"},
				{"GB"}
			},PropsUtils.getTargetStore("CONTENT-193"));
		
	}

	@Factory(dataProvider = "CONTENT193")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT193Test(store);

		return tests;
	}
  
}
