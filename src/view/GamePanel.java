package view;

import controller.EventHandler;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * GamePanel is the main drawing surface and game loop host for the Tank Shooter application.
 * <p>
 * It manages screen and world settings, input handling, tile and entity rendering,
 * and the game update cycle (running at a fixed FPS).
 * </p>
 *
 * @author Emmanuel Botwe
 */
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;
    /** Size of a single tile in pixels (scaled). */
    public final int tileSize = originalTileSize * scale;

    /** Number of tiles horizontally on screen. */
    public final int maxScreenCol = 17;
    /** Number of tiles vertically on screen. */
    public final int maxScreenRow = 20;

    /** Width of the game view in pixels. */
    public final int screenWidth = tileSize * maxScreenRow;
    /** Height of the game view in pixels. */
    public final int screenHeight = tileSize * maxScreenCol;

    // WORLD SETTINGS
    /** Number of columns in the world grid. */
    public int maxWorldCol = 50;
    /** Number of rows in the world grid. */
    public int maxWorldRow = 50;
    /** Width of the entire world in pixels. */
    public final int worldWidth = tileSize * maxWorldCol;
    /** Height of the entire world in pixels. */
    public final int worldHeight = tileSize * maxWorldRow;

    // TARGET FRAMES PER SECOND
    private int FPS = 30;

    // EVENT HANDLING
    public EventHandler eventHandler;

    // SYSTEM COMPONENTS
    public final TileManager tileManager = new TileManager(this, 1);
    public final KeyHandler keyH = new KeyHandler(this);
    public final PS5NewImplementation ps5Handler = new PS5NewImplementation(this);
    private Sound music = new Sound();
    private Sound soundFx = new Sound();
    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UIController uiController = new UIController(this);
    private Thread gameThread;

    // GAME ENTITIES
    public Player player = new Player(this, this.keyH);
    public Entity[] gameObjects = new Entity[10];
    public Entity[] npcs = new Entity[10];
    public Entity[] monsters = new Entity[10];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    private ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public GameState gameState = GameState.TITLE_SCREEN;

    /**
     * Constructs the GamePanel, sets up input handlers and display properties.
     */
    public GamePanel() {
        // Initialize controller input on PS5 handler
        ps5Handler.start();
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);
        eventHandler = new EventHandler(this);
    }

    /**
     * Prepares game assets and entities before starting the game loop.
     */
    public void setUpGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonsters();
        playMusic(SoundAssets.TITLE_SCREEN);
        gameState = GameState.TITLE_SCREEN;
    }

    /**
     * Starts the main game thread, which runs the update-render loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Main game loop: updates game state at fixed FPS and repaints.
     */
    @Override
    public void run() {
        long oneSecond = 1_000_000_000L;
        double drawInterval = (double) oneSecond / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remaining = (nextDrawTime - System.nanoTime()) / oneSecond;
                if (remaining < 0) remaining = 0;
                Thread.sleep((long) (remaining * 1000));
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates game logic based on current state (playing, paused, etc.).
     */
    public void update() {
        if (gameState == GameState.PLAYING) {
            player.update();
            Arrays.stream(npcs).filter(Objects::nonNull).forEach(Entity::update);
            for (int i = 0; i < monsters.length; i++) {
                Entity m = monsters[i];
                if (m != null) {
                    if (m.alive) m.update();
                    else monsters[i] = null;
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                Entity p = projectileList.get(i);
                if (p.alive) p.update(); else projectileList.remove(i);
            }
        } else if (gameState == GameState.PAUSED || gameState == GameState.CHARACTER_STATE) {
            if (!music.isPlaying()) playMusic(SoundAssets.PAUSED);
        }
    }

    /**
     * Renders the game: title screen, tiles, entities, and UI overlays.
     *
     * @param g the Graphics context to paint to
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        long start = System.nanoTime();
        if (gameState == GameState.TITLE_SCREEN) {
            uiController.draw(g2);
        } else {
            tileManager.draw(g2);
            entityList.add(player);
            Collections.addAll(entityList, npcs);
            Collections.addAll(entityList, gameObjects);
            Collections.addAll(entityList, monsters);
            entityList.addAll(projectileList);
            entityList.removeIf(Objects::isNull);
            entityList.sort(Comparator.comparingInt(e -> e.worldY));
            entityList.forEach(e -> e.draw(g2));
            entityList.clear();
            uiController.draw(g2);
        }
        if (keyH.showDebug) {
            g2.setFont(g2.getFont().deriveFont(20f));
            long passed = System.nanoTime() - start;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time " + passed + " ns", 10, 400);
            g2.drawString("WorldX " + player.worldX, 10, 420);
            g2.drawString("WorldY " + player.worldY, 10, 440);
        }
        g2.dispose();
    }

    /**
     * Plays looping background music.
     *
     * @param key the music asset to play
     */
    public void playMusic(SoundAssets key) {
        music.setFile(key);
        music.play();
    }

    /**
     * Plays a one-shot sound effect.
     *
     * @param key the sound effect asset to play
     */
    public void playSoundEffect(SoundAssets key) {
        soundFx.setFile(key);
        soundFx.play();
    }

    /**
     * Toggles between PAUSED and PLAYING states.
     */
    public void switchGamePauseState() {
        if (gameState == GameState.PAUSED) gameState = GameState.PLAYING;
        else if (gameState == GameState.PLAYING) gameState = GameState.PAUSED;
    }

    /**
     * Toggles between CHARACTER_STATE and PLAYING states.
     */
    public void switchGameCharacterState() {
        if (gameState == GameState.CHARACTER_STATE) gameState = GameState.PLAYING;
        else gameState = GameState.CHARACTER_STATE;
    }

    /**
     * @return the width of the game view in pixels
     */
    public int getScreenWidth() { return screenWidth; }

    /**
     * @return the height of the game view in pixels
     */
    public int getScreenHeight() { return screenHeight; }

    /**
     * @return the size of a tile in pixels
     */
    public int getTileSize() { return tileSize; }
}
