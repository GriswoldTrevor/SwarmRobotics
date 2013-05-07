import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Toolbar extends JPanel {
    @SuppressWarnings("unused")
	private LayoutManager layout;

    private JButton buttonStep;
    private JButton buttonPlay;
    
    private JPanel seperator;
    
    private JLabel labelNumRobots;
    private JSpinner spinnerNumRobots;
    private JButton buttonRestart;
    
    private JPanel seperator2;
    
    private JLabel labelRunDelay;
    private JSpinner spinnerRunDelay;
    private JButton buttonSetRunDelay;

    private JPanel seperator3;
    
    private JLabel labelCooperate;
    private JCheckBox checkboxCooperate;
    private JButton buttonSetCooperate;

    private GUI gui;

    public Toolbar(GUI gui) {
        this.gui = gui;

        buttonStep = Util.clickableButton("Step", new StepAction());
        buttonPlay = Util.clickableButton("Play/Pause", new PlayAction());
        
        seperator = new JPanel();
        seperator.setPreferredSize(new Dimension(20, 25));
        
        labelNumRobots = new JLabel("Robots:");
        spinnerNumRobots = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        buttonRestart = Util.clickableButton("Restart", new RestartAction());
        
        seperator2 = new JPanel();
        seperator2.setPreferredSize(new Dimension(20, 25));
        
        labelRunDelay = new JLabel("Run Delay:");
        spinnerRunDelay = new JSpinner(new SpinnerNumberModel(gui.runDelay, 0, 100, 1));
        buttonSetRunDelay = Util.clickableButton("Set", new SetRunDelayAction());
        
        seperator3 = new JPanel();
        seperator3.setPreferredSize(new Dimension(20, 25));
        
        labelCooperate = new JLabel("Cooperation:");
        checkboxCooperate = new JCheckBox();
        checkboxCooperate.setSelected(gui.getCooperate());
        buttonSetCooperate = Util.clickableButton("Set", new SetCooperateAction());
        
        add(buttonStep);
        add(buttonPlay);
        
        add(seperator);
        
        add(labelNumRobots);
        add(spinnerNumRobots);
        add(buttonRestart);
        
        add(seperator2);
        
        add(labelRunDelay);
        add(spinnerRunDelay);
        add(buttonSetRunDelay);
        
        add(seperator3);
        
        add(labelCooperate);
        add(checkboxCooperate);
        add(buttonSetCooperate);
        
    }
    
    private class SetCooperateAction implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	gui.setCooperate(checkboxCooperate.isSelected());
        }
    }
    
    private class RestartAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	gui.restart((int)spinnerNumRobots.getValue(), checkboxCooperate.isSelected());
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