package controller;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;
import utilities.GameState;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class UIController {
    public BufferedImage heartFull, heartBlank, heartHalf;
    private GamePanel gamePanel;
    private Graphics2D graphics2D;
    Font maruMonic, purisaB;
    Font font = new Font("Arial", Font.PLAIN, 20);

    BufferedImage keyImage;

    public boolean messageOn = false;
//    public String message = "";
//    public int notificationCounter = 0;
    private ArrayList<String> message = new ArrayList<>();
    private ArrayList<Integer> messageCounter = new ArrayList<>();

    public int slotCol=0;
    public int slotRow=0;

    public String currentDialog = "";
    public int commandNumber = 2;

    public UIController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.keyImage = new OBJ_Key(gamePanel).image;

        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            assert is != null;
            this.maruMonic = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/PurisaBold.ttf");
            assert is != null;
            this.purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (Exception e) {
            e.printStackTrace();
        }


        //CREATE HEART IMAGE
        OBJ_Heart heart = new OBJ_Heart(this.gamePanel);
        this.heartFull = heart.image3;
        this.heartHalf = heart.image2;
        this.heartBlank = heart.image;

    }


    public void draw(Graphics2D g2) {
        this.graphics2D = g2;
        g2.setFont(maruMonic);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        if (gamePanel.gameState == GameState.PLAYING) {
            //Show Playing UI
            drawPlayerLife();
            drawMessage();
        } else if (gamePanel.gameState == GameState.PAUSED) {
            //Show paused UI
            drawPlayerLife();
            drawPausedScreen();
            drawCharacterScreen();

        } else if (gamePanel.gameState == GameState.DIALOG_STATE) {
            drawDialogScreen();
        } else if (gamePanel.gameState == GameState.TITLE_SCREEN) {
            drawTitleScreen();
        } else if (gamePanel.gameState == GameState.CHARACTER_STATE) {
            drawCharacterScreen();
            drawInventory();
        }

//        g2.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);
//        g2.drawString("X ", 74, 65);
//
//        if (messageOn) {
//            g2.setFont(g2.getFont().deriveFont(30F));
//            g2.drawString(message, gamePanel.tileSize, gamePanel.tileSize * 5);
//
//            notificationCounter++;
//            if (notificationCounter > 90) {
//                messageOn = false;
//                notificationCounter = 0;
//
//            }
//        }

    }


    public void drawInventory(){
        final int frameX = gamePanel.tileSize * 9;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 6;
        final int frameHeight = gamePanel.tileSize * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXStart = frameX  + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;


        int slotSize = gamePanel.tileSize + 3;

        //CURSOR
        int cursorX = slotXStart + (slotSize  * slotCol);
        int cursorY = slotYStart + + (slotSize  * slotRow);
        int cursorWidth = gamePanel.tileSize ;
        int cursorHeight = gamePanel.tileSize ;

        //DRAW PLAYER ITEMS
        for (int i = 0; i < gamePanel.player.inventory.size(); i++) {

            //EQUIP CURSOR
            if(gamePanel.player.inventory.get(i)== gamePanel.player.currentWeapon || gamePanel.player.inventory.get(i) == gamePanel.player.currentShield){
                graphics2D.setColor(new Color(240,190,90));
                graphics2D.fillRoundRect(slotX, slotY, gamePanel.tileSize,  gamePanel.tileSize, 10,10);
            }

            graphics2D.drawImage(gamePanel.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;

            if(i ==4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //DRAW CURSOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);


        //DESCRIPTION
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gamePanel.tileSize * 3;

        //DRAW TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gamePanel.tileSize;
        graphics2D.setFont(graphics2D.getFont().deriveFont(28F));

        int itemIndex = getIntemIndex();
        if(itemIndex < gamePanel.player.inventory.size()){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            String description = gamePanel.player.inventory.get(itemIndex).description;
            for(String line : description.split("\n")){
                graphics2D.drawString(line, textX, textY);
                textY += 32;
            }
        }

    }


    public int getIntemIndex(){
       return slotCol + (slotRow *5);
    }

    public void drawMessage(){
        int messageX  = gamePanel.tileSize;
        int messageY  = gamePanel.tileSize* 4;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 22F));


        for (int i = 0; i < message.size(); i++) {
           if( message.get(i) != null){
               graphics2D.setColor(Color.WHITE);
               graphics2D.drawString(message.get(i), messageX + 2, messageY + 2);

               int counter = messageCounter.get(i) + 1; //increase messageCounter +1
               messageCounter.set(i, counter); //
               messageY += 50;

               if(messageCounter.get(i) >= 180){
                   message.remove(i);
                   messageCounter.remove(i);
               }
           }
        }
    }

    public void drawCharacterScreen(){
        // CREATE A FRAME
        final int frameX = gamePanel.tileSize;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        int textX = 20 + frameX;
        int textY = gamePanel.tileSize + frameY;

        final int lineHeight = 32;

        //NAMES
        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Life", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Strength", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Attack", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Defense", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Exp", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Coin", textX, textY);
        textY += lineHeight + 30;

        graphics2D.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;

        graphics2D.drawString("Shield", textX, textY);
        textY += lineHeight ;


        //VALUES
        int tailX = (frameX + frameWidth) - 30;
        //Reset textY
        textY =  frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.life + " / " + gamePanel.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight ;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        graphics2D.drawImage(gamePanel.player.currentWeapon.down1, tailX- gamePanel.tileSize, textY, null);
        textY += gamePanel.tileSize;
        graphics2D.drawImage(gamePanel.player.currentShield.down1, tailX- gamePanel.tileSize, textY, null);

    }

    public void  drawPlayerLife(){

        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i=0;

        while(i < gamePanel.player.maxLife/2){
            graphics2D.drawImage(heartBlank, x, y, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
            i++;
            x += gamePanel.tileSize / 2;
        }


        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i=0;

        while(i < gamePanel.player.life){
            graphics2D.drawImage(heartHalf, x, y, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
            i++;
            if(i<gamePanel.player.life){
                graphics2D.drawImage(heartFull, x, y, gamePanel.tileSize/2, gamePanel.tileSize/2, null);
            }
            i++;
            x += gamePanel.tileSize / 2;
        }
    }

    public void drawTitleScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 80F));
        String text = "Tank Shooter";
        int x = getXForCenteredText(text);
        int y = gamePanel.tileSize * 2;

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x, y);

        // Draw the Player Image
        x = gamePanel.screenWidth / 2 - (gamePanel.tileSize*2)/2;
        y = gamePanel.tileSize * 3;
        graphics2D.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize*2, gamePanel.tileSize*2, null);


        //MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 30F));

        text = "New Game";
        x = getXForCenteredText(text);
        y = gamePanel.tileSize * 6;
        graphics2D.drawString(text, x, y);
        if(this.commandNumber == 0){
            graphics2D.drawString(">", x - gamePanel.tileSize/2, y);
        }
        text = "Load Game";
        x = getXForCenteredText(text);
        y = gamePanel.tileSize * 7;
        graphics2D.drawString(text, x, y);
        if(this.commandNumber == 1){
            graphics2D.drawString(">", x - gamePanel.tileSize/2, y);
        }

        text = "Quit";
        x = getXForCenteredText(text);
        y = gamePanel.tileSize * 8;
        graphics2D.drawString(text, x, y);
        if(this.commandNumber == 2){
            graphics2D.drawString(">", x - gamePanel.tileSize/2, y);
        }
    }

    public void setCurrentDialog(String dialog) {
        this.currentDialog = dialog;
    }

    public void drawDialogScreen() {
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for (String line : currentDialog.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;

        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        graphics2D.setColor(c);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);


        c = new Color(255, 255, 255);
        graphics2D.setColor(c);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x, y, width, height, 25, 25);
    }

    public void drawPausedScreen() {

        String text = "Paused";

//        int x = getXForCenteredText(text);
//
//        int y = this.gamePanel.screenHeight / 2;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));
        graphics2D.setColor(Color.WHITE);
        this.graphics2D.drawString(text, gamePanel.tileSize * 2, gamePanel.tileSize * 4);
    }

    public int getXForCenteredText(String text) {
        int textWidth = (int) this.graphics2D.getFontMetrics().getStringBounds(text, this.graphics2D).getWidth();
        return this.gamePanel.screenWidth / 2 - textWidth / 2;
    }

    public int getXForAlignToRightText(String text, int tailX) {
            int length = (int) graphics2D.getFontMetrics().getStringBounds(text, this.graphics2D).getWidth();
            return tailX - length;
    }

    public void addMessage(String text) {
//        this.message = message;
//        this.messageOn = true;
//        g2.drawRect(gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize / 2, gamePanel.tileSize / 2);
//        g2.clearRect();

        message.add(text);
        messageCounter.add(0);
    }



    public void nextCommandNumber() {
        this.commandNumber = (commandNumber + 1) % 3;
    }

    public void previousCommandNumber() {
        this.commandNumber = (commandNumber - 1) % 3;
        if(commandNumber <= 0){
            this.commandNumber = 0;
        }

    }
}
