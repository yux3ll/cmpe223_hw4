
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
public class Driver {
    //-----------------------------------------------------
    // Title: Driver
    // Author: Yüksel Çağlar Baypınar & Melisa Subaşı
    // ID: 43951623744 & 22829169256
    // Section: 02
    // Assignment: 4
    // Description: Driver class for the program that also has methods to read orders from the file, increment and decrement minutes by one from their
    // respective places and an inner class for couriers.
    //-----------------------------------------------

    public static void main(String[] args) throws FileNotFoundException {


        Scanner sc = new Scanner(System.in); // create a new Scanner object called sc

        System.out.println("Enter input filename:");

        String filePath = sc.nextLine(); // read the file path from the console
        Scanner input = new Scanner(new File(filePath)); // create a new Scanner object called input, and read the file from the file path

        System.out.println("Enter the maximum average waiting time:");
        final int MAX_WAIT_TIME = sc.nextInt(); // read the maximum average waiting time from the console
        int currentTime = 1; // create an integer called currentTime and assign 0 to it
        double averageWaitTime = 0; // create an integer called averageWaitTime and assign 0 to it
        int driverCount =1; // initial starting courier amount
        int numberOfOrders=input.nextInt(); // first line of the file contains size of array
        String firstLine = "\nMinimum number of couriers required: ";
        String secondLine = "Simulation with ";
        StringBuilder courierCombo= new StringBuilder();


        Heap queue = new Heap(numberOfOrders); // create a new HeapTreePriorityQueue object called queue and assign the first integer of the file to it's size

        while(input.hasNext()) { // fill heap array with the input file
            input.nextLine(); //skip end of line
            receiveOrder(input, queue, currentTime); // call the receiveOrder method
        }
        input.close(); // close the input file
        sc.close(); // close the scanner

        while(averageWaitTime == 0 || averageWaitTime>=MAX_WAIT_TIME){ // the crème de la crème loop, each iteration of this loop is a new simulation (with a new amount of drivers)
            currentTime=1; // used to reset the clock for each iteration of the outermost loop
            courierCombo = new StringBuilder(); // used to reset the string that contains the courier amount for each iteration of the outermost loop
            double totalWaitTime = 0; // create an integer called totalWaitTime and assign 0 to it, resets for each iteration of the outermost loop
            Heap clone = new Heap(queue); // create a new HeapTreePriorityQueue object called clone and assign the queue object to it, this is a literal duplication with a custom constructor
            courier[] amazonPrime = new courier[driverCount++]; // create a new courier array called amazonPrime and assign the driverCount to it's size, then increment the driverCount by one for the possible next simulation
            for(int i = 0; i<amazonPrime.length;i++){ // fill the courier array with courier objects
                amazonPrime[i] = new courier(i); // create a new courier object and assign and id to each element of the array
            }
            while (!clone.isEmpty()){ // the main loop of the simulation, each iteration of this loop is a minute, the loop ends when the queue is empty
                for (Driver.courier courier : amazonPrime) { // iterates through the couriers
                    if (courier.available) { // checks availability of couriers
                        clone.restoreHeap(currentTime); // restores the heap if the courier is available(this method is here to ensure when time passes, the queue is rebuilt partially to accommodate for the new time)
                        Heap.Node temp = clone.traverseHeap(currentTime); // create a new Node called temp and assign the return value of the traverseHeap method to it
                        if (temp != null) { // if the return value of the traverseHeap method is not null
                            courier.sendForDelivery(temp); // call the sendForDelivery method of the courier object
                            courierCombo.append("Courier ").append(courier.ID).append(" takes customer ").append(temp.getID()).append(" at minute ").append(currentTime).append(" (wait: ").append(temp.getWaitTime()).append(" mins)\n"); //keeps track of the current simulation for printing purposes
                            totalWaitTime += temp.getWaitTime(); // since the removed temp object will be erased from memory, we need to keep track of the wait time of the customer

                        }
                    }
                }
                aMinuteHasPassed(currentTime,amazonPrime,clone); // call the aMinuteHasPassed method
                currentTime++; // increment the currentTime by one
            }
            averageWaitTime=totalWaitTime/numberOfOrders; // calculates the average wait time for the outermost loop
        }

        DecimalFormat df = new DecimalFormat("#.00000");
        System.out.println(firstLine + " " + (driverCount-1)+"\n");
        System.out.println(secondLine + (driverCount-1) + " Couriers:\n");
        System.out.println(courierCombo + "\n");
        System.out.println("Average waiting time: " + averageWaitTime(averageWaitTime, df) + " minutes");
        // a whole load of printing and formatting to make the output look nice


     }

    static String averageWaitTime(double number, DecimalFormat df){ // method to format the average wait time since it depends on the value itself
        if (number % 1.0 != 0)
            return df.format(number);
        else
            return String.format("%.0f", number);
    }
     static void receiveOrder(Scanner input, Heap queue, int currentTime){ // method to read the orders from the file
        int id = input.nextInt(); // read the id from the file
        int year = input.nextInt(); // read the year from the file
        int timeOfOrder = input.nextInt(); // read the time of order from the file
        int serviceTime = input.nextInt(); // read the service time from the file
        queue.add(id, year, timeOfOrder, serviceTime, currentTime); // add the order to the queue
        }

     static void aMinuteHasPassed(int currentTime, courier[] fedEx, Heap heap){ // method to increment the minutes by one
         for (courier ex : fedEx) { // iterates through the couriers
             ex.decrementTime(); // call the decrementTime method of the courier object
         }
        heap.waitTime(currentTime); // call the waitTime method of the heap object
     }

     static class courier{
        boolean available;
        int availableAfter; // helps keep track of couriers during deliveries
        final int ID; // used to keep track of couriers

        public courier(int i){
            available = true;
            availableAfter = 0 ;
            ID = i;
        }

        public void sendForDelivery(Heap.Node node){ // method to assign a delivery
            available = false;
            availableAfter = node.getServiceTime();
        }
        public void decrementTime(){ // method to decrement the time
            if(!available & --availableAfter == 0){ // if the courier is not available and the availableAfter is 0, also decrements the availableAfter by one before checking
                available = true; // set the courier to available
            }
        }
     }
}
