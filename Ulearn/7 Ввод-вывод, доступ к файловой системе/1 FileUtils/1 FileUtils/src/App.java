import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public class FileUtils {
        public static long calculateFolderSize(String path) {
            if (path == null || path.isEmpty()) {
                    throw new IllegalArgumentException();
            }

            File folder = new File(path);

            if (!folder.exists() || !folder.isDirectory()) {
                throw new IllegalArgumentException();
            }

            return getFolderSize(folder);
        }

        public static long getFolderSize(File file) {
            long size = 0;

            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    size += f.length();
                } else {
                    size += getFolderSize(f);
                }
            }

            return size;
        }
    }
}
