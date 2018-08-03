package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19972Test;

public class NA19972 {

	@DataProvider(name = "NA19972")
	public static Object[][] Ddata19972() {
		return Common.getFactoryData(new Object[][] { 
			    { "US", "4N40A33715" },
				{ "USEPP", "4N40A33715" }, 
				{ "US_BPCTO", "4N40A33715" } 
				},PropsUtils.getTargetStore("NA-19972"));
	}

	@Factory(dataProvider = "NA19972")
	public Object[] createTest(String store, String productDCG) {

		Object[] tests = new Object[1];

		tests[0] = new NA19972Test(store, productDCG);

		return tests;
	}

}
