public class Heap {
    //-----------------------------------------------------
    // Title: Heap
    // Author: Yüksel Çağlar Baypınar & Melisa Subaşı
    // ID: 43951623744 & 22829169256
    // Section: 02
    // Assignment: 4
    // Description: A class that implements the PriorityQueue interface using a max heap Node array, has additional methods for traversing in a specific matter
    // and a custom compareTo method that creates the dynamic nature of the time based queue.
    //-----------------------------------------------

    private final Node[] heap; // the heap tree
    private int size; // the number of elements in the heap tree

    public Heap(int capacity) { // constructor
        heap = new Node[capacity]; // create a new Node array with the given capacity
        size = 0; // set the size to 0
    }

    public Heap(Heap clone){ // custom constructor for cloning(duplication)
        this.size = clone.size;
        this.heap = new Node[clone.heap.length];
        for(int i = 0; i<clone.heap.length;i++){
            if(clone.heap[i]!=null) {
                this.heap[i] = new Node(clone.heap[i].ID, clone.heap[i].year, clone.heap[i].timeOfOrder, clone.heap[i].serviceTime);
            }
        }
    }
    public void add(int id, int year, int timeOfOrder, int serviceTime, int currentTime) { // add a new Node to the heap tree, public method
        Node node = new Node(id, year, timeOfOrder, serviceTime); // create a new Node object called temp
        add(node, currentTime); // call the add method with a Node object
    }
    private void add(Node node, int currentTime){ // add a new Node to the heap tree, private method, additional parameter is used to augment the compareTO methods for time based dynamic queue
        if (size == heap.length) { // if the size of the heap tree is equal to the length of the heap tree
            System.out.println("Heap is full"); // print "Heap is full"
        }
        heap[size] = node; // add the new Node object to the heap tree
        size++; // increase the size by 1
        bubbleUp(currentTime); // call the bubbleUp method
    }

   public Node remove(int currentTime) { // remove the root Node from the heap tree, public method with an additional parameter for time based dynamic queue
        if (size == 0) // if the size of the heap tree is 0
            return null; // return null
        Node temp = heap[0]; // create a new Node object called temp and set it to the first element of the heap tree
        heap[0] = heap[size - 1]; // set the first element of the heap tree to the last element of the heap tree
        heap[size - 1] = null; // set the last element of the heap tree to null
        size--; // decrease the size by 1
        bubbleDown(currentTime); // call the bubbleDown method
        return temp; // return the temp object
    }

    public void bubbleUp(int currentTime) {
        int index = size - 1; // create an integer called index and assign the size of the heap tree minus 1 to it
        while (hasParent(index) && parent(index).compareTo(heap[index],currentTime) < 0) { // while the parent of the index is not null and the parent of the index is less than the index
            swap(index, parentIndex(index)); // swap the index and the parent index
            index = parentIndex(index); // assign the parent index to the index
        }
    }

    public void bubbleDown(int currentTime){
        int index = 0; // create an integer called index and assign 0 to it
        while(hasLeftChild(index)){ // while the left child of the index is not null
            int largerChildIndex = leftChildIndex(index); // create an integer called largerChildIndex and assign the left child index of the index to it
            if(hasRightChild(index) && rightChild(index).compareTo(leftChild(index),currentTime) > 0){ // if the right child of the index is not null and the right child of the index is greater than the left child of the index
                largerChildIndex = rightChildIndex(index); // assign the right child index of the index to the largerChildIndex
            }
            if(heap[index].compareTo(heap[largerChildIndex],currentTime) > 0){ // if the index is greater than the largerChildIndex
                break; // break the loop
            }else{ // otherwise
                swap(index, largerChildIndex); // swap the index and the largerChildIndex
            }
            index = largerChildIndex; // assign the largerChildIndex to the index
        }
    }

    public void swap(int index1, int index2) { // swap two elements of the heap tree
        Node temp = heap[index1]; // create a new Node object called temp and assign the element at the index1 to it
        heap[index1] = heap[index2]; // assign the element at the index2 to the element at the index1
        heap[index2] = temp; // assign the temp to the element at the index2
    }

    public boolean hasParent(int index) { // check if the parent of the index is not null
        return parentIndex(index) >= 0; // return true if the parent index of the index is greater than or equal to 0, otherwise return false
    }

    public boolean hasLeftChild(int index) { // check if the left child of the index is not null
        return leftChildIndex(index) < size; // return true if the left child index of the index is less than the size of the heap tree, otherwise return false
    }

    public boolean hasRightChild(int index) { // check if the right child of the index is not null
        return rightChildIndex(index) < size; // return true if the right child index of the index is less than the size of the heap tree, otherwise return false
    }

    public int parentIndex(int index) { // return the parent index of the index
        return (index - 1) / 2; // return the parent index of the index
    }

    public int leftChildIndex(int index) { // return the left child index of the index
        return index * 2 + 1; // return the left child index of the index
    }

    public int rightChildIndex(int index) { // return the right child index of the index
        return index * 2 + 2; // return the right child index of the index
    }

