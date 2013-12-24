import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;

public class DarkChess {
	public static void main(String[] args){
		MainFrame game = new MainFrame();
		game.startGame();	//�}�l�C��
	}
}

//****	���l�ѹC��	****//
class MainFrame {
	private static Frame frame;
	private static ChessBoard board;
	private static ControlPanelLeft leftControl;
	private static ControlPanelRight rightControl;
	private static final int length = 950;
	private static final int width = 450;
	private static Message message;
	//****	�C���e��	****//
	public void startGame(){
		message = new Message();
		Panel panelL = new Panel();
		Panel panelM = new Panel();
	    Panel panelR = new Panel();
	    frame = new Frame("Dark");	//�إ߷s������frame title��Five
		frame.setSize(length, width);	//�]�m�������j�p
		frame.setResizable(false);	//�T�w�����j�p
		frame.setLocation(10, 10);	//�]�m�����X�{��m
		board = new ChessBoard();	//�s�W�ѽL
		leftControl = new ControlPanelLeft();	//�s�W ���䪺������
		rightControl = new ControlPanelRight();	//�s�W �k�䪺������
		panelL.add(leftControl, BorderLayout.WEST);
		panelM.add(board, BorderLayout.CENTER);
		panelR.add(rightControl, BorderLayout.EAST);
		frame.add(panelL, BorderLayout.WEST);	//���䪺������ �[�줶������
		frame.add(board, BorderLayout.CENTER);	//�ѽL �[�줶������
		frame.add(panelR, BorderLayout.EAST);	//�k�䪺������ �[�줶���k��
		
		frame.setVisible(true);	//���farme����
		frame.addWindowListener(new MainFrameClose());	//���k�W���e�e������frame
	}

	public Frame getFrame(){
		return frame;
	}
	public ChessBoard getBoard(){
		return board;
	}
	public Message getMessage(){
		return message;
	}
	public ControlPanelLeft getControlPanelLeft(){
		return leftControl;
	}
}

class Player {
	private int[] color = new int[2];
	private int player = 1;
	private int step = 0;
	private boolean setColor = false;
	
	public void reset(){
		setColor = false;
	}
	public void changePlayer(){
		if(player == 1)  player = 2;
		else if(player == 2) player = 1;
	}
	public void setColor(int theColor){
		if(setColor) return;
		if(theColor == 0)  {color[0] = 0; color[1] = 1;}
		else if(theColor == 1)  {color[0] = 1; color[1] = 0;}
		setColor = true;
	}
	public void addStep(){
		step++;
	}
	public void decStep(){
		step--;
	}
	public int getPlayer(){
		return player;
	}
	public int getColor(){
		return color[player-1];
	}
	public String getSColor(){
		if(color[player-1] == 0) return "����";
		else return "�¤�";
	}
	public int getStep(){
		return step;
	}
	public boolean getSetColor(){
		return setColor;
	}
	public boolean check(Chess chess){
		if(chess.getColor() == color[player-1])
			return true; 
		return false;
	}
}

class Coordinate {
	private Chess A;
	private Chess B;
	private int X1;
	private int Y1;
	private int X2;
	private int Y2;
	
	public Coordinate(Chess A, Chess B, int X1, int Y1, int X2, int Y2){
		this.A = A;
		this.B = B;
		this.X1 = X1;
		this.Y1 = Y1;
		this.X2 = X2;
		this.Y2 = Y2;
	}
	
	public Chess getA(){
		return A;
	}
	public Chess getB(){
		return B;
	}
	public int getX1(){
		return X1;
	}
	public int getY1(){
		return Y1;
	}
	public int getX2(){
		return X2;
	}
	public int getY2(){
		return Y2;
	}
}

class Regret {
	private ArrayList<Coordinate> regret = new ArrayList<Coordinate>();
	
	public Coordinate pop(){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		ChessAction action = board.getChessAction();
		Player player = action.getPlayer();
		if(regret.isEmpty()) return null;
		int index = regret.size()-1;
		if(index == 0) {
			player.reset();
		}
		return regret.remove(index);
	}
	public void add(Coordinate c){
		regret.add(c);
	}
}

