package com.nivalsoul.webspider.dao;

import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Dao工厂，用于获取操作数据库的Dao，适用于Nutz框架
 * @author wlxu
 *
 */
public class DaoFactory {
	private static DruidDataSource dds=null;
	SimpleDataSource ds = new SimpleDataSource();
	private static Dao dao = null;
	
	public static Dao getDao(Map<String, String> config) {
		if (dao != null) {
			return dao;
		} else if (dds != null) {
			dao = new NutDao(dds);
			return dao;
		} else {
			String driver, url, userName, password;
			if(config!=null){
				driver = config.get("driver");
				url = config.get("url");
				userName = config.get("userName");
				password = config.get("password");
			}else{
				ResourceBundle rb = ResourceBundle.getBundle("config.DBConfig");
				driver = rb.getString("driver");
				url = rb.getString("url");
				userName = rb.getString("userName");
				password = rb.getString("password");
			}
			dds = new DruidDataSource();
			dds.setDriverClassName(driver);
			dds.setUrl(url);
			dds.setUsername(userName);
			dds.setPassword(password);
			try {
				dds.setFilters("stat");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dao = new NutDao(dds);
			return dao;
		}
	}
	
	/**
	 * 重置dao
	 */
	public static void reset() {
		dao=null;
	}
	
	/**
	 * /关闭池内所有连接
	 */
	public static void closeAllConnection() {
		dds.close(); 
	}
	
	private DaoFactory() {
		// can not use this constructor
	}
}
