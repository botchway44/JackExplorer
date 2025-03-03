package controller;

import object.OBJ_Key;
import utilities.GameState;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIController {
    private GamePanel gamePanel;
    private Graphics2D graphics2D;
    Font font = new Font("Arial", Font.PLAIN, 20);

    BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    public int notificationCounter = 0;

    public UIController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.keyImage = new OBJ_Key().image;
    }


    public void draw(Graphics2D g2) {
        this.graphics2D = g2;
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        if (gamePanel.gameState == GameState.PLAYING) {
            //Show Playing UI
        } else if (gamePanel.gameState == GameState.PAUSED) {
            //Show paused UI
            drawPausedScreen();

        }
        g2.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);
        g2.drawString("X ", 74, 65);

        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gamePanel.tileSize, gamePanel.tileSize * 5);

            notificationCounter++;
            if (notificationCounter > 90) {
                messageOn = false;
                notificationCounter = 0;

            }
        }

    }

    public void drawPausedScreen() {

        String text = "Paused";

        int x = getXForCenteredText(text);

        int y = this.gamePanel.screenHeight / 2;

        this.graphics2D.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int textWidth = (int) this.graphics2D.getFontMetrics().getStringBounds(text, this.graphics2D).getWidth();
        return this.gamePanel.screenWidth / 2 - textWidth / 2;
    }

    public void showMessage(String message) {
        this.message = message;
        this.messageOn = true;
//        g2.drawRect(gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize / 2, gamePanel.tileSize / 2);
//        g2.clearRect();
    }
}
