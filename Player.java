

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * The user controlled player
 * There may only be one player
 * Created by Calvin on 4/7/2016.
 */
public class Player extends Entity
{
    private Animation walkForwardAnimation;
    private Animation walkBackAnimation;
    private Animation currentAnimation;
    //Make the actual size 2 pixel smaller before scaling
    private int originalSpriteWidth = 14;
    private int originalSpriteHeight = 30;
    private double scale;

    //These are the default values and may be changed
    public int damage = 10;
    public int health = 100;
    private int speed = 8;

    /**
     *
     * @param startX
     * @param startY
     * @param scale
     */
    public Player(int startX, int startY, double scale)
    {
        super(startX, startY, 16, 32);

        this.scale = scale;

        try
        {   //The speed value needs to be adjusted for different computers (should really eidt the way animation frames are changed) 
            walkForwardAnimation = new Animation(ImageIO.read(getClass().getResource("/BasicPlayerSheet.png")), 16, 32, 5, super.getX(), super.getY(), this.scale, 5);
            currentAnimation = walkForwardAnimation;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * The player IS collidable with other entities
     * @return
     */
    public boolean isCollidable()
    {
        return true;
    }

    /**
     * Will call the draw method for the currentAnimation for the player
     * @param g The graphics component
     */
    public void animate()
    {
        currentAnimation.increaseCount();
    }
    
    /**
     * 
     */
    public void draw(Graphics g)
    {
        currentAnimation.draw(g);
    }

    /**
     * Returns true since all players have an animation
     * Not really needed
     * @return Returns true
     */
    public boolean hasAnimation()
    {
        return true;
    }

    /**
     * @return speed - The speed in pixels of the player
     */
    public int getSpeed()
    {
        return this.speed;
    }

    /**
     * Will multiply the original pixel width of the player by its scale to get the current real pixel width
     * @return Returns the actually width of the player
     */
    public int getWidth()
    {
        return (int)(originalSpriteWidth * scale);
    }

    /**
     * Will multiply the original pixel width of the player by its scale to get the current real pixel width
     * @return Returns the actual width of the player
     */
    public int getHeight()
    {
        return (int)(originalSpriteHeight * scale);
    }

    /**
     * Sets the new x-coordinate of the player
     * @param newX
     */
    public void setX(int newX)
    {
        super.setX(newX);
        walkForwardAnimation.update(super.getX(), super.getY());
    }

    /**
     * Sets the new y-corrdinate of the player
     * @param newY
     */
    public void setY(int newY)
    {
        super.setY(newY);
        walkForwardAnimation.update(super.getX(), super.getY());
    }

}
