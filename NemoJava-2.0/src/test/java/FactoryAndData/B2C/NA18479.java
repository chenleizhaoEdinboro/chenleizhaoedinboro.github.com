package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18479Test;

public class NA18479 {
	@DataProvider(name = "18479")
	public static Object[][] Data18479() {
		return Common.getFactoryData(new Object[][] { 
				{ "JP", "80SX0074LM"}
			},PropsUtils.getTargetStore("NA-18479"));
		
	}

	@Factory(dataProvider = "18479")
	public Object[] createTest(String store, String productNO) {

		Object[] tests = new Object[1];

		tests[0] = new NA18479Test(store,productNO);

		return tests;
	}

}

