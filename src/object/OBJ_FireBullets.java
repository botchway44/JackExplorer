package object;

import utilities.Constants;
import utilities.Entities;
import view.GamePanel;

public class OBJ_FireBullets extends Projectile{
    public OBJ_FireBullets(GamePanel gamePanel) {
        super(gamePanel);

        name = Constants.BULLET;
        type = Entities.WEAPON;

        speed = 10;
        maxLife = 120;
        life = maxLife;
        attack = 10;
        useCost =1;
        alive = false;

        getImage();

        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.width = 16;
        solidArea.height = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public void getImage(){
        up1 = setup("/objects/projectile/fireball_up_1.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        up2 = setup("/objects/projectile/fireball_up_2.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        down1 = setup("/objects/projectile/fireball_down_1.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        down2 = setup("/objects/projectile/fireball_down_2.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        left1 = setup("/objects/projectile/fireball_left_1.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        left2 = setup("/objects/projectile/fireball_left_2.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        right1 = setup("/objects/projectile/fireball_right_1.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        right2 = setup("/objects/projectile/fireball_right_2.png", gamePanel.tileSize/2, gamePanel.tileSize/2);

    }
}
