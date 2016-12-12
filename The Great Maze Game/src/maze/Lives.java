package maze;

import java.awt.*;

public class Lives {
	int numLives;
	
	int initialX;
	int initialY;
	int width;
	int height;

	Color color;
	int space;
	
	public Lives(int x, int y, int width, int height, Color c, int space, int num) {
		// int space is the space between each
		// life rectangle on the game screen
		this.numLives = num;
		
		this.initialX = x;
		this.initialY = y;
		this.width = width;
		this.height = height;
		this.color = c;
		this.space = space;
	}
	
	public int getNumLives() {
		return this.numLives;
	}
	
	public void removeLife() {
		this.numLives--;
	}
	
	public void addLife() {
		this.numLives++;
	}

	public void drawLives(Graphics pen) {
		pen.setColor(this.color);
		for (int i = 0; i < numLives; i++) {
			pen.fillRect(this.initialX + (i * (this.space + this.width)), this.initialY, this.width, this.height);
		}
	}
}
