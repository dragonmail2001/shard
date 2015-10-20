package org.cloudy.ddl.rule.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudy.ddl.entity.DBSEntity;
import org.cloudy.ddl.entity.SQLEntity;
import org.cloudy.ddl.entity.TBLEntity;
import org.cloudy.ddl.rule.CCDLRuler;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.type.AviatorFunction;

public class CCDLRulerImpl implements CCDLRuler  {
	
	private Map<String, TBLEntity> tables;
	
	public void setTables(Map<String, TBLEntity> tables) {
		this.tables = tables;
	}
	
	public void setRuler(List<AviatorFunction> functions) {
		for(AviatorFunction function : functions) {
			AviatorEvaluator.addFunction(function);
		}
	}

	public DBSEntity parse(Map<String, SQLEntity> mapTable, Object par, String sql) {

		DBSEntity dbs = new DBSEntity(sql);
		Collection<SQLEntity> collection = mapTable.values();
		for(SQLEntity entity : collection) {
			TBLEntity tbl = tables.get(entity.getTable());
			if(tbl == null) {
				dbs.setErr(entity.getTable()," not exist");
				break;
			}		
			
			Object[] pars = (Object[])par;
			Integer[] pos = entity.getPar();
			Map<String, Object> map = new HashMap<String, Object>();
			for(Integer key : pos) {
				map.put( String.format("p%d",key), pars[key]);
			}
			
			Object index = null;
			try {
				index = AviatorEvaluator.execute(tbl.getRule(), map);
			} catch(Exception exc) {
				dbs.setErr(entity.getTable()," ruler err", exc.getMessage());
				break;
			}
			
			int table = Integer.valueOf(index.toString());
			if(tbl.getWr() != null && tbl.getWr() > 0) {
				table = table + tbl.getWr();
			}

			dbs.setSql(dbs.getSql().replaceAll(entity.getTable(), String.format("%s_%d",entity.getTable(),table)));
			
			int routing = 0;
			if(tbl.getDsn() > 1) {
				routing = table % tbl.getDsn();
			}
			
			if(dbs.getRoutingIndex() >= 0 && dbs.getRoutingIndex() != routing){
				dbs.setErr("table must be in same ds");
				break;
			}
			
			dbs.setRoutingIndex(routing);
		}
		
		return dbs;
	}

}
