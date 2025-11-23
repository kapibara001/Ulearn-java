import java.util.ArrayList;
import java.util.Scanner;

class TodoList {
    private ArrayList<String> todolist = new ArrayList<>();
    
    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");
            String command = scanner.nextLine().trim();
            
            switch (command.toUpperCase()) {
                case "LIST":
                    todoList.list();
                    break;

                case "ADD":
                    System.out.print("Введите новое дело: ");
                    String newDo = scanner.nextLine().trim();
                    todoList.add(newDo);
                    break;

                case "EDIT":
                    System.out.print("Введите индекс изменяемого дела: ");
                    int index = Integer.parseInt(scanner.nextLine());

                    System.out.print("Введите новое дело: ");
                    String newTodo = scanner.nextLine().trim();

                    todoList.edit(newTodo, index);
                    break;
                case "DELETE":
                    System.out.print("Введите индекс удаляемого дела");
                    int ind = Integer.parseInt(scanner.nextLine().trim());
                    todoList.delete(ind);
                    break;
    
                default:
                    break;
            }
        }
    }

    public void add(String todo) {
        // TODO: добавьте переданное дело в конец списка
        todolist.add(todo);
        System.out.printf("Добавлено новое дело: %s\n", todo);
    }

    public void add(int index, String todo) {
        // TODO: добавьте дело на указаный индекс,
        //  проверьте возможность добавления
        if (todolist.size() <= index || index < 0) {
            System.out.println("Ошибка: неверный индекс.");
            return;
        }

        todolist.add(index, todo);
    }

    public void edit(String todo, int index) {
        // TODO: заменить дело на index переданным todo индекс,
        //  проверьте возможность изменения
        if (index < 0 || index >= todolist.size()) {
            System.out.println("Ошибка: неверный индекс.");
            return;
        }

        todolist.set(index, todo);
    }

    public void delete(int index) {
        // TODO: удалить дело находящееся по переданному индексу,
        //  проверьте возможность удаления дела
        if (index < 0 || index >= todolist.size()) {
            System.out.println("Ошибка: неверный индекс.");
            return;
        }

        todolist.remove(index);
    }

    public void list() {
        if (todolist.isEmpty()) {
            System.out.println("Список пустой.");
            return;
        }

        for (int i = 0; i < todolist.size(); i++) {
            System.out.printf("%d. %s%n", i+1, todolist.get(i));
        }
    }

    public ArrayList<String> getTodos() {
        return todolist;
    }
}