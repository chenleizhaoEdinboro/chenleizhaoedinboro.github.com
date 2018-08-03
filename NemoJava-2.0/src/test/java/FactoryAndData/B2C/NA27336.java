package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27336Test;

public class NA27336 {
	@DataProvider(name = "27336")
	public static Object[][] Data27286() {
		return Common.getFactoryData(new Object[][] { 
				{ "CA" }
			},PropsUtils.getTargetStore("NA-27336"));
		
	}

	@Factory(dataProvider = "27336")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27336Test(store);

		return tests;
	}

}
