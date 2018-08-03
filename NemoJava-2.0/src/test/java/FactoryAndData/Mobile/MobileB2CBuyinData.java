package FactoryAndData.Mobile;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.Mobile.MobileB2CBuyIn;
import TestScript.B2C.NA16452Test;

public class MobileB2CBuyinData {

	@DataProvider(name = "16452")
	public static Object[][] Data16452() {
		return new Object[][] { 
			{ "AU" }};
	}

	@Factory(dataProvider = "16452")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new MobileB2CBuyIn(store);

		return tests;
	}

	//
}
