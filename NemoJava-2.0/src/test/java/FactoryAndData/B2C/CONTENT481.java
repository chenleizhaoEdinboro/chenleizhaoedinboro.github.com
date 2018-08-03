package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CONTENT481Test;

public class CONTENT481 {
	@DataProvider(name = "CONTENT481")
	public static Object[][] Data481() {
		return Common.getFactoryData(new Object[][] { 
				{"US"},
				{"AU"},
				{"JP"}
			},PropsUtils.getTargetStore("CONTENT-481"));
		
	}

	@Factory(dataProvider = "CONTENT481")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new CONTENT481Test(store);

		return tests;
	}
  
}
