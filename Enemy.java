
import java.awt.*;

/**
 * Created by Calvin on 4/7/2016.
 * Will provide the basis for all enemies
 */
public class Enemy extends Entity
{
    private int damage, health, speed;
    private boolean canMoveUp = true;
    private boolean movingUp = false;
    private boolean canMoveDown = true;
    private boolean movingDown = false;
    private boolean canMoveRight = true;
    private boolean movingRight = false;
    private boolean canMoveLeft = true;
    private boolean movingLeft = false;

    /**
     *
     * @param newDamage
     * @param newHealth
     * @param newSpeed
     * @param newX
     * @param newY
     * @param width
     * @param height
     */
    public Enemy(int newDamage, int newHealth, int newSpeed, int newX, int newY, int width, int height)
    {
        super(newX, newY, width, height);
        this.damage = newDamage;
        this.health = newHealth;
        this.speed  = newSpeed;
    }

    //Will start the enemy's attack
    public void attack(){}
    //Will go through the animation for the enemy's attack, should be called in attack()
    public void attackAnimation(){}
    //Moves the enemy accross the screen
    public void moveHorizontal(boolean direction){}
    //Moves the enemy up and down on the screen
    public void moveVertical(boolean direction) {}
    //Will set the current sprite displayed for the enemy to the first one (should be the standing still sprite)
    public void stand(Graphics g){}
    //Returns the pixels per a frame of the enemy
    public int getSpeed()
    {
        return this.speed;
    }
    //Returns the damage dealt out per a hit by the enemy
    public int getDamage()
    {
        return this.damage;
    }
    //All enemies are collidable
    public boolean isCollidable()
    {
        return true;
    }

    /**
     * Checks to see if the enemy moves speed amount to the right if it will hit the right bounds
     * @param frameWidth The width of the current frame the enemy is in
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
     * Checks to see if the enemy moves speed amount to the left if it will hit the left bounds
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
     * Checks to see if the enemy moves speed amount up if it will hit the top
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
     * Checks if the enemy moves gravity amount down it will hit the bottom of the frame
     * @param gravity Will be used to look ahead and see if the enemy moves gravity amount will it reach the bottom
     * @param frameHeight The height of the main frame that the enemy is in
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
