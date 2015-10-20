package org.cloudy.ddl.entity;

public class DBSEntity {
	
	private String sql;
	private String err;
	private Integer dsn = -1;
	
	public DBSEntity(String sql) {
		this.sql = sql;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}
	
	public void setErr(String err1, String err2) {
		this.err = new StringBuilder(err1).append(err2).toString();
	}
	
	public void setErr(String err1, String err2, String err3) {
		this.err = new StringBuilder(err1).append(err2).append(err3).toString();
	}		

	/**
	 * 
	 * @return
	 */
	public boolean isShard() {
		return true;
	}
	
	
	public boolean isRaws(){
		return true;
	}
	
	public int getRoutingIndex() {
		return this.dsn;
	}
	
	public void setRoutingIndex(int index) {
		this.dsn = index;
	}	
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getSql() {
		return this.sql;
	}
}
