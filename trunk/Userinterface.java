import java.util.Vector;
import java.util.NoSuchElementException;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.Color;
import java.awt.geom.*;
import java.awt.image.*;


/*
 * Class Userinterface -- Contains the graphical user interface components
 * JBuilder X Foundation
 * @ Author: Arunabh Das
 *
 */

public class Userinterface extends JFrame implements ActionListener
{

    Actions cpu; // declare a copy of the object
    Memoryclass memorymap;
    JButton b_startbutton, b_submitbutton;  //buttons
    //create menu items for all the user options
    JMenuBar el_menu;
    JMenu menu_file, menu_methods, menu_blocking;
    JMenuItem b_newbutton, b_openbutton, b_closebutton;
    JRadioButtonMenuItem  r_fifo,r_RR, r_FSS, r_random, r_banker;

    JFileChooser openFileDialog;//declare filedialog
    String fileName="";//variable to store the path of the file

    //some labels
    JLabel t_status, t_status2, t_status3, t_status4;
    JLabel status, status2, status3, status4;
    JLabel internalpanel_id_l;
    JLabel poisson_parameter_label;

    JLabel timelabel, timecounter, dispatch, dispatch_1, block, block_1, timequantum_0, timequantum_1, timequantum_2;
    JLabel r1, r2, r3, r4, r5, r_1, r_2, r_3, r_4, r_5;

    //Create area for data entry
    JTextArea poisson_parameter_ta;
    JTextField internalpanel_id;

    //declare area for process data
    JTextArea processarea;

    //create panels
    JPanel outerpanel;
    JPanel internalpanel_1;
    JPanel internalpanel_2;
    JPanel cpu_count;
    JPanel info_general;
    JPanel izq;
    //JPanel rightpanel;

    //we use the object to timer of java.awt as a clock
    Timer timer;
    boolean pausedstate = true; //to start with, we pause all processes

    /**************************************************************************************
    ***************************************************************************************
                        USER INTERFACE
    ***************************************************************************************
    ***************************************************************************************/

