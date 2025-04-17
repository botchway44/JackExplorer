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

/**
 * The Player class represents the main player character in the game.
 * <p>
 * It handles movement, input processing (keyboard and controller),
 * collision detection, inventory management, combat interactions,
 * and rendering of the player sprite.
 * </p>
 *
 * @author Emmanuel Botwe
 */
public class Player extends Entity {

    /** Reference to the main GamePanel for context and utilities. */
    GamePanel gp;

    /** Keyboard handler for player input. */
    public KeyHandler keyH; // Change back to private if desired

    /** PS5 controller handler for gamepad input. */
    public PS5NewImplementation psHandler;

    /** X coordinate on screen where the player sprite is drawn (centered). */
    public final int screenX;

    /** Y coordinate on screen where the player sprite is drawn (centered). */
    public final int screenY;

    /** Counter used to manage standing animation frames when idle. */
    private int standCounter = 0;

    /** Collection of items the player has picked up. */
    public ArrayList<Entity> inventory = new ArrayList<>();

    /** Maximum number of items allowed in the player's inventory. */
    public int maxInventorySize = 20;

    /**
     * Constructs a new Player.
     *
     * @param gp   the GamePanel instance for rendering and world state
     * @param keyH the KeyHandler for keyboard input events
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        // Center sprite on screen
        this.screenX = gp.screenWidth  / 2 - (gp.tileSize / 2);
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Define collision bounding box
        this.solidArea = new Rectangle(10, 18, 30, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        setInventory();
    }

    /**
     * Initializes default position, speed, stats, and equipment.
     */
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 6;
        direction = EntityDirection.DOWN;

        maxLife = 6;
        life = maxLife;

        level = 1;
        strength = 2;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;

        currentShield = new OBJ_Armor_Shield(gamePanel);
        currentWeapon = new OBJ_Bullet(gamePanel);

        defense = getDefense();
        attack  = getAttack();

