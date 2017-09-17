package robotGame.model.node;

import java.util.ArrayList;

public interface NodeInterface
{
	public void addNeighbour(Node node);
	public void deleteNeighbour(Node node);
	public ArrayList<Node> getNeighbours();
}
