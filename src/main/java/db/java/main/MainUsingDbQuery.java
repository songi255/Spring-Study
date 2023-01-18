package db.java.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import db.java.config.DbConfig;
import db.java.config.DbQueryConfig;
import db.java.dbquery.DbQuery;

public class MainUsingDbQuery {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class,
				DbQueryConfig.class);

		DbQuery dbQuery = ctx.getBean(DbQuery.class);
		int count = dbQuery.count();
		System.out.println(count);
		ctx.close();
	}
}
