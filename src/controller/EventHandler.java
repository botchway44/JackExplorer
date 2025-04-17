package controller;

import utilities.EntityDirection;
import utilities.GameState;
import view.GamePanel;

import java.awt.*;

public class EventHandler {

    public GamePanel gamePanel;
    private EventRect eventRect[][];
    private int previousEventX, previousEventY;
    boolean canTouchEvent = false;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.eventRect = new EventRect[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            this.eventRect[col][row] = new EventRect();
            this.eventRect[col][row].x = 23;
            this.eventRect[col][row].y = 23;
            this.eventRect[col][row].width = 2;
            this.eventRect[col][row].height = 2;
            this.eventRect[col][row].eventRectDefaultX = this.eventRect[col][row].x;
            this.eventRect[col][row].eventRectDefaultY = this.eventRect[col][row].y;

            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }

        }


    }


    public void checkEvent() {

//        check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gamePanel.player.worldX - this.previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - this.previousEventY);
        int distance = Math.max(xDistance , yDistance);

        if(distance > gamePanel.tileSize){
            canTouchEvent = true;
        }

        if(!canTouchEvent) return;


//        if (hit(23, 21, EntityDirection.ALL)) {
//            damageBomb(23,21,GameState.DIALOG_STATE);
//            System.out.println("TRIGGER BOMB");
//        }
//
//        if (hit(23, 12, EntityDirection.UP)) {
//            this.healingPool(23,21, GameState.DIALOG_STATE);
//        }
//
//        if (hit(27, 16, EntityDirection.ALL)) {
//            this.teleport(GameState.DIALOG_STATE);
//        }
    }


    public boolean hit(int col, int row, EntityDirection reqDirection) {
        boolean hit = false;

        System.out.println((gamePanel.player.worldX / gamePanel.tileSize) + "=X  Y=" + (gamePanel.player.worldY / gamePanel.tileSize));
        System.out.println(gamePanel.player.screenX + "=X  Y=" + gamePanel.player.screenY);
        System.out.println(row + "=EnventRow  Col=" + col);


        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        eventRect[col][row].x = col * gamePanel.tileSize + eventRect[col][row].x;

        eventRect[col][row].y = row * gamePanel.tileSize + eventRect[col][row].y;

        if (gamePanel.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            if (gamePanel.player.direction == reqDirection || reqDirection == EntityDirection.ALL) {
                hit = true;
                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        System.out.println("CHECKING HIT ::" + hit);
        return hit;
    }


    public void damageBomb(int col, int row,GameState gameState) {
        gamePanel.gameState = gameState;
        gamePanel.uiController.currentDialog = "You Fall into a pit";
        gamePanel.player.life--;
        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row,GameState gameState) {

//        if(gamePanel.keyH.enterPressed){
        gamePanel.gameState = gameState;
        gamePanel.uiController.currentDialog = "You Fell into the pool. \nYour life has been restored";
        gamePanel.player.life = gamePanel.player.maxLife;
        gamePanel.assetSetter.setMonsters();

//        }


    }

    public void teleport(GameState gameState) {

//        if(gamePanel.keyH.enterPressed){
        gamePanel.gameState = gameState;
        gamePanel.uiController.currentDialog = "Teleport";
        gamePanel.player.worldX = gamePanel.tileSize * 37;
        gamePanel.player.worldY = gamePanel.tileSize * 10;


//        }


    }
}
