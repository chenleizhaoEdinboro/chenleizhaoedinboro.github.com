package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE541Test;

public class BROWSE541 {
	@DataProvider(name = "541")
	public static Object[][] Data28205() {
		return Common.getFactoryData(new Object[][] { 
				{"AU"},
				{"CA"},
				{"NZ"},
				{"US"},
				{"JP"},
				{"USEPP"}
			},PropsUtils.getTargetStore("BROWSE-541"));
		
	}

	@Factory(dataProvider = "541")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE541Test(store);

		return tests;
	}

}
