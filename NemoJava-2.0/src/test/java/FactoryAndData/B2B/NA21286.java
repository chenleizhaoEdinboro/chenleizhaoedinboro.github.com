package FactoryAndData.B2B;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA21286Test;
public class NA21286 {
	@DataProvider(name = "21286")
	public static Object[][] Data21286() {
		return Common.getFactoryData(new Object[][] {			
				{ "AU","AUD","$" }
		},PropsUtils.getTargetStore("NA-21286"));
	}
	@Factory(dataProvider = "21286")
	public Object[] createTest(String store,String countryCurrency,String currencySign) {
		Object[] tests = new Object[1];
		tests[0] = new NA21286Test(store,countryCurrency,currencySign);
		return tests;
	}
}