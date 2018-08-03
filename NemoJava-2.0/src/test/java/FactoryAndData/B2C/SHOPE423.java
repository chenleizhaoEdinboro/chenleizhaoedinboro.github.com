package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOPE423Test;

public class SHOPE423 {
	@DataProvider(name = "SHOPE423")
	public static Object[][] Data18() {
		return Common.getFactoryData(new Object[][] { 
			{ "US", "United States", "US web store unit" },
			},PropsUtils.getTargetStore("SHOPE-423"));
		
	}

	@Factory(dataProvider = "SHOPE423")
	public Object[] createTest(String store,String country,String unit) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE423Test(store, country, unit);

		return tests;
	}
  
}
