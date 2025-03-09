package main;


//import utilities.controller.test.ControllerEvents;

import com.studiohartman.jamepad.ControllerManager;
import utilities.controller.PS5ControllerManager;
import utilities.controller.PS5ModifiedController;
import utilities.controller.PS5NewImplementation;
import utilities.controller.test.ControllerEvents;
import view.GamePanel;

import javax.swing.*;

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

//		PS5ControllerManager ps5ControllerDriver = new PS5ControllerManager();
//		ps5ControllerDriver.start();


		// Check if a button is currently pressed

//		ControllerEvents ctrl = new ControllerEvents();
//		ctrl.run();



// "/usr/local/lib/" for Intel Homebrew
//		ControllerManager controllers = new ControllerManager();
//
//
//		controllers.initSDLGamepad();



		JFrame window  = new JFrame();
		 window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 window.setResizable(false);
		 window.setTitle("Jack Adventure");


		 GamePanel gamePanel = new GamePanel();

		 window.add(gamePanel);

		 window.pack();

		 window.setLocationRelativeTo(null);
		 window.setVisible(true);

		 gamePanel.setUpGame();
		 gamePanel.startGameThread();

	}
	
}
