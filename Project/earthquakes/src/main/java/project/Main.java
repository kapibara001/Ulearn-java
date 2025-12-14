package project;


public class Main {
    public static void main(String[] args) {
        Schedule sc = new Schedule();
        
        // График по среднему количеству землетрясений для каждого года
        sc.create_all_schedule();
        
        // Средняя магнитуда для штата West Virginia
        sc.create_schedule_city("West Virginia");

        // Самое глубокое землетрясение в 2013 году
        sc.create_schedule_deepest(2013);
    }
}