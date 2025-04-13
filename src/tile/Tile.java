package tile;

import java.awt.image.BufferedImage;

public class Tile {
	private int tileNumber;
	public String name;
	public BufferedImage image;
	public boolean collision  = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getTileNumber() {
		return tileNumber;
	}

	public void setTileNumber(int tileNumber) {
		this.tileNumber = tileNumber;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}
}
