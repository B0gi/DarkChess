import java.awt.*;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class DarkChess {
	private static Frame frame;
	private static ChessBoard board;
	private static int length = 900;
	private static int width = 500;
	
	public static void main(String[] args) {
		startGame();
	}
	
	public static void  startGame(){
		Panel panelL = new Panel();
		Panel panelM = new Panel();
	    Panel panelR = new Panel();
	    frame = new Frame("Dark Chess");	//�إ߷s������frame title��Five
		frame.setSize(length, width);	//�]�m�������j�p
		frame.setResizable(false);	//�T�w�����j�p
		frame.setLocation(10, 10);	//�]�m�����X�{��m
		board = new ChessBoard();	//�s�W�ѽL
		ControlPanelLeft leftControl = new ControlPanelLeft();	//�s�W ���䪺������
    	ControlPanelRight rightControl = new ControlPanelRight();	//�s�W �k�䪺������
    	
		panelL.add(leftControl, BorderLayout.WEST);
		panelM.add(board, BorderLayout.CENTER);
		panelR.add(rightControl, BorderLayout.EAST);
		board.addMouseListener(board.new MouseClicked());	//board�[�JsetChess()��Ū���ƹ�����
		frame.add(panelL, BorderLayout.WEST);	//���䪺������ �[�줶������
		frame.add(board, BorderLayout.CENTER);	//�ѽL �[�줶������
		frame.add(panelR, BorderLayout.EAST);	//�k�䪺������ �[�줶���k��
		
		frame.setVisible(true);	//���farme����
		frame.addWindowListener(new AdapterDemo());	//���k�W���e�e������frame
	}
	
	static class ControlPanelLeft extends Panel implements ActionListener {  
	    int tm_unit=200;
	    int tm_sum =0;
	    int sec=0;
	    private JLabel player = new JLabel("    ���a1");
	    private JLabel steps = new JLabel("  ���ʼ� = ");
	    private JLabel time = new JLabel("  �ɶ� = ");
	    private Timer timer = new Timer(tm_unit, this);
	    
	    public ControlPanelLeft(){   
	    	timer.restart();
	        setLayout(new GridLayout(14,1,10,20));
	        add(new Label("                      ")); 
	        add(player);  
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(steps);
	        add(time);
	    }
	    public void player_event(){
	    	if(board.getPlayer() == 1)
	    		player.setText("    ���a1");
	    	else
	    		player.setText("    ���a2");
	    }
	    public void steps_event(){
	    	if(board.getPlayer() == 1)
	    		steps.setText("  ���a1�B�� = "+ board.getCount()/2);
	    	else
	    		steps.setText("  ���a2�B�� = "+ board.getCount()/2);
	    }
	    public void timer_event(){
			if ((tm_sum += tm_unit) >= 1000 && board.getGameStatus() == false){
				tm_sum -= 1000;
				sec+=1;
				time.setText("  �ɶ�= " + sec +"s");
			}
	    }
		public void actionPerformed(ActionEvent e){
			player_event();
			timer_event();
			steps_event();
		}
	}
	
	//****	�k������	****//
	static class ControlPanelRight extends Panel implements ActionListener {
		Button b0 = new Button("�]  �m");
	    Button b1 = new Button("��  ��"); 
	    Button b2 = new Button("��  �s");   
	    Button b3 = new Button("��  �U");   
	    Button b4 = new Button("��  �}"); 
	    
	    public ControlPanelRight(){
	    	setLayout(new GridLayout(14,1,10,5));
	        add(new Label("           "));
	        add(new Label());
	    	add(b0);
	        add(new Label());   
	        add(new Label());   
	        add(new Label());  
	        add(b1);
	        add(new Label());   
	        add(new Label());   
	        add(new Label());   
	        add(b2);   
	        add(b3);   
	        add(b4);   
	        b0.addActionListener(this);
	        b1.addActionListener(this);
	        b2.addActionListener(this);
	        b3.addActionListener(this);
	        b4.addActionListener(this);
	        setBounds(0,0,500,500);   
	    }
	    
		public void actionPerformed(ActionEvent e){
			Button button = (Button) e.getSource();
			if(button == b0){
			}else if(button == b1){
				
			}else if(button == b2){
				new Renew();
			}else if(button == b3){
				new Help();
			}else if(button == b4){
				new Exit();
			}
		}
		
		//****	�]�m�e��	****//
		class Set implements ActionListener {
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p2 = new Panel();
			Panel p3 = new Panel();
			Frame frameSet = new Frame("�]  �m");
			Button b0 = new Button("15 x 15");
			Button b1 = new Button("17 x 17");
			Button b2 = new Button("19 x 19");
			JTextArea text = new JTextArea("�ѽL�j�p");
			
			public Set(){
				frameSet.setSize(200, 100);
				frameSet.setResizable(false);
				frameSet.setLocation(300, 300);
				p0.add(b0);
				p1.add(b1);
				p2.add(b2);
				p3.add(text, BorderLayout.CENTER);
				b0.addActionListener(this);
				b1.addActionListener(this);
				b2.addActionListener(this);
				frameSet.add(p3, BorderLayout.NORTH);
				frameSet.add(p0, BorderLayout.WEST);
				frameSet.add(p1);
				frameSet.add(p2, BorderLayout.EAST);
				frameSet.setVisible(true);
				frameSet.addWindowListener(new AdapterDemo());
			}
			
			public void actionPerformed(ActionEvent e){
				
			}
		}
		
		//****	���s�}�l	****//
		class Renew implements ActionListener{
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p3 = new Panel();
			Frame frameRenew = new Frame("��  �s");
			Button b0 = new Button("��  ��");
			Button b1 = new Button("�T  �w");
			JTextArea text = new JTextArea("�T�w�n���s�}�l?");
			
			public Renew(){
				frameRenew.setSize(200, 100);
				frameRenew.setResizable(false);
				frameRenew.setLocation(300, 300);
				p0.add(b0);
				p1.add(b1);
				p3.add(text, BorderLayout.CENTER);
				b0.addActionListener(this);
				b1.addActionListener(this);
				frameRenew.add(p3, BorderLayout.NORTH);
				frameRenew.add(p0, BorderLayout.EAST);
				frameRenew.add(p1, BorderLayout.WEST);
				frameRenew.setVisible(true);
				frameRenew.addWindowListener(new AdapterDemo());
			}
			
			public void actionPerformed(ActionEvent e){
				Button button = (Button) e.getSource();
				if(button == b0){
					frameRenew.dispose();
				}else if(button == b1){
					frameRenew.dispose();
					frame.dispose();
					startGame();
				}
			}
		}
		
		//****	���U�e��	****//
		class Help implements ActionListener {
			Frame frameHelp = new Frame("��  �U");
			Button bb = new Button("�T�w");
			JTextArea text = new JTextArea(
					" �Ѥl�ƶq�`�@32��A���B�¤�U16��F�C���ѽL�榡�� 4 x 8 �A\n" +
					" �@�}�l�Ѥl�һ\�ۡC�Ѥl���j�p�̧Ǭ��G\n" +
					" �� > �K > �� > �� > �X > �� > �L�F�N > �h > �H > �� > �� > �] > ��C\n" +
					" ���ѮɡA�Ҧ��Ѥl�ҥu�ઽ��(�ξ)�@�B��Ů�(�Υi�Y���Ѥl��m)�A\n" +
					" �u���b��\\�]�Y�ѮɡA�i�H���V�����@��(�w½�}�λ\�Ѭҥi)�A\n" +
					" ����\\�]�b���Y�Ѥl�����p�U�A��u�ઽ��(�ξ)�@�B�C");
			
			public Help(){
				frameHelp.setSize(400, 160);
				frameHelp.setResizable(false);
				frameHelp.setLocation(300, 300);
				bb.addActionListener(this);
				frameHelp.add(text, BorderLayout.NORTH);
				frameHelp.add(bb, BorderLayout.SOUTH);
				frameHelp.setVisible(true);
				frameHelp.addWindowListener(new AdapterDemo());
			}
			
			public void actionPerformed(ActionEvent e){
				Button button = (Button) e.getSource();
				if(button == bb){
					frameHelp.dispose();
				}
			}
		}
		
		//**** ���}�e��	****//
		class Exit implements ActionListener{
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p3 = new Panel();
			Frame frameExit = new Frame("��  �}");
			Button b0 = new Button("��  ��");
			Button b1 = new Button("�T  �w");
			JTextArea text = new JTextArea("�T�w�n���}?");
			
			public Exit(){
				frameExit.setSize(200, 100);
				frameExit.setResizable(false);
				frameExit.setLocation(300, 300);
				p0.add(b0);
				p1.add(b1);
				p3.add(text, BorderLayout.CENTER);
				b0.addActionListener(this);
				b1.addActionListener(this);
				frameExit.add(p3, BorderLayout.NORTH);
				frameExit.add(p0, BorderLayout.EAST);
				frameExit.add(p1, BorderLayout.WEST);
				frameExit.setVisible(true);
				frameExit.addWindowListener(new AdapterDemo());
			}
			
			public void actionPerformed(ActionEvent e){
				Button button = (Button) e.getSource();
				if(button == b0){
					frameExit.dispose();
				}else if(button == b1){
					System.exit(0);
				}
			}
		}
	}
}

