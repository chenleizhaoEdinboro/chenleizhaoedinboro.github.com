package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18378Test;

public class NA18378 {
	@DataProvider(name = "18378")
	public static Object[][] Data18378() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU"},
				{ "NZ"}
				},
				PropsUtils.getTargetStore("NA-18378"));
	}

	@Factory(dataProvider = "18378")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18378Test(store);

		return tests;
	}
}
