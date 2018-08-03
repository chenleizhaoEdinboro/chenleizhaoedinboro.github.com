package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.NA20349Test;

public class NA20349 {

	@DataProvider(name = "test")
	public static Object[][] Data20349() {
		return Common.getFactoryData(new Object[][] { { "US","United Status" } },
				PropsUtils.getTargetStore("NA20349"));
	}

	@Factory(dataProvider = "test")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA20349Test(store,country);
		
		return tests;
	}

}
