package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27059Test;

public class NA27059 {

	@DataProvider(name = "27059")
	public static Object[][] Data15481() {
		return Common.getFactoryData(new Object[][] { 
				{ "US"} 
				},
				PropsUtils.getTargetStore("NA-27059"));
	}

	@Factory(dataProvider = "27059")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA27059Test(store);

		return tests;
	}

}
