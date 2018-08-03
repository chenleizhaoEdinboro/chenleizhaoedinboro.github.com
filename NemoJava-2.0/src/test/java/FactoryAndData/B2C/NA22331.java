package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22331Test;

public class NA22331 {
	
	@DataProvider(name = "22331")
	public static Object[][] Data22331(){
	return Common.getFactoryData(new Object[][]{
			{"US",}
	},PropsUtils.getTargetStore("NA-22331"));	
	}
	
	@Factory(dataProvider = "22331")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22331Test(store);

		return tests;
	}

}
