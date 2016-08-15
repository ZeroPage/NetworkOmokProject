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
	boolean turn;

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
	OmokGame game;
	protected int lineNum;
	private LinePanel lp;
	public OmokGui(int gLineNum, OmokData game) {
		// TODO Auto-generated constructor stub
		super("NeOP's Super Awesome Omok Game");
		this.game = game;
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

		if(!game.myTurn){
			return;
		}

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
			game.put(x, y)
		}
	}
	public void put(int x, int y){
		circleDraw(lp.getGraphics(), x, y);
	}

	public void gameEnd(){
		System.out.println("Game End");
		JOptionPane.showMessageDialog(this, (game.whoIsTurnIsIt() == 1 ? "White" : "Black") + " Win!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
	}

	private void circleDraw(Graphics g,int x,int y){
		x = x * 30 - 15;
		y = y * 30 - 15;
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
