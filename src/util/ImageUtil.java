package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

/**
 * Helper class to handle the image manipulation
 * required to create the mosaic poster (image
 * splitting and merging). Was a lot of code that
 * didn't need to be in the PreviewPanel class.
 *
 * @author Maxx Batterton
 */
public class ImageUtil {

    public static BufferedImage[] splitImage(BufferedImage originalImage, int rows, int columns) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        BufferedImage[] images = new BufferedImage[rows*columns];

        double xSize = (((double) originalWidth)/((double) columns));
        double ySize = (((double) originalHeight)/((double) rows));

        int step = 0;
        for (int c = 0; c < rows; c++) {
            for (int r = 0; r < columns; r++) {
                int newImageXStart = (int) Math.round((r)*xSize);
                int newImageXEnd = (int) Math.round((r+1)*xSize);
                int newImageYStart = (int) Math.round((c)*ySize);
                int newImageYEnd = (int) Math.round((c+1)*ySize);
                int newWidth = newImageXEnd-newImageXStart;
                int newHeight = newImageYEnd-newImageYStart;

                images[step] = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                Graphics2D newGraphics = images[step].createGraphics();
                newGraphics.drawImage(originalImage, 0, 0, newWidth, newHeight, newImageXStart, newImageYStart, newImageXEnd, newImageYEnd, null);
                step++;
            }
        }

        return images;
    }

    public static BufferedImage mergeImages(BufferedImage[] imageArray, int rows, int columns, int originalWidth, int originalHeight) {
        BufferedImage mergedImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D mergedGraphics = mergedImage.createGraphics();

        double xSize = (((double) originalWidth)/((double) columns));
        double ySize = (((double) originalHeight)/((double) rows));

        int step = 0;
        for (int c = 0; c < rows; c++) {
            for (int r = 0; r < columns; r++) {
                int newImageXStart = (int) Math.round((r)*xSize);
                int newImageXEnd = (int) Math.round((r+1)*xSize);
                int newImageYStart = (int) Math.round((c)*ySize);
                int newImageYEnd = (int) Math.round((c+1)*ySize);

                mergedGraphics.drawImage(imageArray[step], newImageXStart, newImageYStart, newImageXEnd, newImageYEnd, 0, 0, imageArray[step].getWidth(), imageArray[step].getHeight(), null);
                step++;
            }
        }

        return mergedImage;
    }

    public static void saveOutputFile(BufferedImage image) {
        try {
            Path outputPath = Path.of("output");
            if (!Files.exists(outputPath)) Files.createDirectory(outputPath);
            File outputFile = new File("output/poster_output "+getFileSafeDate()+".jpg");
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException ignored) {
            System.out.println(ignored);
        }
    }

    public static String getFileSafeDate() {
        return LocalDateTime.now().toString().split("\\.")[0].replace(":", "-").replace("T", " ");
    }
}
