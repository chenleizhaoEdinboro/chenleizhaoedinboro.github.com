package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.SHOPE344Test;

public class SHOPE344 {
	@DataProvider(name = "SHOPE344")
	public static Object[][] Data18() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}
			},PropsUtils.getTargetStore("SHOPE-344"));
		
	}

	@Factory(dataProvider = "SHOPE344")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE344Test(store);

		return tests;
	}
  
}
