package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19971Test;

public class NA19971 {

	@DataProvider(name = "NA19971")
	public static Object[][] Ddata19971() {
		return Common.getFactoryData(new Object[][] { 
			    { "US", "4N40A33715" },
				{ "USEPP", "4N40A33715" }, 
				{ "US_BPCTO", "4N40A33715" } 
				},PropsUtils.getTargetStore("NA-19971"));
	}
	@Factory(dataProvider = "NA19971")
	public Object[] createTest(String store, String productDCG) {

		Object[] tests = new Object[1];

		tests[0] = new NA19971Test(store, productDCG);

		return tests;
	}

}
