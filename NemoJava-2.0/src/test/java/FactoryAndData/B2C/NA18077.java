package FactoryAndData.B2C;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18077Test;
public class NA18077 {
	@DataProvider(name = "18077")
	public static Object[][] Data18077() {
		return Common.getFactoryData(new Object[][] {				
			{ "AU" ,"80U2002GAU","Australia","auweb"},				
			{ "US" ,"80WY0000US","United States","usweb_unit"},
			{ "CA_AFFINITY" ,"80WY0000US","Canada","oma"},							
			{ "GB" ,"81AC000CUK","United Kingdom","gbweb"},
			{ "JP" ,"80SX004AJP","Japan","jpweb"}
		},PropsUtils.getTargetStore("NA-18077"));
	}
	@Factory(dataProvider = "18077")
	public Object[] createTest(String store,String accessory,String countrySearch,String baseStoreName) {
		Object[] tests = new Object[1];
		tests[0] = new NA18077Test(store,accessory,countrySearch,baseStoreName);
		return tests;
	}
}