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
		this.neighbours = new ArrayList<Node>();
	}
	
	@Override
	public void addNeighbour(Node node)
	{
		neighbours.add(node);
	}

	@Override
	public void deleteNeighbour(Node node) 
	{
		neighbours.remove(node);
	}

	@Override
	public ArrayList<Node> getNeighbours() 
	{
		return (ArrayList<Node>)this.neighbours.clone();
	}
	
	@Override
	public void deleteAllNeighbours()
	{
			neighbours = new ArrayList<Node>(); //Expensive
	}

	@Override
	public void addPrize(Prize prize) {
		this.prize = prize;
	}

	@Override
	public void removePrize() {
		this.prize = null;
	}
	
	public boolean hasPrize()
	{
		return !(this.prize == null);
	}
}
