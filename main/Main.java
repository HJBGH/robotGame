package robotGame.main;

import robotGame.view.View;
import robotGame.model.*;
import robotGame.model.infoBoard.InfoBoard;
import robotGame.controller.*;

/**
 * The main class for the robotGame program, runs everything.
 *
 */
public class Main {
	public static void main(String args[]) throws ModelException{
		
		//TODO: consider writing my own point / co-ordinate class
		//The view should register itself with the model to recieve updates about events in the model.
		//controller is bound to view to recieve input from the UI.
		//glue components together with observer pattern
		InfoBoard infoBoard = new InfoBoard();
		Model theModel = new Model(20, 20, infoBoard);
		View theView = new View();
		infoBoard.addObserver(theView);
		theView.setVisible(true);
		Controller theController = new Controller(theModel);
		theView.addBoardMouseListener(theController);
	}

}
