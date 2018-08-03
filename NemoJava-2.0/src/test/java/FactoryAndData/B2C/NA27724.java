package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27724Test;

public class NA27724 {

	@DataProvider(name = "27724")
	public static Object[][] Data27724() {
		return Common.getFactoryData(
				new Object[][] { { "US", "United States", "Canada", "20KH002QUS", "20KH002QUS", "4X40N18009" } },
				PropsUtils.getTargetStore("NA-27724"));
	}

	@Factory(dataProvider = "27724")
	public Object[] createTest(String store, String country1, String country2, String product1, String product2,
			String optionID) {

		Object[] tests = new Object[1];

		tests[0] = new NA27724Test(store, country1, country2, product1, product2, optionID);

		return tests;
	}

}