    public Userinterface(String st, int tq0, int tq1, int tq2)
    {

        //starts the simulation, sends argument FIFO / RR to the instance
        //and set the scheduling method for the CPU
        cpu = new Actions(st, tq0, tq1, tq2);

        //set a delay to throttle the speed of the animation
        //the advantage of timer is that it uses threads
        // ver http://java.sun.com/j2se/1.4.2/docs/api/java/util/Timer.html
        int delay = 100; // delay in milliseconds
        timer = new Timer(delay,this);
        int timeq0 = tq0;
        int timeq1 = tq1;
        int timeq2 = tq2;

        setTitle("Operating System Scheduler Simulator by Arunabh Das");
        setSize(1000,700); //width, height  -- dimensions of the userinterface

        //labels
        t_status = new JLabel("State");
        t_status2 = new JLabel("Temp. Rest.");
        t_status3 = new JLabel("Identification");
        t_status4 = new JLabel("Name");
        status = new JLabel("   ----   ");
        status2 = new JLabel("   ----   ");
        status3 = new JLabel("   ----   ");
        status4 = new JLabel("   ----   ");
        internalpanel_id_l = new JLabel("Enter ID:");


        timelabel =  new JLabel("Time:  ");
        timecounter = new JLabel(""+0); //initiate to zero
        dispatch = new JLabel ("Dispatch: ");
        dispatch_1 = new JLabel("");
        block = new JLabel ("Algoparam: ");
        block_1 = new JLabel("");
        timequantum_0 = new JLabel("Poisson");
        timequantum_1 = new JLabel("CPUL");
        timequantum_2 = new JLabel("CPUU");

        r1 = new JLabel ("R1: ");
        r2 = new JLabel ("R2: ");
        r3 = new JLabel ("R3: ");
        r4 = new JLabel ("R4: ");
        r5 = new JLabel ("R5: ");

        r_1 = new JLabel ("");
        r_2 = new JLabel ("");
        r_3 = new JLabel ("");
        r_4 = new JLabel ("");
        r_5 = new JLabel ("");

        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
        r5.setVisible(false);
        r_1.setVisible(false);
        r_2.setVisible(false);
        r_3.setVisible(false);
        r_4.setVisible(false);
        r_5.setVisible(false);

        //create area for data entry
        internalpanel_id = new JTextField();

 

        poisson_parameter_ta = new JTextArea();

        //initialize the buttons
        b_startbutton = new JButton(" \n Start / Stop \n ");
        b_submitbutton = new JButton("Submit Parameter");

        b_startbutton.addActionListener(this);
        b_startbutton.setToolTipText("Play/Pause");    //explanatory text
        b_startbutton.setAlignmentX(Component.LEFT_ALIGNMENT);


        b_submitbutton.addActionListener(this);
        b_submitbutton.setToolTipText("Submit");    //explanatory text
        b_submitbutton.setAlignmentX(Component.LEFT_ALIGNMENT);

        outerpanel = new JPanel();  //for the processes

        // commenting out this to kill bottomwest
        //internalpanel_2 = new JPanel();
				// TitledBorder tBorder2 =  BorderFactory.createTitledBorder("Entering Processes");
        // Probably a good idea to remove internalpanel_2

        // commenting out this to kill bottomwest
				// internalpanel_2.setBorder( tBorder2);
        // commenting out this to kill bottomwest
        //internalpanel_2.setLayout(new BoxLayout(internalpanel_2,BoxLayout.Y_AXIS));//rows x columns

  


        cpu_count = new JPanel();
        TitledBorder tBorder =  BorderFactory.createTitledBorder("Process Log and Workload Parameters");
        cpu_count.setBorder(tBorder);
        cpu_count.setLayout(new GridLayout(10,20));//rows x columns
        cpu_count.add(timelabel);
        cpu_count.add(timecounter);
        cpu_count.add(dispatch);
        cpu_count.add(dispatch_1);
        cpu_count.add(block);
        cpu_count.add(block_1);
        cpu_count.add(timequantum_0);
        cpu_count.add(timequantum_1);
        cpu_count.add(timequantum_2);

        cpu_count.add(r1);
        cpu_count.add(r_1);
        cpu_count.add(r2);
        cpu_count.add(r_2);
        cpu_count.add(r3);
        cpu_count.add(r_3);
        cpu_count.add(r4);
        cpu_count.add(r_4);
        cpu_count.add(r5);
        cpu_count.add(r_5);

        //create the menus
        el_menu = new JMenuBar(); //main menu
        setJMenuBar(el_menu);

        // menu constructor
        menu_file = new JMenu("File");

        b_newbutton = new JMenuItem("Generate Workload");
        b_newbutton.addActionListener(this);
        menu_file.add(b_newbutton);

        b_closebutton  = new JMenuItem("Quit");
        b_closebutton.addActionListener(this);
        menu_file.add(b_closebutton);

        el_menu.add(menu_file);

        menu_methods = new JMenu("Algorithm");
        menu_blocking = new JMenu("Algorithmparam");

        // menu item for FIFO
        ButtonGroup algogroup = new ButtonGroup();
        r_fifo = new JRadioButtonMenuItem("FIFO");
        r_fifo.setSelected(true);
        r_fifo.setToolTipText("Use FIFO");
        algogroup.add(r_fifo);
        r_fifo.addActionListener(this);
        menu_methods.add(r_fifo);

        // menu item for RR
        r_RR = new JRadioButtonMenuItem("RR");
        r_RR.setToolTipText("Use RR");
        algogroup.add(r_RR);
        r_RR.addActionListener(this);
        menu_methods.add(r_RR);

        // menu item for FSS
        r_FSS = new JRadioButtonMenuItem("FSS");
        r_FSS.setToolTipText("Use FSS");
        algogroup.add(r_FSS);
        r_FSS.addActionListener(this);
        menu_methods.add(r_FSS);


        //menu item for random blocking
        ButtonGroup algogroup2 = new ButtonGroup();
        r_random = new JRadioButtonMenuItem("Random");
        r_random.setSelected(true);
        r_random.setToolTipText("Use random blocking");
        algogroup2.add(r_random);
        r_random.addActionListener(this);
        menu_blocking.add(r_random);


        //menu item for banker algorithm
        r_banker = new JRadioButtonMenuItem("Banker");
        r_banker.setToolTipText("Use banking algorithm");
        algogroup2.add(r_banker);
        r_banker.addActionListener(this);
        menu_blocking.add(r_banker);

        el_menu.add(menu_methods);
        el_menu.add(menu_blocking);


        buildFileDialog(); //for another file dialog
        fill_ready_vector();
        //update_process_values(quantum0, quantum1, quantum2);

        Container principal = getContentPane() ;
        principal.setLayout(new GridLayout(1,0));

        JPanel izq = new JPanel();
        izq.setLayout(new BorderLayout());
        izq.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));


        JPanel arrivingpanel = new JPanel();
        arrivingpanel.setLayout(new BorderLayout());
        arrivingpanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        arrivingpanel.add(outerpanel,"North");  //processes
        //arrivingpanel.add(internalpanel_1,"South"); //look for the processes

        JPanel in_half = new JPanel();
        in_half.setLayout(new BorderLayout());
        in_half.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        in_half.add(cpu_count, "North"); //

        //---------------------------------------
        //           the graphics part
        //---------------------------------------

        memorymap = new Memoryclass(cpu.getProcesses());
        //---------------------------------------
        //---------------------------------------
        in_half.add(b_startbutton, "South");  //start button animation


        JPanel bottompanel = new JPanel();
        bottompanel.setLayout(new GridLayout(0,2));
        bottompanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        bottompanel.add(in_half,"East");   //button

        Color col2 = new Color(0,255,0);
        Color col3 = new Color(0,0,0);
        //color of TextArea
        processarea = new JTextArea();
        processarea.setEditable(false);
        processarea.setBackground(col3);
        processarea.setForeground(col2);
        Font fontvalue = new Font("Dialog", 1, 10);
        processarea.setFont(fontvalue);

        processarea.setText("  \n  Loading... ");
        JScrollPane scroll2 = new JScrollPane(processarea);
        // This is where the greenscreen shows up
        //scroll2.setPreferredSize(new Dimension(80,300));
        // dimensions of the green screen
        scroll2.setPreferredSize(new Dimension(1000,610));
        JPanel info_general = new JPanel();
        //info_general.setLayout(new BoxLayout(info_general, BoxLayout.Y_AXIS));
        info_general.setLayout(new BoxLayout(info_general, BoxLayout.X_AXIS));
        info_general.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        info_general.add(scroll2);  //state of the processes
        izq.add(arrivingpanel, "North");
        //izq.add(log2); //histogram
        izq.add(bottompanel,  "South");
        //rightpanel.add(memorymap);
        principal.add(izq);
        principal.add(info_general);
        //principal.add(rightpanel);
        addWindowListener(
            new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        }
        );
        setVisible(true);
    }

    /**************************************************************************************
    ***************************************************************************************

                      METHODS OF CLASSES

    ***************************************************************************************
    ***************************************************************************************/

    //put the processes in the tail
    public void fill_ready_vector()
    {
        Vector v = cpu.getProcesses();
        Color col2 = new Color (238,238,238); //basic color-type - gray Windows

        outerpanel.setBackground(col2);
        outerpanel.setOpaque(true);

        outerpanel.setSize(650,950); //displays the processes that are running
				cpu_count.setSize(650,950);
        outerpanel.setPreferredSize(new Dimension(650,150));
        //FlowLayout f1 = new FlowLayout(FlowLayout.LEFT);
        FlowLayout f1 = new FlowLayout(FlowLayout.RIGHT);
        outerpanel.setLayout(f1);

        //add the available processes
        for ( int i = 0; i < v.size() ; i++)
        {
            //we cast the process from a node
            PanelProcess p = new PanelProcess( (Process) v.get(i) );
            outerpanel.add(p,"Center");  //add the panels

        }

    }
    // display of greenscreen
    //display in the text area, the information for all the active processes
    public void display_processes()
    {
        Vector y = cpu.getProcesses();
        int clockticking=0;
        String displaytext =  " Fair Share Scheduling - Scheduler Simulation \n" +
                              "  @Author:  Arunabh Das \n" +
                              "__________________________________________________________________\n" +
                              "  ID    CPUburst(total)  CPU burst(remain)   State Elapsed   Run   Wait-time    \n" +
                              "__________________________________________________________________\n";

        String testingtext = "testing";
        int queue_level_initial = 0;
        int wait_time = 0;
        int wait_time0 = 0;
        int wait_time1 = 0;
        int wait_time2 = 0;
        int wait_time3 = 0;
        int wait_time4 = 0;
        int wait_time5 = 0;
        int wait_time6 = 0;
        int wait_time7 = 0;
        int wait_time8 = 0;
        int wait_time9 = 0;

        float throughput = 0;


        int sum_of_all_wait_times = 0;
        // start getting the individual process statuses for termination condition
        Process pro0 = (Process) y.get(0);
        Process pro1 = (Process) y.get(1);
        Process pro2 = (Process) y.get(2);
        Process pro3 = (Process) y.get(3);
        Process pro4 = (Process) y.get(4);
        Process pro5 = (Process) y.get(5);
        Process pro6 = (Process) y.get(6);
        Process pro7 = (Process) y.get(7);
        Process pro8 = (Process) y.get(8);
        Process pro9 = (Process) y.get(9);

        for ( int i = 0; i < y.size() ; i++)
        {
            clockticking++;
            Process pro = (Process) y.get(i);
            queue_level_initial = pro.getPID() % 3;

            wait_time = (int)cpu.getTotalTime() - (int)pro.getTimeSpentRunning();
            if (pro.isDone() == false)
            {
                displaytext = displaytext + "  " +
                              //data to add
                              pro.getPID() +  "     "  +
                              //pro.getName() + "      "  +
                              "        " + pro.getTWaitInitial() + "         " +
                              "      " + pro.getTWait() + "                   " +
                              pro.getState() + "      " +
                              // elapsed time
                              " " + cpu.getTotalTime() + "       " +
                              // run time
                              pro.getTimeSpentRunning() + "       " +
                              // wait time
                              wait_time + "\n";
            }
            else
            {
                displaytext = displaytext + "  " +
                              //data to add
                              pro.getPID() +  "     "  +
                              //pro.getName() + "      "  +
                              "        " + pro.getTWaitInitial() + "         " +
                              "      " + pro.getTWait() + "                   " +
                              pro.getState() + "      " +
                              // elapsed time
                              " " + cpu.getTotalTime() + "       " +
                              // run time
                              pro.getTimeSpentRunning() + "       " +
                              // wait time
                              wait_time + "\n";

            }
            // calculate sum of all wait times
            if (pro.isDone() == true)
            {
                wait_time0 = (int)cpu.getTotalTime() - (int)pro0.getTimeSpentRunning();
                wait_time1 = (int)cpu.getTotalTime() - (int)pro1.getTimeSpentRunning();
                wait_time2 = (int)cpu.getTotalTime() - (int)pro2.getTimeSpentRunning();
                wait_time3 = (int)cpu.getTotalTime() - (int)pro3.getTimeSpentRunning();
                wait_time4 = (int)cpu.getTotalTime() - (int)pro4.getTimeSpentRunning();
                wait_time5 = (int)cpu.getTotalTime() - (int)pro5.getTimeSpentRunning();
                wait_time6 = (int)cpu.getTotalTime() - (int)pro6.getTimeSpentRunning();
                wait_time7 = (int)cpu.getTotalTime() - (int)pro7.getTimeSpentRunning();
                wait_time8 = (int)cpu.getTotalTime() - (int)pro8.getTimeSpentRunning();
                wait_time9 = (int)cpu.getTotalTime() - (int)pro9.getTimeSpentRunning();
            }

            sum_of_all_wait_times = wait_time0 + wait_time1+ wait_time2 + wait_time3 + wait_time4 + wait_time5 + wait_time6 + wait_time7 + wait_time8 + wait_time9;
						
            //displaytext = displaytext + " Clock : " + clockticking + "\n";
            displaytext = displaytext + "_________________________________________________________________\n";
            processarea.setText(displaytext);
        }


        if ( (pro0.isDone() == true) && (pro1.isDone() == true) && ( pro2.isDone() == true ) && ( pro3.isDone() == true) && ( pro4.isDone() == true) && ( pro5.isDone() == true) && ( pro6.isDone()==true) && (pro7.isDone() == true) && (pro8.isDone()==true) && (pro9.isDone() == true) )
        {
            throughput = cpu.getTotalTime()/10;
            displaytext = displaytext + "Completion Time : " + cpu.getTotalTime() + "\n";
            displaytext = displaytext + "Throughput : " + throughput + "milliseconds per process \n";
            displaytext = displaytext + "Average job elapsed time : " + cpu.getTotalTime() + "\n";
            displaytext = displaytext + "Total wait time : " + sum_of_all_wait_times + "\n";
            displaytext = displaytext + "Average wait time : " + sum_of_all_wait_times/10 + "\n";
            processarea.setText(displaytext);
            stop_func();
        }

    }

    //method for removing all the ready processes
    public void empty_ready_vector()
    {
        outerpanel.removeAll();
    }


    //this method looks for the instance corresponding to your internalpanel
    //and returns its values
    public void process_state()
    {

        Vector v = cpu.getProcesses(); //get the vector with all the processes
        long compara = Integer.parseInt(internalpanel_id.getText());
        //if the internalpanel matches with the vector, then we get that node
        Process p = new Process();
        int i;
        for ( i = 0; i < v.size() ; i++)
        {
            p = (Process) v.get(i);  //we cast it so that the node is a process
            long id = p.getPID();
            if (id == compara)
            {
                break;
            }
        }
        //if there is no match, we return no result
        if (i == v.size())
        {
            status.setText("ID not valid");
            status2.setText("");
            status3.setText("");
            status4.setText("");
        }
        else
        {
            //we display the values that are requested
            status2.setText("" + p.getTWait());
            status3.setText("" + p.getPID());
            status4.setText("" + p.getName());
            //the state of the process is a little more complicated since it has 3 states
            //running, ready, blocked and we add finished to it
            status.setText(p.getState());

        }
    }


    //managing events - event handling
    public void actionPerformed(ActionEvent e)
    {
        //initiates the accountant and the animation - shows the bottompanel
        if ( e.getSource() == b_startbutton )
        {
            if (pausedstate == false)
            {
                pausedstate = true;
                stop_func();
                b_startbutton.setSelected(false);
                internalpanel_id.setEditable(true);
                //in_datos_ta.setVisible(true);

            }
            else
            {
                pausedstate = false;
                startAnimation();
                b_startbutton.setSelected(true);
                //in case we want to deactivate the visibility of the buttons
                //during execution (initially set to false)
                internalpanel_id.setEditable(true);
                //in_datos_ta.setVisible(true);
            }

        }
        //initiate the accountant
        //The function is as if it is outside the button, only if it is worth one cycle
        else if ( e.getSource() == timer )
        {
            cpu.singleCycle();
            display_processes();
            // this is suspect on 586
						update_process_values_timer();
            repaint();
        }
        //Execute FIFO
        else if ( e.getSource() == r_fifo)
        {
            cpu.setAlgorithm(Actions.FIFO);

        }
        //For executing RR
        else if ( e.getSource() == r_RR)
        {
            cpu.setAlgorithm(Actions.ROUNDROBIN);

        }
        else if ( e.getSource() == r_FSS)
        {
            cpu.setAlgorithm(Actions.FSS);

        }
        //For exectuing random blocking
        else if ( e.getSource() == r_random)
        {
            cpu.setBlocking(Actions.blocking_random);
            r_1.setVisible(false);
            r_2.setVisible(false);
            r_3.setVisible(false);
            r_4.setVisible(false);
            r_5.setVisible(false);

            r1.setVisible(false);
            r2.setVisible(false);
            r3.setVisible(false);
            r4.setVisible(false);
            r5.setVisible(false);
        }
        //For executing random blocking with banker algorithm
        else if ( e.getSource() == r_banker)
        {
            cpu.setBlocking(Actions.blocking_banker);
            r_1.setVisible(true);
            r_2.setVisible(true);
            r_3.setVisible(true);
            r_4.setVisible(true);
            r_5.setVisible(true);
            r1.setVisible(true);
            r2.setVisible(true);
            r3.setVisible(true);
            r4.setVisible(true);
            r5.setVisible(true);

        }
        //command for random values in internal panel
        else if ( e.getSource() == b_newbutton)
        {
            int algo = cpu.getAlgorithm();
            int blo = cpu.getBlocking();
            int quantum0 = cpu.getQuantum0();
            int quantum1 = cpu.getQuantum1();
            int quantum2 = cpu.getQuantum2();

            cpu = new Actions(); //constructor to test
            cpu.setAlgorithm(algo);
            cpu.setBlocking(blo);

            
						empty_ready_vector();
            fill_ready_vector();
            memorymap.update_process(cpu.getProcesses());
            update_process_values(quantum0, quantum1, quantum2);
            repaint();
        }

        //command in order to open a file
        else if ( e.getSource() == b_openbutton)
        {
            int returnVal = openFileDialog.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File fileName=openFileDialog.getSelectedFile();
                cpu = new Actions(fileName);
                empty_ready_vector();
                fill_ready_vector();
                memorymap.update_process(cpu.getProcesses());
                // this is suspect on 671 
								//update_process_values();
                repaint();
            }
        }

        //For exiting the simulation
        else if ( e.getSource() == b_closebutton)
        {
            //stop_func();
            dispose();
            System.out.println("You have exited the simulator.");
            System.exit(0);
        }

    }
    //in order to start the animation, we synchronize with the start button
    public synchronized void startAnimation()
    {
        if (pausedstate)
        {

        }
        else
        {
            if (!timer.isRunning())
            {
                timer.start();
            }
        }
    }

    //in order to slow down the animation, we use this synchronized timer
    public synchronized void stop_func()
    {
        if (timer.isRunning())
        {
            timer.stop();
        }
    }

    //update process display counters
	public void update_process_values(int q0, int q1, int q2)
    {
        timecounter.setText(Integer.toString((int)cpu.getTotalTime()));
        r_1.setText(Integer.toString((int)cpu.getResource(1)));
        r_2.setText(Integer.toString((int)cpu.getResource(2)));
        r_3.setText(Integer.toString((int)cpu.getResource(3)));
        r_4.setText(Integer.toString((int)cpu.getResource(4)));
        r_5.setText(Integer.toString((int)cpu.getResource(5)));
        //to check the algorithm that the dispatcher uses
        if (cpu.getAlgorithm() == 1)
            dispatch_1.setText("FIFO");
        else if (cpu.getAlgorithm() == 2)
        {
            dispatch_1.setText("RR");
        }
        else if (cpu.getAlgorithm() == 5)
        {
            dispatch_1.setText("FSS");

            timequantum_0.setText("Poisson : " + q0 + "\n");
            timequantum_1.setText("CPUL: " + q1 + "\n");
            timequantum_2.setText("CPUU:  " + q2 + "\n");
        }
        //to find out which algorithm is used for blocking
        if (cpu.getBlocking() == 3)
            block_1.setText("Random");

        else
            block_1.setText("Bank");
    }

	//update process display counters
	public void update_process_values_timer()
	{
		timecounter.setText(Integer.toString((int)cpu.getTotalTime()));
		r_1.setText(Integer.toString((int)cpu.getResource(1)));
		r_2.setText(Integer.toString((int)cpu.getResource(2)));
		r_3.setText(Integer.toString((int)cpu.getResource(3)));
		r_4.setText(Integer.toString((int)cpu.getResource(4)));
		r_5.setText(Integer.toString((int)cpu.getResource(5)));
		//to check the algorithm that the dispatcher uses
		if (cpu.getAlgorithm() == 1)
			dispatch_1.setText("FIFO");
		else if (cpu.getAlgorithm() == 2)
			{
				dispatch_1.setText("RR");
			}
		else if (cpu.getAlgorithm() == 5)
			{
				dispatch_1.setText("FSS");
				
				//timequantum_0.setText("Poisson : " + q0 + "\n");
				//timequantum_1.setText("CPUL: " + q1 + "\n");
				//timequantum_2.setText("CPUU:  " + q2 + "\n");
			}
		//to find out which algorithm is used for blocking
		if (cpu.getBlocking() == 3)
			block_1.setText("Random");
		
		else
			block_1.setText("Bank");
	}

    //to select the file
    void buildFileDialog()
    {
        openFileDialog = new JFileChooser("."); // fires up the file selector
    }

} // --Userinterface
//topleft
class PanelProcess extends JPanel
{
    //create a process area of class Process
    Process proc;
    //value of the size of the processbar
    static final int thickness_processbar  = 30; //thickness of the process bar
    static final int pos_id_y = 120; //y position for the identifier
    static final int height_processbar = 120; //height of the process bar

