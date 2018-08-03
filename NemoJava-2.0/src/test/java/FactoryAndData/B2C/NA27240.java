package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27240Test;
import TestScript.B2C.NA20177Test;

public class NA27240 {

	@DataProvider(name = "27240")
	public static Object[][] Data27240() {
		return Common.getFactoryData(new Object[][] { 
			{ "US","nemoPriceRangeUSD" },
			{"JP","nemoPriceRangeJPY" }
		},PropsUtils.getTargetStore("NA-27240"));
	}

	@Factory(dataProvider = "27240")
	public Object[] createTest(String store,String rangeName) {

		Object[] tests = new Object[1];

		tests[0] = new NA27240Test(store,rangeName);

		return tests;
	}

}
