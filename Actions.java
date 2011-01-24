import java.util.*;
import java.io.*;
import java.text.*;
import java.lang.System;

/*
 *  Contains the processes invoked from the ActionsFrame interface
 *  @ Author: Arunabh Das
 */

public class Actions
{

    //declare constants for type of simulation and number of processes
    public static final int NUMPROCESSES  = 10;
    public static final int FIFO       = 1;   //FIFO
    public static final int ROUNDROBIN = 2;   // round - robin


    public static final int blocking_random = 3; // random blocking
    public static final int blocking_banker = 4; // banked blocking
    public static final int FSS = 5; // mutli-level feedback queue

    public static final int max_resource = 3;
    //5 resources
    int resource_1 = max_resource;
    int resource_2 = max_resource;
    int resource_3 = max_resource;
    int resource_4 = max_resource;
    int resource_5 = max_resource;

    //vector of processes - workload generate generates seven random processes
    Vector allProcesos   = new Vector(NUMPROCESSES); //list of all processes
    int currentTime=0;  //time elapsed
    int quantum=10;   //time quantum
    int quantum0 = 0; // time quantum for level 0
    int quantum1 = 0; // time quantum for level 1
    int quantum2 = 0; // time quantum for level 2
    int quantum_elapsed = quantum;  //initialize counter for time quantum (decrements)
    int algorithm = this.FIFO; //algorithm used
    int blocking_value = this.blocking_random;
    int activeIndex = 0;  //vector index

    /*constructor that generates processarea random we use
    	to test and we do not want to erase it because it serves
    	as area when we do not want to add processarea one by
    one */
    Actions()
    {

        Process p;
        for (int i=0; i < NUMPROCESSES; i++)
        {
            p = new Process();
            allProcesos.add(p);
        }

    }//--Actions


