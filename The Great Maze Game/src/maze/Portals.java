package maze;

import java.awt.*;

public class Portals extends RectangularEntity {
    int exitX;
    int exitY;
    int exitWidth;
    int exitHeight;
    static int numPortals = 0;
    static final int MAX_PORTALS = 20;
    static Portals[] portals = new Portals[MAX_PORTALS];
    
    public Portals() {
    	numPortals = 0;
    }
        
    public Portals(int x, int y, int width, int height, int exitX, int exitY, int exitWidth, int exitHeight, Color c) {
        super(x, y, width, height, c);
        this.exitX = exitX;
        this.exitY = exitY;
        this.exitWidth = exitWidth;
        this.exitHeight = exitHeight;
    }
        
    public static void addPortal(int x, int y, int width, int height, int exitX, int exitY, int exitWidth, int exitHeight, Color c) {
        portals[numPortals] = new Portals(x, y, width, height, exitX, exitY, exitWidth, exitHeight, c);
        numPortals++;
    }
    
    public static void setNumPortals (int num) {
    	numPortals = num;
    }
    
    public void drawPortal(Graphics pen) {
        this.draw(pen);
        pen.setColor(this.color);
        pen.drawRect(this.exitX, this.exitY, this.exitWidth, this.exitHeight);
    }
    public static void drawAllPortals(Graphics pen) {
        for (int i = 0; i < numPortals; i++) {
            portals[i].drawPortal(pen);
        }
    }
    
    public static void teleportIfInPortal(RectangularEntity ent) {
        for (int i = 0; i < numPortals; i++) {
            if (ent.collidedWith(portals[i])) {
                ent.setX(portals[i].exitX);
                ent.setY(portals[i].exitY);
            }
        }
    }
}