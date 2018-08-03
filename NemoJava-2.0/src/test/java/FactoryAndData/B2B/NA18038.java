package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18038Test;

public class NA18038 {
	@DataProvider(name = "18038")
	public static Object[][] Data17699() {
		return Common.getFactoryData(new Object[][] { 
				//{ "AU" }, 
			{ "US" }
		},PropsUtils.getTargetStore("NA-18038"));
	}

	@Factory(dataProvider = "18038")
	public Object[] CreateTest(String store) {
		Object[] tests = new Object[1];
		tests[0] = new NA18038Test(store);
		return tests;
	}
}
