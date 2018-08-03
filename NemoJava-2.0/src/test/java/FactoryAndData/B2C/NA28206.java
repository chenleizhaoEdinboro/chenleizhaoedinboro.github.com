package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28206Test;

public class NA28206 {
	@DataProvider(name = "28206")
	public static Object[][] Data28206() {
		return Common.getFactoryData(new Object[][] { 
				{ "CA","81BL009GUS" },
			},PropsUtils.getTargetStore("NA-28206"));
		
	}

	@Factory(dataProvider = "28206")
	public Object[] createTest(String store,String ProductNo) {

		Object[] tests = new Object[1];

		tests[0] = new NA28206Test(store,ProductNo);

		return tests;
	}

}