class Chess{
	private int[][] chess = {{1,2,2,2,2,2,3,3,4,4,5,5,6,6,7,7},
					{1,2,2,2,2,2,3,3,4,4,5,5,6,6,7,7}};
	
	public Chess(){}
	
	public int getChess(int x, int y){
		return chess[x][y];
	}
	
	public boolean checkMove(int i, int j, int k, int l){
		if(chess[i][j] == 7){
			if(i == k || j == l)
				if(checkEat(i, j, k, l))
					return true;
		}else{
			if(i-1 == k || i+1 == k || j-1 == l || j+1 == l)
				if(checkEat(i, j, k, l))
					return true;
		}
		return false;
	}
	
	public boolean checkEat(int i, int j, int k, int l){
		if(chess[i][j] == 7){
			return true;
		}else if(chess[i][j] == 1 && chess[k][l] != 2){
			return true;
		}else if(chess[i][j] >= chess[k][l]){
			return true;
		}else{
			return false;
		}
	}
}

//****	�ѽL	****//
class ChessBoard extends JComponent implements ActionListener {
	private final int MARGIN=80;
	private final int GRID_SPAN=75;
	private static int ROWS=4;
	private static int COLS=8;
	private int player = 1;
	private int count;
	private boolean gameStatus;
	int[][][] chessBoard = new int[4][8][2];
	boolean[][] alreadyUse = new boolean[2][16];
	int X, Y;
	private Frame hasChess = new Frame("");
	private Frame win = new Frame("Win");
	private Button bb = new Button("�T�w");
	private Button ww = new Button("�T�w");
	private JTextArea textHasChess = new JTextArea("");
	private JTextArea textWin = new JTextArea("");
	Chess chess = new Chess();
	
