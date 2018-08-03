package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOPE293Test;

public class SHOPE293 {
	@DataProvider(name = "SHOPE293")
	public static Object[][] SHOPE293() {
		return Common.getFactoryData(new Object[][] {
				{ "US","20KFCTO1WWENUS0"},
						},PropsUtils.getTargetStore("SHOPE-293"));
	}

	@Factory(dataProvider = "SHOPE293")
	public Object[] createTest(String store,String product) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE293Test(store, product);

		return tests;
	}

}