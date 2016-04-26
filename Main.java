import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/7/2016.
 * 
 */
public class Main extends Canvas implements Runnable
{
    public JFrame frame;
    private boolean running = false;
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private Level level = new Level();
    private final int WAIT = 10; //All thread sleeps need to be the same time to prevent visible stuttering

    /**
     * 
     */
    public Main()
    {
        Dimension size = new Dimension(1216, 1024);
        //setPreferredSize is in the canvas class
        setPreferredSize(size);
        frame = new JFrame();
    }
    
    /**
     * 
     */
    public void run()
    {
        
        while (running)
        {
            
        }
    }
    
    /**
     * 
     */
    public synchronized void start()
    {
        running = true;
    }
}
