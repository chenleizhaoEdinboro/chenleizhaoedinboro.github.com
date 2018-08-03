package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20149Test;

public class NA20149 {

	@DataProvider(name = "20149")
	public static Object[][] Data20149() {
		return Common.getFactoryData(new Object[][] { 
				{ "CO", "80SX0074LM"}
			},PropsUtils.getTargetStore("NA-20149"));
		
	}

	@Factory(dataProvider = "20149")
	public Object[] createTest(String store, String productNO) {

		Object[] tests = new Object[1];

		tests[0] = new NA20149Test(store,productNO);

		return tests;
	}

}
