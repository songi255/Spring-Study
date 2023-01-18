package db.java.dbquery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class DbQuery {
	// JDBC 를 썡으로 쓸 때의 예시코드이다. template 를 쓸거면 건너뛰자.

	private DataSource dataSource;

	public DbQuery(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int count() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection(); // pool 에서 가져온 connection 이 idle 에서 activate 상태가 된다.
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select count(*) from MEMBER")) {
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				try {
					conn.close(); // 실제로 connection 이 끊기는 건 아니고, pool 에 반환된다.
				} catch (SQLException e) {
				}
		}
	}

}
