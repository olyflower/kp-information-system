public class Team {
    private int teamId;
    private final String specialization;
    private final String status;

    public Team(String specialization, String status) {
        this.specialization = specialization;
        this.status = status;
    }

    public Team(int teamId, String specialization, String status) {
        this.teamId = teamId;
        this.specialization = specialization;
        this.status = status;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ID бригади: " + teamId + "\n" +
                "Спеціалізація: " + specialization + "\n" +
                "Статус: " + status;
    }
}
