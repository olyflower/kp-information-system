public class Supplier {
    private int supplierId;
    private final String name;
    private final String phoneNumber;
    private final String email;

    public Supplier(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Supplier(int supplierId, String name, String phoneNumber, String email) {
        this.supplierId = supplierId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ID постачальника: " + supplierId + "\n" +
                "Назва: " + name + "\n" +
                "Номер телефону: " + phoneNumber + "\n" +
                "Електронна пошта: " + email;
    }

}
