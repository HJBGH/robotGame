package robotGame.model;

import java.util.ArrayList;
import java.util.Observable;

import robotGame.model.infoBoard.InfoBoard;
import robotGame.model.node.*;

public class Model implements ModelInterface{

	/*TODO: implement solving in the form of a private class and a variable to hold the
	 * starting node
	 * TODO:
	 * consider adding exceptions
	 * TODO:
	 * consider removing x,y coordinate info from inside Node, I don't think node needs it.
	 * TODO: This class needs a method for changing its dimensions
	 */
	//TODO: add infoBoard updates to each of these methods
	//private ArrayList<Node> nodes = null;
	private Node[][] board = null;
	private static Hero solver = null;
	private int xMax;
	private int yMax;
	private InfoBoard ib = null;
	/*this does not allow for multiple robots. It will break really badly if more than
	 * one are placed on the board.
	 */
	//position tracking variables

	
	//model constructor
	public Model(int xMax, int yMax, InfoBoard infoBoard) throws ModelException{
		if(xMax < 1 || yMax < 1)
		{
			throw new ModelException("Must have positive dimensions.");
		}
		this.board = new Node[xMax][yMax];
		this.xMax = xMax-1;
		this.yMax = yMax-1;
		this.solver = new Hero();
		this.ib = infoBoard;
	}
	
	//board returner AAAAARRRRGH DELET THIS
	/*
	public Node[][] getBoard(){
		return board; //<-- !! privacy leak FIX THIS SHIT
	}*/
	
	
	@Override
	public void toggleNode(int x, int y)
	{
		
		//the situation where x and y are greater or smaller than the bounds of the
		//board will never arise. CHECK FOR IT ANYWAY
		
		//also need to check if there's a node already there
		if(this.board[x][y] != null)
		{
			//delete pre-existing node, then return
			Node removed = this.board[x][y];
			//remove mention of the node from neighbours
			for(Node neighbour: removed.getNeighbours())
			{
				neighbour.deleteNeighbour(removed); //this should work.
			}
			removed.deleteAllNeighbours();
			this.board[x][y] = null; //point to null;
			removed = null; //all mentions of the node removed
			
			this.ib.removeNodePoint(x, y);
		}
		else
		{
			//create a new node
			Node newNode = new Node(x, y);
		
			//insert into array
			this.board[x][y] = newNode;
			System.out.println("node added at: " + x +", "+ y);
			//check for neighbours, be sure not to access and index outside of the array
			//abuse lazy evaluation <-- bad idea
			if(x < this.xMax && this.board[x+1][y] != null)//east neighbour
			{
				newNode.addNeighbour(this.board[x+1][y]);
				this.board[x+1][y].addNeighbour(newNode);
			}
			if(x > 0 && this.board[x-1][y] != null)//west neighbour
			{
				newNode.addNeighbour(this.board[x-1][y]);
				this.board[x-1][y].addNeighbour(newNode);
			}
			if(y < this.yMax && this.board[x][y+1] != null)//north neighbour
			{
				newNode.addNeighbour(this.board[x][y+1]);
				this.board[x][y+1].addNeighbour(newNode);
			}
			if(y > 0 && this.board[x][y-1] != null)//south neighbour
			{
				newNode.addNeighbour(this.board[x][y-1]);
				this.board[x][y-1].addNeighbour(newNode);
			}
			this.ib.addNodePoint(x, y);
		}
	}

	@Override
	public void toggleHero(int x, int y)
	{
		
		//Make sure we have a legitimate place to put the hero.
		
		if(board[x][y] == null)
		{
			this.toggleNode(x, y);
		}
		if(solver.position != null)
		{
			if(solver.position.equals(board[x][y]))
			{
				solver.setPosition(null);
				this.ib.setHeroPoint(null);
				return;
			}
		}
		solver.setPosition(board[x][y]);
		this.ib.setHeroPoint(x, y);
		//set the start point of the solver to this node.
	}

	@Override
	public void toggleGoalNode(int x, int y) {
		System.out.println("toggling goal - model");
		if(board[x][y] == null)
		{
			this.toggleNode(x, y);
		}
		board[x][y].toggleGoal();
		if(board[x][y].isGoal())
		{
			this.ib.addDestination(x, y);
		}
		else
		{
			this.ib.removeDestination(x, y);
		}
	}

	
	@Override
	public void togglePrize(int x, int y)
	{
		if(board[x][y] == null)
		{
			this.toggleNode(x, y);
		}
		if(board[x][y].hasPrize() == false)
		{
			board[x][y].addPrize(new Prize());
			this.ib.addPrizePoint(x, y);
		}
		else
		{
			board[x][y].removePrize();
			this.ib.removePrizePoint(x, y);
		}
	}
	
	//These should be private
	@Override
	public void addPrize(int x, int y) {
		board[x][y].addPrize(new Prize()); //this will fail if the node is null
	}

	@Override
	public Prize removePrize(int x, int y) {
		Prize prize = board[x][y].removePrize();
		//update info board
		return prize; //technically we don't need to be passing this reference everywhere
	}

	@Override
	public void solve() {
		solver.solve();
	}
	
	//figure out how to set changed from within this class
	private class Hero
	{
		private Node position = null;
		private Prize prize = null;
		
		//variables used for finding solution
		private ArrayList<Node> closed = new ArrayList<Node>();
		
		public void setPosition(Node node)
		{
			position = node;
		}
		
		//this will be used in the solve method
		private void setPrize(Prize prize)
		{
			this.prize = prize;
		}
		
		//use DFS to solve mazes, we can do this recursively. But it won't preserve the starting state
		//there's going to be two solutions. solving to get the prize, and solving to find the destination
		//need to handle invalid problems. i.e. maps with no solutions
		public void solve()
		{
			//DFS for prize
			if(prize == null)
			{
				//n for neighbours
				if(position.hasPrize())
				{
					//idk if this will work properly
					setPrize(Model.this.removePrize(position.x, position.y));
					//TODO set changed here! maybe not. Remove prize already removes it.
				}
				ArrayList<Node> n = position.getNeighbours();
				//we're going to have to set change in here. perhaps solver should just be its own method.
				//but that prevents future multithreading.
			}
			System.out.println("Maze solving not yet implemented");
		}
	}
}
