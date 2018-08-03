package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA15490Test;

public class NA15490 {

	@DataProvider(name = "15490")
	public static Object[][] Data15490() {
		return Common.getFactoryData(new Object[][] { { "AU" }, { "US" }, { "JP" } },PropsUtils.getTargetStore("NA-15490"));
	}

	@Factory(dataProvider = "15490")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15490Test(store);

		return tests;
	}

}
