

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

    private Level currentLevel;
    
    private Enemy thisEnemy;

    public int frameWidth, frameHeight;

    /**
     *
     */
    public EnemyHandler(Enemy handledEnemy, ArrayList<Entity> allEntities, int sleep, JFrame currentFrame, Level level)
    {
        //this.enemyList = enemies;
        this.entities = allEntities;
        this.thisEnemy = handledEnemy;
        this.sleep = sleep;
        this.frameWidth = currentFrame.getWidth();
        this.frameHeight = currentFrame.getHeight();
        this.currentLevel = level;
    }

    //Will deal with enemies movements on a different thread
    public void run()
    {
        moverRunning = true;

        while(moverRunning)
        {
           
            //Gets a random number that will determine which direction the enemy goes
            int moveHorizontal = (int)(Math.random() * 200);

            if (moveHorizontal < 100)
            {
       
                moveEnemyRight(10);
            }
            else if (moveHorizontal > 100)
            {
                moveEnemyLeft(10);
            }
            
            moveEnemyDown(20);
            
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
     *
     * @param dx
     */
    public void moveEnemyRight(int dx)
    {
        //Top corner right y
        int tcRightY = this.thisEnemy.getY();
        //Top corner right x
        int tcRightX = this.thisEnemy.getX() + this.thisEnemy.getWidth();

        //Bottom corner right y
        int bcRightY = this.thisEnemy.getY() + this.thisEnemy.getHeight();
        //Bottom corner right x
        int bcRightX = tcRightX;

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int rMiddleY = this.thisEnemy.getY() + (this.thisEnemy.getHeight() / 2);
        //Bottom middle x
        int rMiddleX = tcRightX;
 
        Tile tile1 = currentLevel.getTile(tcRightX + dx, tcRightY);
        Tile tile2 = currentLevel.getTile(bcRightX + dx, bcRightY);
        Tile tile3 = currentLevel.getTile(rMiddleX + dx, rMiddleY);

        if ((tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()))
        {
            return;
        }
        else
        {
            this.thisEnemy.setX(this.thisEnemy.getX() + dx);
        }
    }

    /**
     *
     * @param dx
     */
    public void moveEnemyLeft(int dx)
    {
        //Top left corner left y
        int tcLeftY = this.thisEnemy.getY();
        //Top corner right x
        int tcLeftX = this.thisEnemy.getX();

        //Bottom left corner right y
        int bcLeftY = this.thisEnemy.getY() + this.thisEnemy.getHeight();
        //Bottom corner right x
        int bcLeftX = this.thisEnemy.getX();

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int lMiddleY = this.thisEnemy.getY() + (this.thisEnemy.getHeight() / 2);
        //Bottom middle x
        int lMiddleX = this.thisEnemy.getX();

        Tile tile1 = currentLevel.getTile(tcLeftX - dx, tcLeftY);
        Tile tile2 = currentLevel.getTile(bcLeftX - dx, bcLeftY);
        Tile tile3 = currentLevel.getTile(lMiddleX - dx, lMiddleY);

        if ((tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()))
        {
            return;
        }
        else
        {
            this.thisEnemy.setX(this.thisEnemy.getX() - dx);
        }
    }

    /**
     *
     * @param dy
     */
    public void moveEnemyDown(int dy)
    {
        //Bottom corner left y
        int bcLeftY = this.thisEnemy.getY() + this.thisEnemy.getHeight();

        //Bottom corner right y
        int bcRightY = bcLeftY;
        //Bottom corner right x
        int bcRightX = this.thisEnemy.getX() + this.thisEnemy.getWidth();

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int bMiddleY = bcLeftY;
        //Bottom middle x
        int bMiddleX = this.thisEnemy.getX() + (this.thisEnemy.getWidth() / 2);

        Tile tile1 = currentLevel.getTile(this.thisEnemy.getX(), bcLeftY + dy);
        Tile tile2 = currentLevel.getTile(bcRightX, bcRightY + dy);
        Tile tile3 = currentLevel.getTile(bMiddleX, bMiddleY + dy);

        if ((tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()))
        {
            return;
        }
        else
        {
            this.thisEnemy.setY(this.thisEnemy.getY() + dy);
        }
    }

    /**
     *
     * @param dy
     */
    public void moveEnemyUp(int dy)
    {
        //Top corner left y
        int tcLeftY = this.thisEnemy.getY();
        //Top corner left x
        int tcLeftX = this.thisEnemy.getX();

        //Bottom corner right y
        int bcRightY = tcLeftY;
        //Bottom corner right x
        int bcRightX = this.thisEnemy.getX() + this.thisEnemy.getHeight();

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int bMiddleY = tcLeftY;
        //Bottom middle x
        int bMiddleX = this.thisEnemy.getX() + (this.thisEnemy.getWidth() / 2);

        Tile tile1 = currentLevel.getTile(tcLeftX, tcLeftY - dy);
        Tile tile2 = currentLevel.getTile(bcRightX, bcRightY - dy);
        Tile tile3 = currentLevel.getTile(bMiddleX, bMiddleY - dy);
        if ((tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()))
        {
            return;
        }
        else
        {
            this.thisEnemy.setY(this.thisEnemy.getY() - dy);
        }
    }


    //Ends all threads for the handler
    public void stopAll()
    {
        moverRunning= false;
    }
}
