package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17665Test;


public class NA17665 {
	@DataProvider(name = "17665")
	public static Object[][] Data17665() {
		return Common.getFactoryData(new Object[][] {				
				{ "AU"},			
				{ "US"},				
				{ "JP"}						
		},PropsUtils.getTargetStore("NA-17665"));
	}

	@Factory(dataProvider = "17665")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17665Test(store);

		return tests;
	}

}
