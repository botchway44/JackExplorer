package model;

import object.Projectile;
import utilities.Entities;
import utilities.EntityDirection;
import utilities.SoundAssets;
import utilities.UtilityTool;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Entity  implements Cloneable  {

    public BufferedImage image;
    public String name;
    public boolean collision = false;

    public boolean invincible = false;
    public int invincibleCounter = 0;

    public int worldX, worldY;
    public int speed;

    public Entities type;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public EntityDirection direction = EntityDirection.DOWN;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int solidAreaDefaultX, solidAreaDefaultY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = true;
    public GamePanel gamePanel;
    public int actionLockCounter = 0;
    public ArrayList<String> dialogs = new ArrayList<>();
    public int dialogIndex = 0;

    //CHARACTER STATUS
    public int maxLife;
    public int life;

   public boolean alive = true;
   public boolean dying = false;

   int dyingCounter = 0;

   public boolean hpBarOn = false;
   public int hpBarCounter = 0;

   public int level;
   public int strength;
   public int dexterity;
   public int attack;
   public int defense;
   public int exp;
   public int nextLevelExp;
   public int coin;
   public Entity currentWeapon;
   public Entity currentShield;

   public int attackValue;
   public int defenseValue;
   public String description = "";

   public int maxMana;
   public int mana;
   public Projectile projectile;
   public Projectile defaultProjectile;

    public int useCost;


    public Entity() {
    }


    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.solidArea = new Rectangle(10, 18, 30, 30);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.setDialogs();
    }

    public BufferedImage setup(String path) {
        BufferedImage image = null;

        try{
            image = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path))), gamePanel.tileSize, gamePanel.tileSize);
        }catch (Exception e){
            e.printStackTrace();
        }

        return image;
    }

    public BufferedImage setup(String path, int width, int height) {
        BufferedImage image = null;

        try{
            image = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path))), width, height);
        }catch (Exception e){
            e.printStackTrace();
        }

        return image;
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


        }

        if(invincible){
            hpBarOn = true;
            hpBarCounter=0;
            changeAlpha(g2, 0.4f);
        }

        if(dying){
            dyingAnimation(g2);
        }

        g2.drawImage(image, screenX, screenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //COLLISIONS DEBUGGER
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);


        //DEBUG TEXT
//        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
//        g2.setColor(Color.WHITE);
//        g2.drawString("INVINCIBLE COUNTER : " + invincibleCounter, 10, 400);


        if(type == Entities.MONSTER && hpBarOn){

            double oneScale = (double) gamePanel.tileSize/maxLife;
            double hpBarValue = oneScale * life;

            g2.setColor(new Color(35,35, 35));
            g2.fillRect(screenX-1, screenY - 16, gamePanel.tileSize+2, 12);

            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

            hpBarCounter++;

            if(hpBarCounter > 600){
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

    }

    public void setDialogs() {
        this.dialogs.add("Hello Soldier, ");
        this.dialogs.add("The Enemies are attacking us, \nwe need a way to attack faster");
    }

    public void setAction() {

    }

    public void dyingAnimation( Graphics2D g2) {
        dyingCounter++;

        if(dyingCounter <= 5 ){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            changeAlpha(g2,0);
        }

        if (dyingCounter > 5 && dyingCounter <= 10){
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            changeAlpha(g2,1);
        }

        if (dyingCounter > 10 && dyingCounter <= 15){
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            changeAlpha(g2,0);
        }

        if (dyingCounter > 15 && dyingCounter <= 20){
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            changeAlpha(g2,1);
        }

        if (dyingCounter > 20 && dyingCounter <= 25){
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            changeAlpha(g2,0);
        }

        if (dyingCounter > 25 && dyingCounter <= 30){
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            changeAlpha(g2,1);
        }

        if (dyingCounter > 30 && dyingCounter <= 35){
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            changeAlpha(g2,0);
        }

        if (dyingCounter > 35 && dyingCounter <= 40){
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            changeAlpha(g2,1);
        }

        if(dyingCounter > 40 ){
//            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

    }

    public void update() {
        this.setAction();
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);

        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npcs);
        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monsters);
        boolean contactPlayer =  gamePanel.collisionChecker.checkPlayerCollision(this);

        if(this.type == Entities.MONSTER && contactPlayer) {
            if (!gamePanel.player.invincible){

                gamePanel.playSoundEffect(SoundAssets.RECEIVE_DAMAGE);

                int damage = attack - gamePanel.player.defense;
                if(damage < 0){
                    damage = 0;
                }
                gamePanel.player.life -=damage;


                gamePanel.player.life -=1;
                gamePanel.player.invincible = true;

                gamePanel.playSoundEffect(SoundAssets.HIT_MONSTER);


            }
        }

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

        //count player invisibility
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter >60 ){
                invincibleCounter = 0;
                invincible = false;
            }
        }

    }

    public void damageReaction(){

    }

    public void speak() {
        gamePanel.uiController.setCurrentDialog(this.dialogs.get(this.dialogIndex));
        this.dialogIndex++;

        if (dialogIndex > this.dialogs.size() - 1) {
            dialogIndex = this.dialogs.size() - 1;
        }

        switch (gamePanel.player.direction) {
            case UP:
                direction = EntityDirection.UP;
                break;
            case DOWN:
                direction = EntityDirection.DOWN;
                break;
            case LEFT:
                direction = EntityDirection.LEFT;
                break;
            case RIGHT:
                direction = EntityDirection.RIGHT;
                break;

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

    @Override
    public Entity clone() {
        try {
            Entity clone = (Entity) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
