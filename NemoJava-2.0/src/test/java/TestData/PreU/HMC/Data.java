package TestData.PreU.HMC;

import TestData.HMCData;

public class Data extends HMCData {
	@Override
	public String getHomePageUrl() {
		return TestData.PreC.EnvData.HttpDomain.replace("M0C0v0n3L!@", "M0C0v0n3L!@admin-") + "/hmc/hybris";
	}

	@Override
	public String getLoginId() {
		return "autoHMCMationTest";
	}

	@Override
	public String getPassword() {
		return "Lenovo1234";
	}

	@Override
	public String getTeleLoginId() {
		return "youngmeng_n";
	}

	@Override
	public String getTelePassword() {
		return "Lenovo-1";
	}
	
	
		
}
