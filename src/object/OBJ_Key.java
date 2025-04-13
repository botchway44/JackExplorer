package object;

import model.Entity;
import utilities.Constants;
import utilities.UtilityTool;
import view.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Key extends Entity {


    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);
        //Pass a default with and height
//        this.initialize(16, 16);
        name = Constants.KEY;
        down1 = setup("/objects/key.png");
        description = "[" + name + "] \nUnlocking keys";

    }

//    public OBJ_Key(int width, int height) {
//        this.initialize(width, height);
//    }


//    public void initialize(int width, int height) {
//        name = Constants.KEY;
//
//        try {
//            image = UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/key.png")), width, height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
