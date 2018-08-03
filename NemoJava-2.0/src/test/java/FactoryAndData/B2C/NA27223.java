package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27223Test;

public class NA27223 {

	@DataProvider(name = "27223")
	public static Object[][] Data27223() {
		return Common.getFactoryData(new Object[][] { 
			{ "US","usweb" }
		},PropsUtils.getTargetStore("NA-27223"));
	}

	@Factory(dataProvider = "27223")
	public Object[] createTest(String store,String b2cUnit) {

		Object[] tests = new Object[1];

		tests[0] = new NA27223Test(store,b2cUnit);

		return tests;
	}

}
