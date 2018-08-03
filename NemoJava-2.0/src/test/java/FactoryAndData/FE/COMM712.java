package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.COMM712Test;

public class COMM712 {

	@DataProvider(name = "test")
	public static Object[][] Data712() {
		return Common.getFactoryData(new Object[][] { { "US","United Status" } },
				PropsUtils.getTargetStore("COMM712"));
	}

	@Factory(dataProvider = "test")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new COMM712Test(store,country);
		
		return tests;
	}

}
