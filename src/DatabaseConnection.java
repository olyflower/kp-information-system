import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:WS.db";

    /**
     * Функціональний інтерфейс для перетворення рядка з ResultSet в об'єкт типу T
     */
    @FunctionalInterface
    public interface ResultSetExtractor<T> {
        T extract(ResultSet rs) throws SQLException;
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Допоміжна функція для парсингу поля Date
     */
    public static LocalDate getLocalDate(ResultSet rs, String columnName) throws SQLException {
        Date sqlDate = rs.getDate(columnName);
        return (sqlDate != null) ? sqlDate.toLocalDate() : null;
    }

    /**
     * Універсальний метод для отримання даних з бази
     */
    public static <T> List<T> executeQuery(String sql, ResultSetExtractor<T> extractor) {
        List<T> result = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(extractor.extract(rs));
            }
        } catch (SQLException e) {
            System.out.println("Помилка при виконанні запита: " + e.getMessage());
        }
        return result;
    }

    /**
     * Методи для отримання даних з бази (Виклики, Рахунки, Постачальники, Бригади, Клієнти, Диспетчери, Зони обслуговування
     */

    public static List<Request> getAllRequests() {
        String sql = "SELECT * FROM Request";
        return executeQuery(sql, rs -> new Request(
                rs.getInt("requestId"),
                rs.getInt("clientId"),
                rs.getInt("dispatcherId"),
                getLocalDate(rs, "createdAt"),
                rs.getString("description"),
                rs.getString("priority"),
                rs.getString("status")
        ));
    }


    public static List<Incident> getAllIncidents() {
        String sql = "SELECT * FROM Incident";
        return executeQuery(sql, rs -> new Incident(
                rs.getInt("incidentId"),
                rs.getInt("requestId"),
                rs.getInt("teamId"),
                rs.getString("location"),
                rs.getString("description"),
                rs.getString("complexity"),
                getLocalDate(rs, "startDate"),
                getLocalDate(rs, "endDate")
        ));
    }


    public static List<Bill> getAllBills() {
        String sql = "SELECT * FROM Bill";
        return executeQuery(sql, rs -> new Bill(
                rs.getInt("billId"),
                rs.getInt("incidentId"),
                rs.getString("billName"),
                rs.getDouble("amount"),
                getLocalDate(rs, "dateBill"),
                getLocalDate(rs, "datePayment"),
                rs.getString("status")
        ));
    }

    public static List<Supplier> getAllSuppliers() {
        String sql = "SELECT * FROM Supplier";
        return executeQuery(sql, rs -> new Supplier(
                rs.getInt("supplierId"),
                rs.getString("name"),
                rs.getString("phoneNumber"),
                rs.getString("email")
        ));
    }

    public static List<Team> getAllTeams() {
        String sql = "SELECT * FROM Team";
        return executeQuery(sql, rs -> new Team(
                rs.getInt("teamId"),
                rs.getString("specialization"),
                rs.getString("status")
        ));
    }


    public static List<Client> getAllClients() {
        String sql = "SELECT * FROM Client";
        return executeQuery(sql, rs -> new Client(
                rs.getInt("clientId"),
                rs.getInt("serviceZoneId"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("address"),
                rs.getString("phoneNumber"),
                rs.getString("email"),
                getLocalDate(rs, "registrationDate")
        ));
    }


    public static List<Dispatcher> getAllDispatchers() {
        String sql = "SELECT * FROM Dispatcher";
        return executeQuery(sql, rs -> new Dispatcher(
                rs.getInt("dispatcherId"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("email"),
                rs.getString("phoneNumber")
        ));
    }

    public static List<ServiceZone> getAllServiceZones() {
        String sql = "SELECT * FROM ServiceZone";
        return executeQuery(sql, rs -> new ServiceZone(
                rs.getInt("serviceZoneId"),
                rs.getString("name")
        ));
    }

    /**
     * Методи для додавання даних в базу
     */

    public static boolean addRequest(Request request) {
        String sql = "INSERT INTO Request (clientId, dispatcherId, createdAt, description, priority, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, request.getClientId());
            stmt.setInt(2, request.getDispatcherId());

            if (request.getCreatedAt() != null) {
                stmt.setDate(3, Date.valueOf(request.getCreatedAt()));
            } else {
                stmt.setDate(3, null);
            }
            stmt.setString(4, request.getDescription());
            stmt.setString(5, request.getPriority());
            stmt.setString(6, request.getStatus());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Помилка при додаванні заявки: " + e.getMessage());
            return false;
        }
    }


    public static boolean addClient(Client client) {
        String sql = "INSERT INTO Client (serviceZoneId, name, surname, address, phoneNumber, email, registrationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, client.getServiceZoneId());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getSurname());
            stmt.setString(4, client.getAddress());
            stmt.setString(5, client.getPhoneNumber());
            stmt.setString(6, client.getEmail());
            stmt.setDate(7, Date.valueOf(client.getRegistrationDate()));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Помилка при додаванні клієнта: " + e.getMessage());
            return false;
        }
    }

    public static boolean addDispatcher(Dispatcher dispatcher) {
        String sql = "INSERT INTO Dispatcher (name, surname, email, phoneNumber) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dispatcher.getName());
            stmt.setString(2, dispatcher.getSurname());
            stmt.setString(3, dispatcher.getEmail());
            stmt.setString(4, dispatcher.getPhoneNumber());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Помилка при додаванні диспетчера: " + e.getMessage());
            return false;
        }
    }

    /**
     * Методи для видалення даних з бази
     */

    public static boolean deleteRequest(int requestId) {
        String sql = "DELETE FROM Request WHERE requestId = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, requestId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Помилка при видаленні");
            e.printStackTrace(System.err);
            return false;
        }
    }

    public static boolean deleteClient(int clientId) {
        String sql = "DELETE FROM Client WHERE clientId = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Помилка при видаленні");
            e.printStackTrace(System.err);
            return false;
        }
    }

    /**
     * Методи для оновлення даних в базі
     */

    public static void updateSupplier(Supplier supplier) {
        String sql = "UPDATE Supplier SET name = ?, phoneNumber = ?, email = ? WHERE supplierId = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getPhoneNumber());
            stmt.setString(3, supplier.getEmail());
            stmt.setInt(4, supplier.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Помилка оновлення: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void updateServiceZone(ServiceZone serviceZone) {
        String sql = "UPDATE ServiceZone SET name = ? WHERE serviceZoneId = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceZone.getName());
            stmt.setInt(2, serviceZone.getServiceZoneId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Помилка оновлення: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }


    public static void main(String[] args) {
        try (Connection _ = connect()) {
            System.out.println("З'єднання встановлено");
        } catch (SQLException e) {
            System.err.println("Помилка при з'єднанні");
            e.printStackTrace(System.err);
        }
    }
}
