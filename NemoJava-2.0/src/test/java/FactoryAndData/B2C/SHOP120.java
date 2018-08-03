package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP120Test;

public class SHOP120 {
	@DataProvider(name = "120")
	public static Object[][] Data120() {
		return Common.getFactoryData(new Object[][] { 
				{ "US_SMB"},

			},PropsUtils.getTargetStore("SHOPE-120"));
		
	}

	@Factory(dataProvider = "120")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP120Test(store);

		return tests;
	}

}
