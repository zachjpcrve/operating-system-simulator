Scheduling is an important and a central tool in an operating system. Its fundamental function is to manage sharing of computer resources by a number of processes. A CPU is one of the primary computer resources and the CPU Scheduler manages its allocation. A process can be in five states. It is said to be running in the running state if it is using the CPU. A process is referred to be in a ready state if it can make use of the CPU if it is available. A blocked process is one that is in the waiting state if it is waiting for some event to happen, for instance an I/O completion event, before it can further proceed (or change to the running state). The scheduler makes use of a scheduling algorithm to decide which process from the ready queue should run first and for how long.

Our simulator implements FSS, RR and FIFO algorithms.

Fair-share scheduling is a scheduling strategy for computer operating systems in which the CPU usage is equally distributed among system users or groups, as opposed to equal distribution among processes.
For example, if four users (A,B,C,D) are concurrently executing one process each, the scheduler will logically divide the available CPU cycles such that each user gets 25% of the whole (100% / 4 = 25%). If user B starts a second process, each user will still receive 25% of the total cycles, but both of user B's processes will now use 12.5%. On the other hand, if a new user starts a process on the system, the scheduler will reapportion the available CPU cycles such that each user gets 20% of the whole (100% / 5 = 20%).
Another layer of abstraction allows us to partition users into groups, and apply the fair share algorithm to the groups as well. In this case, the available CPU cycles are divided first among the groups, then among the users within the groups, and then among the processes for that user. For example, if there are three groups (1,2,3) containing three, two, and four users respectively, the available CPU cycles will be distributed as follows:
•	100% / 3 groups = 33.3% per group
•	Group 1: (33.3% / 3 users) = 11.1% per user
•	Group 2: (33.3% / 2 users) = 16.7% per user
•	Group 3: (33.3% / 4 users) = 8.3% per user
One common method of logically implementing the fair-share scheduling strategy is to recursively apply the round-robin-strategy at each level of abstraction (processes, users, groups, etc.) The time quantum required by round-robin is arbitrary, as any equal division of time will produce the same results.


Compiling the Simulator -
There are 5 main files in our simulator -
1) Userinterface.java - This implements the GUI for the simulator
2) Process.java - This contains the class definition for the process
3) Actions.java - This contains the definitions for the interaction between the user and the GUI
4) Simulator.java - This is the main class
5) Poisson.java - This contains the class definition for the Poisson generator for generating workload

Compiling - The Fair Share Scheduling simulator can be compiled by using javac with the Xlint:deprecated flag, like so -

javac -Xlint:deprecated Userinterface.java Process.java Actions.java Simulator.java Poisson.java

Alternatively, you can import it in NetBeans 1.6 or higher and it will build it automatically.