package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17762Test;

public class NA17762 {

	@DataProvider(name = "17762")
	public static Object[][] Data16672() {
		return Common.getFactoryData(new Object[][] { 
			{ "US","United States"}
			
		},PropsUtils.getTargetStore("NA-17762"));
	}

	@Factory(dataProvider = "17762")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA17762Test(store,country);

		return tests;
	}

}
