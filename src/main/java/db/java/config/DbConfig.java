package db.java.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {
	// JDBC 기본에 관한 설명.

	// jdbc 는 DriverManager 외에 DataSource 방식으로 DB 연결을 정의한다.(Spring 에서는 여기의 DataSource 방식 사용)
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();

		// 아래 주요 property 들을 설명한다.
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
		ds.setUsername("spring5");
		ds.setPassword("spring5");
		ds.setInitialSize(2);
		ds.setMaxActive(10); // 최대 커넥션 개수
		ds.setMaxAge(0); // Connection 가져올 때의 timeout. 기본값 0이며, 유효시간이 없음을 의미한다.
		ds.setValidationQuery("select 1 from dual"); // connection 이 유효한지 검사할 때 쓸 query 지정. 기본 null 이고, 이러면 검사하지 않는다.
		// select 1 이나 select 1 from ~~ 을 주로 사용한다.
		ds.setValidationInterval(-1); // 기본값 -1이며, 0 이하값이면 시행하지 않는다.

		ds.setTestWhileIdle(true); // 유휴상태일때 검사할건지?
		ds.setTestOnBorrow(false); // 가져올 때 검사. Return 은 반환할때이다. 전부 false 가 기본값이다.
		ds.setMinEvictableIdleTimeMillis(60000 * 3); // 유유상태 지속 최소시간. testWhileIdle 이 true 이면 이 값을 초과한 connection 이 제거된다. 기본 1분.
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000); // 유휴커넥션 검사주기. 기본 5초. 1초이하로 설정하면 안된다.
		// 이게, DBMS 자체에서, connection 을 끊도록 설정되있는 경우가 있다. 그래도 connection 객체는 안사라지기 떄문에, 그거 해결하는 것이다.(Eviction 관련)
		return ds;
	}
}
