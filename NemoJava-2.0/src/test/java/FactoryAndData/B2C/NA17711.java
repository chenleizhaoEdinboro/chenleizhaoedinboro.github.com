package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17711Test;
import TestScript.B2C.NA20177Test;

public class NA17711 {

	@DataProvider(name = "17711")
	public static Object[][] Data17711() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","Australia" }
		},PropsUtils.getTargetStore("NA-17711"));
	}

	@Factory(dataProvider = "17711")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA17711Test(store,country);

		return tests;
	}

}
