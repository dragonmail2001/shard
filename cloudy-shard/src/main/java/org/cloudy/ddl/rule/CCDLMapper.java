package org.cloudy.ddl.rule;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public abstract class CCDLMapper<T> implements RowMapper<T> {

	public abstract T mapRow(ResultSet arg0, int arg1) throws SQLException ;

}
