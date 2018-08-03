package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18402Test;

public class NA18402 {

	@DataProvider(name = "NA18402")
	public static Object[][] Ddata18402() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","faq","autotest-18402","Autotest-18402",".accordion_container {width: 90%;z-index:1000; }",".accordion_container"}
			
		},
				PropsUtils.getTargetStore("NA-18402"));
	}

	@Factory(dataProvider = "NA18402")
	public Object[] createTest(String store,String faqNumber,String id,String name,String body,String checkbody) {

		Object[] tests = new Object[1];

		tests[0] = new NA18402Test(store,faqNumber,id,name,body,checkbody);

		return tests;
	}

}