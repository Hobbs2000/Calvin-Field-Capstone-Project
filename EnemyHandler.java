

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/8/2016.
 * Handles a single enemy's values in a separate thread
 * Will manage updating one enemy and checking for collisions
 * A lot of the logic will be here
 */
public class EnemyHandler implements Runnable
{
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

    //Will deal with enemies movements on a different thread
    public void run()
    {
        moverRunning = true;

        while(moverRunning)
        {
            boolean canMoveRight = true;
            Entity rightCollideEntity = null;
            boolean canMoveLeft = true;
            Entity leftCollideEntity = null;
            boolean canMoveDown = true;
            Entity bottomCollideEntity = null;
            boolean canMoveUp = true;
            Entity topCollideEntity = null;

            //Gets a random number that will determine which direction the enemy goes
            int moveHorizontal = (int)(Math.random() * 200);

             for (int i = 0; i < entities.size(); i++)
             {
                 if ((entities.get(i) != this.thisEnemy) && !(entities.get(i) instanceof Enemy) && entities.get(i).isCollidable())
                 {
                     int top1= this.thisEnemy.getY();
                     int bottom1 = this.thisEnemy.getY() + this.thisEnemy.getHeight();
                     int right1 = this.thisEnemy.getX() + this.thisEnemy.getWidth();
                     int left1 = this.thisEnemy.getX();

                     int top2 = entities.get(i).getY();
                     int bottom2 = entities.get(i).getY() + entities.get(i).getHeight();
                     int right2 =entities.get(i).getX() + entities.get(i).getWidth();
                     int left2 = entities.get(i).getX();


                     //If the random value is less than 100, move the enemy right
                     if (moveHorizontal < 100)
                     {
                         canMoveLeft = false;

                         //If the enemy is colliding with any entity it cannot move right
                         if ((canMoveRight) && this.thisEnemy.checkRightCollision(entities.get(i), frameWidth))
                         {
                             canMoveRight = false;
                             rightCollideEntity = entities.get(i);
                         }
                     }
                     //If the random value is greater than 100, move the enemy left
                     else if (moveHorizontal > 100)
                     {
                         canMoveRight = false;

                         //If the enemy is colliding with any entity it cannot move left
                         if ((canMoveLeft) && this.thisEnemy.checkLeftCollision(entities.get(i)))
                         {
                             canMoveLeft = false;
                             leftCollideEntity = entities.get(i);
                         }
                     }
                     

                     //Checks for collision with any entity before moving the enemy down
                     if ((canMoveDown) && this.thisEnemy.checkBottomCollision(entities.get(i), frameHeight, 20))
                     {
                         canMoveDown = false;
                         bottomCollideEntity = entities.get(i);
                     }
                     
                     if ((canMoveUp) && this.thisEnemy.checkTopCollision(entities.get(i)))
                     {
                         canMoveUp = false;
                         topCollideEntity = entities.get(i);
                     }
                 }
             }



            if (canMoveRight)
            {
                this.thisEnemy.moveHorizontal(true);
            }
            if (canMoveLeft)
            {
                this.thisEnemy.moveHorizontal(false);
            }
            
            if (canMoveDown)
            {
                this.thisEnemy.moveDown(20);
            }
   
            //These next two if statements help (but doesn't always) prevent a bug where when the corner of both entities meet causing both entities to become permanently stuck
            //This problem occurs when one moving entity is on top of another, then when the top one falls off it immediately tries to go into the lower entity causing both entities to become stuck
            if ((rightCollideEntity != null) && ((topCollideEntity == rightCollideEntity) || (bottomCollideEntity == leftCollideEntity)) && moveHorizontal < 100)
            {
                this.thisEnemy.moveHorizontal(false);
            }
            else if ((leftCollideEntity != null) && ((topCollideEntity == leftCollideEntity) || (bottomCollideEntity == leftCollideEntity)) && moveHorizontal > 100)
            {
                this.thisEnemy.moveHorizontal(true);
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
