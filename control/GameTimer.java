package cs2410.assn8.control;/**
 * Created by Ryan on 11/23/2016.
 */

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ryan
 * @version 1.0
 * Contains relevant data for the timing of each game
 */
public class GameTimer
{
    public Timer gameTime;
    public SimpleLongProperty currentTimeProperty = new SimpleLongProperty(0);

    public boolean timerStarted;

    /**
     * default constructor, gets timer ready
     */
    public GameTimer()
    {
        gameTime = new Timer();
    }

    /**
     * starts timer and handles updating of game time
     */
    public void startTimer()
    {
        gameTime.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                timerStarted = true;
                currentTimeProperty.set(currentTimeProperty.get() + 1);
                System.out.println("currentTimeProperty: " + currentTimeProperty.get());
            }
        }, 1000, 1000);
    }

    /**
     * Resets timer to default values
     */
    public void reset()
    {
        gameTime.cancel();
        gameTime = new Timer();
        currentTimeProperty.set(0);
        timerStarted = false;
    }
}
