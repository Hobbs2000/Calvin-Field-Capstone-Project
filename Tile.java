

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Calvin on 4/16/2016.
 * The basic tile that all other tiles will extend off of
 */
public class Tile extends Entity
{

    private BufferedImage sprite;

    /**
     *
     * @param x
     * @param y
     */
    public Tile(int x, int y)
    {
        //Each tile is 64 by 64 tiles big
        super(x, y, 32, 32);

        try
        {
            sprite = ImageIO.read(getClass().getResource("/TileSheet1.png"));
            sprite = sprite.getSubimage(0,0, 32, 32);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param newSprite
     */
    public void setSprite(BufferedImage newSprite)
    {
        sprite = newSprite;
    }

    /**
     *
     * @param g
     */
    public void animate(Graphics g)
    {
        //g.setColor(Color.WHITE);
        //g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.drawImage(sprite, getX(), getY(), getWidth(), getHeight(), null);
    }

    /**
     *
     * @param row
     * @param col
     */
    public void setLoc(int row, int col)
    {
        setX(getWidth() * col);
        setY(getHeight() * row);
    }

    /**
     * The default tile is solid
     * @return true
     */
    public boolean isSolid()
    {
        return true;
    }
}
