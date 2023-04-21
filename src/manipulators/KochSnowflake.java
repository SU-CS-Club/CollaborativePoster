package manipulators;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;


/**
 * Manipulator that creates the Koch Snowflake.
 * 
 * @author Caleb Highsmith
 *
 */
 
public class KochSnowflake extends Manipulator {
 
    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
       
        int height;
        int width;
        int level = 3;
        Graphics g = image.createGraphics();
       
        g.setColor(Color.RED);
        height = image.getHeight() - image.getHeight()/4;
        width = image.getWidth();
        int xStart = width/2 - height/2;
        drawSnow(g, level, xStart + 20,             height - 20,   xStart + height - 20, height - 20);
        drawSnow(g, level, xStart + height - 20,    height - 20,   xStart + height/2,    20);
        drawSnow(g, level, xStart + height/2,       20,            xStart + 20,          height - 20);

        Color c = new Color(image.getRGB(x,y));

        int[] RGB = new int[3];

        RGB[0] = c.getRed();
        RGB[1] = c.getGreen();
        RGB[2] = c.getBlue();

        return new Color(255-RGB[0],255-RGB[1],255-RGB[2]);
 
    }
 
    private void drawSnow (Graphics g, int lev, int x1, int y1, int x5, int y5){
          int deltaX, deltaY, x2, y2, x3, y3, x4, y4;
 
          if (lev == 0){
              g.drawLine(x1, y1, x5, y5);
          }
          else{
                deltaX = x5 - x1;
                deltaY = y5 - y1;
 
                x2 = x1 + deltaX / 3;
                y2 = y1 + deltaY / 3;
 
                x3 = (int) (0.5 * (x1+x5) + Math.sqrt(3) * (y1-y5)/6);
                y3 = (int) (0.5 * (y1+y5) + Math.sqrt(3) * (x5-x1)/6);
 
                x4 = x1 + 2 * deltaX /3;
                y4 = y1 + 2 * deltaY /3;
 
                drawSnow (g,lev-1, x1, y1, x2, y2);
                drawSnow (g,lev-1, x2, y2, x3, y3);
                drawSnow (g,lev-1, x3, y3, x4, y4);
                drawSnow (g,lev-1, x4, y4, x5, y5);
            }
        }
}
 

