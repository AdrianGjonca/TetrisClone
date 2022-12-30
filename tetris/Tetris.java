package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Tetris {
	final static Color colors[] ={
		Color.WHITE,
		Color.RED,
		Color.BLUE,
		Color.YELLOW,
		Color.GREEN,
		Color.PINK,
		Color.MAGENTA,
		Color.GRAY
	};
	
	static int block[][] = {
		{0,0,0,0},
		{1,1,1,1},
		{0,0,0,0},
		{0,0,0,0},
	};
	
	static int board[][] = new int[10][20];
	
	static int blockDrop = 0;
	static int blockX = 0;
	static int blockColor = 1;
	
	static int blocktype = 0;
	static int rotation = 0;
	
	static int blockDropF = 100;
	
	static int clears = 0;
	static int level = 1;
	static int blockCount = 0;
	static boolean gameover = false;
	static void drawGrid(int offx, int offy, Graphics g) {
		g.setColor(Main.edge);
		g.drawRect(offx-1, offy-1, 20*10 + 2, 20*20 +2);
		g.setColor(Main.superedge);
		g.drawRect(offx-2, offy-2, 20*10 + 4, 20*20 +4);
		g.setColor(Main.edgeFade);
		g.drawRect(offx-3, offy-3, 20*10 + 6, 20*20 +6);
		
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 20; y++) {
				g.setColor(colors[board[x][y]]);
				g.fillRect(offx + x*20, offy + y*20, 20, 20);
				g.setColor(Color.DARK_GRAY);
				g.drawRect(offx + x*20, offy + y*20, 20, 20);
			}
		}
		
		for(int x = 0; x<4; x++) {
			for(int y = 0; y<4; y++) {
				g.setColor(colors[blockColor * block[x][y]]);
				if(block[x][y]>0)g.fillRect(offx + (blockX+x) * 20 + 1, offy + (blockDrop+y) * 20 + 1, 19, 19);
			}
		}
		
		g.setColor(Main.bg);
		g.fillRect(250, 0, 250, 500);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica",Font.PLAIN,30));
		g.drawString("Level "+level, 260, 60);
		g.setFont(new Font("Helvetica",Font.ITALIC,18));
		g.drawString("Clears "+clears, 260, 95);
		
		g.setFont(new Font("Helvetica",Font.BOLD,18));
		g.drawString("Score "+(300 * clears + 50*blockCount + 1000*(level*level-1)), 260, 120);
	}
	
	public static boolean wait = false;
	static Random rand = new Random();
	
	static void tick() {
		if(blockDropF <= 0) {
			gameover = true;
		}
		
		if(clears > level*2) {
			level++;
			clears=0;
		}
		wait = true;
		blockDrop++;
		if(!checkValid()) {
			blockDrop--;
			for(int x = 0; x<4; x++) {
				for(int y = 0; y<4; y++) {
					if(block[x][y] > 0) board[blockX+x][blockDrop+y] = blockColor * block[x][y];
				}
			}
			blockDropF = blockDrop;
			blockDrop = 0;
			blockX = rand.nextInt(2, 7);
			blocktype = rand.nextInt(0, Blocks.blocks.length);
			blockColor = blocktype + 1;
			rotation = rand.nextInt(0, Blocks.blocks[blocktype].length);
			block = Blocks.blocks[blocktype][rotation];
			blockCount++;
		}
		clearLine();
		wait = false;
	}
	
	
	public static void rotate() {
		rotation++;
		if(rotation >= Blocks.blocks[blocktype].length) rotation = 0;
		block = Blocks.blocks[blocktype][rotation];
		if(!checkValid()) {
			rotation--;
			if(rotation < 0) rotation = Blocks.blocks[blocktype].length;
			block = Blocks.blocks[blocktype][rotation];
		}
	}
	public static void left() {
		blockX--;
		if(!checkValid()) {
			blockX++;
		}
	}
	public static void right() {
		blockX++;
		if(!checkValid()) {
			blockX--;
		}
	}
	
	static void clearLine() {
		for(int y = 0; y<20; y++) {
			boolean toClear = true;
			for(int x = 0; x< 10; x++) {
				if(board[x][y] == 0) toClear = false;
			}
			if(toClear) {
				clears++;
				for(int i = y; i > 0; i--) {
					for(int x = 0; x< 10; x++) {
						board[x][i] = board[x][i-1];
					}
				}
				clearLine();
			}
		}
	}
	
	static boolean checkValid() {
		for(int x = 0; x<4; x++) {
			for(int y = 0; y<4; y++) {
				try {
					if(block[x][y]>0 && board[blockX+x][blockDrop+y]>0) {
						return false;
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					return false;
				}
				
			}
		}
		return true;
	}
}
