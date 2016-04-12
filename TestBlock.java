 

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Calvin on 4/9/2016.
 */
public class TestBlock extends Entity
{
    private double scale = 3;
    private int width = 16;
    private int height = 16;
    private Animation staticAnimation;

    public TestBlock(int x, int y)
    {
        super(x, y, 16, 16);
        try
        {
            staticAnimation = new Animation(ImageIO.read(getClass().getResource("/Single 16 Pix Block.jpg")), 16, 16, 1, super.getX(), super.getY(), this.scale, 2);
        }
        catch(IOException e)
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
        staticAnimation.draw(g);
    }
}
