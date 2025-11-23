package myapp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Box_Storage extends Storage {
    private ArrayList<Box> boxList = new ArrayList<>();

    public void addBox(Box box) {
        boxList.add(box);
    } 

    public List<Box> getSortedByLength() {
        List<Box> sortedList = new ArrayList<>(boxList);
        sortedList.sort(Comparator.comparingDouble(box -> box.getLength() * box.getHeight() * box.getWidth()));

        return sortedList;
    }

    public int getFragileAmount() {
        int countFragile = 0;
        
        for (Box box : boxList) {
            if (box.getIsBreakUp()) {
                countFragile++;
            }
        }

        return countFragile;
    }
}
