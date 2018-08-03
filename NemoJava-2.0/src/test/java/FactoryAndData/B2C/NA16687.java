package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16687Test;

public class NA16687 {

	@DataProvider(name = "16687")
	public static Object[][] Data16687() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","Australia" }, 
			{ "US","United States" },
			{ "BR","BRAZIL" },
			{ "JP","Japan" },
			{ "GB","United Kingdom" },
			{ "IE","Ireland" }
		},PropsUtils.getTargetStore("NA-16687"));
	}

	@Factory(dataProvider = "16687")
	public Object[] createTest(String store,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA16687Test(store,country);

		return tests;
	}

}
