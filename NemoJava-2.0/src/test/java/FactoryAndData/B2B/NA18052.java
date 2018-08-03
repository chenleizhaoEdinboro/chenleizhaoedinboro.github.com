package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18052Test;

public class NA18052 {

	@DataProvider(name = "18052")
	public static Object[][] Data18052() {
		return Common.getFactoryData(new Object[][] {
				{ "US"} ,
				//{ "AU"},
				//{ "JP"} 
				

		},PropsUtils.getTargetStore("NA-18052"));
	}

	@Factory(dataProvider = "18052")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA18052Test(store);

		return tests;
	}

}
