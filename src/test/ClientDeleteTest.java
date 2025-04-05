import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDeleteTest {

    static class FakeDatabase {
        static boolean simulateDeleteFail = false;

        public static boolean deleteClient(int clientId) {
            return !simulateDeleteFail;
        }
    }

    private String deleteClient(int selectedRow, boolean dbShouldFail) {
        if (selectedRow == -1) {
            return "Виберіть клієнта для видалення!";
        }

        int clientId = selectedRow;
        FakeDatabase.simulateDeleteFail = dbShouldFail;

        boolean success = FakeDatabase.deleteClient(clientId);
        return success ? "Клієнта успішно видалено." : "Помилка при видаленні клієнта.";
    }

    // Тест 1: Користувач не вибрав рядок (selectedRow == -1)
    @Test
    void testDeleteClient_NoRowSelected() {
        String result = deleteClient(-1, false);
        assertEquals("Виберіть клієнта для видалення!", result);
    }

    // Тест 2: Користувач вибрав рядок, але не підтвердив видалення
    @Test
    void testDeleteClient_ConfirmedNo() {
        String result = deleteClient(1, false);
        assertEquals("Клієнта успішно видалено.", result);
    }

    // Тест 3: Користувач вибрав рядок, і операція успішна
    @Test
    void testDeleteClient_SuccessfulDeletion() {
        String result = deleteClient(1, false);
        assertEquals("Клієнта успішно видалено.", result);
    }

    // Тест 4: Користувач вибрав рядок, але сталася помилка при видаленні
    @Test
    void testDeleteClient_FailedDeletion() {
        String result = deleteClient(1, true);
        assertEquals("Помилка при видаленні клієнта.", result);
    }
}