class ChessAction extends Observable{
	private int step = 0;
	private int tmpX, tmpY;
	private static Player player = new Player();
	private Regret regret = new Regret();
	private Coordinate coordinate;
	private Chess A;
	private Chess B;
	public ChessAction(){
		addObserver(new Message());
	}
	public void check(int X, int Y){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		String s = "";
		int ROWS = board.getRows();
		int COLS = board.getCols(); 
		if(X < COLS && Y < ROWS){
			if(!board.getChess(X, Y).getState()){
				board.getChess(X, Y).setState(true);
				step = 0;
				player.setColor(board.getChess(X, Y).getColor());
				player.changePlayer();
				player.addStep();
				s = "�i½�ѡj"+ player.getSColor() +" step"+ (player.getStep()+1)/2 +"�G"+
						" ½�}("+ X +","+ Y +")  "+ board.getChess(X, Y).toString() +"\n";
				coordinate = new Coordinate(null, null, X, Y, -1, -1);
				regret.add(coordinate);
			}else{
				if(board.hasChess(X ,Y)){
					if(step == 0 || A.getColor() == board.getChess(X ,Y).getColor()){
						A = board.getChess(X ,Y);
						if(!player.check(A)) return;
						tmpX = X;
						tmpY = Y;
						step = 1;
					}else if(step == 1){
						if(!move(tmpX, tmpY, X, Y)){
							new HasChess("����");
							s = "�iĵ�i�j"+ A.toString() +"("+ tmpX +","+ tmpY +") ���ಾ�ʨ� ("+ X +","+ Y +")\n";
							step = 0;
						}else{
							B = board.getChess(X ,Y);
							if(eat(tmpX, tmpY, X, Y)){
								board.setChess(X, Y, A);
								board.setHasChess(tmpX, tmpY, false);
								B.setDie(true);
								step = 0;
								player.changePlayer();
								player.addStep();
								s = "�i�Y�ѡj"+ player.getSColor() +" step"+ (player.getStep()+1)/2 +"�G"+ A.toString()
										+"("+ tmpX +","+ tmpY +") �Y "+ B.toString() +"("+ X +","+ Y +")\n";
								coordinate = new Coordinate(A, B, tmpX, tmpY, X, Y);
								regret.add(coordinate);
							}else{
								new HasChess("�Y");
								s = "�iĵ�i�j"+ A.toString() +"("+ tmpX +","+ tmpY +") ����Y "+
										B.toString() + "("+ X +","+ Y +")" +"\n";
								step = 0;
							}
						}
					}
				}else if(step == 1){
					if(!move(tmpX, tmpY, X, Y)){
						new HasChess("����");
						s ="�iĵ�i�j"+ A.toString() +"("+ tmpX +","+ tmpY +") ���ಾ�ʨ� ("+ X +","+ Y +")\n";
						step = 0;
					}else{
						board.setHasChess(X, Y, true);
						board.setChess(X, Y, A);
						board.setHasChess(tmpX, tmpY, false);
						step = 0;
						player.changePlayer();
						player.addStep();
						s = "�i���ʡj"+ player.getSColor() +" step"+ (player.getStep()+1)/2 +"�G"+
								A.toString() +"("+ tmpX +","+ tmpY +")   ���ʨ� ("+ X +","+ Y +")\n";
						coordinate = new Coordinate(A, null, tmpX, tmpY, X, Y);
						regret.add(coordinate);
					}
				}
			}
			setChanged();
			notifyObservers(s);
			board.paint(board.getGraphics());
			if(step == 1){
				board.selectChess(X, Y);
			}
			int winer = win();
			if(winer != -1){
				new Win(winer);
				board.setGameStatus(false);
			}
		}
	}

