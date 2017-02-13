package robotGame.controller;

import java.util.ArrayList;

import robotGame.view.View;
import robotGame.model.*;
import robotGame.model.Model;

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
			if ((cmd.toUpperCase()).equals("PICK"))
			{
				the_model.pickup();
			} else if ((cmd.toUpperCase()).equals("DROP"))
			{
				the_model.drop();
			} else
			{
				int count = Character.getNumericValue(cmd.charAt(0));
				char direction = Character.toUpperCase(cmd.charAt(1));

				for (int i = 0; i < count; i++)
				{
					try{
						the_model.moveRobot(direction);
					}catch(ModelException ME){
						the_view.set_alert(ME.getMessage());
					}
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
			if (the_model.checkForWin())
			{
				the_view.set_alert("!!!!!!GAME OVER!!!!!!");
				the_view.disableButtons();
				return;
			}
			;
		}
	}
}
