import manipulators.*;
import util.DisplayPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PosterMain {

    public static void main(String[] args) throws IOException {
        String imagePath = "src/imagesources/test_image.png";
        BufferedImage image = ImageIO.read(new File(imagePath));

        Manipulator[] manipulators = new Manipulator[] {
                new ExampleBluifierManipulator(),
                new ExampleGradientManipulator(),
                new ExampleRandomManipulator()
        };


        JFrame frame = DisplayPanel.createSimpleJFrame(image, manipulators);
        frame.setVisible(true);
    }

}
