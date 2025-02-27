package object;

import utilities.Constants;
import utilities.UtilityTool;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {


    public OBJ_Key() {

        //Pass a default with and height
        this.initialize(16, 16);
    }

    public OBJ_Key(int width, int height) {
        this.initialize(width, height);
    }


    public void initialize(int width, int height) {
        name = Constants.KEY;

        try {
            image = UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/key.png")), width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
