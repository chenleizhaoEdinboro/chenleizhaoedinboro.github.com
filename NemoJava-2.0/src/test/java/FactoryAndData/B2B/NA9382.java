package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA9382Test;

public class NA9382 {
	@DataProvider(name = "9382")
	public static Object[][] Data9382() {
		return Common.getFactoryData(new Object[][] { 
			{ "US","1213577815","1213348423","1213348423","20JES1X200","20JES1X200"}
		},PropsUtils.getTargetStore("NA-9382"));
	}

	@Factory(dataProvider = "9382")
	public Object[] createTest(String store, String defaultUnit, String defaultDMU, String AccessLevel, String productNum1,String productNum2) {

		Object[] tests = new Object[1];

		tests[0] = new NA9382Test(store,defaultUnit,defaultDMU,AccessLevel,productNum1,productNum2);

		return tests;
	}
}
