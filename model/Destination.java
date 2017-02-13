package robotGame.model;

import java.util.ArrayList;

public class Destination {
	/*An array list of sources contained in the destination,
	 * note that once they're in they can't get out. this attribute never gets used
	 * it was originally intended to be used for multiple source/robot games.
	 */
	private ArrayList<Source> source_list = new ArrayList<Source>();
	
	public Destination(){
		//empty constructor.
	}
	
	//allows the addition of sources to the source list.
	public void addSource(Source new_src){
		source_list.add(new_src);
	}
	
	//returns the array of sources contained in the destination.
	public ArrayList<Source> getSources(){
		return source_list;
	}

}
