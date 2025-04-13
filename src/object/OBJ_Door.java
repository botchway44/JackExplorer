package object;

import model.Entity;
import view.GamePanel;

public class OBJ_Door extends Entity {


    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);


        name = "OBJ_Door";
        down1 = setup("/objects/door.png");

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
    }

}
