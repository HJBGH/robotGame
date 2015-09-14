package model;

import java.util.ArrayList;

public class Destination {
	/*An array list of sources contained in the destination,
	 * note that once they're in they can't get out.
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
	//this should have an array of sources contained in it.
	//this class should be able to return the array.
	//this class should be able to append sources to the array.
}
