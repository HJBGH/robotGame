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
			//every time the walkable parts of the board change we should validate the position of the solver
			if(!solver.validatePosition())
			{
				//maybe use toggle hero?
				this.solver.setPosition(null);
				this.ib.setHeroPoint(null);
			}
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
		//The solver is responsible for updating the board about it's position.
		solver.setPosition(board[x][y]);
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
		//create a thread for the solver and run that sumbith
		try {
			this.solver.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//figure out how to set changed from within this class
	//set this to be another thread
	private class Hero implements Runnable
	{
		private Thread thread;
		private String name = "Solver";
		private Node position = null;
		private Prize prize = null;
		
		//variables used for finding solution
		private ArrayList<Node> closed = new ArrayList<Node>();
		//private ArrayList<>
		public void setPosition(Node node)
		{
			if(node != null)
			{
				Model.this.ib.setHeroPoint(node.x, node.y);
			} //this code is the perfect example of why you shouldn't use null values to represent blank nodes
			position = node;
			//we might have to update the InfoBoard from in here
		}
		
		//If the argument node is the same instance as the current position, set the current position to null
		//This is probably bad code, as it's only used once the only point I use it in.
		public boolean validatePosition()
		{
			//this will return true if both variables are null, it shouldn't
			if(this.position == null)
				return true;
			if(Model.this.board[this.position.x][this.position.y] == null)
			{
				return false;
			}
			return true;
		}
		//this will be used in the solve method
		private void setPrize(Prize prize)
		{
			this.prize = prize;
		}
		
		//use DFS to solve mazes, we can do this recursively. But it won't preserve the starting state
		//there's going to be two solutions. solving to get the prize, and solving to find the destination
		//need to handle invalid problems. i.e. maps with no solutions
		public void solve() throws Exception //TODO: add custom exception
		{
			//publicly facing solve method, checks that the solver has a starting position then
			//calls the DFS solve method 
			//probably just better to validate the position.
			if(this.position == null)
			{
				throw new Exception("Null solver position");
			}
			dfs_solve(this.position);
			this.closed = new ArrayList<Node>();
			System.out.println("Model - Done solving");
			
		}
		
		//At large board sizes, this has the potential to construct truly ridiculous frame
		//stack sizes
		private void dfs_solve(Node currentNode)
		{
			System.out.println("Solver - Traversing :" + currentNode.x + ", " + currentNode.y);
			Model.this.ib.AF.addStep(currentNode);//illustrate initial exploration of the node.
			//DFS for prize
			if(this.prize == null)
			{
				//n for neighbours, as far as I remember the .getNeighbours method returns a clone, not the actual
				//neighbours arraylist pointer.
				position = currentNode;
				this.closed.add(currentNode);
				if(position.hasPrize())
				{
					//idk if this will work properly
					setPrize(Model.this.removePrize(position.x, position.y));
					return;
				}
				for(Node n : currentNode.getNeighbours())
				{
					if(!this.closed.contains(n) && prize == null)
					{
						dfs_solve(n);
					}
					
				}
			}
			Model.this.ib.AF.addStep(currentNode);//illustrate backtracking
			//follow this up with a second recursion for destination finding.
			
			System.out.println("Maze solving not fully implemented");
			return;
		}

		@Override
		public void run() 
		{
			try 
			{
				this.solve();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		public void start()
		{
			System.out.println("creating thread - solver start method");
			if(thread == null)
			{
				thread = new Thread(this, name);
				thread.start();
			}
		}
	}
}
