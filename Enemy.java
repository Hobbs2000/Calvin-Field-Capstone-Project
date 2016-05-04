
import java.awt.*;

/**
 * Created by Calvin on 4/7/2016.
 * Will provide the basis for all enemies
 */
abstract class Enemy extends Entity
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

    /**
     * Returns the speed of the enemy
     * @return The speed of the enemy
     */
    public int getSpeed()
    {
        return this.speed;
    }
    
    /**
     * Returns the damage dealt out per a hit by the enemy
     * @return The damage the enemy deals to the player
     */
    public int getDamage()
    {
        return this.damage;
    }
    
    /**
     * All enemies are collidable
     * @return True because all enemies are collidable
     */
    public boolean isCollidable()
    {
        return true;
    }

}
