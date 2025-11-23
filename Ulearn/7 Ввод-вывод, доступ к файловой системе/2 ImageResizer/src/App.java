import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
    
    public class ImageResizer {
        public static void resize(String path, int width) throws IOException {
            try {
                if (path == null || path.isEmpty()) {
                    throw new IllegalArgumentException("Путь не может быть пустым!");
                }
    
                File folder = new File(path);
    
                if (!folder.exists() || !folder.isDirectory()) {
                    throw new IllegalArgumentException("Папка не существует: " + path);
                }
    
                File[] files = folder.listFiles();
                if (files == null) return;
    
                for (File f : files) {
                    if (f.isFile()) {
                        BufferedImage image = ImageIO.read(f);
                        if (image == null) continue; // не изображение, пропускаем
    
                        BufferedImage resizedImage = Scalr.resize(image, width);
    
                        String format = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                        ImageIO.write(resizedImage, format, f);
                    }
                }
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        }
    }
}

