/**
 * 
 * @author Alex Cherniak
 * 
 * this class represents a stick that is put on the table
 *
 */
public class Stick 
{
	/********************************************************************************************************************************
	 * Instance Variables
	 *******************************************************************************************************************************/
	private int value;
	private boolean free = true;
	
	/********************************************************************************************************************************
	 * Constructor
	 *******************************************************************************************************************************/
	public Stick(int value)
	{
		this.value = value;
	}
	
	
	
	/**********************************************************************************************************************************
	 * Methods
	 **********************************************************************************************************************************/
	public int getValue()
	{
		return value;
	}
	/*
	 * isFree method - designed to provide info about the current stick if its free or taken
	 */
	public boolean isFree()
	{
		return free;
	}
	/*
	 * setStatus method is designed to set the status of a current stick
	 * if stick is taken by a philosopher -> free = false
	 * if stick is free -> free = true
	 */
	public void setStatus(boolean status)
	{
		free = status;
	}
}
