import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Toolbar extends JPanel {
    @SuppressWarnings("unused")
	private LayoutManager layout;

    private JButton buttonStep;
    private JButton buttonPlay;
    private JButton buttonRestart;
    
    private JPanel seperator;
    
    private JLabel labelNumRobots;
    private JSpinner spinnerNumRobots;
    private JButton buttonSetNumRobots;
    
    private JPanel seperator2;
    
    private JLabel labelRunDelay;
    private JSpinner spinnerRunDelay;
    private JButton buttonSetRunDelay;

    private GUI gui;

    public Toolbar(GUI gui) {
        this.gui = gui;

        buttonStep = Util.clickableButton("Step", new StepAction());
        buttonPlay = Util.clickableButton("Play/Pause", new PlayAction());
        buttonRestart = Util.clickableButton("Restart", new RestartAction());
        
        seperator = new JPanel();
        seperator.setPreferredSize(new Dimension(50, 25));
        
        labelRunDelay = new JLabel("Run Delay");
        spinnerRunDelay = new JSpinner(new SpinnerNumberModel(gui.runDelay, 0, 100, 1));
        buttonSetRunDelay = Util.clickableButton("Set", new SetRunDelayAction());
        
        seperator2 = new JPanel();
        seperator2.setPreferredSize(new Dimension(50, 25));
        
        labelNumRobots = new JLabel("Number of Robots");
        spinnerNumRobots = new JSpinner(new SpinnerNumberModel(1, 1, 25, 1));
        buttonSetNumRobots = Util.clickableButton("Set", new SetNumRobotsAction());
        
        add(buttonStep);
        add(buttonPlay);
        add(buttonRestart);
        
        add(seperator);
        
        add(labelRunDelay);
        add(spinnerRunDelay);
        add(buttonSetRunDelay);
        
        add(seperator2);
        
        add(labelNumRobots);
        add(spinnerNumRobots);
        add(buttonSetNumRobots);
        
    }
    
    private class RestartAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	gui.restart();
        }
    }
    
    private class SetNumRobotsAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	gui.restart((int)spinnerNumRobots.getValue());
        }
    }
    
    private class SetRunDelayAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	gui.runDelay = (int) spinnerRunDelay.getValue();
        }
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