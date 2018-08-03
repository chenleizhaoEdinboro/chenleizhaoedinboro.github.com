package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT35Test;

public class CONTENT35 {
	@DataProvider(name = "CONTENT35")
	public static Object[][] Data35() {
		return Common.getFactoryData(new Object[][] { 
				{"IE"},
				{"FR"},
				{"GB"}
			},PropsUtils.getTargetStore("CONTENT-35"));
		
	}

	@Factory(dataProvider = "CONTENT35")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT35Test(store);

		return tests;
	}
  
}
