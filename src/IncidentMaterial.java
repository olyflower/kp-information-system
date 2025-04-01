public class IncidentMaterial {
    private int incidentMaterialId;
    private final int incidentId;
    private final int materialId;
    private final double quantityUsed;

    public IncidentMaterial(int incidentId, int materialId, double quantityUsed) {
        this.incidentId = incidentId;
        this.materialId = materialId;
        this.quantityUsed = quantityUsed;
    }

    public IncidentMaterial(int incidentMaterialId, int incidentId, int materialId, double quantityUsed) {
        this.incidentMaterialId = incidentMaterialId;
        this.incidentId = incidentId;
        this.materialId = materialId;
        this.quantityUsed = quantityUsed;
    }

    public int getIncidentMaterialId() {
        return incidentMaterialId;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public double getQuantityUsed() {
        return quantityUsed;
    }
}
