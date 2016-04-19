
/**
 * A static final class that is used for collision detection of two entities (not for collision with tiles )
 * Created by Calvin on 4/15/2016.
 */
public final class Collision
{
    /**
     * Checks to see if entity1 is overlapping with entity2 
     * Not to be used for collision with tiles and solid objects
     * @param entity1 The current entity
     * @param entity2 The entity that entity1 is checking for a collision with
     * @return Returns if there is a collision or not
     */
    public static boolean collided(Entity entity1, Entity entity2)
    {
        int top1= entity1.getY();
        int bottom1 = entity1.getY() + entity1.getHeight();
        int right1 = entity1.getX() + entity1.getWidth();
        int left1 = entity1.getX();

        int top2 = entity2.getY();
        int bottom2 = entity2.getY() + entity2.getHeight();
        int right2 =entity2.getX() + entity2.getWidth();
        int left2 = entity2.getX();

        if ((left1 < right2 && right1 > left2 && top1 < bottom2 && bottom1 > top2))
        {
            return true;
        }
        return false;
    }
}
