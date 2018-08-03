package TestData.PreC.HAC;

import TestData.HACData;

public class Data extends HACData {
	@Override
	public String getHomePageUrl() {
		return TestData.PreC.EnvData.HttpDomain.replace("M0C0v0n3L!@", "M0C0v0n3L!@admin-") + "/hac";
	}

	@Override
	public String getLoginId() {
		return "autoHMCMationTest";
	}

	@Override
	public String getPassword() {
		return "Lenovo1234";
	}

}
