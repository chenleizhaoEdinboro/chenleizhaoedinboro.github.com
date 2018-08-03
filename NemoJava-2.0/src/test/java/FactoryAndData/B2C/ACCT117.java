package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT117Test;

public class ACCT117 {

	@DataProvider(name = "ACCT117")
	public static Object[][] DataACCT117() {
		return Common.getFactoryData(new Object[][] { 
				{ "US_SMB" ,"youngmeng_n","Lenovo-1","RR00000001"}
		},PropsUtils.getTargetStore("ACCT117"));
	}

	@Factory(dataProvider = "ACCT117")
	public Object[] createTest(String store , String customer,String password_cus,String partNumber) {

		Object[] tests = new Object[1];

		tests[0] = new ACCT117Test(store,customer,password_cus,partNumber);

		return tests;
	}

}
