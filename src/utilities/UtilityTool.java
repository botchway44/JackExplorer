package utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public static BufferedImage scaleImage(BufferedImage original, int newWidth, int newHeight) {
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, newWidth, newHeight, null);
        return scaledImage;
    }
}
