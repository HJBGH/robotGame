package robotGame.view;

import robotGame.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Observable;
import java.util.Observer;
/*
 * View for the robot game
 */
public class View extends JFrame implements Observer 
{
	private JPanel gui = new JPanel(new BorderLayout(1, 3));
	private JPanel board = new JPanel();
	private JButton cell[][] = new JButton[10][10];

	private JPanel menu = new JPanel();
	private JButton newGame = new JButton("New Game");
	private JButton instructions = new JButton("Instructions");
	private JButton solve = new JButton("Solve");
	private JLabel pathLabel = new JLabel("Path: ");
	private JButton path = new JButton("Path");
	private JLabel objectsLabel = new JLabel("Objects: ");
	private JButton robot = new JButton("Robot");
	private JButton source = new JButton("Source");
	private JButton destination = new JButton("Destination");

	private JPanel inputPanel = new JPanel();

	private JPanel alertSection = new JPanel();
	private JLabel alertLabel = new JLabel("Command Status: ");
	private JTextField alertBox = new JTextField();

	private JPanel commandSection = new JPanel();
	private JLabel commandLineLabel = new JLabel("Enter commands here: ");
	private JTextField commandLine = new JTextField();
	private JButton commandEnter = new JButton("Enter Commands");

	private JButton[] buttonArray = { newGame, instructions, solve, path,
			robot, source, destination, commandEnter };
	
	//at the end of the day the Board class functions as the actual view.
	private Board testBoard;

	public View()
	{
		/*
		 * When adding elements to JPanels/JFrames, add them first, then set
		 * their size, margin, etc.
		 */

		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// used if you want to make frame fullscreen - need to import dimension
		// first
		this.setBounds(0, 0, 900, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Robot Game");
		this.add(gui);

		/* Resizing Icons for board */
		
		menu = new JPanel(new FlowLayout());
		menu.add(newGame);
		menu.add(instructions);
		menu.add(solve);
		menu.add(pathLabel);
		menu.add(path);
		menu.add(objectsLabel);
		menu.add(robot);
		menu.add(source);
		menu.add(destination);
		gui.add(menu, BorderLayout.NORTH);

		testBoard = new Board(900, 900, 10, 10);
		//gui.add(board, BorderLayout.CENTER);
		gui.add(testBoard);

		commandSection = new JPanel(new BorderLayout(2, 0));
		commandSection.add(commandLineLabel, BorderLayout.WEST);
		commandSection.add(commandLine, BorderLayout.CENTER);
		commandSection.add(commandEnter, BorderLayout.EAST);
		commandLine.setText("");

		alertSection = new JPanel(new BorderLayout(2, 0));
		alertSection.add(alertLabel, BorderLayout.WEST);
		alertSection.add(alertBox, BorderLayout.CENTER);
		alertBox.setEditable(false);
		alertBox.setText("");

		inputPanel = new JPanel(new BorderLayout(0, 2));
		inputPanel.add(commandSection, BorderLayout.NORTH);
		inputPanel.add(alertSection, BorderLayout.SOUTH);
		gui.add(inputPanel, BorderLayout.SOUTH);

		// Draw new board
		initialiseBoard();
	}

	/* Method to initialise the board (draws clean game board) */
	public void initialiseBoard()
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				cell[i][j] = new JButton();
				board.add(cell[i][j]);
				Insets buttonMargin = new Insets(0, 0, 0, 0);
				cell[i][j].setMargin(buttonMargin);
				cell[i][j].setBackground(Color.darkGray);
			}
		}
	}

	public int getNewGameOption()
	{
		Object options[] = { "OK", "Cancel" };

		int selection = JOptionPane
				.showOptionDialog(
						gui,
						"Are you sure you want to create a new game? Game will be lost if you continue.",
						"Warning - Create New Game",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);

		for (int i = 0; i < options.length; i++)
		{
			if (i == selection)
			{
				return i;
			}
		}

		return JOptionPane.NO_OPTION;
	}
		
	private void pause(int time) 
	{
		try 
		{
			Thread.sleep(time);
		} catch (InterruptedException ie) 
		{
			// do nothing
		}
	}
	
	
	public void disableButtons()
	{
		solve.setEnabled(false);
		path.setEnabled(false);
		robot.setEnabled(false);
		source.setEnabled(false);
		destination.setEnabled(false);
		commandEnter.setEnabled(false);
		commandLine.setText("");
		commandLine.setEditable(false);
	}

	public void reenableButtons()
	{
		solve.setEnabled(true);
		path.setEnabled(true);
		robot.setEnabled(true);
		source.setEnabled(true);
		destination.setEnabled(true);
		commandEnter.setEnabled(true);
		alertBox.setText("");
		commandLine.setText("");
	}

	private Image resizeIcons(ImageIcon icon)
	{
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(50, 50,
				java.awt.Image.SCALE_SMOOTH);
		return newImg;
	}

	// getters and setters
	public String get_command()
	{
		return commandLine.getText();
	}

	public void set_alert(String str)
	{
		alertBox.setText(str);
	}

	public void addBoardMouseListener(MouseListener ml)
	{
		//used to tie the board JPanel to the controller
		this.testBoard.addMouseListener(ml);
	}
	
	// adding listeners for the regular buttons.
	public void addButtonListener(ActionListener general_ear)
	{
		for (int i = 0; i < buttonArray.length; i++)
		{
			buttonArray[i].addActionListener(general_ear);
		}
	}

	// getting the clicked cell
	public int[] getClickedCell(ActionEvent clickEvent)
	{
		//TODO; Fix this ham fisted attempt at event handling. jesus christ this is awful
		for (int y = 0; y < 10; y++)
		{
			for (int x = 0; x < 10; x++)
			{
				if (cell[y][x] == clickEvent.getSource())
				{
					int[] a = { x, y };
					return a;
				}
			}
		}
		int[] a = { -1, -1 };
		return a;
	}

	// getting the clicked button
	public String getClickedButton(ActionEvent buttonEvent)
	{
		Object source = buttonEvent.getSource();
		for (int i = 0; i < buttonArray.length; i++)
		{
			if (buttonArray[i] == source)
			{
				return buttonArray[i].getText();
			}
		}
		return ("Unknown button clicked.");
	}
	
	private class Board extends JPanel
	{
		//todo
		private int x;
		private int y;
		private int cellsX;
		private int cellsY;
		
		public Board(int x, int y, int cellsX, int cellsY)//x, y dimensions for board size and number of cells per column and row.
		{
			this.x = x;
			this.y = y;
			this.cellsX = cellsX;
			this.cellsY = cellsY;
		}
		
		public void paint(Graphics g)
		{
			g.setColor(Color.BLUE);
			g.fillRect(0,0,this.x, this.y);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub, method for updating the board representation
		
	}
}
