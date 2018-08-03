package FactoryAndData.Mobile;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.Mobile.NA16389Mobile;
import TestScript.B2B.NA16389Test;

public class NA16389MobileData {

	@DataProvider(name = "16389")
	public static Object[][] Data16389() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU" }
			//,{ "US" }
		},PropsUtils.getTargetStore( "16389"));
	}

	@Factory(dataProvider = "16389")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA16389Mobile(store);

		return tests;
	}

}

