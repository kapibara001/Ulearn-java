

public class Program {
    public static void main(String[] args) {
        Object[] testdata = {123, "123", "Abc", null, "3.14"};
        
        for (Object obj : testdata) {
            try {
                int result = parseData(obj);
                System.out.println("Результат: " + result);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка превращения в строки в число.");
            } catch (NullPointerException e) {
                System.out.println("Ошибка: null.");
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение.");
            }
        }
    }

    public static int parseData(Object data) {
        if (data == null) {
            throw new NullPointerException("Значение null.");
        }

        if (data instanceof String) {   
            return Integer.parseInt((String) data);
        } else if (data instanceof Integer) {
            return (Integer) data;
        } else {
            throw new IllegalArgumentException("Неподдерживаемый тип данных");
        }
    }
}