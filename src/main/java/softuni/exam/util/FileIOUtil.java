package softuni.exam.util;

import java.io.IOException;

public interface FileIOUtil {

    String readFileContents(String filePath) throws IOException;

    void write(String content, String filePath) throws IOException;
}
