package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28145Test;

public class NA28145 {
	@DataProvider(name = "28145")
	public static Object[][] Data28205() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
				{"AU"}
			},PropsUtils.getTargetStore("NA-28145"));
		
	}

	@Factory(dataProvider = "28145")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28145Test(store);

		return tests;
	}
  
}
