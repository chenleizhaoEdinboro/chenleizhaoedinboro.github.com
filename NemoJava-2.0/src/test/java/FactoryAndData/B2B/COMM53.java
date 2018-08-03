package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.COMM52Test;
import TestScript.B2B.COMM53Test;

public class COMM53 {
	@DataProvider(name = "53")
	public static Object[][] Data53() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","2020 Hospitality Court","Morrisville","North Carolina","2319","AU-SA" }
		},PropsUtils.getTargetStore("COMM-53"));
	}

	@Factory(dataProvider = "53")
	public Object[] createTest(String store,String street,String city,String state,String pin,String regionCode) {

		Object[] tests = new Object[1];

		tests[0] = new COMM53Test(store, street, city, state, pin,regionCode);

		return tests;
	}
}
