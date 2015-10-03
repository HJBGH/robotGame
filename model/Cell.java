package model;

public class Cell {
	private boolean walkable = false; //all cells are unwalkable to start with.
	private Robot bot;
	private Source source;
	private Destination dest; //dest short for destination.
	public int weight;
	
	public Cell(){
		// empty constructor
	}
	
	//walkable settings, toggles, getters etc
	//the toggle
	public void toggleWalkable()throws CellException{
		if((hasBot()==true || hasSrc()==true)||hasDst()==true){
			throw new CellException("Cannot toggle walkable while entity present");
		}
		else{
			//System.out.println("Toggled walkable");
			this.walkable = !this.walkable;
		}
	}
	//the walkable getter
	public boolean getWalkable(){
		return this.walkable;
	}
	//the walkable setter
	public void setWalkable(boolean bool) throws CellException{
		if(hasBot()==true){
			throw new CellException("Cannot set walkable while entity present");
		}
		this.walkable = bool;
	}
	
	
	/*Placer methods, these methods take instances of the various entity objects as
	 * arguements and set them to the appropriate variable on the cell. effectively
	 * "Placing" them on the cell. These methods are actually just one method overloaded.
	 */
	//Robot placer
	public void place(Robot new_bot) throws CellException{
		if(getWalkable() == false){
			throw new CellException("Cannot place Robot on unwalkable cell");
		}
		else if(hasBot()==true){
			throw new CellException("Cannot place two Robots on one cell");
		}
		else{
			System.out.println("Bot placed");
			this.bot= new_bot;
		}
	}
	//Source placer
	public void place(Source new_source) throws CellException{
		if(getWalkable() == false){
			throw new CellException("Cannot place Source on unwalkable cell");
		}
		else if(hasSrc()==true){
			throw new CellException("Cannot place two Sources on one cell");
		}
		else
		{
			System.out.println("Source placed");
			this.source = new_source;
		}
	}
	//Destination placer
	public void place(Destination new_dest) throws CellException{
		if(getWalkable() == false){
			throw new CellException("Cannot place Destination on unwalkable cell");
		}
		else if(hasDst()==true){
			throw new CellException("Cannot place two Destinations on one cell");
		}
		else
		{
			System.out.println("Destination placed");
			this.dest = new_dest;
		}
	}
	
	/*Remover methods, these methods will simply set the cells object reference to null
	 * effectively "removing" it from the cell
	 */
	//robot remover
	public Robot removeBot() throws CellException{
		if(hasBot()==false){
			throw new CellException("This cell has no robot");
		}
		else
		{
			Robot temp_bot=this.bot;
			this.bot=null;
			return temp_bot;
		}
	}
	//source remover
	public Source removeSrc() throws CellException{
		if(hasSrc()==false){
			throw new CellException("This cell has no source");
		}
		else
		{
			Source temp_src=this.source;
			this.source=null;
			return temp_src;
		}
	}
	//destination remover
	public Destination removeDst() throws CellException{
		if(hasDst()==false){
			throw new CellException("This cell has no destination");
		}
		else
		{
			Destination temp_dst = this.dest;
			this.dest=null;
			return temp_dst;
		}
	}

	//presence checking methods
	//checking for the presence of a robot on the cell
	public boolean hasBot(){
		if(this.bot!=null){
			return true;
		}
		return false;
	}
	
	//checking for the presence of a source on the cell
	public boolean hasSrc(){
		if(this.source!=null){
			return true;
		}
		return false;
	}
	
	/*checking for presence of a destination, this will be needed in
	 * win state checking 
	 */
	public boolean hasDst(){
		if(this.dest!=null){
			return true;
		}
		return false;
	}
	
	/*this method is a side effect of my implementation, essentially it should be 
	 * run every time a robot drops something. It checks to see if a source and
	 * destination are present and then it passes the destination the source*/
	/*Note that this method isn't used anywhere and probably won't be unless we
	 * get time to work on our stuff some more before we have to submit. But I've
	 * got WEB, C and DCNC to do so I doubt that'll happen 
	 */
	public void sourceToDest(){
		if(hasDst()&&hasSrc()){
			this.dest.addSource(this.source);
		}
		//placeholder comment, I got too bored to implment this.
	}
	public void pickup(){
		if(hasBot()&&hasSrc()){
			this.bot.holdSource(this.source);
			this.source=null;
		}
	}
	public void drop(){
		if(hasBot()&&!hasSrc()){
			if(this.bot.source_held==true){
				this.source=bot.dropSource();
			}
		}
	}
}
