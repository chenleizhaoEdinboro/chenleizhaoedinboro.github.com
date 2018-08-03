package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;

import TestScript.B2C.CartRedesignTest;
import TestScript.B2C.NA16390Test;
import TestScript.B2C.NA21981Test;

public class CartRedesign {

	@DataProvider(name = "CartRedesign")
	public static Object[][] Data16390() {
		return Common.getFactoryData(new Object[][] { //{ "AU", "MTM" },
				{ "AU", "CTO" },
				{ "US", "MTM" },
				{ "US", "CTO" },
				{ "CA", "MTM" },
				{ "CA", "CTO" },
				{ "NZ", "MTM" },
				{ "NZ", "CTO" },
				{ "SG", "MTM" },
				{ "SG", "CTO" },
				{ "HK", "MTM" },
				{ "HK", "CTO" },
				 }, PropsUtils.getTargetStore("CartRedesign"));
	}

	@Factory(dataProvider = "CartRedesign")
	public Object[] createTest(String store,String ProductNo) {

		Object[] tests = new Object[1];

		tests[0] = new CartRedesignTest(store, ProductNo);

		return tests;
	}

}