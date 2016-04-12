
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
    private boolean collidingWithFloor = false;

    private int last_entity_top = 0;

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

    //Returns if this enemy is exceeding the bounds of the frame
    public boolean exceedingBoundsRight(int frameWidth)
    {
        if ((this.getX() + this.getWidth() + this.speed) > frameWidth)
        {
            return true;
        }

        return false;
    }
    public boolean exceedingBoundsLeft()
    {
        if ((this.getX() - this.speed) < 0)
        {

            return true;
        }

        return false;
    }
    public boolean exceedingBoundsTop()
    {
        if ((this.getY() - speed) < 0)
        {
            return true;
        }

        return false;
    }
    public boolean exceedingBoundsBottom(int frameHeight, int gravity)
    {
        if ((this.getY() + this.getHeight() + gravity) > frameHeight)
        {
            return true;
        }
        return false;
    }


    //Will nearly always be called since there is gravity
    public void moveDown(int gravity)
    {
        if ( canMoveDown() == true )
        {
            setMovingDown(true);
            setMovingUp(false);
            super.setY(super.getY() + gravity);
        }
    }

    //Returns if the enemy can move right or not
    public boolean canMoveRight(int frameWidth)
    {
        if ((this.getX() + this.getWidth() + this.speed) > frameWidth)
        {
            return false;
        }

        return canMoveRight;
    }
    //Returns if the enemy is going right
    public boolean isMovingRight()
    {
        return movingRight;
    }
    //Sets if the enemy is currently traveling right
    public void setMovingRight(boolean newRight)
    {
        this.movingRight = newRight;
    }
    //Will look ahead and check to see if the enemy moves speed amount to the right will there be a collision
    //Other entity must be collidable
    public boolean checkRightCollision(Entity other, int frameWidth)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = other.getY();
        int bottom2 = other.getY() + other.getHeight();
        int right2 =other.getX() + other.getWidth();
        int left2 = other.getX();

        if ((left1 < right2 && (right1 + speed) > left2 && top1 < bottom2 && bottom1 > top2) || ((right1 + speed) > frameWidth))
        {
            return true;
        }
        return false;

    }

    //Returns if the enemy can move left or not
    public boolean canMoveLeft()
    {
        if ((this.getX() - this.speed) < 0)
        {
            return false;
        }

        return canMoveLeft;
    }
    //Returns if the enemy is going left
    public boolean isMovingLeft()
    {
        return movingLeft;
    }
    //Sets if the enemy is currently traveling left
    public void setMovingLeft(boolean newLeft)
    {
        this.movingLeft = newLeft;
    }
    //Will look ahead and check to see if the enemy moves speed amount to the left will there be a collision
    //Other entity must be collidable
    public boolean checkLeftCollision(Entity other)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = other.getY();
        int bottom2 = other.getY() + other.getHeight();
        int right2 =other.getX() + other.getWidth();
        int left2 = other.getX();

        if (((left1 - speed) < right2 && right1 > left2 && top1 < bottom2 && bottom1 > top2) || ((left1 - speed) < 0))
        {
            canMoveLeft = false;
            canMoveRight = true;
            return true;
        }

        canMoveRight = false;
        canMoveLeft = true;
        return false;
    }

    //Returns if the enemy can move up or not
    public boolean canMoveUp()
    {
        return canMoveUp;
    }
    //Returns if the enemy is going up
    public boolean isMovingUp()
    {
        return movingUp;
    }
    //Sets if the enemy is currently traveling up
    public void setMovingUp(boolean newUp)
    {
        this.movingUp = newUp;
    }
    //Will look ahead and check to see if the enemy moves speed amount up, will there be a collision
    //Other entity must be collidable
    public boolean checkTopCollision(Entity other)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = other.getY();
        int bottom2 = other.getY() + other.getHeight();
        int right2 =other.getX() + other.getWidth();
        int left2 = other.getX();

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
    //Sets if the enemy is currently traveling down
    public void setMovingDown(boolean newDown)
    {
        this.movingDown = newDown;
    }
    //Will look ahead and check to see if the enemy moves speed amount down, will there be a collision
    //Other entity must be collidable
    public boolean checkBottomCollision(Entity other, int frameHeight)
    {
        int top1= this.getY();
        int bottom1 = this.getY() + this.getHeight();
        int right1 = this.getX() + this.getWidth();
        int left1 = this.getX();

        int top2 = other.getY();
        int bottom2 = other.getY() + other.getHeight();
        int right2 =other.getX() + other.getWidth();
        int left2 = other.getX();

        if ((left1 < right2 && right1 > left2 && top1 < bottom2 && (bottom1 + speed) > top2) || ((bottom1 + speed) > frameHeight))
        {
            return  true;
        }
        return false;
    }


}
