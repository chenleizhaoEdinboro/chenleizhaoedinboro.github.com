package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA17710Test;

public class NA17710 {

	@DataProvider(name = "17710")
	public static Object[][] Data17710() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","","" }, 
			//{ "US","20HJCTO1WWENUS4","5PS0E97110" },
			{ "US","20JJCTO1WWENUSA","5PS0E97110" },
			{ "JP","",""}
		},PropsUtils.getTargetStore("NA-17710"));
	}

	@Factory(dataProvider = "17710")
	public Object[] createTest(String store,String productID,String accessoryID) {

		Object[] tests = new Object[1];

		tests[0] = new NA17710Test(store,productID,accessoryID);

		return tests;
	}

}
