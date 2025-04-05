import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DispatcherBoundaryTest {

    @Test
    public void testNameNotEmpty() {
        String name = "";
        assertFalse(isNameValid(name));
    }

    @Test
    public void testNameAtBoundary() {
        String name = "A";
        assertTrue(isNameValid(name));
    }

    @Test
    public void testNameAfterBoundary() {
        String name = "Ан";
        assertTrue(isNameValid(name));
    }

    @Test
    public void testEmailEmpty() {
        String email = "";
        assertFalse(isEmailValid(email));
    }

    @Test
    public void testEmailAtBoundary() {
        String email = "a@b.co";
        assertTrue(isEmailValid(email));
    }

    @Test
    public void testEmailAfterBoundary() {
        String email = "abc@de.fg";
        assertTrue(isEmailValid(email));
    }

    @Test
    public void testEmailInvalidFormat() {
        String email = "testexample.com";
        assertFalse(isEmailValid(email));
    }

    @Test
    public void testEmailInvalidDotPlacement() {
        String email = "test@.com";
        assertFalse(isEmailValid(email));
    }

    @Test
    public void testPhoneNumberTooShort() {
        String phoneNumber = "38012345678";
        assertFalse(isPhoneNumberValid(phoneNumber));
    }

    @Test
    public void testPhoneNumberAtBoundary() {
        String phoneNumber = "380123456789";
        assertTrue(isPhoneNumberValid(phoneNumber));
    }

    @Test
    public void testPhoneNumberTooLong() {
        String phoneNumber = "3801234567890";
        assertFalse(isPhoneNumberValid(phoneNumber));
    }

    @Test
    public void testPhoneNumberInvalidPrefix() {
        String phoneNumber = "381987654321";
        assertFalse(isPhoneNumberValid(phoneNumber));
    }

    @Test
    public void testPhoneNumberInvalidFormat() {
        String phoneNumber = "1234567890";
        assertFalse(isPhoneNumberValid(phoneNumber));
    }

    private boolean isNameValid(String name) {
        return name != null && !name.isEmpty();
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && !email.isEmpty() && email.matches(emailRegex);
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        String phoneRegex = "^380\\d{9}$";
        return phoneNumber != null && phoneNumber.matches(phoneRegex);
    }
}

