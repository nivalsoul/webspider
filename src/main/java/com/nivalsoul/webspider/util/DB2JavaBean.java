package com.nivalsoul.webspider.util;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DB2JavaBean {

	private String driver = ""; // 驱动
	private String url = ""; // 数据库访问串
	private String userName = ""; // 数据库用户名
	private String password = ""; // 数据库密码
	// 要生成jopo对象的表名,使用;进行分割
	private String tableName = "";
	// 数据库表名匹配模式,使用like语句的匹配模式,自动选择属于此用户且表名称与tableMatchPattern匹配的表,区分大小写
	private String tableMatchPattern = "";
	private String matchPattern = ""; // 是否启用数据库表名匹配模式功能,启用后tableName属性不被使用
	// 输出目录
	String outputDir = "";
	//输出文件的包名
	String packageName="com.entity";
	

	/**
	 * 默认构造
	 * */
	public DB2JavaBean() {
	}

	/**
	 * 使用默认属性文件bqw.tool.DB2JavaBean进行初始化
	 * */
	public DB2JavaBean(boolean init) {
		if (init) {
			ResourceBundle rb = ResourceBundle.getBundle("config.DB2JavaBean");
			this.driver = rb.getString("driver");
			this.url = rb.getString("url");
			this.userName = rb.getString("userName");
			this.password = rb.getString("password");
			this.tableName = rb.getString("tableName");
			this.matchPattern = rb.getString("matchPattern");
			this.tableMatchPattern = rb.getString("tableMatchPattern");
			this.outputDir = rb.getString("outputDir");
			this.packageName = rb.getString("packageName");
		}

	}

	/**
	 * 使用指定属性文件进行初始化,格式需和config.DB2JavaBean文件一致
	 * */
	public DB2JavaBean(String baseName) {
		ResourceBundle rb = ResourceBundle.getBundle(baseName);
		this.driver = rb.getString("driver");
		this.url = rb.getString("url");
		this.userName = rb.getString("userName");
		this.password = rb.getString("password");
		this.tableName = rb.getString("tableName");
		this.tableMatchPattern = rb.getString("tableMatchPattern");
		this.matchPattern = rb.getString("matchPattern");
		this.outputDir = rb.getString("outputDir");
		this.packageName = rb.getString("packageName");
	}

	/**
	 * 使用程序初始化
	 * */
	public DB2JavaBean(String driver, String url, String userName,
			String password, String tableName, String tableMatchPattern,
			String matchPattern, String outputDir, String packageName) {
		this.driver = driver;
		this.password = password;
		this.userName = userName;
		this.url = url;
		this.tableName = tableName;
		this.tableMatchPattern = tableMatchPattern;
		this.matchPattern = matchPattern;
		this.outputDir = outputDir;
		this.packageName = packageName;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setTableMatchPattern(String tableMatchPattern) {
		this.tableMatchPattern = tableMatchPattern;
	}
	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
	}
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getDriver() {
		return this.driver;
	}
	public String getUrl() {
		return this.url;
	}
	public String getUserName() {
		return this.userName;
	}
	public String getPassword() {
		return this.password;
	}
	public String getTableName() {
		return this.tableName;
	}
	public String getTableMatchPattern() {
		return this.tableMatchPattern;
	}
	public String getMatchPattern() {
		return this.matchPattern;
	}
	public String getOutputDir() {
		return outputDir;
	}
	public String getPackageName() {
		return packageName;
	}
	

	public void init() {
		try {
			Class.forName(this.driver).newInstance();
			Connection conn = DriverManager.getConnection(this.url,
					this.userName, this.password);
			String[] tables = new String[0];
			ArrayList<String> tableal = new ArrayList<String>(20);
			if ("true".equals(this.matchPattern)) {
				DatabaseMetaData dbmd = conn.getMetaData();
				ResultSet dbmdrs = dbmd.getTables(null,
						this.userName.toUpperCase(), this.tableMatchPattern,
						new String[] { "TABLE" });
				while (dbmdrs.next()) {
					tableal.add(dbmdrs.getString(3));
				}
				dbmdrs.close();
				if (tableal.size() == 0) {
					dbmdrs = dbmd.getTables(null, this.userName.toLowerCase(),
							this.tableMatchPattern, new String[] { "TABLE" });
					while (dbmdrs.next()) {
						tableal.add(dbmdrs.getString(3));
					}
					dbmdrs.close();
				}
				if (tableal.size() == 0) {
					dbmdrs = dbmd.getTables(null, this.userName,
							this.tableMatchPattern, new String[] { "TABLE" });
					while (dbmdrs.next()) {
						tableal.add(dbmdrs.getString(3));
					}
					dbmdrs.close();
				}
				tables = new String[tableal.size()];
				for (int ti = 0; ti < tableal.size(); ti++) {
					tables[ti] = tableal.get(ti);
				}
			} else {
				tables = this.tableName.split(";");
			}
			String strType;
			String strName;
			String className;
			String[] nameSect;
			StringBuilder tbn = new StringBuilder();
			StringBuilder tstr1 = new StringBuilder();
			File file = new File(outputDir);
			if (!file.exists())
				file.mkdir();
			if (!file.isDirectory())
				file.mkdir();
			for (int i = 0; i < tables.length; i++) {
				nameSect = tables[i].split("_");
				for (String ns : nameSect) {
					tbn.append(ns.substring(0, 1).toUpperCase() + ns.substring(1));
				}

				className = tbn.toString();
				tbn.delete(0, tbn.length());
				tstr1.append("package " + this.packageName + ";");
				tstr1.append("\n\n");
				tstr1.append("import java.util.Date;");
				tstr1.append("\n");
				tstr1.append("import java.sql.*; ");
				tstr1.append("\n");
				tstr1.append("import java.io.*; ");
				tstr1.append("\n\n");
				tstr1.append("import org.nutz.dao.entity.annotation.*;");
				tstr1.append("\n\n");
				tstr1.append("@Table(\""+tables[i]+"\")");
				tstr1.append("\n");
				tstr1.append("public class " + className + " { ");
				try {
					System.out.println("正在处理表："+tables[i]);
					Statement statement = conn.createStatement();
					//获取主键
					DatabaseMetaData dbmd= conn.getMetaData();
					ResultSet rs0 = dbmd.getPrimaryKeys(null,null,tables[i]);
					String keyName="";
					while (rs0.next()) 
						keyName=rs0.getString(4);
					rs0.close();
					
					ResultSet rs = statement.executeQuery("select * from "+ tables[i]);
					ResultSetMetaData rsd = rs.getMetaData();
					StringBuffer fields = new StringBuffer();
					StringBuffer methods = new StringBuffer();
					int cc = rsd.getColumnCount();
					for (int j = 1; j <= cc; j++) {
						strType = this.getObjectType(rsd.getColumnType(j));
						if (strType == null)
							continue;
						strName = rsd.getColumnName(j);
						//获取字段部分
						fields.append(getFieldStr(strType,strName,keyName));
						//获取方法部分
						methods.append(getMethodStr(strType,strName));
					}
					tstr1.append(fields);
					tstr1.append(methods);
					
					rs.close();
					statement.close();

				} catch (Exception tableE) {
					tableE.printStackTrace();
				}
				tstr1.append("\n");
				tstr1.append("}");
				tstr1.append("\n");
				
				file = new File(outputDir + "/" + className + ".java");
				FileWriter fw = new FileWriter(file);
				fw.write(tstr1.toString());
				fw.flush();
				fw.close();
				tstr1.delete(0, tstr1.length());
			}
			conn.close();
		} catch (Exception driverE) {
			driverE.printStackTrace();
		}
	}
	
	private String getFieldStr(String strType, String strName, String keyName) {
		String str ="\n\t";
		if ((strName.endsWith("id") || strName.endsWith("ID") || strName.endsWith("Id"))
				&& ("Integer".equals(strType) || "Long".equals(strType)) && strName.equals(keyName))
			str += "@Id";
		else if(strName.equals(keyName))
			str += "@Name";
		else
		    str += "@Column";
		str +="\n\t";
	    str += "private " + strType + " " + strName + ";";
	    return str;
	}

	private String getMethodStr(String strType, String strName) {
		StringBuffer str = new StringBuffer();
		str.append("\n\n\t");
		str .append("public void set" + strName.substring(0, 1).toUpperCase() + strName.substring(1) 
				+ "(" + strType + " " + strName.toLowerCase() + "){");
		str.append("\n\t\t");
		str.append("this." + strName + " = " + strName.toLowerCase() + ";");
		str.append("\n\t");
		str.append("}");
		str.append("\n\t");
		str.append("public " + strType + " get" + strName.substring(0, 1).toUpperCase() + strName.substring(1) + "(){");
		str.append("\n\t\t");
		str.append("return this." + strName + ";");
		str.append("\n\t");
		str.append("}");
		return str.toString();
	}

	

	public String getObjectType(int type) {
		switch (type) {
		case Types.ARRAY:
			return null;
		case Types.BIGINT:
			return "Long";
		case Types.BINARY:
			return null;
		case Types.BIT:
			return "Byte";
		case Types.BLOB:
			return "Blob";
		case Types.BOOLEAN:
			return "Boolean";
		case Types.CHAR:
			return "String";
		case Types.CLOB:
			return "Clob";
		case Types.DATALINK:
			return null;
		case Types.DATE:
			return "Date";
		case Types.DECIMAL:
			return "Double";
		case Types.DISTINCT:
			return null;
		case Types.DOUBLE:
			return "Double";
		case Types.FLOAT:
			return "Float";
		case Types.INTEGER:
			return "Integer";
		case Types.NUMERIC:
			return "Integer";
		case Types.JAVA_OBJECT:
			return null;
		case Types.LONGVARBINARY:
			return null;
		case Types.LONGVARCHAR:
			return "String";
		case Types.NULL:
			return null;
		case Types.OTHER:
			return null;
		case Types.REAL:
			return null;
		case Types.REF:
			return null;
		case Types.SMALLINT:
			return "Short";
		case Types.STRUCT:
			return null;
		case Types.TIME:
			return "Time";
		case Types.TIMESTAMP:
			return "Timestamp";
		case Types.TINYINT:
			return "Short";
		case Types.VARBINARY:
			return null;
		case Types.VARCHAR:
			return "String";
		default:
			return null;
		}
	}

	public static void main(String[] args) {
		DB2JavaBean d2j = new DB2JavaBean(true);
		System.out.println(d2j.getDriver());
		System.out.println(d2j.getUrl());
		System.out.println(d2j.getUserName());
		System.out.println(d2j.getPassword());
		System.out.println(d2j.getTableName());
		System.out.println(d2j.getTableMatchPattern());
		System.out.println(d2j.getMatchPattern());
		d2j.init();
		// d2j.init(StaticVar.COMMONLYTYPE);
		System.out.println("OK");
	}
}