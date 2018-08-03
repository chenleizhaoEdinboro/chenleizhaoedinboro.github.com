package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21902Test;

public class NA21902 {

	@DataProvider(name = "21902")
	public static Object[][] Data21902() {
		return Common.getFactoryData(new Object[][] {
			 { "AU", "Australia","01" },
			 { "US", "United States","02" },
			 { "HK", "Hong Kong S.A.R. of China","07" },
		 }, PropsUtils.getTargetStore("NA-21902"));
	}

	@Factory(dataProvider = "21902")
	public Object[] createTest(String store, String country, String billNumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA21902Test(store, country,billNumber);

		return tests;
	}

}
