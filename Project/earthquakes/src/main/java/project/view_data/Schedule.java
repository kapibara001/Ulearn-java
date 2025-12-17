package project.view_data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

public class Schedule {
    private Connection conn;
    
    public Schedule() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:earthquakes.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMainGUI() {
        JFrame frame = new JFrame("Earthquake Data");
        JPanel panel = new JPanel();
    
        JButton btnAll = new JButton("Show Earthquakes Chart");
        btnAll.addActionListener(e -> create_schedule());
    
        JButton btnCity = new JButton("Average Magnitude for state");
        btnCity.addActionListener(e -> {
            String city = JOptionPane.showInputDialog(frame, "State name:");
            if (city != null && !city.trim().isEmpty()) {
                double avg = avergeMagnutude(city);
                JOptionPane.showMessageDialog(frame, "Average magnitude for " + city + ": " + avg);
            }
        });
    
        JButton btnDeepest = new JButton("Deepest Earthquake State for Year");
        btnDeepest.addActionListener(e -> {
            String yearStr = JOptionPane.showInputDialog(frame, "Enter year:");
            if (yearStr != null) {
                try {
                    int year = Integer.parseInt(yearStr.trim());
                    String state = theDeepestEarthquake(year);
                    JOptionPane.showMessageDialog(frame, "State with deepest earthquake in " + year + ": " + state);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid year format. Please enter a number.");
                }
            }
        });
    
        panel.add(btnCity);
        panel.add(btnDeepest);
        panel.add(btnAll);
    
        frame.add(panel);
        frame.setSize(500, 150);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    // среднее количество землетрясений по годам
    public void create_schedule() {
        SortedMap<Integer, Integer> earthquakesPerYear = getEarthquakesPerYear();
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Брать данные из БД
        
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
    

    public SortedMap<Integer, Integer> getEarthquakesPerYear() {
        SortedMap<Integer, Integer> map = new TreeMap<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT strftime('%Y', time) as year, COUNT(*) as count FROM earthquake GROUP BY year ORDER BY year");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                int count = rs.getInt("count");
                map.put(year, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Double avergeMagnutude(String city) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT AVG(magnitude) as avg FROM earthquake e JOIN state s ON e.state_id = s.state_id WHERE LOWER(s.state_name) LIKE LOWER(?)")) {
            ps.setString(1, "%" + city + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double avg = rs.getDouble("avg");
                return Double.parseDouble(String.format(Locale.US, "%.2f", avg));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public String theDeepestEarthquake(int year) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT s.state_name FROM earthquake e JOIN state s ON e.state_id = s.state_id WHERE strftime('%Y', e.time) = ? ORDER BY e.noun DESC LIMIT 1")) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("state_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Нет данных за " + year + " год";
    }
}
