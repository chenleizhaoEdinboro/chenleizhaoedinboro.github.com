package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23950Test;

public class NA23950 {

	@DataProvider(name = "23950")
	public static Object[][] Data23950() {
		return  Common.getFactoryData(new Object[][] { 
				{"US_BPCTO","http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/us/en/bpcto/insight/login"}
		},PropsUtils.getTargetStore("NA-23950"));
	}

	@Factory(dataProvider = "23950")
	public Object[] createTest(String store,String Url) {

		Object[] tests = new Object[1];

		tests[0] = new NA23950Test(store,Url);

		return tests;
	}
}
