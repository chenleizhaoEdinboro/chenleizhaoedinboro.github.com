package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26709Test;

public class NA26709 {

	@DataProvider(name = "NA26709")
	public static Object[][] Data26709() {
		return Common.getFactoryData(new Object[][] {
				{ "US","12135","23AB123"},
				{ "CA","12135","23AB123"},
				{ "JP","12135","23AB123"},
				{ "AU","12135","23AB123"}
		},PropsUtils.getTargetStore("NA-26709"));
	}

	@Factory(dataProvider = "NA26709")
	public Object[] createTest(String store, String errorNumber, String rightNumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA26709Test(store,errorNumber,rightNumber);

		return tests;
	}

}
