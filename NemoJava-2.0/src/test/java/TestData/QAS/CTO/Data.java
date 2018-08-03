package TestData.QAS.CTO;

import TestData.CTOData;

public class Data extends CTOData{
	@Override
	public String getHomePageUrl() {
		return TestData.PreC.EnvData.HttpDomain + "/configurator_hana/#";
	}

	@Override
	public String getLoginId() {
		return "houqq1@lenovo.com";
	}

	@Override
	public String getPassword() {
		return "1q2w3e4r";
	}

	
}
