package view;

import utilities.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {


    private GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            this.upPressed = true;
        } else if (code == KeyEvent.VK_S) {
            this.downPressed = true;
        } else if (code == KeyEvent.VK_A) {
            this.leftPressed = true;
        } else if (code == KeyEvent.VK_D) {
            this.rightPressed = true;
        } else if (code == KeyEvent.VK_P) {
            //pause Game
            if (this.gamePanel.gameState == GameState.PAUSED) {
                this.gamePanel.gameState = GameState.PLAYING;
            } else if (this.gamePanel.gameState == GameState.PLAYING) {
                this.gamePanel.gameState = GameState.PAUSED;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            this.upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            this.downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            this.leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            this.rightPressed = false;
        }

    }


}
