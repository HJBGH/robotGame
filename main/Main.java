package robotGame.main;

import robotGame.view.View;
import robotGame.model.*;
import robotGame.model.infoBoard.InfoBoard;

import javax.swing.SwingUtilities;

import robotGame.controller.*;

/**
 * The main class for the robotGame program, runs everything.
 *
 */
public class Main 
{
	private static final int CELLS_X = 20;
	private static final int CELLS_Y = 20;
	public static void main(String args[]) throws ModelException{
		
		//Multithreading might be a worthwhile consideration
		//The view should register itself with the model to recieve updates about events in the model.
		//controller is bound to view to recieve input from the UI.
		//glue components together with observer pattern
		
		InfoBoard infoBoard = new InfoBoard();
		Model theModel = new Model(CELLS_X, CELLS_Y, infoBoard);
		final View theView = new View();
		infoBoard.addObserver(theView);
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run()
			{
				theView.setVisible(true);
			}
		});
		Controller theController = new Controller(theModel, CELLS_X, CELLS_Y);
		theView.addBoardMouseListener(theController);
		theView.addButtonListener(theController);
	}

}
