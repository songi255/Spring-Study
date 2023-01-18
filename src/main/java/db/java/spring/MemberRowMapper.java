package db.java.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import spring.Member;

public class MemberRowMapper implements RowMapper<spring.Member> {

	@Override
	public spring.Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		spring.Member member = new Member(
				rs.getString("EMAIL"),
				rs.getString("PASSWORD"),
				rs.getString("NAME"),
				rs.getTimestamp("REGDATE").toLocalDateTime());
		member.setId(rs.getLong("ID"));
		return member;
	}

}
