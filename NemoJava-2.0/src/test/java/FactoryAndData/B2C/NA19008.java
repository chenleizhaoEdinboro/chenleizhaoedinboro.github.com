package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19008Test;

public class NA19008 {

	@DataProvider(name = "19008")
	public static Object[][] Data19008() {
		return Common.getFactoryData(new Object[][] { { "HK", "EN", "HK", "ZF", "Traditional Chinese" } },
				PropsUtils.getTargetStore("NA-19008"));

	}

	@Factory(dataProvider = "19008")
	public Object[] createTest(String store, String language, String country, String language1,String language2) {

		Object[] tests = new Object[1];

		tests[0] = new NA19008Test(store, language, country, language1, language2);

		return tests;
	}

}