    //the colors we are going to use
    Color segmentcolor;
    JLabel identifier;
    JLabel percentage;

    //constructor generates a process
    PanelProcess()
    {
        proc = new Process();
        initPanel();
    }
    //create a panel for the process
    PanelProcess( Process p)
    {
        proc = p;
        initPanel();
    }

    //construct the panel for the processes to display
    void initPanel()
    {
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setLayout(new BorderLayout());
        identifier = new JLabel("p"+ (int)proc.getPID());
        percentage = new JLabel("Percentages");
        identifier.setHorizontalAlignment(SwingConstants.CENTER);
        percentage.setHorizontalAlignment(SwingConstants.CENTER);
        setSize(thickness_processbar,pos_id_y);
        add(percentage, "South");
        add(identifier,"North");
    }

    //when the process finishes, clear it. If it hasn't, update the advance
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if ( proc.isDone() == true)
        {
            setVisible(false); //hide it when it finishes
        }
        else
        {
            draw_processbar(g); //else draw the processbar

        }
    }
    //topleft stuff
    //draw the progress bar with filling color
    // the active process has another color
    void draw_processbar(Graphics g)
    {
        int alt_initial=0;
        int completed=0;
        int gro=0;
        int percentcomplete = 0;

        gro  = (int) thickness_processbar-2; //a small adustment so that we can stay within the frame
        alt_initial = (int) proc.getTInitial();
        completed = (int) proc.getTWait();
        percentcomplete =  (completed * 100)/ alt_initial ;
        //if the process is blocked, color it red
        //if the process is not active, color it green
        //if the process is active, color it green
        if (proc.isBlocked() == true)
            segmentcolor = new Color (180, 10, 10);
        else if (proc.isRunning() == true)
            segmentcolor = new Color (0, 134, 74);
        else
            //	segmentcolor = new Color (51,102,102);
            segmentcolor = new Color (21,56,56);

        identifier.setForeground( new Color (51,102,102) ); //color of the label
        percentage.setForeground( new Color (255, 255, 0) );
        percentage.setText( "" + percentcomplete + "%");

        //shape of the processsbar
        g.setColor(new Color(190,190,190));
        g.fillRect(0,height_processbar - alt_initial,gro,alt_initial);
        //filled
        g.setColor(segmentcolor);
        g.fillRect(0,height_processbar - completed,gro,completed);

    }

    public Dimension getPreferredSize()
    {
        return ( new Dimension(30,pos_id_y));
    }


} // --  PanelProcess

