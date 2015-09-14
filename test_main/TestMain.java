package test_main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.omg.PortableServer.THREAD_POLICY_ID;

import view.*;
import model.*;
import controller.*;
//TODO: 

public class TestMain {
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		View test_view = new View();
		Model test_model = new Model();
		Controller test_control = new Controller(test_view, test_model);
		//test_control.testMethod();
		
	}

}
