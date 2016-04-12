 

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Calvin on 4/8/2016.
 * Will
 */
public class Sprite
{
    private BufferedImage image;
    private int width, height;

    /**
     *
     * @param spriteSheet
     * @param xStart
     * @param yStart
     * @param width
     * @param height
     * @param scale
     */
    public Sprite(BufferedImage spriteSheet, int xStart, int yStart, int width, int height, double scale)
    {
        if (spriteSheet != null)
        {
            //All this creates a scaled sub image from the spriteSheet image
            //Width and height scaled using the scale param
            //image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image = spriteSheet.getSubimage(xStart, yStart, width, height);
            this.width = width;
            this.height = height;
            //Graphics2D g = image.createGraphics();
            //AffineTransform trans = AffineTransform.getScaleInstance(scale, scale);
            //g.drawRenderedImage(spriteSheet.getSubimage(xStart, yStart, width, height), trans);
        }
    }

    /**
     *
     * @return
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     *
     * @return
     */
    public int getWidth()
    {
        return width;
    }

    /**
     *
     * @return
     */
    public int getHeight()
    {
        return height;
    }
}