	private boolean eat(int X1, int Y1, int X2, int Y2){
		MainFrame frame = new MainFrame();
		ChessBoard board = frame.getBoard();
		int priorityA = A.getPriority();
		int priorityB = B.getPriority();
		int colorA = A.getColor();
		int colorB = B.getColor();
		if(colorA == colorB){
			return false;
		}
		if(priorityA == 1) {
			int count = 0;
			if(X1 < X2){
				for(int i=X1;i<X2;i++) 
					if(board.getHasChess(i, Y2)) count++;
			}else if(X1 > X2){
				for(int i=X1;i>X2;i--) 
					if(board.getHasChess(i, Y2)) count++;
			}else if(Y1 < Y2){
				for(int i=Y1;i<Y2;i++) 
					if(board.getHasChess(X2, i)) count++;
			}else if(Y1 > Y2){
				for(int i=Y1;i>Y2;i--) 
					if(board.getHasChess(X2, i)) count++;
			}
			System.out.println(count);
			if(count == 2) return true;
		}else if(priorityA == 0 && priorityB == 6){
			return true;
		}else if(priorityA == 6 && priorityB == 0){
			return false;
		}else if(priorityA >= priorityB){
			return true;
		}
		return false;
	}
	
	private boolean move(int X1, int Y1, int X2, int Y2){
		MainFrame frame = new MainFrame();
		ChessBoard board = frame.getBoard();
		if(A.getPriority() == 1 && board.getHasChess(X2, Y2)){
			if((X1 > X2 || X1 < X2) && Y1 == Y2) return true;
			if((Y1 > Y2 || Y1 < Y2) && X1 == X2) return true;
		}else{
			if((X1+1 == X2 || X1-1 == X2) && Y1 == Y2) return true;
			if((Y1+1 == Y2 || Y1-1 == Y2) && X1 == X2) return true;
		}
		return false;
	}
	
	public void regret(){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		String s = "";
		if(!board.getGameStatus()) return;
		Coordinate c = regret.pop();
		if(c == null)	return;
		Chess A = c.getA();
		Chess B = c.getB();
		int X1 = c.getX1();
		int Y1 = c.getY1();
		int X2 = c.getX2();
		int Y2 = c.getY2();
		if(A == null){
			s = "�i���ѡj ½�}("+ X1 +","+ Y1 +")�G"+ board.getChess(X1, Y1).toString() +"\n";
			board.getChess(X1, Y1).setState(false);
			player.decStep();
			player.changePlayer();
		}else if(B == null){
			s = "�i���ѡj "+ A.toString() +"("+ X1 +","+ Y1 +") ���ʨ� ("+ X2 +","+ Y2 +")\n";
			board.setHasChess(X2, Y2, false);
			board.setChess(X1, Y1, A);
			board.setHasChess(X1, Y1, true);
			player.changePlayer();
			player.decStep();
		}else{
			s = "�i���ѡj "+ A.toString() +"("+ X1 +","+ Y1 +") �Y ("+ X2 +","+ Y2 +"):"+B.toString() +"\n";
			board.setChess(X1, Y1, A);
			board.setHasChess(X1, Y1, true);
			board.setChess(X2, Y2, B);
			board.setHasChess(X2, Y2, true);
			B.setDie(false);
			player.changePlayer();
			player.decStep();
		}
		setChanged();
		notifyObservers(s);
		board.paint(board.getGraphics());
	}
	
	private int win(){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		AllChess allChess = board.getAllChess();

		for(int j = 0; j < 2; j++){
			int count = 16;
			for(int i=0; i<16; i++){
				Chess chess = allChess.getChess(j, i);
				if(chess.isDie()==true){
					count--;
				}
			}
			if(count==0){
				return j;
			}
		}
		return -1;
	}
	
	public Player getPlayer(){
		return player;
	}
}


//****	�ѽL	****//
class ChessBoard extends JComponent{
	private final int MARGIN=20;
	private final int GRID_SPAN=90;
	private final int ROWS=4;
	private final int COLS=8;
	private boolean[][] hasChess = new boolean[COLS][ROWS];
	private Chess[][] chess = new Chess[COLS][ROWS];
	private boolean gameStatus;
	private AllChess allChess = new AllChess();
	private ChessAction chessAction = new ChessAction();
	
	public ChessBoard(){
		addMouseListener(new MouseClicked());
		reset();
	}
	