    public Node parent(int index) { // return the parent of the index
        return heap[parentIndex(index)]; // return the parent of the index
    }

    public Node leftChild(int index) { // return the left child of the index
        return heap[leftChildIndex(index)]; // return the left child of the index
    }
    public Node rightChild(int index) { // return the right child of the index
        return heap[rightChildIndex(index)]; // return the right child of the index
    }
    public boolean isEmpty() { // check if the heap tree is empty
        return size == 0; // return true if the size of the heap tree is equal to 0, otherwise return false
    }

    public void restoreHeap(int currentTime){ // restore the heap tree, public method with an additional parameter for time based dynamic queue, it raises to the top the Nodes that actually exist within given time period
        for(int i = size/2 - 1; i >= 0; i--){ // for loop that starts from the middle of the heap tree and goes to the first element
            bubbleDown(i, currentTime); // call the bubbleDown method with an additional parameter for time based dynamic queue
        }
    }

    public void bubbleDown(int index, int currentTime){ // bubbleDown method with an index parameter for the restoreHeap method
        while(hasLeftChild(index)){ // while the left child of the index is not null
            int largerChildIndex = leftChildIndex(index); // create an integer called largerChildIndex and assign the left child index of the index to it
            if(hasRightChild(index) && rightChild(index).compareTo(leftChild(index),currentTime) > 0){ // if the right child of the index is not null and the right child of the index is greater than the left child of the index
                largerChildIndex = rightChildIndex(index); // assign the right child index of the index to the largerChildIndex
            }
            if(heap[index].compareTo(heap[largerChildIndex],currentTime) > 0){ // if the index is greater than the largerChildIndex
                break;
            }else{ //
                swap(index, largerChildIndex); // swap the index and the largerChildIndex
            }
            index = largerChildIndex; // assign the largerChildIndex to the index
        }
    }
    // this method is actually redundant, but it's effect to the performance is negligible, so I kept it in as a safety measure, as I can't guarantee that the heap tree is always in a valid state after my time based sorting algorithm
    public Node traverseHeap(int currentTime) { //method that traverses the heap until the next node's timeOfOrder is more than the time given in the parameter, removes the node with the highest priority, uses the removeNode method
        if (heap[0].timeOfOrder > currentTime) {
            return null;
        } else {
            Node temp = heap[0];
            int i = 1;
            while (i < size) {
                if (heap[i].timeOfOrder <= currentTime && heap[i].prioritySameMin > temp.prioritySameMin) {
                    temp = heap[i];
                }
                i++;
            }
            return remove(currentTime);
        }
    }

    public void waitTime(int currentTime) { // method that increments the wait time of the nodes in the heap tree, but only those that are actually waiting
        int i=0;
        while (i < size) {
            if(heap[i].timeOfOrder <= currentTime) heap[i].waitTime++;
            i++;
        }
    }



    public static class Node { // Inner static class Node
        private final int ID; // create an integer called ID
        private final int serviceTime; // create an integer called serviceTime that represents the time needed to process the order
        private final int year; // create an integer called year
        private final int timeOfOrder; // create an integer called timeOfOrder that represents the time when the order was placed
        private final int prioritySameMin; // create an integer called prioritySameMin that represents the priority of the order based on customer loyalty
        private int waitTime; // create an integer called waitTime that represents the time the order has been waiting in the queue

        public Node(int ID, int year, int timeOfOrder, int serviceTime) { // constructor for the Node class
            this.ID = ID;
            this.serviceTime = serviceTime;
            this.year = year;
            this.timeOfOrder = timeOfOrder;
            this.prioritySameMin = 2022 - year;
            this.waitTime = 0;
        }

        public int getServiceTime() {
            return serviceTime;
        }

        public int getID() {
            return ID;
        }

        public int getWaitTime() { return waitTime; }


        public int compareTo(Node other, int currentTime) { // heavily modified compareTo method that compares the priority of the nodes based on the time given in the parameter, priority and time of order
            if ((currentTime - this.timeOfOrder>=0 && currentTime - other.timeOfOrder>=0) ||(currentTime - this.timeOfOrder<0 && currentTime - other.timeOfOrder<0)) { // if either both orders are already placed or both orders are not yet placed
                if (this.prioritySameMin == other.prioritySameMin) { //then we just compare as we would normally
                    return Integer.compare(other.timeOfOrder, this.timeOfOrder);
                } else if (this.prioritySameMin < other.prioritySameMin) {
                    return -1;
                } else {
                    return 1;
                }
            }else if (currentTime - this.timeOfOrder >=0 && currentTime - other.timeOfOrder<0){ // if the first order is already placed and the second order is not yet placed
                return 1; // then the first order has higher priority
            } else if (currentTime - this.timeOfOrder <0 && currentTime - other.timeOfOrder>=0) { // if the first order is not yet placed and the second order is already placed
                return -1; // then the second order has higher priority

            }
        return 0; // if none of the above conditions are met, then the orders are equal, except it's impossible to reach this point...
        }
    }
}

