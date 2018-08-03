package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE20Test;

public class BROWSE20 {
	@DataProvider(name = "20")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"AU"},
			},PropsUtils.getTargetStore("BROWSE-20"));
		
	}

	@Factory(dataProvider = "20")
	public Object[] createTest(String store ) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE20Test( store);

		return tests;
	}
  
}
