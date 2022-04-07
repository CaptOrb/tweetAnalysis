package org.tojaco.FileIO;

import java.io.File;
import java.io.IOException;

public class FileService {
    static File createFile(String directory, String fileName) throws IOException {
        File file = new File(directory, fileName);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        return file;
    }
}
