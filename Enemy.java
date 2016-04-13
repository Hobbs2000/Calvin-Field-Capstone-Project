
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


    //Will nearly always be called since there is gravity
    /**
     *  Moves the enemy down by gravity amount
     *  @param gravity Will be the amount that the player is moved down by
     */
    public void moveDown(int gravity)
    {
        if ( canMoveDown() == true )
        {
            setMovingDown(true);
            setMovingUp(false);
            super.setY(super.getY() + gravity);
        }
    }

    /**
     * Returns if the enemy is travelling right
     * @return Returns if the enemy is going right
     */
    public boolean isMovingRight()
    {
        return movingRight;
    }

    //Sets if the enemy is currently traveling right
    public void setMovingRight(boolean newRight)
    {
        this.movingRight = newRight;
    }

    /**
     *Will look ahead and check to see if the player moves speed amount to the right will there be a collision
     * <dt><b>precondition:</b><dd> otherEntity entity must be collidable
     * @param otherEntity The entity that the enemy is checking for a collision with
     * @return Will return if this player is colliding with the passed in entity on the right side
     */
    public boolean checkRightCollision(Entity otherEntity, int frameWidth)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = otherEntity.getY();
        int bottom2 = otherEntity.getY() + otherEntity.getHeight();
        int right2 =otherEntity.getX() + otherEntity.getWidth();
        int left2 = otherEntity.getX();

        if ((left1 < right2 && (right1 + speed) > left2 && top1 < bottom2 && bottom1 > top2) || ((right1 + speed) > frameWidth))
        {
            return true;
        }
        return false;

    }


    /**
     * Returns if the enemy is going left
     * @return Returns if the enemy is going left
     */
    public boolean isMovingLeft()
    {
        return movingLeft;
    }

    //Sets if the enemy is currently traveling left
    public void setMovingLeft(boolean newLeft)
    {
        this.movingLeft = newLeft;
    }

    /**
     * Will look ahead and check to see if the enemy moves speed amount to the left will there be a collision
     * <dt><b>precondition:</b><dd> otherEntity entity must be collidable
     * @return Returns if there is a collision or not
     */
    public boolean checkLeftCollision(Entity otherEntity)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = otherEntity.getY();
        int bottom2 = otherEntity.getY() + otherEntity.getHeight();
        int right2 =otherEntity.getX() + otherEntity.getWidth();
        int left2 = otherEntity.getX();

        if (((left1 - speed) < right2 && right1 > left2 && top1 < bottom2 && bottom1 > top2) || (left1 < 0))
        {
            canMoveLeft = false;
            canMoveRight = true;
            return true;
        }

        canMoveRight = false;
        canMoveLeft = true;
        return false;
    }

    /**
     * Returns if the enemy is going up
     * @return Returns if the enemy is going up
     */
    public boolean isMovingUp()
    {
        return movingUp;
    }

    //Sets if the enemy is currently traveling up
    public void setMovingUp(boolean newUp)
    {
        this.movingUp = newUp;
    }

    /**
     * Will look ahead and check to see if the enemy moves speed amount up, will there be a collision
     * <dt><b>precondition:</b><dd> otherEntity entity must be collidable
     * @return Returns if there is a collision or not
     */
    public boolean checkTopCollision(Entity otherEntity)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = otherEntity.getY();
        int bottom2 = otherEntity.getY() + otherEntity.getHeight();
        int right2 =otherEntity.getX() + otherEntity.getWidth();
        int left2 = otherEntity.getX();

        if ((left1 < right2 && right1 > left2 && (top1 - speed) < bottom2 && bottom1 > top2) || ((top1 - speed) < 0))
        {
            return  true;
        }
        return false;
    }

    //Returns if the enemy can move down or not
    public boolean canMoveDown()
    {
        return canMoveDown;
    }

    //Returns if the enemy is going down
    public boolean isMovingDown()
    {
        return movingDown;
    }

    /**
     * Returns if the enemy is going down
     * @return Returns if the enemy is going down
     */
    public void setMovingDown(boolean newDown)
    {
        this.movingDown = newDown;
    }

    /**
     * Will look ahead and check to see if the enemy moves speed amount down, will there be a collision
     * <dt><b>precondition:</b><dd> otherEntity entity must be collidable
     * @return Returns if there is a collision or not
     */
    public boolean checkBottomCollision(Entity otherEntity, int frameHeight, int gravity)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = otherEntity.getY();
        int bottom2 = otherEntity.getY() + otherEntity.getHeight();
        int right2 =otherEntity.getX() + otherEntity.getWidth();
        int left2 = otherEntity.getX();

        if ((left1 < right2 && right1 > left2 && top1 < bottom2 && (bottom1 + gravity) > top2) || (bottom1  > frameHeight))
        {
            return  true;
        }
        return false;
    }


}
