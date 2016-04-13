

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/8/2016.
 * Handles a single enemy's values in a separate thread
 * Will manage updating one enemy and checking for collisions
 */
public class EnemyHandler implements Runnable
{
    //private ArrayList<Enemy> enemyList;
    private ArrayList<Entity> entities;
    private int sleep;

    public boolean running = false;

    private Enemy thisEnemy;

    public int frameWidth, frameHeight;

    /**
     * @param handledEnemy The single enemy meant to be handled 
     * @param allEntities The shared list of all entities 
     * @param sleep The time in milliseconds for the program to pause
     * @param currentFrame The frame that the handledEnemy is in 
     */
    public EnemyHandler(Enemy handledEnemy, ArrayList<Entity> allEntities, int sleep, JFrame currentFrame)
    {
        //this.enemyList = enemies;
        this.entities = allEntities;
        this.thisEnemy = handledEnemy;
        this.sleep = sleep;
        this.frameWidth = currentFrame.getWidth();
        this.frameHeight = currentFrame.getHeight();
    }


    /**
     *  Decides what movement the enemy will do then proceeds to check for collisions
     *  If no collisions in the direction it wants to go, move the enemy
     */
    public void run()
    {
        running = true;

        while(running)
        {
            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            boolean canMoveUp = true;
            boolean canMoveDown = true;

            //This block of code does not need to be synchronized since it is not actually changing entities
            int moveHorizontal = (int)(Math.random() * 200);
            for (int i = 0; i < entities.size(); i++)
            {
                if ((entities.get(i) != this.thisEnemy) && !(entities.get(i) instanceof Enemy))
                {
                    if (moveHorizontal < 100)
                    {
                        canMoveLeft = false;
                        if ((canMoveRight) && this.thisEnemy.checkRightCollision(entities.get(i), frameWidth))
                        {
                            canMoveRight = false;
                        }
                    }
                    else if (moveHorizontal > 100)
                    {
                        canMoveRight = false;
                        if ((canMoveLeft) && this.thisEnemy.checkLeftCollision(entities.get(i)))
                        {
                            canMoveLeft = false;
                        }
                    }
                    if ((canMoveDown) && this.thisEnemy.checkBottomCollision(entities.get(i), frameHeight))
                    {
                        canMoveDown = false;
                    }
                }
            }


            if (canMoveRight)
            {
                this.thisEnemy.moveHorizontal(true);
            }
            else if (canMoveLeft)
            {
                this.thisEnemy.moveHorizontal(false);
            }
            if (canMoveDown)
            {
                this.thisEnemy.moveDown(5);
            }

            try
            {
                Thread.sleep(this.sleep);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Makes running equal false, ending the loop in run() and effectivly ending the thread
     */
    public void stop()
    {
        running= false;
    }
}
