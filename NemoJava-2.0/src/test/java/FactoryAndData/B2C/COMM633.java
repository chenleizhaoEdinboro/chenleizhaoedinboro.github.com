package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.COMM633Test;

public class COMM633 {
	@DataProvider(name = "633")
	public static Object[][] Data633() {
		return Common.getFactoryData(new Object[][] { 
			//the third bundle first product should be CTO
				{"US","test-cb-3","test-cb-2","test-cb-1"}
			},PropsUtils.getTargetStore("COMM633"));
		
	}

	@Factory(dataProvider = "633")
	public Object[] createTest(String store,String bundle1,String bundle2,String bundle3) {

		Object[] tests = new Object[1];

		tests[0] = new COMM633Test(store,bundle1,bundle2,bundle3);

		return tests;
	}
  
}
