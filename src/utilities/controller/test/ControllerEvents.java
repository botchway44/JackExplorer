package utilities.controller.test;



import utilities.controller.ControllerIndex;
import utilities.controller.ControllerManager;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class ControllerEvents {
    public static int NUM_CONTROLLERS = 4;


    public void run(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            System.out.println("Error setting lookandfeel");
            e.printStackTrace();
        }

        startController();
    }

    public void startController() {

        ControllerManager controllers = new ControllerManager(NUM_CONTROLLERS);
        controllers.initSDLGamepad();
        JTabbedPane tabbedPane = new JTabbedPane();
        JFrame testFrame = new JFrame();

        testFrame.setDefaultCloseOperation(3);
        testFrame.setLocationRelativeTo((Component)null);
        testFrame.setMinimumSize(new Dimension(640, 350));
        testFrame.setResizable(false);
        testFrame.setVisible(true);
        ControllerInfoPanel[] controllerTabs = new ControllerInfoPanel[NUM_CONTROLLERS];


        for(int i = 0; i < NUM_CONTROLLERS; ++i) {
            controllerTabs[i] = new ControllerInfoPanel();
            tabbedPane.add("   Controller " + (i + 1) + "   ", controllerTabs[i]);
        }

        testFrame.setContentPane(tabbedPane);

        while(true) {
            try {
                Thread.sleep(30L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            controllers.update();

            for(int i = 0; i < controllerTabs.length; ++i) {
                ControllerIndex controllerAtIndex = controllers.getControllerIndex(i);
                if (controllerAtIndex.isConnected()) {
                    controllerTabs[i].updatePanel(controllerAtIndex);
                } else {
                    controllerTabs[i].setAsDisconnected();
                }
            }

            testFrame.repaint();
        }
    }
}
