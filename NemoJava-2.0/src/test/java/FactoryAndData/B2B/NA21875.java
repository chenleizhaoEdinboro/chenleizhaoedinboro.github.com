package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21875Test;

public class NA21875 {

	@DataProvider(name = "21875")
	public static Object[][] Data21875() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU"}, 
			{ "US"},
			{ "JP"}
		},PropsUtils.getTargetStore("NA-21875"));
	}

	@Factory(dataProvider = "21875")
	public Object[] createTest(String store) {
//	public Object[] createTest(String store, String b2bAccount) {
		Object[] tests = new Object[1];

		tests[0] = new NA21875Test(store);

		return tests;
	}

}
