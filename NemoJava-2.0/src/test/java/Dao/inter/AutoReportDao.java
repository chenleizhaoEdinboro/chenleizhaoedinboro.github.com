package Dao.inter;

import java.util.ArrayList;
import java.util.List;

public interface AutoReportDao {
	public void update(String sql,String log);
	public boolean insertImage(String pic_id, byte[] in, String  url, String startTime);
	public List<String> getProductNB(String Country,String ProductType ,String env);
	public List<String> getProductNB2(String Country,String ProductType ,String env);
	public String getRerunStores(String runId, String caseName, boolean isCompatibitlity);
	public ArrayList<String[]> getRerunStoresPayment(String runId, String category, boolean isCompatibitlity);
}
