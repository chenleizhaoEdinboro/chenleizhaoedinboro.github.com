package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27832Test;

public class NA27832 {

	@DataProvider(name = "27832")
	public static Object[][] Data27832() {
		return Common.getFactoryData(new Object[][] { 
				{ "CA", "80SX0074LM"},
				{ "US", "80SX0074LM"}
			},PropsUtils.getTargetStore("NA-27832"));
		
	}

	@Factory(dataProvider = "27832")
	public Object[] createTest(String store, String productNO) {

		Object[] tests = new Object[1];

		tests[0] = new NA27832Test(store,productNO);

		return tests;
	}

}

