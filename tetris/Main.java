package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main implements KeyListener{
	
	static BufferedImage screen = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
	
	public static Color bg = new Color(227, 226, 218);
	public static Color edge = new Color(163, 162, 153);
	public static Color superedge = new Color(156, 154, 140);
	public static Color edgeFade = new Color(207, 206, 198);
	public static void main(String[] args) throws InterruptedException {
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.add(panel);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new Main());
		frame.setMinimumSize(frame.getSize());
		panel.setBackground(bg);
		
		Graphics g = screen.createGraphics();
		
		while(true) {
			g.setColor(bg);
			g.fillRect(0, 0, 500, 500);
			Tetris.tick();
			Tetris.drawGrid(20, 20,g);
			panel.getGraphics().drawImage(screen, panel.getWidth()/2 - panel.getHeight()/2, 0,panel.getHeight(),panel.getHeight(), null);
			Thread.sleep((int)(200.0 / (Math.sqrt(0.5+((double)Tetris.level)/3.0))));
			
			while(Tetris.gameover) {
				g.setColor(bg);
				g.fillRect(0, 0, 500, 500);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Helvetica",Font.PLAIN,60));
				g.drawString("Level " + Tetris.level, 100, 200);
				g.setFont(new Font("Helvetica",Font.BOLD,18));
				g.drawString("Score "+(300 * Tetris.clears + 50*Tetris.blockCount + 1000*(Tetris.level*Tetris.level-1)), 120, 240);
				g.setFont(new Font("Helvetica",Font.ITALIC,30));
				g.drawString("Press P to play again!", 100, 350);
				panel.getGraphics().drawImage(screen, panel.getWidth()/2 - panel.getHeight()/2, 0,panel.getHeight(),panel.getHeight(), null);
				Thread.sleep(50);
				g.clearRect(0, 0, 500, 500);
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(Tetris.gameover) {
			if(e.getKeyChar() != 'p') return;
			Tetris.wait = false;
			Tetris.block = new int[][]{
					{0,0,0,0},
					{1,1,1,1},
					{0,0,0,0},
					{0,0,0,0},
				};
				
			Tetris.board = new int[10][20];
				
			Tetris.blockDrop = 0;
			Tetris.blockX = 0;
			Tetris.blockColor = 1;
				
			Tetris.blocktype = 0;
			Tetris.rotation = 0;
					
			Tetris.clears = 0;
			Tetris.level = 1;
			
			Tetris.blockDropF = 100;
			Tetris.gameover = false;
			Tetris.blockCount = 0;
		}
		while(Tetris.wait);
		if(e.getKeyChar() ==' ') {
			Tetris.rotate();
		}
		if(e.getKeyChar() =='a') {
			Tetris.left();
		}
		if(e.getKeyChar() =='d') {
			Tetris.right();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
