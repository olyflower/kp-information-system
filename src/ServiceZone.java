public class ServiceZone {
    private int serviceZoneId;
    private final String name;

    public ServiceZone(String name) {
        this.name = name;
    }

    public ServiceZone(int serviceZoneId, String name) {
        this.serviceZoneId = serviceZoneId;
        this.name = name;
    }

    public int getServiceZoneId() {
        return serviceZoneId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
