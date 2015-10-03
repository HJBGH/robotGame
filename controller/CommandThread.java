package controller;

import java.util.ArrayList;
import view.View;
import model.Model;

public class CommandThread implements Runnable
{
	private ArrayList<String> commands;
	private Model the_model;
	private View the_view;

	
	public CommandThread(View the_view, Model the_model,
			ArrayList<String> commands)
	{
		this.commands = commands;
		this.the_model = the_model;
		this.the_view = the_view;
	}

	
	
	public void run()
	{
		for (String cmd : commands)
		{
			if(the_model.checkForWin()){
				the_view.set_alert("WINRAR IS YOU");
				return;
			};
			if ((cmd.toUpperCase()).equals("PICK"))
			{
				the_model.pickup();
			} 
			else if ((cmd.toUpperCase()).equals("DROP"))
			{
				the_model.drop();
			} 
			else
			{
				int count = Character.getNumericValue(cmd.charAt(0));
				char direction = cmd.charAt(1);

				for (int i = 0; i < count; i++)
				{
					the_model.moveRobot(direction);
					try
					{
						Thread.sleep(200);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					the_view.redrawBoard(the_model.getBoard());
				}
			}
			
			try
			{
				Thread.sleep(200);
			} 
			
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			the_view.redrawBoard(the_model.getBoard());
		}
	}
}
