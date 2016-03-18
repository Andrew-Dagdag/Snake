import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//Date started: 1/21/2016
//Time check 10:49 at around 10-15 minutes of coding
//Time check 11:39 wasted around half an hour surfing facebook. Did not regret
//Time check 12:12 midnight, wasted another 20 minutes. Still did not regret. 
//                 Will pause for a while.
//Time check 12:45 resuming rn lemme see how far I could go
//Time check  1:04 and I just finished tiling. I'm now making the tiles for the grid.
//Time check  1:24 and I'm having problems with image transparency in the snake grid.
//                 Will ask Kyne or Donn on making transparent png files
//                 Also will pause until further notice.
//Current Date as of ^ -> 1/22/2016
//Time check  7:27 and I'm gonna try using JPanel for the grid
//Time check  7:39 and the JPanel did become transparent, but I can't select which
//                 parts of the grid will be transparent or not
//				   possible solution: USE TRANSPARENT IMAGES.
//                 Will stop for now.
//Time check  1:44 afternoon, previous problem solved. Current problem is grid
//				   isn't removing the spaces. Keh. 
//Date check 1/25/2016 and time is 5:04am
//Time check  5:19 am and I just finished solving the current problem. Now for KeyListener
//Time check  5:52 am and I'm having problems with the KeyListener. Damnit.
//Time check  6:11 am and I have to get ready to go to Miagao. See ya later Little Snakey Snakey.
//Time check  8:50 am and I just arrived at Miagao. Lemme see if I can do anything about this.
//Time check  9:19 am and I have had enough of this shit. Checking internet for solution to
//				   current problem!!!!
//Time check  9:31 am and I just solved the keyListener shit! Wohoo!! Now idk how to proceed.
//				   because I need to know how to use the time shit from threading. gahd.
//Time check 10:46 am and I'll convert my one dimensional grid array into a 2 dimensional array
//Date check 1/27/2016 and the time is 9:52pm
//				   Current problem rn is the snake movement algorithm.
//Time check  9:54pm and internet is back. Will procrastinate.


/*
Current problem:
	Grid isn't filling up the whole frame. It should but it doesn't. #SOLVED
	How to use timer to keep it going even without user input;A;

*/

public class RecreatingSnake extends Thread{
	//Each tile is 20x20 pixels
	//The whole grid is also 20x20
	//The whole thing will be 400x400pixels
	
	public JButton start, quit;
	public JFrame frame;

	public int[][] grid = new int[20][20];

	public int snakeLength = 3, snakeDirection = 4;
	public int[] pastDirection = new int[400], snakeX = new int[400], snakeY = new int[400];
	
	public boolean start1 = true;
	
	RecreatingSnake(){
		if(start1){ 
			start1 = false;
			Start();
		}
	}
	
	public static void main(String[] args){
		new RecreatingSnake();
	}
	
	public void Start(){
		frame = new JFrame();
		frame.setTitle("Welcome to Andrew's attempt at recreating snake!");
		frame.setSize(410,410); //Width, Height
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//Insert menu screen
		menu();
	}
	
	public void resetFrame(){
		frame.setSize(409,409);
		frame.setSize(410,410);
	}
	
	//Test success, method not removed for further testing of other possible ideas
	public void test(){
		
	}
	
	public void menu(){
		//Setting background
		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JLabel(new ImageIcon("SnakeBG.png")));
		//After setting background, resetting layout to add stuff on top of background
		frame.setLayout(new BorderLayout());
		
		//Adding menu buttons
		start = new JButton("Start");
		quit = new JButton("Quit");
		
		//Adding the listeners, becas u can't add listeners in static methods
		addingMenuListener();
		Box box = new Box(BoxLayout.X_AXIS);
		
		box.add(Box.createVerticalGlue());
		box.add(Box.createHorizontalGlue());
		box.add(start);
		box.add(quit);
		box.add(Box.createVerticalGlue());
		box.add(Box.createHorizontalGlue());
		frame.add(box);
		
