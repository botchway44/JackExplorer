package view;

import model.Entity;
import tile.Tile;
import tile.TileManager;
import utilities.AssetSetter;
import utilities.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TileEditor   extends JPanel implements Runnable, MouseListener {

    public final TileManager tileManager;
    private Thread gameThread;
    private GamePanel gamePanel;
    private AssetSetter assetSetter;


    public TileEditor(){
        this.gamePanel = new GamePanel();
        setSize(this.gamePanel.worldWidth, this.gamePanel.worldHeight);

        this.tileManager = new TileManager(this.gamePanel, 0.4f);
        assetSetter = new AssetSetter(gamePanel);

        this.addMouseListener(this);
    }
    public void startGameThread() {
        this.assetSetter.setObject();
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }


    @Override
    public void run() {

        long oneSecond = 1000000000;


        double drawInterval = (double) oneSecond / 30; //0.01666 seconds
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


    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.drawAllTiles(g2);


        g2.dispose();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        System.out.println(e.getSource());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
