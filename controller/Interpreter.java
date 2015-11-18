package controller;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
public class Interpreter 
{
	
	
	public ArrayList<String> interpret(String commands) throws InvalidCommandException
	{
		// Stores the commands that are verified
		ArrayList<String> validCmds = new ArrayList<String>();
		
		// The regular expression pattern to match against
		Pattern p = Pattern.compile("(\\b(pick|drop)\\b|\\b([0-9][NSEW])\\b)",Pattern.CASE_INSENSITIVE);
		
		/* Regular expression that splits the received string by 
		** any character that is NOT a letter or a digit 
		** This way user can enter commands separated by 
		** space or comma or any symbol like /
		** This should probably be more specific and I can change it if need be
		** to be whatever we specify as valid 
		*/
		String[] tokens = commands.split("[^0-9a-zA-Z]");
		
		// Object that checks for a match with the pattern
		Matcher m;
		
		/* Loop through tokens and check each one for a match
		** If no match then throw InvalidCommandException
		** If match then add to validCmds array then return
		*/
		for(String token: tokens)
		{
			m = p.matcher(token);
			if(m.find())
			{
				validCmds.add(token);
			}
			else
			{
				if(!token.isEmpty())
				{
					throw new InvalidCommandException(String.format("Inavlid command %s",token));
				}
			}
		}
		return validCmds;
	}
}
