

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 */
public class Controls implements KeyListener
{

    //Creates an array for all the keys on the keyboard
    private boolean[] keys = new boolean[120];

    public boolean up, down, left, right, space;

    /**
     *
     */
    public void update()
    {
        System.out.println();
        //Will set up,right,down and left to the value (true or false) of their corresponding key
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        space = keys[KeyEvent.VK_SPACE];
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        //This does nothing
    }
}
