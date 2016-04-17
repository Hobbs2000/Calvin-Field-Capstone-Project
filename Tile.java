
import java.awt.*;

/**
 * Created by Calvin on 4/16/2016.
 */
public class Tile extends Entity
{

    /**
     *
     * @param x
     * @param y
     */
    public Tile(int x, int y)
    {
        //Each tile is 64 by 64 tiles big
        super(x, y, 64, 64);
    }

    /**
     *
     * @param g
     */
    public void animate(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
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
