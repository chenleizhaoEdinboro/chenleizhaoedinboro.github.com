package TestData.PreU.B2C.BR;

import java.util.ArrayList;
import java.util.List;

import Dao.impl.AutoReportImpl;
import Dao.inter.AutoReportDao;
import TestData.B2CData;
import TestData.PropsUtils;

public class Data extends B2CData {
	private List<String> MTM;
	private List<String> ACC;
	public static AutoReportDao daoService = null;
	@Override
	public String getHomePageUrl() {
		return TestData.PreC.EnvData.HttpsDomain + "/br/pt/brweb";
	}

	@Override
	public String getGateKeeperPassCode() {
		return "nemo1234";
	}

	@Override
	public String getGateKeeperSerialNmuber() {
		return null;
	}

	@Override
	public String getGateKeeperSerialLastName() {
		return null;
	}

	@Override
	public String getGateKeeperRegistrationLenovoID() {
		return null;
	}

	@Override
	public String getGateKeeperRegistrationPassword() {
		return null;
	}
	@Override
	public String getDefaultMTMPN() {
		MTM=null;
		if (MTM != null) {
			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20HE005EBR";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB("BR", "MTM","PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20HE005EBR";
			}
		}
		
	}
	
	@Override
	public String getDefaultMTMPN2() {
		MTM=null;
		if (MTM != null) {
			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20HE005EBR";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB2("BR", "MTM","PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20HE005EBR";
			}
		}
	}
	@Override
	public String getDefaultAddressLine1() {
		return "Jardim São Paulo";
	}

	@Override
	public String getDefaultAddressCity() {
		return "São João";
	}

	@Override
	public String getDefaultAddressPostCode() {
		return "83331-000";
	}

	@Override
	public String getDefaultAddressState() {
		return "Alagoas";
	}

	@Override
	public String getDefaultAddressPhone() {
		return "18510702984";
	}

	@Override
	public String getOverwritePN() {
		return null;
	}

	@Override
	public String getUnit() {
		return "brweb";
	}

	@Override
	public String getBatchAssignPN() {
		return null;
	}

	@Override
	public String getLoginID() {
		return "lisong2@lenovo.com";
	}

	@Override
	public String getRepID() {
		return null;
	}

	@Override
	public String getDefaultCTOPN() {
		return null;
	}

	@Override
	public String getLoginPassword() {
		return "1q2w3e4r";
	}

	@Override
	public String getStore() {
		return "brweb";
	}

	@Override
	public String getTelesalesAccount() {
		return "youngmeng_n";
	}

	@Override
	public String getTelesalesPassword() {
		return "Lenovo-1";
	}

	@Override
	public String getConsumerTaxNumber() {
		return "84511773521";
	}

	@Override
	public String getTeleSalesUrl() {
		return TestData.PreC.EnvData.TeleSalesDomain + "/br/pt/brweb";
	}

	@Override
	public String getPartSalesUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWebsites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultAccessoryPN() {
		if (ACC != null) {
			if (ACC.get(0) != null) {
				return ACC.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "80NV008GAU";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			ACC = new ArrayList<String>();
			ACC = daoService.getProductNB("BR", "Accessory","PreC");

			if (ACC.get(0) != null) {
				return ACC.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "80RV003NAU";
			}
		}
	}
	
	@Override
	public String getResetPasswordId(){
		return "brpassword_b2c@sharklasers.com";
	}
	

	@Override
	public ArrayList<String> getContentMigrationURLs() {
		ArrayList<String> urls=new ArrayList<>();
		urls.add("http://web-content.dit1.online.lenovo.com/us/en/events/ifa");
		urls.add("http://www3.lenovo.com/us/en/events/ifa");
		
		urls.add("http://web-content.dit1.online.lenovo.com/us/en/events/ces");
		urls.add("http://www3.lenovo.com/us/en/events/ces");
		
		urls.add("http://web-content.dit1.online.lenovo.com/us/en/events/mwc");
		urls.add("http://www3.lenovo.com/us/en/events/mwc");
		
		return urls;
	}
	
	@Override
	public String getCssValueAlertPositive()
	{
		return "#4a8534";
	}
	@Override
	public int getInvalidQuantity()
	{
		return 50;
	}
	@Override
	public String getNewPartNumber()
	{
		return "20L9001TUS";
	}
	@Override
	public String getUserName()
	{
		return "msharma2@lenovo.com";
	}
	@Override
	public String getPassword()
	{
		return "lenovo123";
	}
	@Override
	public String getCartName()
	{
		return "CartName1";
	}
}
