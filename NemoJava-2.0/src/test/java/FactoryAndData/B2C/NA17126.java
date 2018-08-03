package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17126Test;


public class NA17126 {

	@DataProvider(name = "NA17126")
	public static Object[][] Data17126() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","auweb" },
			{ "CA_AFFINITY","oma" },
			{ "JP","jpweb" }
		}, PropsUtils.getTargetStore("NA17126"));
	}

	@Factory(dataProvider = "NA17126")
	public Object[] createTest(String store,String webID) {

		Object[] tests = new Object[1];

		tests[0] = new NA17126Test(store, webID);

		return tests;
	}

}