package TestData.QAS.B2C.MX_OUTLET;

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
		return TestData.QAS.EnvData.HttpsDomain + "/mx/es/mxoutlet";
	}

	@Override
	public String getGateKeeperPassCode() {
		return "nemo1234";
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
		if (MTM != null) {
			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20GBA00900";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB("MX", "MTM" ,"PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20GBA00900";
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
				return "20GBA00900";
			}
		} else {

			if (daoService == null) {
				daoService = AutoReportImpl.creatInstance(PropsUtils.getParameter("PreC"));
			}
			MTM = new ArrayList<String>();
			MTM = daoService.getProductNB2("MX", "MTM","PreC");

			if (MTM.get(0) != null) {
				return MTM.get(0);
			} else {
				// if DB is empty return a hardcoded value
				return "20GBA00900";
			}
		}
	}
	@Override
	public String getDefaultAddressLine1() {
		// TODO Auto-generated method stub
		return "223 AutoAddress1";
	}

	@Override
	public String getDefaultAddressCity() {
		// TODO Auto-generated method stub
		return "AutoCity";
	}

	@Override
	public String getDefaultAddressPostCode() {
		// TODO Auto-generated method stub
		return "86018";
	}

	@Override
	public String getDefaultAddressState() {
		// TODO Auto-generated method stub
		return "Chiapas";
	}

	@Override
	public String getDefaultAddressPhone() {
		// TODO Auto-generated method stub
		return "18510702984";
	}

	@Override
	public String getOverwritePN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUnit() {
		return "mxweb";
	}

	@Override
	public String getBatchAssignPN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLoginID() {
		// TODO Auto-generated method stub
		return "lisong2@lenovo.com";
	}

	@Override
	public String getRepID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultCTOPN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLoginPassword() {
		// TODO Auto-generated method stub
		return "1q2w3e4r";
	}

	@Override
	public String getStore() {
		return "mxweb";
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
		return "84511773521";
	}

	@Override
	public String getTeleSalesUrl() {
		// TODO Auto-generated method stub
		return TestData.QAS.EnvData.TeleSalesDomain + "/mx/es/mxoutlet";
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
			ACC = daoService.getProductNB("MX_OUTLET", "Accessory","PreC");

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
		return "mxoutletpassword_b2c@sharklasers.com";
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
