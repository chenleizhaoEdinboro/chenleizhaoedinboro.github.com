package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16452Test;
import TestScript.B2C.PartNoRetrieval;

public class PartNoRetrievalData {

	@DataProvider(name = "PartNoRetrievalData")
	public static Object[][] Data16452() {
		return Common.getFactoryData(new Object[][] { 
           /*{ "AU","Laptops" }, 
			{ "AU","Tablet" },  
			{ "AU","Accessory" },
			{ "NZ","Laptops" }, 
		    { "NZ","Tablet" }, 
			{ "NZ","Accessory" }, 
		    { "CA","Laptops" }, 
	     	{ "CA","Tablet" }, 
			{ "CA","Accessory" }, 
			{ "US","Laptops" }, 
			{ "US","Tablet" }, 
			{ "US","Accessory" }, 
			
				{ "USEPP","Laptops" },
			{ "USEPP","Tablet" }, 
			{ "USEPP","Accessory" },
				{ "CA_AFFINITY","Laptops" },
			{ "CA_AFFINITY","Tablet" }, 
			{ "CA_AFFINITY","Accessory" },
				{ "US_BPCTO" },
            { "US_OUTLET","Laptops"  },
            { "US_OUTLET","Tablet"  },
            { "US_OUTLET","Accessory" },*/
            /*{ "HK" },
			{ "HKZF" },
			{ "SG" },
			{ "JP" },*/
//			{ "GB" },
//			{ "IE" }
		},PropsUtils.getTargetStore("PartNoRetrievalData"));
	}

	@Factory(dataProvider = "PartNoRetrievalData")
	public Object[] createTest(String store, String productCategory) {

		Object[] tests = new Object[1];

		tests[0] = new PartNoRetrieval(store,productCategory);

		return tests;
	}

}
