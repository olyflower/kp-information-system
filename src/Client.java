import java.time.LocalDate;

public class Client {
    private int clientId;
    private final int serviceZoneId;
    private final String name;
    private final String surname;
    private final String address;
    private final String phoneNumber;
    private final String email;
    private final LocalDate registrationDate;


    public Client(int serviceZoneId, String name, String surname, String address, String phoneNumber, String email, LocalDate registrationDate) {
        this.serviceZoneId = serviceZoneId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registrationDate = registrationDate != null ? registrationDate : LocalDate.now();
    }

    public Client(int clientId, int serviceZoneId, String name, String surname, String address, String phoneNumber, String email, LocalDate registrationDate) {
        this.clientId = clientId;
        this.serviceZoneId = serviceZoneId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registrationDate = registrationDate != null ? registrationDate : LocalDate.now();
    }

    public int getClientId() {
        return clientId;
    }

    public int getServiceZoneId() {
        return serviceZoneId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "ID клієнта: " + clientId + "\n" +
                "ID зони обслуговування: " + serviceZoneId +
                "Ім'я: " + name + "\n" +
                "Прізвище: " + surname + "\n" +
                "Адреса: " + address + "\n" +
                "Номер телефону: " + phoneNumber + "\n" +
                "Електронна пошта: " + email + "\n" +
                "Дата реєстрації: " + registrationDate + "\n";
    }
}
