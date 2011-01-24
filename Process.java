import java.io.*;

/*
 *      Class Process -- Loads each process in scheduler
 *      @ Author: Arunabh Das
 */

public class Process
{
    int PID  = 0; //process ID
    int queue_level = 0;
    static int nextPID  =  0; //add next process in tail
    int time_remaining = 0; //time used by the process [0 - 100]
    int time_remaining_initial = 0;
    int temp_initial = 0; //initialize time
    int resource = 0;
    int memory_value = (int) (Math.random() * 100) + 1;;
    String name = "";
    int time_spent_running = 0;
    //next we set the three statuses
    boolean ready = true; //to check if a process is in ready queue or not
    boolean done = false;  //to check if a process is terminated
    boolean running = false;   //to check if a process is running
    //to check if a process is blocked or not
    boolean blocked = false;

    //boolean values so that a process cannot take more than 2 resources
    boolean r1 = false;
    boolean r2 = false;
    boolean r3 = false;
    boolean r4 = false;
    boolean r5 = false;
    // Default constructor - it generates a full process and the values
    Process()
    {
        nextPID++;
        PID = nextPID;
        // we shall comment out the following line so we can make the cpu burst configurable
        //time_remaining = (int) (Math.random() * 90 ) + 10;
        time_remaining = (int) (Math.random() * 90 ) + 10;
        time_remaining_initial = time_remaining;
        temp_initial = time_remaining; //to start with, we initialize everything
        /* this is where we introduce the logic to figure out which queue the process goes into*/

        //queue_level = PID % 3;

    }

    //constructor in case the info comes from file
    //@param name, time
    Process(String b, int d)
    {
        nextPID++;
        PID = nextPID;
        time_remaining = d; //time of life
        temp_initial = time_remaining;
        name = b;

    }
    //incident methods in the status of the process
    //in status running
    public void running()
    {
        running = true;
        ready = false;
        blocked = false;
        time_remaining--;//we decrement the remaining time
        //if time finishes, then the process is finished
        time_spent_running++;
        queue_level = PID;
        if ( time_remaining == 0 )
        {
            done = true;
            running = false;
            ready = false;
            time_remaining = 0;
        }


    }

    //ready status
    public  void listo()
    {
        ready = true;
        running = false;
        blocked = false;
        //time_spent_waiting++;
    }

    public void blocked()
    {
        blocked = true;
        running = false;
        ready = false;
        //time_spent_waiting++;
    }

    //Methods for state of processes
    // that are being executed by the CPU
    public boolean isRunning()
    {
        return running;
    }

    //is the process finished or not
    public boolean isDone()
    {
        return done;
    }

    //is the process ready or not
    public boolean isReady()
    {
        return ready;
    }

    //is the process blocked or not
    public boolean isBlocked()
    {
        return blocked;
    }

    //get the value of the cpu burst remaining
    public int getTWait()
    {
        return time_remaining;
    }
    //get the value of the cpu burst (initial)
    public int getTWaitInitial()
    {
        return time_remaining_initial;
    }

    public int getTimeSpentRunning()
    {
        return time_spent_running;
    }
    // get the value of the queue for this process
    public int getQueueLevel()
    {
        return queue_level;
    }


    //get the value of the lifetime of the process
    public int getTInitial()
    {
        return temp_initial;
    }

    //get the value of the PID
    public int getPID()
    {
        return PID;
    }

    public String getName()
    {
        return name;
    }

    public String getState()
    {

        String status = "";
        if (blocked == true)
            //status = "BLOCK(" + getResource() +")";
            status = "BLOCK";
        if (running == true)
            status = "_RNNG";
        if (done == true)
            status = "_TERM";
        if (ready == true)
            status = "READY";
        return status;
    }

    public void setResource(int rec)
    {

        resource = rec;
    }

    public void generaResource()
    {

        int probabilidad = (int) (Math.random() * 5) + 1;
        setResource(probabilidad);
    }

    public int getResource()
    {
        return resource;
    }

    //get memory_value in KB
    public int getMemoryclass()
    {

        return memory_value;
    }

    public void changeBool(int i)
    {
        switch (i)
        {
        case 1 :
            if ( r1 == false)
                r1 = true;
            else
                r1 = false;
            break;
        case 2 :
            if ( r2 == false)
                r2 = true;
            else
                r2 = false;
            break;
        case 3 :
            if ( r3 == false)
                r3 = true;
            else
                r3 = false;
            break;
        case 4 :
            if ( r4 == false)
                r4 = true;
            else
                r4 = false;
            break;
        case 5 :
            if ( r5 == false)
                r5 = true;
            else
                r5 = false;
            break;
        }
    }

    public boolean getBool(int i)
    {

        boolean recu = false;

        switch (i)
        {
        case 1 :
            recu = r1;
            break;
        case 2 :
            recu = r2;
            break;
        case 3 :
            recu = r3;
            break;
        case 4 :
            recu = r4;
            break;
        case 5 :
            recu = r5;
            break;
        }
        return recu;
    }


} //--process
