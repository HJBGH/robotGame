package view;

import model.*;
import model.Robot;

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
				cell[i][j].setSize(64, 64); //useless as layout rules has priority
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
		
		instFrame.setBounds(0, 0, 400, 400);
		instText.setMargin(new Insets(10,10,10,10));
		instFrame.setVisible(true);

		instScrollPane.setBackground(Color.white);
		instScrollPane.getVerticalScrollBar().setValue(0);
		
		instText.setLineWrap(true);
		instText.setWrapStyleWord(true);
		instText.setEditable(false);
		
		instText.append("Hello and welcome to Robot Game by Team Ytterbium!\n\n");
		// TODO write instructions:
		instText.append("Aim:\n");
		instText.append("The aim of Robot Game is to simulate a Robot moving a "
				+ "Source to a Destination along a user-specified path.\n\n\n");
		instText.append("Basic Features:\n");
		instText.append("1. You can place and remove path pieces; clicking on "
				+ "board cells to place and clicking again to remove."
				+ " Path pieces will be coloured white while non-path pieces are grey.\n\n");
		instText.append("2. You can specify the locations of the Robot, Source, and Destination objects by "
				+ "clicking on the appropriate button and then clicking on a cell.\n\n\n");
		instText.append("Setting Up The Board:\n");
		instText.append("1. Set up the path: Click the menu option 'Place Path', "
				+ "then click any amount of cells to create a path for the Robot. "
				+ "Please remember to ensure all path pieces (white cells) are connected.\n\n");
		instText.append("2. Set up the objects: Click the corresponding menu option to place the Robot, Source or Destination, "
				+ "followed by clicking a white cell on which you would like to place "
				+ "that object. Do this until one of each object is on the board.\n\n\n");
		instText.append("Plotting Your Moves:\n");
		instText.append("Once the path, Robot, Source and Destination have all been set, "
				+ "you can either; manually enter commands to dictate the Robot's movement or click the 'Solve' button to move the Robot "
				+ "via the shortest path possible.\n\n");
		instText.append("Directions:\n");
		instText.append("Up (North); = 'N' / 'n'\n");
		instText.append("Right (East); = 'E' / 'e'\n");
		instText.append("Down (South); = 'S' / 's'\n");
		instText.append("Left (West); = 'W' / 'w'\n\n\n");
		instText.append("Moving The Robot Manually Via Commands: \n");
		instText.append("1. Enter the number of moves you would like the robot to make, "
				+ "followed by the direction (WITHOUT ANY SPACES), i.e. '4N' will move the Robot 4 cells north (up).\n\n");
		instText.append("2. Provide a space between each command, e.g. '4N 2E 3S'"
				+ " will move the robot 4 cells north, 2 east and 3 south all in one attempt.\n\n");
		instText.append("3. Press the 'Enter Commands' button to run your commands.\n\n\n");
		instText.append("Performing Actions With The Robot: \n");
		instText.append("1. Once the user has entered a command to move the Robot, "
				+ "entering commands; 'pick' or 'drop' will allow the Robot to grab or drop the source respectively.\n\n");
		instText.append("2. Commands 'pick' or 'drop' can be used separately or with movement commands, "
				+ "e.g. '4S 2E pick 1N 2W drop'\n\n\n");
		instText.append("To Note:\n");
		instText.append("Pressing the 'New Game' menu option at anytime will give you the option to restart the game. \n\n");
		instText.append("Good luck and have fun!\n\n");
		instText.append("- Dev Team @ Team Ytterbium :)");
		
		
		instFrame.add(instScrollPane);
	}
	
		
		// flash the newGame button's background 
		private void flashJButtonColor(JButton button, Color color)
		{
			Color origColor = newGame.getBackground();
			button.setBackground(color);
			pause(1000);
			button.setBackground(origColor);
			pause(1000);
			button.setBackground(color);
			pause(1000);
			
			button.setBackground(origColor);
		}
		
		// flash the alertBox TextField's background - WINRAR MODE!
		private void flashJTextFieldColor(JTextField textfield)
		{
			Color origColor = alertBox.getBackground();
			textfield.setBackground(Color.red);
			pause(100);
			textfield.setBackground(Color.green);
			pause(100);
			textfield.setBackground(Color.blue);
			pause(100);
			textfield.setBackground(Color.yellow);
			pause(100);
			textfield.setBackground(Color.red);
			pause(100);
			textfield.setBackground(Color.green);
			pause(100);
			textfield.setBackground(Color.blue);
			pause(100);
			textfield.setBackground(Color.yellow);
			pause(100);
			
			textfield.setBackground(origColor);
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
