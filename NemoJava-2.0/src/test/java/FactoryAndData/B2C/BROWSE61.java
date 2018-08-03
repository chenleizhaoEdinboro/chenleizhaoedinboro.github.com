package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE61Test;

public class BROWSE61 {
	@DataProvider(name = "61")
	public static Object[][] Data61() {
		return Common.getFactoryData(new Object[][] { 
				{"US","22TP2TA2750"},
				{"HK","22TP2TA2750"},
				{"JP","22TP2TA2750"}
			},PropsUtils.getTargetStore("BROWSE-61"));
		
	}

	@Factory(dataProvider = "61")
	public Object[] createTest(String store,String SubSeries) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE61Test( store,SubSeries);

		return tests;
	}
  
}
