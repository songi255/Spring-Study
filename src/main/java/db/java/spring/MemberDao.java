package db.java.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {
	// Jdbc Template 를 사용한 예시.
	// Dao class 안에서 종속성으로 Template 를 사용해서 정의한다.
	private JdbcTemplate jdbcTemplate;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectByEmail(String email) {
		// JdbcTemplate 의 대표적인 query 메서드. SQL string, RowMapper, Args 를 받는 형태로 많이 사용한다.

		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where EMAIL = ?", // ? 순서별로 args 가 자동 매핑된다.
				new RowMapper<Member>() {
					@Override
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
						Member member = new Member(
								rs.getString("EMAIL"),
								rs.getString("PASSWORD"),
								rs.getString("NAME"),
								rs.getTimestamp("REGDATE").toLocalDateTime());
						member.setId(rs.getLong("ID"));
						return member;
					}
				}, email); // 웬만하면 lambda 쓸듯.

		return results.isEmpty() ? null : results.get(0); // 검색결과 없음 처리
	}

	public void insert(Member member) {
		// keyHolder 는, insert 시에, autoincrement 로 생성된 key 값을 받아올 때 사용한다.
		KeyHolder keyHolder = new GeneratedKeyHolder();
		// PreStatement 를 이용한 예시. setter 를 통해서 args 를 주입할 수 있다.
		// 보안에 좋다고 하는데, 한번 알아봐야할 듯 하다.
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				// 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
				PreparedStatement pstmt = con.prepareStatement(
						"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
						"values (?, ?, ?, ?)",
						new String[] { "ID" }); // 마지막에 준 String[] 은 autoIncrement 인 컬럼을 지정할 때 사용한다.
				// 인덱스 파라미터 값 설정
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4,
						Timestamp.valueOf(member.getRegisterDateTime()));
				// 생성한 PreparedStatement 객체 리턴
				return pstmt;
			}
		}, keyHolder); // 여기서 keyHolder 를 넣어서, increment 를 받아낸다.
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}

	public void update(Member member) {
		// select 말고 Insert, Update, Delete 는 update() 를 이용한다.
		// JdbcTemplate 의 update() 메서드 자체는 변경된 행의 수를 리턴한다.
		jdbcTemplate.update(
				"update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
				member.getName(), member.getPassword(), member.getEmail());
	}

	public List<Member> selectAll() {
		List<Member> results = jdbcTemplate.query("select * from MEMBER",
				(ResultSet rs, int rowNum) -> {
					Member member = new Member(
							rs.getString("EMAIL"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getTimestamp("REGDATE").toLocalDateTime());
					member.setId(rs.getLong("ID"));
					return member;
				});
		return results;
	}

	public int count() {
		// queryForObject()는 결과가 1행인 경우 바로 받을 수 있는 메서드이다.
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from MEMBER", Integer.class);
		return count;
		// 만약 결과 column 이 1개가 아니면? rowMapper 를 사용하는 오버로딩된 메서드를 쓰면 된다.
		// query()와 차이점은 Collection 이 아닌 그대로 반환할 수 있다는 점이다.
	}

}

// Exception에 관한 이야기.
// Template 코드를 보면, Exception에 관한 처리가 강제되지 않는다.
// Exception 발생 시, Template 에서 내부적으로 SQL Exception 을 적절한 사유의 Exception 으로 변환해서 던진다.
// 왜 Spring 은 SQLException 을 받아서 다른 Exception 으로 바꿔서 던지게 할까?
// 주된 이유는 연동기술에 상관없이 동일하게 Exception 을 처리할 수 있도록 하기 위함이다. 처리는 필요할 때만 하면 된다.















