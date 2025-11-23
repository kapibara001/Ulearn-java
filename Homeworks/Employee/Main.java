import model.Employee;;

public class Main {
    public static void main(String[] args) {
        Employee newObject = new Employee("John", 4000);
        System.out.println(newObject.getName());
    }
}