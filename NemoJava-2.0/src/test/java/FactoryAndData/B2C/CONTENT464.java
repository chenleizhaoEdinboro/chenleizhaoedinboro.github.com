package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT464Test;

public class CONTENT464 {
	@DataProvider(name = "CONTENT464")
	public static Object[][] Data464() {
		return Common.getFactoryData(new Object[][] { 
				{"JP"},
			},PropsUtils.getTargetStore("CONTENT-464"));
		
	}

	@Factory(dataProvider = "CONTENT464")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT464Test(store);

		return tests;
	}
  
}
