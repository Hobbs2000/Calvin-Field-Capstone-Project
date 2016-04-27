import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * 
 */
public class Renderer implements Runnable
{
    private Canvas thisCanvas;
    private ArrayList<Entity> entities;
    private JFrame frame;
    private Level currentLevel;
    private boolean running = false;
    
    /**
     * 
     */
    public Renderer(Canvas canvas, JFrame currentFrame, ArrayList<Entity> entities, Level level)
    {
        thisCanvas = canvas;
        this.frame = currentFrame;
        this.entities = entities;
        currentLevel = level;
    }
    
    /**
     * 
     */
    public void run()
    {
        running = true;
        while (running)
        {
            renderAll();
        }
    }
    
    /**
     * Displays the graphics on the screen
     * Will repeat as many times as possible in a second (no cap) 
     */
    public void renderAll()
    {
        BufferStrategy bs = this.thisCanvas.getBufferStrategy();
        //Checks to see if the BufferStrategy has already been created, it only needs to be created once
        if (bs == null)
        {
            //Always do triple buffering (put 3 in the param)
            this.thisCanvas.createBufferStrategy(3);
            return;
        }

        //Links the bufferStrategy and graphics, creating a graphics context
        //Everything that deals with graphics is between the next line and g.dispose()
        Graphics g = bs.getDrawGraphics();

        //Set background to white
        g.setColor(Color.white);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        //Synchronized to avoid a concurrent modification error
        synchronized (entities)
        {
            for (Entity entity : entities)
            {
                if (entity.hasAnimation() == true && !(entity instanceof Player))
                {
                    entity.animate(g);
                }
            }
        }

        //Player is drawn on the top most layer
        entities.get(0).animate(g);

        currentLevel.drawWorld(g);


        g.dispose();

        //Swap out and destroy old buffer(Frame) and show the new one
        bs.show();
    }
}