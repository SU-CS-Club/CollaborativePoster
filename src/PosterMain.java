import manipulators.*;
import util.DisplayPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

import static util.ConfigUtil.CONFIG;

/**
 * Main class that does the set up work
 * to create the GUI, load the images and
 * manipulators. Should be the main method
 * that you run.
 *
 * @author Maxx Batterton
 */
public class PosterMain {

    public static void main(String[] args) {
        String[] classes = new String[]{};
        BufferedImage image;
        try {
            String imagePath;
            if (!CONFIG.containsKey("displayImage")) {
                CONFIG.put("displayImage", "src/imagesources/test_image.png");
            }
            imagePath = CONFIG.get("displayImage");

            System.out.println(imagePath);
            System.out.println(new File(imagePath).exists());
            image = ImageIO.read(new File(imagePath));

            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("manipulators/");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();

                    classes = new Scanner((InputStream) url.getContent()).useDelimiter("\\A").next().split("\n");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LinkedList<Manipulator> manipulators = new LinkedList<>();
        for (String classname : classes) {
            try {
                Class<?> targetClass = Class.forName("manipulators." + classname.replace(".class", ""));
                if (Modifier.isAbstract(targetClass.getModifiers())) {
                    System.out.printf("Skipped abstract \"%s\"\n", classname);
                } else {
                    manipulators.add((Manipulator) targetClass.getConstructors()[0].newInstance());
                    System.out.printf("Added \"%s\"\n", classname);
                }
            } catch (Exception e) {
                System.out.printf("Failed to add \"%s\"\n", classname);
            }
        }

        Manipulator[] manipulatorsArray = manipulators.toArray(new Manipulator[0]);
        JFrame frame = DisplayPanel.createSimpleJFrame(image, manipulatorsArray);
        frame.setVisible(true);
    }

}
