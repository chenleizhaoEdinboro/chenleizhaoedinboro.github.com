package Logger;



import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
public class Dailylog {
	public static Map<String, String> maplog = new HashMap<String, String>();
	
	private static Logger logger = Logger.getLogger("Dailylog");
	private static boolean flag = false;
	
	private static synchronized void getPropertyFile(){
		try {
			PropertyConfigurator.configure("log4j.properties");
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getFlag(){
		if (flag == false) {
			getPropertyFile();
		}
	}
	/*
	 * testName=CaseName
	 */
	public static void logInfoDB(int Step,Object message,String store,String testName){
		getFlag();
		if(maplog.get(store+testName)==null){
			maplog.put(store+testName, "Step "+String.valueOf(Step)+": "+message+"\n");
		}else{
			maplog.put(store+testName, maplog.get(store+testName)+"Step "+String.valueOf(Step)+": "+message+"\n");			
		}
		logger.info("Step "+String.valueOf(Step)+": "+message+"\n");
	}
	public static void logInfo(Object message){
		getFlag();
		logger.info(message);
	}
	
	public static void logError(Object message, String string){
		getFlag();
		logger.error(message);
	}
	public static void logError(Object message){
		getFlag();
		logger.error(message);
	}
	public static void logError(Object message, Throwable t){
		getFlag();
		logger.error(message, t);
	}
	public static void logWarn(Object message){
		getFlag();
		logger.warn(message);
	}

}