        projectile        = (Projectile) currentWeapon;
        defaultProjectile = projectile;
    }

    /**
     * Populates the player's inventory with initial items.
     */
    public void setInventory() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Grenade(gamePanel, GrenadeTypes.TYPE_ONE));
        inventory.add(new OBJ_Grenade(gamePanel, GrenadeTypes.TYPE_TWO));
        inventory.add(new OBJ_Grenade(gamePanel, GrenadeTypes.TYPE_THREE));
        inventory.add(new OBJ_FireBullets(gamePanel));
        inventory.add(new OBJ_FireBullets(gamePanel));
    }

    /**
     * Returns the player's total attack (strength × weapon value).
     *
     * @return computed attack value
     */
    public int getAttack() {
        return strength * currentWeapon.attackValue;
    }

    /**
     * Returns the player's total defense (dexterity × shield value).
     *
     * @return computed defense value
     */
    public int getDefense() {
        return dexterity * currentShield.defenseValue;
    }

    /**
     * Loads and scales directional sprite images for the player.
     */
    public void getPlayerImage() {
        try {
            up1     = UtilityTool.scaleImage(
                    ImageIO.read(Objects.requireNonNull(
                            getClass().getResourceAsStream("/player/player-up-1.png"))),
                    gp.tileSize, gp.tileSize
            );
            up2     = up1;
            down1   = UtilityTool.scaleImage(
                    ImageIO.read(Objects.requireNonNull(
                            getClass().getResourceAsStream("/player/player-down-1.png"))),
                    gp.tileSize, gp.tileSize
            );
            down2   = down1;
            right1  = UtilityTool.scaleImage(
                    ImageIO.read(Objects.requireNonNull(
                            getClass().getResourceAsStream("/player/player-right-1.png"))),
                    gp.tileSize, gp.tileSize
            );
            right2  = right1;
            left1   = UtilityTool.scaleImage(
                    ImageIO.read(Objects.requireNonNull(
                            getClass().getResourceAsStream("/player/player-left-1.png"))),
                    gp.tileSize, gp.tileSize
            );
            left2   = left1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates movement direction based on PS5 controller input.
     */
    public void checkControllerDirections() {
        if (gp.ps5Handler.upPressed)    direction = EntityDirection.UP;
        if (gp.ps5Handler.downPressed)  direction = EntityDirection.DOWN;
        if (gp.ps5Handler.leftPressed)  direction = EntityDirection.LEFT;
        if (gp.ps5Handler.rightPressed) direction = EntityDirection.RIGHT;
    }

    /**
     * Updates movement direction based on keyboard arrow keys.
     */
    public void checkKeyboardDirections() {
        if (keyH.upPressed)    direction = EntityDirection.UP;
        if (keyH.downPressed)  direction = EntityDirection.DOWN;
        if (keyH.leftPressed)  direction = EntityDirection.LEFT;
        if (keyH.rightPressed) direction = EntityDirection.RIGHT;
    }

    /**
     * Called each frame to update player state: movement, collisions,
     * picking up objects, NPC/monster interactions, and animations.
     */
    @Override
    public void update() {
        boolean moving = keyH.upPressed   || keyH.downPressed  ||
                keyH.leftPressed || keyH.rightPressed ||
                gp.ps5Handler.upPressed   || gp.ps5Handler.downPressed  ||
                gp.ps5Handler.leftPressed || gp.ps5Handler.rightPressed ||
                keyH.enterPressed;

        if (moving) {
            checkKeyboardDirections();
            checkControllerDirections();

            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            int objectIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            int npcIndex = gp.collisionChecker.checkEntityCollision(this, gp.npcs);
            interactWithNPC(npcIndex);

            int monsterIndex = gp.collisionChecker.checkEntityCollision(this, gp.monsters);
            contactMonster(monsterIndex);

            gp.eventHandler.checkEvent();

            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case UP:    worldY -= speed; break;
                    case DOWN:  worldY += speed; break;
                    case LEFT:  worldX -= speed; break;
                    case RIGHT: worldX += speed; break;
                }
            }
            keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        // Handle shooting
        if (gamePanel.keyH.shootKeyPressed) {
            projectile.set(worldX, worldY, direction, true, this);
            gamePanel.projectileList.add(projectile);
            gamePanel.playSoundEffect(SoundAssets.SHOOT);
            setCurrentProject(defaultProjectile);
        }

        // Handle invincibility timer
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        //Handle Life
        if (life <=0){
            this.gamePanel.gameState = GameState.GAME_OVER;
        }
    }

    /**
     * Handles interaction when colliding with an NPC.
     *
     * @param index index of the NPC collided with, or -1 if none
     */
    private void interactWithNPC(int index) {
        if (index >= 0 && keyH.enterPressed) {
            gp.gameState = GameState.DIALOG_STATE;
            gp.npcs[index].speak();
        }
    }

    /**
     * Handles taking damage when colliding with a monster.
     *
     * @param index index of the monster collided with, or -1 if none
     */
    public void contactMonster(int index) {
        if (index >= 0 && !invincible && gp.monsters[index].dying) {
            gp.playSoundEffect(SoundAssets.RECEIVE_DAMAGE);
            int damage = gp.monsters[index].attack - defense;
            if (damage < 0) damage = 0;
            gp.monsters[index].life -= damage;
            invincible = true;
        }
    }

    /**
     * Inflicts damage on a monster, handles its death, and awards experience.
     *
     * @param index  index of the target monster
     * @param attack attack value to apply
     */
    public void damageMonster(int index, int attack) {
        if (index >= 0 && !gp.monsters[index].invincible) {
            gp.monsters[index].invincible = true;
            int damage = attack - gp.monsters[index].defense;
            if (damage < 0) damage = 0;
            gp.monsters[index].life -= damage;
            gamePanel.uiController.addMessage(damage + " damage");
            gp.playSoundEffect(SoundAssets.HIT_MONSTER);

            if (gp.monsters[index].life <= 0) {
                gp.monsters[index].dying = true;
                gamePanel.uiController.addMessage("Killed the " + gp.monsters[index].name);
                gamePanel.uiController.addMessage("Exp " + gp.monsters[index].exp);
                exp += gp.monsters[index].exp;
                checkLevelUp();
            }
        }
    }

    /**
     * Checks if the player has enough experience to level up, and does so.
     */
    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gamePanel.playSoundEffect(SoundAssets.LEVEL_UP);
            gamePanel.gameState = GameState.DIALOG_STATE;
            gamePanel.uiController.currentDialog =
                    "You are at Level " + level + " now\nYou feel Stronger";
        }
    }

    /**
     * Attempts to pick up an object at the given index.
     *
     * @param objectIndex index of the object in the gameObjects array
     */
    private void pickUpObject(int objectIndex) {
        if (objectIndex >= 0 && objectIndex < gp.gameObjects.length) {
            Entity object = gp.gameObjects[objectIndex];
            String text = "";

            if (Objects.equals(object.name, Constants.CHEST)) {
                speed += 5;
                gp.playSoundEffect(SoundAssets.POWERUP);
                gp.uiController.addMessage("You picked up a Chest with Speed Booster");

            } else if (Objects.equals(object.name, Constants.KEY)) {
                gp.playSoundEffect(SoundAssets.COIN);
                gp.gameObjects[objectIndex] = null;
                if (inventory.size() <= maxInventorySize) {
                    text = "You picked a " + object.name;
                    inventory.add(object);
                } else {
                    text = "You cannot carry any more items";
                }
                gp.uiController.addMessage(text);

            } else if (Objects.equals(object.name, Constants.DOOR)) {
                gp.playSoundEffect(SoundAssets.UNLOCK);
            }
        }
    }

    /**
     * Draws the player sprite at its screen position.
     *
     * @param g2 the Graphics context
     */
    public void draw(Graphics g2) {
        BufferedImage image = null;
        switch (direction) {
            case UP:    image = (spriteNum == 1) ? up1    : up2;    break;
            case DOWN:  image = (spriteNum == 1) ? down1  : down2;  break;
            case RIGHT: image = (spriteNum == 1) ? right1 : right2; break;
            case LEFT:  image = (spriteNum == 1) ? left1  : left2;  break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }

    /**
     * Allows the player to select an inventory item for use.
     */
    public void selectItem() {
        int itemIndex = gamePanel.uiController.getIntemIndex();
        if (itemIndex < inventory.size()) {
            Entity object = inventory.get(itemIndex);
            if (object.type == Entities.WEAPON) {
                currentWeapon = object;
                attack = getAttack();
                setCurrentProject(object);
            }
            if (object.type == Entities.SHIELD) {
                currentShield = object;
                defense = getDefense();
            }
            // Consumables can be handled here in future
        }
    }

    /**
     * Sets the current projectile type based on the given object.
     *
     * @param object the weapon or projectile object
     */
    public void setCurrentProject(Object object) {
        if (object instanceof OBJ_FireBullets) {
            this.projectile        = new OBJ_FireBullets(this.gamePanel);
            this.defaultProjectile = new OBJ_FireBullets(this.gamePanel);
        }
        else if (object instanceof OBJ_Bullet) {
            this.projectile        = new OBJ_Bullet(this.gamePanel);
            this.defaultProjectile = new OBJ_Bullet(this.gamePanel);
        }
    }
}
