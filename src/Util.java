import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class Util {
    public static final Random random = new Random();
    
    public static double normalizeAngle(double angle){
    	double newAngle = angle;
    	while ((newAngle >= 360) || (newAngle < 0)) {
			if (newAngle < 0){
				newAngle = newAngle + 360;
			} else {
				newAngle = newAngle - 360;
			}
		}
    	return newAngle;
    }
    public static double angleDifference(double angle1, double angle2) {
    	double difference = Math.abs(angle1 - angle2);
    	if (difference > 180) {
    		difference = 360 - difference;
    	}
    	return difference;
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
        }
    }

    public static void joinThread(Thread thread) {
        try {
            thread.join();
        }
        catch (InterruptedException e) {
        }
    }
    
    public static JButton clickableButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);

        return button;
    }
}