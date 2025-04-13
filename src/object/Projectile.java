package object;

import model.Entity;
import utilities.EntityDirection;
import view.GamePanel;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }



    public void update() {

        if(user != gamePanel.player) {

        }

        if (user == gamePanel.player) {
            int monsterIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monsters);
            if(monsterIndex >=0){
                gamePanel.player.damageMonster(monsterIndex, attack);
                alive = false;
                life = 0;
            }
        }


        switch (direction){
            case LEFT: worldX -= speed; break;
            case RIGHT: worldX += speed; break;
            case UP: worldY -= speed; break;
            case DOWN: worldY += speed; break;
        }

        life--;

        if(life <= 0){
            alive = false;
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum ==1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }


    public void set(int worldX, int worldY, EntityDirection direction, boolean alive, Entity entity) {
            this.worldX = worldX + gamePanel.tileSize/4;
            this.worldY = worldY + gamePanel.tileSize/4;
            this.direction = direction;
            this.alive = alive;
            this.user = entity;
    }
}
