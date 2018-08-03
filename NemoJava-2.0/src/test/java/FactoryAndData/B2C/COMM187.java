package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM187Test;
import TestScript.B2C.NA21153Test;

public class COMM187 {

	@DataProvider(name = "COMM187")
	public static Object[][] DataCOMM187() {
		return Common.getFactoryData(new Object[][] { 
			
			{ "BR" },
		},PropsUtils.getTargetStore("COMM-187"));
	}

	@Factory(dataProvider = "COMM187")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new COMM187Test(store);

		return tests;
	}

}
