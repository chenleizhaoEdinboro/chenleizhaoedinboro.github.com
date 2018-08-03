package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20136Test;

public class NA20136 {

	@DataProvider(name = "20136")
	public static Object[][] Data20136() {
		return Common.getFactoryData(new Object[][] { 
			{ "BR" }
		},PropsUtils.getTargetStore("NA-20136"));
	}

	@Factory(dataProvider = "20136")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];  

		tests[0] = new NA20136Test(store);

		return tests;
	}

}
