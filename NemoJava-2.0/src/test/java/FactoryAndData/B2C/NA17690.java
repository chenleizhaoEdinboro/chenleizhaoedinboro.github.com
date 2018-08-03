package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17690Test;

public class NA17690 {

	@DataProvider(name = "17690")
	public static Object[][] Data17690() {
		return Common.getFactoryData(new Object[][] { 
			    { "AU" },
				{ "CA_AFFINITY" },
				{ "JP" }			
				}, PropsUtils.getTargetStore("NA-17690"));
	}

	@Factory(dataProvider = "17690")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17690Test(store);

		return tests;
	}

}
