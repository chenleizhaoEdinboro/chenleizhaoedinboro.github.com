package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM366Test;

public class COMM366 {
	@DataProvider(name = "366")
	public static Object[][] Data366() {
		return Common.getFactoryData(new Object[][] { 
			{ "MY"}},
				PropsUtils.getTargetStore("COMM-366"));
	}

	@Factory(dataProvider = "366")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM366Test(store);

		return tests;
	}
}
