package util;

import manipulators.Manipulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;

import static util.ConfigUtil.CONFIG;

public class PreviewPanel extends JPanel {

    private final BufferedImage sourceImage;
    private final JLabel bigPicLabel;

    public PreviewPanel(BufferedImage sourceImage, Manipulator[] manipulators) {
        this.sourceImage = sourceImage;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(700, 700));
        {
            bigPicLabel = new JLabel();
            bigPicLabel.setIcon(new ImageIcon(sourceImage.getScaledInstance(600, 600, Image.SCALE_SMOOTH)));
            leftPanel.add(bigPicLabel);
        }
        add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(300, 700));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        {

        }
        add(rightPanel);

    }

    public static void createSimpleJFrame(Manipulator[] manipulators, WindowAdapter closeBehavior) {
        JFrame frame = new JFrame("Preview Panel");

        BufferedImage image;
        try {
            String imagePath = CONFIG.get("previewImage");
            image = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PreviewPanel main = new PreviewPanel(image, manipulators);
        frame.add(main);

        frame.setSize(1000, 700);

        frame.addWindowListener(closeBehavior);

        frame.setVisible(true);
    }
}
