

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
            
            do
            {
                int x = Math.random() * frame.getWidth();
                int y = Math.random() * frame.getHeight();
                
                //TODO: get rid of these magic numbers
                int topRightY = y;
                int topRightX = x + 64;
                
                int bottomLeftY = y + 64;
                int bottomLeftX = x;
                
                int bottomRightY = y + 64;
                int bottomRightX = x + 64;
                
                Tile tile1 = currentLevel.getTile(x, y);
                Tile tile2 = currentLevel.getTile(topRightX, topRightY);
                Tile tile3 = currentLevel.getTile(bottomLeftX, bottomLeftY);
                Tile tile4 = currentLevel.getTile(bottomLeftX, bottomLeftY);
            } while();
                

            //Any thread that uses/changes the entities ArrayList must be in a synchronized code block
            synchronized (entities)
            {
                //Adds a new zombie and creates a handler for it
                entities.add(new Zombie(230, 90, 3));
                new Thread(new EnemyHandler((Enemy)entities.get(entities.size()-1),entities, 50, frame, currentLevel)).start();
            }
        }
    }
}
