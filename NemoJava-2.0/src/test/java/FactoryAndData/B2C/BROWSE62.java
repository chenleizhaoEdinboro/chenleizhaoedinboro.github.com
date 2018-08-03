package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE62Test;

public class BROWSE62 {
	@DataProvider(name = "62")
	public static Object[][] Data62() {
		return Common.getFactoryData(new Object[][] { 
				{ "US","22TP2TT4700","88YG9000859", "88YG900085980Y7" },

		}, PropsUtils.getTargetStore("BROWSE-62"));

	}

	@Factory(dataProvider = "62")
	public Object[] createTest(String store, String subseriesOne,
			String subseriesTwo, String MT) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE62Test(store, subseriesOne, subseriesTwo, MT);

		return tests;
	}

}
