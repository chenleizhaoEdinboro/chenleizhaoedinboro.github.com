package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19792Test;

public class NA19792 {

	@DataProvider(name = "19792")
	public static Object[][] Data19792() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","1213654197"}, 
			{ "US","1213577815"}
		},PropsUtils.getTargetStore("NA-19792"));
	}

	@Factory(dataProvider = "19792")
	public Object[] createTest(String store,String number) {

		Object[] tests = new Object[1];

		tests[0] = new NA19792Test(store,number);

		return tests;
	}

}

