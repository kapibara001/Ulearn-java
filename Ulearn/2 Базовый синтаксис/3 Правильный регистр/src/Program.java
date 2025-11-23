public class Program {
    public static void main(String[] args) {
        System.out.println(getRevertString("NyLL"));
    }

    public static String getRevertString(String str)
    {
        StringBuilder sb = new StringBuilder(str);
        String reversed_string = sb.reverse().toString();

        String first_char = reversed_string.substring(0, 1).toUpperCase();
        String last_char = reversed_string.substring(1).toLowerCase();

        return first_char + last_char;
    }
}