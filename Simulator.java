import java.lang.System;

/*
 *      Clase Simulator -- This is the main class
 *      @ Author: Arunabh Das
 */

public class Simulator
{

    public static void main(String args[])
    {
        //check the arguments to see if we have to use FIFO or RR
        String useroption = "";
        String timequantum0 = "0";
        String timequantum1 = "0";
        String timequantum2 = "0";

        /*
        if (args.length == 1)
        	{
        		useroption = args[0];

        	}
        else if (args.length > 1)
        	{
        		useroption = args[0] + " " + args[1];
        		timequantumint0 = Integer.parseInt(timequantum0);
        		timequantumint1 = Integer.parseInt(timequantum1);
        		timequantumint2 = Integer.parseInt(timequantum2);
        		//in case the user makes an error
        	}
        else
        	{
        System.out.println( "Number of incorrect arguments" );
        System.out.println( "Correct Usage is as follows -" );
        System.out.println( "java Simulator FIFO" );
        System.out.println( "java Simulator Round Robin" );
        		System.out.println( "java Simulator FSS" );
        System.exit(0);
        	}
        */

        System.out.print("There are ");
        System.out.print(args.length);
        System.out.print(" command line arguments");
        System.out.println();

        if (args.length == 4)
        {
            useroption = args[0];
            timequantum0 = args[1];
            timequantum1 = args[2];
            timequantum2 = args[3];
        }
        System.out.println("Poisson parameter : " + timequantum0);
        System.out.println("CPU lower bound : " + timequantum1);
        System.out.println("CPU upper bound " + timequantum2);
        //constructor for userinterface
        Userinterface fr = new Userinterface(useroption, Integer.parseInt(timequantum0), Integer.parseInt(timequantum1), Integer.parseInt(timequantum2));
    }

}
