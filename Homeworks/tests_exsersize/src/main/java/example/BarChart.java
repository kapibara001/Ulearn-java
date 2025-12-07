package example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;

public class BarChart {
    public static void showTop10TeamsChart(WorkWithPlayers wp) {
        var top10 = wp.getTop10TeamsByTransferValue();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        top10.forEach((team, cost) -> {
            dataset.addValue(cost / 1000, "Суммарная стоимость", team);
        });

        JFreeChart barchart = ChartFactory.createBarChart(
            "Топ 10 команд с наивысшей трансферной стоиомстью",
            "Команда",
            "Стоимость",
            dataset);

            
        barchart.setBackgroundPaint(Color.GRAY);

        JFrame frame = new JFrame("Задание 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ChartPanel chartpanel = new ChartPanel(barchart);
        chartpanel.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(chartpanel);
        frame.setVisible(true);
    }
}