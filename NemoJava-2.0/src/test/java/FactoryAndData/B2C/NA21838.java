package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21838Test;

public class NA21838 {

	@DataProvider(name = "21838")
	public static Object[][] Data21838() {
		return Common.getFactoryData(new Object[][] { 
			 { "AU", "Australia","01" },
			 { "US", "United States","02" },
			 { "HK", "Hong Kong S.A.R. of China","07" },
			 { "JP", "Japan","08" }
				}, PropsUtils.getTargetStore("NA-21838"));
	}

	@Factory(dataProvider = "21838")
	public Object[] createTest(String store, String country, String billNumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA21838Test(store, country,billNumber);

		return tests;
	}

}
