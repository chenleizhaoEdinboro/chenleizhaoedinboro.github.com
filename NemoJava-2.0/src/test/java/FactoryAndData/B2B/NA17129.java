package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17129Test;

public class NA17129 {

	@DataProvider(name = "17129")
	public static Object[][] Data17129() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU"}, 
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-17129"));
	}

	@Factory(dataProvider = "17129")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17129Test(store);

		return tests;
		
	}

}
