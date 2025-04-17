package tile;


import utilities.UtilityTool;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public final int[][] mapTileNum;
    float scale = 1;
    public String currentMap = "/maps/test.txt";
    public String tileData = "res/maps/tiledata.txt";
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<Boolean> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp,float scale) {
        this.gp = gp;

        readMapData();

        readTileData();
        this.getTileImage();



        System.out.println("Width " + gp.maxWorldCol);
        System.out.println("HEIGHT " + gp.maxWorldRow);

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        this.scale = scale;
        this.loadMap(this.currentMap);

    }

    public void readTileData(){
        //READ TILE DATA FILE

        try{
            Scanner input = new Scanner(new File(tileData));

            while (input.hasNextLine()) {
                String fileName = input.nextLine();
                fileNames.add(fileName);

                if(input.hasNextLine()){
                    Boolean collision = input.nextLine().equals("true");
                    collisionStatus.add(collision);

                }
            }
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.tile = new Tile[fileNames.size()];

    }

    public void readMapData()  {
        //READ TILE DATA FILE
        InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream(currentMap));
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));


        try{
            if(br.readLine() != null){
                String[] data = br.readLine().split(" ");
                gp.maxWorldCol = data.length;
                gp.maxWorldRow = data.length;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("MAX WORL COL" + gp.maxWorldCol);
        System.out.println("MAX WORL ROW" +gp.maxWorldRow);

    }

    public void getTileImage() {

        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            boolean collision = collisionStatus.get(i);
            setup(i, fileName, collision);
        }

    }

    public void setup(int index, String resourceName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].name = resourceName;

//            System.out.println("Path " + resourceName + " Number  " + index);
            tile[index].image = UtilityTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + resourceName ))), (int) (gp.tileSize * scale), (int) (gp.tileSize * scale));
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

            System.out.println("Testing Width " + mapTileNum.length);
            System.out.println("Testing HEIGHT " + mapTileNum[0].length);
            System.out.println("MAX WORL COL" + gp.maxWorldCol);
            System.out.println("MAX WORL ROW" +gp.maxWorldRow);

            for (int col = 0; col < gp.maxWorldCol; col++) {

                 String line = br.readLine();
                 System.out.println("CURR LINE :: "+line);
                System.out.println("HEIGHT :: "+line.length());

                 String[] lineSplit = line.split(" ");
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    if (row <= lineSplit.length) {
//                       System.out.println(lineSplit.length + "LENGTH ROW" + row);

                        int num = Integer.parseInt(lineSplit[row]);
                        mapTileNum[row][col] = num;
                    }
                }
            }


            System.out.println("Testing Width " + mapTileNum.length);
            System.out.println("Testing HEIGHT " + mapTileNum[0][21]);

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
