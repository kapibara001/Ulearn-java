import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkWithPlayers implements IResolver {
    private String filename;
    private List<String> lines;

    public WorkWithPlayers(String filename) {
        this.filename = filename;
        try {
            this.lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Player> getPlayers() {
        //Name[0];Team[1];City[2];Position[3];Nationality[4];Agency[5];Transfer cost[6];Participations[7];Goals[8];Assists[9];Yellow cards[10];Red cards[11]
        List<Player> players = new ArrayList<>();
        Position pos = null;

        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("Name;Team;")) {
                continue;
            }

            String[] parts = line.split(";");

            switch (parts[3].trim()) {
                case "MIDFIELD":
                    pos = Position.MIDFIELD;
                    break;
                case "DEFENDER":
                    pos = Position.DEFENDER;
                    break;
                case "FORWARD":
                    pos = Position.FORWARD;
                    break;
                case "GOALKEEPER":
                    pos = Position.GOALKEEPER;
                default:
                    pos = Position.GOALKEEPER;
            }
            
            Player newPlayer = new Player(parts[0], parts[1], parts[2], pos, parts[4], parts[5],
                                            Integer.parseInt(parts[6]), Short.parseShort(parts[8]),
                                            Short.parseShort(parts[11]));
            players.add(newPlayer);
        }

        return players;
    }

    public int getCountWithoutAgency() {
        return (int) getPlayers().stream()
            .filter(p -> p.getAgency() == null || p.getAgency().trim().isEmpty())
            .count();
    }

    public int getMaxDefenderGoalsCount() {
        return (int) getPlayers().stream()
            .filter(p -> p.getPosition() == Position.DEFENDER)
            .mapToInt(Player::getGoals)
            .max()
            .orElse(0);
    }

    public String getTheExpensiveGermanPlayerPosition() {
        return getPlayers().stream()
            .filter(p -> "Germany".equalsIgnoreCase(p.getNationality()))
            .max(Comparator.comparingInt(Player::getTransferCost))
            .map(p -> switch (p.getPosition()) {
                case DEFENDER   -> "Защитник";
                case MIDFIELD   -> "Полузащитник";
                case FORWARD    -> "Нападающий";
                case GOALKEEPER -> "Вратарь";
            })
            .orElse("ERROR");
    }

    public Map<String, String> getPlayersByPosition() {
        return getPlayers().stream()
            .collect(Collectors.groupingBy(
                p -> switch (p.getPosition()) {
                    case DEFENDER   -> "Защитники";
                    case MIDFIELD   -> "Полузащитники";
                    case FORWARD    -> "Нападающие";
                    case GOALKEEPER -> "Вратари";
                },
                Collectors.mapping(
                    Player::getName,
                    Collectors.joining(", ")
                )
            ));
    }

    public Set<String> getTeams() {
        return getPlayers().stream()
            .map(Player::getTeam)
            .collect(Collectors.toSet());
    }

    public Map<String, Integer> getTop5TeamsByGoalsCount() {
        return getPlayers().stream()
                .collect(Collectors.groupingBy(
                    Player::getTeam,
                    Collectors.summingInt(Player::getGoals)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }

    public String getAgencyWithMinPlayersCount() {
        return getPlayers().stream()
            .filter(p -> p.getAgency() != null &&
                        !p.getAgency().trim().isEmpty() &&
                        !"No Agency".equalsIgnoreCase(p.getAgency().trim()))
            .collect(Collectors.groupingBy(
                Player::getAgency,
                Collectors.counting()
            ))
            .entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Нет агентств");
    }

    public String getTheRudestTeam() {
        return getPlayers().stream()
            .collect(Collectors.groupingBy(
                Player::getTeam,
                Collectors.averagingInt(Player::getRedCards)
            ))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("other");
    }

    public Map<String, Integer> getTop10TeamsByTransferValue() {
        return getPlayers().stream()
                .collect(Collectors.groupingBy(
                    Player::getTeam,
                    Collectors.summingInt(Player::getTransferCost)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }
}