 

import java.awt.*;

/**
 * Created by Calvin on 4/8/2016.
 * Any object in the program
 */
public class Entity
{
    private int x, y, width, height;

    public Entity(int newX, int newY, int width, int height)
    {
        this.x = newX;
        this.y = newY;
        this.width = width;
        this.height = height;
    }

    //Returns the current x coordinate of the enemy
    public int getX()
    {
        return this.x;
    }
    //Will set x to the parameter passed in
    public void setX(int newX)
    {
        this.x = newX;
    }

    //Returns the y coordinate of the enemy
    public int getY()
    {
        return this.y;
    }
    //Will set y to the parameter passed in
    public void setY(int newY)
    {
        this.y = newY;
    }

    //Returns how wide the entity is in pixels (If entity image is scaled, method should be overriden to account for that)
    public int getWidth()
    {
        return this.width;
    }
    //Returns how tall the entity is in pixels (If entity image is scaled, method should be overriden to account for that)
    public int getHeight()
    {
        return this.height;
    }

    //Returns if this entity has any animations
    public boolean hasAnimation()
    {
        return false;
    }

    //Calls the draw method of all the animations for this entity
    public void animate(Graphics g) {}

    //Will return if the entity can collide with other collidable entities
    //Default is false
    public boolean isCollidable()
    {
        return false;
    }

    //Will return if the entity's sprite is scaled or not
    public boolean isScaled()
    {
        return false;
    }
}
