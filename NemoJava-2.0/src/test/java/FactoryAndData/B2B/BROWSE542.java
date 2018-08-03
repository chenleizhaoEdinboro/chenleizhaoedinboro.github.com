package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.BROWSE542Test;

public class BROWSE542 {
	@DataProvider(name = "542")
	public static Object[][] Data542() {
		return Common.getFactoryData(new Object[][] { 

			//{ "AU","RR00000001"}, 
			{ "US","RR00000003" }
		},PropsUtils.getTargetStore("BROWSE-542"));
	}

	@Factory(dataProvider = "542")
	public Object[] createTest(String store, String Subscription) {


		Object[] tests = new Object[1];

		tests[0] = new BROWSE542Test(store,  Subscription );

		return tests;
	}
}
