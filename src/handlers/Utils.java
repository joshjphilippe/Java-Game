package handlers;

/**
 * 
 * @author Joshua Jean-Philippe
 * This will handle any random utility methods
 * instead having code cluttered in classes to get the same result
 */
public class Utils {
	
	/**
	 * This is just a delay, will be used throughout program
	 * @param seconds
	 */
    public static void delay(int seconds) {
        try {
        	int x = seconds * 1000;
            Thread.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
