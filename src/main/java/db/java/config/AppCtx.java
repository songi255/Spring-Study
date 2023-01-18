package db.java.config;

import db.java.spring.*;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Configuration
@EnableTransactionManagement // transaction 설정을 하기 위한 어노테이션
// @Transaction 이 적용된 Bean 의 Proxy 를 생성한다.
// 실제 Transaction 처리는 PlatformTransactionManager 가 한다. Proxy 는 이를 호출할 뿐이다.
// 주요 속성
//   - proxyTargetClass : class 를 이용해서 proxy 생성여부를 지정. 기본값은 false 로, interface 를 이용해서 생성한다.(뭐가다를까...)
//   - order : AOP 적용순서 지정. 기본값은 가장 낮은 우선순위에 해당하는 INTEGER_MAXVALUE 이다.
public class AppCtx {

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
		ds.setUsername("spring5");
		ds.setPassword("spring5");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		return ds;
	}

	// 아래 Bean 을 추가하면 Platform Transaction Manager 를 사용할 수 있다. 즉, @Transaction 을 사용할 수 있다.
	@Bean
	public PlatformTransactionManager transactionManager() {
		// JDBC 는 DataSourceTransactionManager 를 사용한다.
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		// dataSource 지정한다.
		tm.setDataSource(dataSource());
		return tm;
	}
	// 트랜잭션이란, 여러 쿼리가 원자성을 만족하면서 반영되어야 하는 경우 사용하는 것이다.
	// Jdbc 를 쌩으로 사용한다면, setAutoCommit(false) 해주고 commit(), rollback() 으로 직접 제어해야 하는데, 실수의 여지도 많고 반복도 많다.
	// 대신 @Transaction 을 필요한 메서드에 붙여주기만 하면 알아서 트랜잭션 처리를 해준다. (구현기술에 상관없이 처리할 수 있다.)

	// @Transaction 은 내부적으로 AOP 를 사용한다. 즉, proxy 객체를 생성한다.

	// 별도 설정이 없으면, RuntimeException 발생 시 rollback 한다. (Exception 설계시 이를 염두에 두고 RuntimeException 을 상속하기도 한다.)
	// JDBCTemplate 의 연동과정에서 생기는 DataAccessException 도 Runtime 이다.

	// SQLException 은 아니다. 그래서 아래처럼 rollbackFor 속성을 사용해야 한다. ({}로 여러개 지정가능하다.)
	@Transactional(rollbackFor = {SQLException.class, })
	// 반대의 경우 noRollbackFor 을 사용할 수 있다.
	public void someMethod(){};

	/* 그 외 @Transaction 의 다양한 속성들
	  - value : String - Transaction 관리에 사용할 PlatformManager Bean 이름을 지정한다. 기본값 "". 없으면 type 으로 찾는다.
	  - propagation : Propagation - 기본값 Propagation.REQUIRED.
		- REQUIRED : Transaction 필요. 현재 진행중인 Transaction 존재 시 해당 Transaction 사용. 없으면 새로 생성
		- MANDATORY : Transaction 필요. 진행 중 없으면 Exception.
		- REQUIRES_NEW : 항상 새로운 Transaction 시작. 진행중인게 있으면 일시정지 시킨 후 새로 시작, 완료 후 재개.
		- SUPPORTS : 필요하진 않지만, 진행중인게 있으면 사용한다.
		- NOT_SUPPORTS : 필요하지 않고, 진행중인게 있으면 일시중지 시킨 후 작업한다.
		- NEVER : 필요하지 않고, 진행중인게 있으면 Exception.
		- NESTED : 진행중인게 있으면 중첩된 Transaction 생성. 없다면 REQUIRED 와 동일하게 동작. JDBC 3.0 사용시에만 적용됨.
	  - isolation : Isolation - 격리레벨 지정. 기본값 Isolation.DEFAULT
	  	- DEFAULT : 기본설정 사용
	  	- READ_UNCOMMITTED : 다른 Transaction 이 commit 하지 않은 Data 읽을 수 있음
	  	- READ_COMMITTED : 다른 Transaction 이 commit 한 Data 를 읽을 수 있음.
	  	- REPEATABLE_READ : 처음 읽어 온 Data 와 두번째 읽어온 Data 가 동일한 값을 갖는다.
	  	- SERIALIZABLE : 동일 Data에 동시에 2개 이상의 Transaction 수행할 수 없다.
	  간략한 예시를 들어보겠다.
	  	- @Transactional some() { @Transactional any() } 같은 함수 구조는, some() 과 any() 가 동시에 Transaction 에 접근한다.
	  	- 이런 경우, 기본값 Require 이기 때문에 함께 묶여서 처리되는 것이다.
	  	- 만약, any() 에 @ 가 없는데, any 에서 DB에 접근하면 어떻게 될까?
	  		- JDBCTemplate 를 사용한다면, 얘가 알아서 트랜잭션에 포함시켜 준다!
	  Transaction 격리 레벨은 DB 동시접근 시 제어설정을 다룬다. 잘 모르는 초보개발자는 건드리지 맣고 선배개발자에게 물어보자.
	  - timeout : int - Transaction 의 제한시간 설정. 기본값 -1 -> DB의 자체 timeout 을 사용함을 의미. 초단위로 지정.

	*/

	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}

	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao());
	}

	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao());
		return pwdSvc;
	}

	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}

	@Bean
	public MemberListPrinter listPrinter() {
		return new MemberListPrinter(memberDao(), memberPrinter());
	}

	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		infoPrinter.setMemberDao(memberDao());
		infoPrinter.setPrinter(memberPrinter());
		return infoPrinter;
	}
}
