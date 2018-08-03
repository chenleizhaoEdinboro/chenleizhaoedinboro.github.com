package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.SWAT1366Test;

public class SWAT1366 {

	@DataProvider(name = "test")
	public static Object[][] Data1366() {
		return Common.getFactoryData(new Object[][] { {"US","United Status"} },
				PropsUtils.getTargetStore("SWAT1366"));
	}

	@Factory(dataProvider = "test")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1366Test(store,country);
		
		return tests;
	}

}
