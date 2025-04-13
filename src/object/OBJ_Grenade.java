package object;

import model.Entity;
import utilities.Constants;
import utilities.GrenadeTypes;
import view.GamePanel;

public class OBJ_Grenade extends Entity {


    public OBJ_Grenade(GamePanel gamePanel, GrenadeTypes type) {
        super(gamePanel);
        //Pass a default with and height
//        this.initialize(16, 16);
        name = Constants.GRENADE +" "+type.toString();


        description = "[" + name + "] \nGrenades";
        setType(type);
    }


    public void setType(GrenadeTypes type) {
        switch (type){
            case TYPE_ONE -> {
                down1 = setup("/objects/grenades/grenades_3.png");
                break;
            }
            case TYPE_TWO -> {
                down1 = setup("/objects/grenades/grenades_5.png");
                break;

            }
            case TYPE_THREE -> {
                down1 = setup("/objects/grenades/Granade_Shell.png");
                break;

            }
        }
    }

}
