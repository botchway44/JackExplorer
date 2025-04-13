package utilities.controller;


import org.hid4java.*;
        import org.hid4java.event.HidServicesEvent;
import utilities.GameState;
import view.GamePanel;

public class PS5NewImplementation implements HidServicesListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed = false;
    GamePanel gamePanel;

    // Vendor and product IDs for the PS5 DualSense controller
    private static final int VENDOR_ID = 0x054C;   // Sony
    private static final int PRODUCT_ID = 0x0CE6;  // DualSense PS5 Controller

    private HidDevice controller;
    private boolean running = true;

    HidServicesSpecification spec = new HidServicesSpecification();

    //Pressing the options key triggers the pause multiple times.
    //This will allow us to delay the triggers
    private int pauseCounter = 0;
    private int maxCounter = 10;

    public PS5NewImplementation(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void start() {
        // Configure HID services
        spec.setAutoStart(true);
        spec.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

        HidServices hidServices = HidManager.getHidServices(spec);
        hidServices.addHidServicesListener(this);

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        controller = hidServices.getHidDevice(VENDOR_ID, PRODUCT_ID, null);
        if (controller == null) {
            System.err.println("Controller not connected!");
//            System.exit(1);
        }

        startInputPolling();
    }

    /**
     * Polls input data continuously.
     */
    private void startInputPolling() {
        new Thread(() -> {
            byte[] data = new byte[64]; // Ensure buffer is large enough
            while (running) {
                int bytesRead = controller.read(data, 100);
                if (bytesRead > 0) {
                    parseInput(data);
                }
                try {
                    Thread.sleep(11);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    /**
     * Parses the raw byte data from the controller report, using the following mapping:
     *
     * Byte 0: Report ID (always 0x01)
     * Byte 1: Left stick X axis (0x00 = left, 0xff = right, neutral ~0x80)
     * Byte 2: Left stick Y axis (0x00 = up, 0xff = down, neutral ~0x80)
     * Byte 3: Right stick X axis (0x00 = left, 0xff = right, neutral ~0x80)
     * Byte 4: Right stick Y axis (0x00 = up, 0xff = down, neutral ~0x80)
     * Byte 5: L2 trigger analog (0x00 neutral, 0xff fully pressed)
     * Byte 6: R2 trigger analog (0x00 neutral, 0xff fully pressed)
     * Byte 7: Vendor defined (ignored here)
     * Byte 8:
     *   - Lower nibble (bits 0-3): Hat switch (D-pad) (0x8 = neutral,
     *     0x0 = N, 0x1 = NE, 0x2 = E, 0x3 = SE, 0x4 = S, 0x5 = SW, 0x6 = W, 0x7 = NW)
     *   - Bit 4: Square button
     *   - Bit 5: Cross button
     *   - Bit 6: Circle button
     *   - Bit 7: Triangle button
     * Byte 9:
     *   - Bit 0: L1 button
     *   - Bit 1: R1 button
     *   - Bit 2: L2 button (digital)
     *   - Bit 3: R2 button (digital)
     *   - Bit 4: Create button
     *   - Bit 5: Options button
     *   - Bit 6: L3 button
     *   - Bit 7: R3 button
     * Byte 10:
     *   - Bit 0: PS button
     *   - Bit 1: Touchpad button
     *   - Bit 2: Mute button
     *   - Bits 3-7: Vendor defined (ignored)
     * Byte 12: Vendor defined extra data (ignored)
     */
    private void parseInput(byte[] data) {
        int reportId = data[0] & 0xFF;

        // Handle different report IDs
        if (reportId == 0x01) {
            // Short Bluetooth report (10 bytes)
            if (data.length < 10) {
                System.err.println("Invalid short Bluetooth report");
                return;
            }

            // Parse buttons and axes
            float leftX = ((data[1] & 0xFF) - 128) / 127.0f;
            float leftY = ((data[2] & 0xFF) - 128) / 127.0f;
            float rightX = ((data[3] & 0xFF) - 128) / 127.0f;
            float rightY = ((data[4] & 0xFF) - 128) / 127.0f;

            int dpad = data[5] & 0x0F;
            String dpadDir = switch (dpad) {
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

            boolean square = (data[5] & 0x10) != 0;
            boolean cross = (data[5] & 0x20) != 0;
            boolean circle = (data[5] & 0x40) != 0;
            boolean triangle = (data[5] & 0x80) != 0;

            boolean l1 = (data[6] & 0x01) != 0;
            boolean r1 = (data[6] & 0x02) != 0;
            boolean l2Button = (data[6] & 0x04) != 0;
            boolean r2Button = (data[6] & 0x08) != 0;
            boolean create = (data[6] & 0x10) != 0;
            boolean options = (data[6] & 0x20) != 0;
            boolean l3 = (data[6] & 0x40) != 0;
            boolean r3 = (data[6] & 0x80) != 0;

//            System.out.println("------ Short Bluetooth Report ------");
//            System.out.printf("Left Stick: (%.2f, %.2f)%n", leftX, leftY);
//            System.out.printf("Right Stick: (%.2f, %.2f)%n", rightX, rightY);

            if (!dpadDir.equals("Neutral")) {
                System.out.println("D-pad: " + dpadDir);
            }

            if (square) System.out.println("Square pressed");
            if (circle) System.out.println("Circle pressed");
            if (triangle) System.out.println("Triangle pressed");
            if (l1) System.out.println("L1 pressed");
            if (r1) System.out.println("R1 pressed");
            if (l2Button) System.out.println("L2 button pressed");
            if (r2Button) System.out.println("R2 button pressed");
            if (create) System.out.println("Create pressed");
            if (l3) System.out.println("L3 pressed");
            if (r3) System.out.println("R3 pressed");

            if (gamePanel.gameState == GameState.TITLE_SCREEN){
                if (cross){
                    if (gamePanel.uiController.commandNumber == 0) {
                        this.gamePanel.gameState = GameState.PLAYING;
                    }
                }

            }
            else if (gamePanel.gameState == GameState.PLAYING) {

                if (options) {
                    pauseCounter++;

                    System.out.println("Options pressed");

                    if (pauseCounter == maxCounter) {
                        //move game to paused state
                        this.gamePanel.switchGamePauseState();
                        pauseCounter = 0;
                    }
                }
                if(create){
                         pauseCounter++;

                        System.out.println("Create pressed");

                        if (pauseCounter == maxCounter) {
                            //move game to paused state
                            this.gamePanel.switchGameCharacterState();
                            pauseCounter = 0;
                        }

                }

                if(cross){
                    gamePanel.keyH.shootKeyPressed = true;
                }else {
                    gamePanel.keyH.shootKeyPressed = false;
                }

            }
            else if (gamePanel.gameState == GameState.PAUSED) {
                if (options) {
                    pauseCounter++;

                    System.out.println("Options pressed");

                    if (pauseCounter == maxCounter) {
                        //move game to paused state
                        this.gamePanel.switchGamePauseState();
                        pauseCounter = 0;
                    }
                }
            }
            else if (gamePanel.gameState == GameState.DIALOG_STATE) {
                if(cross) this.gamePanel.gameState = GameState.PLAYING;
            }
            else if (gamePanel.gameState == GameState.CHARACTER_STATE) {

                if(cross) {
                    gamePanel.player.selectItem();
                };

                if(create){
                    pauseCounter++;

                    System.out.println("Create pressed");

                    if (pauseCounter == maxCounter) {
                        //move game to paused state
                        this.gamePanel.switchGameCharacterState();
                        pauseCounter = 0;
                    }

                }
            }

            parseDirectionalInput(dpadDir);

        }
        else if (reportId == 0x31 && data.length >= 78) {
            // Extended Bluetooth report with motion and touchpad data
            float leftX = ((data[1] & 0xFF) - 128) / 127.0f;
            float leftY = ((data[2] & 0xFF) - 128) / 127.0f;
            float rightX = ((data[3] & 0xFF) - 128) / 127.0f;
            float rightY = ((data[4] & 0xFF) - 128) / 127.0f;

            float l2Trigger = ((data[5] & 0xFF)) / 255.0f;
            float r2Trigger = ((data[6] & 0xFF)) / 255.0f;

            int dpad = data[8] & 0xF;
            String dpadDir = switch (dpad) {
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

            // Motion sensors
            int gyroX = ((data[15] <<8)| data [16]);

        }
    }

    private void parseDirectionalInput(String dpadDir) {

        if(gamePanel.gameState == GameState.PLAYING){

        switch (dpadDir) {
            case "Neutral" -> {
                //release any pressed key
                this.upPressed = false;
                this.downPressed = false;
                this.leftPressed = false;
                this.rightPressed = false;
            }
            case "N" -> this.upPressed = true;
            case "S" -> this.downPressed = true;
            case "W" -> this.leftPressed = true;
            case "E" -> this.rightPressed = true;
        }
        
        } else if (gamePanel.gameState == GameState.PAUSED) {
            
            
        } else if (gamePanel.gameState == GameState.TITLE_SCREEN) {
            if(dpadDir != null){

                    pauseCounter++;

                    if (pauseCounter == maxCounter) {
                        //move game to paused state

                        switch (dpadDir) {
                            case "Neutral" -> {
                                //release any pressed key
                                this.upPressed = false;
                                this.downPressed = false;
                                this.leftPressed = false;
                                this.rightPressed = false;
                            }
                            case "N" ->  gamePanel.uiController.previousCommandNumber();
                            case "S" -> gamePanel.uiController.nextCommandNumber();
                        }


                        pauseCounter = 0;
                    }

            }

        } else if (gamePanel.gameState == GameState.CHARACTER_STATE) {

            if(dpadDir != null){

                pauseCounter++;

                if (pauseCounter == maxCounter) {
                    //move game to paused state

                    switch (dpadDir) {
                        case "Neutral" -> {
                            //release any pressed key
                        }
                        case "N" -> {
                            if (gamePanel.uiController.slotRow > 0) gamePanel.uiController.slotRow--;
                        }
                        case "S" -> {
                            if ( gamePanel.uiController.slotRow <3)  gamePanel.uiController.slotRow++;
                        }
                        case "W" -> {
                            if (gamePanel.uiController.slotCol > 0) gamePanel.uiController.slotCol--;
                        }
                        case "E" -> {
                            if ( gamePanel.uiController.slotCol <4) gamePanel.uiController.slotCol++;
                        }
                    }



                    pauseCounter = 0;
                }

            }

            }
    }

    private void shutdown() {
        running = false;
        if (controller != null) {
            controller.close();
        }
    }

    @Override
    public void hidDeviceAttached(HidServicesEvent event) {
        // Not used in this demo
    }

    @Override
    public void hidDeviceDetached(HidServicesEvent event) {
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

    private void setHapticFeedback(byte leftMotorIntensity, byte rightMotorIntensity) {
        // Construct the control report
        byte[] outputReport = new byte[64]; // Control reports are typically 64 bytes
        outputReport[0] = 0x05; // Report ID for haptics (example; may vary)
        outputReport[1] = (byte) 0xFF; // Command type (example; may vary)
        outputReport[2] = 0x00; // Reserved
        outputReport[3] = leftMotorIntensity;  // Left motor intensity (0x00 to 0xFF)
        outputReport[4] = rightMotorIntensity; // Right motor intensity (0x00 to 0xFF)
        outputReport[5] = 0x00; // Reserved
        outputReport[6] = 0x00; // Reserved
        outputReport[7] = 0x01; // Enable adaptive trigger effect
        outputReport[8] = 0x40; // Set trigger stiffness level


        // Send the control report to the controller
        int bytesWritten = controller.write(outputReport, outputReport.length, (byte) 0);
        if (bytesWritten < 0) {
            System.err.println("Failed to send haptic feedback command");
        }
    }


}
