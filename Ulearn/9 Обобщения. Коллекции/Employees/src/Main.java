import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Main {
    public static ArrayList<Employee> staff;

    public static void main(String[] args, String path) {
        staff = Employee.loadStaffFromFile(path);
    }

    public static Employee findEmployeeWithHighestSalary(int year) {
        if (staff == null || staff.isEmpty()) {
            throw new IllegalArgumentException("Staff list is empty or null");
        }
        
        Employee result = staff.stream()
                .filter(employee -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(employee.getWorkStart());
                    return calendar.get(Calendar.YEAR) == year;
                })
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
        
        if (result == null) {
            throw new IllegalArgumentException("No employees found for year: " + year);
        }
        
        return result;
    }

    public static ArrayList<Employee> sortEmployee(String column) {
        if (staff == null || staff.isEmpty()) {
            throw new IllegalArgumentException("Staff list is empty or null");
        }
        
        if (column == null || column.trim().isEmpty()) {
            throw new IllegalArgumentException("Column parameter cannot be null or empty");
        }
        
        Comparator<Employee> comparator;
        
        switch (column.toLowerCase()) {
            case "name":
                comparator = Comparator.comparing(Employee::getName);
                break;
            case "salary":
                comparator = Comparator.comparingInt(Employee::getSalary);
                break;
            case "date":
                comparator = Comparator.comparing(Employee::getWorkStart);
                break;
            default:
                throw new IllegalArgumentException("Invalid column name: " + column);
        }
        
        return staff.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

class Employee {
    private String name;
    private Integer salary;
    private Date workStart;

    public Employee(String name, Integer salary, Date workStart) {
        this.name = name;
        this.salary = salary;
        this.workStart = workStart;
    }

    public String getName() {
        return name;
    }

    public Integer getSalary() {
        return salary;
    }

    public Date getWorkStart() {
        return workStart;
    }

    public static ArrayList<Employee> loadStaffFromFile(String path) {
        ArrayList<Employee> employees = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            
            for (String line : lines) {
                String[] parts = line.trim().split("\\s+");
                
                if (parts.length >= 4) {
                    String name = parts[0] + " " + parts[1];
                    Integer salary = Integer.parseInt(parts[2]);
                    Date workStart = dateFormat.parse(parts[3]);
                    
                    employees.add(new Employee(name, salary, workStart));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return employees;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return name + " — " + salary + " — " + dateFormat.format(workStart);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
               Objects.equals(salary, employee.salary) &&
               Objects.equals(workStart, employee.workStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary, workStart);
    }
}