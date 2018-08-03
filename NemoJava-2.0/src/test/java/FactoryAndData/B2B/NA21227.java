package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21227Test;

public class NA21227 {

	@DataProvider(name = "21227")
	public static Object[][] Data21226() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU" }
			},PropsUtils.getTargetStore("NA-21227"));
	}

	@Factory(dataProvider = "21227")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA21227Test(store);

		return tests;
	}

}
