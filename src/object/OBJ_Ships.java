package object;

import utilities.Entities;
import utilities.PlaneType;
import utilities.ShipType;
import view.GamePanel;

public class OBJ_Ships extends Projectile {

    public OBJ_Ships(GamePanel gamePanel, ShipType planeType) {
        super(gamePanel);

        name = "Ship " + planeType;
        checkPlaneType(planeType);
        description = "[" + name + "] \nCurrent Plane";
        type = Entities.OBJECT;
        defenseValue = 1;
    }


    public void checkPlaneType(ShipType planeType) {
        switch (planeType) {
            case FIGHTER: down1 = setup("/npc/ships/Boat_color3_1.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
            break;
            case ALIEN: down1 = setup("/npc/ships/Boat_color3_2.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
                break;
            case WAR: down1 = setup("/npc/ships/Boat_color3_3.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
                break;
            case SIMPLE: down1 = setup("/npc/ships/Boat_color3_4.png", gamePanel.tileSize* 2, gamePanel.tileSize* 2);
                break;
        }
    }
}
