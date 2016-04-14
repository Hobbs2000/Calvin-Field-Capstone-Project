

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * This will handle the players movements and collision detection
 * Will be on a separate thread
 * Has a keyboard listener class that will use input for player control
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
                        }
                    }

                    //Checks for collision with any entity before moving the enemy down
                    if ((canMoveDown) && this.thisPlayer.checkBottomCollision(entities.get(i), frameHeight, 20))
                    {
                        canMoveDown = false;
                    }
                }
            }


            if (canMoveRight)
            {
                this.thisPlayer.moveHorizontal(true);
            }
            if (canMoveLeft)
            {
                this.thisPlayer.moveHorizontal(false);
            }
            if (canMoveDown)
            {
                this.thisPlayer.moveDown(20);
            }

            //These next two if statements help (but doesn't always) prevent a bug where when the corner of both entities meet causing both entities to become permanently stuck
            //This problem occurs when one moving entity is on top of another, then when the top one falls off it immediatly tries to go into the lower entity causing both entities to become stuck
            if ((canMoveRight == false) && (canMoveDown == false)&& (controls.right) && (rightCollidedEntity != null) && (Math.abs(((this.thisPlayer.getX() + this.thisPlayer.getWidth()) - rightCollidedEntity.getX())) == 1))
            {
                System.out.println("Case 1");
                this.thisPlayer.moveHorizontal(true);
            }
            if ((canMoveLeft == false) && (canMoveDown == false) &&(controls.left) && (leftCollideEntity != null) && (Math.abs((this.thisPlayer.getX() - (leftCollideEntity.getX() + leftCollideEntity.getWidth()))) == 1))
            {
                System.out.println("Case 2");
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
