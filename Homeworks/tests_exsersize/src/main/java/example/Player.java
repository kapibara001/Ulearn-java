package example;

public class Player {
    private String name;
    private String team;
    private String city;
    private Position position;
    private String nationality;
    private String agency;
    private int transferCost;
    private short goals;
    private short redCards;

    public Player(
        String name, String team, 
        String city, Position position,
        String nationality, 
        String agency, int transferCost,
        short goals, short redCards
    ) 
    {
        this.name = name;
        this.team = team;
        this.city = city;
        this.position = position;
        this.nationality = nationality;
        this.agency = agency;
        this.transferCost = transferCost;
        this.goals = goals;
        this.redCards = redCards;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getCity() {
        return city;
    }

    public Position getPosition() {
        return position;
    }

    public String getNationality() {
        return nationality;
    }

    public String getAgency() {
        return agency;
    }

    public int getTransferCost() {
        return transferCost;
    }

    public short getGoals() {
        return goals;
    }

    public short getRedCards() {
        return redCards;
    }

    @Override
    public String toString() {
        return String.format("\n\nName: " + getName() + ";\nTeam: " + getTeam() + ";\nCity: " + getCity()
                                + ";\nField position: " + getPosition() + "\nNationality: " + getNationality() +  ";\nAgency " + getAgency()
                                + ";\nTransfer cost: " + getTransferCost() + ";\nGoals: " + getGoals() 
                                + ";\nRed Cards: " + getRedCards());
    }
}


// Name;Team;City;Position;Nationality;Agency;Transfer cost;Participations;Goals;Assists;Yellow cards;Red cards
