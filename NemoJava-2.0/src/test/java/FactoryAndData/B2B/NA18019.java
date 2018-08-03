package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18019Test;

public class NA18019 {
	@DataProvider(name = "18019")
	public static Object[][] Data18019() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" },
				//{ "US" }
		},PropsUtils.getTargetStore("NA-18019"));
	}

	@Factory(dataProvider = "18019")
	public Object[] createTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18019Test(store);
		return tests;
	}
}
