package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18975Test;

public class NA18975 {

	@DataProvider(name = "18975")
	public static Object[][] Data18975() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }
				},PropsUtils.getTargetStore("NA-18975"));
	}

	@Factory(dataProvider = "18975")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18975Test(store);

		return tests;
	}

}