//class logo (prints the image that it gets from the URL)
class Logo extends JPanel
{
    String path_to_image = "";

    Logo(String r)
    {
        path_to_image = r;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    void draw(Graphics g)
    {
        //create the image
        Image icon;
        Toolkit T = this.getToolkit();
        icon = T.getImage(path_to_image);
        g.drawImage(icon, 3, 3, this);
    }
} // --  Logo

//class logo (print the image that it gets from the URL)
class Memoryclass extends JPanel
{
    int greencolor = 0;
    int redcolor = 150;
    int bluecolor = 0;

    Vector v = null;
    int displacementX = 0;
    int displacementY = 0;
    int sentry = 0;
    int id_actual = 0;

    int activeIndex= -1;

    int largePage = 64;
    int counter = largePage;

    Color fillercolorr = new Color(180,180,180);
    boolean original = true;

    Color fillercolorr2;

    //constructor default
    Memoryclass()
    {
        //nothing for the moment
    }
    //read the vector
    Memoryclass( Vector vec)
    {
        v = vec;
    }

    ///////////////////////////////////////////////////////////////////
    //////////////////////// Userinterface components ////////////////

    boolean page_values_array[] = {false, false, false, false, false};
    boolean double_values_array[] = {false, false, false, false, false};
    int initial_values_array[] = {0,80,160,240,320};
    //int initial_values_array[] = {160,240,320,400,480};
    int fillercolor[] = {0,0,0,0,0};
    Color colors[] = {new Color(0,0,0),new Color(100,0,0),new Color(0,0,0),new Color(100,0,0),new Color(0,0,0)};


