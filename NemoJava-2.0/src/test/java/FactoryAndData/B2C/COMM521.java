package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM521Test;

public class COMM521 {
	@DataProvider(name = "521")
	public static Object[][] Data521() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("COMM521"));
		
	}

	@Factory(dataProvider = "521")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM521Test(store);

		return tests;
	}
  
}
