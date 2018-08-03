package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22340Test;

public class NA22340 {

	@DataProvider(name = "22340")
	public static Object[][] Data22340() {
		return  Common.getFactoryData(new Object[][] { 
				{"US_BPCTO","/us/en/bpcto/insight/"}
		},PropsUtils.getTargetStore("NA-22340"));
	}

	@Factory(dataProvider = "22340")
	public Object[] createTest(String store,String Url) {

		Object[] tests = new Object[1];

		tests[0] = new NA22340Test(store,Url);

		return tests;
	}
}
