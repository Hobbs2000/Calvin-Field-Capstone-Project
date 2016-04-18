
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * This will handle the players movements and collision detection
 * Will be on a separate thread
 * Has a keyboard listener class that will use input for player control
 * Most of the logic will be here
 * Created by Calvin on 4/12/2016.
 */
public class PlayerHandler implements Runnable
{

    private ArrayList<Entity> entities;
    private int sleep;
    private boolean running;

    //This stuff is for jumping 
    int yVelocity = -80;
    int GRAVITY = 20;
    boolean jumping = false;

    private Player thisPlayer;
    private Controls controls;

    private Level currentLevel;

    public int frameWidth, frameHeight;

    /**
     *
     * @param player
     * @param entityList
     * @param frame
     */
    public PlayerHandler(Player player, ArrayList<Entity> entityList, int sleep, JFrame frame, Controls controls, Level level)
    {
        this.thisPlayer = player;
        this.entities = entityList;
        this.controls = controls;
        this.sleep = sleep;
        this.currentLevel = level;
        this.frameWidth = frame.getWidth();
        this.frameHeight = frame.getHeight();
    }

    /**
     *
     */
    public void run()
    {
        running = true;


        while(running)
        {

            //Get the key/s pressed
            controls.update();

            if (controls.space)
            {
                jumping = true;
            }
            if (jumping)
            {
                if (yVelocity < 0)
                {
                    movePlayerUp(-1*yVelocity);
                    yVelocity += GRAVITY;
                }
                else if (yVelocity >= 0)
                {
                    movePlayerDown(yVelocity);
                    
                    //If the player is moving faster than the size of a tile, it would clip through the tile
                    if (yVelocity >= 63)
                    {
                        yVelocity = 63;
                    }
                    else
                    {
                        yVelocity += GRAVITY;
                    }
                }
            }

            //Move down because of gravity
            if (!jumping)
            {
                movePlayerDown(40);
            }


            //Will actually move the player based on collisions and what keys were pressed
            if (controls.right)
            {
                movePlayerRight(this.thisPlayer.getSpeed());
            }
            if (controls.left)
            {
                movePlayerLeft(this.thisPlayer.getSpeed());
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
     *
     * @param dx
     */
    public void movePlayerRight(int dx)
    {
        //Top corner right y
        int tcRightY = this.thisPlayer.getY();
        //Top corner right x
        int tcRightX = this.thisPlayer.getX() + thisPlayer.getWidth();

        //Bottom corner right y
        int bcRightY = this.thisPlayer.getY() + this.thisPlayer.getHeight();
        //Bottom corner right x
        int bcRightX = tcRightX;

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int rMiddleY = this.thisPlayer.getY() + (this.thisPlayer.getHeight() / 2);
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
            this.thisPlayer.setX(this.thisPlayer.getX() + dx);
        }
    }

    /**
     *
     * @param dx
     */
    public void movePlayerLeft(int dx)
    {
        //Top left corner left y
        int tcLeftY = this.thisPlayer.getY();
        //Top corner right x
        int tcLeftX = this.thisPlayer.getX();

        //Bottom left corner right y
        int bcLeftY = this.thisPlayer.getY() + thisPlayer.getHeight();
        //Bottom corner right x
        int bcLeftX = this.thisPlayer.getX();

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int lMiddleY = this.thisPlayer.getY() + (this.thisPlayer.getHeight() / 2);
        //Bottom middle x
        int lMiddleX = this.thisPlayer.getX();

        Tile tile1 = currentLevel.getTile(tcLeftX - dx, tcLeftY);
        Tile tile2 = currentLevel.getTile(bcLeftX - dx, bcLeftY);
        Tile tile3 = currentLevel.getTile(lMiddleX - dx, lMiddleY);

        if ((tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()))
        {
            return;
        }
        else
        {
            this.thisPlayer.setX(this.thisPlayer.getX() - dx);
        }
    }

    /**
     *
     * @param dy
     */
    public void movePlayerDown(int dy)
    {
        //Bottom corner left y
        int bcLeftY = this.thisPlayer.getY() + this.thisPlayer.getHeight();

        //Bottom corner right y
        int bcRightY = bcLeftY;
        //Bottom corner right x
        int bcRightX = this.thisPlayer.getX() + this.thisPlayer.getWidth();

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int bMiddleY = bcLeftY;
        //Bottom middle x
        int bMiddleX = this.thisPlayer.getX() + (this.thisPlayer.getWidth() / 2);

        Tile tile1 = currentLevel.getTile(this.thisPlayer.getX(), bcLeftY + dy);
        Tile tile2 = currentLevel.getTile(bcRightX, bcRightY + dy);
        Tile tile3 = currentLevel.getTile(bMiddleX, bMiddleY + dy);

        if ((tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()))
        {
            jumping = false;
            yVelocity = -80;
            return;
        }
        else
        {
            this.thisPlayer.setY(this.thisPlayer.getY() + dy);
        }
    }

    /**
     *
     * @param dy
     */
    public void movePlayerUp(int dy)
    {
        //Top corner left y
        int tcLeftY = this.thisPlayer.getY();
        //Top corner left x
        int tcLeftX = this.thisPlayer.getX();

        //Bottom corner right y
        int tcRightY = tcLeftY;
        //Bottom corner right x
        int tcRightX = this.thisPlayer.getX() + this.thisPlayer.getWidth();

        //Testing the middle point is only neccesary if the player is wider than a standard tile
        //Bottom middle y
        int tMiddleY = tcLeftY;
        //Bottom middle x
        int tMiddleX = this.thisPlayer.getX() + (this.thisPlayer.getWidth() / 2);

        Tile tile1 = currentLevel.getTile(tcLeftX, tcLeftY - dy);
        Tile tile2 = currentLevel.getTile(tcRightX, tcRightY - dy);
        Tile tile3 = currentLevel.getTile(tMiddleX, tMiddleY - dy);
        if ((tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()))
        {
            return;
        }
        else
        {
            this.thisPlayer.setY(this.thisPlayer.getY() - dy);
        }
    }

    /**
     * Stops the run loop which ends the thread
     */
    public void stop()
    {
        running = false;
    }

}
