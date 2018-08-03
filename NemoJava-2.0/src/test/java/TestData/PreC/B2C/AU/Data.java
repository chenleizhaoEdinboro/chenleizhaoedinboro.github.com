package TestData.PreC.B2C.AU;

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
		return TestData.PreC.EnvData.HttpsDomain + "/au/en/auweb";
	}
	@Override
	public String getPartSalesUrl() {
		return "https://presupport.lenovo.com/au/en/partslookup";
	}
	
	@Override
	public String getGateKeeperPassCode() {
		return null;
	}

	@Override
	public String getGateKeeperSerialNmuber() {
		return "STAR";
	}

	@Override
	public String getGateKeeperSerialLastName() {
		return "SuperStar";
	}

	@Override
	public String getGateKeeperRegistrationLenovoID() {
		return "lisong2@lenovo.com";
	}

	@Override
	public String getGateKeeperRegistrationPassword() {
		return "1q2w3e4r";
	}

	@Override
	public String getDefaultMTMPN() {
		MTM=null;
		if (MTM != null) {
			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "80NV008GAU";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB("AU", "MTM","PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "80RV003NAU";
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
				return "80NV008GAU";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB2("AU", "MTM","PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "80NV008GAU";
			}
		}
	}
	@Override
	public String getDefaultAddressLine1() {
		return "62 Streeton Dr";
	}

	@Override
	public String getDefaultAddressCity() {
		return "RIVETT";
	}

	@Override
	public String getDefaultAddressPostCode() {
		return "2611";
	}

	@Override
	public String getDefaultAddressState() {
		return "Australian Capital Territory";
	}

	@Override
	public String getOverwritePN() {
		return "60DFAAR1AU";
	}

	@Override
	public String getUnit() {
		return "auPublic_unit";
	}

	@Override
	public String getDefaultAddressPhone() {
		return "0432535196";
	}

	@Override
	public String getBatchAssignPN() {
		return null;
	}

	public String getLoginID() {
		return "testau@sharklasers.com";
	}

	@Override
	public String getRepID() {
		return "2900718028";
	}

	@Override
	public String getDefaultCTOPN() {
		if (CTO != null) {
			if (CTO.get(0) != null) {
				return CTO.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20ENCTO1WWENAU0";
			}
		} else {
		if (daoService == null) {
			daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
		}
		CTO = new ArrayList<String>();
		CTO = daoService.getProductNB("AU", "CTO","PreC");
		if (CTO.get(0) != null) {
			return CTO.get(0);
		} else {

			return "20ENCTO1WWENAU0";
		}}
	}

	@Override
	public String getLoginPassword() {
		return "1q2w3e4r";
	}

	@Override
	public String getStore() {
		return "auweb";
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
		return null;
	}

	//@Override
	public String getTeleSalesUrl() {
		return TestData.PreC.EnvData.TeleSalesDomain + "/au/en/auweb";
	}
	@Override
	public String getWebsites() {
		// TODO Auto-generated method stub
		return "auweb";
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
			ACC = daoService.getProductNB("AU", "Accessory","PreC");

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
		return "aupassword_b2c@sharklasers.com";
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
