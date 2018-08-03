package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA28440Test;

public class NA28440{

	@DataProvider(name = "NA28440")
	public static Object[][] Data28440() {
		return Common.getFactoryData(new Object[][] {
			{ "JP","Japan","JP-B2B" }
		}, PropsUtils.getTargetStore("NA-28440"));
	}
	@Factory(dataProvider = "NA28440")
	public Object[] createTest(String store,String country,String ruleName) {

		Object[] tests = new Object[1];

		tests[0] = new NA28440Test(store,country,ruleName);

		return tests;
	}
}
