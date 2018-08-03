package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM522Test;

public class COMM522 {
	@DataProvider(name = "522")
	public static Object[][] Data522() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM522"));
		
	}

	@Factory(dataProvider = "522")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM522Test(store);

		return tests;
	}
  
}
