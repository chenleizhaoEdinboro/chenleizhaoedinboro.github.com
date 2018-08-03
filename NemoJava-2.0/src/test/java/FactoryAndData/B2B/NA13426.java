package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA13426Test;

public class NA13426 {
	@DataProvider(name = "13426")
	public static Object[][] Data13426() {
		return Common.getFactoryData(new Object[][] { 
			{ "US","1213577815","1213348423","1213348423","20HECTO1WWENUSB"}
		},PropsUtils.getTargetStore("NA-13426"));
	}

	@Factory(dataProvider = "13426")
	public Object[] createTest(String store, String defaultUnit, String defaultDMU, String AccessLevel, String productNum) {

		Object[] tests = new Object[1];

		tests[0] = new NA13426Test(store,defaultUnit,defaultDMU,AccessLevel,productNum);

		return tests;
	}
}