	//****	set	****//
	private void reset(){
		setGameStatus(true);
		for(int i = 0; i < COLS; i++)
			for (int j = 0; j < ROWS; j++)
				hasChess[i][j] = true;
		
		for(int i = 0; i < COLS; i++){
			for (int j = 0; j < ROWS;){
				int X = (int) (Math.random()*2);
				int Y = (int) (Math.random()*16);
				Chess tmp = allChess.getChess(X, Y);
				if(tmp.isDie()){
					tmp.setDie(false);
					chess[i][j] = tmp;
					j++;
				}
			}
		}
		
		for (int j = 0; j < ROWS; j++){
			for(int i = 0; i < COLS; i++){
				System.out.printf("%3s",chess[i][j].toString());
			}
			System.out.println();
		}
	}
	public void setChess(int i, int j, Chess theChess){
		this.chess[i][j] = theChess;
	}
	public void setHasChess(int i, int j, boolean change){
		hasChess[i][j] = change;
	}
	//****	get	****//
	public int getMargin(){
		return MARGIN;
	}
	public int getGridSpan(){
		return GRID_SPAN;
	}
	public int getRows(){
		return ROWS;
	}
	public int getCols(){
		return COLS;
	}
	public Chess getChess(int X, int Y){
		return chess[X][Y];
	}
	public Boolean getHasChess(int X, int Y){
		return hasChess[X][Y];
	}
	public ChessAction getChessAction(){
		return chessAction;
	}
	public AllChess getAllChess(){
		return allChess;
	}
	public void setGameStatus(boolean theGameStatus){
		this.gameStatus = theGameStatus;
	}
	public boolean getGameStatus(){
		return gameStatus;
	}
	public boolean hasChess(int X, int Y){
		if(hasChess[X][Y] == true)
			return true;
		else
			return false;
	}
	
	//****	�e�X�ѽL	****//
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		for(int i=0;i<=ROWS;i++){  
	        g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
	    }  
	    for(int i=0;i<=COLS;i++){  
	        g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
	    }
	    putChess(g);
	}
	
	private void putChess(Graphics g){
	    for(int i = 0; i < COLS; i++){
	    	for(int j = 0; j < ROWS; j++){
	    		if(!hasChess[i][j]){
	    			g.drawImage(new ImageIcon("chess/09.jpg").getImage(),23+GRID_SPAN*i,23+GRID_SPAN*j,this);
	    		}else if(!chess[i][j].getState()){
	    			g.drawImage(new ImageIcon("chess/08.jpg").getImage(),30+GRID_SPAN*i,30+GRID_SPAN*j,this);
	    		}else{
	    			int chessColor = chess[i][j].getColor();
	    			int chessID = chess[i][j].getIdentify();
	    			g.drawImage(new ImageIcon("chess/09.jpg").getImage(),23+GRID_SPAN*i,23+GRID_SPAN*j,this);
	    			switch(chessColor){
		    			case 0:
		    				g.drawImage(new ImageIcon("chess/"+ chessColor +""+ chessID +".jpg").getImage(),30+GRID_SPAN*i,30+GRID_SPAN*j,this);
		    				break;
		    			case 1:
		    				g.drawImage(new ImageIcon("chess/"+ chessColor +""+ chessID +".jpg").getImage(),30+GRID_SPAN*i,30+GRID_SPAN*j,this);
		    				break;
		    		}
	    		}
	    	}
		}
	}
	
	public void selectChess(int i, int j){
		Graphics g = getGraphics();
		int chessColor = chess[i][j].getColor();
		int chessID = chess[i][j].getIdentify();
		g.drawImage(new ImageIcon("chess/09.jpg").getImage(),23+GRID_SPAN*i,23+GRID_SPAN*j,this);
		g.drawImage(new ImageIcon("chess/"+ chessColor +""+ chessID +".jpg").getImage(),30+GRID_SPAN*i,23+GRID_SPAN*j,this);
	}
}


class Chess {
	private int priority; //�v��
	private int identify; //�s��
	private int color;
	private String name;
	private boolean isDie = true;
	private boolean isOpen = false; //�ȶ}�F��~
	
	public Chess(int color, int identify){
		setColor(color);
		setIdentify(identify);
		setPriority();
	}
	
