package object;

import model.Entity;
import utilities.Constants;
import utilities.UtilityTool;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    public BufferedImage image2, image3;
    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);

        name  = "OBJ_Heart";
        image = setup("/objects/heart/heart_blank.png");
        image2 = setup("/objects/heart/heart_half.png");
        image3 = setup("/objects/heart/heart_full.png");
    }

}
