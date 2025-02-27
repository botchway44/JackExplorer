package main;


import javax.swing.JFrame;



import view.GamePanel;

public class MainDriver {
	
	public static void main(String[] args) {
		
		 JFrame window  = new JFrame();
		 window.setDefaultCloseOperation(0);
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
