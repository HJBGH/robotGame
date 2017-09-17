package robotGame.model;

import java.util.Observable;

public interface ModelInterface
{
	//provides methods for interacting with the model.
	public void addNode(int x, int y); //add a node with the co-ordinates x,y
	public void removeNode(int x, int y); //remove a node with the coordinates x,y
	
	public void addHero(int x, int y);
	public void removeHero(int x, int y);
	
	public void addEndNode(int x, int y);
	public void removeEndNode(int x, int y);
	
	public void addPrize(int x, int y);
	public void removePrize(int x, int y);
}
