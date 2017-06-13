/**
 * 
 * @author Alex Cherniak
 * this class represents a table that has 5 sticks on it and 5 philosophers are sitting around it 
 */
public class Table 
{
	/*****************************************************************************************************************************************
	 * Instance Variables
	 ****************************************************************************************************************************************/
	private Stick[] sticks; //array of sticks
	
	/*****************************************************************************************************************************************
	 * Constructor
	 ****************************************************************************************************************************************/
	public Table(Stick[] sticks)
	{
		this.sticks = sticks;
	}
	
	
	
	/*
	 * the build is that each stick is between 2 philosophers
	 * description:
	 *          *  <---------0  
	 * 0---> |     |  <----4
	 * 1--->*        * <----4
	 * 1---> |     | <----3
	 * 2--->  *   * <---3
	 *          | <-----2
	 */
	
	/*
	 * get first stick method - will get the first stick , depending on which philosopher called the method
	 * if stick is unavailable , the philosopher will wait for it
	 */
	public synchronized Stick getFirstStick(int serial)
	{
		switch(serial)
		{
			case 1: //philosopher[0] -> SN=1 -> try to get sticks[0] - with value=1
				return getStick(0);
	
			case 2: //philosopher[1] -> SN=2 -> try to get sticks[0] - with value=1
				return getStick(0);
				
			case 3: //philosopher[2] -> SN=3 -> try to get sticks[1] - with value=2
				return getStick(1);
				
			case 4: //philosopher[3] -> SN=4 -> try to get sticks[2] - with value=3
				return getStick(2);
				
			case 5: //philosopher[4] -> SN=5 -> try to get sticks[3] - with value=4
				return getStick(3);
				
			default:
				System.out.println("philosopher was not recognized - verify that there are 5 philosophers");
				return null; //this shouldnt happen... ever!
		}
	}
	
	/*
	 * get second stick method - will get the second stick , depending on which philosopher called the method
	 * if stick is unavailable , the philosopher will wait for it
	 */
	public synchronized Stick getSecondStick(int serial)
	{
		switch(serial)
		{
		case 1: //philosopher[0] -> SN=1 -> try to get sticks[4] - with value=5
				return getStick(4);
	
			case 2: //philosopher[1] -> SN=2 -> try to get sticks[1] - with value=2
				return getStick(1);
				
			case 3: //philosopher[2] -> SN=3 -> try to get sticks[2] - with value=3
				return getStick(2);
				
			case 4: //philosopher[3] -> SN=4 -> try to get sticks[3] - with value=4
				return getStick(3);
				
			case 5: //philosopher[4] -> SN=5 -> try to get sticks[4] - with value=5
				return getStick(4);
				
			default:
				System.out.println("philosopher was not recognized - verify that there are 5 philosophers");
				return null; //this shouldnt happen... ever!
		}
	}
	
	/*
	 * getStick method - a private method to check if a stick is free to use - it so , it will return the stick
	 * if the stick is not free - it will set the thread to a waiting state until the stick will be free again
	 */
	private synchronized Stick getStick(int index)
	{
		while(!sticks[index].isFree()) //while stick is not free - wait
		{
			try 
			{
				wait();
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		//once stick is free - take it
		sticks[index].setStatus(false);//set status as taken
		return sticks[index];//give the stick to the current philosopher
	}
	
	/*
	 * returnStick method - each philosopher when done eating -  will return his sticks to the table
	 * it will cause the sticks to be free to use again once they are returned to the table
	 */
	public void returnSticks(int firstStickValue , int secondStickValue)
	{
		//sticks[0] -> value=1 || sticks[1] -> value=2 || sticks[2] -> value=3 || sticks[3] -> value=4 || sticks[4] -> value=5
		putStick(firstStickValue-1);
		putStick(secondStickValue-1);
	}
	
	/*
	 * put stick method - will put the stick with the given index back on the table
	 */
	private synchronized void putStick(int index)
	{
		sticks[index].setStatus(true); //set the status as free
		notifyAll(); //notify all threads that a stick is free - all threads will retry taking their desired sticks
	}
	
	
	
	
	
}
