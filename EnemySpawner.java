

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
