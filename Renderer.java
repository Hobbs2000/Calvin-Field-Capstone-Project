import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/27/2016
 * Draws everything onto the current canvas by calling the draw methods of all the entities and level tiles
 * Does this on a separate thread
 * Creates a triple buffered BufferStrategy to get the graphics context from
 */
public class Renderer implements Runnable
{
    private Canvas thisCanvas;
    private ArrayList<Entity> entities;
    private JFrame frame;
    private Level currentLevel;
    private boolean running = false;
    
    /**
     * @param canvas The canvas to draw onto
     * @param currentFrame The current frame the canvas is on
     * @param entities The ArrayList of all the entities to be drawn
     * @param level The current level object
     */
    public Renderer(Canvas canvas, JFrame currentFrame, ArrayList<Entity> entities, Level level)
    {
        thisCanvas = canvas;
        frame = currentFrame;
        this.entities = entities;
        currentLevel = level;
    }
    
    /**
     * Repeatedly calls the renderAll() method
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

        //Draw the level background for this level
        currentLevel.drawBackground(g);
        
        //Synchronized to avoid a concurrent modification error
        synchronized (entities)
        {
            for (Entity entity : entities)
            {
                if (entity.hasAnimation() == true && !(entity instanceof Player))
                {
                    //Animates every entity
                    entity.draw(g);
                }
            }
        }

        currentLevel.drawWorld(g);
        
        //Player is drawn on the top most layer
        entities.get(0).draw(g);

        g.dispose();

        //Swap out and destroy old buffer(Frame) and show the new one
        bs.show();
    }
}