
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Calvin on 4/8/2016.
 * This is a tester so I dont care how bad it is
 */
public class AnimationTester extends Canvas implements Runnable
{
    public JFrame frame;
    private boolean running = false;
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private Level level = new Level();
    private final int WAIT = 10; //All thread sleeps need to be the same time to prevent visible stuttering
    Renderer renderer;

    public AnimationTester()
    {
        Dimension size = new Dimension(1216, 1024);
        //setPreferredSize is in the canvas class
        setPreferredSize(size);
        frame = new JFrame();
    }

    public static void main(String[] args)
    {
        AnimationTester tester = new AnimationTester();
        tester.frame.setResizable(false);
        tester.frame.setTitle("Game");
        tester.frame.add(tester);
        tester.frame.pack();

        tester.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tester.frame.setLocationRelativeTo(null);
        tester.frame.setVisible(true);

        //Make the window not have to be clicked on to get input (Set is as the main focus when it begins)
        tester.requestFocusInWindow();

        //Start the program
        tester.start();
    }

    //Starts all processes for the game to begin
    public synchronized void start()
    {
        entities.add(new Player(240, 300, 3));

        entities.add(new Zombie(200, 400, 3));
        //entities.add(new Zombie(250, 200, 5));


        Controls controls = new Controls();

        super.addKeyListener(controls);
        new Thread(new PlayerHandler((Player)entities.get(0), entities, WAIT, frame, controls, level)).start();

        new Thread(new EnemyHandler((Enemy)entities.get(1),entities, WAIT, frame, level)).start();


        EnemySpawner spawner = new EnemySpawner(entities, level);
        spawner.startZombieSpawner(frame);

        renderer = new Renderer(this, frame, entities, level);
        Thread renderThread = new Thread(renderer);
        renderThread.start();
        
        Thread mainThread = new Thread(this);
        mainThread.start();
    }

    /**
     * Keeps the main thread running
     */
    public void run()
    {
        while (true)
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


