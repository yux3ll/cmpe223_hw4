
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
        int currentTime = 1; // create an integer called currentTime and assign 0 to it
        double averageWaitTime = 0; // create an integer called averageWaitTime and assign 0 to it
        int driverCount =1;
        int numberOfOrders=input.nextInt();
        String firstLine = "Minimum number of couriers required: ";
        String secondLine = "Simulation with ";
        String courierCombo= "";


        Heap queue = new Heap(numberOfOrders); // create a new HeapTreePriorityQueue object called queue and assign the first integer of the file to it's size

        while(input.hasNext()) { // fill heap array with the input file
            input.nextLine(); //skip end of line
            recieveOrder(input, queue, currentTime); // call the receiveOrder method
        }
        input.close(); // close the input file
        sc.close(); // close the scanner

        while(averageWaitTime == 0 || averageWaitTime>=MAX_WAIT_TIME){
            currentTime=1;
            courierCombo ="";
            Heap clone = new Heap(queue);
            courier[] amazonPrime = new courier[driverCount++];
            for(int i = 0; i<amazonPrime.length;i++){
                amazonPrime[i] = new courier(i);
            }
            double totalWaitTime = 0;
            while (!clone.isEmpty()){
                for (int i = 0; i<amazonPrime.length;i++){
                  if(amazonPrime[i].available){
                      clone.restoreHeap(currentTime);
                      Heap.Node temp = clone.traverseHeap(currentTime);
                      if(temp!=null) {
                          amazonPrime[i].sendForDelivery(temp);
                          courierCombo += "Courier " + amazonPrime[i].ID + " takes customer " + temp.getID() + " at minute " + currentTime + " (wait: " + temp.getWaitTime() + " mins)\n";
                          totalWaitTime+=temp.getWaitTime();

                      }
                  }
                }
                aMinuteHasPassed(currentTime,amazonPrime,clone);
                currentTime++;
            }
            averageWaitTime=totalWaitTime/numberOfOrders;
        }
        System.out.println(firstLine + " " + (driverCount-1)+"\n");
        System.out.println(secondLine + (driverCount-1) + " Couriers:\n");
        System.out.println(courierCombo + "\n");
        System.out.println("Average waiting time: " + averageWaitTime + " minutes");
        // if run out of drivers, time to increment, same for customers


     }
    // check if there are couriers available, increment int i once for every available courier //
    // if i>0, traverse heap with time of order vs current time constraint and remove only the highest priority customer if there are any customers at all, if not inc current time, decrement courier times, break loop, do this until i reaches 0, decrement i for every customer removed, print each assigned courier
    // if i=0, increment wait time for all customers in heap with order times less than or equal to current time, decrement from couriers(decrement also switches availability)
    //loop back to beginning until heap is empty
    //


     static void recieveOrder(Scanner input, Heap queue, int currentTime){
        int id = input.nextInt(); // read the id from the file
        int year = input.nextInt(); // read the year from the file
        int timeOfOrder = input.nextInt(); // read the time of order from the file
        int serviceTime = input.nextInt(); // read the service time from the file
        queue.add(id, year, timeOfOrder, serviceTime, currentTime); // add the order to the queue
        }

     static void aMinuteHasPassed(int currentTime, courier[] fedEx, Heap heap){
        for (int i = 0; i<fedEx.length;i++){
            fedEx[i].decrementTime();
        }
        heap.waitTime(currentTime);
     }

     static class courier{
        boolean available;
        int avaliableAfter;

        final int ID;

        public courier(int i){
            available = true;
            avaliableAfter = 0 ;
            ID = i;
        }



        public void sendForDelivery(Heap.Node node){
            available = false;
            avaliableAfter = node.getServiceTime();
        }
        public void decrementTime(){
            if(!available & --avaliableAfter == 0){
                available = true;
            }
        }
     }
}
