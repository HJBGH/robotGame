package controller;

import java.util.ArrayList;

import view.View;
import model.*;

public class Solver {
	private String commandOutput;

	public Solver(){
		//empty constructor
	}
	
	private String generatePath(Model the_model, int[] a){
		//Jack,(or Assessors) if you're reading this Sam and I are kinda sorry.
		Cell[][] the_board = the_model.getBoard();
		String commandString = "";
		int[] botPos = a;
		int current_Weight = the_board[botPos[1]][botPos[0]].weight;
		int next_Weight = 999;
		while(current_Weight!=0){
			System.out.println(botPos[0] +","+ botPos[1]);
			
			current_Weight = the_board[botPos[1]][botPos[0]].weight;
			System.out.println(current_Weight);
			
			
			String direction = "";
			try{
				if(the_board[botPos[1]-1][botPos[0]].getWalkable())
				{
					System.out.println("walkable north");
					if(the_board[botPos[1]-1][botPos[0]].weight<current_Weight
							||the_board[botPos[1]-1][botPos[0]].weight<next_Weight)
					{
							next_Weight = the_board[botPos[1]-1][botPos[0]].weight;
							direction = "1N,";	
					}
				}
			}
			catch(Exception E)
			{
				System.out.print("North OOB");
			}
			try{
				if(the_board[botPos[1]][botPos[0]+1].getWalkable())
				{
					if(the_board[botPos[1]][botPos[0]+1].weight<current_Weight
							||the_board[botPos[1]][botPos[0]+1].weight<next_Weight)
					{
							next_Weight = the_board[botPos[1]][botPos[0]+1].weight;
							direction = "1E,";	
					}
				}
			}
			catch(Exception E)
			{
				System.out.print("East OOB");
			}
			try{
				
				if(the_board[botPos[1]+1][botPos[0]].getWalkable())
				{
					if(the_board[botPos[1]+1][botPos[0]].weight<current_Weight
							||the_board[botPos[1]+1][botPos[0]].weight<next_Weight)
					{
							next_Weight = the_board[botPos[1]+1][botPos[0]].weight;
							direction = "1S,";	
					}
				}
			}
			catch(Exception E)
			{
				System.out.print("South OOB");
			}
			try{
				if(the_board[botPos[1]][botPos[0]-1].getWalkable())
				{
					if(the_board[botPos[1]][botPos[0]-1].weight<current_Weight
							||the_board[botPos[1]][botPos[0]-1].weight<next_Weight)
					{
							next_Weight = the_board[botPos[1]][botPos[0]-1].weight;
							direction = "1W,";	
					}
				}
			}
			catch(Exception E)
			{
				System.out.print("West OOB");
			}
			current_Weight = next_Weight;
			commandString+= direction;
			
			switch(direction)
			{
				case "1N,": botPos[1]-=1;
						   break;
				case "1E,": botPos[0]+=1;
						   break;
				case "1S,": botPos[1]+=1;
						   break;
				case "1W,": botPos[0]-=1;
						   break;
				default: System.out.println("IN DEFAULT");
						 break;
			}
			//If source and destination are placed on the same cell the robot will pick up the source and then stop.
		}
		
		
		if(the_model.hasSrc(botPos[0], botPos[1])){
			commandString += "pick,";
		}		
		else if(the_model.hasDst(botPos[0], botPos[1])){
			commandString += "drop,";
		}

		
		return commandString;
		
	}
	
	public String solve(Model the_model){
		//get the locations of the pieces.
		int[] srcPos = the_model.getSrcPos();
		int[] dstPos = the_model.getDstPos();
		int[] botPos = the_model.getBotPos();
		Cell[][] the_board = the_model.getBoard();
		String holdStr = "";
		System.out.printf("The bot is at %d,%d",botPos[0],botPos[1]);
		//source bit
		the_model.setWeightsNegOne();
		/* initial weight set at zero because there is zero difference between a piece and
		 *itself is 0*/
		the_model.setInitalWeight(srcPos[0], srcPos[1]);
		the_model.calculateWeights(srcPos[0], srcPos[1], 0);
		//
		if (the_board[botPos[1]][botPos[0]].weight == -1)
		{
			System.out.println("Cannot find path to Source");
			return "";
		}
		commandOutput = generatePath(the_model, botPos);
		System.out.println(commandOutput);
		System.out.println("source check finished");
		
		//destination bit
		the_model.setWeightsNegOne();
		the_model.setInitalWeight(dstPos[0], dstPos[1]);
		the_model.calculateWeights(dstPos[0], dstPos[1], 0);
		//
		if (the_board[botPos[1]][botPos[0]].weight == -1)
		{
			System.out.println("Cannot find path Destination");
			return "";
		}
		
		commandOutput += generatePath(the_model, botPos); 
		return commandOutput;
	}

}
