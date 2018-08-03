package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA16389Test;
import TestScript.B2B.NA18080Test;

public class NA18080 {

	@DataProvider(name = "18080")
	public static Object[][] Data16389() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","aule","Australia","1213654197" ,"AU Public unit"}, 
			{ "US","usle","United States","1213577815" ,"US web store unit"}
		},PropsUtils.getTargetStore("NA-18080"));
	}

	@Factory(dataProvider = "18080")
	public Object[] createTest(String store, String siteId,String b2bCountry,String b2bUnit,String unit) {

		Object[] tests = new Object[1];

		tests[0] = new NA18080Test(store,siteId,b2bCountry,b2bUnit,unit);

		return tests;
	}

}

