package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.BROWSE23Test;

public class BROWSE23 {
	@DataProvider(name = "23")
	public static Object[][] Data23() {
		return Common.getFactoryData(new Object[][] { 
				{"US","20M70011AU","20KFA00DAU","20M7CTO1WWENAU1","20KFCTO1WWENAU1","22TP2TBL38020M7","22TP2TX2800","65C5KAC1AU"},
				{"HK","20M70011AU","20KFA00DAU","20M7CTO1WWENAU1","20KFCTO1WWENAU1","22TP2TBL38020M7","22TP2TX2800","65C5KAC1AU"},
				{"JP","20M70011AU","20KFA00DAU","20M7CTO1WWENAU1","20KFCTO1WWENAU1","22TP2TBL38020M7","22TP2TX2800","65C5KAC1AU"}
			},PropsUtils.getTargetStore("BROWSE-23"));
		
	}

	@Factory(dataProvider = "23")
	public Object[] createTest(String store,String mtm1,String mtm2,String cto1,String cto2,String machineType,String subseries,String accessory) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE23Test( store, mtm1, mtm2, cto1, cto2, machineType, subseries, accessory);

		return tests;
	}
  
}
