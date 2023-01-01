
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Driver {
    //-----------------------------------------------------
    // Title: Driver
    // Author: Yüksel Çağlar Baypınar & Melisa Subaşı
    // ID: 43951623744 & 22829169256
    // Section: 02
    // Assignment: 4
    // Description:
    //-----------------------------------------------

    public static void main(String[] args) throws FileNotFoundException {


        Scanner sc = new Scanner(System.in); // create a new Scanner object called sc

        System.out.println("Enter input filename:");

        String filePath = sc.nextLine(); // read the file path from the console
        Scanner input = new Scanner(new File(filePath)); // create a new Scanner object called input, and read the file from the file path

        System.out.println("Enter the maximum average waiting time:");
        final int MAX_WAIT_TIME = sc.nextInt(); // read the maximum average waiting time from the console
        int currentTime = 0; // create an integer called currentTime and assign 0 to it
        int averageWaitTime = 0; // create an integer called averageWaitTime and assign 0 to it
        int driverCount =1;


        Heap queue = new Heap(input.nextInt()); // create a new HeapTreePriorityQueue object called queue and assign the first integer of the file to it's size

        while(input.hasNext()) { // fill heap array with the input file
            input.nextLine(); //skip end of line
            recieveOrder(input, queue); // call the receiveOrder method
        }
        input.close(); // close the input file
        sc.close(); // close the scanner

        while(averageWaitTime>0 & averageWaitTime<MAX_WAIT_TIME){
            Heap clone = new Heap(queue);
            courier[] amazonPrime = new courier[driverCount];
            int availableDrivers = 0;
            while (!clone.isEmpty()){
                for (int i = 0; i<amazonPrime.length;i++){
                  if(amazonPrime[i].available) availableDrivers++;
                }
                if(availableDrivers>0){



                }

            }


        }


     }
    // check if there are couriers available, increment int i once for every available courier
    // if i>0, traverse heap with time of order vs current time constraint and remove only the highest priority customer if there are any customers at all, if not inc current time, decrement courier times, break loop, do this until i reaches 0, decrement i for every customer removed, print each assigned courier
    // if i=0, increment wait time for all customers in heap with order times less than or equal to current time, decrement from couriers(decrement also switches availability)
    //loop back to beginning until heap is empty
    //


     static int recieveOrder(Scanner input, Heap queue){
        int id = input.nextInt(); // read the id from the file
        int year = input.nextInt(); // read the year from the file
        int timeOfOrder = input.nextInt(); // read the time of order from the file
        int serviceTime = input.nextInt(); // read the service time from the file
        queue.add(id, year, timeOfOrder, serviceTime); // add the order to the queue
        return timeOfOrder; // return the time of order
     }


     class courier{
        boolean available;
        int avaliableAfter;

        public courier(){
            available = true;
            avaliableAfter = 0 ;
        }

        public void decrementTime(){
            if(!available & --avaliableAfter == 0){
                available = true;
            }
        }
     }
}
