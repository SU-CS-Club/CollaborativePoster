import manipulators.*;
import util.ConfigUtil;
import util.DisplayPanel;
import util.PreviewPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
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
                    manipulators.add((Manipulator) targetClass.getConstructor().newInstance());
                    System.out.printf("Added \"%s\"\n", classname);
                }
            } catch (Exception e) {
                System.out.printf("Failed to add \"%s\"\n", classname);
                e.printStackTrace();
                System.exit(1);
            }
        }

        Manipulator[] manipulatorsArray = manipulators.toArray(new Manipulator[0]);
        try {
            JFrame frame = DisplayPanel.createSimpleJFrame(image, manipulatorsArray);
        } catch(HeadlessException e) { // Will happen if no graphic environment is available.
        	boolean[] allEnabled = new boolean[manipulatorsArray.length];
            Arrays.fill(allEnabled, true);
        	
        	String imagePath = CONFIG.get("previewImage");
            BufferedImage previewImage = ImageIO.read(new File(imagePath));
            int height = Integer.parseInt(CONFIG.get("previewHeight"));
            int width = Integer.parseInt(CONFIG.get("previewWidth"));
        	
        	BufferedImage poster = PreviewPanel.getPosterImagePreview(manipulatorsArray, allEnabled, previewImage, height, width);
            ImageIO.write(poster, "jpg", new File("poster.jpg"));

            Manipulator m = Arrays.stream(manipulatorsArray).filter(manipulator -> manipulator.getClass().getName().equals("manipulators."+ CONFIG.get("lastSelected"))).findFirst().get();

            BufferedImage modified = m.transformImage(previewImage, new Random());
            ImageIO.write(modified, "jpg", new File("modified.jpg"));
            
            ConfigUtil.saveToFile();

            System.out.println("Could not load GUI so simply wrote test image to \"modified.jpg\" and a poster to \"poster.jpg\"");
        }
    }

}
