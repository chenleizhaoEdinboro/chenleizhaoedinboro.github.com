package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22341Test;

public class NA22341 {

	@DataProvider(name = "22341")
	public static Object[][] Data22341() {
		return  Common.getFactoryData(new Object[][] { 
				{"AU","/au/en/auingramreseller/"}
		},PropsUtils.getTargetStore("NA-22341"));
	}

	@Factory(dataProvider = "22341")
	public Object[] createTest(String store,String Url) {

		Object[] tests = new Object[1];

		tests[0] = new NA22341Test(store,Url);

		return tests;
	}
}
