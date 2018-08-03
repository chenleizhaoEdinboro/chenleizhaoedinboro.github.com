package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT46Test;

public class CONTENT46 {
	@DataProvider(name = "CONTENT46")
	public static Object[][] Data46() {
		return Common.getFactoryData(new Object[][] { 
				{"US","22TP2TXX16G"},
				{"AU","22TP2TXX16G"}
			},PropsUtils.getTargetStore("CONTENT-46"));
		
	}

	@Factory(dataProvider = "CONTENT46")
	public Object[] createTest(String store,String SubSeries) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT46Test(store,SubSeries);

		return tests;
	}
  
}
