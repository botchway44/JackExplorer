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
    public final int[][] mapTileNum;
    float scale = 1;
    public String currentMap = "/maps/worldV2.txt";

    public TileManager(GamePanel gp,float scale) {
        this.gp = gp;

        this.tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        this.scale = scale;
        this.getTileImage();
        this.loadMap(this.currentMap);

    }


    public void getTileImage() {


        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        setup(10, "grass00", false);
        setup(11, "grass01", false);

        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water05", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);


//        setup(1, "wall", true);
//        setup(2, "water", false);
//        setup(3, "earth", false);
//        setup(4, "tree", true);
//        setup(5, "sand", false);

    }

    public void setup(int index, String resourceName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].name = resourceName;


            tile[index].image = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + resourceName + ".png"))), (int) (gp.tileSize * scale), (int) (gp.tileSize * scale));
            tile[index].collision = collision;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String path) {
        try {
            InputStream file = getClass().getResourceAsStream(path);
            assert file != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(file));


            for (int col = 0; col < gp.maxWorldCol; col++) {

//                System.out.println("SYSTEM OUTLINE ::: ");
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


    public void drawAllTiles(Graphics g2) {



        for (int col = 0; col < gp.maxWorldCol; col++) {

            for (int row = 0; row < gp.maxWorldRow; row++) {

                int worldX = (int) (row * (gp.tileSize * 0.43));
                int worldY = (int) (col * (gp.tileSize  * 0.43));

//                int screenX = worldX - gp.player.worldX + gp.player.screenX;
//                int screenY = worldY - gp.player.worldY + gp.player.screenY;

//                if (
//                        (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX) &&
//                                (worldX - gp.tileSize < gp.player.worldX + gp.player.screenX) &&
//                                (worldY + gp.tileSize > gp.player.worldY - gp.player.screenY) &&
//                                (worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
//                ) {

                    g2.drawImage(tile[mapTileNum[row][col]].image, worldX, worldY, null);
//                }

            }
        }


    }

}
