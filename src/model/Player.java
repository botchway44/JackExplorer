package model;

import object.SuperObject;
import utilities.*;
import view.GamePanel;
import view.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity {

    GamePanel gp;
    public KeyHandler keyH; //Change back to private

    public final int screenX;
    public final int screenY;

    private int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);


        this.solidArea = new Rectangle(10, 18, 30, 30);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.setDefaultValues();
        this.getPlayerImage();

    }


//    @Override
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = EntityDirection.DOWN;
    }


    public void getPlayerImage() {
        try {

            up1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png"))), gp.tileSize, gp.tileSize);
            up2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png"))), gp.tileSize, gp.tileSize);

            down1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png"))), gp.tileSize, gp.tileSize);
            down2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png"))), gp.tileSize, gp.tileSize);

            right1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png"))), gp.tileSize, gp.tileSize);
            right2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png"))), gp.tileSize, gp.tileSize);

            left1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png"))), gp.tileSize, gp.tileSize);
            left2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png"))), gp.tileSize, gp.tileSize);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {


            if (this.keyH.upPressed == true) {
//			this.worldY -= this.speed;
                this.direction = EntityDirection.UP;
            }

            if (this.keyH.downPressed == true) {
//			this.worldY += this.speed;
                this.direction = EntityDirection.DOWN;
            }

            if (this.keyH.leftPressed == true) {
//			this.worldX -= this.speed;
                this.direction = EntityDirection.LEFT;
            }

            if (this.keyH.rightPressed == true) {
//			this.worldX += this.speed;
                this.direction = EntityDirection.RIGHT;
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            //Check object collision
            int objectIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            //Check NPC Collision
            Entities e = gp.collisionChecker.checkEntityCollision(this,gp.npcs);
            interactWithNPC(e);

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


        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    private void interactWithNPC(Entities e) {
        if(e != null) {
            System.out.println("interactWithNPC"+ e.toString());
        }
    }

    private void pickUpObject(int objectIndex) {
        if (objectIndex >= 0 && objectIndex < gp.gameObjects.length) {
            SuperObject object = gp.gameObjects[objectIndex];
//            System.out.println();
            this.gp.gameObjects[objectIndex] = null;

            if (Objects.equals(object.name, Constants.BOOTS)) {
                speed += 5;
                this.gp.playSoundEffect(SoundAssets.POWERUP);

                this.gp.uiController.showMessage("You picked up a Boots");

            } else if (Objects.equals(object.name, Constants.KEY)) {
                this.gp.playSoundEffect(SoundAssets.COIN);
                this.gp.uiController.showMessage("You picked up a key");
            } else if (Objects.equals(object.name, Constants.DOOR)) {
                this.gp.playSoundEffect(SoundAssets.UNLOCK);
            }
            //Pick up Like keys and use the keys to open doors

        }
    }

     public void draw(Graphics g2) {

//		g2.setColor(Color.white);
//		g2.fillRect(this.x, this.y, this.gp.tileSize, this.gp.tileSize);

        BufferedImage image = null;

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


        //COLLISIONS DEBUGGER
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
