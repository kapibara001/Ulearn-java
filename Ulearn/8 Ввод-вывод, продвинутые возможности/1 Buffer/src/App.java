import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class App {
    public static void main(String[] args) throws Exception {
        
    }

    public class BufferTask {
        public static void refactorFile() {
            try (ReadableByteChannel inChannel = Channels.newChannel(new FileInputStream("File.txt"));
                WritableByteChannel outChannel = Channels.newChannel(new FileOutputStream("Copy.txt"))) {

                ByteBuffer buffer = ByteBuffer.allocate(1024);

                while (inChannel.read(buffer) > 0) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
