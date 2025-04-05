import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ClientAddTest {

    static class FakeDatabase {
        static boolean simulateFail = false;

        public static boolean addClient(Client client) {
            return !simulateFail;
        }
    }

    private String validateAndAddClient(String name, String surname, String address,
                                        String phone, String email, boolean dbShouldFail) {
        if (isEmpty(name) || isEmpty(surname) || isEmpty(address) || isEmpty(phone) || isEmpty(email)) {
            return "Всі поля повинні бути заповнені.";
        }

        Client client = new Client(1, name, surname, address, phone, email, LocalDate.now());
        FakeDatabase.simulateFail = dbShouldFail;

        boolean added = FakeDatabase.addClient(client);
        return added ? "Клієнта успішно додано." : "Помилка при додаванні клієнта.";
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    @Test
    void testEmptyName() {
        String result = validateAndAddClient("", "Іванов", "вул. Шевченка", "0501234567", "test@example.com", false);
        assertEquals("Всі поля повинні бути заповнені.", result);
    }

    @Test
    void testEmptySurname() {
        String result = validateAndAddClient("Петро", "", "вул. Шевченка", "0501234567", "test@example.com", false);
        assertEquals("Всі поля повинні бути заповнені.", result);
    }

    @Test
    void testEmptyAddress() {
        String result = validateAndAddClient("Петро", "Іванов", "", "0501234567", "test@example.com", false);
        assertEquals("Всі поля повинні бути заповнені.", result);
    }

    @Test
    void testEmptyPhone() {
        String result = validateAndAddClient("Петро", "Іванов", "вул. Шевченка", "", "test@example.com", false);
        assertEquals("Всі поля повинні бути заповнені.", result);
    }

    @Test
    void testEmptyEmail() {
        String result = validateAndAddClient("Петро", "Іванов", "вул. Шевченка", "0501234567", "", false);
        assertEquals("Всі поля повинні бути заповнені.", result);
    }

    @Test
    void testAllFieldsValid() {
        String result = validateAndAddClient("Петро", "Іванов", "вул. Шевченка", "0501234567", "test@example.com", false);
        assertEquals("Клієнта успішно додано.", result);
    }

    @Test
    void testDatabaseError() {
        String result = validateAndAddClient("Петро", "Іванов", "вул. Шевченка", "0501234567", "test@example.com", true);
        assertEquals("Помилка при додаванні клієнта.", result);
    }

}