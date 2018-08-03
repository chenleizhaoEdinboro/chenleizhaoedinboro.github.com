package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA15492Test;

public class NA15492 {

	@DataProvider(name = "NA15492")
	public static Object[][] Data15492() {
		return Common.getFactoryData(new Object[][] {
				{ "NZ", "/tablets/c/TABLETS?menu-id=Tablets",
						"/desktops-and-all-in-ones/professional/c/for-professional" },
				{ "CA", "/laptops/ideapad/c/IdeaPad", "/desktops-and-all-in-ones/thinkcentre/c/ThinkCentre" },
				{ "JP", "/notebooks/ideapad/c/ideapad", "/desktops/thinkcentre/c/thinkcentre" },
				{ "GB", "/laptops/ideapad/c/IdeaPad",
						"/laptops/thinkpad/edge-series/c/thinkpade" }
						},PropsUtils.getTargetStore("NA-15492"));
	}

	@Factory(dataProvider = "NA15492")
	public Object[] createTest(String store, String mtmUrl, String ctoUrl) {

		Object[] tests = new Object[1];

		tests[0] = new NA15492Test(store, mtmUrl, ctoUrl);

		return tests;
	}

}
