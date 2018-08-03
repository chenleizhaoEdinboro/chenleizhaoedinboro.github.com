package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.SHOPE340Test;

public class SHOPE340 {

	@DataProvider(name = "SHOPE340")
	public static Object[][] Data340() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU", "Australia", "1213286247"}
			},
			PropsUtils.getTargetStore("SHOPE-340"));
	}

	@Factory(dataProvider = "SHOPE340")
	public Object[] createTest(String store,String country,String unit) {

		Object[] tests = new Object[1];

		tests[0] = new SHOPE340Test(store,country,unit);

		return tests;
	}

}

