package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18404Test;

public class NA18404 {

	@DataProvider(name = "NA18404")
	public static Object[][] Ddata18404() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"},
			{ "BR"},
			{ "CO"},
			{ "US"},
			{ "HK"},
			{ "JP"},
			{ "US_OUTLET"}},
				PropsUtils.getTargetStore("NA-18404"));
	}

	@Factory(dataProvider = "NA18404")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18404Test(store);

		return tests;
	}

}