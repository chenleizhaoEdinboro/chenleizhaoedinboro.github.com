package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.DR20Test;

public class DR20 {

	@DataProvider(name = "DR20")
	public static Object[][] DataDR20() {
		return Common.getFactoryData(new Object[][] { 
				{ "FR", "80XC004LFR","80XC004HFR"},
				{ "IE", "81C80066UK","GX40H34821"} 
			}, PropsUtils.getTargetStore("DR-17"));
	}

	@Factory(dataProvider = "DR20")
	public Object[] createTest(String store, String MTMPartNumber, String Accessories) {

		Object[] tests = new Object[1];

		tests[0] = new DR20Test(store, MTMPartNumber,Accessories);

		return tests;
	}

}