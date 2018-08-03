package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT112Test;

public class ACCT112 {

	@DataProvider(name = "ACCT112")
	public static Object[][] DataACCT34() {
		return Common.getFactoryData(new Object[][] { 
				{ "US_SMB" ,"brassgeko@consultant.com","r3d@C0rd"}
		},PropsUtils.getTargetStore("ACCT112"));
	}

	@Factory(dataProvider = "ACCT112")
	public Object[] createTest(String store, String customer,String password_cus) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT112Test(store,customer,password_cus);

		return tests;
	}

}
