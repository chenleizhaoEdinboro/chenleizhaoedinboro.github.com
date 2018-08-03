package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28216Test;

public class NA28216 {
	@DataProvider(name = "28216")
	public static Object[][] Data28205() {
		return Common.getFactoryData(new Object[][] { 
				{"AU"},
				{"CA"},
				{"NZ"},
				{"US"},
				{"JP"},
				{"USEPP"}
			},PropsUtils.getTargetStore("NA-28216"));
		
	}

	@Factory(dataProvider = "28216")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA28216Test(store);

		return tests;
	}

}
