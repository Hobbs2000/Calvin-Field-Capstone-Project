

import java.awt.*;

/**
 * Created by Calvin on 4/17/2016.
 * This is what the player and all entities will be in
 */
public class Level
{
    //This is all of the world tiles
    //They are arranged in how they will be displayed on screen
    private Tile[][] worldTiles;

    /**
     *
     */
    public Level()
    {
        createWorld();
    }

    /**
     * Generates a 2d array of ints, 
     * and each int in the array stands for a different tile, and each
     * value determines which tile will be in that position.
     * After the 2d int array is created, it is passed into the interperetWorld method
     */
    public void createWorld()
    {
        //Each number in the code array represents a different type of tile
        int[][] code = {
                {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,1,1,1,1,1,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,1,2,2,2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,2},
                {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,1,1,1,1,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2}
        };
        //This turns the simple list of ints into a list of tiles
        worldTiles = Level.interpretWorld(code);
    }

    /**
     * Draws all the tiles that are not null on the screen
     * @param g The graphics context
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
     * Gets the tile at the specified coordinates
     * @param x The x coordinate (not the col index) 
     * @param y The y coordinate (not the row index)
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
     * Creates and returns a 2d array of Tiles, which are then initialized based on what values are in 
     * the 2d array of ints code
     * @param code A 2d array of ints used to determine what will be put in the Tiles array
     * @return worldTiles - A 2d array of Tiles 
     */
    public static Tile[][] interpretWorld(int[][] code)
    {
        Tile[][] worldTiles = new Tile[code.length][code[0].length];

        for (int r = 0; r < code.length; r++)
        {
            for (int c = 0; c < code[r].length; c++)
            {
                int tileInt = code[r][c];

                switch (tileInt)
                {
                    case 1:
                        worldTiles[r][c] = new Tile((c*32), (r*32));
                        break;
                    case 2:
                        worldTiles[r][c] = new Grass((c*32), (r*32));
                        break;
                    default:
                        worldTiles[r][c] = null;
                        break;
                }
            }
        }

        return worldTiles;
    }

}
