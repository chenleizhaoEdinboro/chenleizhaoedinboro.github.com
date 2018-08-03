package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.SWAT1239Test;

public class SWAT1239{

	@DataProvider(name = "test")
	public static Object[][] Data1239() {
		return Common.getFactoryData(new Object[][] { { "US","United Status" } },
				PropsUtils.getTargetStore("SWAT1239"));
	}

	@Factory(dataProvider = "test")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1239Test(store,country);
		
		return tests;
	}

}
