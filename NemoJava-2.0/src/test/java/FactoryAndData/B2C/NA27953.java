package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA27953Test;

public class NA27953 {

	@DataProvider(name = "NA27953")
	public static Object[][] Data27953() {
		return Common.getFactoryData(new Object[][] {
				{ "JP","80XHX002JP","src/test/uploadfiles/JP_Outlet_product_load_test.xls"},
				{ "US_OUTLET","80SF001EUS","src/test/uploadfiles/product_upload_example_for_usoutlet.xls"}
				},PropsUtils.getTargetStore("NA-27953"));
	}

	@Factory(dataProvider = "NA27953")
	public Object[] createTest(String store,String partNo,String filePath) {

		Object[] tests = new Object[1];

		tests[0] = new NA27953Test(store,partNo,filePath);

		return tests;
	}

}
