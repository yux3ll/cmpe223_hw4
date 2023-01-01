
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

        System.out.println("Enter input filename:");
        Scanner sc = new Scanner(System.in); // create a new Scanner object called sc
        String filePath = sc.nextLine(); // read the file path from the console
        Scanner input = new Scanner(new File(filePath)); // create a new Scanner object called input, and read the file from the file path
        System.out.println("Enter the maximum average waiting time:");
        final int MAX_WAIT_TIME = sc.nextInt(); // read the maximum average waiting time from the console



     }
}
