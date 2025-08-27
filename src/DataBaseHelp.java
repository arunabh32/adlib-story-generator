import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBaseHelp {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    // Static block runs once when the class is loaded
    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getStoryTemplate(int id) {
        String query = "SELECT template FROM stories WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("template");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getStoryTitles() {
        String query = "SELECT title FROM stories";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery(query)) {
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();

            String[] titles = new String[size];
            int i = 0;
            while (rs.next()) {
                titles[i++] = rs.getString("title");
            }
            return titles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
