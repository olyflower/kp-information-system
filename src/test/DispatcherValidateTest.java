import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DispatcherValidateTest {

    @Test
    void testNameValidation() {
        assertTrue(addDispatcher("Іван", "Петренко", "test@example.com", "380123456789"));
        assertFalse(addDispatcher("", "Петренко", "test@example.com", "380123456789"));
    }

    @Test
    void testSurnameValidation() {
        assertTrue(addDispatcher("Іван", "Петренко", "test@example.com", "380123456789"));
        assertFalse(addDispatcher("Іван", "", "test@example.com", "380123456789"));
    }

    @Test
    void testEmailValidation() {
        assertTrue(addDispatcher("Іван", "Петренко", "test@example.com", "380123456789"));
        assertFalse(addDispatcher("Іван", "Петренко", "", "380123456789"));
        assertFalse(addDispatcher("Іван", "Петренко", "testexample.com", "380123456789"));
        assertFalse(addDispatcher("Іван", "Петренко", "test@.com", "380123456789"));
    }

    @Test
    void testPhoneNumberValidation() {
        assertTrue(addDispatcher("Іван", "Петренко", "test@example.com", "380123456789"));
        assertFalse(addDispatcher("Іван", "Петренко", "test@example.com", ""));
        assertFalse(addDispatcher("Іван", "Петренко", "test@example.com", "1234567890"));
        assertFalse(addDispatcher("Іван", "Петренко", "test@example.com", "3801234567"));
    }

    private boolean addDispatcher(String name, String surname, String email, String phoneNumber) {
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            return false;
        }
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            return false;
        }
        return phoneNumber.matches("^380\\d{9}$");
    }
}
