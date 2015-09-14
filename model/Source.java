package model;

public class Source {
	boolean held= false; //sources do not start off held.
	String name;
	
	public Source(String name){
		this.name = name;
	}
	public Source(){
		//overloaded empty constructor
	}
	
	public void picked(){
		held = true;
	}
	
	public void dropped(){
		held = false;
	}
}
