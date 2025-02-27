package object;

import utilities.UtilityTool;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Generic extends SuperObject {

    public OBJ_Generic(String filePath, String name, Boolean collision) {

        this.name = name;
        this.collision = collision;
        this.initialize(filePath, 16, 16);
    }

    public OBJ_Generic(String filePath, String name, Boolean collision, int width, int height) {

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
