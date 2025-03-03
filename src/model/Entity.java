package model;

import utilities.EntityDirection;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public int worldX, worldY;
    public int speed;


    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public EntityDirection direction = EntityDirection.DOWN; //@Todo : make this an enum of directions

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int solidAreaDefaultX, solidAreaDefaultY;
	
    public Rectangle solidArea;
    public boolean collisionOn = false;
    private GamePanel gamePanel;
    public int actionLockCounter = 0;
    public Entity( ) {
    }


    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.solidArea = new Rectangle(10, 18, 30, 30);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }




    public void draw(Graphics2D g2) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        BufferedImage image = null;

        if (
                (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX) &&
                        (worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX) &&
                        (worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY) &&
                        (worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY)
        ) {


            switch (direction) {
                case UP:

                    if (spriteNum == 1) {
                        image = up1;
                    } else if (spriteNum == 2) {
                        image = up2;
                    }
                    break;

                case DOWN:

                    if (spriteNum == 1) {
                        image = down1;
                    } else if (spriteNum == 2) {
                        image = down2;
                    }

                    break;

                case RIGHT:
//			image = right1;

                    if (spriteNum == 1) {
                        image = right1;
                    } else if (spriteNum == 2) {
                        image = right2;
                    }

                    break;

                case LEFT:
//			image = left1;

                    if (spriteNum == 1) {
                        image = left1;
                    } else if (spriteNum == 2) {
                        image = left2;
                    }

                    break;
            }


            g2.drawImage(image, screenX, screenY, null);
        }

    }


    public void setAction(){

    }

    public void update() {
        this.setAction();
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayerCollision(this);
        //if the collision is false, player can move
        if (!collisionOn) {
            switch (direction) {
                case UP:
                    this.worldY -= this.speed;
                    break;
                case DOWN:
                    this.worldY += this.speed;
                    break;
                case LEFT:
                    this.worldX -= this.speed;
                    break;
                case RIGHT:
                    this.worldX += this.speed;
                    break;
            }
        }

        this.spriteCounter++;

        if (this.spriteCounter > 10) {
            if (this.spriteNum == 1) {
                this.spriteNum = 2;
            } else if (this.spriteNum == 2) {
                this.spriteNum = 1;
            }

            this.spriteCounter = 0;
        }

    }


    //GETTERS AND SETTERS
    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public void setUp1(BufferedImage up1) {
        this.up1 = up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public void setUp2(BufferedImage up2) {
        this.up2 = up2;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public void setDown1(BufferedImage down1) {
        this.down1 = down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public void setDown2(BufferedImage down2) {
        this.down2 = down2;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public void setLeft1(BufferedImage left1) {
        this.left1 = left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public void setLeft2(BufferedImage left2) {
        this.left2 = left2;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public void setRight1(BufferedImage right1) {
        this.right1 = right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public void setRight2(BufferedImage right2) {
        this.right2 = right2;
    }

    public EntityDirection getDirection() {
        return direction;
    }

    public void setDirection(EntityDirection direction) {
        this.direction = direction;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
}
