package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22330Test;
import TestScript.B2C.NA22331Test;
import TestScript.B2C.NA23007Test;

public class NA22330 {
	
	@DataProvider(name = "22330")
	public static Object[][] Data22330(){
	return Common.getFactoryData(new Object[][]{
			{"AU",}
	},PropsUtils.getTargetStore("NA-22330"));	
	}
	
	@Factory(dataProvider = "22330")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA22330Test(store);

		return tests;
	}

}
