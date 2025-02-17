package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.*;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTING
	
	final int originalTileSize = 16; //16x16 tile
	
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 16*3 = 48x48 tile
	
	public final int maxScreenCol = 12;
	public final int maxScreenRow = 16;
	
	public final int screenWidth = tileSize * maxScreenRow ; //768 pixels

	public final int screenHeight = tileSize * maxScreenCol; // 576 pixels
	
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	public final int worldWidth =  tileSize * maxWorldCol;	
	public final int worldheight =  tileSize * maxWorldRow;

	
	//FPS
	int FPS = 30;

	
	KeyHandler keyH = new KeyHandler();
	
	Thread gameThread;
	public Player player = new Player(this, this.keyH);
	TileManager tileManager = new TileManager(this);
	
	
	
	//set Default Player Position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	
	
	public void startGameThread() {
		this.gameThread = new Thread(this);
		this.gameThread.start();
	}
	
	
	@Override
	public void run() {
		
		long oneSecond  = 1000000000;

		
		double drawInterval = oneSecond / FPS; //0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(this.gameThread != null) {
//			System.out.println("The game loop is running");
			

			// Update information about the character
			this.update();
			
			// Draw the screen with the updated information
			repaint();
			
			
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/oneSecond;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	public void update() {
		player.update();
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		tileManager.draw(g2);
		player.draw(g2);

		g2.dispose();
		
	}
}
