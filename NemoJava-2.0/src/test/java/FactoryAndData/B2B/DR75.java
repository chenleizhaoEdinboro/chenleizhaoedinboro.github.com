package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.DR75Test;

public class DR75 {

	@DataProvider(name = "75")
	public static Object[][] Data75() {
		return Common.getFactoryData(new Object[][] { 
			{ "FR" }},
				PropsUtils.getTargetStore("DR-75"));
	}

	@Factory(dataProvider = "75")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new DR75Test(store);

		return tests;
	}

}
