import java.io.IOException;
import java.io.InputStream;

public class File01 {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = File01.class.getClassLoader().getResourceAsStream("file 2.txt");
        /*String file = File01.class.getClassLoader().getResource("file 2.txt").getFile();
        System.out.println(file);*/
        int content;
        while ((content = inputStream.read()) != -1) {
            // convert to char and display it
            System.out.print((char) content);
        }
    }
}
