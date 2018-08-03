package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP34Test;

public class SHOP34 {
	@DataProvider(name = "SHOP34")
	public static Object[][] Data18() {
		return Common.getFactoryData(new Object[][] { 
				{"US"}
			},PropsUtils.getTargetStore("SHOPE-34"));
		
	}

	@Factory(dataProvider = "SHOP34")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP34Test( store);

		return tests;
	}
  
}
