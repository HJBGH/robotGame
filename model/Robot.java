package model;

public class Robot {
	public boolean source_held = false;
	private Source held_source;
	
	public Robot(){

	}

	//tell the robot to hold a source
	public void holdSource(Source given_src){
		this.held_source = given_src;
		source_held = true;
		System.out.println("Held source");
	}
	
	//tell the robot to drop the source
	public Source dropSource(){
		Source dropped_src = held_source;
		this.held_source=null;
		this.source_held=false;
		System.out.println("Dropped sauce");
		return dropped_src;
	}
}
