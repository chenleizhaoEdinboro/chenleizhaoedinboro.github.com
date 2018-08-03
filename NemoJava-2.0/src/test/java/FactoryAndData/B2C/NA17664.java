package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17664Test;
import TestScript.B2C.NA22330Test;
import TestScript.B2C.NA22331Test;
import TestScript.B2C.NA23007Test;

public class NA17664 {
	
	@DataProvider(name = "17664")
	public static Object[][] Data22330(){
	return Common.getFactoryData(new Object[][]{
			
			{"US"}
	},PropsUtils.getTargetStore("NA-17664"));	
	}
	
	@Factory(dataProvider = "17664")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17664Test(store);

		return tests;
	}

}
