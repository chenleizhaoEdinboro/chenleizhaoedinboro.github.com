package TestData;

import java.util.ArrayList;

public abstract class B2CData {
	public String getloginPageUrl() {
		return getHomePageUrl() + "/login";
	}
	public abstract String getHomePageUrl();
	public abstract String getTeleSalesUrl();
	public abstract String getPartSalesUrl();
	public abstract String getGateKeeperPassCode();
	public abstract String getGateKeeperSerialNmuber();
	public abstract String getGateKeeperSerialLastName();
	public abstract String getGateKeeperRegistrationLenovoID();
	public abstract String getGateKeeperRegistrationPassword();
	public abstract String getDefaultMTMPN();
	public abstract String getDefaultMTMPN2();
	public abstract String getDefaultAddressLine1();
	public abstract String getDefaultAddressCity();
	public abstract String getDefaultAddressPostCode();
	public abstract String getDefaultAddressState();
	public abstract String getDefaultAddressPhone();
	public abstract String getOverwritePN();
	public abstract String getUnit();
	public abstract String getWebsites();

	public abstract String getBatchAssignPN();

	public abstract String getLoginID();
	public abstract String getLoginPassword();
	public abstract String getRepID();
	public abstract String getDefaultCTOPN();
	public abstract String getDefaultAccessoryPN();
	
	public abstract String getStore();
	public abstract String getTelesalesAccount();
	public abstract String getTelesalesPassword();
	public abstract String getConsumerTaxNumber();
	public abstract String getResetPasswordId();
	public abstract ArrayList<String> getContentMigrationURLs();
	public abstract String getCssValueAlertPositive();
	public abstract int getInvalidQuantity();
	public abstract String getNewPartNumber();
	public abstract String getUserName();
	public abstract String getPassword();
	public abstract String getCartName();

	
}
