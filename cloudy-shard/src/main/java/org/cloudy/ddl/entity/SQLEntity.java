package org.cloudy.ddl.entity;

public class SQLEntity {
	
	private String sql;
	private String table;
	private Integer[] par;

	public SQLEntity(String line, String sql) {
		String[] node = line.split("#");
		
		this.sql = sql;
		this.table = node[0];
		par = new Integer[node.length - 1];
		
		for(int i=1;i<node.length;i++) {
			par[i-1] = Integer.parseInt(node[i]);
		}
	}
	
	public String getSql() {
		return this.sql;
	}
	
	public String getTable() {
		return this.table;
	}
	
	public Integer[] getPar() {
		return this.par;
	}
}
