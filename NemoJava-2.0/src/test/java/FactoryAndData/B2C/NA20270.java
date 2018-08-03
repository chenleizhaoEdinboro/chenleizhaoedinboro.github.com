package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20270Test;

public class NA20270 {

	@DataProvider(name = "20270")
	public static Object[][] Data20270() {
		return  Common.getFactoryData(new Object[][] { 
			{ "AU" ,"88IP3000706" ,"India" ,"ap-inContentCatalog - Online"}
		},PropsUtils.getTargetStore("NA-20270"));
	}

	@Factory(dataProvider = "20270")
	public Object[] createTest(String store,String productID,String UnitID,String catalogVersion) {

		Object[] tests = new Object[1];

		tests[0] = new NA20270Test(store,productID,UnitID,catalogVersion);

		return tests;
	}
}
