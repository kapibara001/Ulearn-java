package project.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.opencsv.exceptions.CsvValidationException;

import project.database.Create_database;
import project.database.Data_filling;


public class Create_parse_data {
    private static final String URL = "jdbc:sqlite:earthquakes.db";

    public Create_parse_data() {}

    public void checkDateBase(Path path) throws SQLException, CsvValidationException, IOException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            // Создание таблиц, если их нет
            Create_database.createTables(conn);
            System.out.println("Таблицы созданы, если их не было.");

            // Проверка, заполнены ли таблицы
            String sql = """
                SELECT id_earthquake FROM earthquake LIMIT 1
                        """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println("Таблицы уже заполнены. Вставка данных не выполнена.");
                return;
            }
            
            Data_filling df = new Data_filling("Землетрясения.csv");
            df.fillingTablesDB(conn);
            System.out.println("Данные вставлены в таблицы.");
        }
    } 
} 