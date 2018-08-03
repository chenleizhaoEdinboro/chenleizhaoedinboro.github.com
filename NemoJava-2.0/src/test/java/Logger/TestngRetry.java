package Logger;

import java.util.ArrayList;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestngRetry implements IRetryAnalyzer {
	private ArrayList<String> rerunedlist = new ArrayList<String>();

	@Override
	public boolean retry(ITestResult result) {
		String storeInfo = result.getTestContext().getAttribute("Store").toString();
		System.out.println("RERUN Target - " + storeInfo);
		boolean flag = true;
		for (String str : rerunedlist) {
//			System.out.println(str);
			if (str.equals(storeInfo)) {
				flag = false;
			}
		}
		
		if(flag){
			System.out.println(storeInfo + " RERUN!");rerunedlist.add(storeInfo);}
		else
			System.out.println(storeInfo + " NO RERUN!");
		return flag;
	}

}
