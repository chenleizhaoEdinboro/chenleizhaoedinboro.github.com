package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.SHOPE342Test;

public class SHOPE342 {

	@DataProvider(name = "SHOPE342")
	public static Object[][] Data342() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU", "Australia", "1213286247"}
			},
			PropsUtils.getTargetStore("SHOPE-342"));
	}

	@Factory(dataProvider = "SHOPE342")
	public Object[] createTest(String store,String country,String unit) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE342Test(store,country,unit);

		return tests;
	}

}



