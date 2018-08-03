package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23009Test;

public class NA23009 {
	
	@DataProvider(name = "23009")
	public static Object[][] Data23009(){
	return Common.getFactoryData(new Object[][]{
			{"US"}
	},PropsUtils.getTargetStore("NA-23009"));	
	}
	
	@Factory(dataProvider = "23009")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23009Test(store);

		return tests;
	}

}
