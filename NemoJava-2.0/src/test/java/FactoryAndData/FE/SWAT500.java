package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.SWAT500Test;

public class SWAT500 {

	@DataProvider(name = "deals")
	public static Object[][] Data500() {
		return Common.getFactoryData(new Object[][] { {"US","United Status"} },
				PropsUtils.getTargetStore("SWAT500"));
	}

	@Factory(dataProvider = "deals")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT500Test(store,country);


		return tests;
	}

}
