package object;

import model.Entity;
import utilities.UtilityTool;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Generic extends Entity {

    public OBJ_Generic(GamePanel gamePanel, String filePath, String name, Boolean collision) {
        super(gamePanel);
        this.name = name;
        this.collision = collision;
        this.initialize(filePath, 16, 16);
    }

    public OBJ_Generic(GamePanel gamePanel, String filePath, String name, Boolean collision, int width, int height) {
        super(gamePanel);
        this.collision = collision;
        this.name = name;
        this.initialize(filePath, width, height);
    }


    public void initialize(String filePath, int width, int height) {

        try {
            image = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath))), width, height);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
