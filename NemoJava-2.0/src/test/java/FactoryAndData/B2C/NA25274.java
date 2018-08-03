package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25274Test;

public class NA25274 {
	@DataProvider(name = "25274")
	public static Object[][] Data25274() {
		return Common.getFactoryData(new Object[][] { 
				{ "MY", "22TP2TX2700"}
			},PropsUtils.getTargetStore("NA-25274"));
		
	}

	@Factory(dataProvider = "25274")
	public Object[] createTest(String store, String productNO) {

		Object[] tests = new Object[1];

		tests[0] = new NA25274Test(store,productNO);

		return tests;
	}

}