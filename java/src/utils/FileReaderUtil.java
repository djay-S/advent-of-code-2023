package src.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileReaderUtil {
    public static BufferedReader getInputFile(String fileName) throws FileNotFoundException {
        String baseFolderPath = "./input";
        String filePath = baseFolderPath + fileName;
        File file = new File(filePath);
        return new BufferedReader(new FileReader(file));
    }
}
