package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.*;


public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;

	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);;
		
		this.setDefaultValues();
		this.getPlayerImage();
		
	}
	
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			

		
		if(this.keyH.upPressed == true) {
			this.worldY -= this.speed;
			this.direction = "up";
		}
		
		if(this.keyH.downPressed == true) {
			this.worldY += this.speed;
			this.direction = "down";
		}
		
		if(this.keyH.leftPressed == true) {
			this.worldX -= this.speed;
			this.direction = "left";
		}
		
		if(this.keyH.rightPressed == true) {
			this.worldX += this.speed;
			this.direction = "right";
		}
		
		this.spriteCounter++;
		
		if(this.spriteCounter > 10) {
			if(this.spriteNum == 1) {
				this.spriteNum = 2;
			}else if(this.spriteNum ==2) {
				this.spriteNum = 1;
			}
			
			this.spriteCounter = 0;
		}


		}
	}
	
	public void draw(Graphics g2) {
		
//		g2.setColor(Color.white);
//		g2.fillRect(this.x, this.y, this.gp.tileSize, this.gp.tileSize);
		
		BufferedImage image  = null;
		
		switch(direction) {
		case "up" : 
			
			if(spriteNum == 1) {
				image = up1;
			}else if(spriteNum == 2) {
				image = up2;
			}
			break;
			
		case "down" : 
			
			if(spriteNum == 1) {
				image = down1;
			}else if(spriteNum == 2) {
				image = down2;
			}
			
			break;
			
		case "right" : 
//			image = right1;
			
			if(spriteNum == 1) {
				image = right1;
			}else if(spriteNum == 2) {
				image = right2;
			}
			
			break;
			
		case "left" : 
//			image = left1;
			
			if(spriteNum == 1) {
				image = left1;
			}else if(spriteNum == 2) {
				image = left2;
			}
			
			break;
		}
		
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
