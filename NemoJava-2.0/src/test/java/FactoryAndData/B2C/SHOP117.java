package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP117Test;

public class SHOP117 {
	@DataProvider(name = "117")
	public static Object[][] Data117() {
		return Common.getFactoryData(new Object[][] { 
				{ "US_SMB"},

			},PropsUtils.getTargetStore("SHOPE-117"));
		
	}

	@Factory(dataProvider = "117")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP117Test(store);

		return tests;
	}

}