    ///////////////////////////////////////////////////////////////////

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //disabling the drawGrid
        drawmfqGrid(g);//draw the grid
        mydrawProcesses(g); // this is where I draw the processes belonging to each level
        //drawProcesses(g);//draw the processes one by one
    }

    //use page_values_arraytion
    void drawProcesses(Graphics g)
    {
        Process p = null;
        for (int i=0; i< v.size(); i++)
        {
            p = (Process) v.get(i);
            if (p.isRunning() == true)
            {
                // disabling the memory panel outlines
                assignSpace(p.getMemoryclass(), p.getPID(), g);
            }//if
        }//for
    }//draws Processes

    void mydrawProcesses(Graphics g)
    {
        g.setColor(new Color(0,0,0));
        g.fillRect(30, 30,20,20);
        g.setColor(new Color(0,0,0));
        g.drawRect(40, 40,60,60);

    }
    void assignSpace(int spaceMem, int identidad, Graphics g)
    {

        if (identidad == id_actual)
        {
            for (int i = 0; i < 5; i++)
            {
                // disabling the memory panel fills
                drawSpace(initial_values_array[i],fillercolor[i], colors[i], identidad, spaceMem, initial_values_array[activeIndex], g);

            }

            //if the identity changes at the beginning of the program
        }
        else
        {
            id_actual = identidad;
            activeIndex++;

            if (activeIndex >= 5)
                activeIndex = -0;

            if (spaceMem < 64)
            {
                changeColor2();
                colors[sentry] = fillercolorr2;
                page_values_array[sentry] = true;
                fillercolor[sentry] = spaceMem;
                sentry++;

                if (sentry >= 5) sentry = 0;

                if (double_values_array[sentry] == true)
                {
                    double_values_array[sentry] = false;
                    fillercolor[sentry] = 0;
                }
            }
            else
            {

                changeColor2();
                colors[sentry] = fillercolorr2;
                page_values_array[sentry] = true;
                fillercolor[sentry] = largePage;
                sentry++;

                if (sentry >= 5)
                    sentry = 0;

                colors[sentry] = fillercolorr2;
                page_values_array[sentry] = true;
                fillercolor[sentry] = spaceMem - 64;
                double_values_array[sentry] = true;
                sentry++;

                if (sentry >= 5)
                    sentry = 0;

            }

        }//else
    }//void
    void drawSpace(int initial_values_array, int largo, Color c, int processID, int space_required, int comodin, Graphics g)
    {

        int dX = 0;
        int dY = initial_values_array;
        int finishcondition = comodin + space_required;

        for (int i = 0; i < largo; i++)
        {

            g.setColor(c);
            g.fillRect(dX, dY,20,20);
            g.setColor(new Color(0,0,0));
            g.drawRect(dX, dY,20,20);
            dX = dX + 20;

            //carriage return
            if (dX > 300)
            {
                dX = 0;
                dY = dY + 20;
            }
        }

        String el_displaytext1 = "Process to execute: ID-" + processID;
        String el_displaytext2 = "Space required: " +  space_required;
        String el_displaytext3 = "Initial memory address: (0," +  comodin + ")";
        String el_displaytext4 = "Final memory address: " +  finishcondition;

        g.drawString(el_displaytext1, 60,450);
        g.drawString(el_displaytext2, 60,465);
        g.drawString(el_displaytext3, 60,480);
        g.drawString(el_displaytext4, 60,495);
    }

