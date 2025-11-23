public class Program {
    public static void main(String[] args) {
        System.out.println(calculate(20, 50, 3));;
    }

    public static int calculate(int a, int b, int c) {
        int max_in_a = a / c;
        int max_in_b = b / c;

        return max_in_a * max_in_b;
    }
}