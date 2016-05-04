 
import java.awt.*;

/**
 * Created by Calvin on 4/8/2016.
 * Any object in the program
 */
public class Entity
{
    private int x, y, width, height;

    /**
     * @param newX The starting x coordinate of the entity
     * @param newY The starting y coordinate of the entity
     * @param width The starting width of the entity 
     * @param height The starting height of the entity 
     */
    public Entity(int newX, int newY, int width, int height)
    {
        this.x = newX;
        this.y = newY;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the current x coordinate of the entity
     * @return X The x coordinate
     */
    public int getX()
    {
        return this.x;
    }
    
    /**
     * Sets the x coordinate of the entity
     */
    public void setX(int newX)
    {
        this.x = newX;
    }

    /**
     * Returns the y coordinate of the enemy
     * @return Y The y coordinate
     */
    public int getY()
    {
        return this.y;
    }
    
    /**
     * Sets the y coordinate of the entity
     */
    public void setY(int newY)
    {
        this.y = newY;
    }

    
    /**
     * Returns how wide the entity is in pixels 
     * If entity image is scaled, method should be overriden to account for that
     */
    public int getWidth()
    {
        return this.width;
    }
    
    
    /**
     * Returns how tall the entity is in pixels 
     * If entity image is scaled, method should be overriden to account for that
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * Returns if this entity has any animations
     * @return false Default entities will not have animations 
     */
    public boolean hasAnimation()
    {
        return false;
    }

    /**
     * Calls the draw method of all the animations for this entity
     * Not implemented here 
     * Must be implemented in entity subclasses that have animations
     */
    public void animate(Graphics g) {}

    /**
     * Will return if the entity can collide with other collidable entities
     * Default is false
     * @return false Default is false
     */
    public boolean isCollidable()
    {
        return false;
    }

    /**
     * Will return if the entity's sprite is scaled or not
     * @return Defaults to false
     */
    public boolean isScaled()
    {
        return false;
    }
}
