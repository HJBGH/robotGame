package robotGame.view;

import robotGame.model.*;
import robotGame.model.infoBoard.InfoBoard;
import robotGame.view.animation.AnimationNode;
import robotGame.view.animation.XanimationNode;
import robotGame.view.animation.YanimationNode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Observable;
import java.util.Observer;
/*
 * View for the robot game, this entire class is spaghetti code.
 */
public class View extends JFrame implements Observer 
{
	/*
	 * stupid globals for bad code. How do I make the size of the board dynamic?
	 * TODO: Add support for window resizing
	 */
	private static final int BOARD_HEIGHT = 505;
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
	private JButton hero = new JButton("Hero");
	private JButton prize = new JButton("Prize");
	private JButton destination = new JButton("Destination");

	private JPanel inputPanel = new JPanel();

	private JPanel alertSection = new JPanel();
	private JLabel alertLabel = new JLabel("Command Status: ");
	private JTextField alertBox = new JTextField();

	private JPanel commandSection = new JPanel();
	private JLabel commandLineLabel = new JLabel("Enter commands here: ");
	private JTextField commandLine = new JTextField();
	private JButton commandEnter = new JButton("Enter Commands");
	
	//at the end of the day the Board class functions as the actual view.
	private Board testBoard;
	private InfoBoard ib;

	private JButton[] buttons = {newGame, commandEnter, destination, path, hero, prize, solve};
	public View()
	{
		/*
		 * When adding elements to JPanels/JFrames, add them first, then set
		 * their size, margin, etc. This needs a parameter for the number of cells
		 * I should probably re-think it.
		 */
		/*
		 * Set action commands, used to figure out which buttons do what.
		 */
		this.newGame.setActionCommand("NG");
		this.commandEnter.setActionCommand("ENTER");
		this.destination.setActionCommand("DEST");
		this.path.setActionCommand("NODE");
		this.hero.setActionCommand("HERO");
		this.prize.setActionCommand("PRIZE");
		this.solve.setActionCommand("SOLVE");
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// used if you want to make frame fullscreen - need to import dimension
		// first
		this.setSize(new Dimension(1000, 700));
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
		menu.add(hero);
		menu.add(prize);
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
		//this.pack();
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
		hero.setEnabled(false);
		prize.setEnabled(false);
		destination.setEnabled(false);
		commandEnter.setEnabled(false);
		commandLine.setText("");
		commandLine.setEditable(false);
	}

	public void reenableButtons()
	{
		solve.setEnabled(true);
		path.setEnabled(true);
		hero.setEnabled(true);
		prize.setEnabled(true);
		destination.setEnabled(true);
		commandEnter.setEnabled(true);
		alertBox.setText("");
		commandLine.setText("");
	}

	/*
	private Image resizeIcons(ImageIcon icon)
	{
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(50, 50,
				java.awt.Image.SCALE_SMOOTH);
		return newImg;
	}*/

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
		for(JButton button : buttons)
		{
			button.addActionListener(general_ear);//idk why this is called general_ear
		}
	}
	
	@SuppressWarnings("serial")
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
			System.out.println("repainting");
			g.setColor(Color.GRAY);
			g.fillRect(0,0,(this.getWidth()/cellsX)*this.cellsX, (this.getHeight()/cellsY)*this.cellsY);
			if(View.this.ib == null)
				return;
			for(Point point : View.this.ib.nodePoints)
			{
				g.setColor(Color.DARK_GRAY);
				g.fillRect((this.getWidth()/cellsX) * point.x, (this.getHeight()/cellsY) * point.y,
						this.getWidth()/cellsX, this.getHeight()/cellsY);
			}
			for(Point dest : View.this.ib.destPoints)
			{
				g.setColor(Color.GREEN);
				g.fillRect((this.getWidth()/cellsX) * dest.x, (this.getHeight()/cellsY) * dest.y,
						this.getWidth()/cellsX, this.getHeight()/cellsY);
			}
			for(Point pp : View.this.ib.prizePoints) //pp for prize point
			{
				g.setColor(Color.YELLOW);
				g.fillOval((this.getWidth()/cellsX) * pp.x,
						(this.getHeight()/cellsY) * pp.y,
						(this.getWidth()/cellsX)/2, (this.getHeight()/cellsY)/2);
			}
			if(View.this.ib.heroPoint != null)
			{
				g.setColor(Color.BLUE);
				g.fillOval((this.getWidth()/cellsX) * View.this.ib.heroPoint.x,
						(this.getHeight()/cellsY) * View.this.ib.heroPoint.y,
						this.getWidth()/cellsX, this.getHeight()/cellsY);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		//method for updating the board representation
		System.out.println("update called");
		if(this.ib == null)
			this.ib = (InfoBoard)o;
		//for some reason this gets deferred
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			
			public void run()
			{
				repaint();//I'm not sure if I should be calling this directly.
			}
		});
		if(this.ib.getSolved())
		{
			System.out.println("A problem was solved! playing animation");
			playAnimation();//play the animation according the Animation chain
		}
	}
	
	//this will get called in the update method, as the infoboard will be updated once the animation chain 
	//is finished constructing
	private void playAnimation()
	{
		/*For each link in the chain, move the representation of the solver by a calculated
		* amount */
		int positionX = this.ib.heroPoint.x;
		int positionY = this.ib.heroPoint.y;
		//float change = 0.0f;
		AnimationNode animChain = this.ib.AF.getAnimationChain();
		//Timer timer = new Timer(33, this);
		while(animChain != null)
		{
			if(animChain.getClass() == XanimationNode.class)
			{
				System.out.println("moving in x");
			}
			else if(animChain.getClass() == YanimationNode.class)
			{
				System.out.println("moving in y");
			}
			animChain = animChain.getNext();
		}
		System.out.println("finished animating");
	}
}
