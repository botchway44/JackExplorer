package controller;

import view.GamePanel;

import java.awt.*;

public class UI {
    GamePanel gamePanel;

    Font font = new Font("Arial", Font.PLAIN, 20);

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void draw(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("Welcome to Jack TheExplorer", 10, 20);
    }
}
