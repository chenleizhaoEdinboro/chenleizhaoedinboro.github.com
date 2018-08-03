package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28798Test;

public class NA28798 {
	@DataProvider(name = "28798")
	public static Object[][] Data28798() {
		return Common.getFactoryData(new Object[][] { 
				{ "JP","22TP2TXX380"},
				{ "US","22TP2TXX380"},
				{ "HK","22TP2TXX380"}
				},
				PropsUtils.getTargetStore("NA-28798"));
	}

	@Factory(dataProvider = "28798")
	public Object[] createTest(String store,String partnum) {

		Object[] tests = new Object[1];

		tests[0] = new NA28798Test(store,partnum);

		return tests;
	}
}
