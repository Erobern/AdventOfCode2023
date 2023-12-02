package adventofcode2023.fileloaders;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;


public class FileLoaders {

    public static List<String> loadInputIntoStringList(String input) {
        File file = getFileFromResource("puzzleinputs/"+input);

        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    private static File getFileFromResource(String fileName) {

        ClassLoader classLoader = FileLoaders.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
