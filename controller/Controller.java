package robotGame.controller;

import java.awt.event.*;
import java.util.ArrayList;

import robotGame.model.*;
import robotGame.model.Model;
import robotGame.view.View;

public class Controller implements MouseListener, ActionListener
{
	//TODO: Need an options enumeration for placing pieces
	//this is going to need a reference to the model.
	private Model model = null;
	private int cells_x = 0;
	private int cells_y = 0;
	
	
	public Controller(Model theModel, int cells_x, int cells_y)
	{
		this.model = theModel;
		this.cells_x = cells_x;
		this.cells_y = cells_y;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("MouseClicked");
		//need to do event processing in here
		//this doesn't actually work, we need the actual co-ordinates of the game
		//Magic number note: the 900 is the dimension of the board
		
		/*
		 * view board cell dimensions.
		 */
		double x = 900/cells_x;
		double y = 900/cells_y;
		
		model.toggleNode((int)(e.getX()/x), (int)(e.getY()/y));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("An action happened!");
		
	}

	
}