	/* Set Methods */
	public void setColor(int color){
		this.color = color;
	}
	public void setIdentify(int identify){
		this.identify = identify;
	}
	public void setPriority(){
		this.priority = judgePriority(identify);
	}
	public void setDie(boolean change){
		this.isDie = change;
	}
	public void setState(boolean change){
		isOpen = change;
	}
	
	/* Get Methods */
	public int getColor(){
		return color;
	}
	public int getPriority(){
		return priority;
	}
	public int getIdentify(){
		return identify;
	}
	public boolean isDie(){
		return isDie;
	}
	public boolean getState(){
		return isOpen;
	}
	
	/*�P�_�v��*/
	private int judgePriority(int identify){
		switch(identify){
		case 0: 
			this.priority = 6;
			if(color == 0){
				name = "�N";
			} else
				name = "��";
			break;
		case 1:
			this.priority = 5;
			if(color == 0){
				name = "�h";
			} else
				name = "�K";
			break;
		case 2:
			this.priority = 4;
			if(color == 0){
				name = "�H";
			} else
				name = "��";
			break;
		case 3:
			this.priority = 3;
			if(color == 0){
				name = "��";
			} else
				name = "��";
			break;
		case 4:
			this.priority = 2;
			if(color == 0){
				name = "��";
			} else
				name = "��";
			break;
		case 5:
			this.priority = 1;
			if(color == 0){
				name = "�]";
			} else
				name = "��";	
			break;
		case 6:
			this.priority = 0;
			if(color == 0){
				name = "��";
			} else
				name = "�L";
			break;
		}
		return priority;
	}
	public String toString(){
		String s;
		//s = "��: " + this.name + "\t�s���G " + identify + "\t�v���G " + priority +" \n";
		s = this.name;
		return s;
	}
}

class AllChess{
	private Chess A1 = new Chess(0, 0);//�N
	private Chess A2 = new Chess(0, 1);//�h
	private Chess A3 = new Chess(0, 1);//�h
	private Chess A4 = new Chess(0, 2);//�H
	private Chess A5 = new Chess(0, 2);//�H
	private Chess A6 = new Chess(0, 3);//��
	private Chess A7 = new Chess(0, 3);//��
	private Chess A8 = new Chess(0, 4);//��
	private Chess A9 = new Chess(0, 4);//��
	private Chess A10 = new Chess(0, 5);//�]
	private Chess A11 = new Chess(0, 5);//�]
	private Chess A12 = new Chess(0, 6);//��
	private Chess A13 = new Chess(0, 6);//��
	private Chess A14 = new Chess(0, 6);//��
	private Chess A15 = new Chess(0, 6);//��
	private Chess A16 = new Chess(0, 6);//��
	
	private Chess B1 = new Chess(1, 0);//��
	private Chess B2 = new Chess(1, 1);//�K
	private Chess B3 = new Chess(1, 1);//�K
	private Chess B4 = new Chess(1, 2);//��
	private Chess B5 = new Chess(1, 2);//��
	private Chess B6 = new Chess(1, 3);//����
	private Chess B7 = new Chess(1, 3);//����
	private Chess B8 = new Chess(1, 4);//����
	private Chess B9 = new Chess(1, 4);//����
	private Chess B10 = new Chess(1, 5);//��
	private Chess B11 = new Chess(1, 5);//��
	private Chess B12 = new Chess(1, 6);//�L
	private Chess B13 = new Chess(1, 6);//�L
	private Chess B14 = new Chess(1, 6);//�L
	private Chess B15 = new Chess(1, 6);//�L
	private Chess B16 = new Chess(1, 6);//�L
	private Chess[][] chess = 
		{{A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16},
		{B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16}};
	
	public Chess getChess(int color, int index){
		return chess[color][index];
	}
}

