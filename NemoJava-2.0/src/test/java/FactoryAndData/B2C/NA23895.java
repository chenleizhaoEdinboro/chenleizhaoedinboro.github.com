package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23895Test;

public class NA23895 {
	@DataProvider(name = "23895")
	public static Object[][] Data23895() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU","Lenovo720 test mkt_name","Test test test Beautifully finished in polished aluminum, the Ideapad 720 combines advanced processing with premium design. A big step up from the Ideapad 520, this powerful laptop offers faster, larger storage options and high-performance dedicated graphics for the gamer in you. "
					,"auPublic_unit"}
		},PropsUtils.getTargetStore("NA-23895"));
	}

	@Factory(dataProvider = "23895")
	public Object[] createTest(String store,String name,String description,String unit) {
		Object[] tests = new Object[1];
		tests[0] = new NA23895Test(store,name,description,unit);
		return tests;
	}
}
