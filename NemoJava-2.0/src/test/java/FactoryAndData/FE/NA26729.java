package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.NA26729Test;

public class NA26729 {

	@DataProvider(name = "test")
	public static Object[][] Data26729() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("NA26729"));
	}


	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26729Test(store);
		return tests;
}
	
	
	
	
	
	
	
	
}
