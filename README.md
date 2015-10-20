# shard
split database split table  spring jdbctemplate split rw


	<context:component-scan base-package="org.cloudy.ddl">
		<context:include-filter type="annotation" expression="org.aspectj.lang.annotation.Aspect" />
	</context:component-scan>	

	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
		<property name="driverClass" value="${jdbc.driver}" />
		<property name="jdbcUrl" value="${jdbc.url}" />
		<property name="user" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
		<property name="initialPoolSize" value="${connection_pools.initial_pool_size}" />
		<property name="minPoolSize" value="${connection_pools.min_pool_size}" />
		<property name="maxPoolSize" value="${connection_pools.max_pool_size}" />
		<property name="maxIdleTime" value="${connection_pools.max_idle_time}" />
		<property name="acquireIncrement" value="${connection_pools.acquire_increment}" />
		<property name="checkoutTimeout" value="${connection_pools.checkout_timeout}" />
	</bean>
	<!--
	<bean id="dataSource2" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
		<property name="driverClass" value="${jdbc.driver}" />
		<property name="jdbcUrl" value="${jdbc.url2}" />
		<property name="user" value="${jdbc.username2}" />
		<property name="password" value="${jdbc.password2}" />
		<property name="initialPoolSize" value="${connection_pools.initial_pool_size}" />
		<property name="minPoolSize" value="${connection_pools.min_pool_size}" />
		<property name="maxPoolSize" value="${connection_pools.max_pool_size}" />
		<property name="maxIdleTime" value="${connection_pools.max_idle_time}" />
		<property name="acquireIncrement" value="${connection_pools.acquire_increment}" />
		<property name="checkoutTimeout" value="${connection_pools.checkout_timeout}" />
	</bean>	-->
	
	<bean id="kDataSourceGroup" class="org.cloudy.ddl.jdbc.CCDLDatasource">
		<property name="targetDataSources">
			<map key-type="java.lang.Integer">
				<!-- <entry key="0" value-ref="dataSource2" /> -->
				<entry key="1" value-ref="dataSource" />
			</map>
		</property>
	</bean>		
	
	<bean id="ccdlJdbcTemplate" class="org.cloudy.ddl.jdbc.CCDLJdbcTemplateImpl">
		<property name="dataSource" ref="kDataSourceGroup" />
	</bean>
	
	<bean id="tableRuler" class="org.cloudy.ddl.rule.impl.RulerImpl">
		<property name="tables">
			<map key-type="java.lang.String">
				<entry key="cddl_test1">
					<bean class="org.cloudy.ddl.entity.TBLEntity">
						<property name="wr" value="4" />
						<property name="dsn" value="2" />
						<property name="rule" value="p2 % 3" />
					</bean>
				</entry>
				<entry key="cddl_test2">
					<bean class="org.cloudy.ddl.entity.TBLEntity">
						<property name="wr" value="4" /><!-- 读写分离的界点 -->
						<property name="dsn" value="2" /><!-- 数据源的个数 -->
						<property name="rule" value="p2 % 3" /><!-- 计算数据落在那张表 -->
					</bean>
				</entry>						
			</map>
		</property>
	</bean>		
