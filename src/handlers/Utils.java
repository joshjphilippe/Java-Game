package handlers;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Joshua Jean-Philippe
 * This will handle any random utility methods
 * instead having code cluttered in classes to get the same result
 */
public class Utils {
	
	/**
	 * This is just a delay, will be used throughout program
     * There isn't a reason to delay stuff, just like it cause immersion
	 * @param seconds
	 */
    public static void delay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
