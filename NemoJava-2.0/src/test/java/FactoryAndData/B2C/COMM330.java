package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM330Test;
import TestScript.B2C.NA22330Test;
import TestScript.B2C.NA22331Test;
import TestScript.B2C.NA23007Test;
import TestScript.B2C.NA26085Test;

public class COMM330 {
	
	@DataProvider(name = "330")
	public static Object[][] Data330(){
	return Common.getFactoryData(new Object[][]{
			{"AU","00HM888"},
			{"US","00HM888"}
	},PropsUtils.getTargetStore("COMM-330"));	
	}
	
	@Factory(dataProvider = "330")
	public Object[] createTest(String store,String product) {

		Object[] tests = new Object[1];

		tests[0] = new COMM330Test(store,product);

		return tests;
	}

}
