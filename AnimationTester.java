 

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
    private final int WAIT = 50; //All thread sleeps need to be the same time to prevent visible stuttering

    public AnimationTester()
    {
        Dimension size = new Dimension(640, 640);
        //setPreferredSize is in the canvas class
        setPreferredSize(size);
        frame = new JFrame();
    }

    public static void main(String[] args)
    {
        AnimationTester tester = new AnimationTester();
        tester.frame.setResizable(false);
        tester.frame.setTitle("Tester");
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
        entities.add(new Player(240, 100, 3));

        //entities.add(new Zombie(0, 200, 5));
        //entities.add(new Zombie(250, 200, 5));


        Controls controls = new Controls();

        super.addKeyListener(controls);
        new Thread(new PlayerHandler((Player)entities.get(0), entities, WAIT, frame, controls, level)).start();

        //new Thread(new EnemyHandler((Enemy)entities.get(1),entities, WAIT, frame)).start();
        //new Thread(new EnemyHandler((Enemy)entities.get(2),entities, WAIT, frame)).start();


        //EnemySpawner spawner = new EnemySpawner(entities);
        //spawner.startZombieSpawner(frame);

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

            render();

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

    /**
     * Displays the graphics on the screen
     * Will repeat as many times as possible in a second (no cap)
     */
    public void render()
    {
        BufferStrategy bs = getBufferStrategy();
        //Checks to see if the BufferStrategy has already been created, it only needs to be created once
        if (bs == null)
        {
            //Always do triple buffering (put 3 in the param)
            createBufferStrategy(3);
            return;
        }

        //Links the bufferStrategy and graphics, creating a graphics context
        //Everything that deals with graphics is between the next line and g.dispose()
        Graphics g = bs.getDrawGraphics();

        //Set background to black
        g.setColor(Color.BLACK);
        g.fillRect(0,0, frame.getWidth(), frame.getHeight());


        for (Entity entity : entities)
        {
            if (entity.hasAnimation() == true)
            {
                entity.animate(g);
            }
        }

        level.drawWorld(g);


        g.dispose();

        //Swap out and destroy old buffer(Frame) and show the new one
        bs.show();
    }
}


