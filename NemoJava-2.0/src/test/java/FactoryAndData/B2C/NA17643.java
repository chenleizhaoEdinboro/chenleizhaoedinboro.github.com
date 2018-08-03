package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17643Test;


public class NA17643 {
	@DataProvider(name = "17643")
	public static Object[][] Data17643() {
		return Common.getFactoryData(new Object[][] {				
				{ "AU"},				
				{ "US"},
				{ "USEPP"},				
				{ "CA_AFFINITY"},
				{ "US_BPCTO"},			
				{ "HK"},				
				{ "JP"}					
		},PropsUtils.getTargetStore("NA-17643"));
	}

	@Factory(dataProvider = "17643")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17643Test(store);

		return tests;
	}

}
