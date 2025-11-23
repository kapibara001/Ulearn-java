public class Program {
    public static void main(String[] args) {
        char[][] myarr = getTwoDimensionalArray(7);
        System.out.println(getStringArray(myarr));

        String result = "A" + 12;
        System.out.println(result);
    }


    public static char[][] getTwoDimensionalArray(int size) {
        if (size % 2 == 0) {
            throw new IllegalArgumentException("Неверно указан размер.");
        }

        char[][] arr = new char[size][size];
 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == j) || j == size-1-i) {
                    arr[i][j] = 'X';
                } else {
                    arr[i][j] = ' ';
                }
            }
        }

        return arr;
    }


    public static String getStringArray(char[][] array) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sb.append(array[i][j]);
            }
            sb.append("\n"); 
        }

        return sb.toString();
    }
}