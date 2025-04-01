import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodTestDriver {

    public static void main(String[] args) {
        // Тест 1: Список бригад порожній
        List<Team> team1 = new ArrayList<>();
        testShowTeams(team1);

        // Тест 2: Список заявок не порожній
        List<Team> team2 = new ArrayList<>(Arrays.asList(
                new Team(1, "Прорив труби", "В процесі"),
                new Team(2, "Встановлення водоміру", "В очікуванні")
        ));
        testShowTeams(team2);
    }

    private static void testShowTeams(List<Team> teams) {

        if (teams.isEmpty()) {
            System.out.println("Немає бригад.");
            return;
        }

        System.out.println("Список бригад:");
        System.out.println("ID Бригади | Спеціалізація | Статус");

        for (Team team : teams) {
            System.out.printf("%d | %s | %s%n",
                    team.getTeamId(),
                    team.getSpecialization(),
                    team.getStatus());
        }
    }
}
