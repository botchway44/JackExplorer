package model;

import utilities.EntityDirection;
import utilities.UtilityTool;
import view.GamePanel;
import view.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPCOldMan extends Entity {

    private KeyHandler keyHandler;
    private GamePanel gamePanel;

    public NPCOldMan(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
//        this.keyHandler = keyHandler;
        this.getImages();
    }


    public void getImages() {
        try {

            up1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-up-1.png"))), gamePanel.tileSize, gamePanel.tileSize);
            up2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-up-1.png"))), gamePanel.tileSize, gamePanel.tileSize);

            down1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-down-1.png"))), gamePanel.tileSize, gamePanel.tileSize);
            down2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-down-1.png"))), gamePanel.tileSize, gamePanel.tileSize);

            right1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-right-1.png"))), gamePanel.tileSize, gamePanel.tileSize);
            right2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-right-1.png"))), gamePanel.tileSize, gamePanel.tileSize);

            left1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-left-1.png"))), gamePanel.tileSize, gamePanel.tileSize);
            left2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/amor_cars/npc-left-1.png"))), gamePanel.tileSize, gamePanel.tileSize);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter >= 100) {

            Random rand = new Random();
            int randomNum = rand.nextInt(100) + 1;


            //Divide the number into 4 quads and use each quad as a direction
            if (randomNum <= 25) {
                direction = EntityDirection.UP;
            } else if (randomNum <= 50) {
                direction = EntityDirection.DOWN;
            } else if (randomNum <= 75) {
                direction = EntityDirection.LEFT;
            } else if (randomNum < 100) {
                direction = EntityDirection.RIGHT;
            }

            actionLockCounter = 0;
        }

    }


    @Override
    public void speak() {
       super.speak();
    }
}
