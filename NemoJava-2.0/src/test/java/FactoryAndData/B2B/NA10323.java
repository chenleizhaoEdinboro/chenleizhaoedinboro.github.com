package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA10323Test;

public class NA10323 {
	@DataProvider(name = "10323")
	public static Object[][] Data10323() {
		return Common.getFactoryData(new Object[][] { 
			{ "US"}
		},PropsUtils.getTargetStore("NA-10323"));
	}

	@Factory(dataProvider = "10323")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA10323Test(store);

		return tests;
	}
}
