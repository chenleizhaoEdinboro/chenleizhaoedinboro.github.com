package TestData;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import Dao.impl.AutoReportImpl;

public class PropsUtils {
	private static Dao.inter.AutoReportDao testCaseService;

	public static Properties loadProps() {
		Properties properties = new Properties();
		try {
			FileInputStream in = new FileInputStream("Conf.properties");
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static int getDefaultTimeout() {
		return Integer.parseInt(loadProps().getProperty("DefaultTimeout"));
	}

	public static String getTargetStore(String caseName) {
		if (System.getProperty("onlyRerunFail").toLowerCase().equals("true")) {
			if (testCaseService == null) {
				testCaseService = AutoReportImpl
						.creatInstance(PropsUtils.getParameter(System.getProperty("environment")));
			}
			return testCaseService.getRerunStores(System.getProperty("runId"), caseName, false);
		} else
			return System.getProperty("targetStore");
	}

	public static ArrayList<String[]> getTargetStorePayment(String category) {
		if (System.getProperty("onlyRerunFail").toLowerCase().equals("true")) {
			if (testCaseService == null) {
				testCaseService = AutoReportImpl
						.creatInstance(PropsUtils.getParameter(System.getProperty("environment")));
			}
			return testCaseService.getRerunStoresPayment(System.getProperty("runId"), category, false);
		} else {
			ArrayList<String[]> result = new ArrayList<String[]>();
			result.add(new String[] { "ALL" });
			return result;
		}

	}

	public static String getParameter(String parameter) {
		return loadProps().getProperty(parameter);
	}
}
