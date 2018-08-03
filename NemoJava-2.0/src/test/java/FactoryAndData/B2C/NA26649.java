package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26649Test;

public class NA26649 {
	@DataProvider(name = "26649")
	public static Object[][] Data26649() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU", "NEWTAG"},
				{ "NZ","NEWTAG"}
			},PropsUtils.getTargetStore("NA-26649"));
		
	}

	@Factory(dataProvider = "26649")
	public Object[] createTest(String store, String tag) {

		Object[] tests = new Object[1];

		tests[0] = new NA26649Test(store,tag);

		return tests;
	}

}

