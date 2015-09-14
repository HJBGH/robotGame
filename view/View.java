package view;

import model.*;
import model.Robot;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*this version of view is a slightly modified ripoff of Jack's view code.
 * a number of changes have been made though:
 * 		The view now extends JFrame instead of possessing one
 * 		Ilya's cell finding ideas have been worked into methods here
 * 		The view can now has the method to redraw the board.
 */
public class View extends JFrame{

	private JPanel gui = new JPanel(new BorderLayout(1, 3));
	private JPanel board = new JPanel();
	private JButton cell[][] = new JButton[10][10];
	
	private ImageIcon bowser = new ImageIcon("resources/bowser.png");
	private ImageIcon peach = new ImageIcon("resources/peach.png");
	private ImageIcon castle = new ImageIcon("resources/castle.png");
	private ImageIcon bowserHasPeach = new ImageIcon("resources/bowserHasPeach.png");
	private ImageIcon bowserAndPeach = new ImageIcon("resources/bowserPeach.png"); //TO:DO make proper image for this
	private ImageIcon finished = new ImageIcon("resources/finished.png"); //TO:DO make proper image for this
	
	private JPanel menu = new JPanel();
	private JButton newGame = new JButton("New Game");
	private JLabel pathLabel = new JLabel("Path: ");
	private JButton path = new JButton("Place Path");
	private JLabel objectsLabel = new JLabel("Objects: ");
	private JButton robot = new JButton("Place Robot");
	private JButton source = new JButton("Place Source");
	private JButton destination = new JButton("Place Destination");
	private JButton instructions = new JButton("Instructions");
	
	private JPanel inputPanel = new JPanel();
	
	private JPanel alertSection = new JPanel();
	private JLabel alertLabel = new JLabel("Command Status: ");
	private JTextArea alertBox = new JTextArea(2, 20);
	
	private JPanel commandSection = new JPanel();
	private JLabel commandLineLabel = new JLabel("Enter commands here: ");
	private JTextArea commandLine = new JTextArea(2, 20);
	private JButton commandEnter = new JButton("Enter Commands");

	private JButton[] buttonArray = {newGame, path, robot, source, destination, instructions, commandEnter};
	
