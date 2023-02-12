package util;

import manipulators.Manipulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

import static util.ConfigUtil.CONFIG;

public class PreviewPanel extends JPanel {

    private final BufferedImage sourceImage;
    private final JLabel bigPicLabel;
    private final JTextField heightField;
    private final JTextField widthField;

    private boolean[] enabled;

    public PreviewPanel(BufferedImage sourceImage, Manipulator[] manipulators) {
        this.sourceImage = sourceImage;
        this.enabled = new boolean[manipulators.length];

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
            JPanel sizePanel = new JPanel();
            sizePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            sizePanel.setPreferredSize(new Dimension(300, 90));
            {
                heightField = new JTextField(CONFIG.get("previewHeight"));
                heightField.setPreferredSize(new Dimension(30, 30));
                heightField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        CONFIG.put("previewHeight", heightField.getText());
                        try {
                            updateImage(manipulators);
                        } catch (Exception ignored) {}
                    }
                });
                sizePanel.add(heightField);
                sizePanel.add(new Label("   by"));
                widthField = new JTextField(CONFIG.get("previewWidth"));
                widthField.setPreferredSize(new Dimension(30, 30));
                widthField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        CONFIG.put("previewWidth", widthField.getText());
                        try {
                            updateImage(manipulators);
                        } catch (Exception ignored) {}
                    }
                });
                sizePanel.add(widthField);
            }
            rightPanel.add(sizePanel);

            JPanel manipulatorPanel = new JPanel();
            manipulatorPanel.setPreferredSize(new Dimension(300, Math.max(610, manipulators.length*20)));
            {
                for (int i = 0; i < manipulators.length; i++) {
                    JCheckBoxMenuItem item = new JCheckBoxMenuItem(manipulators[i].toString());
                    item.setPreferredSize(new Dimension(300, 20));
                    item.setState(true);
                    int itemID = i;
                    item.addActionListener(e -> {
                        updateImage(itemID, item.getState(), manipulators);
                    });
                    enabled[i] = true;
                    manipulatorPanel.add(item);
                }
                manipulatorPanel.setAutoscrolls(true);
            }
            rightPanel.add(new JScrollPane(manipulatorPanel, ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER));
        }
        add(rightPanel);
        updateImage(manipulators);
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

    private void updateImage(Manipulator[] manipulators) {
        updateImage(0, enabled[0], manipulators);
    }

    private void updateImage(int changed, boolean newState, Manipulator[] manipulators) {
        enabled[changed] = newState;
        boolean atLeastOneSelected = false;
        for (boolean b : enabled) {
            if (b) {
                atLeastOneSelected = true;
                break;
            }
        }
        if (atLeastOneSelected) {
            int step = -1;
            int filled = 0;
            int height = Integer.parseInt(heightField.getText());
            int width = Integer.parseInt(widthField.getText());
            int total = height * width;
            BufferedImage[] images = ImageUtil.splitImage(sourceImage, height, width);

            while (filled < total) {
                step = (step+1)%(enabled.length);
                while (!enabled[step]) step = (step+1)%(enabled.length);
                images[filled] = manipulators[step].transformImage(images[filled], new Random());
                filled++;
            }

            BufferedImage modifiedImage = ImageUtil.mergeImages(images, height, width, sourceImage.getWidth(), sourceImage.getHeight());

            bigPicLabel.setIcon(new ImageIcon(modifiedImage.getScaledInstance(600, 600, Image.SCALE_SMOOTH)));
        } else {
            bigPicLabel.setIcon(new ImageIcon(sourceImage.getScaledInstance(600, 600, Image.SCALE_SMOOTH)));
        }
    }
}
