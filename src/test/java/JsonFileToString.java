import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonFileToString {
    public String getStringFromJSON(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();

        Reader reader = new FileReader(String.format("src/test/resources/%s",fileName));

        int data = reader.read();
        while (data != -1) {

            sb.append((char) data);
            data = reader.read();
        }
        return sb.toString();
    }


}
