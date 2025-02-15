package tile;


import utilities.UtilityTool;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public final Tile[] tile;
    public final int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        this.tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];


        this.getTileImage();
        this.loadMap("/maps/world01.txt");

    }


    public void getTileImage() {


        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", false);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);

    }

    public void setup(int index, String resourceName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].name = resourceName;
            tile[index].image = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + resourceName + ".png"))), gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String path) {
        try {
            InputStream file = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(file));


            for (int col = 0; col < gp.maxWorldCol; col++) {


                String line = br.readLine();
//				System.out.println("Tile "+ line);
                String[] lineSplit = line.split(" ");
                for (int row = 0; row < gp.maxWorldRow; row++) {

                    if (row <= lineSplit.length) {

                        int num = Integer.parseInt(lineSplit[row]);

                        if (num == 1) {
//							System.out.println("Wall "+ lineSplit[row]);
                        } else if (num == 2) {
//							System.out.println("Water "+ lineSplit[row]);
                        }

                        mapTileNum[row][col] = num;
//						g2.drawImage(tile[0].image, row *  gp.tileSize, col *  gp.tileSize, gp.tileSize, gp.tileSize, null);
                    }
                }
            }

//			System.out.println(mapTileNum.toString());
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics g2) {
        
        for (int col = 0; col < gp.maxWorldCol; col++) {

            for (int row = 0; row < gp.maxWorldRow; row++) {

                int worldX = row * gp.tileSize;
                int worldY = col * gp.tileSize;

                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (
                        (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX) &&
                                (worldX - gp.tileSize < gp.player.worldX + gp.player.screenX) &&
                                (worldY + gp.tileSize > gp.player.worldY - gp.player.screenY) &&
                                (worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
                ) {

                    g2.drawImage(tile[mapTileNum[row][col]].image, screenX, screenY, null);
                }

            }
        }


    }

}
