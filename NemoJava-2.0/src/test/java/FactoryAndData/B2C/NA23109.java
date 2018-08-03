package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23109Test;

public class NA23109 {
	
	@DataProvider(name = "23109")
	public static Object[][] Data23109(){
	return Common.getFactoryData(new Object[][]{
			{"JP"},
			{"US"}

	},PropsUtils.getTargetStore("NA-23109"));	
	}
	
	@Factory(dataProvider = "23109")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23109Test(store);

		return tests;
	}

}
