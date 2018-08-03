package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT306Test;

public class CONTENT306 {
	@DataProvider(name = "CONTENT306")
	public static Object[][] Data306() {
		return Common.getFactoryData(new Object[][] { 
				{"JP_SMB"},
				{"US_SMB"},
			},PropsUtils.getTargetStore("CONTENT-306"));
		
	}

	@Factory(dataProvider = "CONTENT306")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT306Test(store);

		return tests;
	}
  
}
