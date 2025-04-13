package main;


//import utilities.controller.test.ControllerEvents;

import view.GamePanel;
import view.TileEditor;

import javax.swing.*;
import java.awt.*;

public class MainDriver {
	
	public static void main(String[] args) {
		System.setProperty("jna.library.path", "/opt/homebrew/lib");
		// Load SDL2 explicitly (ARM path shown)
		System.load("/opt/homebrew/lib/libSDL2-2.0.0.dylib");
		// Then load your SDL-dependent library (e.g., Jamepad)
//		System.loadLibrary("jamepad");

		// Required for macOS Big Sur+ with M1/M2 chips
		System.setProperty("org.lwjgl.librarypath",
				"/opt/homebrew/lib/"); // ARM path


		JFrame window  = new JFrame();
		window.setSize(900,900);
		 window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 window.setResizable(true);
		 window.setTitle("Tank Shooter");


		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);



//		TileEditor tleditor = new TileEditor();
//		JScrollPane scrPane = new JScrollPane(tleditor);
//		window.add(scrPane, BorderLayout.CENTER);
//		window.add(new Button("Hello"), BorderLayout.EAST);



		window.pack();
//		tleditor.startGameThread();

		 window.setLocationRelativeTo(null);
		 window.setVisible(true);

		 gamePanel.setUpGame();
		 gamePanel.startGameThread();
	}
	
}
