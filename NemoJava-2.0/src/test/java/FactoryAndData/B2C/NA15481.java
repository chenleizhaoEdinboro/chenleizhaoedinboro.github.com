package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA15481Test;

public class NA15481 {

	@DataProvider(name = "15481")
	public static Object[][] Data15481() {
		return Common.getFactoryData(new Object[][] { { "JP" } },
				PropsUtils.getTargetStore("NA-15481"));
	}

	@Factory(dataProvider = "15481")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA15481Test(store);

		return tests;
	}

}
