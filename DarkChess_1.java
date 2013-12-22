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
	    frame = new Frame("Dark Chess");	//建立新的介面frame title為Five
		frame.setSize(length, width);	//設置介面的大小
		frame.setResizable(false);	//固定介面大小
		frame.setLocation(10, 10);	//設置介面出現位置
		board = new ChessBoard();	//新增棋盤
		ControlPanelLeft leftControl = new ControlPanelLeft();	//新增 左邊的控制鍵
    	ControlPanelRight rightControl = new ControlPanelRight();	//新增 右邊的控制鍵
    	
		panelL.add(leftControl, BorderLayout.WEST);
		panelM.add(board, BorderLayout.CENTER);
		panelR.add(rightControl, BorderLayout.EAST);
		board.addMouseListener(board.new MouseClicked());	//board加入setChess()來讀取滑鼠按鍵
		frame.add(panelL, BorderLayout.WEST);	//左邊的控制鍵 加到介面左邊
		frame.add(board, BorderLayout.CENTER);	//棋盤 加到介面中央
		frame.add(panelR, BorderLayout.EAST);	//右邊的控制鍵 加到介面右邊
		
		frame.setVisible(true);	//顯示farme介面
		frame.addWindowListener(new AdapterDemo());	//按右上角叉叉能關閉frame
	}
	
	static class ControlPanelLeft extends Panel implements ActionListener {  
	    int tm_unit=200;
	    int tm_sum =0;
	    int sec=0;
	    private JLabel player = new JLabel("    玩家1");
	    private JLabel steps = new JLabel("  移動數 = ");
	    private JLabel time = new JLabel("  時間 = ");
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
	    		player.setText("    玩家1");
	    	else
	    		player.setText("    玩家2");
	    }
	    public void steps_event(){
	    	if(board.getPlayer() == 1)
	    		steps.setText("  玩家1步數 = "+ board.getCount()/2);
	    	else
	    		steps.setText("  玩家2步數 = "+ board.getCount()/2);
	    }
	    public void timer_event(){
			if ((tm_sum += tm_unit) >= 1000 && board.getGameStatus() == false){
				tm_sum -= 1000;
				sec+=1;
				time.setText("  時間= " + sec +"s");
			}
	    }
		public void actionPerformed(ActionEvent e){
			player_event();
			timer_event();
			steps_event();
		}
	}
	
	//****	右控制鍵	****//
	static class ControlPanelRight extends Panel implements ActionListener {
		Button b0 = new Button("設  置");
	    Button b1 = new Button("悔  棋"); 
	    Button b2 = new Button("重  新");   
	    Button b3 = new Button("幫  助");   
	    Button b4 = new Button("離  開"); 
	    
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
		
		//****	設置畫面	****//
		class Set implements ActionListener {
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p2 = new Panel();
			Panel p3 = new Panel();
			Frame frameSet = new Frame("設  置");
			Button b0 = new Button("15 x 15");
			Button b1 = new Button("17 x 17");
			Button b2 = new Button("19 x 19");
			JTextArea text = new JTextArea("棋盤大小");
			
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
		
		//****	重新開始	****//
		class Renew implements ActionListener{
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p3 = new Panel();
			Frame frameRenew = new Frame("重  新");
			Button b0 = new Button("取  消");
			Button b1 = new Button("確  定");
			JTextArea text = new JTextArea("確定要重新開始?");
			
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
		
		//****	幫助畫面	****//
		class Help implements ActionListener {
			Frame frameHelp = new Frame("幫  助");
			Button bb = new Button("確定");
			JTextArea text = new JTextArea(
					" 棋子數量總共32支，紅、黑方各16支；遊戲棋盤格式為 4 x 8 ，\n" +
					" 一開始棋子皆蓋著。棋子的大小依序為：\n" +
					" 帥 > 仕 > 相 > 車 > 傌 > 炮 > 兵；將 > 士 > 象 > 車 > 馬 > 包 > 卒。\n" +
					" 走棋時，所有棋子皆只能直走(或橫移)一步到空格(或可吃的棋子位置)，\n" +
					" 只有在炮\\包吃棋時，可以跳越中間一格(已翻開或蓋棋皆可)，\n" +
					" 但炮\\包在不吃棋子的情況下，亦只能直走(或橫移)一步。");
			
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
		
		//**** 離開畫面	****//
		class Exit implements ActionListener{
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p3 = new Panel();
			Frame frameExit = new Frame("離  開");
			Button b0 = new Button("取  消");
			Button b1 = new Button("確  定");
			JTextArea text = new JTextArea("確定要離開?");
			
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

//****	棋盤	****//
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
	private Button bb = new Button("確定");
	private Button ww = new Button("確定");
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
	
	//****	設置棋子	****//
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
		//****	棋子位置	****//
		for(int i = 0; i < ROWS; i++){
	    	for(int j  =0; j < COLS; j++){
	    	}
		}
	}
	
	//****	畫出棋盤	****//
	public void paint(Graphics g){
		super.paint(g);
	
		//****	畫出格子	****//
		g.setColor(Color.BLACK);
		for(int i=0;i<=ROWS;i++){  
	        g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
	    }  
	    for(int i=0;i<=COLS;i++){  
	        g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
	    }
	}

	//****	檢查是否以放棋子	****//
	public void check(int x, int y){
		
	}
	
	//****	放置棋子	****//
	public void setChess(int x, int y){
		
	}
	//****	悔棋	****//
	public void regret() {
		
	}
	
	//****	有棋子時會出現的畫面	****//
	public void hasChess(){
		
	}
	
	//****	獲勝或和局畫面	****//
	public void win(){
		Panel p = new Panel();
		gameStatus = true;
		Graphics g = getGraphics();
		update(g);
		win.setSize(200, 100);
		win.setResizable(false);
		win.setLocation(300, 300);
		if(player == 1)
			textWin = new JTextArea("黑 方 勝 !!");
		else
			textWin = new JTextArea("紅 方 勝 !!");
		p.add(textWin, BorderLayout.CENTER);
		ww.addActionListener(this);
		win.add(p, BorderLayout.NORTH);
		win.add(ww, BorderLayout.SOUTH);
		win.setVisible(true);
		win.addWindowListener(new AdapterDemo());
	}
	
	//**** 接收按鈕	****//
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		if(button == bb){
			hasChess.dispose();
		}else if(button == ww){
			win.dispose();
		}
	}
	
	//**** 判斷贏	****//
	public boolean checkWin(){
		return false;
		
	}
	
	//**** 判斷贏的型態	****//
	private boolean chechWin(int i, int j, int player){
		return false;
		
	}
	
	//****	接收滑鼠點擊	****//
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
		        check(X, Y);	//檢查
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


//****	叉叉關閉視窗		****//
class AdapterDemo extends WindowAdapter {
  public void windowClosing(WindowEvent e){
  	e.getWindow().dispose();
  }
}