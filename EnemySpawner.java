

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/10/2016.
 */
public class EnemySpawner
{

    private ArrayList<Entity> entities;
    private int sleep;

    private boolean zombieSpawing = false;

    Level currentLevel;

    private JFrame frame;

    /**
     *
     * @param startEntities
     */
    public EnemySpawner(ArrayList<Entity> startEntities, Level level)
    {
        currentLevel = level;
        this.entities = startEntities;
    }

    public void startZombieSpawner(JFrame frame)
    {
        this.frame = frame;
        zombieSpawing = true;
        new Thread(() -> zombieSpawner()).start();
    }

    /**
     * Periodically adds a new zombie to the main entity list
     */
    public void zombieSpawner()
    {
        while (zombieSpawing)
        {
            try
            {
                Thread.sleep(2500);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            
            //Repeatidly pick a coordinate and check to see if it will collide with any tiles
            //If not, then spawn the enemy in that location
            boolean hasSpawned = false;
            do
            {
                int x = (int)(Math.random() * frame.getWidth());
                int y = (int)(Math.random() * frame.getHeight());
                
                //TODO: get rid of these magic numbers
                int topRightY = y;
                int topRightX = x + 100;
                
                int bottomLeftY = y + 100;
                int bottomLeftX = x;
                
                int bottomRightY = y + 100;
                int bottomRightX = x + 100;
                
                Tile tile1 = currentLevel.getTile(x, y);
                Tile tile2 = currentLevel.getTile(topRightX, topRightY);
                Tile tile3 = currentLevel.getTile(bottomRightX, bottomRightY);
                Tile tile4 = currentLevel.getTile(bottomLeftX, bottomLeftY);
 
                if ((tile1 != null && tile1.isSolid()) ||
                    (tile2 != null && tile2.isSolid()) ||
                    (tile3 != null && tile3.isSolid()) ||
                    (tile4 != null && tile4.isSolid()))
                {
                    hasSpawned = true;              
                    //Any thread that uses/changes the entities ArrayList must be in a synchronized code block
                    synchronized (entities)
                    {
                        //Adds a new zombie and creates a handler for it
                        entities.add(new Zombie(x, y, 3));
                        new Thread(new EnemyHandler((Enemy)entities.get(entities.size()-1),entities, 50, frame, currentLevel)).start();
                    }
                }
            } while(!hasSpawned);
        }
    }
}
