package robotGame.model;

public class ModelException extends Exception {
	
	public ModelException(){
		//default constructor
	}
	
	//calling super class constructor with custom message
	public ModelException(String msg){
		super(msg);
	}
}
