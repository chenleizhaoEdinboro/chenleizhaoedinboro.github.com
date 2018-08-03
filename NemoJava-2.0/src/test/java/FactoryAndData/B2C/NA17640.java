package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17640Test;

public class NA17640 {
	@DataProvider(name = "17640")
	public static Object[][] Data17640() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },				
				{ "US" },			
				{ "JP" },
				{ "GB" }				
				},PropsUtils.getTargetStore("NA-17640"));
	}

	@Factory(dataProvider = "17640")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17640Test(store);

		return tests;
	}
}
