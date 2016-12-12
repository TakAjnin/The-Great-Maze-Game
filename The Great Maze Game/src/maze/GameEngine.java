package maze;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class GameEngine extends Canvas {

    Character player = new Character(330, 550, 20, 20, Color.GREEN, 15);
    int initialNumLives = 3;
    int moves = 0;
    boolean playerWon = false;

    Lives playerLives = new Lives(10, 610, 40, 20, Color.GREEN, 10, initialNumLives);

    Character enemy1 = new Character(200, 200, 80, 80, Color.RED, 40);
    Character enemy2 = new Character(300, 300, 60, 60, Color.RED, 60);
    Character enemy3 = new Character(400, 400, 45, 45, Color.RED, 80);

    RectangularEntity finishLine = new RectangularEntity(207, 0, 93, 7, Color.GREEN);

    boolean godMode = false;

    static int mazeWidth = 600;
    static int mazeHeight = 600;
    static Color mazeColor = Color.DARK_GRAY;
    Maze theMaze = new Maze(mazeWidth, mazeHeight);

    Coins theCoins = new Coins(20, 20, Color.YELLOW, 5);
    // A dice of this many sides is rolled when the player picks
    // up a coin. If a 1 is rolled, the player gains an extra life.
    int extraLifeDice = 2;

    boolean displayedInitialScreen = false;
    boolean displayedWinMessage = false;

    static GameWindow window;

    public GameEngine() {
        setSize(new Dimension(theMaze.width, theMaze.height));
        setBackground(Color.LIGHT_GRAY);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyPress) {
                // Each key press is one turn
                runGameEngine(keyPress);
            }
        });
    }

    public void runGameEngine(KeyEvent keyPress) {
        switch (keyPress.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                // move along positive Y axis by (playerSpeed) pixels
                player.y += player.speed;
                break;
            case KeyEvent.VK_UP:
                player.y -= player.speed;
                break;
            case KeyEvent.VK_LEFT:
                player.x -= player.speed;
                break;
            case KeyEvent.VK_RIGHT:
                player.x += player.speed;
                break;
            case KeyEvent.VK_I:
                if (godMode == true) {
                    godMode = false;
                } else {
                    godMode = true;
                }
                break;
        }

        enemy1.moveRandomly();
        enemy2.moveRandomly();
        enemy3.moveRandomly();

        if (player.collidedWith(enemy1) ||
            player.collidedWith(enemy2) ||
            player.collidedWith(enemy3)) {
            if (!godMode) {
                playerLives.removeLife();
                // System.out.print("You got eaten by a rektangle! "); // debug
                if (playerLives.getNumLives() >= 0) {
                    // System.out.println("Lives remaining: " + playerLives.getNumLives()); // debug

                    player.returnToStart();
                } else {
                    window.endGame("You got eaten by a rektangle! ");
                }
            }
        }

        if (theMaze.overlapsWith(player)) {
            if (!godMode) {
                playerLives.removeLife();
                // System.out.print("You took a brick to the face. "); // debug
                if (playerLives.getNumLives() >= 0) {
                    // System.out.println("Lives remaining: " + playerLives.getNumLives()); // debug
                } else {
                    window.endGame("You took a brick to the face. ");
                }

                player.returnToStart();
            }
        }

        if (theCoins.removeCoinIfTouching(player)) {
            Random rand = new Random();
            if (rand.nextInt(extraLifeDice) == 0) {
                playerLives.addLife();
            }
        }

        Portals.teleportIfInPortal(player);

        if (player.collidedWith(finishLine)) {
            if (theCoins.numCoins == 0) {
                playerWon = true;
                System.out.println("You win!"); // debug
                if (displayedWinMessage == false) {
                    window.endGame("Congratulations, you win! ");
                    displayedWinMessage = true;
                }
            } else {
                System.out.println("You haven't collected all the coins!");
                player.returnToStart();
            }
        }
        repaint();
    }

    public void paint(Graphics pen) {
        theMaze.numWalls = 0;

        // paint player sprite
        if (playerLives.getNumLives() >= 0) {
            player.draw(pen);
        }

        playerLives.drawLives(pen);

        // paint enemy sprites
        enemy1.draw(pen);
        enemy2.draw(pen);
        enemy3.draw(pen);

        // paints coins
        theCoins.drawCoins(pen);

        // paints maze walls    
        drawWalls(pen);

        if (displayedInitialScreen == false) {
            theCoins.teleportCoinsRandomly(theMaze);

            Portals.setNumPortals(0);
            Portals.addPortal(550, 50, 30, 30, 100, 100, player.width, player.height, Color.CYAN);
            Portals.addPortal(128, 557, 12, 27, 490, 550, player.width, player.height, Color.CYAN);
            Portals.addPortal(555, 420, 30, 30, 343, 343, player.width, player.width, Color.CYAN);
            Portals.addPortal(30, 250, 30, 30, player.startingX, player.startingY, player.width, player.height, Color.CYAN);
            displayedInitialScreen = true;
        }

        Portals.drawAllPortals(pen);
        theCoins.drawCoins(pen);
    }

    public void drawWalls(Graphics pen) {
        theMaze.setNumWalls(0);

        // Edge walls
        theMaze.addWall(0, 0, 200, 7, mazeColor, pen);
        theMaze.addWall(0, 593, 307, 7, mazeColor, pen);
        theMaze.addWall(375, 593, 225, 7, mazeColor, pen);
        theMaze.addWall(593, 0, 7, 593, mazeColor, pen);
        theMaze.addWall(0, 0, 7, 600, mazeColor, pen);
        theMaze.addWall(300, 0, 300, 7, mazeColor, pen);
        theMaze.addWall(300, 593, 76, 7, Color.ORANGE, pen);

        // Inner walls
        theMaze.addWall(75, 0, 7, 200, mazeColor, pen);
        theMaze.addWall(75, 200, 70, 7, mazeColor, pen);
        theMaze.addWall(145, 75, 7, 50, mazeColor, pen);
        theMaze.addWall(145, 125, 255, 7, mazeColor, pen);
        theMaze.addWall(200, 0, 7, 75, mazeColor, pen);
        theMaze.addWall(200, 75, 100, 7, mazeColor, pen);
        theMaze.addWall(400, 125, 7, 275, mazeColor, pen);
        theMaze.addWall(0, 300, 200, 7, mazeColor, pen);
        theMaze.addWall(200, 200, 7, 200, mazeColor, pen);
        theMaze.addWall(200, 200, 100, 7, mazeColor, pen);
        theMaze.addWall(300, 300, 100, 7, mazeColor, pen);
        theMaze.addWall(300, 300, 7, 150, mazeColor, pen);
        theMaze.addWall(375, 0, 7, 75, mazeColor, pen);
        theMaze.addWall(375, 75, 125, 7, mazeColor, pen);
        theMaze.addWall(460, 75, 7, 382, mazeColor, pen);
        theMaze.addWall(150, 450, 310, 7, mazeColor, pen);
        theMaze.addWall(150, 400, 7, 50, mazeColor, pen);
        theMaze.addWall(150, 543, 7, 50, mazeColor, pen);
        theMaze.addWall(90, 543, 60, 7, mazeColor, pen);
        theMaze.addWall(90, 418, 7, 125, mazeColor, pen);
        theMaze.addWall(200, 450, 7, 100, mazeColor, pen);
        theMaze.addWall(300, 518, 7, 82, mazeColor, pen);
        theMaze.addWall(300, 518, 75, 7, mazeColor, pen);
        theMaze.addWall(460, 518, 7, 75, mazeColor, pen);
        theMaze.addWall(460, 518, 75, 7, mazeColor, pen);
        theMaze.addWall(540, 400, 53, 7, mazeColor, pen);
        theMaze.addWall(540, 225, 7, 175, mazeColor, pen);
        theMaze.addWall(540, 150, 53, 7, mazeColor, pen);

        finishLine.draw(pen);
    }

    public static void main(String[] args) {
        try {
            window = new GameWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.startMenu.pack();
        window.startMenu.setVisible(true);
        window.startMenu.setResizable(false);
    }
}