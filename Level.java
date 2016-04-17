 

import java.awt.*;

/**
 * Created by Calvin on 4/17/2016.
 */
public class Level
{

    private Tile[][] worldTiles;

    /**
     *
     */
    public Level()
    {


        //Each number in the code array represents a different type of tile
        int[][] code = {
                {1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,1,1,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1}
        };
        //This turns the simple list of ints into a list of tiles
        worldTiles = Level.interpretWorld(code);
    }

    /**
     *
     * @param g
     */
    public void drawWorld(Graphics g)
    {
        for (int r = 0; r < worldTiles.length; r++)
        {
            for (int c = 0; c < worldTiles[r].length; c++)
            {
                if (worldTiles[r][c] != null)
                {
                    worldTiles[r][c].animate(g);
                }
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Tile getTile(int x, int y)
    {
        for (int r = 0; r < worldTiles.length; r++)
        {
            for (int c = 0; c < worldTiles[r].length; c++)
            {
                if (worldTiles[r][c] != null)
                {
                    int right = worldTiles[r][c].getX() + worldTiles[r][c].getWidth();
                    int bottom = worldTiles[r][c].getY() + worldTiles[r][c].getHeight();

                    if (x > worldTiles[r][c].getX() && x < right && y > worldTiles[r][c].getY() && y < bottom)
                    {
                        return worldTiles[r][c];
                    }
                }
            }
        }

        return null;
    }

    /**
     *
     * @param code
     * @return
     */
    public static Tile[][] interpretWorld(int[][] code)
    {
        Tile[][] worldTiles = new Tile[code.length][code[0].length];

        for (int r = 0; r < code.length; r++)
        {
            for (int c = 0; c < code[r].length; c++)
            {
                int tileInt = code[r][c];

                //Will have more blocks eventually
                if (tileInt == 1)
                {
                    worldTiles[r][c] = new Tile((c*64), (r*64));
                }
                else
                {
                    worldTiles[r][c] = null;
                }
            }
        }

        return worldTiles;
    }

}
