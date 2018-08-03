package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.NA22916Test;

public class NA22916 {
	@DataProvider(name = "test")
	public static Object[][] Data22916() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("NA22916"));
	}


	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];
		
		tests[0] = new NA22916Test(store);
		return tests;
}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
