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
		
		//View test_view = new View();
		//Model test_model = new Model();
		//Controller test_control = new Controller(test_view, test_model);
		
	}

}
