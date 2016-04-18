

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
    private int damage = 10;
    private int health = 100;
    private int speed = 8;

    private boolean movingUp = false;
    private boolean canMoveDown = true;
    private boolean movingDown = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;

    private int yVelocity = -150;
    private boolean jumping = false;


    /**
     *
     * @param startX
     * @param startY
     * @param scale
     */
    public Player(int startX, int startY, int scale)
    {
        super(startX, startY, 16, 32);

        this.scale = scale;

        try
        {
            walkForwardAnimation = new Animation(ImageIO.read(getClass().getResource("/BasicPlayerSheet.png")), 16, 32, 5, super.getX(), super.getY(), this.scale, 10);
            currentAnimation = walkForwardAnimation;

            //walkBackAnimation = new Animation(ImageIO.read(getClass().getResource("/scottpilgrim_sheet.jpg")), 32, 36, 8, super.getX(), super.getY(), this.scale, 1.5);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public boolean isCollidable()
    {
        return true;
    }

    /**
     * Moves the player's x coordinate speed amount
     * @param direction True means right and false means left
     */
    public void moveHorizontal(boolean direction)
    {
        if (direction == true)
        {
            movingRight = true;
            movingRight = false;
            this.setX(super.getX() + this.speed);
            this.currentAnimation = walkForwardAnimation;
        }
        else
        {
            movingLeft = true;
            movingRight = false;
            this.setX(super.getX() - this.speed);
            this.currentAnimation = walkBackAnimation;
        }
        walkForwardAnimation.update(super.getX(), super.getY());

        if (currentAnimation != walkForwardAnimation)
        {
            currentAnimation = walkForwardAnimation;
        }
    }

    //Will nearly always be called since there is gravity
    /**
     *  Moves the player down by gravity amount
     *  @param gravity Will be the amount that the player is moved down by
     */
    public void moveDown(int gravity)
    {
        if ( canMoveDown == true )
        {
            movingDown = true;
            movingUp = false;
            super.setY(super.getY() + gravity);
        }
    }

    /**
     *
     */
    public void moveUp(int amount)
    {
        super.setY(super.getY() - amount);
    }

    /**
     *
     */
    public void jump(int gravity)
    {
        jumping = true;
        yVelocity += gravity;
        super.setY(super.getY() + yVelocity);
    }

    /**
     *
     */
    public void stopJumping()
    {
        jumping = false;
        yVelocity = -90;
    }

    /**
     * Will call the draw method for the currentAnimation for the player
     * @param g The graphics component
     */
    public void animate(Graphics g)
    {
        currentAnimation.draw(g);
    }

    /**
     * Returns true since all players have an animation
     * @return Returns true
     */
    public boolean hasAnimation()
    {
        return true;
    }

    /**
     *
     */
    public boolean isJumping()
    {
        return jumping;
    }

    /**
     *
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
     * @return Returns the actually width of the player
     */
    public int getHeight()
    {
        return (int)(originalSpriteHeight * scale);
    }

    /**
     *
     * @param newX
     */
    public void setX(int newX)
    {
        super.setX(newX);
        walkForwardAnimation.update(super.getX(), super.getY());
    }

    /**
     *
     * @param newY
     */
    public void setY(int newY)
    {
        super.setY(newY);
        walkForwardAnimation.update(super.getX(), super.getY());
    }

    /**
     * Checks to see if the player moves speed amount to the right if it will hit the right bounds
     * @param frameWidth The width of the current frame the player is in
     * @return returns true if it is exceeding the right frame bounds false if not
     */
    public boolean exceedingBoundsRight(int frameWidth)
    {
        if ((this.getX() + this.getWidth() + this.speed) > frameWidth)
        {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the player moves speed amount to the left if it will hit the left bounds
     * @return returns true if it is exceeding the left frame bounds false if not
     */
    public boolean exceedingBoundsLeft()
    {
        if ((this.getX() - this.speed) < 0)
        {
            return true;
        }
        return false;
    }
    /**
     * Checks to see if the player moves speed amount up if it will hit the top
     * @return returns true if it is exceeding the top frame bounds false if not
     */
    public boolean exceedingBoundsTop()
    {
        if ((this.getY() - speed) < 0)
        {
            return true;
        }
        return false;
    }
    /**
     * Checks if the player moves gravity amount down it will hit the bottom of the frame
     * @param gravity Will be used to look ahead and see if the player moves gravity amount will it reach the bottom
     * @param frameHeight The height of the main frame that the player is in
     * @return returns true if it is exceeding the bottom frame bounds false if not
     */
    public boolean exceedingBoundsBottom(int frameHeight, int gravity)
    {
        if ((this.getY() + this.getHeight() + gravity) > frameHeight)
        {
            return true;
        }
        return false;
    }


}
