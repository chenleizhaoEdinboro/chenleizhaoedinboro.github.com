package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA19418Test;

public class NA19418 {

	@DataProvider(name = "19418")
	public static Object[][] Data19418() {
		return Common.getFactoryData(new Object[][] { 
			{ "US" ,"8800025235186","BuyFromResellerTab","BUY FROM RESELLER","test five-test-redis clean+12","88GMY500808"},
			{ "CA" ,"8800025235186","BuyFromResellerTab","BUY FROM RESELLER","test five-test-redis clean+12","88GMY500808"},
			{ "NZ" ,"8800025235186","BuyFromResellerTab","BUY FROM RESELLER","test five-test-redis clean+12","88GMY500808"}
			},PropsUtils.getTargetStore("NA-19418"));
	}

	@Factory(dataProvider = "19418")
	public Object[] createTest(String store,String pknumber,String name,String description,String content,String productnumber) {

		Object[] tests = new Object[1];

		tests[0] = new NA19418Test(store,pknumber,name,description,content,productnumber);

		return tests;
	}

}
