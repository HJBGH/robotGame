package robotGame.model;

public class Source {
	boolean held= false; //sources do not start off held.
	String name;
	
	public Source(String name){
		this.name = name;
	}
	public Source(){
		
	}
	//these are essentially setters and getters with more meaningful names
	public void picked(){
		held = true;
	}
	
	public void dropped(){
		held = false;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
