package utilities.controller.test;


import org.hid4java.*;
import org.hid4java.event.HidServicesEvent;

public class PS5ModifiedController implements HidServicesListener {
    private static final int VENDOR_ID = 0x054C;   // Sony
    private static final int PRODUCT_ID = 0x0CE6;  // DualSense PS5 Controller
    private HidDevice controller;
    private boolean running = true;

    // Previous state variables
    private byte prevButtons = 0;
    private float prevL2 = 0, prevR2 = 0;
    private float prevLeftX = 0, prevLeftY = 0, prevRightX = 0, prevRightY = 0;

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
        float leftX = ((data[1] & 0xFF) - 128) / 127.0f;
        float leftY = ((data[2] & 0xFF) - 128) / 127.0f;
        float rightX = ((data[3] & 0xFF) - 128) / 127.0f;
        float rightY = ((data[4] & 0xFF) - 128) / 127.0f;

        // Triggers (0.0 to 1.0)
        float l2 = (data[5] & 0xFF) / 255.0f;
        float r2 = (data[6] & 0xFF) / 255.0f;

        // D-pad (byte 8, first 4 bits)
        int dpad = data[8] & 0x0F;
        String dpadDirection = switch (dpad) {
            case 0 -> "N";
            case 1 -> "NE";
            case 2 -> "E";
            case 3 -> "SE";
            case 4 -> "S";
            case 5 -> "SW";
            case 6 -> "W";
            case 7 -> "NW";
            default -> "Neutral";
        };

        // Buttons
        boolean square = (data[8] & 0x10) != 0;
        boolean cross = (data[8] & 0x20) != 0;
        boolean circle = (data[8] & 0x40) != 0;
        boolean triangle = (data[8] & 0x80) != 0;

        boolean l1 = (data[9] & 0x01) != 0;
        boolean r1 = (data[9] & 0x02) != 0;
        boolean l2Button = (data[9] & 0x04) != 0;
        boolean r2Button = (data[9] & 0x08) != 0;
        boolean create = (data[9] & 0x10) != 0;
        boolean options = (data[9] & 0x20) != 0;
        boolean l3 = (data[9] & 0x40) != 0;
        boolean r3 = (data[9] & 0x80) != 0;

        boolean ps = (data[10] & 0x01) != 0;
        boolean touchpad = (data[10] & 0x02) != 0;
        boolean mute = (data[10] & 0x04) != 0;

        // Print button states (only when pressed)
        if (square) System.out.println("Square pressed");
        if (cross) System.out.println("Cross pressed");
        if (circle) System.out.println("Circle pressed");
        if (triangle) System.out.println("Triangle pressed");
//        if (!dpadDirection.equals("Neutral")) System.out.println("D-pad: " + dpadDirection);
        if (l1) System.out.println("L1 pressed");
        if (r1) System.out.println("R1 pressed");
        if (l2Button) System.out.println("L2 button pressed");
        if (r2Button) System.out.println("R2 button pressed");
        if (create) System.out.println("Create pressed");
        if (options) System.out.println("Options pressed");
        if (l3) System.out.println("L3 pressed");
        if (r3) System.out.println("R3 pressed");
        if (ps) System.out.println("PS button pressed");
        if (touchpad) System.out.println("Touchpad pressed");
        if (mute) System.out.println("Mute pressed");

        // Print analog values if they've changed significantly
//        if (Math.abs(leftX) > 0.1 || Math.abs(leftY) > 0.1)
//            System.out.printf("Left Stick: (%.2f, %.2f)%n", leftX, leftY);
//        if (Math.abs(rightX) > 0.1 || Math.abs(rightY) > 0.1)
//            System.out.printf("Right Stick: (%.2f, %.2f)%n", rightX, rightY);
//        if (l2 > 0.1)
//            System.out.printf("L2 Trigger: %.2f%n", l2);
//        if (r2 > 0.1)
//            System.out.printf("R2 Trigger: %.2f%n", r2);
    }

//    private void parseInput(byte[] data) {
//
//        System.out.println("Parsing data"+ data.length);
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
//        // Buttons
//        byte buttons = data[5];
//
//        // Check for changes and print only when there's a change
//        if (buttons != prevButtons) {
//            printButtonChanges(buttons);
//        }
//
//        if (Math.abs(l2 - prevL2) > 0.1) {
//            System.out.printf("L2 Trigger: %.2f%n", l2);
//        }
//
//        if (Math.abs(r2 - prevR2) > 0.1) {
//            System.out.printf("R2 Trigger: %.2f%n", r2);
//        }
//
//        // Check for significant analog stick movement
//        float threshold = 0.2f;
//        if (Math.abs(leftX - prevLeftX) > threshold || Math.abs(leftY - prevLeftY) > threshold) {
//            System.out.printf("Left Stick: (%.2f, %.2f)%n", leftX, leftY);
//        }
//
//        if (Math.abs(rightX - prevRightX) > threshold || Math.abs(rightY - prevRightY) > threshold) {
//            System.out.printf("Right Stick: (%.2f, %.2f)%n", rightX, rightY);
//        }
//
//        // Update previous state
//        prevButtons = buttons;
//        prevL2 = l2;
//        prevR2 = r2;
//        prevLeftX = leftX;
//        prevLeftY = leftY;
//        prevRightX = rightX;
//        prevRightY = rightY;
//    }

    private void printButtonChanges(byte buttons) {
        if ((buttons & 0x01) != 0) System.out.println("Cross pressed");
        if ((buttons & 0x02) != 0) System.out.println("Circle pressed");
        if ((buttons & 0x04) != 0) System.out.println("Triangle pressed");
        if ((buttons & 0x08) != 0) System.out.println("Up arrow Pressed pressed");
        // Add more button checks as needed
        if ((buttons & 0x16) != 0) System.out.println("Test pressed");

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

}

