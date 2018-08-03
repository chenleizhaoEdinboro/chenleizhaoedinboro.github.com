package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26453Test;

public class NA26453 {

	@DataProvider(name = "NA26453")
	public static Object[][] Ddata26453() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_OUTLET"}},
				PropsUtils.getTargetStore("NA-26453"));
	}

	@Factory(dataProvider = "NA26453")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA26453Test(store);

		return tests;
	}

}