	public ChessBoard(){
		reset();
	}
	
	//****	get	****//
	public int getCount(){
		return count;
	}
	public int getPlayer(){
		return player;
	}
	public boolean getGameStatus(){
		return gameStatus;
	}
	
	//****	�]�m�Ѥl	****//
	public void reset(){
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 16; j++){
				alreadyUse[i][j] = false;
			}
		}
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 8;){
				int x = (int) (Math.random()*2);
				int y = (int) (Math.random()*16);
				if(alreadyUse[x][y] == false){
					alreadyUse[x][y] = true;
					chessBoard[i][j][1] = chess.getChess(x, y);
					chessBoard[i][j][0] = 0;
					j++;
				}
			}
		}
		//****	�Ѥl��m	****//
		for(int i = 0; i < ROWS; i++){
	    	for(int j  =0; j < COLS; j++){
	    	}
		}
	}
	
	//****	�e�X�ѽL	****//
	public void paint(Graphics g){
		super.paint(g);
	
		//****	�e�X��l	****//
		g.setColor(Color.BLACK);
		for(int i=0;i<=ROWS;i++){  
	        g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
	    }  
	    for(int i=0;i<=COLS;i++){  
	        g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
	    }
	}

	//****	�ˬd�O�_�H��Ѥl	****//
	public void check(int x, int y){
		
	}
	
	//****	��m�Ѥl	****//
	public void setChess(int x, int y){
		
	}
	//****	����	****//
	public void regret() {
		
	}
	
	//****	���Ѥl�ɷ|�X�{���e��	****//
	public void hasChess(){
		
	}
	
	//****	��өΩM���e��	****//
	public void win(){
		Panel p = new Panel();
		gameStatus = true;
		Graphics g = getGraphics();
		update(g);
		win.setSize(200, 100);
		win.setResizable(false);
		win.setLocation(300, 300);
		if(player == 1)
			textWin = new JTextArea("�� �� �� !!");
		else
			textWin = new JTextArea("�� �� �� !!");
		p.add(textWin, BorderLayout.CENTER);
		ww.addActionListener(this);
		win.add(p, BorderLayout.NORTH);
		win.add(ww, BorderLayout.SOUTH);
		win.setVisible(true);
		win.addWindowListener(new AdapterDemo());
	}
	
	//**** �������s	****//
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		if(button == bb){
			hasChess.dispose();
		}else if(button == ww){
			win.dispose();
		}
	}
	
	//**** �P�_Ĺ	****//
	public boolean checkWin(){
		return false;
		
	}
	
	//**** �P�_Ĺ�����A	****//
	private boolean chechWin(int i, int j, int player){
		return false;
		
	}
	
	//****	�����ƹ��I��	****//
	class MouseClicked extends MouseAdapter {
		public MouseClicked(){}
			
		public void mousePressed(MouseEvent e){
			/*
		    System.out.print("screen x: " + e.getXOnScreen());
		    System.out.print(", y: " + e.getYOnScreen() + "\n");
		    */
		    if (e.getButton() == MouseEvent.BUTTON1){
		        //System.out.println("left button");
		        X = (e.getX()-MARGIN/2)/35;
		        Y = (e.getY()-MARGIN/2)/35;
		        check(X, Y);	//�ˬd
		    }
		    if (e.getButton() == MouseEvent.BUTTON2){
		        System.out.println("middle button");
		    }
		    if (e.getButton() == MouseEvent.BUTTON3){
		        System.out.println("right button");
		    }
		    /*
		    System.out.println("mouse position: " + e.getPoint());
		    System.out.println("mouse screen position: " + e.getLocationOnScreen());
		    System.out.println("mouse clicks: " + e.getClickCount());
		    */
	    }
	}	
	
	
}


//****	�e�e��������		****//
class AdapterDemo extends WindowAdapter {
  public void windowClosing(WindowEvent e){
  	e.getWindow().dispose();
  }
}