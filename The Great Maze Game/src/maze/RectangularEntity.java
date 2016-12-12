package maze;

import java.awt.*;
import java.util.Random;

public class RectangularEntity {
	 int x;
	 int y;
	 int width;
	 int height;
	 Color color;
	
	 Random rand = new Random();

	 public RectangularEntity() {
	 }
	
	 public RectangularEntity(int x, int y, int width, int height, Color c) {
		  this.x = x;
		  this.y = y;
		  this.width = width;
		  this.height = height;
		  this.color = c;
	 }
	
	 public int getX() {
		 return this.x;
	 }
	
	 public void setX(int x) {
		 this.x = x;
	 }
	
	 public int getY() {
		 return this.y;
	 }
	
	 public void setY(int y) {
		 this.y = y;
	 }
	
	 public void draw(Graphics pen) {
		  pen.setColor(this.color);
		  pen.fillRect(this.x, this.y, this.width, this.height);
	 }
	
	 public boolean collidedWith(RectangularEntity ent) {
		  if (this.x + this.width >= ent.x && this.x <= ent.x + ent.width) {
			   if (this.y + this.height >= ent.y && this.y <= ent.y + ent.height) {
				   return true;
			   	}
		  }
		  return false;
	 }
	
	 public String toString() {
		 return "I am a rectangle at (" + this.x + ", " + this.y + ") of width " + this.width + " and height " + this.height;
	 }
	
	 public void teleport() {
		 int x = rand.nextInt(GameEngine.mazeWidth);
		 int y = rand.nextInt(GameEngine.mazeHeight);
		 
		 this.setX(x);
		 this.setY(y);
	 }
	
	 public void teleportWithinWalls(Maze maze) {
		 this.teleport();
		 while (maze.overlapsWith(this)) {
			 this.teleport();
		 }
	 }	 
}
