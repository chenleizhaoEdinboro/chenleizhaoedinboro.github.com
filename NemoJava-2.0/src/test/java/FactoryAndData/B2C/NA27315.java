package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27315Test;

public class NA27315 {

	@DataProvider(name = "27315")
	public static Object[][] Data15481() {
		return Common.getFactoryData(new Object[][] { 
				{ "US","usweb"} 
				},
				PropsUtils.getTargetStore("NA-27315"));
	}

	@Factory(dataProvider = "27315")
	public Object[] createTest(String store, String unit) {

		Object[] tests = new Object[1];

		tests[0] = new NA27315Test(store,unit);

		return tests;
	}

}
