package robotGame.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.omg.PortableServer.THREAD_POLICY_ID;

import robotGame.view.View;
import robotGame.model.*;
import robotGame.controller.*;

/**
 * The main class for the robotGame program, runs everything.
 *
 */
public class Main {
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		
		//TODO: consider writing my own point / co-ordinate class
		//The view should register itself with the model to recieve updates about events in the model.
		//controller is bound to view to recieve input from the UI.
		//glue components together with observer pattern
		//View test_view = new View();
		//Model test_model = new Model();
		//test_model.addObserver(test_view);
		//Controller test_control = new Controller(test_view, test_model);
		
	}

}
