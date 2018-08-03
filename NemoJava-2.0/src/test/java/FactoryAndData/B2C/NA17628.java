package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17628Test;

public class NA17628 {
	@DataProvider(name = "17628")
	public static Object[][] Data17628() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","22TP2CP011220JB"},
			{ "CA","22TP2CP011220JB"},
			{ "JP","22TP2CP011220JB"},
			{ "US","22TP2CP011220JB"}
				},PropsUtils.getTargetStore("NA-17628"));
	}

	@Factory(dataProvider = "17628")
	public Object[] createTest(String store,String partNum) {

		Object[] tests = new Object[1];

		tests[0] = new NA17628Test(store,partNum);

		return tests;
	}
}
