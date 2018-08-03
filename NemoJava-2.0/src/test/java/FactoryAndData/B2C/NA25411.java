package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25411Test;

public class NA25411 {
	@DataProvider(name = "NA25411")
	public static Object[][] Data25411() {
		return Common.getFactoryData(new Object[][] {
				{ "NZ"},
				{ "JP"},
				{ "US"},
				{ "CO"}
						},PropsUtils.getTargetStore("NA-25411"));
	}

	@Factory(dataProvider = "NA25411")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25411Test(store);

		return tests;
	}

}
