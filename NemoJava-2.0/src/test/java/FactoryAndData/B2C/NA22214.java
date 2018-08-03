package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22214Test;

public class NA22214 {

	@DataProvider(name = "22214")
	public static Object[][] Data14919() {
		return Common.getFactoryData(new Object[][] { 
			{ "US", "88GMY500808","22TP2WPWP71","88YG7000827","/landingpage/promotions/weekly-sale/thinkpad-laptops" }
		},PropsUtils.getTargetStore("NA-22214"));
	}

	@Factory(dataProvider = "22214")
	public Object[] createTest(String store, String mixSubseriesNum, String ctoSubseriesNum, String mtmSubseriesNum, String dealsUrl) {

		Object[] tests = new Object[1];

		tests[0] = new NA22214Test(store, mixSubseriesNum, ctoSubseriesNum, mtmSubseriesNum, dealsUrl);

		return tests;
	}

}
