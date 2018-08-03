package FactoryAndData.FE;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.SWAT1455Test;

public class SWAT1455 {
	/*
	 * msharma2
	 * SWAT-1455
	 */
	@DataProvider(name = "test")
	public static Object[][] Data1455() {
		return Common.getFactoryData(new Object[][] { { "US","United Status" } },
				PropsUtils.getTargetStore("SWAT1455"));
	}

	@Factory(dataProvider = "test")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1455Test(store,country);

		return tests;
	}
}
