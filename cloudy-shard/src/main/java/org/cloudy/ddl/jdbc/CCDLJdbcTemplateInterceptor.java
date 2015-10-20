package org.cloudy.ddl.jdbc;

import java.util.HashMap;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.cloudy.ddl.entity.DBSEntity;
import org.cloudy.ddl.entity.SQLEntity;
import org.cloudy.ddl.error.CCDLException;
import org.cloudy.ddl.rule.CCDLRuler;
import org.cloudy.ddl.util.Util;

@Aspect
public class CCDLJdbcTemplateInterceptor {

	@Resource
	private CCDLDatasourceHolder ccdlDataSourceHolder;
	
	@Resource
	private CCDLJdbcTemplate jdbcTemplate;
	
	@Resource
	private CCDLRuler tableRuler;

	@Around("execution(* org.cloudy.ddl.jdbc.CCDLJdbcTemplate.update*(..))")
	public Object interceptUpdate(ProceedingJoinPoint proceedingJoinPoint) {
		return execute(proceedingJoinPoint, true);
	}
	
	@Around("execution(* org.cloudy.ddl.jdbc.CCDLJdbcTemplate.query*(..))")
	public Object interceptQuery(ProceedingJoinPoint proceedingJoinPoint) {
		return execute(proceedingJoinPoint, false);
	}
	
	public Object execute(ProceedingJoinPoint point, boolean operation) {

		Object[] args = point.getArgs();
		if(args.length <= 1 || !(args[0] instanceof String)){
			throw new java.lang.NoSuchMethodError("method is not exist");
		}
		
		//cddl_test1#0#2#3@cddl_test2#0#1#6@select user_id,id,name,create_date,modify_date from cddl_test where user_id = ?
		String[] sqlNode = ((String) args[0]).split("@");
		if(Util.checkFormat(sqlNode)){
			throw new CCDLException("sql format error");
		}
		
		HashMap<String, SQLEntity> mapTable = new HashMap<String, SQLEntity>();
		for(int i=0; i<sqlNode.length-1; i++) {
			SQLEntity entity = new SQLEntity(sqlNode[i], sqlNode[sqlNode.length-1]);
			mapTable.put(entity.getTable(), entity);
		}	
		
		Object par = args[1];
		if(!par.getClass().isArray()) {
			if(!args[2].getClass().isArray()) {
				throw new CCDLException("param fomat error");
			}
			par = args[2];
		}
		DBSEntity dbs = tableRuler.parse(mapTable, par, sqlNode[sqlNode.length-1]);
		if (dbs.getErr() == null) {
			args[0] = dbs.getSql();
			ccdlDataSourceHolder.setRoutingIndex(dbs.getRoutingIndex());
		} else {
			throw new CCDLException("rule config error",dbs.getErr());
		}
			
			
		Object result = null;
		try {
			result = point.proceed(args);
		} catch (Throwable thr) {
			throw new CCDLException(thr);
		}
		return result;
	}	
	
}
