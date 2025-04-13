package model;

import utilities.Entities;
import utilities.EntityDirection;
import view.GamePanel;

import java.util.Random;

public class Enemy_GreenSlime extends Entity {
    public Enemy_GreenSlime (GamePanel panel) {
        super(panel);

        type = Entities.MONSTER;

        name = "Enemy Green Slime";
        speed = 1;
        maxLife = 30;
        life = maxLife;

        attack = 5;
        defense = 0;
        exp = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }


    public void getImage(){
        up1 = setup("/objects/slime/greenslime_down_1.png");
        up2 = setup("/objects/slime/greenslime_down_2.png");

        down1 = setup("/objects/slime/greenslime_down_1.png");
        down2 = setup("/objects/slime/greenslime_down_2.png");

        left1 = setup("/objects/slime/greenslime_down_1.png");
        left2 = setup("/objects/slime/greenslime_down_2.png");

        right1 = setup("/objects/slime/greenslime_down_1.png");
        right2 = setup("/objects/slime/greenslime_down_2.png");
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

    public void damageReaction(){
            actionLockCounter = 0;
            direction = super.gamePanel.player.direction;
    }

}
