package controller;

import java.awt.event.*;
import java.util.ArrayList;

import view.*;
import model.*;
import Interpreter.*;
public class Controller {
	private View the_view = new View();
	private Model the_model = new Model();
	private enum Setting{
		PATH,
		BOT,
		SOURCE,
		DEST,
	};
	Setting selected;
	private String cmdsStr;
	private Interpreter jim = new Interpreter();
	/*controller constructor, note that the entire program is run
	 * from testMain in the test_main package. 
	 */
	public Controller(View the_view, Model the_model){
		this.the_view = the_view;
		this.the_model = the_model;
		this.the_view.setVisible(true);
		this.the_view.addboardListener(new boardListener());
		this.the_view.addButtonListener(new buttonListener());
	}
	/* just ignore the testMethod()
	public void testMethod(){
		//this method is used for testing random shit.
		try{
			the_model.toggleWalkable(3, 5);
		}
		catch(Exception e){
			System.out.println("exception occured");
		}
		this.the_view.redrawBoard(the_model.getBoard());
		try{
			the_model.toggleWalkable(7, 8);
		}
		catch(Exception e){
			System.out.println("exception occurred");
		}
		this.the_view.redrawBoard(the_model.getBoard());
	}*/
	
	/*This implementation of ActionListener is specific to board.
	 * 100 instances of it are generated when it gets passed to 
	 * model.addboardListener in the constructor. There may be a 
	 * better method.
	 * All this does is get the cell Co-ordinates (cheers Ilya) 
	 * and use the appropriate model method to toggle the walkable
	 * state of that cell. The board is then redrawn.
	 */
	class boardListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent boardEvent) {
			int[] clickedCell = the_view.getClickedCell(boardEvent);
			System.out.println(selected);
			if(selected == Setting.PATH){
				try{
					the_model.toggleWalkable(clickedCell[0], clickedCell[1]);
				}
				catch(Exception ohFuck){
					System.out.println("shit broke");
					ohFuck.printStackTrace(System.out);
				}
			}
			if(selected == Setting.BOT){
				if(the_model.hasBot(clickedCell[0], clickedCell[1])==false){
					try{
						the_model.place(clickedCell[0], clickedCell[1], new Robot());
					}
					catch(Exception ohFuck){
						System.out.println("shit broke");
						ohFuck.printStackTrace(System.out);
					}
				}
				else{
					try{
						System.out.println("removing rerobot");
						the_model.removeBot(clickedCell[0], clickedCell[1]);
					}
					catch(Exception godDamnit){
						System.out.println("broke");
					}
				}
			}
			if(selected == Setting.SOURCE){
				if(the_model.hasSrc(clickedCell[0], clickedCell[1])==false){
					try{
						the_model.place(clickedCell[0], clickedCell[1], new Source());
					}
					catch(Exception ohFuck){
						System.out.println("shit broke");
						ohFuck.printStackTrace(System.out);
					}
				}
				else{
					try{
						the_model.removeSrc(clickedCell[0], clickedCell[1]);
					}
					catch(Exception godDamnit){
						System.out.println("broke");
					}
				}
			}
			if(selected == Setting.DEST){
				if(the_model.hasDst(clickedCell[0], clickedCell[1])==false){
					try{
						the_model.place(clickedCell[0], clickedCell[1], new Destination());
					}
					catch(Exception ohFuck){
						System.out.println("shit broke");
						ohFuck.printStackTrace(System.out);
					}
				}
				else{
					try{
						the_model.removeDst(clickedCell[0], clickedCell[1]);
					}
					catch(Exception godDamnit){
						System.out.println("broke");
					}
				}
			}
			the_view.redrawBoard(the_model.getBoard());
		}
	}
	//This listener will listen to all the other buttons.
	class buttonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent buttonEvent){
			String buttonStr = the_view.getClickedButton(buttonEvent);
			switch(buttonStr){
			case "Enter Commands":
				cmdsStr = the_view.get_command();
				System.out.println(cmdsStr);
				try {
					executeCommands(jim.interpret(cmdsStr));
				} catch (InvalidCommandException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					the_view.set_alert(e.getMessage());
				}
				
				//TODO: implement interpreter action and appropriate movement methods here.
				break;
			case "Place Robot":
				selected = Setting.BOT;
				break;
			case "Place Source":
				selected = Setting.SOURCE;
				break;
			case "Place Destination":
				selected = Setting.DEST;
				break;
			case "Place Path":
				selected = Setting.PATH;
				break;
			case "Instructions":
				//TODO: implement instruction pop-up thingy
				break;
			case "New Game":
				//Genesis flood option, wipes clean the board.
				the_model = new Model();
				the_view.redrawBoard(the_model.getBoard());
				break;
			default:
				System.out.println("How did you do that?");
				break;
			}
		}

		private void executeCommands(ArrayList<String> commands)
		{
			CommandThread ct = new CommandThread(the_view,the_model,commands);
			new Thread(ct).start();
		}
	}
}
