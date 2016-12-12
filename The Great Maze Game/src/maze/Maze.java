package maze;

import java.awt.*;

public class Maze
{    
    int width;
    int height;
    static final int MAX_WALLS = 100;
    
    RectangularEntity[] walls = new RectangularEntity[MAX_WALLS];
    int numWalls = 0;    
    
    static int numMazes = 0;
    
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public int getNumWalls() {
        return this.numWalls;
    }
    public void setNumWalls(int numWalls) {
        this.numWalls = numWalls;
    }
    
    public void addWall(int x, int y, int width, int height, Color color, Graphics pen) {
        RectangularEntity wall = new RectangularEntity(x, y, width, height, color);
        wall.draw(pen);
        this.walls[numWalls] = wall;
        this.numWalls++;
    }
    
    public boolean overlapsWith(RectangularEntity ent) {
        for (int i = 0; i < numWalls; i++) {
            if (ent.collidedWith(this.walls[i])) {
                return true;
            }
        }
        return false;
    }
}
