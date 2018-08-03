package TestData.PreU.B2C.GB;

import java.util.ArrayList;
import java.util.List;

import Dao.impl.AutoReportImpl;
import Dao.inter.AutoReportDao;
import TestData.B2CData;
import TestData.PropsUtils;

public class Data extends B2CData {
	private List<String> CTO;
	private List<String> MTM;
	private List<String> ACC;
	public static AutoReportDao daoService = null;
	@Override
	public String getHomePageUrl() {
		return TestData.PreC.EnvData.HttpsDomain + "/gb/en/gbweb";
	}

	@Override
	public String getGateKeeperPassCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGateKeeperSerialNmuber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGateKeeperSerialLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStore() {
		return "gbweb";
	}

	@Override
	public String getGateKeeperRegistrationLenovoID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGateKeeperRegistrationPassword() {
		// TODO Auto-generated method stub
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
		return "80SM01AWUK";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB("GB", "MTM" ,"PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "80SM01AWUK";
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
				return "80SM01AWUK";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB2("GB", "MTM","PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "80SM01AWUK";
			}
		}
	}
	@Override
	public String getDefaultAddressLine1() {
		// TODO Auto-generated method stub
		return "39 Kwu Tung Road";
	}

	@Override
	public String getDefaultAddressCity() {
		// TODO Auto-generated method stub
		return "Sheung Shui";
	}

	@Override
	public String getDefaultAddressPostCode() {
		// TODO Auto-generated method stub
		return "999077";
	}

	@Override
	public String getDefaultAddressState() {
		// TODO Auto-generated method stub
		return "Ards";
	}

	@Override
	public String getDefaultAddressPhone() {
		// TODO Auto-generated method stub
		return "1234567890";
	}

	@Override
	public String getOverwritePN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUnit() {
		return "gbweb";
	}

	@Override
	public String getBatchAssignPN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLoginID() {
		return "testau@sharklasers.com";
	}

	@Override
	public String getRepID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultCTOPN() {
		if (CTO != null) {
			if (CTO.get(0) != null) {
				return CTO.get(0);
			} else {
				// if DB is empty return a hardcoded value
		return "20FLCTO1WWENGB0";
			}
		} else {
		if (daoService == null) {
			daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
		}
		CTO = new ArrayList<String>();
		CTO = daoService.getProductNB("GB", "CTO" ,"PreC");
		if (CTO.get(0) != null) {
			return CTO.get(0);
		} else {
		return "20FLCTO1WWENGB0";
		}}
		
	}

	@Override
	public String getLoginPassword() {
		return "1q2w3e4r";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTeleSalesUrl() {
		// TODO Auto-generated method stub
		return TestData.PreC.EnvData.TeleSalesDomain + "/gb/en/gbweb";
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
			ACC = daoService.getProductNB("GB", "Accessory","PreC");

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
		return "gbpassword_b2c@sharklasers.com";
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
