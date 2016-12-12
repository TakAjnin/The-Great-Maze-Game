package maze;

import java.awt.Color;
import java.util.Random;

public class Character extends RectangularEntity {
    int speed;
    int startingX;
    int startingY;
    
    Random rand = new Random();
    
    public Character(int startingX, int startingY, int width, int height, Color c, int speed)
    {
        super(startingX, startingY, width, height, c);
        this.startingX = startingX;
        this.startingY = startingY;
        this.speed = speed;
    }
    
    public int getStartingX() {
        return this.startingX;
    }
    public void setStartingX(int startingX) {
        this.startingX = startingX;
    }
    public int getStartingY() {
        return this.startingY;
    }
    public void setStartingY(int startingY) {
        this.startingY = startingY;
    }
    
    public void returnToStart() {
        this.x = this.startingX;
        this.y = this.startingY;
    }
    
    public void moveRandomly() {
        int randomSpeed = rand.nextInt(this.speed) + 1;
        int direction = rand.nextInt(4) + 1;
        switch (direction) {
        case 1:
            this.x += randomSpeed; break;
        case 2:
            this.x -= randomSpeed; break;
        case 3:
            this.y += randomSpeed; break;
        case 4:
            this.y -= randomSpeed; break;
        }
        
        if (this.x < 0) this.x += Math.abs(4 * this.speed);
        if (this.x > GameEngine.mazeWidth) this.x -= Math.abs(4 * this.speed);
        if (this.y < 0) this.y += Math.abs(4 * this.speed);
        if (this.y > GameEngine.mazeHeight) this.y -= Math.abs(4 * this.speed);
    }
}