	public View()
	{
		/* When adding elements to JPanels/JFrames, add them first, then set their size, margin, etc. */
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		// used if you want to make frame fullscreen - need to import dimension first
		this.setBounds(0, 0, 800, 800);
		/*this.setVisible(true); setting visible in the constructor
		 * was causing weird problems where the window would appear twice
		 * but any changes made to the data would only appear once.
		 */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Robot Game by Team Ytterbium");
		this.add(gui);
		
		/*Resizing Icons for board*/
		Image newImg = resizeIcons(bowser);
		bowser = new ImageIcon(newImg);
		
		newImg = resizeIcons(peach);
		peach = new ImageIcon(newImg);
		
		newImg = resizeIcons(castle);
		castle = new ImageIcon(newImg);
		
		newImg = resizeIcons(bowserHasPeach);
		bowserHasPeach = new ImageIcon(newImg);
		
		newImg = resizeIcons(bowserAndPeach);
		bowserAndPeach = new ImageIcon(newImg);
		
		newImg = resizeIcons(finished);
		finished = new ImageIcon(newImg);
		
		
		
		menu = new JPanel(new FlowLayout());
		menu.add(newGame);
		menu.add(instructions);
		menu.add(pathLabel);
		menu.add(path);
		menu.add(objectsLabel);
		menu.add(robot);
		menu.add(source);
		menu.add(destination);
		gui.add(menu, BorderLayout.NORTH);
		
		
		board = new JPanel(new GridLayout(10, 10));
		gui.add(board, BorderLayout.CENTER);
		
		
		commandSection = new JPanel(new BorderLayout(2, 0));
		commandSection.add(commandLineLabel, BorderLayout.WEST);
		commandSection.add(commandLine, BorderLayout.CENTER);
		commandSection.add(commandEnter, BorderLayout.EAST);
		commandLine.setLineWrap(true);
		
		alertSection = new JPanel(new BorderLayout(2, 0));
		alertSection.add(alertLabel, BorderLayout.WEST);
		alertSection.add(alertBox, BorderLayout.CENTER);
		alertBox.setEditable(false);
		
		inputPanel = new JPanel(new BorderLayout(0, 2));
		inputPanel.add(commandSection, BorderLayout.NORTH);
		inputPanel.add(alertSection, BorderLayout.SOUTH);
		gui.add(inputPanel, BorderLayout.SOUTH);
		
		
		
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				cell[i][j] = new JButton();
				board.add(cell[i][j]);
				cell[i][j].setSize(64, 64);
				Insets buttonMargin = new Insets(0,0,0,0);
                cell[i][j].setMargin(buttonMargin);
				cell[i][j].setBackground(Color.darkGray);
				cell[i][j].setOpaque(true);
			}
		}
	}
	
	
	private Image resizeIcons(ImageIcon icon)
	{
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		return newImg;
	}
	/*board drawing method, all this does is redraw the board
	 * using a copy of the board. Mostly called inside event handlers
	 * in controller to update the view when the model data changes.
	 */
	public void redrawBoard(Cell[][] boardData){
		for(int i = 0; i < 10; i++)
		{
			//System.out.println("You're in the first for loop");
			for(int j = 0; j < 10; j++)
			{
				cell[i][j].setIcon(null);
				//System.out.println("You're in the second for loop");
				if(boardData[i][j].getWalkable()==false){
					//System.out.println("You're in the walkable false");
					cell[i][j].setBackground(Color.darkGray);
					//cell[i][j].repaint();
				}
				else{
					//System.out.println("You're in the else");
					
					/* The following code sets the 
					 * 
					 */
					cell[i][j].setBackground(Color.white);
					if(boardData[i][j].hasBot() && boardData[i][j].hasSrc() && boardData[i][j].hasDst())
					{
						cell[i][j].setIcon(finished);
					}
					else if(boardData[i][j].hasBot() && boardData[i][j].hasSrc())
					{
						//bowser has peach
						cell[i][j].setIcon(bowserAndPeach);
					}
					else if(boardData[i][j].hasBot())
					{
						cell[i][j].setIcon(bowser);
						/*try
						{
							/*Robot temp = boardData[i][j].removeBot();
							if(temp.source_held)
							{
								cell[i][j].setIcon(bowserHasPeach);
							}
							else
							{
								cell[i][j].setIcon(bowser);
							}
						}
						catch (CellException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
					}
					else if(boardData[i][j].hasSrc())
					{
						cell[i][j].setIcon(peach);
					}
					else if(boardData[i][j].hasDst())
					{
						cell[i][j].setIcon(castle);
					}
					
				}
			}
		}
	}
	//getters and setters
		public String get_command(){
			return commandLine.getText();
		}
		public void set_alert(String str){
			alertBox.setText(str);
		}
		//listener malarky
		//adding the listener for the board.
		public void addboardListener(ActionListener board_ear){
			for (int y = 0; y < 10; y++){
				for (int x = 0; x < 10; x++){
					cell[y][x].addActionListener(board_ear);
				}
			}
		}
		//adding listeners for the regular buttons.
		public void addButtonListener(ActionListener general_ear){
			for(int i=0; i<buttonArray.length; i++){
				buttonArray[i].addActionListener(general_ear);
			}
		}
		//getting the clicked cell
		public int[] getClickedCell(ActionEvent clickEvent){
			for (int y = 0; y < 10; y++){
				for (int x = 0; x < 10; x++){
					if(cell[y][x] == clickEvent.getSource()){
						int[] a = {x, y};
						return a;
					}
				}
			}
			int[] a = {-1,-1};
			return a;
		}
		//getting the clicked button
		public String getClickedButton(ActionEvent buttonEvent){
			Object source = buttonEvent.getSource();
			for(int i=0; i<buttonArray.length; i++){
				if(buttonArray[i]== source){
					return buttonArray[i].getText();
				}
			}
			return ("pbtpbtpbtpbtpbt");
		}
	
}
