package org.clm.javaproject.sqlparser;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String dbType = JdbcConstants.POSTGRESQL; // 可以是ORACLE、POSTGRESQL、SQLSERVER、ODPS等
		String sql = "select * from agent_down_info";
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
	}

}
