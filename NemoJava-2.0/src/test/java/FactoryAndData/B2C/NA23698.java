package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23698Test;

public class NA23698 {
	
	@DataProvider(name = "23698")
	public static Object[][] Data23698(){
	return Common.getFactoryData(new Object[][]{
			{"AU"},
			{"NZ"},
			{"CA"},
			{"US"},
			/*{"USEPP"},
			{"CA_AFFINITY" },
			{ "HK" },
			{ "HKZF" },
			{ "SG" },
			{ "JP" },*/
		
		
	},PropsUtils.getTargetStore("NA-23698"));	
	}
	
	@Factory(dataProvider = "23698")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA23698Test(store);

		return tests;
	}

}
