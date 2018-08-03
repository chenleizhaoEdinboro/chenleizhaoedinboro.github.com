package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21866Test;

public class NA21866 {

	@DataProvider(name = "21866")
	public static Object[][] Data21866() {
		return Common.getFactoryData( new Object[][] { 
			{ "AU" , "3"},
			{ "US" , "3"},
			{ "USEPP" , "3"},
			{ "US_BPCTO" ,  "3"},
			{ "CA_AFFINITY" , "3"},
			{ "JP", "3"},
			{ "GB"  , "3"},
			{ "HK" , "3"}
		},PropsUtils.getTargetStore("NA-21866"));
	}

	@Factory(dataProvider = "21866")
	public Object[] createTest(String store,String processNode) {

		Object[] tests = new Object[1];

		tests[0] = new NA21866Test(store,processNode);

		return tests;
	}

}
