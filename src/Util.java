import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class Util {
    public static final Random random = new Random();

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