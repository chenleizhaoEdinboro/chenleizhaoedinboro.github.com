package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25385Test;

public class NA25385 {

	@DataProvider(name = "NA25385")
	public static Object[][] Ddata25385() {
		return Common.getFactoryData(new Object[][] { 
			    { "US"}
				},PropsUtils.getTargetStore("NA-25385"));
	}

	@Factory(dataProvider = "NA25385")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25385Test(store);

		return tests;
	}

}