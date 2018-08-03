package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT350Test;

public class ACCT350 {

	@DataProvider(name = "ACCT350")
	public static Object[][] DataACCT350() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","Your password has been changed" }, 
			{ "NZ","Your password has been changed" },
			{ "US","Your password has been changed" },
		    { "USEPP","Your password has been changed" },
			{ "CA","Your password has been changed" },
			{ "CA_AFFINITY","Your password has been changed" },
			{ "US_OUTLET","Your password has been changed" },
			{ "HK","Your password has been changed" },
			{ "HKZF","Your password has been changed" },
			{ "SG","Your password has been changed" },
			{ "JP","Your password has been changed" },
			{ "GB","Your password has been changed" },
			{ "IE","Your password has been changed" },
			{ "CO","Your password has been changed" }
		},PropsUtils.getTargetStore("ACCT350"));
	}

	@Factory(dataProvider = "ACCT350")
	public Object[] createTest(String store,String successMsgBox) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT350Test(store,successMsgBox);

		return tests;
	}

}
