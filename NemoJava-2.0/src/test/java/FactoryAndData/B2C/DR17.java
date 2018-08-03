package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.DR17Test;

public class DR17 {

	@DataProvider(name = "DR17")
	public static Object[][] DataDR17() {
		return Common.getFactoryData(new Object[][] { 
				{ "FR", "80X7004WFR", "06P4069"},
				{ "IE", "80U20055UK", "06P4069"} 
			}, PropsUtils.getTargetStore("DR-17"));
	}

	@Factory(dataProvider = "DR17")
	public Object[] createTest(String store, String MTMPartNumber, String Accessories) {

		Object[] tests = new Object[1];

		tests[0] = new DR17Test(store, MTMPartNumber, Accessories);

		return tests;
	}

}