import java.util.Random;
import javax.swing.JLabel;
/**
 * @author Alex Cherniak
 * 
 * this class represents a philosopher that is sitting behind a table
 * each philosopher is a separate thread
 */



public class Philosopher extends Thread 
{
	/*****************************************************************************************************************************************
	 * Instance Variables
	 ****************************************************************************************************************************************/
	private final int MAX_TIME = 10000; //the maximum delay time for a philosopher to eat / think
	private boolean eating = false;
	private Table table;
	private int serialNumber; //represents the sit of the current philosopher
	private Stick firstStick; //represent the first stick that the philosopher holds
	private Stick secondStick; //represent the second stick the philosopher holds
	private JLabel eatingLabel; //an eating label from the gui
	private boolean running = true;
	
	/*****************************************************************************************************************************************
	 * Constructor
	 ****************************************************************************************************************************************/
	public Philosopher(int sn , Table table , JLabel label)
	{
		this.table = table; //the table that this philosopher sits next to
		this.eatingLabel = label;
		serialNumber = sn; //set the serial number
		firstStick = null; //no stick
		secondStick = null;//no stick
	}
	
	/*
	 * the run method will start the thread 
	 * the philosopher will try to pick the lowest valued stick that is next to him , if the stick is unavailable , he will wait for it
	 * after picked the lowest valued stick , will try to pick the second stick
	 * once he has both sticks , will eat for a random time between 0 and 10 seconds
	 * then will put the sticks back on the table
	 */
	
	/*******************************************************************************************************************************
	 * Methods
	 *******************************************************************************************************************************/
	
	public void run()
	{
		while(running)
		{
			think();
			firstStick = table.getFirstStick(serialNumber); //always the lower valued stick next to the philosopher
			secondStick = table.getSecondStick(serialNumber); //always the higher valued stick next to the philosopher
			System.out.println("Philosopher " + serialNumber + " started eating");
			eatingLabel.setVisible(true);
			eating = true; //turn eating state to true
			think(); //eating for a random time between 0 and 10 seconds
			table.returnSticks(firstStick.getValue() , secondStick.getValue()); //return the sticks to the table
			eatingLabel.setVisible(false);
			System.out.println("Philosopher " + serialNumber + " done eating");
		}
		
	}
	
	/*
	 * think method - a method that will cause a delay of maximum 10 seconds
	 * will be used for the time that the philosophers is thinking or eating
	 */
	public synchronized void think()
	{
		Random rnd = new Random();
		int time = rnd.nextInt(MAX_TIME);
		try 
		{
			wait(time);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public int getSerialNumber()
	{
		return serialNumber;
	}
	
	public boolean getEatingState()
	{
		return eating;
	}
	
	public void setRunning(boolean state)
	{
		running = state;
	}
	

}
