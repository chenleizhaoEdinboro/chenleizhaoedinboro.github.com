package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28208Test;

public class NA28208 {

	@DataProvider(name = "28208")
	public static Object[][] Data14919() {
		return Common.getFactoryData(new Object[][] { 
			{ "CO" }, 
		 	
			},PropsUtils.getTargetStore("NA-28208"));
	}

	@Factory(dataProvider = "28208")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28208Test(store);

		return tests;
	}

}
