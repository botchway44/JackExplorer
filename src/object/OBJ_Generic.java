package object;

import javax.imageio.ImageIO;

public class OBJ_RUINS extends SuperObject{

	public OBJ_RUINS(String filePath) {
		
		name = "Key";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(filePath));

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
