import java.time.LocalDate;

public class Incident {
    private int incidentId;
    private final int requestId;
    private final int teamId;
    private final String location;
    private final String description;
    private final String complexity;
    private final LocalDate startDate;
    private final LocalDate endDate;


    public Incident(int requestId, int teamId, String location,
                    String description, String complexity, LocalDate startDate, LocalDate endDate) {
        this.requestId = requestId;
        this.teamId = teamId;
        this.location = location;
        this.description = description;
        this.complexity = complexity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Incident(int incidentId, int requestId, int teamId, String location,
                    String description, String complexity, LocalDate startDate, LocalDate endDate) {
        this.incidentId = incidentId;
        this.requestId = requestId;
        this.teamId = teamId;
        this.location = location;
        this.description = description;
        this.complexity = complexity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getComplexity() {
        return complexity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "ID Інциденту: " + incidentId + "\n" +
                "ID Виклику: " + requestId + "\n" +
                "ID Бригади: " + teamId + "\n" +
                "Місцезнаходження: " + location + "\n" +
                "Опис: " + description + "\n" +
                "Складність: " + complexity + "\n" +
                "Дата початку роботи над інцидентом: " + startDate + "\n" +
                "Дата завершення роботи над інцидентом: " + endDate;
    }
}
