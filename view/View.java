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
public class View extends JFrame
{
	private JPanel gui = new JPanel(new BorderLayout(1, 3));
	private JPanel board = new JPanel();
	private JButton cell[][] = new JButton[10][10];

	private ImageIcon bowser = new ImageIcon("resources/bowser.png");
	private ImageIcon peach = new ImageIcon("resources/peach.png");
	private ImageIcon castle = new ImageIcon("resources/castle.png");
	private ImageIcon bowserHasPeach = new ImageIcon(
			"resources/bowserHasPeach.png");
	private ImageIcon bowserAndPeach = new ImageIcon(
			"resources/bowserPeach.png");
	private ImageIcon finished = new ImageIcon("resources/finished.png");

	private JPanel menu = new JPanel();
	private JButton newGame = new JButton("New Game");
	private JButton instructions = new JButton("Instructions");
	private JButton solve = new JButton("Solve");
	private JLabel pathLabel = new JLabel("Path: ");
	private JButton path = new JButton("Place Path");
	private JLabel objectsLabel = new JLabel("Objects: ");
	private JButton robot = new JButton("Place Robot");
	private JButton source = new JButton("Place Source");
	private JButton destination = new JButton("Place Destination");

	private JPanel inputPanel = new JPanel();

	private JPanel alertSection = new JPanel();
	private JLabel alertLabel = new JLabel("Command Status: ");
	private JTextArea alertBox = new JTextArea(2, 20);

	private JPanel commandSection = new JPanel();
	private JLabel commandLineLabel = new JLabel("Enter commands here: ");
	private JTextArea commandLine = new JTextArea(2, 20);
	private JButton commandEnter = new JButton("Enter Commands");

	private JButton[] buttonArray = { newGame, instructions, solve, path,
			robot, source, destination, commandEnter };

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
		/*
		 * this.setVisible(true); setting visible in the constructor was causing
		 * weird problems where the window would appear twice but any changes
		 * made to the data would only appear once.
		 */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Robot Game by Team Ytterbium");
		this.add(gui);

		/* Resizing Icons for board */
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
		menu.add(solve);
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
				cell[i][j].setSize(64, 64);
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
		JFrame instFrame = new JFrame("Instructions for Robot Game");
		JTextArea instText = new JTextArea(instFrame.getWidth(), 40);
		JScrollPane instScrollPane = new JScrollPane(instText);

		instScrollPane.getVerticalScrollBar().setValue(0);
		instFrame.setBounds(0, 0, 400, 400);
		instFrame.setVisible(true);

		instScrollPane.setBackground(Color.white);

		instText.setEditable(false);
		instText.append("Hello and welcome to Robot Simulation by Team Ytterbium!\n\n");
		// TODO write instructions:
		instText.append("Aim:\n");
		instText.append("The aim of Robot Simulation is to simulate a Robot moving a "
				+ "Source to a Destination along a user-specified path.\n\n");
		instText.append("Basic Features:\n");
		instText.append("1. The User can place and remove path pieces; clicking on "
				+ "board cells to place and clicking again to remove."
				+ " Path pieces will be coloured white while non-path pieces are grey.\n");
		instText.append("2. The User can specify the locations of the Robot, Source, and Destination Objects by "
				+ "clicking on the appropriate button and then clicking on a cell.\n\n");
		instText.append("How to set up the board: \n\n:");
		instText.append("1. Set up the path: Click the menu option 'Set Path', "
				+ "then click cells which you would like to be a path for the robot. "
				+ "Please remember to ensure all 'path' or white cells are connected.\n\n");
		instText.append("2. Set up the Objects: Click each menu item for Place Robot, Source and Destination, "
				+ "followed by clicking a white cell on which you would like to place "
				+ "each object consecutively. \n\n");
		instText.append("How to plot your own moves:\n\n");
		instText.append("Once the path, robot, source and destination have been set, "
				+ "the user can enter commands to move the robot manually.\n\n");
		instText.append("Directions (NOT case sensative): \n\n");
		instText.append("Down (South); = 'S'\n");
		instText.append("Up (North); = 'N'\n");
		instText.append("Right (East); = 'E'\n");
		instText.append("Left (West); = 'W'\n\n");
		instText.append("Moving the robot: \n\n");
		instText.append("1. Enter the amount of moves you would like the robot to make, "
				+ "followed by the direction (WITHOUT any spaces), i.e. '4N' will move the robot 4 cells north (up).\n\n");
		instText.append("2. Provide a space between commands e.g. '4N 2E 3S'"
				+ "will move the robot 4 cells up, 2 right and 3 down all in one attempt.\n\n");
		instText.append("3. Press the 'Enter Commands' button to implement your moves on the board.\n\n");
		instText.append("Perform actions with Robot: \n\n");
		instText.append("1. Once the user has entered command to move the robot, "
				+ "entering 'pick' or 'drop' will allow the robot to grab or drop the source \n\n");
		instText.append("2. 'pick' or 'drop' can be used with direction commands or seperately "
				+ "e.g. '4S 2E pick 1N 2W drop' is OK \n\n");
		instText.append("Pressing New Game at anytime will give the user the option to restart the game. ");

		instText.setLineWrap(true);
		instText.setWrapStyleWord(true);

		instFrame.add(instScrollPane);
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
	 * the board. Mostly called inside event handlers in controller to update
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
					// System.out.println("You're in the else");

					/*
					 * The following code sets the
					 */
					cell[i][j].setBackground(Color.white);
					if (boardData[i][j].hasBot() && boardData[i][j].hasSrc()
							&& boardData[i][j].hasDst())
					{
						cell[i][j].setIcon(finished);
					} else if (boardData[i][j].hasBot()
							&& boardData[i][j].hasSrc())
					{
						// bowser has peach
						cell[i][j].setIcon(bowserAndPeach);
					} else if (boardData[i][j].hasBot())
					{
						cell[i][j].setIcon(bowser);
						if (boardData[i][j].botHasSrc())
						{
							cell[i][j].setIcon(bowserHasPeach);
						}

					} else if (boardData[i][j].hasSrc())
					{
						cell[i][j].setIcon(peach);
					} else if (boardData[i][j].hasDst())
					{
						cell[i][j].setIcon(castle);
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

}
