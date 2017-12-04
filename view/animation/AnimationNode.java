package robotGame.view.animation;

/**
 * This class will be used as a superclass to construct a chain of changes to be
 * made to the solvers' position on the views' representation of the board.
 * Each of the subclasses will represent one of the four directions that the solver can move in.
 * @author hb
 *
 */
public class AnimationNode {
	private int delta = 0;
	private AnimationNode nextChange = null;
	
	public int getDelta()
	{
		return delta;
	}
	
	public void setNext(AnimationNode next)
	{
		this.nextChange = next;
	}
	
	public AnimationNode getNext()
	{
		return this.nextChange;
	}
	
	public AnimationNode(int D)
	{
		this.delta = D;
	}
}