		resetFrame();
	}
	
	public class actionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String click = e.getActionCommand();
			if(click == "Start"){
				frame.setTitle("Game on!");
				addingKeyListener();
				gameOn();
			}else if(click == "Quit"){
				//System.exit(0);
				frame.setTitle("I clicked Quit");
			}
		}
	}

	public class keyListener implements KeyListener{
		public void keyPressed(KeyEvent e){
			System.out.println("I got shit to work");
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
				snakeDirection = 1; 
				pastDirection[0] = 1;
			}else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
				snakeDirection = 2;
				pastDirection[0] = 2;
			}else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
				snakeDirection = 3;
				pastDirection[0] = 3;
			}else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
				snakeDirection = 4;
				pastDirection[0] = 4;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e){}
		@Override
		public void keyTyped(KeyEvent e){}
	}

	/*
	Legend for grid shit.
	0 - Do nothing. Basically let the background show
	1 - Snake Head
	2 - Snake Body
	3 - Snake Tail
	
	Movement
	1 - up
	2 - left
	3 - down
	4 - right
	0 - end loop
	*/
	public void addingKeyListener(){
		frame.addKeyListener(new keyListener());
	}
	
	public void addingMenuListener(){
		start.setActionCommand("Start");
		quit.setActionCommand("Quit");
		start.addActionListener(new actionListener());
		quit.addActionListener(new actionListener());
	}
	
	public void setGridValues(){
		
		for(int i = 0; i < 400; i++){
			pastDirection[i] = 0;
		}
		for(int x = 0; x < 20; x++){
			for(int y = 0; y < 20; y++){
				grid[x][y] = 0;
			}
		}
		
 		snakeX[0] = 10;
		snakeY[0] = 5;
		pastDirection[0] = 4;
		grid[snakeX[0]][snakeY[0]] = 1;

		snakeX[1] = 10;
		snakeY[1] = 4;
		pastDirection[1] = 4;
		grid[snakeX[1]][snakeY[1]] = 1;
		
		snakeX[2] = 10;
		snakeY[2] = 3;
		pastDirection[2] = 4;
		grid[snakeX[2]][snakeY[2]] = 1;
		
		snakeX[3] = 10;
		snakeY[3] = 2;
		pastDirection[3] = 4;
		grid[snakeX[3]][snakeY[3]] = 1;
		
		snakeX[4] = 9;
		snakeY[4] = 2;
		pastDirection[4] = 3;
		grid[snakeX[4]][snakeY[4]] = 1;
		
	}
	
	public JLabel tile;
	public void printGrid(){
		frame.getContentPane().removeAll();
		frame.validate();
		frame.repaint();
 		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JLabel(new ImageIcon("SnakeBG.png")));
		
	//	frame.setLayout(new BorderLayout());
	//	frame.add(new JLabel("Press spacebar to pause/play the game"), BorderLayout.SOUTH); 
		
		//addingKeyListener();
		frame.setLayout(new GridLayout(20,20,0,0));
		
		grid[5][5] = 2;
		
		for(int x = 0; x < 20; x++){
			for(int y = 0; y < 20; y++){
				if (grid[x][y] == 0){
					tile = new JLabel();
				}else if (grid[x][y] == 1){
					tile = new JLabel(new ImageIcon("Head.png"));
				}else if (grid[x][y] == 2){
					tile = new JLabel(new ImageIcon("Body.png"));
				}else if (grid[x][y] == 3){
					tile = new JLabel(new ImageIcon("Empty.png"));
				}
				frame.add(tile);
			}
		}
		
		frame.requestFocus();
		resetFrame(); 
		//reiterate();
	}
	
	private class Reiteration implements Runnable{
		Reiteration(){}
		public void run(){
			try{
				while(true){
					Thread.sleep(500);
					setSnakeValues();
					printGrid();
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void setSnakeValues(){
		for(int x = 0; x < 20; x++){
			for(int y = 0; y < 20; y++){
				grid[x][y] = 0;
			}
		} //Clears the grid of everything
		
		int tempX1 = snakeX[0], tempX2 = snakeX[1];
		int tempdirection1 = pastDirection[0], tempdirection2 = pastDirection[1];
		int tempY1 = snakeY[0], tempY2 = snakeY[2];
		boolean trigger = true;
		for(int i = 1; i < 400; i++){ 
			if(pastDirection[i] == 0){
				grid[snakeX[i-1]][snakeY[i-1]] = 3;
				break;
			}else{
				if(trigger){
					tempX2 = snakeX[i];
					tempY2 = snakeY[i];
					tempdirection2 = pastDirection[i];
				
					snakeX[i] = tempX1;
					snakeY[i] = tempY1;
					pastDirection[i] = tempdirection1;
					
					trigger = false;
				}else if(!trigger){
					tempX1 = snakeX[i];
					tempY1 = snakeY[i];
					tempdirection1 = pastDirection[i];
				
					snakeX[i] = tempX2;
					snakeY[i] = tempY2;
					pastDirection[i] = tempdirection2;
					
					trigger = true;
				} 
					grid[snakeX[i]][snakeY[i]] = 2;
			}
		}
		
		if(snakeDirection == 1){
			snakeX[0]--;
		}else if(snakeDirection == 2){
			snakeY[0]--;
		}else if(snakeDirection == 3){
			snakeX[0]++;
		}else if(snakeDirection == 4){
			snakeY[0]++;
		}else{}
		
		grid[snakeX[0]][snakeY[0]] = 1;
	}
	
	public void gameOn(){
		setGridValues();
		Thread reiterate = new Thread(new Reiteration());
		reiterate.start();
		printGrid();
	}
}
