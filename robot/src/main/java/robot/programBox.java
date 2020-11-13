package robot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class programBox implements ActionListener {
    public void actionPerformed(ActionEvent e) {

        JComboBox cb = (JComboBox)e.getSource();
        RobotSimulator.programNum = cb.getSelectedIndex();
    }
}