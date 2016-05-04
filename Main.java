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
     * Creates a new Main object and starts it
     */
    public static void main(String[] args)
    {
        Main main = new Main();
        main.frame.setResizable(false);
        main.frame.setTitle("Game");
        main.frame.add(main);
        main.frame.pack();

        main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.frame.setLocationRelativeTo(null);
        main.frame.setVisible(true);

        //Make the window not have to be clicked on to get input (Set is as the main focus when it begins)
        main.requestFocusInWindow();

        //Start the program
        main.start();
    }
    
    /**
     * Adds the player to the first index of enities
     * Sets up everything to for the player, including creating and starting the playerHandler
     * Sets up an enemy spawner and starts it
     * Creates a renderer and starts rendering
     * Begins the main thread
     */
    public synchronized void start()
    {
        running = true;
        
        //Starting all player stuff
        entities.add(new Player(240, 300, 2.5));
        Controls controls = new Controls();
        super.addKeyListener(controls);
        PlayerHandler playerHandler = new PlayerHandler((Player)entities.get(0), entities, WAIT, frame, controls, level);
        Thread playerThread = new Thread(playerHandler);
        playerThread.start();
      
        //Starts spawning enemies
        EnemySpawner spawner = new EnemySpawner(entities, level);
        spawner.startZombieSpawner(frame);
        
        //Start rendering everything
        Renderer renderer = new Renderer(this, frame, entities, level);
        Thread renderThread = new Thread(renderer);
        renderThread.start();
        
        //Start main thread to keep program running
        Thread mainThread = new Thread(this);
        mainThread.start();
    }
    
     /**
     * 
     */
    public void run()
    {
        while (running)
        {
             try
            {
                Thread.sleep(WAIT);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