//****	��������	****//
class ControlPanelLeft extends Panel implements ActionListener {
	private int tm_unit=200;
	private int tm_sum =0;
	private int sec=0;
    private JLabel color = new JLabel("");
    private JLabel steps = new JLabel("  �B��");
    private JLabel time = new JLabel("  �ɶ� = ");
    private Timer timer = new Timer(tm_unit, this);
    private MainFrame mainFrame = new MainFrame();
    private ChessBoard board = mainFrame.getBoard();
    private ChessAction chessAction = board.getChessAction();
    private Player player = chessAction.getPlayer();
    public ControlPanelLeft(){
    	timer.restart();
        setLayout(new GridLayout(20,1,10,30));
        add(new Label("                            ")); 
        add(color);
        add(new Label());
        add(new Label());
        add(new Label());
        add(steps);
        add(time);
    }
    public void paint(Graphics g){
    	super.paint(g);
    }
    private void player_event(){
    	Graphics g = getGraphics();
    	if(!player.getSetColor()){
    		g.drawImage(new ImageIcon("chess/09.jpg").getImage(),1,22,this);
    	}else if(player.getColor() == 0){
    		g.drawImage(new ImageIcon("chess/00.jpg").getImage(),1,22,this);
    	}else if(player.getColor() == 1){
    		g.drawImage(new ImageIcon("chess/10.jpg").getImage(),1,22,this);
    	}
    }
    private void steps_event(){
    	if(!player.getSetColor()){
    		steps.setText("  �B��");
    	}else if(player.getColor() == 0){
    		steps.setText("  �¤�B�� = "+ (player.getStep()+1)/2);
    	}else if(player.getColor() == 1){
    		steps.setText("  ����B�� = "+ (player.getStep()+1)/2);
    	}
    }
    private void timer_event(){
		if ((tm_sum += tm_unit) >= 1000 && board.getGameStatus() == true){
			tm_sum -= 1000;
			sec+=1;
			time.setText("  �ɶ�= " + sec +"s");
		}
    }
	public void actionPerformed(ActionEvent e){
			if(board.getGameStatus()){ 
				player_event();
				timer_event();
				steps_event();
			}
	}
}

//****	�k������	****//
class ControlPanelRight extends Panel implements ActionListener {
	private Button b0 = new Button("�]  �m");
	private Button b1 = new Button("��  ��"); 
	private Button b2 = new Button("��  �s");   
	private Button b3 = new Button("��  �U");   
	private Button b4 = new Button("��  �}"); 
    
    public ControlPanelRight(){
    	setLayout(new GridLayout(0, 1, 0, 5));
        add(new Label());
    	//add(b0);
        add(new Label());     
        add(new Label());
        add(new Label());  
        add(b1);
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
    }
    
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		ChessAction chessAction = board.getChessAction();
		
		if(button == b0){
			new Set();
		}else if(button == b1){
			if(board.getGameStatus() == true)
				chessAction.regret();
		}else if(button == b2){
			new Renew();
		}else if(button == b3){
			new Help();
		}else if(button == b4){
			new Exit();
		}
	}
}

//****	�]�m�e��	****//
class Set implements ActionListener {
	private Frame frameSet = new Frame("�]  �m");
	
	public Set(){
		frameSet.setSize(200, 100);
		frameSet.setResizable(false);
		frameSet.setLocation(300, 300);
		frameSet.setVisible(true);
		frameSet.addWindowListener(new CloseWindow());
	}
	
	public void actionPerformed(ActionEvent e){
	}
}

//****	���s�}�l	****//
class Renew implements ActionListener {
	private Panel p0 = new Panel();
	private Panel p1 = new Panel();
	private Panel p3 = new Panel();
	private Frame frameRenew = new Frame("��  �s");
	private Button b0 = new Button("��  ��");
	private Button b1 = new Button("�T  �w");
	private JTextArea text = new JTextArea("�T�w�n���s�}�l?");
	
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
		frameRenew.addWindowListener(new CloseWindow());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		MainFrame mainFrame = new MainFrame();
		Frame frame = mainFrame.getFrame();
		ChessBoard board = mainFrame.getBoard();
		Message message = mainFrame.getMessage();
		if(button == b0){
			frameRenew.dispose();
		}else if(button == b1){
			board.setGameStatus(false);
			frameRenew.dispose();
			message.dispose();
			frame.dispose();
			mainFrame.startGame();
		}
	}
}

