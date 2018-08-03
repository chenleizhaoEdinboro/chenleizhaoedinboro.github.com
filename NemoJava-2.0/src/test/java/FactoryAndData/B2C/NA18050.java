package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18050Test;

public class NA18050 {
	@DataProvider(name = "18050")
	public static Object[][] Data18050() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU","NZ","06P4069" },
				{ "US","CA","06P4069" },
				{ "JP","NZ","06P4069" },
				{ "GB","NZ","06P4069" }
		},PropsUtils.getTargetStore("NA-18050"));
	}

	@Factory(dataProvider = "18050")
	public Object[] createTest(String store,String anotherStore,String accessory) {

		Object[] tests = new Object[1];

		tests[0] = new NA18050Test(store,anotherStore,accessory);

		return tests;
	}
}
