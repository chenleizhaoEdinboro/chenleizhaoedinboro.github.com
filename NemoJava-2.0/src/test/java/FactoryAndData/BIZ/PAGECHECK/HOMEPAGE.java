package FactoryAndData.BIZ.PAGECHECK;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import TestScript.BIZ.PAGECHECK.HomePage;

public class HOMEPAGE {
	
	@DataProvider(name = "BizB2C")
	public static Object[][] BizB2B() {
		return new Object[][] {
				
				{
					"AU",
					"Australia",
					"http://pre-c-hybris.lenovo.com/au/en",
					"lisong2@lenovo.com",
					"1q2w3e4r",
					"94",
				},
				
		};

	}
	
	// DataFactory to connect ProdB2CProduct test data and test class
	@Factory(dataProvider = "BizB2B")
	public Object[] createTest(String Store,String Country,String Url,String Account,String Password,String machineInfo) {

		Object[] tests = new Object[1];

		tests[0] = new HomePage(Store, Country, Url, Account,Password,machineInfo);

		return tests;
	}
	
	
	
	
	
	
	

}
