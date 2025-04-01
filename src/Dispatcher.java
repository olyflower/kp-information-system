public class Dispatcher {
    private int dispatcherId;
    private final String name;
    private final String surname;
    private final String email;
    private final String phoneNumber;


    public Dispatcher(String name, String surname, String email, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Dispatcher(int dispatcherId, String name, String surname, String email, String phoneNumber) {
        this.dispatcherId = dispatcherId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    public int getDispatcherId() {
        return dispatcherId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "ID диспетчера: " + dispatcherId + "\n" +
                "Ім'я: " + name + "\n" +
                "Прізвище: " + surname + "\n" +
                "Email: " + email + "\n" +
                "Телефон: " + phoneNumber;
    }
}
