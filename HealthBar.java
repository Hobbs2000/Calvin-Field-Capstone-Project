import java.awt.*;
/**
 * A bar to show how much health an entity has
 */
public class HealthBar extends Rectangle
{
    int maxWidth;
    Color color = Color.RED;
    
    /**
     * 
     */
    public HealthBar(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        maxWidth = width;
    }
    
    /**
     * Sets the current color of the healthBar
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }
    
    /**
     * This draws the healthBar in its current location  
     */
    public void draw(Graphics g)
    {
        g.setColor(this.color);
        g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
    }
    
    /**
     * Scales the healthBar according to the health of the entity
     */
    public void scale(int currentHealth)
    {
        if (currentHealth != 0)
        {
            //This should not be a set number (should be different max healths for different entities)
            float percent = (currentHealth / 100);
            System.out.println("health:"+currentHealth);
            System.out.println("percent:"+percent);
            setSize((int)(maxWidth*percent), (int)getHeight());
        }
    }
}