package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19104Test;

public class NA19104 {

	@DataProvider(name = "19104")
	public static Object[][] Data16968() {
		return  Common.getFactoryData(new Object[][] { 
			{ "US" },
			{ "CA" },
			{ "GB" },
			{ "BR" },	
			{ "JP" }
			
		},PropsUtils.getTargetStore("NA-19104"));
	}

	@Factory(dataProvider = "19104")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA19104Test(store);

		return tests;
	}

}
