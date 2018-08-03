package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.SHOPE343Test;

public class SHOPE343 {
	@DataProvider(name = "SHOPE343")
	public static Object[][] Data18() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}
			},PropsUtils.getTargetStore("SHOPE-343"));
		
	}

	@Factory(dataProvider = "SHOPE343")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE343Test(store);

		return tests;
	}
  
}
