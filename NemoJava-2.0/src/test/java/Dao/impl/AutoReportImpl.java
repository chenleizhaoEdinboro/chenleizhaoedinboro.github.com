package Dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import Dao.inter.AutoReportDao;
import Logger.Dailylog;

public class AutoReportImpl implements AutoReportDao {
	private static AutoReportDao testCaseDao = null;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void update(String sql, String log) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(sql, new Object[] { log }, new int[] { java.sql.Types.VARCHAR });
	}

	public static AutoReportDao creatInstance(String DB) {
		if (testCaseDao == null) {
			try {
				// 创建ioc容器
				ApplicationContext act = new ClassPathXmlApplicationContext("beans.xml");

				testCaseDao = (AutoReportDao) act.getBean(DB);
			} catch (BeansException e) {
				// e.printStackTrace();
				System.out.println(e.getMessage() + e);
			}
		}
		return testCaseDao;
	}

	@Override
	public boolean insertImage(String pic_id, byte[] in, String url, String startTime) {
		boolean flag = false;
		Dailylog.logInfo("insertImage");
		try {
			String insert = "INSERT INTO picture_db (`picture_id`, `picture_long`, `url`, `start_time`) VALUES (?,?,?,?);";
			jdbcTemplate.update(insert, new Object[] { pic_id, in, url, startTime }, new int[] { java.sql.Types.VARCHAR,
					java.sql.Types.BLOB, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });
			Dailylog.logInfo("int  insertImage " + pic_id);

		} catch (DataAccessException e) {
			flag = true;
			e.printStackTrace();
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProductNB(String Country, String ProductType, String env) {
		// TODO Auto-generated method stub
		final List<String> pds = new ArrayList<String>();
		
		jdbcTemplate.query("select (select ProductNO FROM product_number_b2c WHERE Country=? AND ProductType=? and env=? "
				+ "order by updatedStock desc LIMIT 0,1) as ProductNO from product_number_b2c LIMIT 0,1",
				new Object[] { Country, ProductType, env},
				new RowMapperResultSetExtractor(new RowMapper() {
					@Override
					public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
						pds.add(rs.getString(1));
						return pds;
					}
				}));

		return pds;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProductNB2(String Country, String ProductType, String env) {
		// TODO Auto-generated method stub
		final List<String> pds = new ArrayList<String>();
		
		jdbcTemplate.query("select (select ProductNO FROM product_number_b2c WHERE Country=? AND ProductType=? and env=? "
				+ "order by updatedStock desc LIMIT 1,1) as ProductNO from product_number_b2c LIMIT 0,1",
				new Object[] { Country, ProductType, env},
				new RowMapperResultSetExtractor(new RowMapper() {
					@Override
					public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
						pds.add(rs.getString(1));
						return pds;
					}
				}));

		return pds;
	}

	@Override
	public String getRerunStores(String runId, String caseName, boolean isCompatibitlity) {
		String targetStores = System.getProperty("targetStore");
		String failedStores = "";
		String finalStores = "";
		String organization = "('Compatibility')";
		if (!isCompatibitlity)
			organization = "('P1','P2')";

		// Get Failed Stores
		List rows = jdbcTemplate
				.queryForList("select store from test_case where test_runid = ? and case_name = ? and organization in "
						+ organization + " and result not in (4,0);", new Object[] { runId, caseName });
		if (rows.size() == 0)
			return "";
		for (int i = 0; i < rows.size(); i++) {
			failedStores = failedStores + "," + rows.get(i).toString().replace("}", "").split("=")[1];
		}
		failedStores = failedStores.substring(1);

		// ALL
		if(targetStores.toLowerCase().equals("all"))
		{
			return failedStores;
		}
		
		// CA	CA,JP
		String[] failStore = failedStores.split(",");	
		List<String> targetList = Arrays.asList(targetStores.split(","));
		for (int i = 0; i < failStore.length; i++) {
			if (targetList.contains(failStore[i].toString())) {
				finalStores = finalStores + "," + failStore[i].toString();
			}
		}
		finalStores = finalStores.substring(1);
		
		return finalStores;
	}

	@Override
	public ArrayList<String[]> getRerunStoresPayment(String runId, String category, boolean isCompatibitlity) {
		String targetStores = System.getProperty("targetStore");
		String organization = "('Compatibility')";
		if (!isCompatibitlity)
			organization = "('P1','P2')";

		// Get Failed Stores
		List rows = jdbcTemplate
				.queryForList("select store, case_name from test_case where test_runid = ? and case_name like '%_"+category+"' and organization in "
						+ organization + " and result not in (4,0,7);", new Object[] { runId });
		if (rows.size() == 0)
			return new ArrayList<String[]>();
		ArrayList<String[]> failedStores = new ArrayList<String[]>();
		for (int i = 0; i < rows.size(); i++) {
			failedStores.add(new String[] {rows.get(i).toString().split(",")[0].split("=")[1], rows.get(i).toString().split(",")[1].split("=")[1].replaceAll("}", "")});
		}

		// ALL - true
		if(targetStores.toLowerCase().equals("all"))
		{
			return failedStores;
		}
		
		// CA	CA,JP - true
		ArrayList<String[]> finalStores = new ArrayList<String[]>();
		String[] target = targetStores.split(",");
		for(String[] item : failedStores)
		{
			if(Arrays.asList(target).contains(item[0]))
				finalStores.add(item);
		}
		
		return finalStores;
	}
}
