package org.cloudy.ddl.jdbc;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;


@Component("dataSourceHolder")
public class CCDLDatasource extends AbstractRoutingDataSource {
	@Resource
	private CCDLDatasourceHolder ccdlDataSourceHolder;

	@Override
	protected Object determineCurrentLookupKey() {
		return ccdlDataSourceHolder.getRoutingIndex();
	}
}
