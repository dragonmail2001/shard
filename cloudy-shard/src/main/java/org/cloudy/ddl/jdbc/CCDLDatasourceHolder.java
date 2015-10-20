package org.cloudy.ddl.jdbc;

import org.springframework.stereotype.Component;

@Component("ccdlDataSourceHolder")
public class CCDLDatasourceHolder {
	private static final ThreadLocal<Integer> holder = new ThreadLocal<Integer>();

	public void setRoutingIndex(int index) {
		holder.set(index);
	}

	public int getRoutingIndex() {
		return holder.get();
	}
}
