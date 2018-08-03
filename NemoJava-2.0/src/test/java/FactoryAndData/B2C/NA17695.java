package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17695Test;

public class NA17695 {

	@DataProvider(name = "NA17695")
	public static Object[][] Data17695() {
		return Common.getFactoryData(new Object[][] {				 
			 { "US" }
						},PropsUtils.getTargetStore("NA-17695"));
	}

	@Factory(dataProvider = "NA17695")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA17695Test(store);

		return tests;
	}

}
