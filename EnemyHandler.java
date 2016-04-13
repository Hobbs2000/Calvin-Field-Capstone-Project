

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/8/2016.
 * Handles a single enemy's values in a separate thread
 * Will manage updating one enemy and checking for collisions
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
            Entity rightCollidedEntity = null;
            boolean canMoveLeft = true;
            Entity leftCollideEntity = null;
            boolean canMoveUp = true;
            boolean canMoveDown = true;

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
                         }
                     }

                     //Checks for collision with any entity before moving the enemy down
                     if ((canMoveDown) && this.thisEnemy.checkBottomCollision(entities.get(i), frameHeight, 20))
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
                this.thisEnemy.moveDown(20);
            }

            //These next two if statements help (but don't always) prevent a bug where when the corner of both entities meet causing both entities to become permanently stuck
            if ((canMoveRight == false) && (moveHorizontal < 100) && (rightCollidedEntity != null) && (Math.abs(((this.thisEnemy.getX() + this.thisEnemy.getWidth()) - rightCollidedEntity.getX())) == 1))
            {
                System.out.println("Case 1");
                this.thisEnemy.moveHorizontal(true);
            }
            if ((canMoveLeft == false) && (moveHorizontal > 100) && (leftCollideEntity != null) && (Math.abs((this.thisEnemy.getX() - (leftCollideEntity.getX() + leftCollideEntity.getWidth()))) == 1))
            {
                System.out.println("Case 2");
                this.thisEnemy.moveHorizontal(false);
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
