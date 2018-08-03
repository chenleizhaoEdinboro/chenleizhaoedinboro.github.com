package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19421Test;

public class NA19421 {

	@DataProvider(name = "19421")
	public static Object[][] Data19421() {
		return Common.getFactoryData(new Object[][] { 
			{ "BR" }
			},PropsUtils.getTargetStore("NA-19421"));
	}

	@Factory(dataProvider = "19421")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19421Test(store);

		return tests;
	}

}
