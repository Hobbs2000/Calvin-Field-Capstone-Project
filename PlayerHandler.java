
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

    private Player thisPlayer;
    private Controls controls;

    public int frameWidth, frameHeight;

    /**
     *
     * @param player
     * @param entityList
     * @param frame
     */
    public PlayerHandler(Player player, ArrayList<Entity> entityList, int sleep, JFrame frame, Controls controls)
    {
        this.thisPlayer = player;
        this.entities = entityList;
        this.controls = controls;
        this.sleep = sleep;

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

            boolean canMoveRight = true;
            Entity rightCollidedEntity = null;
            boolean canMoveLeft = true;
            Entity leftCollideEntity = null;
            boolean canMoveDown = true;
            Entity bottomCollideEntity = null;
            boolean canMoveUp = true;
            Entity topCollideEntity = null;


            for (int i = 0; i < entities.size(); i++)
            {

                if ((entities.get(i) != this.thisPlayer) && entities.get(i).isCollidable())
                {

                    int top1= this.thisPlayer.getY();
                    int bottom1 = this.thisPlayer.getY() + this.thisPlayer.getHeight();
                    int right1 = this.thisPlayer.getX() + this.thisPlayer.getWidth();
                    int left1 = this.thisPlayer.getX();

                    int top2 = entities.get(i).getY();
                    int bottom2 = entities.get(i).getY() + entities.get(i).getHeight();
                    int right2 =entities.get(i).getX() + entities.get(i).getWidth();
                    int left2 = entities.get(i).getX();



                    //If the right key is pressed and a collision is detected, the player cannot move right
                    if (controls.right)
                    {
                        canMoveLeft = false;

                        if (this.thisPlayer.checkRightCollision(entities.get(i), frameWidth))
                        {
                            canMoveRight = false;
                            //Set which entity it collided with
                            rightCollidedEntity = entities.get(i);
                        }
                    }

                    //If the left key is pressed and a collision is detected, the player cannot move left
                    if (controls.left)
                    {
                        canMoveRight = false;

                        if (this.thisPlayer.checkLeftCollision(entities.get(i)))
                        {
                            canMoveLeft = false;
                            //Set which entity it collided with
                            leftCollideEntity = entities.get(i);
                        }
                    }

                    //Checks for collision with any entity before moving the enemy down
                    if ((canMoveDown) && this.thisPlayer.checkBottomCollision(entities.get(i), frameHeight, 20))
                    {
                        canMoveDown = false;
                        //Set which entity it collided with
                        bottomCollideEntity = entities.get(i);
                    }

                    if ((canMoveUp) && this.thisPlayer.checkTopCollision(entities.get(i)))
                    {
                        canMoveUp = false;
                        //Set which entity it collided with
                        topCollideEntity = entities.get(i);
                    }
                }
            }

            //Will actually move the player based on collisions and what keys were pressed
            if (canMoveRight)
            {
                this.thisPlayer.moveHorizontal(true);
            }
            if (canMoveLeft)
            {
                this.thisPlayer.moveHorizontal(false);
            }

            //Will start the jumping sequence
            if (canMoveUp && controls.space && (this.thisPlayer.isJumping() == false))
            {
                this.thisPlayer.jump(20);
            }
            else if (this.thisPlayer.isJumping() && canMoveDown)
            {
                this.thisPlayer.jump(20);
            }
            else
            {
                this.thisPlayer.stopJumping();
            }

            //Will only start the regular falling if the player is not already jumping
            if (canMoveDown && !this.thisPlayer.isJumping())
            {
                this.thisPlayer.moveDown(20);
            }

            //These next two if statements help (but doesn't always) prevent a bug where when the corner of both entities meet causing both entities to become permanently stuck
            //This problem occurs when one moving entity is on top of another, then when the top one falls off it immediatly tries to go into the lower entity causing both entities to become stuck
            if ((rightCollidedEntity != null) && ((topCollideEntity == rightCollidedEntity) || (bottomCollideEntity == leftCollideEntity)) && controls.right)
            {
                this.thisPlayer.moveHorizontal(true);
            }
            else if ((leftCollideEntity != null) && ((topCollideEntity == leftCollideEntity) || (bottomCollideEntity == leftCollideEntity)) && controls.left)
            {
                this.thisPlayer.moveHorizontal(false);
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
     */
    public void stop()
    {
        running = false;
    }



}
