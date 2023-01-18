package db.java.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import db.java.dbquery.DbQuery;

@Configuration
public class DbQueryConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	public DbQuery dbQuery() {
		return new DbQuery(dataSource);
	}
}
