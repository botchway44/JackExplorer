package object;

import utilities.Entities;
import utilities.PlaneType;
import view.GamePanel;

public class OBJ_Plane extends Projectile {

    public OBJ_Plane(GamePanel gamePanel, PlaneType planeType) {
        super(gamePanel);

        name = "Plane 1";
        checkPlaneType(planeType);
        description = "[" + name + "] \nCurrent Plane";
        type = Entities.OBJECT;
        defenseValue = 1;
    }


    public void checkPlaneType(PlaneType planeType) {
        switch (planeType) {
            case FIGHTER: down1 = setup("/npc/planes/fighters1.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
            break;
            case ALIEN: down1 = setup("/npc/planes/fighters2.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
                break;
            case WAR: down1 = setup("/npc/planes/fighters3.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
                break;
            case SIMPLE: down1 = setup("/npc/planes/fighters4.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
                break;
        }
    }
}
