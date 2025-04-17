package main;

import view.GamePanel;
import view.TileEditor;

import javax.swing.*;
import java.awt.*;

/**
 * The MainDriver class serves as the entry point for the Tank Shooter game application.
 * <p>
 * It configures system properties and native library paths required for SDL2 and LWJGL,
 * initializes the main application window, adds the GamePanel for rendering and input,
 * and starts the game thread.
 * </p>
 *
 * @author Emmanuel Botwe
 */
public class MainDriver {
	/**
	 * The main method initializes native libraries, sets up the game window,
	 * and starts the game loop.
	 *
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		// Set the library path for JNA (SDL2) on ARM-based macOS
		System.setProperty("jna.library.path", "/opt/homebrew/lib");
		// Load the SDL2 native library explicitly
		System.load("/opt/homebrew/lib/libSDL2-2.0.0.dylib");

		// Set the LWJGL library path for macOS Big Sur+ with M1/M2 chips
		System.setProperty("org.lwjgl.librarypath", "/opt/homebrew/lib/");

		// Create and configure the main application window
		JFrame window = new JFrame();
		window.setSize(900, 900);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("Tank Shooter");

		// Initialize the game panel and add it to the window
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);

		// Pack to fit preferred component sizes
		window.pack();
		// Center the window on screen
		window.setLocationRelativeTo(null);
		// Make the window visible
		window.setVisible(true);

		// Set up and start the game thread
		gamePanel.setUpGame();
		gamePanel.startGameThread();
	}
}
