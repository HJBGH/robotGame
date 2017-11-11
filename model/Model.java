package robotGame.model;

import java.util.ArrayList;
import java.util.Observable;

import robotGame.model.node.*;

public class Model extends Observable implements ModelInterface{

	/*TODO: implement solving in the form of a private class and a variable to hold the
	 * starting node
	 * TODO:
	 * consider adding exceptions
	 * TODO:
	 * consider removing x,y coordinate info from inside Node, I don't think node needs it.
	 */
	//TODO: add controller updates to each of these methods
	//private ArrayList<Node> nodes = null;
	private Node[][] board = null;
	private static Hero solver = null;
	private int xMax;
	private int yMax;
	/*this does not allow for multiple robots. It will break really badly if more than
	 * one are placed on the board.
	 */
	//position tracking variables

	
	//model constructor
	public Model(int xMax, int yMax){
		this.board = new Node[xMax][yMax];
		this.xMax = xMax;
		this.yMax = yMax;
		this.solver = new Hero();
	}
	
	//board returner AAAAARRRRGH DELET THIS
	public Node[][] getBoard(){
		return board; //<-- !! privacy leak FIX THIS SHIT
	}
	
	
	@Override
	public void addNode(int x, int y)
	{
		
		//the situation where x and y are greater or smaller than the bounds of the
		//board will never arise.
		
		//create a new node
		Node newNode = new Node(x, y);
		
		//insert into array
		this.board[x][y] = newNode;
		
		//check for neighbours, be sure not to access and index outside of the array
		//abuse lazy evaluation
		if(this.xMax <= x+1 && this.board[x+1][y] != null)//east neighbour
		{
			newNode.addNeighbour(this.board[x+1][y]);
			this.board[x+1][y].addNeighbour(newNode);
		}
		if(x-1 >= 0 && this.board[x-1][y] != null)//west neighbour
		{
			newNode.addNeighbour(this.board[x+1][y]);
			this.board[x+1][y].addNeighbour(newNode);
		}
		if(this.yMax <= y+1 && this.board[x][y+1] != null)//north neighbour
		{
			newNode.addNeighbour(this.board[x+1][y]);
			this.board[x][y+1].addNeighbour(newNode);
		}
		if(y-1 >= 0 && this.board[x][y-1] != null)//south neighbour
		{
			newNode.addNeighbour(this.board[x+1][y]);
			this.board[x][y-1].addNeighbour(newNode);
		}
		setChanged();
	}

	@Override
	public void removeNode(int x, int y)
	{
		//TODO: Add controller updates
		Node removed = this.board[x][y];
		//remove mention of the node from neighbours
		for(Node neighbour: removed.getNeighbours())
		{
			neighbour.deleteNeighbour(removed); //this should work.
		}
		removed.deleteAllNeighbours();
		this.board[x][y] = null; //point to null;
		removed = null; //all mentions of the node removed
		setChanged();
	}

	@Override
	public void addHero(int x, int y)
	{
		
		//Make sure we have a legitimate place to put the hero.
		
		if(board[x][y] == null)
		{
			this.addNode(x, y);
		}
		solver.setPosition(board[x][y]);
		//set the start point of the solver to this node.
		setChanged();
	}

	@Override
	public void removeHero(int x, int y) {
		solver.setPosition(null);
		setChanged();
	}

	@Override
	public void toggleGoalNode(int x, int y) {
		board[x][y].toggleGoal();
		setChanged();
	}

	@Override
	public void addPrize(int x, int y) {
		board[x][y].addPrize(new Prize());
		setChanged();
	}

	@Override
	public void removePrize(int x, int y) {
		board[x][y].removePrize();
		setChanged();
	}

	@Override
	public void solve() {
		solver.solve();
	}
	
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
					setPrize(position.removePrize());
				}
				ArrayList<Node> n = position.getNeighbours();
				//we're going to have to set change in here. perhaps solver should just be its own method.
				//but that prevents future multithreading.
			}
			System.out.println("Maze solving not yet implemented");
		}
	}
}
