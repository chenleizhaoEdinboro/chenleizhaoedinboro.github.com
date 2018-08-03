package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21876Test;

public class NA21876 {

	@DataProvider(name = "21876")
	public static Object[][] Data21876() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU", "$"}, 
			{ "US", "$"},
			{ "JP", "￥"}
		},PropsUtils.getTargetStore("NA-21876"));
	}

	@Factory(dataProvider = "21876")
	public Object[] createTest(String store, String currencySign) {
		Object[] tests = new Object[1];

		tests[0] = new NA21876Test(store,currencySign);

		return tests;
	}

}
