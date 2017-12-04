package robotGame.utils;

import robotGame.view.animation.*;
import robotGame.model.node.*;

/**
 * A factory class for constructing chains of animation nodes based on the input of the 
 * solver member class in Model
 * @author hb
 *
 */
public class AnimationChainFactory{
	/*
	 * here's the plan:
	 * The calling routine provides a model node.
	 * The factory calculates the difference in x or y co-ordinates between the
	 * provided node an the last, and constructs a new animation node of the
	 * appropriate type based on this information.
	 */
	private AnimationNode head = null;
	private AnimationNode current = null;
	private Node previousNode = null;
	
	//returns the animation chain and prepares the factory for constructing the next chain.
	public AnimationNode getAnimationChain()
	{
		this.current = null;
		this.previousNode = null;
		AnimationNode temp = head;
		this.head = null;
		return temp;
	}
	
	public void addStep(Node modelNode)
	{
		//first step in chain, adding the initial position of the solver
		if(modelNode == null)
		{
			return;
		}
		if(this.previousNode == null)
		{
			this.previousNode = modelNode;
		}
		//recording the first move the solver makes.
		else if(this.head == null)
		{
			if(previousNode.y != modelNode.y)
			{
				this.head = new YanimationNode(modelNode.y - previousNode.y);
			}
			else if(previousNode.x != modelNode.x)
			{
				this.head = new XanimationNode(modelNode.x - previousNode.x);
			}
			this.current = head;
			this.previousNode = modelNode;
		}
		//recording the remainder of the solvers moves
		else
		{
			AnimationNode newAnimNode = null;
			if(previousNode.y != modelNode.y)
			{
				newAnimNode = new YanimationNode(modelNode.y - previousNode.y);
			}
			else
			{
				newAnimNode = new XanimationNode(modelNode.x - previousNode.x);
			}
			this.current.setNext(newAnimNode);
			this.current = newAnimNode;
			this.previousNode = modelNode;
		}
	}
}
