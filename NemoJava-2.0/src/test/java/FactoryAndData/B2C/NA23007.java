package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23007Test;

public class NA23007 {
	
	@DataProvider(name = "23007")
	public static Object[][] Data23007(){
	return Common.getFactoryData(new Object[][]{
			{"AU","Australia"}
	},PropsUtils.getTargetStore("NA-23007"));	
	}
	
	@Factory(dataProvider = "23007")
	public Object[] createTest(String store,String Country) {

		Object[] tests = new Object[1];

		tests[0] = new NA23007Test(store,Country);

		return tests;
	}

}
