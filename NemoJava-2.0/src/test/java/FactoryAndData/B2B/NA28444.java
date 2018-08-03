package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28444Test;

public class NA28444{

	@DataProvider(name = "NA28444")
	public static Object[][] Data28444() {
		return Common.getFactoryData(new Object[][] {
			{ "JP","Japan","JP-B2B" }
		}, PropsUtils.getTargetStore("NA-28444"));
	}
	@Factory(dataProvider = "NA28444")
	public Object[] createTest(String store,String country,String ruleName) {

		Object[] tests = new Object[1];

		tests[0] = new NA28444Test(store,country,ruleName);

		return tests;
	}
}
