package model;



/*Some of the classes in this package may seem un-necessary, but I find them useful
 * for management.
 */
public class Model {

	/*the absence of booleans means that I no longer have to worry about tracking
	 * the boolean for each thingo, I just need to check to see if an entity is present 
	 * at the targeted cell when a command is given.
	 */
	private Cell[][] board = new Cell[10][10];
	//this does not allow for multiple robots. It will break really badly if more than one are placed on the board.
	private int[] botPos;
	//model constructor
	public Model(){
		for(int y=0;y<10;y++){
			for(int x=0;x<10;x++){
				board[y][x]= new Cell();
			}
		}
	}
	
	//board returner
	public Cell[][] getBoard(){
		return board;
	}
	
	//toggle walkable
	public void toggleWalkable(int x, int y) throws ModelException{
		try{
			board[y][x].toggleWalkable();
			System.out.println("Cell at "+x+","+y+" walkable state toggled");
		}
		catch(CellException CellE){
			throw new ModelException("Cell cannot be toggled, object present");
		}
		catch(Exception GeneralE){
			System.out.println(GeneralE.getStackTrace());
			throw new ModelException("An unknown exception occurred, printed trace");
		}
	}
	
	//the model doesn't need to get walkable
	//placers for robot, source and destination
	public void place(int x, int y, Robot new_bot) throws ModelException{
		if(!boardHasBot()){
			try{
				board[y][x].place(new_bot);
				botPos = new int[] {x, y};
				System.out.println("Bot placed on"+x+"'"+y);

			}
			catch(CellException cell)
			{
				throw new ModelException("Robot cannot be placed on cell");
			}
			catch(Exception GeneralE){
				System.out.println(GeneralE.getStackTrace());
				throw new ModelException("An unknown exception occurred, printed trace");
			}
		}
	}
	//source placer
	public void place(int x, int y, Source new_src) throws ModelException{
		if(!boardHasSrc()){
			try{
				board[y][x].place(new_src);
			}
			catch(CellException cell)
			{
				throw new ModelException("Source cannot be placed on cell");
			}
			catch(Exception GeneralE){
				System.out.println(GeneralE.getStackTrace());
				throw new ModelException("An unknown exception occurred, printed trace");
			}
		}
	}
	//destination placer
	public void place(int x, int y, Destination new_dst) throws ModelException{
		if(!boardHasDst()){
			try{
				board[y][x].place(new_dst);
			}
			catch(CellException cell)
			{
				throw new ModelException("Destination cannot be placed on cell");
			}
			catch(Exception GeneralE){
				System.out.println(GeneralE.getStackTrace());
				throw new ModelException("An unknown exception occurred, printed trace");
			}
		}
	}
	//the model doesn't need to get walkable
	//removers for robot, source and destination
	//there really should be a method that picks up the robot to move it.
	public Robot removeBot(int x, int y) throws ModelException{
		try{
			return board[y][x].removeBot();
		}
		catch(CellException cell)
		{
			throw new ModelException("No robot on this cell");
		}
		catch(Exception GeneralE){
			System.out.println(GeneralE.getStackTrace());
			throw new ModelException("An unknown exception occurred, printed trace");
		}
	}
	//source remover
	public Source removeSrc(int x, int y) throws ModelException{
		try{
			return board[y][x].removeSrc();
		}
		catch(CellException cell)
		{
			throw new ModelException("No source on this cell");
		}
		catch(Exception GeneralE){
			System.out.println(GeneralE.getStackTrace());
			throw new ModelException("An unknown exception occurred, printed trace");
		}
	}
	//destination remover
	public Destination removeDst(int x, int y) throws ModelException{
		try{
			return board[y][x].removeDst();
		}
		catch(CellException cell)
		{
			throw new ModelException("No destination on this cell");
		}
		catch(Exception GeneralE){
			System.out.println(GeneralE.getStackTrace());
			throw new ModelException("An unknown exception occurred, printed trace");
		}
	}
	//checking for object presence
	public boolean hasBot(int x, int y){
		return(this.board[y][x].hasBot());
	}
	
	//checking for the presence of a source on the cell
	public boolean hasSrc(int x, int y){
		return(this.board[y][x].hasSrc());
	}
	
	/*checking for presence of a destination, this will be needed in
	 * win state checking 
	 */
	public boolean hasDst(int x, int y){
		return(this.board[y][x].hasDst());
	}
	//checking the whole board for the items
	/*the only reason these methods exist is to prevent the user from
	 * placing multiple items because otherwise out pathfinding doesn't 
	 * work.
	 */
	public boolean boardHasBot(){
		for(int i = 0; i<10; i++){
			for(int n = 0; n<10; n++){
				if(hasBot(i,n)){
					return true;
				}
			}
		}
		return false;
	}
	//check if the board has a destination on it
	public boolean boardHasDst(){
		for(int i = 0; i<10; i++){
			for(int n = 0; n<10; n++){
				if (hasDst(i,n)){
					return true;
				}
			}
		}
		return false;
	}
	//check if the board has a source on it
	public boolean boardHasSrc(){
		for(int i = 0; i<10; i++){
			for(int n = 0; n<10; n++){
				if (hasSrc(i,n)){
					return true;
				}
			}
		}
		return false;
	}
	
	//methods for moving the robot
	public void moveRobot(char v){
		Robot temp_bot;
		try {
			temp_bot = removeBot(botPos[0],botPos[1]);
		} catch (ModelException e2) {
			e2.printStackTrace();
			System.out.println("no source to pickup");
			return;
		}
		switch(v){
		case 'N':
			try {
				this.place(botPos[0], botPos[1]-1, temp_bot);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			break;
		case 'E':
			try {
				this.place(botPos[0]+1, botPos[1], temp_bot);
			} catch (ModelException e) {
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			break;
		case 'S':
			try {
				this.place(botPos[0], botPos[1]+1, temp_bot);
			} catch (ModelException e) {
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			break;
		case 'W':
			try {
				this.place(botPos[0]-1, botPos[1], temp_bot);
			} catch (ModelException e) {
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			break;
		}
	}
	
	//telling the cell to tell it's robot to hold the source
	public void pickup(){
		board[botPos[1]][botPos[0]].pickup();
	}
	//telling the cell to tell it's robot to put down the source
	public void drop(){
		board[botPos[1]][botPos[0]].drop();
	}
}
