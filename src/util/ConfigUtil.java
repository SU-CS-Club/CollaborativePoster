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
 * of the program. Default configs are defined in the
 * defaults() method, otherwise config can be accessed
 * from any class through the CONFIG variable
 *
 * @author Maxx Batterton
 */
public class ConfigUtil {

    public static Map<String, String> CONFIG = ConfigUtil.readFromFile();

    public static void saveToFile() {
        try {
            Path configPath = FileSystems.getDefault().getPath("poster.cfg");
            if (!Files.exists(configPath)) {
                Files.createFile(configPath);
            }
            BufferedWriter stream = Files.newBufferedWriter(configPath, StandardOpenOption.TRUNCATE_EXISTING);
            for (String key : CONFIG.keySet()) {
                stream.write(key + ":" + CONFIG.get(key) + "\n");
            }
            stream.close();
            System.out.println("Config was saved with no errors.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Map<String, String> readFromFile() {
        Path configPath = FileSystems.getDefault().getPath("poster.cfg");
        HashMap<String, String> data = defaults();
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
                return data;
            }
        } else {
            System.out.println("No config found, proceeding with default values.");
            return data;
        }
    }

    private static HashMap<String, String> defaults() {
        HashMap<String, String> data = new HashMap<>();
        data.putIfAbsent("lastSelected", "CPPNManipulator");
        data.putIfAbsent("displayImage", "src/imagesources/test_image.png");
        data.putIfAbsent("previewImage", "src/imagesources/real_poster.png");
        data.putIfAbsent("previewHeight", "12");
        data.putIfAbsent("previewWidth", "8");
        return data;
    }

}
