package robotGame.model;


public interface ModelInterface
{
	//provides methods for interacting with the model.
	//Combine these two node methods into a toggle node method.
	public void toggleNode(int x, int y); //Toggle the existance node with the co-ordinates x,y
	
	public void toggleGoalNode(int x, int y);
	
	public void addPrize(int x, int y);
	public Prize removePrize(int x, int y);
	
	public void solve(); //The model runs its solver

	void toggleHero(int x, int y);

	void togglePrize(int x, int y);
}
