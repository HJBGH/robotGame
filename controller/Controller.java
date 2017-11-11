package robotGame.controller;

import java.awt.event.*;
import java.util.ArrayList;

import robotGame.model.*;
import robotGame.model.Model;
import robotGame.view.View;

public class Controller implements MouseListener
{
	//TODO: Need an options enumeration for placing pieces
	//this is going to need a reference to the model.
	private Model model = null;
	
	
	public Controller(Model theModel)
	{
		this.model = theModel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("MouseClicked");
		//need to do event processing in here
		model.addNode(0, 0);
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

	
}
