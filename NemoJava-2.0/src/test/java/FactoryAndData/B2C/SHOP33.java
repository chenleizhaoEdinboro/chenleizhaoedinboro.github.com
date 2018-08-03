package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP33Test;

public class SHOP33 {
	@DataProvider(name = "SHOP33")
	public static Object[][] Data18() {
		return Common.getFactoryData(new Object[][] { 
			{ "US", "United States", "US web store unit", "B2C" },
			{ "AU", "Australia", "1213654197", "B2B"}
			},PropsUtils.getTargetStore("SHOP-33"));
		
	}

	@Factory(dataProvider = "SHOP33")
	public Object[] createTest(String store,String country,String unit,String group) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP33Test(store, country, unit, group);

		return tests;
	}
  
}
