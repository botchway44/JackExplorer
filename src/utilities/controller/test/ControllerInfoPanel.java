package utilities.controller.test;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import utilities.controller.ControllerAxis;
import utilities.controller.ControllerButton;
import utilities.controller.ControllerIndex;
import utilities.controller.ControllerUnpluggedException;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

public class ControllerInfoPanel extends JPanel {
    private JPanel title;
    private JPanel axes;
    private JPanel buttons;
    private JSlider leftRumble;
    private JSlider rightRumble;
    private JButton vibrateButton;
    private JLabel titleLabel;

    public ControllerInfoPanel() {
        this.setLayout(new BorderLayout());
        this.title = new JPanel();
        this.axes = new JPanel();
        this.buttons = new JPanel();
        JPanel vibratePanel = new JPanel();
        this.vibrateButton = new JButton("Rumble");
        this.leftRumble = new JSlider(0, 100, 100);
        this.rightRumble = new JSlider(0, 100, 100);
        vibratePanel.add(this.leftRumble);
        vibratePanel.add(this.rightRumble);
        vibratePanel.add(this.vibrateButton);
        this.title.setLayout(new BoxLayout(this.title, 1));
        this.title.setAlignmentX(0.5F);
        this.titleLabel = new JLabel();
        this.title.add(this.titleLabel);
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, 1));
        middlePanel.add(this.title);
        middlePanel.add(this.axes);
        middlePanel.add(this.buttons);
        this.add(middlePanel);
        this.add(vibratePanel, "South");
    }

    public void updatePanel(ControllerIndex c) {
        try {
            this.titleLabel.setText(c.getName());
            this.axes.removeAll();

            for(ControllerAxis a : ControllerAxis.values()) {
                JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(100, 30));
                label.setText(a.name());
                JProgressBar progressBar = new JProgressBar(-100, 100);
                progressBar.setPreferredSize(new Dimension(200, 30));
                progressBar.setValue((int)(c.getAxisState(a) * 100.0F));
                JPanel axisPanel = new JPanel();
                axisPanel.setLayout(new BoxLayout(axisPanel, 0));
                axisPanel.add(label);
                axisPanel.add(progressBar);
                this.axes.add(axisPanel);
            }

            this.buttons.removeAll();

            for(ControllerButton b : ControllerButton.values()) {
                JButton button = new JButton(b.name());
                button.setEnabled(c.isButtonPressed(b));
                this.buttons.add(button);
            }

            Stream var10000 = Arrays.stream(this.vibrateButton.getActionListeners());
            JButton var10001 = this.vibrateButton;
//            var10000.forEach(var10001::removeActionListener);
            this.vibrateButton.addActionListener((event) -> {
                try {
                    c.doVibration((float)this.leftRumble.getValue() / 100.0F, (float)this.rightRumble.getValue() / 100.0F, 1000);
                } catch (ControllerUnpluggedException var4) {
                    System.err.println("Failed to vibrate!");
                }

            });
        } catch (ControllerUnpluggedException e) {
            e.printStackTrace();
            this.titleLabel.setText("a Jamepad runtime exception occurred!");
            this.axes.removeAll();
            this.buttons.removeAll();
            this.axes.add(new JLabel(e.getMessage()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setAsDisconnected() {
        this.titleLabel.setText("No controller connected at this index!");
        this.axes.removeAll();
        this.buttons.removeAll();
    }
}
