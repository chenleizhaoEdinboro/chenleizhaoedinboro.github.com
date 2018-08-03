package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25328Test;

public class NA25328 {
	@DataProvider(name = "25328")
	public static Object[][] Data25328() {
		return Common.getFactoryData(new Object[][] { 
				{ "US" }
			},PropsUtils.getTargetStore("NA-25328"));
		
	}

	@Factory(dataProvider = "25328")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25328Test(store);

		return tests;
	}

}
