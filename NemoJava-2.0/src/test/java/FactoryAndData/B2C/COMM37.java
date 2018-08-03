package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM37Test;

public class COMM37 {
	@DataProvider(name = "37")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"AU"},
			},PropsUtils.getTargetStore("COMM37"));
		
	}

	@Factory(dataProvider = "37")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM37Test( store);

		return tests;
	}
  
}
