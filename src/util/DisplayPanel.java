package util;

import manipulators.Manipulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.*;

import static util.ConfigUtil.CONFIG;

/**
 * Class laying out the GUI to display images with
 * the manipulator selector and randomizer.
 *
 * @author Maxx Batterton
 */
public class DisplayPanel extends JPanel {

    private final BufferedImage sourceImage;
    private Random random;
    
    private final JComboBox<Manipulator> dropDown;
    private final JButton randomizeButton;
    private final JButton previewButton;
    private final JLabel oldpicLabel;
    private final JLabel newPicLabel;

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
            randomizeButton.addActionListener(e -> {
                random = new Random();
                updateImage();
            });
            controlPanel.add(randomizeButton);

            previewButton = new JButton("Preview Big Image");
            previewButton.addActionListener(e -> {

            });
            controlPanel.add(previewButton);
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
        loadData(manipulators);

        updateImage();
    }

    private void updateImage() {
        Manipulator selected = (Manipulator) dropDown.getSelectedItem();
        CONFIG.put("lastSelected", dropDown.getSelectedItem().toString());
        BufferedImage newImage = selected.transformImage(sourceImage, random);
        newPicLabel.setIcon(new ImageIcon(newImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
    }

    public void loadData(Manipulator[] manipulators) {
        Optional<Manipulator> m = Arrays.stream(manipulators).filter(manipulator -> manipulator.getClass().getName().equals("manipulators."+ CONFIG.get("lastSelected"))).findFirst();
        if (m.isPresent()) {
            dropDown.setSelectedItem(m.get());
        }

        // Extensible here
    }

    public static JFrame createSimpleJFrame(BufferedImage sourceImage, Manipulator[] manipulators) {
        JFrame frame = new JFrame("panel");

        DisplayPanel main = new DisplayPanel(sourceImage, manipulators);
        frame.add(main);

        frame.setSize(700, 450);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                ConfigUtil.saveToFile();
            }
        });
        return frame;
    }

}
