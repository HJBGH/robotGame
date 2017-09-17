package robotGame.model.node;

import java.util.ArrayList;

import robotGame.model.Prize;

public class Node implements NodeInterface{

	private int x;
	private int y;
	private ArrayList<Node> neighbours = null;
	private Prize prize = null; //A prize, the hero will look for this.
	
	public Node(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void addNeighbour(Node node)
	{
		neighbours.add(node);
	}
	
	public boolean isEnd()//used to check if a node is the destination node.
	{
		return (this instanceof DestinationNode);
	}

	@Override
	public void deleteNeighbour(Node node) 
	{
		neighbours.remove(node);
	}

	@Override
	public ArrayList<Node> getNeighbours() 
	{
		return this.neighbours;
	}
}
