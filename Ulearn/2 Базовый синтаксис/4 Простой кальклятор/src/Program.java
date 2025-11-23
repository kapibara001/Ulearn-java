public class Program {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println(calc.calculate("12 + 7"));         
        System.out.println(calc.calculate("100 - 45"));       
        System.out.println(calc.calculate("Привет - вет"));  
        System.out.println(calc.getNumbers(123, 456));         
        System.out.println(calc.getMinimalType("5"));          
        System.out.println(calc.getMinimalType("-35000"));     
    }
}

class Calculator {
    public static String calculate(String input) {
        String[] input_args = input.split(" ");
        if (input_args.length != 3) {
            return "Ошибка: неверный формат";
        }

        String aStr = input_args[0];
        String bStr = input_args[2];
        String operation = input_args[1];

        try {
            double a = Double.parseDouble(aStr);
            double b = Double.parseDouble(bStr);
            return String.valueOf(calculate(a, b, operation));
        } catch (NumberFormatException e) {
            return calculate(aStr, bStr, operation);
        }
    }


    private static String calculate(String a, String b, String operation) {
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                String result = a.replaceFirst(b, ""); 
                if (result.equals(a)) {
                    throw new IllegalArgumentException("Не найдено значений для исключения.");
                }
                return result;
            default:
                throw new IllegalArgumentException("Неизвестная операция для строк.");
        }
    }


    private static double calculate(double a, double b, String operation) {
        switch (operation) {
            case "+": 
                return a + b;
            case "-": 
                return a - b;
            case "*": 
                return a * b;
            case "/": 
                if (b == 0) throw new ArithmeticException("Деление на 0.");
                return a / b;
            case "%": 
                if (b == 0) throw new ArithmeticException("Деление на 0.");
                return a % b;
            default:
                throw new IllegalArgumentException("Указан неверный оператор.");
        }
    }


    public static int getNumbers(int a, int b) {
        int sum = a + b;
        int evenCount = 0;

        String sumStr = String.valueOf(sum);
        for (int i = 0; i < sumStr.length(); i++) {
            int digit = Character.getNumericValue(sumStr.charAt(i));
            if (digit % 2 == 0) {
                evenCount++;
            }
        }

        return evenCount;
    }


    public static String getMinimalType(String input) {
        long number = Long.parseLong(input);

        if (number >= -128 && number <= 127) {
            return "Byte";
        } else if (number >= -32768 && number <= 32767) {
            return "Short";
        } else if (number >= -2147483648L && number <= 2147483647L) {
            return "Int";
        } else {
            return "Long";
        }
    }
}
