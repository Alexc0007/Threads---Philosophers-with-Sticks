import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Alex Cherniak
 * This Class represents the Gui + Main for the Philosophers VS Sticks program
 */
public class MainGUI 
{
	/*******************************************************************************************************************************
	 * Instance Variables
	 ******************************************************************************************************************************/
	private JFrame frmPhilosophersVsSticks;
	//eating labels
	private JLabel lblIsEating1;
	private JLabel lblIsEating2;
	private JLabel lblIsEating3;
	private JLabel lblIsEating4;
	private JLabel lblIsEating5;
	private JLabel lblTable;
	private JButton btnStart;
	private JButton jbStop;
	private JPanel mainPanel;
	final int NUMBER_OF_STICKS = 5;
	final int NUMBER_OF_PHILOSOPHERS = 5;
	int serialNumber = 0; //will represent the serial number for the philosophers
	protected JLabel[] eatingLables = new JLabel[NUMBER_OF_PHILOSOPHERS]; //create an array of eating labels
	private ExecutorService exec = Executors.newFixedThreadPool(NUMBER_OF_PHILOSOPHERS);
	private Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS]; //create philosophers array

	/********************************************************************************************************************************
	 * Launch the application.
	 *******************************************************************************************************************************/
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frmPhilosophersVsSticks.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/********************************************************************************************************************************
	 * Create the application.
	 ********************************************************************************************************************************/
	public MainGUI() 
	{
		initialize();
	}

	/*********************************************************************************************************************************
	 * Initialize the contents of the frame.
	 *********************************************************************************************************************************/
	private void initialize() 
	{
		frmPhilosophersVsSticks = new JFrame();
		frmPhilosophersVsSticks.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\\u05DE\u05D3\u05E2\u05D9 \u05D4\u05DE\u05D7\u05E9\u05D1 - \u05EA\u05D5\u05D0\u05E8 \u05E8\u05D0\u05E9\u05D5\u05DF\\Advanced Java\\Threads - Philosophers\\src\\expertadvise.png"));
		frmPhilosophersVsSticks.setTitle("Philosophers VS Sticks");
		frmPhilosophersVsSticks.setResizable(false);
		frmPhilosophersVsSticks.setBounds(100, 100, 1500, 891);
		frmPhilosophersVsSticks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPhilosophersVsSticks.getContentPane().setLayout(new BorderLayout(0, 0));
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(250, 235, 215));
		frmPhilosophersVsSticks.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		
		lblIsEating1 = new JLabel("Is Eating");
		lblIsEating1.setForeground(new Color(255, 0, 0));
		lblIsEating1.setBounds(686, 100, 56, 16);
		lblIsEating1.setVisible(false);
		mainPanel.add(lblIsEating1);
		
		lblIsEating2 = new JLabel("Is Eating");
		lblIsEating2.setForeground(new Color(255, 0, 0));
		lblIsEating2.setBounds(527, 190, 56, 16);
		lblIsEating2.setVisible(false);
		mainPanel.add(lblIsEating2);
		
		lblIsEating3 = new JLabel("Is Eating");
		lblIsEating3.setForeground(new Color(255, 0, 0));
		lblIsEating3.setBounds(527, 397, 56, 16);
		lblIsEating3.setVisible(false);
		mainPanel.add(lblIsEating3);
		
		lblIsEating4 = new JLabel("Is Eating");
		lblIsEating4.setForeground(new Color(255, 0, 0));
		lblIsEating4.setBounds(847, 397, 56, 16);
		lblIsEating4.setVisible(false);
		mainPanel.add(lblIsEating4);
		
		lblIsEating5 = new JLabel("Is Eating");
		lblIsEating5.setForeground(new Color(255, 0, 0));
		lblIsEating5.setBounds(836, 190, 56, 16);
		lblIsEating5.setVisible(false);
		mainPanel.add(lblIsEating5);
		
		lblTable = new JLabel("");
		lblTable.setOpaque(true);
		lblTable.setBounds(492, 71, 443, 489);
		lblTable.setIcon(new ImageIcon("D:\\\u05DE\u05D3\u05E2\u05D9 \u05D4\u05DE\u05D7\u05E9\u05D1 - \u05EA\u05D5\u05D0\u05E8 \u05E8\u05D0\u05E9\u05D5\u05DF\\Advanced Java\\Threads - Philosophers\\src\\Inked5pplRoundTable.jpg"));
		mainPanel.add(lblTable);
		
		
		//create start button
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				start();
			}
		});
		btnStart.setForeground(new Color(0, 100, 0));
		btnStart.setFont(new Font("Papyrus", Font.PLAIN, 42));
		btnStart.setBounds(257, 628, 928, 78);
		mainPanel.add(btnStart);
		
		/*
		 * create the stop button
		 * 
		 * once the stop button is pressed , the philosophers will finish their cycle of "eating" and will then stop
		 */
		jbStop = new JButton("Stop");
		jbStop.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(int i=0;i<philosophers.length;i++)
				{
					philosophers[i].setRunning(false);
				}
			}
		});
		jbStop.setForeground(new Color(165, 42, 42));
		jbStop.setFont(new Font("Papyrus", Font.PLAIN, 32));
		jbStop.setBounds(527, 745, 376, 67);
		mainPanel.add(jbStop);
	}
	
	/*
	 * start method - once pressed , will start the application by launching the threads and displaying who is eating
	 */
	public void start()
	{
		serialNumber = 0;
		eatingLables[0] = lblIsEating1;
		eatingLables[1] = lblIsEating2;
		eatingLables[2] = lblIsEating3;
		eatingLables[3] = lblIsEating4;
		eatingLables[4] = lblIsEating5;		
		
		Stick[] sticks = new Stick[NUMBER_OF_STICKS]; //create an array of 5 sticks
		for(int i=0;i<sticks.length;i++) //initialize the sticks array
		{
			sticks[i] = new Stick(i+1);
		}
		
		Table woodenTable = new Table(sticks); //create the table
		
		for(int i=0;i<philosophers.length;i++)//create the threads and start them
		{
			serialNumber++;
			philosophers[i] = new Philosopher(serialNumber, woodenTable , eatingLables[i]);
			exec.execute(philosophers[i]);
		}
		
	}
}
