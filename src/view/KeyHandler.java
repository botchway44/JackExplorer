package view;

import utilities.GameState;
import utilities.KeyPressEvent;
import utilities.SoundAssets;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {


    private GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed = false, showDebug = false, shootKeyPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gamePanel.gameState == GameState.TITLE_SCREEN){
          titleState(code);
        }
        else if (gamePanel.gameState == GameState.PLAYING) {
            playingState(code);
        }
        else if (gamePanel.gameState == GameState.PAUSED) {
            pausedState(code);
        }
        else if (gamePanel.gameState == GameState.DIALOG_STATE) {
            dialogState(code);
        }
        else if (gamePanel.gameState == GameState.CHARACTER_STATE) {
            characterState(code);
        }


    }

    public void characterState(int code){
        if (code == KeyEvent.VK_C) {
            this.gamePanel.gameState = GameState.PLAYING;
        }

       else if(code == KeyEvent.VK_W){
           if ( gamePanel.uiController.slotRow >0) gamePanel.uiController.slotRow--;
        }
        else if(code == KeyEvent.VK_A){
            if ( gamePanel.uiController.slotCol > 0) gamePanel.uiController.slotCol--;
        }
        else if(code == KeyEvent.VK_S){
            if ( gamePanel.uiController.slotRow <3)  gamePanel.uiController.slotRow++;
        }
        else if(code == KeyEvent.VK_D){
            if ( gamePanel.uiController.slotCol <4) gamePanel.uiController.slotCol++;
        }
        else if(code == KeyEvent.VK_ENTER){
            gamePanel.player.selectItem();
        }

        gamePanel.playSoundEffect(SoundAssets.CURSOR);
    }
    public void dialogState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            this.gamePanel.gameState = GameState.PLAYING;
        }
    }

    public void pausedState(int code) {
        if (code == KeyEvent.VK_P) {
            this.gamePanel.gameState = GameState.PLAYING;
        }
    }

    public void playingState(int code){

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
       else if (code == KeyEvent.VK_C) {
            this.gamePanel.gameState = GameState.CHARACTER_STATE;
        }
       else if (code == KeyEvent.VK_SPACE) {
            shootKeyPressed  = true;
        }

    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.uiController.previousCommandNumber();
        } else if (code == KeyEvent.VK_S) {
            gamePanel.uiController.nextCommandNumber();
        } else if (code == KeyEvent.VK_ENTER) {

            if(gamePanel.uiController.commandNumber == 0){
                this.gamePanel.gameState = GameState.PLAYING;
//                    gamePanel.playMusic();
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


        //DEBUG
        if (code == KeyEvent.VK_Q) {
            this.showDebug = !showDebug;
        }

        //REFRESH
        if (code == KeyEvent.VK_R) {
            gamePanel.tileManager.loadMap(gamePanel.tileManager.currentMap);

        }

        if (code == KeyEvent.VK_SPACE) {
            this.shootKeyPressed =  false;
        }
    }

//    public void keyPressed(KeyPressEvent e) {
//        System.out.println("Called here : " + this.upPressed);
//
//        if (e == KeyPressEvent.UP) {
//
//            this.upPressed = true;
//            System.out.println("Direction here : " + this.upPressed);
//        } else if (e == KeyPressEvent.DOWN) {
//            this.downPressed = true;
//        } else if (e == KeyPressEvent.LEFT) {
//            this.leftPressed = true;
//        } else if (e == KeyPressEvent.RIGHT) {
//            this.rightPressed = true;
//        } else if (e == KeyPressEvent.PAUSE) {
//            //pause Game
//            if (this.gamePanel.gameState == GameState.PAUSED) {
//                this.gamePanel.gameState = GameState.PLAYING;
//            } else if (this.gamePanel.gameState == GameState.PLAYING) {
//                this.gamePanel.gameState = GameState.PAUSED;
//            }
//        }
//
//    }
}
