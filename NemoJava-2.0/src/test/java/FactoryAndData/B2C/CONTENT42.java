package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT42Test;

public class CONTENT42 {
	@DataProvider(name = "CONTENT42")
	public static Object[][] Data42() {
		return Common.getFactoryData(new Object[][] { 
				{"AU","aupremium_unit"},
				{"NZ","nzAffinity_unit"}
			},PropsUtils.getTargetStore("CONTENT-42"));
		
	}

	@Factory(dataProvider = "CONTENT42")
	public Object[] createTest(String store,String SubSeries) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT42Test(store,SubSeries);

		return tests;
	}
  
}
