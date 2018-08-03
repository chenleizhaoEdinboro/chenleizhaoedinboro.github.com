package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22333Test;

public class NA22333 {

	@DataProvider(name = "22333")
	public static Object[][] Data22333() {
		return  Common.getFactoryData(new Object[][] { 
				
				{"US_BPCTO","/us/en/bpcto/insight/"}
		},PropsUtils.getTargetStore("22333"));
	}

	@Factory(dataProvider = "22333")
	public Object[] createTest(String store,String Url) {

		Object[] tests = new Object[1];

		tests[0] = new NA22333Test(store,Url);

		return tests;
	}
}
