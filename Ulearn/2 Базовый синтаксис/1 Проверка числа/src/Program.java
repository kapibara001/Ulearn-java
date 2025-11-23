public class Program {
    public static void main(String[] args) {
        System.out.println(check(5555));
        System.out.println(check(2468));
        System.out.println(check(2568));
    }

    public static boolean check(int number) {
        String str_number = String.valueOf(number);

        for (int i = 0; i < str_number.length(); i++) {
            int digit = str_number.charAt(i);
            if (digit % 2 != 0) {
                return false;
            }
        }

        return true;
    }
}
