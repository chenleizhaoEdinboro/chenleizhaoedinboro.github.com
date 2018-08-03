package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT219Test;

public class CONTENT219 {
	@DataProvider(name = "CONTENT219")
	public static Object[][] Data219() {
		return Common.getFactoryData(new Object[][] { 
				{"IE"},
				{"FR"},
				{"GB"}
			},PropsUtils.getTargetStore("CONTENT-219"));
		
	}

	@Factory(dataProvider = "CONTENT219")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT219Test(store);

		return tests;
	}
  
}
