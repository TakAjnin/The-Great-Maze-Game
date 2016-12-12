package maze;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;

public class GameWindow extends JFrame implements ActionListener {
	
    JFrame gameWindow = new JFrame("The Great Maze Game");
    static int windowWidth = 606;
    static int windowHeight = 680;
    JFrame startMenu = new JFrame("Welcome to the Maze");
    
    Button startButton = new Button("Enter the Maze, If you Dare!");
    Button instructionsButton = new Button("Instructions");
    Button exitButton = new Button("I'm too scared");
    
    JPanel endGamePanel = new JPanel();
    static int endGamePanelHeight = 50;
    JLabel endGameMessage;
    Button yesButton = new Button("Yes");
    Button noButton = new Button("No");
    
    GameEngine engine = new GameEngine();
    
    boolean didInitialSetup = false;
 
    public GameWindow() throws IOException {    	
		// Create and set up the menu
        startMenu.setPreferredSize(new Dimension(500, 360));
        startMenu.setLocationRelativeTo(null);
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BufferedImage startMenuBackground = ImageIO.read(new File("Images/background.jpg"));
    	JLabel imgLabel = new JLabel(new ImageIcon(startMenuBackground));
    	startMenu.add(imgLabel);
      
        JPanel buttonPanel  = new JPanel();
        // Display the window
        startButton.setSize(200, 100);
        instructionsButton.setSize(200, 100);
        exitButton.setSize(200, 100);
        startButton.addActionListener(this);
        instructionsButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        buttonPanel.add(startButton, BorderLayout.CENTER);
        buttonPanel.add(instructionsButton, BorderLayout.CENTER);
        buttonPanel.add(exitButton, BorderLayout.CENTER);
        startMenu.getContentPane().add(buttonPanel, BorderLayout.NORTH);
    }
    
    
     //this is where the box is supposed to pop up at the bottom and ask the user if they want to continue.
     public void endGame(String message){
    	 
		 endGamePanel.removeAll();
    	 endGamePanel.setPreferredSize(new Dimension(windowWidth, endGamePanelHeight));
    	
    	 // Setting up the buttons
    	 yesButton.setSize(20, 10);
    	 noButton.setSize(20, 10);
    	 yesButton.addActionListener(this);
    	 noButton.addActionListener(this);
        
    	 // Creating the end game message
    	 endGameMessage = new JLabel(message + "Play again?");
        
    	 // Adding the end game message and buttons to the end game panel
    	 endGamePanel.add(endGameMessage);
    	 endGamePanel.add(yesButton, BorderLayout.SOUTH);
    	 endGamePanel.add(noButton, BorderLayout.SOUTH);
    	 gameWindow.getContentPane().add(endGamePanel, BorderLayout.SOUTH);
    	 
    	 gameWindow.pack();
    	 
    	 didInitialSetup = true;

    	 endGamePanel.setVisible(true);

    	 gameWindow.setVisible(true);
     }
    
    
    @Override
    public void actionPerformed(ActionEvent click) {
        if(click.getSource() == startButton){
            engine = new GameEngine();
            engine.requestFocus();
            
            gameWindow.getContentPane().add(engine);
            gameWindow.setSize(windowWidth, windowHeight);
            gameWindow.setResizable(true);
            gameWindow.setLocationRelativeTo(null);
            gameWindow.setVisible(true);
            gameWindow.pack();
            gameWindow.requestFocus();
            
            startMenu.setVisible(false);
        } else if (click.getSource() == instructionsButton) {
            JPanel instructions = new JPanel();
            JLabel line1 = new JLabel("You are the green rectangle, and you have stumbled onto the Great Maze.");
            JLabel line2 = new JLabel("You must navigate your way through the maze, avoiding the walls and enemies.");
            JLabel line3 = new JLabel("The red rektangles will eat you and the walls are made of poision.");
            JLabel line4 = new JLabel("You must collect all the coins before you cross the finish line.");
            JLabel line5 = new JLabel("You have 3 lives, and remember to use the portals. Good Luck, and don't get rekt!");
            JLabel line6 = new JLabel("Use the Arrow Keys to Move");
            
            instructions.add(line1);
            instructions.add(line2);
            instructions.add(line3);
            instructions.add(line4);
            instructions.add(line5);
            instructions.add(line6);
            
            startMenu.getContentPane().add(instructions, BorderLayout.CENTER);
           
            startMenu.setVisible(true);
        } else if (click.getSource() == exitButton) {
            System.exit(0);
        } else if (click.getSource() == yesButton){
        	engine.setVisible(false);
            
        	engine = new GameEngine();
            engine.requestFocus();
           
            gameWindow.getContentPane().add(engine);
            gameWindow.setSize(windowWidth, windowHeight + 10);
            System.out.println("set size to " +windowWidth +", " +windowHeight); //debug
            gameWindow.setResizable(true);
            gameWindow.setLocationRelativeTo(null);
            gameWindow.setVisible(true);
            gameWindow.pack();
            gameWindow.requestFocus();
            
            gameWindow.setVisible(true);
            endGamePanel.setEnabled(false);
            endGamePanel.setVisible(false);
        } else if (click.getSource() == noButton){
            System.exit(0);
        }
    }
}