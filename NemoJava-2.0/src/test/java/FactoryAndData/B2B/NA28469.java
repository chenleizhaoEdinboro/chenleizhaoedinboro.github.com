package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28469Test;

public class NA28469 {

	@DataProvider(name = "28469")
	public static Object[][] Data28469() {

		return Common.getFactoryData(new Object[][] {
				{"AU","20HGS0CB0P"},
				{"US","20JES1X200"}
		},PropsUtils.getTargetStore("NA-28469"));

	}

	@Factory(dataProvider = "28469")
	public Object[] createTest(String store,String partnumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA28469Test(store,partnumber);

		return tests;
	}

}
