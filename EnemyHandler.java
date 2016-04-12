

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

    public boolean moverRunning = false;

    private Enemy thisEnemy;

    public int frameWidth, frameHeight;

    /**
     *
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

    /*
    public void startMover(int time)
    {
        this.sleep = time;
        moverRunning = true;
        new Thread(() -> run()).start();
    }
    */


    //Will deal with enemies movements on a different thread
    public void run()
    {
        moverRunning = true;

        while(moverRunning)
        {
            boolean canMoveRight = true;
            boolean canMoveLeft = true;
            boolean canMoveUp = true;
            boolean canMoveDown = true;

            //Any thread that uses/changes the entities ArrayList must be in a synchronized code block

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


    //Ends all threads for the handler
    public void stopAll()
    {
        moverRunning= false;
    }
}
