import manipulators.*;
import util.ConfigUtil;
import util.DisplayPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.awt.*;

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

    public static void main(String[] args) throws IOException {
        String[] classes = new String[]{};
        BufferedImage image;
        try {
            String imagePath = CONFIG.get("displayImage");
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
        try {
            JFrame frame = DisplayPanel.createSimpleJFrame(image, manipulatorsArray);
        } catch(HeadlessException e) { // Will happen if no graphic environment is available.
            ImageIO.write(image, "jpg", new File("poster.jpg"));
            System.out.println("Could not load GUI so simply wrote poster to \"poster.jpg\"");
        }
    }

}
