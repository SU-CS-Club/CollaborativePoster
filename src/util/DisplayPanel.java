package util;

import manipulators.Manipulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DisplayPanel extends JPanel {

    private final BufferedImage sourceImage;
    private Random random;
    
    private JComboBox<Manipulator> dropDown;
    private JButton randomizeButton;
    private JLabel oldpicLabel;
    private JLabel newPicLabel;

    public DisplayPanel(BufferedImage sourceImage, Manipulator[] manipulators) {
        this.sourceImage = sourceImage;
        this.random = new Random();
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel controlPanel = new JPanel();
        {
            controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            dropDown = new JComboBox<>();
            for (Manipulator m : manipulators) {
                dropDown.addItem(m);
            }
            dropDown.addItemListener(e -> updateImage());
            controlPanel.add(dropDown, 0);

            randomizeButton = new JButton("Generate New Random");
            randomizeButton.addActionListener(e -> random = new Random());
            controlPanel.add(randomizeButton);
        }
        add(controlPanel);

        JPanel imagePanel = new JPanel();
        {
            oldpicLabel = new JLabel();
            oldpicLabel.setIcon(new ImageIcon(sourceImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
            imagePanel.add(oldpicLabel);

            newPicLabel = new JLabel();
            imagePanel.add(newPicLabel);
        }
        add(imagePanel);
        updateImage();
    }

    private void updateImage() {
        Manipulator selected = (Manipulator) dropDown.getSelectedItem();
        BufferedImage newImage = selected.transformImage(sourceImage, random);
        newPicLabel.setIcon(new ImageIcon(newImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
    }

    public static JFrame createSimpleJFrame(BufferedImage sourceImage, Manipulator[] manipulators) {
        JFrame frame = new JFrame("panel");

        frame.add(new DisplayPanel(sourceImage, manipulators));

        frame.setSize(700, 450);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }
}
