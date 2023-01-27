package util;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to handle file IO for saving the state
 * of the program. Probably could be written better, but
 * it gets the job done.
 *
 * @author Maxx Batterton
 */
public class ConfigUtil {

    public static void saveToFile(Map<String, Object> data) {
        try {
            Path configPath = FileSystems.getDefault().getPath("poster.cfg");
            if (!Files.exists(configPath)) {
                Files.createFile(configPath);
            }
            BufferedWriter stream = Files.newBufferedWriter(configPath, StandardOpenOption.TRUNCATE_EXISTING);
            for (String key : data.keySet()) {
                stream.write(key + ":" + data.get(key));
            }
            stream.close();
            System.out.println("Config was saved with no errors.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Map<String, String> readFromFile() {
        Path configPath = FileSystems.getDefault().getPath("poster.cfg");
        HashMap<String, String> data = new HashMap<>();
        if (Files.exists(configPath)) {
            try {
                BufferedReader reader = Files.newBufferedReader(configPath);
                var contents = reader.lines();

                contents.forEach(s -> {
                    String[] split = s.split(":");
                    data.put(split[0], split[1]);
                });

                System.out.println("Successfully loaded config.");
                return data;
            } catch (Exception e) {
                System.out.println("Config was corrupt, proceeding with default values.");
                return new HashMap<>();
            }
        } else {
            System.out.println("No config found, proceeding with default values.");
            return data;
        }
    }

}
