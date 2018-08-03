package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.SWAT564Test;

public class SWAT564 {

	@DataProvider(name = "test")
	public static Object[][] Data564() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("SWAT564"));
	}

	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT564Test(store);
		

		return tests;
	}

}
