package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.SHOPE336Test;

public class SHOPE336 {

	@DataProvider(name = "SHOPE336")
	public static Object[][] Data336() {
		return Common.getFactoryData(new Object[][] { 
			 //{ "US", "RR00000001", "20HAS1L000", "Subscription Test" },
			{ "US", "RR00000001", "20JES1X200", "Subscription Test" },
			
			},
			PropsUtils.getTargetStore("SHOPE-336"));
	}

	@Factory(dataProvider = "SHOPE336")
	public Object[] createTest(String store, String subscription, String product, String sectionTitle) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE336Test(store, subscription, product, sectionTitle);

		return tests;
	}

}

