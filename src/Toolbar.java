import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Toolbar extends JPanel {
    private LayoutManager layout;

    private JButton buttonStep;
    private JButton buttonPlay;

    private GUI gui;

    public Toolbar(GUI gui) {
        this.gui = gui;

        buttonStep = Util.clickableButton("Step", new StepAction());
        buttonPlay = Util.clickableButton("Play/Pause", new PlayAction());

        add(buttonStep);
        add(buttonPlay);
    }

    private class StepAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gui.pause();
            gui.step();
        }
    }

    private class PlayAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gui.toggleRunning();
        }
    }
}