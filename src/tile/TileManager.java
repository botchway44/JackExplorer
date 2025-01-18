package tile;


import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import view.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		this.tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		
		this.getTileImage();
		this.loadMap("/maps/world01.txt");
		
		
	}
	
	
	public void getTileImage() {
		
		try {
			tile[0] = new Tile();
			tile[0].name = "Grass";
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].name = "Wall";
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].name = "Water";
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
			
			tile[3] = new Tile();
			tile[3].name = "Earth";
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].name = "Tree";
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			
			tile[5] = new Tile();
			tile[5].name = "Sand";
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void loadMap(String path) {
		try {
			InputStream file = getClass().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(file));
			
			
			
			for(int col=0; col<gp.maxWorldCol; col++) {
				

				String line = br.readLine();
				System.out.println("Tile "+ line);
				String[] lineSplit = line.split(" ");
				for(int row=0; row<gp.maxWorldRow; row++) {
					
					if(row <=lineSplit.length ) {
						
						int num = Integer.parseInt(lineSplit[row]);
						
						if(num == 1) {
							System.out.println("Wall "+ lineSplit[row]);
						}else if(num == 2) {
							System.out.println("Water "+ lineSplit[row]);
						}
						
						mapTileNum[row][col]  = num;
//						g2.drawImage(tile[0].image, row *  gp.tileSize, col *  gp.tileSize, gp.tileSize, gp.tileSize, null);
					}
				}
			}
			
			System.out.println(mapTileNum.toString());
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
 
		public void draw(Graphics g2) {
			
//			g2.setColor(Color.white);
//			g2.fillRect(this.x, this.y, this.gp.tileSize, this.gp.tileSize);
			
//			BufferedImage image  = null;
			 
			
//			int worldCol = 0;
//			int worldRow = 0;
			
//			g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
//			g2.drawImage(tile[1].image, gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
//
//			g2.drawImage(tile[2].image, gp.tileSize*2, 0, gp.tileSize, gp.tileSize, null);
			
			
//			int col = 0, row = 0, x = 0, y = 0;
			
			for(int col=0; col<gp.maxWorldCol; col++) {

				for(int row=0; row<gp.maxWorldRow; row++) {
					
					int worldX = row *  gp.tileSize;
					int worldY = col *  gp.tileSize;
					
					int screenX = worldX - gp.player.worldX + gp.player.screenX;
					int screenY = worldY - gp.player.worldY + gp.player.screenY;

					if( 
							(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX) && 
							(worldX -  gp.tileSize < gp.player.worldX + gp.player.screenX) && 
							(worldY +  gp.tileSize > gp.player.worldY - gp.player.screenY) &&
							(worldY -  gp.tileSize < gp.player.worldY + gp.player.screenY)
							) {
						
						g2.drawImage(tile[mapTileNum[row][col]].image, screenX, screenY, gp.tileSize, gp.tileSize, null);						
					}

//					if(mapTileNum[row][col] == 0) {
//						g2.drawImage(tile[0].image, row *  gp.tileSize, col *  gp.tileSize, gp.tileSize, gp.tileSize, null);						
//					}
//					else if(mapTileNum[row][col] == 1) {
//						g2.drawImage(tile[1].image, row *  gp.tileSize, col *  gp.tileSize, gp.tileSize, gp.tileSize, null);						
//					}
//					else if(mapTileNum[row][col] == 2) {
//						g2.drawImage(tile[2].image, row *  gp.tileSize, col *  gp.tileSize, gp.tileSize, gp.tileSize, null);						
//					}
//					
//					else if(mapTileNum[row][col] == 3) {
//						g2.drawImage(tile[2].image, row *  gp.tileSize, col *  gp.tileSize, gp.tileSize, gp.tileSize, null);						
//					}
				}
			}
			

		}
	
}
