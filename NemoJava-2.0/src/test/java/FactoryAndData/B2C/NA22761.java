package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22761Test;

public class NA22761 {

	@DataProvider(name = "22761")
	public static Object[][] Data22761() {
		return  Common.getFactoryData(new Object[][] { 
				{"MX"},
				{"CO"},
				{"BR"}
		},PropsUtils.getTargetStore("NA-22761"));
	}

	@Factory(dataProvider = "22761")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22761Test(store);

		return tests;
	}
}
