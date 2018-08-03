package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28439Test;

public class NA28439{

	@DataProvider(name = "NA28439")
	public static Object[][] Data28439() {
		return Common.getFactoryData(new Object[][] {
			{ "CA","Canada","CA-B2C" }
		}, PropsUtils.getTargetStore("NA-28439"));
	}
	@Factory(dataProvider = "NA28439")
	public Object[] createTest(String store,String country,String ruleName) {

		Object[] tests = new Object[1];

		tests[0] = new NA28439Test(store,country,ruleName);

		return tests;
	}
}
