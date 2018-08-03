package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18570Test;

public class NA18570 {

	@DataProvider(name = "NA18570")
	public static Object[][] Ddata18570() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","22TP2TX2700"},
			{ "US","22TP2TX2700"},
			{ "NZ","22TP2TX2700"},
			{ "USEPP","22TP2TX2700"},
			{ "CA","22TP2TX2700"}},
				PropsUtils.getTargetStore("NA-18570"));
	}

	@Factory(dataProvider = "NA18570")
	public Object[] createTest(String store,String number) {

		Object[] tests = new Object[1];

		tests[0] = new NA18570Test(store,number);

		return tests;
	}

}