package robotGame.model.node;

import java.util.ArrayList;
import robotGame.model.*;

public interface NodeInterface
{
	public void addNeighbour(Node node);
	public void deleteNeighbour(Node node);
	public ArrayList<Node> getNeighbours();
	public void deleteAllNeighbours();
	public void addPrize(Prize prize);
	public Prize removePrize();
	public boolean hasPrize();
}
