package object;

import model.Entity;
import utilities.Entities;
import view.GamePanel;

public class OBJ_Armor_Shield extends Projectile {

    public OBJ_Armor_Shield(GamePanel gamePanel) {
        super(gamePanel);

        name = "Shield ";
        down1 = setup("/objects/shield/basic_shield.png");
        description = "[" + name + "] \nCurrent Shield";
        type = Entities.SHIELD;
        defenseValue = 1;
    }
}
