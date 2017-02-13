package robotGame.model;



/*To The Assessors:
 * 	Some of the classes in the model package have attributes and methods which are not used in the
 *	program. Examples are the destination's source list and the methods associated with this list,
 *	and the name String, held boolean and relevant methods in the source class.
 *	There are other methods and attributes like this in the cell and in the model.
 *	Originally we planned to attempt to implement multiple robots and sources, these attributes and 
 *	variables would have helped in that case. However; around about week 10 we realised we were no-where
 *	close to adding this functionality so the attributes and methods have remained unused.
 *	They have not been removed, we though it would be better to leave them in incase an
 *	opportunity to implement multiple robots and sources came up.
 */   
public class Model {

	/*the absence of booleans means that I no longer have to worry about tracking
	 * the boolean for each piece, I just need to check to see if an entity is present 
	 * at the targeted cell when a command is given.
	 */
	private Cell[][] board = new Cell[10][10];
	/*this does not allow for multiple robots. It will break really badly if more than
	 * one are placed on the board.
	 */
	//position tracking variables
	private int[] botPos;
	private int[] dstPos;
	private int[] srcPos;
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
	//getters for the position int arrays
	public int[] getDstPos(){
		return dstPos;
	}
	public int[] getSrcPos(){
		return srcPos;
	}
	public int[] getBotPos(){
		int[] pos = {botPos[0],botPos[1]};
		return pos;
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
				srcPos = new int[]{x, y};
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
				dstPos = new int[] {x, y};
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
	public Robot removeBot(int x, int y) throws ModelException{
		try{
			System.out.println("remove bot looked for bot at: "+ x +"," + y);
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
	 * work. Orignally we had planned to let the user use multiple pieces but we 
	 * ran out of time.
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
	
	//methods for moving the robot, where char v is the 'v'ector.
	public void moveRobot(char v) throws ModelException{
		Robot temp_bot;
		try {
			temp_bot = removeBot(botPos[0],botPos[1]);
			if(temp_bot == null){
				throw new ModelException("NPE occurred in removeBot");	
			}
		} catch (ModelException e2) {
			e2.printStackTrace();
			System.out.println("no bot to pickup");
			System.out.println("Looked for bot at: "+ botPos[0]+","+botPos[1]);
			return;
		}
		switch(v){
		case 'N':
			try {
				this.place(botPos[0], botPos[1]-1, temp_bot);
				//System.out.println("Moved north");
			} catch (ModelException e) {
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					e1.printStackTrace();
				}
				
				throw new ModelException("No walkable cell North of Robot");
				//e.printStackTrace();
			}
			break;
		case 'E':
			try {
				this.place(botPos[0]+1, botPos[1], temp_bot);
				//System.out.println("Moved east");
			} catch (ModelException e) {
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					e1.printStackTrace();
				}
				throw new ModelException("No walkable cell East of Robot");
			}
			break;
		case 'S':
			try {
				this.place(botPos[0], botPos[1]+1, temp_bot);
				//System.out.println("Moved south");
			} catch (ModelException e) {
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					e1.printStackTrace();
				}
				throw new ModelException("No walkable cell South of Robot");
			}
			break;
		case 'W':
			try {
				this.place(botPos[0]-1, botPos[1], temp_bot);
				//System.out.println("Moved west");
			} catch (ModelException e) {
				try {
					this.place(botPos[0], botPos[1], temp_bot);
				} catch (ModelException e1) {
					e1.printStackTrace();
				}
				throw new ModelException("No walkable cell West of Robot");
			}
			break;
		default:
			System.out.println("Unrecognized direction");
			break;
		}
	}
	
	//telling the cell to tell its robot to hold the source
	public void pickup(){
		board[botPos[1]][botPos[0]].pickup();
	}
	//telling the cell to tell its robot to put down the source
	public void drop(){
		board[botPos[1]][botPos[0]].drop();
	}
	
	/*Checking for winstate conditions, ie robot, source and destination
	 * all on one cell. Note that this implementation is not how I would
	 * have liked to do it, but time constraints mean we will only be dealing
	 * with one of each entity. Ideally what would have happened is that the 
	 * source would have been added to the destinations source list. Then; 
	 * once there were no more sources on the board the source list would 
	 * be checked to make sure all the sources were there. If so; win.
	 */
	public boolean checkForWin(){
		if(hasSrc(botPos[0], botPos[1]) && hasDst(botPos[0], botPos[1])){
			return true;
		}
		return false;
	}
	
	/* set all the weights to negative one*/
	public void setWeightsNegOne(){
		//10 is the width and height of the board
		for(int i = 0; i<10; i++){
			for(int n = 0; n<10; n++){
				board[i][n].weight=-1;
			}
		}
	}
	/*set all the weights in the cells for getting from that cell to the cell at
	 * position x,y
	 */
	public void calculateWeights(int x, int y, int count){
		try{
			board[y][x].weight = count;
			if(board[y-1][x].getWalkable()==true &&
					(board[y-1][x].weight<0 || count+1<board[y-1][x].weight)){
				System.out.println("checking north");
				
				calculateWeights(x, y-1, count+1);
			}
		}
		catch(Exception e){
			System.out.println("north OOB");
		}
		try{
			board[y][x].weight = count;
			if(board[y][x+1].getWalkable()==true && 
					(board[y][x+1].weight<0 || count+1<board[y][x+1].weight)){
				System.out.println("checking east");
				calculateWeights(x+1, y, count+1);
			}
		}
		catch(Exception e){
			System.out.println("east OOB");
		}
		try{
			board[y][x].weight = count;
			if(board[y+1][x].getWalkable()==true &&
					(board[y+1][x].weight<0 || count+1<board[y+1][x].weight)){
				System.out.println("checking south");
				calculateWeights(x, y+1, count+1);
			}
		}
		catch(Exception e){
			System.out.println("south OOB");
		}
		try{
			board[y][x].weight = count;
			if(board[y][x-1].getWalkable()==true &&
					(board[y][x-1].weight<0 || count+1<board[y][x-1].weight)){
				System.out.println("checking west");
				calculateWeights(x-1, y, count+1);
			}
		}
		catch(Exception e){
			System.out.println("west OOB");
		}
	}
	
	/*	set initial weight function, used for setting source and destination weights in pathfinding*/
	public void setInitalWeight(int x, int y){
		board[y][x].weight=0;
	}
}