    //this is where we draw the grid
    void drawGrid(Graphics g)
    {

        counter = largePage;
        int dX = 0;
        int dY = 0;

        //first draw the grid
        for (int i = 0; i < 320 ; i++)
        {
            //populate the page

            if (counter == 0)
            {
                counter = largePage;
                changeColor();
            }
            g.setColor(fillercolorr);
            g.fillRect(dX, dY, 20,20);
            counter--;
            //----------------------

            //a simple square
            g.setColor(new Color(0,0,0));
            g.drawRect(dX, dY,20,20);
            dX = dX + 20;

            if (dX > 300)
            {
                dX = 0;
                dY = dY + 20;
            }
        }//for

    }
    //this is where we draw the mfqgrid
    void drawmfqGrid(Graphics g)
    {

        counter = largePage;
        int dX = 0;
        int dY = 0;

        Color redcolor = new Color(150,0,0);
        g.setColor(redcolor);
        // level1 queue
        // no text here
        g.setColor(fillercolorr);
        g.fillRect(0,0,500,500);
        g.drawRect(0,0,500,500);

        g.setColor(redcolor);
        // level 2 queue
        g.drawString("level2 queue", 10,150);
        g.setColor(fillercolorr);
        g.fillRect(100,100,20,20);
        g.drawRect(100,100,20,20);

        g.setColor(redcolor);
        // level 3 queue
        g.drawString("level3 queue", 10,200);
        g.setColor(fillercolorr);
        g.fillRect(100,100,20,20);
        g.drawRect(100,100,20,20);
        //first draw the grid


    }
    void changeColor()
    {

        if (original == true)
        {
            fillercolorr = new Color (160,160,160);
            original = false;
        }
        else
        {
            fillercolorr = new Color (180,180,180);
            original = true;
        }

    }

    void changeColor2()
    {
        fillercolorr2 = new Color(redcolor,greencolor,bluecolor);
        if (redcolor == 150)
        {
            greencolor = 150;
            redcolor = 0;
            bluecolor = 0;
        }

        else if (greencolor == 150)
        {
            greencolor = 0;
            redcolor = 0;
            bluecolor = 150;
        }

        else if (bluecolor == 150)
        {
            greencolor = 0;
            redcolor = 150;
            bluecolor = 0;
        }
    }

    public void update_process(Vector v2)
    {
        v = v2;
    }

} // --  Memoryclass
