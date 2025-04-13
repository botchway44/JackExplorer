package utilities.controller.test;
import org.hid4java.*;

import org.hid4java.event.HidServicesEvent;

import java.util.Scanner;

public class PS5ControllerManager implements HidServicesListener {
    private static final int VENDOR_ID = 0x054C;   // Sony
    private static final int PRODUCT_ID = 0x0CE6;  // DualSense PS5 Controller
    private HidDevice controller;
    private boolean running = true;

    public void start() {
        HidServicesSpecification spec = new HidServicesSpecification();
        spec.setAutoStart(true);
        spec.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

        HidServices hidServices = HidManager.getHidServices(spec);
        hidServices.addHidServicesListener(this);

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        controller = hidServices.getHidDevice(VENDOR_ID, PRODUCT_ID, null);
        if (controller == null) {
            System.err.println("Controller not connected!");
            System.exit(1);
        }

        startInputPolling();
        startControlTest();
    }

    private void startInputPolling() {
        new Thread(() -> {
            byte[] data = new byte[64];
            while (running) {
                int bytesRead = controller.read(data, 100);
                if (bytesRead > 0) parseInput(data);

                try { Thread.sleep(10); }
                catch (InterruptedException e) { break; }
            }
        }).start();
    }

    private void parseInput(byte[] data) {
        // Analog Sticks (Normalized to -1.0 to 1.0)
        float leftX = ((data[1] & 0xFF) - 127) / 127.0f;
        float leftY = ((data[2] & 0xFF) - 127) / 127.0f;
        float rightX = ((data[3] & 0xFF) - 127) / 127.0f;
        float rightY = ((data[4] & 0xFF) - 127) / 127.0f;

        // Triggers (0.0 to 1.0)
        float l2 = (data[5] & 0xFF) / 255.0f;
        float r2 = (data[6] & 0xFF) / 255.0f;

        // Action Buttons
        boolean cross = (data[8] & 0x20) != 0;
        boolean circle = (data[8] & 0x40) != 0;
        boolean triangle = (data[8] & 0x80) != 0;
        boolean square = (data[8] & 0x10) != 0;

        // Directional Buttons
        int dpad = data[8] & 0x0F;
        boolean up = dpad == 0 || dpad == 1 || dpad == 7;
        boolean right = dpad == 1 || dpad == 2 || dpad == 3;
        boolean down = dpad == 3 || dpad == 4 || dpad == 5;
        boolean left = dpad == 5 || dpad == 6 || dpad == 7;

        // Thumbstick Buttons
        boolean l3 = (data[9] & 0x40) != 0;
        boolean r3 = (data[9] & 0x80) != 0;

        // Other Buttons
        boolean create = (data[9] & 0x10) != 0;
        boolean options = (data[9] & 0x20) != 0;
        boolean ps = (data[10] & 0x01) != 0;
        boolean touchpad = (data[10] & 0x02) != 0;
        boolean mute = (data[10] & 0x04) != 0;

        // Bumpers
        boolean l1 = (data[9] & 0x01) != 0;
        boolean r1 = (data[9] & 0x02) != 0;

        // Triggers (as buttons)
        boolean l2Button = (data[9] & 0x04) != 0;
        boolean r2Button = (data[9] & 0x08) != 0;

        // Print button states (only when pressed)
        if (cross) System.out.println("Cross pressed");
        if (circle) System.out.println("Circle pressed");
        if (triangle) System.out.println("Triangle pressed");
        if (square) System.out.println("Square pressed");
        if (up) System.out.println("Up pressed");
        if (down) System.out.println("Down pressed");
        if (left) System.out.println("Left pressed");
        if (right) System.out.println("Right pressed");
        if (l3) System.out.println("L3 pressed");
        if (r3) System.out.println("R3 pressed");
        if (create) System.out.println("Create pressed");
        if (options) System.out.println("Options pressed");
        if (ps) System.out.println("PS button pressed");
        if (touchpad) System.out.println("Touchpad pressed");
        if (mute) System.out.println("Mute pressed");
        if (l1) System.out.println("L1 pressed");
        if (r1) System.out.println("R1 pressed");
        if (l2Button) System.out.println("L2 button pressed");
        if (r2Button) System.out.println("R2 button pressed");

        // Print analog trigger values
        System.out.printf("L2 Trigger: %.2f, R2 Trigger: %.2f%n", l2, r2);

        // Print analog stick positions
        System.out.printf("Left Stick: (%.2f, %.2f), Right Stick: (%.2f, %.2f)%n", leftX, leftY, rightX, rightY);
    }

//
//    private void parseInput(byte[] data) {
//        // Analog Sticks (Normalized to -1.0 to 1.0)
//        float leftX = ((data[1] & 0xFF) - 127) / 127.0f;
//        float leftY = ((data[2] & 0xFF) - 127) / 127.0f;
//        float rightX = ((data[3] & 0xFF) - 127) / 127.0f;
//        float rightY = ((data[4] & 0xFF) - 127) / 127.0f;
//
//        // Triggers (0.0 to 1.0)
//        float l2 = (data[8] & 0xFF) / 255.0f;
//        float r2 = (data[9] & 0xFF) / 255.0f;
//
//        // Buttons (Bitmask)
//        boolean cross = (data[5] & 0x01) != 0;
//        boolean circle = (data[5] & 0x02) != 0;
//        boolean triangle = (data[5] & 0x04) != 0;
//        boolean square = (data[5] & 0x08) != 0;
//
//        System.out.printf("\nL: (%.2f, %.2f) R: (%.2f, %.2f) " +
//                        "Triggers: L2=%.2f R2=%.2f%n" +
//                        "Buttons: [%s%s%s%s]",
//                leftX, leftY, rightX, rightY, l2, r2,
//                cross ? "X" : "", circle ? "O" : "",
//                triangle ? "△" : "", square ? "□" : "");
//    }

    private void startControlTest() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nPress ENTER to test rumble/LEDs (q to quit)");

            while (running) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    shutdown();
                    System.exit(0);
                }

                // Test rumble
                setRumble((byte) 0xFF, (byte) 0xFF);
                setLED(0, 255, 0); // Green
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                setRumble((byte) 0x00, (byte) 0x00);
                setLED(0, 0, 255); // Blue
            }
        }).start();
    }

    private void setRumble(byte left, byte right) {
        byte[] output = {0x02, 0x01, 0x00, 0x00, left, right, 0x00, 0x00};
        controller.write(output, output.length, (byte) 0);
    }

    private void setLED(int r, int g, int b) {
        byte[] output = {0x02, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                (byte) r, (byte) g, (byte) b, 0x00};
        controller.write(output, output.length, (byte) 0);
    }

    private void shutdown() {
        running = false;
        if (controller != null) {
            controller.close();
        }
    }

    @Override public void hidDeviceAttached(HidServicesEvent event) {}
    @Override public void hidDeviceDetached(HidServicesEvent event) {
        if (event.getHidDevice() == controller) {
            System.err.println("Controller disconnected!");
            shutdown();
        }
    }

    @Override
    public void hidFailure(HidServicesEvent hidServicesEvent) {

    }

    @Override
    public void hidDataReceived(HidServicesEvent hidServicesEvent) {

    }

    public static void main(String[] args) {
        new PS5ControllerManager().start();
    }
}
