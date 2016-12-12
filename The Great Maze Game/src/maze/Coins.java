package maze;

import java.awt.*;

public class Coins {
    RectangularEntity[] coins = new RectangularEntity[100];
    int numCoins = 0;
    
    public Coins() {
        this.numCoins = 0;
    }
    
    public Coins(int width, int height, Color color,int numCoins) {
        this.numCoins = numCoins;
        for (int i = 0; i < numCoins; i++) {
            this.coins[i] = new RectangularEntity(GameEngine.mazeWidth / 2, GameEngine.mazeHeight / 2, width, height, color);
        }
    }
    
    public int getNumCoins() {
        return this.numCoins;
    }
    public void addCoin(int x, int y, int width, int height, Color c) {
        this.coins[numCoins] = new RectangularEntity(x, y, width, height, c);
        numCoins++;
    }
    public void addCoin(RectangularEntity coin) {
        this.coins[numCoins] = coin;
        numCoins++;
    }
    public void generateCoins(int x, int y, int width, int height, Color c, int num) {
        for (int i = 0; i < num; i++) {
            this.addCoin(x, y, width, height, c);
        }
    }
    
    public void drawCoins(Graphics pen) {
        for (int i = 0; i < this.numCoins; i++) {
            this.coins[i].draw(pen);
        }
    }
    
    public void teleportCoinsRandomly(Maze m) {
        for (int i = 0; i < numCoins; i++) {
            this.coins[i].teleportWithinWalls(m);
        }
    }
    
    public boolean removeCoinIfTouching(RectangularEntity ent) {
    	boolean gotCoin = false;
        for (int i = 0; i < this.numCoins; i++) {
            if (ent.collidedWith(this.coins[i])) {
            	gotCoin = true;
                for (int j = i; j < numCoins; j++) {
                    this.coins[j] = this.coins[j+1];
                }
                numCoins--;
            }
        }
        
        return gotCoin;
    }
}
