

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Calvin on 4/7/2016.
 */
public class Zombie extends Enemy
{
    private Animation walkAnimation;
    private Animation currentAnimation;
    private double scale;
    private int originalSpriteWidth;
    private int originalSpriteHeight;
    private int DY = -20;
    private boolean jumping = false;

    /**
     *
     */
    public Zombie(int spawnX, int spawnY, double scale)
    {
        /*
        Damage = 5
        Health = 20
        Speed  = 5 (5 pixels per a frame)*/
        super(5, 20, 5, spawnX, spawnY, 16, 16);
        originalSpriteHeight = 16;
        originalSpriteWidth = 16;
        this.scale = scale;
        try
        {
            walkAnimation = new Animation(ImageIO.read(getClass().getResource("/TestSpriteSheet.jpg")), 16, 16, 2, super.getX(), super.getY(), this.scale, 5);
            currentAnimation = walkAnimation;
            //walkAnimation = new Animation(ImageIO.read(getClass().getResource("/scottpilgrim_sheet.jpg")), 32, 36, 8, super.getX(), super.getY(), this.scale, 1.5);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void setX(int newX)
    {
        super.setX(newX);
        currentAnimation.update(newX, this.getY());
    }

    /**
     *
     */
    public void setY(int newY)
    {
        super.setY(newY);
        currentAnimation.update(this.getX(), newY);
    }

    /**
     * Calls the draw method of all the animations for this enemy
     * @param g
     */
    public void animate()
    {
        currentAnimation.increaseCount();
    }
    
    public void draw(Graphics g)
    {
        currentAnimation.draw(g);
    }

    /**
     *
     */
    public boolean hasAnimation()
    {
        return true;
    }

    /**
     *
     */
    public int getWidth()
    {
        return (int)(originalSpriteWidth * scale);
    }

    /**
     *
     */
    public int getHeight()
    {
        return (int)(originalSpriteHeight * scale);
    }




}
