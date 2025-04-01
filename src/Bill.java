import java.time.LocalDate;

public class Bill {
    private int billId;
    private final int incidentId;
    private final String billName;
    private final double amount;
    private final LocalDate dateBill;
    private final LocalDate datePayment;
    private final String status;

    public Bill(int incidentId, String billName, double amount, LocalDate dateBill, LocalDate datePayment, String status) {
        this.incidentId = incidentId;
        this.billName = billName;
        this.amount = amount;
        this.dateBill = dateBill;
        this.datePayment = datePayment;
        this.status = status;
    }

    public Bill(int billId, int incidentId, String billName, double amount, LocalDate dateBill, LocalDate datePayment, String status) {
        this.billId = billId;
        this.incidentId = incidentId;
        this.billName = billName;
        this.amount = amount;
        this.dateBill = dateBill;
        this.datePayment = datePayment;
        this.status = status;
    }

    public int getBillId() {
        return billId;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public String getBillName() {
        return billName;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDateBill() {
        return dateBill;
    }

    public LocalDate getDatePayment() {
        return datePayment;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ID рахунку: " + billId + "\n" +
                "ID інциденту: " + incidentId + "\n" +
                "Назва рахунку: " + billName + "\n" +
                "Сума: " + amount + "\n" +
                "Дата рахунку: " + dateBill + "\n" +
                "Дата оплати: " + datePayment + "\n" +
                "Статус: " + status;
    }

}
