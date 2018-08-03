package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE18Test;
import TestScript.B2C.BROWSE23Test;

public class BROWSE18 {
	@DataProvider(name = "18")
	public static Object[][] Data18() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
				{"HK"},
				{"JP"},
				{"AU"}
			},PropsUtils.getTargetStore("BROWSE-18"));
		
	}

	@Factory(dataProvider = "18")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE18Test( store);

		return tests;
	}
  
}
