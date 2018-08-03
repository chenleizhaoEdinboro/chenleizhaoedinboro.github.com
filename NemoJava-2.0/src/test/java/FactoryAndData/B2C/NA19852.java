package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19852Test;

public class NA19852 {

	@DataProvider(name = "19852")
	public static Object[][] Data19852() {
		return Common.getFactoryData(new Object[][] { 
			{ "BR","brweb" }
		},PropsUtils.getTargetStore("NA-19852"));
	}

	@Factory(dataProvider = "19852")
	public Object[] createTest(String store,String b2cUnit) {

		Object[] tests = new Object[1];

		tests[0] = new NA19852Test(store,b2cUnit);

		return tests;
	}

}