    //constructor that reads from the command line
    Actions(String filename, int timeq0, int timeq1, int timeq2)
    {


        if (filename.equals("FIFO"))
        {
            System.out.println( "Executing simulator for FIFO..." );
            System.out.println( filename);
            algorithm = this.FIFO;
        }
        else if (filename.equals("Round Robin"))
        {
            System.out.println( "Executing simulator for ROUNDROBIN..." );
            System.out.println(filename);
            algorithm = this.ROUNDROBIN;
        }
        else if (filename.equals("FSS"))
        {

            System.out.println( "Executing simulator for Fair Share Scheduling..." );
            System.out.println(filename);
            System.out.println("Poisson parameter : " + timeq0);
            System.out.println("CPU lower bound  : " + timeq1);
            System.out.println("CPU upper bound  : " + timeq2);

            quantum0 = timeq0;
            quantum1 = timeq1;
            quantum2 = timeq2;


            algorithm = this.FSS;

        }
        else
        {
            System.out.println("Incorrect usages.");
            System.out.println( "Correct usage is as follows" );
            System.out.println( "java Simulator FIFO <numprocesses> <cpu_lower> <cpu_upper> " );
            System.out.println( "java Simulator Round Robin <numprocesses> <cpu_lower> <cpu_upper>" );
            System.out.println( "java Simulator FSS <numprocesses> <cpu_lower> <cpu_upper> " );
            System.exit(0);
        }
        String archivefilename = filename + ".txt";
        File file = new File(archivefilename);
        file = file.getAbsoluteFile();
        Process proc = null;
        String s = null;
        int d=0;
        String nom = "";
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(file));
            s = input.readLine();
            int num_processarea = Integer.parseInt(s);
            for (int i = 0; i < num_processarea; i++)
            {
                s = input.readLine();
                StringTokenizer st = new StringTokenizer(s, " \n\t\r,", false);
                st.nextToken();
                nom = st.nextToken();
                d = Integer.parseInt(st.nextToken());
                proc = new Process(nom,d);
                allProcesos.add(proc);
            }//for
            //if it is Round Robin, obtain the time quantum
            s = input.readLine();
            if (s != null)
            {
                int rr = Integer.parseInt (s);
                this.setQuantum(rr);
            }//if
        } //try
        catch (FileNotFoundException fnfe) {}
        catch (IOException ioe) {}
        catch (NumberFormatException nfe) {}
    }//Actions
    // it reads the internalpanel data from the textarea and adds it to the processarea
    void add_process(String thickness_processbarsso)
    {

        Process proc = null;
        String s = null;
        int d=0;
        String nom = null;

        s = thickness_processbarsso;
        StringTokenizer st = new StringTokenizer(s, " \n\t\r,", false);

        int num_processarea = Integer.parseInt(st.nextToken());

        for (int i = 0; i < num_processarea; i++)
        {
            st.nextToken();
            nom = st.nextToken();
            d = Integer.parseInt(st.nextToken());

            proc = new Process(nom,d);    //create new processarea
            allProcesos.add(proc);        //add it to the vector with all the processareas


        }//for
        //if it is Round Robin, obtain the time quantum
        if (st.hasMoreTokens() == true)
        {
            int rr = Integer.parseInt (st.nextToken());
            this.setQuantum(rr);
            algorithm = this.ROUNDROBIN;
        }//if2

    }//Actions

    //reads data from file
    Actions(File filename)
    {
        Process proc = null;
        String s = null;
        int d=0;
        String nom = "";

        try
        {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            s = input.readLine();
            int num_processarea = Integer.parseInt(s);

            for (int i = 0; i < num_processarea; i++)
            {
                s = input.readLine();
                StringTokenizer st = new StringTokenizer(s, " \n\t\r,", false);
                st.nextToken();
                nom = st.nextToken();
                d = Integer.parseInt(st.nextToken());

                proc = new Process(nom,d);
                allProcesos.add(proc);
            }
            //if this is Round Robin, obtain the time quantum
            s = input.readLine();
            if (s != null)
            {

                int rr = Integer.parseInt (s);
                this.setQuantum(rr);
                algorithm = this.ROUNDROBIN;
            }
        }
        catch (FileNotFoundException fnfe) {}
        catch (IOException ioe) {}
        //when archives FIFO are read, it tries to read a line
        //but we ignore the exception that it generates
        catch (NumberFormatException nfe) {}
    }

    //we release the resource that it has occupied
    void freeResource(Process p)
    {
        for (int i = 1; i <= 5; i++)
        {

            //if it finishes without releasing the resource, it releases it
            if (p.getBool(i) == true)
            {
                p.changeBool(i);
                incrementaResource(i);
            }
        }
    }


    //method for FIFO
    void RunFIFO()
    {
        Process p = null;
        try
        {
            p = (Process) allProcesos.get(activeIndex);
            if (p.isDone() == true)
            {
                freeResource(p);
                activeIndex++;
                //to make sure that it is not outside the vector
                if ( activeIndex == allProcesos.size() )
                {
                    activeIndex = 0;
                }

            }
            else if ( p.isBlocked() == true )
            {

                unblockmethod(p);

            }
            else
            {
                Apply_Blocking(activeIndex);
            }
        }
        catch ( NullPointerException e)
        {
        }
    }

    //to unblock the processarea
    void unblockmethod(Process p)
    {

        if (blocking_value == blocking_random)
        {

            int probabilidad = (int) (Math.random() * 100);
            //the probability is 10% of which it is unblocked
            if (probabilidad <= 10)
            {
                p.blocked = false;
            }
        }
        else if (blocking_value == blocking_banker)
        {
            //to check if it can be unblocked in banker
            int probabilidad2 = (int) (Math.random() * 100);
            if (probabilidad2 <= 10)
            {
                switch (p.getResource())
                {
                case 0 :
                    break;
                case 1 :
                    p.blocked = false;
                    resource_1++;
                    p.changeBool(1);
                    break;
                case 2 :
                    p.blocked = false;
                    resource_2++;
                    p.changeBool(2);
                    break;
                case 3 :
                    p.blocked = false;
                    resource_3++;
                    p.changeBool(3);
                    break;
                case 4 :
                    p.blocked = false;
                    resource_4++;
                    p.changeBool(4);
                    break;
                case 5 :
                    p.blocked = false;
                    resource_5++;
                    p.changeBool(5);
                    break;
                }//switch
            }
            else
            {
                //if it is not freed, then it goes to the following process in the tail (vector)
                activeIndex++;
                if ( activeIndex == allProcesos.size() )
                {
                    activeIndex = 0;
                }
            }//else
        }//else if
    }//unblockmethod

    //Method for  Round Robin
    void RunRoundRobin()
    {
        Process p = null;
        try
        {
            p = (Process) allProcesos.get(activeIndex);
            if (quantum_elapsed == 0)
            {
                activeIndex++;
                quantum_elapsed = quantum;
                p.listo();
                //this is to make sure that we do not leave the vector
                if ( activeIndex == allProcesos.size() )
                {
                    activeIndex = 0;
                }

            }
            else if ( p.isDone() == true )
            {
                freeResource(p);
                activeIndex++;
                quantum_elapsed = quantum;
                //this is to make sure that we do not leave the vector
                if ( activeIndex == allProcesos.size() )
                {
                    activeIndex = 0;
                }
            }
            else if (p.isBlocked() == true )
            {
                unblockmethod(p);

            }
            else
            {
                Apply_Blocking(activeIndex);
            }

        }
        catch ( NullPointerException e)
        {

        }

    }
    //Method for  FSS
    void RunFSS()
    {
        Process p = null;
        try
        {
            p = (Process) allProcesos.get(activeIndex);
            if (quantum_elapsed == 0)
            {
                activeIndex++;
                quantum_elapsed = quantum;
                p.listo();
                //this is to make sure that we do not leave the vector
                if ( activeIndex == allProcesos.size() )
                {
                    activeIndex = 0;
                }

            }
            else if ( p.isDone() == true )
            {
                freeResource(p);
                activeIndex++;
                quantum_elapsed = quantum;
                //this is to make sure that we do not leave the vector
                if ( activeIndex == allProcesos.size() )
                {
                    activeIndex = 0;
                }
            }
            else if (p.isBlocked() == true )
            {
                unblockmethod(p);

            }
            else
            {
                Apply_Blocking(activeIndex);
            }

        }
        catch ( NullPointerException e)
        {

        }

    }

    void RunBlockingRandom(int index)
    {

        Process p = null;
        p = (Process) allProcesos.get(index);

        //in each execution, find the probability that exists that it is blocked
        int probabilidad = (int) (Math.random() * 1000);

        //the probability is of [.008 * time_initial]%  of which it is blocked
        if (probabilidad <= 10)
        {
            p.blocked();
            p.generaResource();
            activeIndex++;

            if ( activeIndex == allProcesos.size() )
                activeIndex = 0;

        }
        else
        {
            p.running();
            quantum_elapsed--;
        }
    }

    void RunBlockingBanker(int index)
    {
        Process p = null;
        p = (Process) allProcesos.get(index);
        int el_resource = 0;

        int probabilidad = (int) (Math.random() * 1000);
        if (probabilidad <= 200)
        {
            p.generaResource();
            el_resource = p.getResource();

            //to check the resources
            switch (el_resource)
            {
            case 1 :
                if (resource_1 > 0 && p.getBool(1) == false)
                {
                    resource_1--;
                    p.changeBool(1);
                }
                else if (p.getBool(1) == true)
                {
                    //don't do anything
                }
                else
                {
                    p.blocked();
                    activeIndex++;
                    if ( activeIndex == allProcesos.size() )
                        activeIndex = 0;
                }
                break;
                //------------------------
            case 2 :
                if (resource_2 > 0 && p.getBool(2) == false)
                {
                    resource_2--;
                    p.changeBool(2);
                }
                else if (p.getBool(2) == true)
                {
                    //don't do anything
                }
                else
                {
                    p.blocked();
                    activeIndex++;
                    if ( activeIndex == allProcesos.size() )
                        activeIndex = 0;
                }
                break;
                //------------------------
            case 3 :
                if (resource_3 > 0 && p.getBool(3) == false)
                {
                    resource_3--;
                    p.changeBool(3);
                }
                else if (p.getBool(3) == true)
                {
                    //don't do anything
                }
                else
                {
                    p.blocked();
                    activeIndex++;
                    if ( activeIndex == allProcesos.size() )
                        activeIndex = 0;
                }
                break;
                //------------------------
            case 4 :
                if (resource_4 > 0 && p.getBool(4) == false)
                {
                    resource_4--;
                    p.changeBool(4);
                }
                else if (p.getBool(4) == true)
                {
                    //don't do anything
                }
                else
                {
                    p.blocked();
                    activeIndex++;
                    if ( activeIndex == allProcesos.size() )
                        activeIndex = 0;
                }
                break;
                //------------------------
            case 5 :
                if (resource_5 > 0 && p.getBool(5) == false)
                {
                    resource_5--;
                    p.changeBool(5);
                }
                else if (p.getBool(5) == true)
                {
                    //don't do anything
                }
                else
                {
                    p.blocked();
                    activeIndex++;
                    if ( activeIndex == allProcesos.size() )
                        activeIndex = 0;
                }
                break;
            } //switch
        }
        else
        {
            //if there are no matches
            // then it runs
            p = (Process) allProcesos.get(activeIndex);
            p.running();
            quantum_elapsed--;
        }
    }

    //everything happens in a cycle
    public void singleCycle()
    {

        Apply_Method();
        currentTime++;

    }

    // we select the method and we execute one while the other one is sent to the tail of ready queue
    void Apply_Method()
    {
        switch ( algorithm )
        {
        case FIFO :
            RunFIFO();
            break;
        case ROUNDROBIN :
            RunRoundRobin();
            break;
        case FSS :
            RunFSS();
            break;
        }
    }

    // select blocking
    void Apply_Blocking(int index)
    {
        switch ( blocking_value )
        {
        case blocking_random :
            RunBlockingRandom(index);
            break;
        case blocking_banker :
            RunBlockingBanker(index);
            break;

        }
    }

    //accessors and modifiers


    public int getAlgorithm()
    {
        return algorithm;    //get the value of the method
    }
    public void setAlgorithm(int algo)
    {
        algorithm = algo;    //set the value of the method
    }

    public int getQuantum0()
    {
        return quantum0;    // return the value of time quantum for level 0
    }
    public int getQuantum1()
    {
        return quantum1;    // return the value of time quantum for level 1
    }
    public int getQuantum2()
    {
        return quantum2;    // return the value of time quantum for level 2
    }

    public int getBlocking()
    {
        return blocking_value;    //get the value of the method
    }
    public void setBlocking(int blo)
    {
        blocking_value = blo;    //set the value of the method
    }

    public int getTotalTime()
    {
        return currentTime;    //get the total time for which the simulation has run
    }

    public int getQuantum()
    {
        return quantum;    //get the value of the quantum
    }
	  public void setQuantum(int  v)
    {
        this.quantum = v;    //set the value of the quantum
    }
    public void setQuantum0(int  v0)
    {
        this.quantum = v0;    //set the value of the quantum
    }
    public void setQuantum1(int  v1)
    {
        this.quantum = v1;    //set the value of the quantum
    }
    public void setQuantum2(int  v2)
    {
        this.quantum = v2;    //set the value of the quantum
    }

    public Vector getProcesses()
    {
        return allProcesos;    //get all the processes in the vector
    }
    public int getResource(int i)
    {
        int resultado = 0;
        switch (i)
        {
        case 1:
            resultado = resource_1;
            break;
        case 2:
            resultado = resource_2;
            break;
        case 3:
            resultado = resource_3;
            break;
        case 4:
            resultado = resource_4;
            break;
        case 5:
            resultado = resource_5;
            break;
        }
        return resultado;
    }

    public void incrementaResource(int i)
    {

        switch (i)
        {
        case 1:
            resource_1++ ;
            break;
        case 2:
            resource_2++ ;
            break;
        case 3:
            resource_3++ ;
            break;
        case 4:
            resource_4++ ;
            break;
        case 5:
            resource_5++ ;
            break;
        }
    }

}// -- class Actions
