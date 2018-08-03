package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26410Test;

public class NA26410 {

	@DataProvider(name = "NA26410")
	public static Object[][] Ddata26410() {
		return Common.getFactoryData(new Object[][] { 
			{ "CO", "COLOMBIA", "MEXICO" },
			{ "JP", "Japan", "KOREA, REPUBLIC OF" }
			}, PropsUtils.getTargetStore("NA-26410"));
	}

	@Factory(dataProvider = "NA26410")
	public Object[] createTest(String store, String country1, String country2) {

		Object[] tests = new Object[1];

		tests[0] = new NA26410Test(store, country1, country2);

		return tests;
	}

}