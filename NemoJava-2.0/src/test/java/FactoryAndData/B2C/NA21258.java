package FactoryAndData.B2C;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21258Test;
public class NA21258 {
	@DataProvider(name = "21258")
	public static Object[][] Data21258() {
		return Common.getFactoryData(new Object[][] {			
				{ "AU","AUD","$" }
		},PropsUtils.getTargetStore("NA-21258"));
	}
	@Factory(dataProvider = "21258")
	public Object[] createTest(String store,String countryCurrency,String currencySign) {
		Object[] tests = new Object[1];
		tests[0] = new NA21258Test(store,countryCurrency,currencySign);
		return tests;
	}
}