//****	���U�e��	****//
class Help implements ActionListener {
	private Frame frameHelp = new Frame("��  �U");
	private Button b0 = new Button("�T�w");
	private JTextArea text = new JTextArea(
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
		b0.addActionListener(this);
		frameHelp.add(text, BorderLayout.NORTH);
		frameHelp.add(b0, BorderLayout.SOUTH);
		frameHelp.setVisible(true);
		frameHelp.addWindowListener(new CloseWindow());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == b0){
			frameHelp.dispose();
		}
	}
}

//**** ���}�e��	****//
class Exit implements ActionListener {
	private Panel p0 = new Panel();
	private Panel p1 = new Panel();
	private Panel p3 = new Panel();
	private Frame frameExit = new Frame("��  �}");
	private Button b0 = new Button("��  ��");
	private Button b1 = new Button("�T  �w");
	private JTextArea text = new JTextArea("�T�w�n���}?");
	
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
		frameExit.addWindowListener(new CloseWindow());
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

//**** ��Ĺ�e�� ****//
class Win implements ActionListener {
	private MainFrame mainFrame = new MainFrame();
	private ChessBoard board = mainFrame.getBoard();
	private Frame win = new Frame("Win");
	private Button enter = new Button("�T�w");
	private JTextArea textWin = new JTextArea("");
	
	public Win(int color){
		Panel p = new Panel();
		board.setGameStatus(false);
		win.setSize(200, 100);
		win.setResizable(false);
		win.setLocation(300, 300);
		if(color == 1)
			textWin = new JTextArea("�� �� �� !!");
		else if(color == 0)
			textWin = new JTextArea("�� �� �� !!");
		p.add(textWin, BorderLayout.CENTER);
		enter.addActionListener(this);
		win.add(p, BorderLayout.NORTH);
		win.add(enter, BorderLayout.SOUTH);
		win.setVisible(true);
		win.addWindowListener(new CloseWindow());
	}

	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == enter){
			win.dispose();
		}
	}
}

//**** �Ѥl���ʿ��~�e�� ****//
class HasChess implements ActionListener {
	private Frame hasChess = new Frame("");
	private static JTextArea textHasChess = new JTextArea();
	private Button enter = new Button("�T�w");
	
	public HasChess(String s){
		hasChess.setSize(200, 100);
		hasChess.setResizable(false);
		hasChess.setLocation(300, 300);
		enter.addActionListener(this);
		textHasChess.setText("�iĵ�i�j����o��"+ s +"!!");
		hasChess.add(textHasChess, BorderLayout.NORTH);
		hasChess.add(enter, BorderLayout.SOUTH);
		hasChess.setVisible(true);
		hasChess.addWindowListener(new CloseWindow());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == enter){
			hasChess.dispose();
		}
	}
}

class Message implements Observer{
	private Frame frame;
	private JTextPane text;
	private String s = "";
	
	public Message(){
		frame = new Frame();
		ScrollPane JSP = new ScrollPane();
		Panel panel = new Panel();
		text = new JTextPane();
	    frame = new Frame("Message");
		frame.setSize(280, 450);
		frame.setResizable(false);
		frame.setLocation(1000, 10);
		panel.add(text);
		JSP.add(panel);
		frame.add(JSP);
		frame.setVisible(true);
		frame.addWindowListener(new CloseWindow());
	}

	public void dispose(){
		frame.dispose();
	}

	public void update(Observable o, Object arg) {
		this.s = this.s + arg;
		text.setText(this.s);
	}
}


//****	�����ƹ��I��	****//
class MouseClicked extends MouseAdapter {
	private int X;
	private int Y;
	public MouseClicked(){}
		
	public void mousePressed(MouseEvent e){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		ChessAction chessAction = board.getChessAction();
	    if (e.getButton() == MouseEvent.BUTTON1){
	        X = (e.getX()-board.getMargin())/board.getGridSpan();
	        Y = (e.getY()-board.getMargin())/board.getGridSpan();
	        chessAction.check(X, Y);	//�ˬd
	    }
	}
}

//****	�e�e��������		****//
class CloseWindow extends WindowAdapter {
	public void windowClosing(WindowEvent e){
		e.getWindow().dispose();
	}
}

class MainFrameClose extends WindowAdapter {
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
}
