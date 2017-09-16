package robotGame.view;

import robotGame.model.*;
import robotGame.model.Robot;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*this version of view is a slightly modified version of Jack's view code.
 * a number of changes have been made though:
 * 		The view now extends JFrame instead of possessing one
 * 		Ilya's cell finding ideas have been worked into methods here
 * 		The view can now has the method to redraw the board.
 */
public class View extends JFrame
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

		board = new JPanel(new GridLayout(10, 10));
		testBoard = new Board(900, 900, 10);
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

	public void displayInstructions()
	{
		//TODO; write instructions frame
	}
	
		
		// flash the newGame button's background 
	private void flashJButtonColor(JButton button, Color color)
	{
	
	}
		
	// flash the alertBox TextField's background - WINRAR MODE!
	private void flashJTextFieldColor(JTextField textfield)
	{
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
		flashJTextFieldColor(alertBox);
		pause(1000);
		flashJButtonColor(newGame, Color.LIGHT_GRAY);
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

	/*
	 * board drawing method, all this does is redraw the board using a copy of
	 * the board model. Mostly called inside event handlers in controller to update
	 * the view when the model data changes.
	 */
	public void redrawBoard(Cell[][] boardData)
	{
		for (int i = 0; i < 10; i++)
		{
			// System.out.println("You're in the first for loop");
			for (int j = 0; j < 10; j++)
			{
				cell[i][j].setIcon(null);
				// System.out.println("You're in the second for loop");
				if (boardData[i][j].getWalkable() == false)
				{
					// System.out.println("You're in the walkable false");
					cell[i][j].setBackground(Color.darkGray);
					// cell[i][j].repaint();
				} else
				{
					cell[i][j].setBackground(Color.white);
					if (boardData[i][j].hasBot() && boardData[i][j].hasSrc()
							&& boardData[i][j].hasDst())
					{
						cell[i][j].setBackground(Color.BLUE);
					} else if (boardData[i][j].hasBot()
							&& boardData[i][j].hasSrc())
					{
						// bowser has peach
						//cell[i][j].setIcon(bowserAndPeach);
					} else if (boardData[i][j].hasBot())
					{
						cell[i][j].setBackground(Color.CYAN);
						if (boardData[i][j].botHasSrc())
						{
							cell[i][j].setBackground(Color.MAGENTA);
						}

					} else if (boardData[i][j].hasSrc())
					{
						cell[i][j].setBackground(Color.GREEN);
					} else if (boardData[i][j].hasDst())
					{
						cell[i][j].setBackground(Color.ORANGE);
					}

				}
			}
		}
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

	// listener code
	// adding the listener for the board.
	public void addboardListener(ActionListener board_ear)
	{
		for (int y = 0; y < 10; y++)
		{
			for (int x = 0; x < 10; x++)
			{
				cell[y][x].addActionListener(board_ear);
			}
		}
	}

	public void addGboardListener(MouseListener ml)
	{
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
		private int cells;
		
		public Board(int x, int y, int cells)//x, y dimensions for board size and number of cells per column and row.
		{
			this.x = x;
			this.y = y;
			this.cells = cells;
		}
		
		public void paint(Graphics g)
		{
			g.setColor(Color.GRAY);
			g.fillRect(0,0,this.x, this.y);
		}
	}
}
