 

import com.sun.prism.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Calvin on 4/11/2016.
 */
public class TestGround extends Entity
{
    private double scale = 3;
    private int width = 100;
    private int height = 16;
    private BufferedImage image;

    public TestGround(int x, int y)
    {
        super(x, y, 50, 16);
        try
        {
            image = ImageIO.read(getClass().getResource("/GroundSprite.jpg"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isCollidable()
    {
        return true;
    }

    public int getWidth()
    {
        return (int)(width * scale);
    }

    public int getHeight()
    {
        return (int)(height * scale);
    }

    public boolean hasAnimation()
    {
        return true;
    }

    public void animate(Graphics g)
    {
        g.drawImage(image, super.getX(), super.getY(), (int)(width*scale), (int)(height*scale), null);
    }
}
