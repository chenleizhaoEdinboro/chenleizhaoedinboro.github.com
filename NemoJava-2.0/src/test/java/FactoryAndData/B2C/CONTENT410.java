package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT410Test;

public class CONTENT410 {
	@DataProvider(name = "CONTENT410")
	public static Object[][] Data410() {
		return Common.getFactoryData(new Object[][] { 
				{"JP_SMB"},
			},PropsUtils.getTargetStore("CONTENT-410"));
	}

	@Factory(dataProvider = "CONTENT410")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT410Test(store);

		return tests;
	}
  
}
