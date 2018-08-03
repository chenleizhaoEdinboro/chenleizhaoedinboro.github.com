package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23077Test;

public class NA23077{

	@DataProvider(name = "23077")
	public static Object[][] Data23077() {
		return Common.getFactoryData(new Object[][] { 
			{ "BR" }, 
			
		},PropsUtils.getTargetStore("NA-23077"));
	}

	@Factory(dataProvider = "23077")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23077Test(store);

		return tests;
	}

}
