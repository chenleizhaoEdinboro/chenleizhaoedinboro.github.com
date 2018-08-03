package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28205Test;

public class NA28205 {
	@DataProvider(name = "28205")
	public static Object[][] Data28205() {
		return Common.getFactoryData(new Object[][] { 
				{ "CO_OUTLET","60FBHAR1US" },
				{ "BR_OUTLET","4X30M39463" },
				{ "MX_OUTLET","60G2AAR6US" }
			},PropsUtils.getTargetStore("NA-28205"));
		
	}

	@Factory(dataProvider = "28205")
	public Object[] createTest(String store,String ProductNo) {

		Object[] tests = new Object[1];

		tests[0] = new NA28205Test(store,ProductNo);

		return tests;
	}

}
