package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21944Test;

public class NA21944 {
	@DataProvider(name = "21944")
	public static Object[][] Data21944() {
		return Common.getFactoryData( new Object[][] { 
			{ "US"}

		},PropsUtils.getTargetStore("NA-21944"));
	}

	@Factory(dataProvider = "21944")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

	 	tests[0] = new NA21944Test(store);

		return tests;
	}

}
