package wikipedia.java_wikipedia_client_example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws SQLException {
		String databaseHost = System.getenv("WIKIPEDIA_DB_HOST");
		String username = System.getenv("WIKIPEDIA_DB_USERNAME");
		String password = System.getenv("WIKIPEDIA_DB_PASSWORD");
		String databaseName = System.getenv("WIKIPEDIA_DB_NAME");
		String url = String.format("jdbc:mysql://%s/%s?characterEncoding=UTF-8", databaseHost, databaseName);
		try (Connection con = DriverManager.getConnection(url, username, password)) {
			Integer id = 99;
			String title = getPageTitleByPageID(con, id);
			System.out.println(title);
		}
	}

	public static String getPageTitleByPageID(Connection con, Integer id) throws SQLException {
		String sql = "SELECT page_title FROM page WHERE page_id = ?;";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			String title = new String(resultSet.getBytes("page_title"));
			return title;
		}
		return "";
	}
}
