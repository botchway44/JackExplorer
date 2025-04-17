package object;

import utilities.Constants;
import utilities.Entities;
import view.GamePanel;

public class OBJ_Bullet extends Projectile{
    public OBJ_Bullet(GamePanel gamePanel) {
        super(gamePanel);

        type = Entities.WEAPON;
        name = Constants.BULLET;
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
        up1 = setup("/objects/bullets/bullet_up.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        up2 = setup("/objects/bullets/bullet_up.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        down1 = setup("/objects/bullets/bullet_down.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        down2 = setup("/objects/bullets/bullet_down.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        left1 = setup("/objects/bullets/bullet_left.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        left2 = setup("/objects/bullets/bullet_left.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        right1 = setup("/objects/bullets/bullet_right.png", gamePanel.tileSize/2, gamePanel.tileSize/2);
        right2 = setup("/objects/bullets/bullet_right.png", gamePanel.tileSize/2, gamePanel.tileSize/2);

    }
}
