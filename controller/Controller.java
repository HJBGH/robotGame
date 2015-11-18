package controller;

import java.awt.event.*;
import java.util.ArrayList;

import view.*;
import model.*;

public class Controller
{
	private View the_view = new View();
	private Model the_model = new Model();

	private enum Setting
	{
		PATH, BOT, SOURCE, DEST,
	};

	Setting selected;
	private String cmdsStr;
	private Interpreter the_interpreter = new Interpreter();
	private Solver the_solver = new Solver();

	/*
	 * controller constructor, note that the entire program is run from testMain
	 * in the test_main package.
	 */
	public Controller(View the_view, Model the_model)
	{
		this.the_view = the_view;
		this.the_model = the_model;
		this.the_view.setVisible(true);
		this.the_view.addboardListener(new boardListener());
		this.the_view.addButtonListener(new buttonListener());
	}

	/*
	 * This implementation of ActionListener is specific to board. 100 instances
	 * of it are generated when it gets passed to model.addboardListener in the
	 * constructor. There may be a better method. There is a better method, use
	 * one action listener, represent the board with openGL and get co-ordinates
	 * from the action listener All this does is get the cell Co-ordinates
	 * (cheers Ilya) and use the appropriate model method to toggle the walkable
	 * state of that cell. The board is then redrawn.
	 */
	class boardListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent boardEvent)
		{
			int[] clickedCell = the_view.getClickedCell(boardEvent);
			System.out.println(selected);
			if (selected == Setting.PATH)
			{
				try
				{
					the_model.toggleWalkable(clickedCell[0], clickedCell[1]);
				} catch (Exception e)
				{
					e.printStackTrace(System.out);
				}
			}
			if (selected == Setting.BOT)
			{
				if (the_model.hasBot(clickedCell[0], clickedCell[1]) == false)
				{
					try
					{
						the_model.place(clickedCell[0], clickedCell[1],
								new Robot());
					} catch (Exception e)
					{
						e.printStackTrace(System.out);
					}
				} else
				{
					try
					{
						System.out.println("removing rerobot");
						the_model.removeBot(clickedCell[0], clickedCell[1]);
					} catch (Exception e)
					{
						e.printStackTrace(System.out);
					}
				}
			}
			if (selected == Setting.SOURCE)
			{
				if (the_model.hasSrc(clickedCell[0], clickedCell[1]) == false)
				{
					try
					{
						the_model.place(clickedCell[0], clickedCell[1],
								new Source());
					} catch (Exception e)
					{
						e.printStackTrace(System.out);
					}
				} else
				{
					try
					{
						the_model.removeSrc(clickedCell[0], clickedCell[1]);
					} catch (Exception e)
					{
						e.printStackTrace(System.out);
					}
				}
			}
			if (selected == Setting.DEST)
			{
				if (the_model.hasDst(clickedCell[0], clickedCell[1]) == false)
				{
					try
					{
						the_model.place(clickedCell[0], clickedCell[1],
								new Destination());
					} catch (Exception e)
					{
						e.printStackTrace(System.out);
					}
				} else
				{
					try
					{
						the_model.removeDst(clickedCell[0], clickedCell[1]);
					} catch (Exception e)
					{
						e.printStackTrace(System.out);
					}
				}
			}
			the_view.redrawBoard(the_model.getBoard());
		}
	}

	// This listener will listen to all the other buttons.
	class buttonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent buttonEvent)
		{
			String buttonStr = the_view.getClickedButton(buttonEvent);
			switch (buttonStr)
			{
			case "Enter Commands":
				selected = null;
				cmdsStr = the_view.get_command();
				System.out.println(cmdsStr);
				try
				{
					executeCommands(the_interpreter.interpret(cmdsStr));
				} catch (InvalidCommandException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					the_view.set_alert(e.getMessage());
				}

				// TODO: implement interpreter action and appropriate movement
				// methods here.
				break;
			case "Robot":
				selected = Setting.BOT;
				break;
			case "Source":
				selected = Setting.SOURCE;
				break;
			case "Destination":
				selected = Setting.DEST;
				break;
			case "Path":
				selected = Setting.PATH;
				break;
			case "Instructions":
				the_view.displayInstructions();
				break;
			case "New Game":
				selected = null;
				switch (the_view.getNewGameOption())
				{
				case 0:
					System.out
							.println("User clicked OK. Board re-initialising...");

					the_model = new Model();
					the_view.redrawBoard(the_model.getBoard());
					the_view.reenableButtons();
					break;

				case 1:
					System.out.println("JOptionPane closed.");
					break;

				default:
					System.out.println("Default case called of case New Game");
					break;
				}
				break;
			case "Solve": try {
				selected=null;
				executeCommands(the_interpreter.interpret(the_solver.solve(the_model)));
			} catch (InvalidCommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e){
				the_view.set_alert(e.getMessage());
			}
			break;
			default:
				System.out.println("How did you do that?");
				break;
			}
		}

		private void executeCommands(ArrayList<String> commands)
		{
			CommandThread ct = new CommandThread(the_view, the_model, commands);
			new Thread(ct).start();
		}
	}
}
