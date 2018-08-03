package TestData;
public class TestData {
	public HMCData HMC = null;
	public HACData HAC = null;
	public CTOData CTO = null;
	public B2BData B2B = null;
	public B2CData B2C = null;
	public String Environment = null;
	public String Store = null;
	public String Type = null;
	public EnvData envData = null;
	public TestData(String environment, String type, String store) {
		try {
			HAC = (HACData) Class.forName("TestData." + environment + ".HAC.Data").newInstance();
			HMC = (HMCData) Class.forName("TestData." + environment + ".HMC.Data").newInstance();
			CTO = (CTOData) Class.forName("TestData." + environment + ".CTO.Data").newInstance();
			if (type.toLowerCase().equals("b2b"))
				B2B = (B2BData) Class.forName("TestData." + environment + ".B2B." + store + ".Data").newInstance();
			else
				B2C = (B2CData) Class.forName("TestData." + environment + ".B2C." + store + ".Data").newInstance();
			Environment = environment;
			Store = store;
			Type = type;
			envData = (EnvData) Class.forName("TestData." + environment + ".EnvData").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}