package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18082Test;

public class NA18082 {
	@DataProvider(name = "NA18082")
	public static Object[][] Data25376() {
		return Common.getFactoryData(new Object[][] {
				{ "AU"},
						},PropsUtils.getTargetStore("NA-18082"));
	}

	@Factory(dataProvider = "NA18082")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18082Test(store);

		return tests;
	}

}
