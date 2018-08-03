package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE18Test;
import TestScript.B2C.BROWSE540Test;
import TestScript.B2C.BROWSE62Test;

public class BROWSE540 {
	@DataProvider(name = "540")
	public static Object[][] Data540() {
		return Common.getFactoryData(new Object[][] { 
			{ "JP","81BV004QJP","0C19886", "888015005", "22TP2TXX16G" },
			},PropsUtils.getTargetStore("BROWSE-540"));
		
	}

	@Factory(dataProvider = "540")
	public Object[] createTest(String store, String model, String accessory1, String accessory2, String subseries) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE540Test( store, model, accessory1, accessory2, subseries);

		return tests;
	}
  
}