package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18461Test;

public class NA18461 {

	@DataProvider(name = "NA18461")
	public static Object[][] Ddata15492() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU" ,"Australia"}
			//{ "US" ,"United States"} 
			}, PropsUtils.getTargetStore("NA-18461"));
	}

	@Factory(dataProvider = "NA18461")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA18461Test(store,country);

		return tests;
	}

}