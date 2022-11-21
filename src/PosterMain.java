import manipulators.*;
import util.DisplayPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Scanner;

public class PosterMain {

    public static void main(String[] args) {
        String[] classes = new String[]{};
        BufferedImage image;
        try {
            String imagePath = "src/imagesources/test_image.png";
            image = ImageIO.read(new File(imagePath));

            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("manipulators/");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();

                    classes = new Scanner((InputStream) url.getContent()).useDelimiter("\\A").next().split("\n");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Manipulator[] manipulators = new Manipulator[classes.length-1];
        int place = 0;
        for (String classname : classes) {
            if (!classname.equals("Manipulator.class")) {
                try {
                    manipulators[place] = (Manipulator) Class.forName("manipulators."+classname.replace(".class", "")).getConstructors()[0].newInstance();
                    System.out.printf("Added \"%s\"\n", classname);
                    place++;
                } catch (Exception e) {
                    System.out.printf("Failed to add \"%s\"\n", classname);
                }
            }

        }


        JFrame frame = DisplayPanel.createSimpleJFrame(image, manipulators);
        frame.setVisible(true);
    }

}
