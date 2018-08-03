package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22337Test;

public class NA22337 {

	@DataProvider(name = "22337")
	public static Object[][] Data22337() {
		return  Common.getFactoryData(new Object[][] { 
				{"US_BPCTO","/us/en/bpcto/insight/"}
		},PropsUtils.getTargetStore("NA-22337"));
	}

	@Factory(dataProvider = "22337")
	public Object[] createTest(String store,String Url) {

		Object[] tests = new Object[1];

		tests[0] = new NA22337Test(store,Url);

		return tests;
	}
}
