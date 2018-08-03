package TestData.QAS.B2C.BR_OUTLET;

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
		return TestData.QAS.EnvData.HttpsDomain + "/br/pt/broutlet";
	}

	@Override
	public String getGateKeeperPassCode() {
		return "a12341234";
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
		return TestData.QAS.EnvData.TeleSalesDomain + "/br/pt/broutlet";
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
			ACC = daoService.getProductNB("BR_OUTLET", "Accessory","PreC");

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
		return "broutletpassword_b2c@sharklasers.com";
	}

	@Override
	public ArrayList<String> getContentMigrationURLs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCssValueAlertPositive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInvalidQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNewPartNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCartName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
