package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21842Test;

public class NA21842 {

	@DataProvider(name = "21842")
	public static Object[][] Data21842() {
		return Common.getFactoryData(new Object[][] { 
					{ "AU", ".//*[@id='state']/option[contains(text(),'Victoria')]", "61A9MAR1AU" },
					{ "US", ".//*[@id='state']/option[contains(text(),'Illinois')]", "60FELAR1US" },
					{ "HK", ".//*[@id='state']/option[contains(text(),'New Territories')]", "61ABMAR1WW" },
					{ "JP", ".//*[@id='state']/option[contains(text(),'東京都')]", "60DFAAR1JP" }
				}, PropsUtils.getTargetStore("NA-21842"));
	}

	@Factory(dataProvider = "21842")
	public Object[] createTest(String store,String StateXpath,String productNumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA21842Test(store, StateXpath,productNumber);

		return tests;
	}

}