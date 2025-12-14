package project;

import java.io.IOException;
// import java.util.Arrays; // Еслм захочу увидеть массивы не в виде хэшей памяти
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

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
        SortedMap<Integer, Integer> earthquakesPerYear = getEarthquakesPerYear();
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        earthquakesPerYear.forEach((year, count) -> {
            dataset.addValue(count, "Count earthquakes per year", year);
        });

        JFreeChart chart = ChartFactory.createBarChart("Earthquakes",
            "year", 
            "Count earthquakes", 
            dataset);

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // Поворот 45 градусов

        // отступы
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame(); 
        frame.setSize(800, 600);
        frame.setContentPane(chartPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    // Вывести в консоль среднюю магнитуду для города city (West Virginia)
    public void create_schedule_city(String city) {
        System.out.println(avergeMagnutude(city));
    }
    
    // Вывести название штата, в котором произошло самое глубокое землетрясение за year год
    public void create_schedule_deepest(int year) {
        System.out.println(theDeepestEarthquake(year));
    }
    

    public SortedMap<Integer, Integer> getEarthquakesPerYear() {
        return data.stream()
            .skip(1)
            .map(row -> row[5])
            .map(dateStr -> Integer.parseInt(dateStr.substring(0, 4)))
            .collect(Collectors.groupingBy(year -> year, TreeMap::new, Collectors.summingInt(year -> 1)));
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

    public String theDeepestEarthquake(int year) {
        String stateEarthquakest = data.stream()
            .skip(1)
            .filter(row -> Integer.parseInt(row[5].substring(0, 4)) == year)
            .max(Comparator.comparingInt(row -> Integer.parseInt(row[1])))
            .map(row -> row[4].toLowerCase())
            .orElse("Нет данных за " + year + " год");

        return stateEarthquakest;
    }

}
