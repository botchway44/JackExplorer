package view;

import controller.UIController;
import model.Entity;
import model.Player;
import object.SuperObject;
import tile.CollisionChecker;
import tile.TileManager;
import utilities.*;
import utilities.controller.PS5NewImplementation;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTING

    final int originalTileSize = 16; //16x16 tile

    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 16*3 = 48x48 tile

    public final int maxScreenCol = 12;
    public final int maxScreenRow = 16;

    public final int screenWidth = tileSize * maxScreenRow; //768 pixels

    public final int screenHeight = tileSize * maxScreenCol; // 576 pixels


    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //STILL NOT USING THIS, WILL COME BACK TO IT
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldheight = tileSize * maxWorldRow;


    //FPS
    int FPS = 30;


    //SYSTEM
    public final TileManager tileManager = new TileManager(this);
    final KeyHandler keyH = new KeyHandler(this);
    public final PS5NewImplementation ps5Handler = new PS5NewImplementation(this);
    Sound music = new Sound();
    Sound soundFx = new Sound();

    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UIController uiController = new UIController(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this, this.keyH);
    public SuperObject[] gameObjects = new SuperObject[10];
    public HashMap<Entities, Entity> npcs = new HashMap<Entities, Entity>();

    //GAME STATE
    public GameState gameState = GameState.PLAYING;

    //set Default Player Position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel() {
        ps5Handler.start(); // new PS5NewImplementation().start(); registers controller input listener
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);

        this.setFocusable(true);
    }


    public void setUpGame() {
        this.assetSetter.setObject();
        this.assetSetter.setNPC();
        this.playMusic(SoundAssets.BACKGROUND);
        this.gameState = GameState.PLAYING;
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }


    @Override
    public void run() {

        long oneSecond = 1000000000;


        double drawInterval = (double) oneSecond / FPS; //0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (this.gameThread != null) {
            //System.out.println("The game loop is running");


            // Update information about the character
            this.update();

            // Draw the screen with the updated information
            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / oneSecond;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void update() {

        if (this.gameState == GameState.PLAYING) {
            player.update();

            //Update NPCs as well
            for (Entity npc : npcs.values()) {
                npc.update();
            }
        } else if (this.gameState == GameState.PAUSED) {
            //nothing for now
            // later show paused screen
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        for (int i = 0; i < gameObjects.length; i++) {
            if (gameObjects[i] != null) {
                gameObjects[i].draw(g2, this);
            }
        }

        //Draw NPCs
        for(Entity e : npcs.values()) {
            e.draw(g2);

        }

        //PLAYER
        player.draw(g2);

        //UI
        uiController.draw(g2);

        g2.dispose();

    }


    public void playMusic(SoundAssets key) {
        music.setFile(key);
        music.play();
        music.loop();
    }

    public void playSoundEffect(SoundAssets key) {
        soundFx.setFile(key);
        soundFx.play();
    }


    public void switchGamePauseState(){
        if (this.gameState == GameState.PAUSED) {
            this.gameState = GameState.PLAYING;
        } else if (this.gameState == GameState.PLAYING) {
            this.gameState = GameState.PAUSED;
        }
    }


    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getTileSize() {
        return tileSize;
    }
}
