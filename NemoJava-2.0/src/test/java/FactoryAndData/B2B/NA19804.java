package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA19804Test;

public class NA19804 {

	@DataProvider(name = "19804")
	public static Object[][] Data19804() {
		return Common.getFactoryData(new Object[][] {
			{ "AU" ,"Your password has been changed"}, 
			{ "US" ,"Your password has been changed" }, 
			{ "JP" ,"Your password has been changed"}
		}, PropsUtils.getTargetStore("NA-19804"));
	}

	@Factory(dataProvider = "19804")
	public Object[] createTest(String store,String successMsgBox) {

		Object[] tests = new Object[1];

		tests[0] = new NA19804Test(store,successMsgBox);

		return tests;
	}

}
