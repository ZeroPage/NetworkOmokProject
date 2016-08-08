package omok;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class LinePanel extends JPanel{
	protected int lineNum;
	int x0, y0;
	
	public LinePanel(int lineNum){
		this.lineNum = lineNum;
		this.x0 = 0;
		this.y0 = 0;
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i = 0; i<lineNum; i++){
			g.drawLine(x0+30 , 30+y0 + i*30, 30+x0+30*(lineNum-1), 30+y0 + i*30);
			g.drawLine(x0+30 + i*30 ,30+y0, 30+x0+i*30, 30+ y0+30*(lineNum-1));
		}
	}
}

public class OmokGui extends JFrame implements MouseListener{
	Omok game;
	protected int lineNum;
	private LinePanel lp;
	public OmokGui(int gLineNum) {
		// TODO Auto-generated constructor stub
		super("NeOP's Super Awesome Omok Game");
		game = new Omok(gLineNum, gLineNum);
		this.lineNum = gLineNum++;
		gLineNum *= 30;
		
		setVisible(true);
		Container contain = getContentPane();
		lp = new LinePanel(this.lineNum);
		contain.add(lp);
		this.setSize(this.getInsets().left + gLineNum, this.getInsets().top + gLineNum);
		lp.addMouseListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

	}
	
	public void mousePressed(MouseEvent e){
		int x, y, outerBoundary = 15 + lineNum * 30;
		if(!game.isItend()){
			x = e.getX() + 15;
			y = e.getY() + 15;
			
			if(x < 15 || y < 15 || x >= outerBoundary || y >= outerBoundary) {
				return;
			}
			x = (x - (x % 30)) / 30;
			y = (y - (y % 30)) / 30;
			//System.out.println(x + " " + y);
			//System.out.println("a");
			if(!game.put(x, y)){
				return;
			}
			circleDraw(lp.getGraphics(), x * 30 - 15, y * 30 - 15);
			if(game.isItend()){
				this.gameEnd();
			}
		}
	}
	
	public void gameEnd(){
		System.out.println("Game End");
		JOptionPane.showMessageDialog(this, (game.whoIsTurnIsIt() == 1 ? "White" : "Black") + " Win!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void circleDraw(Graphics g,int x,int y){
		if(game.whoIsTurnIsIt() == 1){
			g.setColor(Color.WHITE);
		}
		else{
			g.setColor(Color.BLACK);
		}
		g.fillOval(x, y, 30, 30);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
