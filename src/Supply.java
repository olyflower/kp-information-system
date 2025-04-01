import java.time.LocalDate;

public class Supply {
    private int supplyId;
    private final int materialId;
    private final double quantitySupply;
    private final LocalDate dateSupply;

    public Supply(int materialId, double quantitySupply, LocalDate dateSupply) {
        this.materialId = materialId;
        this.quantitySupply = quantitySupply;
        this.dateSupply = dateSupply;
    }

    public Supply(int supplyId, int materialId, double quantitySupply, LocalDate dateSupply) {
        this.supplyId = supplyId;
        this.materialId = materialId;
        this.quantitySupply = quantitySupply;
        this.dateSupply = dateSupply;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public double getQuantitySupply() {
        return quantitySupply;
    }

    public LocalDate getDateSupply() {
        return dateSupply;
    }

    @Override
    public String toString() {
        return "ID постачання: " + supplyId + "\n" +
                "ID матеріалу: " + materialId + "\n" +
                "Кількість постачання: " + quantitySupply + "\n" +
                "Дата постачання: " + dateSupply;
    }

}
