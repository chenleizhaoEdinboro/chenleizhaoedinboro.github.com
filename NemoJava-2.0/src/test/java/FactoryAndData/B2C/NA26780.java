package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA26780Test;
import TestScript.B2C.NA20177Test;

public class NA26780 {

	@DataProvider(name = "26780")
	public static Object[][] Data26780() {
		return Common.getFactoryData(new Object[][] { 
			{ "US","22TP2TXX15G","11TC1MTM60010G9" },
			{"JP","22TP2TXX15G","11TC1MTM60010G9" },
			{"HK","22TP2TXX15G","11TC1MTM60010G9" },
			{"GB","22TP2TXX15G","11TC1MTM60010G9" }
		},PropsUtils.getTargetStore("NA-26780"));
	}

	@Factory(dataProvider = "26780")
	public Object[] createTest(String store,String MT,String SubSeries) {

		Object[] tests = new Object[1];

		tests[0] = new NA26780Test(store,MT,SubSeries);

		return tests;
	}

}
