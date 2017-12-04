package robotGame.model.infoBoard;

import java.util.Observable;
import robotGame.utils.*;
import java.util.HashSet;
import java.awt.Point;
/*
 * This class will contain public information about the model for use by the view. it is observable.
 * It should probably be a singleton.
 * It's incredibly inefficient to re-draw the entire board for one change, but I can work that out later
 */
public class InfoBoard extends Observable{
	//member variables will represent information about the model.
	//make a method to notify the view of updates to this object
	//hashset for storing relevant information
	public AnimationChainFactory AF = new AnimationChainFactory();
	public HashSet<Point> nodePoints = new HashSet<Point>();
	public HashSet<Point> prizePoints = new HashSet<Point>();
	public Point heroPoint = null; //locations of solvers
	public HashSet<Point> destPoints = new HashSet<Point>();
	private boolean solved = false;
	public void addNodePoint(int x, int y)
	{
		//we risk adding loads of duplicates here
		nodePoints.add(new Point(x, y));
		setChanged(); //set changed
		notifyObservers();
	}
	
	public void removeNodePoint(int x, int y)
	{
		//iterate through the hashset, see if there already exists and object like this.
		nodePoints.remove(new Point(x, y));//hopefully Equals() is defined in point based on X and Y co-ordinates
		destPoints.remove(new Point(x, y));
		setChanged();
		notifyObservers();
	}
	
	public void setHeroPoint(int x, int y)
	{
		this.heroPoint = new Point(x, y);
		System.out.println("IB - Setting hero point to " + x + ","+y);
		setChanged();
		notifyObservers();
	}
	
	public void setHeroPoint(Point point)
	{
		this.heroPoint = point;
		setChanged();
		notifyObservers();
	}
	
	public void addPrizePoint(int x, int y)
	{
		prizePoints.add(new Point(x, y));
		setChanged();
		notifyObservers();
	}
	
	public void removePrizePoint(int x, int y)
	{
		prizePoints.remove(new Point(x, y));
		setChanged();
		notifyObservers();
	}
	
	public void addDestination(int x, int y)
	{
		destPoints.add(new Point(x, y));
		setChanged();
		notifyObservers();
	}
	
	public void removeDestination(int x, int y)
	{
		destPoints.remove(new Point(x, y));
		setChanged();
		notifyObservers();
	}
	
	public void toggleSolved()
	{
		this.solved = !this.solved;
		setChanged();
		notifyObservers();
	}
	
	public boolean getSolved()
	{
		return this.solved;
	}
}
