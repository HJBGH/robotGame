package robotGame.controller;

import java.awt.event.*;
import static java.lang.System.out;
import javax.swing.JPanel;

import robotGame.model.*;
import robotGame.model.Model;
import robotGame.view.View;

//these listeners need to be their own classes, violated cohesion
public class Controller extends MouseAdapter implements ActionListener
{
	//TODO: Need an options enumeration for placing pieces
	//this is going to need a reference to the model.
	private enum Instruction { HERO, PRIZE, DEST, NODE};
	
	private Model model = null;
	private int cells_x = 0;
	private int cells_y = 0;
	private Instruction currentInstruction = Instruction.NODE;
	
	
	public Controller(Model theModel, int cells_x, int cells_y)
	{
		this.model = theModel;
		this.cells_x = cells_x;
		this.cells_y = cells_y;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("MousePressed");
		//need to do event processing in here
		
		/*
		 * view board cell dimensions, it may be a bad idea to have that JPanel cast.
		 */
		double x = ((JPanel)e.getSource()).getWidth()/cells_x;
		double y = ((JPanel)e.getSource()).getHeight()/cells_y;
		switch(currentInstruction)
		{
		case NODE:
			model.toggleNode((int)(e.getX()/x), (int)(e.getY()/y));
			break;
		case DEST:
			System.out.println("Toggling goal node - Controller");
			model.toggleGoalNode((int)(e.getX()/x), (int)(e.getY()/y));
			break;
		case HERO:
			System.out.println("Toggling hero location - Controller");
			model.toggleHero((int)(e.getX()/x), (int)(e.getY()/y));
			break;
		case PRIZE:
			System.out.println("Adding prize");
			model.togglePrize((int)(e.getX()/x), (int)(e.getY()/y));
			break;
		default:
			System.out.println("something has gone horribly awry");
			break;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		out.println("FUG :-D");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("An action happened!");
		System.out.println(e.getActionCommand());
		switch(e.getActionCommand())
		{
		case "NODE":
			this.currentInstruction = Instruction.NODE;
			break;
		case "PRIZE":
			this.currentInstruction = Instruction.PRIZE;
			break;
		case "HERO":
			this.currentInstruction = Instruction.HERO;
			break;
		case "DEST":
			this.currentInstruction = Instruction.DEST;
			break;
		case "SOLVE":
			model.solve();
			System.out.println("solve called");
			break;
		default:
			System.out.println("unhandled action command");
			break;
		}
	}

	
}
