package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17361Test;

public class NA17361 {
	@DataProvider(name = "17361")
	public static Object[][] Data17361() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU","88GMY700794"},
				{ "US","88GMY700794"},
				{ "NZ","88GMY700794"},
				{ "USEPP","88GMY700794"},
				{ "CA","88GMY700794"}},
				PropsUtils.getTargetStore("NA-17361"));
	}

	@Factory(dataProvider = "17361")
	public Object[] createTest(String store,String partnumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA17361Test(store,partnumber);

		return tests;
	}
}
