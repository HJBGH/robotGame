package model;

public class CellException extends Exception{
	
	public CellException(){
		//empty constructor. no message provided
	}
	
	//overloading the constructor to let me pass a message in it.
	public CellException(String msg){
		super(msg);
	}

}
