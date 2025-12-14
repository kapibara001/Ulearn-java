package project;

import java.io.IOException;
import java.util.Arrays; // Еслм захочу увидеть массивы не в виде хэшей памяти
import java.util.List;
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

    public Map<Integer, Integer> getEarthquakesPerYear() {
        return data.stream()
            .skip(1)
            .map(row -> row[5])
            .map(dateStr -> Integer.parseInt(dateStr.substring(0, 4)))
            .collect(Collectors.groupingBy(year -> year, Collectors.summingInt(year ->1)));
    }

    // надо среднее количество землетрясений по годам
    public void create_all_schedule() {
        Map<Integer, Integer> earthquakesPerYear = getEarthquakesPerYear();

        System.out.println(earthquakesPerYear);
    }

    // Вывести в консоль среднюю магнитуду для города city (West Virginia)
    public void create_schedule_city(String city) {

    }

    // Вывести название штата, в котором произошло самое глубокое землетрясение за year год
    public void create_schedule_deepest(int year) {

    }

    public static void main(String[] args) {
        Schedule sc = new Schedule();
        sc.create_all_schedule();
    }
}
