

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/12/2016
 * This will handle the players movements and collision detection
 * Will be on a separate thread
 * Has a keyboard listener class that will use input for player control
 * Most of the logic will be here
 */
public class PlayerHandler implements Runnable
{

    private ArrayList<Entity> entities;
    private int sleep;
    private boolean running;

    //This stuff is for jumping
    //yVelocity is the speed at which the player rises and falls
    int yVelocity = -20;
    //This is what the yVelocity will be reset to when a jumping sequence is over
    int maxY_Velocity = -20;
    //Gravity is what is added to yVelocity for making a parabola like jump
    int GRAVITY = 2;
    boolean jumping = false;

    //The time in between the player taking damage
    //The actual amount of time is sleep * invulnerableTime
    int invulnerableTime = 50;

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
     * Handles all player stuff
     * Includes handling actions such as moving and collisions
     */
    public void run()
    {
        running = true;


        while(running)
        {
            //Get the key/s pressed for this loop
            controls.update();

            //Go through all entities and check for collisions
            //This is how the player is damaged
            for (int i = 0; i < entities.size(); i++)
            {
                boolean hasCollided = Collision.collided(this.thisPlayer, entities.get(i));
                //Check to see if the entity is an enemy and the player's invulnerability time is up
                if (entities.get(i) instanceof Enemy && hasCollided && invulnerableTime <= 0)
                {
                    Enemy enemy = (Enemy)entities.get(i);
                    this.thisPlayer.health -= enemy.getDamage();
                    invulnerableTime = 50;
                }
            }
            invulnerableTime--;

            //Checks to see if the space key was pressed, which starts the jumping sequence by setting jumping to true
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
                    if (yVelocity >= 31)
                    {
                        yVelocity = 31;
                    }
                    else
                    {
                        yVelocity += GRAVITY;
                    }
                }
            }

            //Move down because of gravity
            //This is for regular falling, not jump falling, that is why it only executes when the player is not jumping
            if (!jumping)
            {
                movePlayerDown(10);
            }


            //Move the player based on collisions and what keys were pressed
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
     * Gets the coordinates of 3 points along the right side of the bounding box of the player (top right, middle right, bottom right)
     * Checks to see if any of those points would collide with a solid tile if the player were to move dx to the right
     * If no collision, the player will be move to the new coordinates
     * @param dx The amount to move player to the right 
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
        else //If no collision, then actually move player to the right by dx amount
        {
            this.thisPlayer.setX(this.thisPlayer.getX() + dx);
        }
    }

    /**
     * Gets the coordinates of 3 points along the left side of the player's bounding box (top left, middle left, bottom left)
     * Checks to see if any of those points would collide with a solid tile if the player were to move dx amount to the left
     * If no collision, the player will be move to the new coordinates
     * @param dx The amount to move the player to the right
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
        else //If no collision, then move player to the left by dx amount
        {
            this.thisPlayer.setX(this.thisPlayer.getX() - dx);
        }
    }

    /**
     * Gets the coordinates of 3 points along the bottom of the player's bounding box (bottom right, bottom middle, bottom left)
     * Checks to see if any of those points would collide with a solid tile if the player were to move dy amount down
     * If no collision, the player will be move to the new coordinates
     * @param dy The amount to the move the player down 
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
            yVelocity = maxY_Velocity;
            return;
        }
        else //If no collision, move player down by dy amount
        {
            this.thisPlayer.setY(this.thisPlayer.getY() + dy);
        }
    }

    /**
     * Gets the coordinates of 3 points along the top of the player's bounding box (top right, top middle, top left)
     * will check to see if any of those points would collide with a solid tile if the player were to move dy amount up.
     * If no collision, the player will be move to the new coordinates
     * @param dy The amount to move the player up
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
