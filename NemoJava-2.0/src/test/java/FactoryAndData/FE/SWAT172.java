package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.SWAT172Test;

public class SWAT172 {

	@DataProvider(name = "test")
	public static Object[][] Data172() {
		return Common.getFactoryData(new Object[][] { { "US","United Status" } },
				PropsUtils.getTargetStore("SWAT172"));
	}

	@Factory(dataProvider = "test")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT172Test(store,country);
		
		return tests;
	}

}
