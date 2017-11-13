package robotGame.view;

import robotGame.model.*;
import robotGame.model.infoBoard.InfoBoard;

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
	/*
	 * stupid globals for bad code. How do I make the size of the board dynamic?
	 * TODO: Add support for window resizing
	 */
	private static final int BOARD_HEIGHT = 500;
	private static final int BOARD_WIDTH = 650;
	private static final int CELLS_X = 20;
	private static final int CELLS_Y = 20;
	
	private JPanel gui = new JPanel(new BorderLayout(1, 3));

	private JPanel menu = new JPanel();
	private JButton newGame = new JButton("New Game");
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

	private JButton[] buttonArray = { newGame, solve, path,
			robot, source, destination, commandEnter };
	
	//at the end of the day the Board class functions as the actual view.
	private Board testBoard;
	private InfoBoard ib;

	public View()
	{
		/*
		 * When adding elements to JPanels/JFrames, add them first, then set
		 * their size, margin, etc. This needs a parameter for the number of cells
		 * I should probably re-think it.
		 */

		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// used if you want to make frame fullscreen - need to import dimension
		// first
		this.setBounds(0, 0, 700, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Robot Game");
		this.add(gui);
		this.setBackground(Color.CYAN);//debugging
		/* Resizing Icons for board */
		
		menu = new JPanel(new FlowLayout());
		menu.add(newGame);
		menu.add(solve);
		menu.add(pathLabel);
		menu.add(path);
		menu.add(objectsLabel);
		menu.add(robot);
		menu.add(source);
		menu.add(destination);
		gui.add(menu, BorderLayout.NORTH);

		testBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT, CELLS_X, CELLS_Y); //CHANGE THE 20, NO MAGIC NUMBERS
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
		private int cellsX;
		private int cellsY;
		
		public Board(int x, int y, int cellsX, int cellsY)//x, y dimensions for board size and number of cells per column and row.
		{
			this.cellsX = cellsX;
			this.cellsY = cellsY;
			//this.setSize(x, y); setsize is futile here, it gets overridden by the layout manager.
			this.setMaximumSize(new Dimension(x, y));
			this.setBackground(Color.PINK);
		}
		
		public void paint(Graphics g)
		{
			g.setColor(Color.GRAY);
			g.fillRect(0,0,(this.getWidth()/cellsX)*this.cellsX, (this.getHeight()/cellsY)*this.cellsY);
			if(View.this.ib == null)
				return;
			for(Point point : View.this.ib.nodePoints)
			{
				g.setColor(Color.WHITE);
				g.fillRect((this.getWidth()/cellsX) * point.x, (this.getHeight()/cellsY) * point.y,
						this.getWidth()/cellsX, this.getHeight()/cellsY);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		//method for updating the board representation
		System.out.println("update called");
		this.ib = (InfoBoard)o;
		testBoard.repaint();//I'm not sure if I should be calling this directly.
	}
}
