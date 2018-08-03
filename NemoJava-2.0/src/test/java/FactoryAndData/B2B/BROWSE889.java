package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.BROWSE889Test;

public class BROWSE889 {
	@DataProvider(name = "889")
	public static Object[][] Data889() {
		return Common.getFactoryData(new Object[][] { 

			//{ "AU","RR00000001"}, 
			{ "US","RR00000003" }
		},PropsUtils.getTargetStore("BROWSE-889"));
	}

	@Factory(dataProvider = "889")
	public Object[] createTest(String store, String Subscription) {


		Object[] tests = new Object[1];

		tests[0] = new BROWSE889Test(store,  Subscription );

		return tests;
	}
}
