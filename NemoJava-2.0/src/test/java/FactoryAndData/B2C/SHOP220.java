package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.SHOP220Test;

public class SHOP220 {

	@DataProvider(name = "SHOP220")
	public static Object[][] Data220() {
		return Common.getFactoryData(new Object[][] { 
			{ "US_OUTLET","United States","US Outlet" }},
			PropsUtils.getTargetStore("SHOP-220"));
	}

	@Factory(dataProvider = "SHOP220")
	public Object[] createTest(String store,String country,String unit) {

		Object[] tests = new Object[1];

		tests[0] = new SHOP220Test(store,country,unit);

		return tests;
	}

}
