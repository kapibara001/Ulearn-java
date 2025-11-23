package myapp;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Sack_Storage extends Storage {
    protected ArrayList<Sack> sacks = new ArrayList<>();
    protected ArrayList<String> sackOrders = new ArrayList<>();

    public void addSack(Sack sack) {
        sacks.add(sack);
    }

    public Map<Sack.CargoType, ArrayList<Sack>> getSacksByTypes() {
        Map<Sack.CargoType, ArrayList<Sack>> map = new HashMap<>();
        for (Sack sack : sacks) {
            map.computeIfAbsent(sack.getType(), k -> new ArrayList<>());
            map.get(sack.getType()).add(sack);
        }

        return map;
    }

    public Set<Sack.CargoType> getEmptyTypes() {
        Set<Sack.CargoType> presentTypes = EnumSet.noneOf(Sack.CargoType.class);

        for (Sack sack : sacks) {
            presentTypes.add(sack.getType());
        }
        
        Set<Sack.CargoType> emptyTypes = EnumSet.allOf(Sack.CargoType.class);
        emptyTypes.removeAll(presentTypes);

        return emptyTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sack sack : sacks) {
            sb.append(sack.getWeigth() + " " + sack.getType() + "\n");
        }

        return sb.toString().toLowerCase();
    }
}
