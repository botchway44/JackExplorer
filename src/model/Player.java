package model;

import object.*;
import utilities.*;
import utilities.controller.PS5NewImplementation;
import view.GamePanel;
import view.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Player extends Entity {

    GamePanel gp;
    public KeyHandler keyH; //Change back to private
    public PS5NewImplementation psHandler;
    public final int screenX;
    public final int screenY;

    private int standCounter = 0;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public int maxInventorySize = 20;


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
        setInventory();
    }


//    @Override
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = EntityDirection.DOWN;
        maxLife = 6;
        life = maxLife;

        //STATS
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentShield = new OBJ_Armor_Shield(gamePanel);
        currentWeapon = new OBJ_Bullet(gamePanel);
        defense = getAttack(); // the total attack value is decided by the strength and weapon
        attack = getDefense(); // the total defense value is decided by dexterity and shield

        projectile = (Projectile) currentWeapon;
        defaultProjectile = projectile;
    }

    public void setInventory() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Grenade(gamePanel, GrenadeTypes.TYPE_ONE));
        inventory.add(new OBJ_Grenade(gamePanel, GrenadeTypes.TYPE_TWO));
        inventory.add(new OBJ_Grenade(gamePanel, GrenadeTypes.TYPE_THREE));
        inventory.add(new OBJ_FireBullets(gamePanel));
        inventory.add(new OBJ_FireBullets(gamePanel));

    }

    public int getAttack(){
        return strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
        try {


            up1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-up-1.png"))), gp.tileSize, gp.tileSize);
            up2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-up-1.png"))), gp.tileSize, gp.tileSize);


            down1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-down-1.png"))), gp.tileSize, gp.tileSize);
            down2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-down-1.png"))), gp.tileSize, gp.tileSize);

            right1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-right-1.png"))), gp.tileSize, gp.tileSize);
            right2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-right-1.png"))), gp.tileSize, gp.tileSize);

            left1 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-left-1.png"))), gp.tileSize, gp.tileSize);
            left2 = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player-left-1.png"))), gp.tileSize, gp.tileSize);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //check keyboard input
    public void checkControllerDirections(){


        if (this.gp.ps5Handler.upPressed ) {
//			this.worldY -= this.speed;
            this.direction = EntityDirection.UP;
        }

        if (this.gp.ps5Handler.downPressed) {
//			this.worldY += this.speed;
            this.direction = EntityDirection.DOWN;
        }

        if (this.gp.ps5Handler.leftPressed ) {
//			this.worldX -= this.speed;
            this.direction = EntityDirection.LEFT;
        }

        if (this.gp.ps5Handler.rightPressed) {
//			this.worldX += this.speed;
            this.direction = EntityDirection.RIGHT;
        }

    }

    //check keyboard input
    public void checkKeyboardDirections(){


        if (this.keyH.upPressed) {
//			this.worldY -= this.speed;
            this.direction = EntityDirection.UP;
        }

        if (this.keyH.downPressed) {
//			this.worldY += this.speed;
            this.direction = EntityDirection.DOWN;
        }

        if (this.keyH.leftPressed) {
//			this.worldX -= this.speed;
            this.direction = EntityDirection.LEFT;
        }

        if (this.keyH.rightPressed) {
//			this.worldX += this.speed;
            this.direction = EntityDirection.RIGHT;
        }

    }

    @Override
    public void update() {

        //optimize this check
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed
        || gp.ps5Handler.upPressed || gp.ps5Handler.downPressed || gp.ps5Handler.leftPressed || gp.ps5Handler.rightPressed || keyH.enterPressed
        ) {

            this.checkKeyboardDirections();
            this.checkControllerDirections();

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            //Check object collision
            int objectIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            //Check NPC Collision
//            Entities e = gp.collisionChecker.checkEntityCollision(this,gp.npcs);
            int e = gp.collisionChecker.checkEntityCollision(this,gp.npcs);

            interactWithNPC(e);

            //Check Monster Collision
            int monsterIndex = gp.collisionChecker.checkEntityCollision(this, gp.monsters);
            contactMonster(monsterIndex);

            //Check Events
            gp.eventHandler.checkEvent();



            //if the collision is false, player can move
            if (!collisionOn && !keyH.enterPressed) {
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


            //update enter key pressed
            gp.keyH.enterPressed = false;

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

        if(gamePanel.keyH.shootKeyPressed){
            projectile.set(worldX,worldY, direction, true, this);

            //use the current projectile here
//           setCurrentProject(defaultProjectile);

            gamePanel.projectileList.add(projectile);
            gamePanel.playSoundEffect(SoundAssets.SHOOT);

            setCurrentProject(defaultProjectile);

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

    private void interactWithNPC(Entities e) {
//        if(e != null) {
//
//            if(gp.keyH.enterPressed){
//
//            System.out.println("interactWithNPC"+ e.toString());
//            gp.gameState = GameState.DIALOG_STATE;
//            gp.npcs.get(e).speak();
//
//            } else {
//                gp.gameState = GameState.DIALOG_STATE;
//                System.out.println("Collided WithNPC"+ e.toString());
//                gp.npcs.get(e).speak();
//
//            }
//        }



    }

    private void interactWithNPC(int i) {
        if(i >= 0) {

            if(gp.keyH.enterPressed){

                System.out.println("interactWithNPC"+ gp.npcs[i].toString());
                gp.gameState = GameState.DIALOG_STATE;
                gp.npcs[i].speak();

            }
        }



    }


    public void contactMonster(int i){
        System.out.println("contactMonster Again");
        if(i >=0){
            if(!invincible && gp.monsters[i].dying) {
                gp.playSoundEffect(SoundAssets.RECEIVE_DAMAGE);
                int damage = gp.monsters[i].attack - defense ;
                if(damage < 0){
                    damage = 0;
                }
                gp.monsters[i].life -=damage;

                invincible = true;
            }
        }
    }


    public void damageMonster(int i, int attack){
        if (i >= 0) {
            if(!gp.monsters[i].invincible){
                gp.monsters[i].invincible = true;

                int damage = attack - gp.monsters[i].defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.monsters[i].life -=damage;
                gamePanel.uiController.addMessage(damage + " damage");
                gp.playSoundEffect(SoundAssets.HIT_MONSTER);


                if(gp.monsters[i].life <=0){
                    gp.monsters[i].dying = true;
                    gamePanel.uiController.addMessage("Killed the " + gamePanel.monsters[i].name);

                    gamePanel.uiController.addMessage("Exp " + gamePanel.monsters[i].exp);
                    exp += gamePanel.monsters[i].exp;

                    checkLevelUp();

                }
            }
        }


    }

    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife +=2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gamePanel.playSoundEffect(SoundAssets.LEVEL_UP);
            gamePanel.gameState = GameState.DIALOG_STATE;
            gamePanel.uiController.currentDialog = "You are at Level " + level +" now\n"
            + "You feel Stronger";
        }
    }

    private void pickUpObject(int objectIndex) {


        if (objectIndex >= 0 && objectIndex < gp.gameObjects.length) {
            Entity object = gp.gameObjects[objectIndex];
//            System.out.println();

            String text = "";

            if (Objects.equals(object.name, Constants.CHEST)) {
                speed += 5;
                this.gp.playSoundEffect(SoundAssets.POWERUP);

                this.gp.uiController.addMessage("You picked up a Chest with Speed Booster");

            } else if (Objects.equals(object.name, Constants.KEY)) {
                this.gp.playSoundEffect(SoundAssets.COIN);

                this.gp.gameObjects[objectIndex] = null;

                if(inventory.size() <= maxInventorySize){
                    text = "You picked a " + object.name;
                    inventory.add(object);
                }else {
                    text = "You cannot any more object ";
                }
                this.gp.uiController.addMessage(text);

            } else if (Objects.equals(object.name, Constants.DOOR)) {
                this.gp.playSoundEffect(SoundAssets.UNLOCK);
            }
            //Pick up Like keys and use the keys to open doors

        }
    }

     public void draw(Graphics g2) {


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


        System.out.println("Using this for Player");
    }


    public void selectItem() {
        int itemIndex = gamePanel.uiController.getIntemIndex();
        if(itemIndex < inventory.size()){
            Entity object = inventory.get(itemIndex);
            if(object.type == Entities.WEAPON){
                currentWeapon = object;
                attack = getAttack();

                setCurrentProject(object);
            }

            if(object.type == Entities.SHIELD){
                currentShield = object;
                defense = getDefense();
            }

            if (object.type == Entities.CONSUMABLE){
                //later
            }
        }
    }

    public void setCurrentProject(Object object){
        //check the type of objec
        if(object instanceof OBJ_FireBullets){
            this.projectile = new OBJ_FireBullets(this.gamePanel);
            this.defaultProjectile =  new OBJ_FireBullets(this.gamePanel);
            System.out.println("This is the Fire Bullets");
        }
        else if (object instanceof OBJ_Bullet){
            this.projectile = new OBJ_Bullet(this.gamePanel);
            this.defaultProjectile =  new OBJ_Bullet(this.gamePanel);
        }

        System.out.println("This is the Projectile " + this.projectile.life);
//        projectile.life = maxLife;
//        projectile.alive = true;
//        projectile.dying = false;
    }
}
