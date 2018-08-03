
package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17176Test;

public class NA17176 {

	@DataProvider(name = "17176")
	public static Object[][] Data17176() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}, 
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-17176"));
	}

	@Factory(dataProvider = "17176")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17176Test(store);

		return tests;
	}

}
