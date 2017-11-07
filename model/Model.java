package robotGame.model;

import java.util.ArrayList;

import robotGame.model.node.*;

/** 
 * 	Some of the classes in the model package have attributes and methods which are not used in the
 *	program. Examples are the destination's source list and the methods associated with this list,
 *	and the name String, held boolean and relevant methods in the source class.
 *	There are other methods and attributes like this in the cell and in the model.
 *	Originally we planned to attempt to implement multiple robots and sources, these attributes and 
 *	variables would have helped in that case. However; around about week 10 we realised we were no-where
 *	close to adding this functionality so the attributes and methods have remained unused.
 *	They have not been removed, we though it would be better to leave them in incase an
 *	opportunity to implement multiple robots and sources came up.
 */   
public class Model implements ModelInterface{

	/*TODO: implement support solving in the form of a private class and a variable to hold the
	 * starting node
	 */
	//private ArrayList<Node> nodes = null;
	private Node[][] board = null;
	private int xMax;
	private int yMax;
	/*this does not allow for multiple robots. It will break really badly if more than
	 * one are placed on the board.
	 */
	//position tracking variables
	private int[] botPos;
	private int[] dstPos;
	private int[] srcPos;
	//model constructor
	public Model(int xMax, int yMax){
		board = new Node[xMax][yMax];
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	//board returner
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
	}

	@Override
	public void removeNode(int x, int y)
	{
		Node removed = this.board[x][y];
		//remove mention of the node from neighbours
		for(Node neighbour: removed.getNeighbours())
		{
			neighbour.deleteNeighbour(removed); //this should work.
		}
		removed.deleteAllNeighbours();
		this.board[x][y] = null; //point to null;
		removed = null; //all mentions of the node removed
	}

	@Override
	public void addHero(int x, int y)
	{
		//Make sure we have a legitimate place to put the hero.
		if(board[x][y] == null)
		{
			this.addNode(x, y);
		}
		//set the start point of the solver to this node.
	}

	@Override
	public void removeHero(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEndNode(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEndNode(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPrize(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePrize(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
