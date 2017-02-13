package robotGame.controller;
/* NOTE This Exception class will be thrown when an invalid command is
** found in a given string in the Interpreter class 
**/
public class InvalidCommandException extends Exception
{
	
	
	public InvalidCommandException(String message)
	{
		//Send the message to super so it can be printed in a catch statement
		super(message);
	}
}
