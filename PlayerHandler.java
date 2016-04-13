
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
            boolean canMoveLeft = true;
            boolean canMoveUp = true;
            boolean canMoveDown = true;

            for (int i = 0; i < entities.size(); i++)
            {

                if ((entities.get(i) != this.thisPlayer) && entities.get(i).isCollidable())
                {
                    //If the right key is pressed and a collision is detected, the player cannot move right
                    System.out.println(controls.right);
                    if (controls.right)
                    {
                        canMoveLeft = false;

                        if (this.thisPlayer.checkRightCollision(entities.get(i), frameWidth))
                        {
                            canMoveRight = false;
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
                    if ((canMoveDown) && this.thisPlayer.checkBottomCollision(entities.get(i), frameHeight))
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
                this.thisPlayer.moveDown(5);
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