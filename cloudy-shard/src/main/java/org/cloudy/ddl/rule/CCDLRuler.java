package org.cloudy.ddl.rule;

import java.util.Map;

import org.cloudy.ddl.entity.DBSEntity;
import org.cloudy.ddl.entity.SQLEntity;

public interface CCDLRuler {
	public DBSEntity parse(Map<String, SQLEntity> mapTable, Object par, String sql) ;
}
