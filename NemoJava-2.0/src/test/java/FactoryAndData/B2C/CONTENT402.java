package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT402Test;

public class CONTENT402 {
	@DataProvider(name = "CONTENT402")
	public static Object[][] Data402() {
		return Common.getFactoryData(new Object[][] { 
				{"US_SMB"},
			},PropsUtils.getTargetStore("CONTENT-402"));
		
	}

	@Factory(dataProvider = "CONTENT402")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT402Test(store);

		return tests;
	}
  
}
