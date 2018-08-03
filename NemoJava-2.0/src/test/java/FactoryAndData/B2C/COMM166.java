package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM166Test;

public class COMM166 {
	@DataProvider(name = "166")
	public static Object[][] Data166() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM166"));
		
	}

	@Factory(dataProvider = "166")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM166Test(store);

		return tests;
	}
  
}
