package project;

import java.io.IOException;
import java.util.List;

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

    // надо среднее количество землетрясений по годам
    public void create_all_schedule() {
        
    }

    // Вывести в консоль среднюю магнитуду для города city (West Virginia)
    public void create_schedule_city(String city) {

    }

    // Вывести название штата, в котором произошло самое глубокое землетрясение за year год
    public void create_schedule_deepest(int year) {

    }
}
