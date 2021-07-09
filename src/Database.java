import java.sql.*;
import java.util.ArrayList;

public class Database {
    public static String url;

    public static void createDatabase(String fileName) {
        url = "jdbc:sqlite:" + fileName;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "number TEXT," +
                "pin TEXT," +
                "balance INTEGER DEFAULT 0)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addCardToDB(CreditCard card) {
        try (Connection conn = DriverManager.getConnection(url)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?)")) {
                ps.setString(1, card.getCardNumber());
                ps.setString(2, card.getPin());
                ps.setInt(3, card.getBalance());
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setIncome(CreditCard card) {
        try (Connection conn = DriverManager.getConnection(url)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE card SET balance = ? WHERE number = ?")) {
                ps.setInt(1, card.getBalance());
                ps.setString(2, card.getCardNumber());
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCard(CreditCard card) {
        try (Connection conn = DriverManager.getConnection(url)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM card WHERE number = ?")) {
                ps.setString(1, card.getCardNumber());
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> downloadCards() {
        String sql = "SELECT id, number, pin, balance FROM card";
        ArrayList<String> creditCards = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                creditCards.add(rs.getString("number"));
                creditCards.add(rs.getString("pin"));
                creditCards.add(rs.getString("balance"));
            }
            return creditCards;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return creditCards;
    }
}