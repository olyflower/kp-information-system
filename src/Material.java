public class Material {
    private int materialId;
    private final int supplierId;
    private final String name;
    private final double price;
    private final String unit;

    public Material(int supplierId, String name, double price, String unit) {
        this.supplierId = supplierId;
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    public Material(int materialId, int supplierId, String name, double price, String unit) {
        this.materialId = materialId;
        this.supplierId = supplierId;
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    public int getMaterialId() {
        return materialId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "ID матеріалу: " + materialId + "\n" +
                "ID постачальника: " + supplierId + "\n" +
                "Назва: " + name + "\n" +
                "Ціна: " + price + "\n" +
                "Одиниця виміру: " + unit;
    }

}
