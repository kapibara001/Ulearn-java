package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays; // Еслм захочу увидеть массивы не в виде хэшей памяти
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvException;

public class Schedule {
    private Data_parser dp = new Data_parser();
    private List<String[]> data;

    public Schedule() {
        try {
            this.data = dp.readAllData();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    // ID,Глубина в метрах,Тип магнитуды,Магнитуда,Штат,Время
    //  0,               1,            2,        3,   4,    5
    
    // надо среднее количество землетрясений по годам
    public void create_all_schedule() {
        Map<Integer, Integer> earthquakesPerYear = getEarthquakesPerYear();
        
        System.out.println(earthquakesPerYear);
    }
    
    // Вывести в консоль среднюю магнитуду для города city (West Virginia)
    public void create_schedule_city(String city) {
        System.out.println(avergeMagnutude(city));
    }
    
    // Вывести название штата, в котором произошло самое глубокое землетрясение за year год
    public void create_schedule_deepest(int year) {
        
    }
    

    public Map<Integer, Integer> getEarthquakesPerYear() {
        return data.stream()
            .skip(1)
            .map(row -> row[5])
            .map(dateStr -> Integer.parseInt(dateStr.substring(0, 4)))
            .collect(Collectors.groupingBy(year -> year, Collectors.summingInt(year ->1)));
    }

    public Double avergeMagnutude(String city) {
        List<Double> magnitudes = data.stream()
        .filter(row -> row[3] != null && row[4] != null)
        .filter(row -> row[4].toLowerCase().contains(city.toLowerCase()))
        .map(row -> {
            return Double.parseDouble(row[3]);
        })
        .collect(Collectors.toList());

        double sumMgn = 0;
        for (int i = 0; i < magnitudes.size(); i++) {
            sumMgn += magnitudes.get(i);
        }

        if (sumMgn == 0) {
            return 0.0;
        }

        double avergeMgnPerCity = sumMgn / magnitudes.size();
        String formatted = String.format(Locale.US, "%.2f", avergeMgnPerCity);

        return Double.parseDouble(formatted);
    }

    public static void main(String[] args) {
        Schedule sc = new Schedule();
        sc.create_all_schedule();
        
        sc.create_schedule_city("Ohio");
    }
}
