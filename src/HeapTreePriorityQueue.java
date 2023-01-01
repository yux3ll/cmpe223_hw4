public class HeapTreePriorityQueue {
    //-----------------------------------------------------
    // Title:
    // Author: Yüksel Çağlar Baypınar & Melisa Subaşı
    // ID: 43951623744 & 22829169256
    // Section: 02
    // Assignment: 4
    // Description: A class that implements the PriorityQueue interface using a heap tree, has additional methods to calculate the priority of a node using years and time of order.
    //-----------------------------------------------

    private Node[] heap; // the heap tree
    private int size; // the number of elements in the heap tree

    public HeapTreePriorityQueue() {
        heap = new Node[100]; // create a new Node array with size 100
        size = 0; // set the size to 0
    }

    public HeapTreePriorityQueue(int capacity) {
        heap = new Node[capacity]; // create a new Node array with the given capacity
        size = 0; // set the size to 0
    }


    public void add(Node node) {
        if (size == heap.length) // if the size of the heap tree is equal to the length of the heap tree
            resize(); // call the resize method
        heap[size] = node; // add the node to the heap tree
        size++; // increase the size by 1
        bubbleUp(); // call the bubbleUp method
    }

    public Node remove() {
        if (isEmpty()) // if the heap tree is empty
            return null; // return null
        Node temp = heap[0]; // create a new Node object called temp and assign the first element of the heap tree to it
        heap[0] = heap[size - 1]; // assign the last element of the heap tree to the first element of the heap tree
        heap[size - 1] = null; // assign null to the last element of the heap tree
        size--; // decrease the size by 1
        bubbleDown(); // call the bubbleDown method
        return temp; // return the temp Node object
    }

    public Node peek() {
        if (isEmpty()) // if the heap tree is empty
            return null; // return null
        return heap[0]; // return the first element of the heap tree
    }

    public void bubbleUp() {
        int index = size - 1; // create an integer called index and assign the size of the heap tree minus 1 to it
        while (hasParent(index) && parent(index).getPriority() > heap[index].getPriority()) { // while the index has a parent and the priority of the parent is greater than the priority of the index
            swap(index, parentIndex(index)); // swap the index and the parent index
            index = parentIndex(index); // assign the parent index to the index
        }
    }

    public void bubbleDown() {
        int index = 0; // create an integer called index and assign 0 to it
        while (hasLeftChild(index)) { // while the index has a left child
            int smallerChildIndex = leftChildIndex(index); // create an integer called smallerChildIndex and assign the left child index to it
            if (hasRightChild(index) && rightChild(index).getPriority() < leftChild(index).getPriority()) // if the index has a right child and the priority of the right child is less than the priority of the left child
                smallerChildIndex = rightChildIndex(index); // assign the right child index to the smallerChildIndex
            if (heap[index].getPriority() < heap[smallerChildIndex].getPriority()) // if the priority of the index is less than the priority of the smallerChildIndex
                break; // break the loop
            else // otherwise
                swap(index, smallerChildIndex); // swap the index and the smallerChildIndex
            index = smallerChildIndex; // assign the smallerChildIndex to the index
        }
    }

    public void resize() {
        Node[] temp = new Node[heap.length * 2]; // create a new Node array called temp with the length of the heap tree times 2
        for (int i = 0; i < heap.length; i++) // for each element in the heap tree
            temp[i] = heap[i]; // assign the element to the temp array
        heap = temp; // assign the temp array to the heap tree
    }

    public void swap(int index1, int index2) {
        Node temp = heap[index1]; // create a new Node object called temp and assign the element at the index1 to it
        heap[index1] = heap[index2]; // assign the element at the index2 to the element at the index1
        heap[index2] = temp; // assign the temp to the element at the index2
    }

    public boolean hasParent(int index) {
        return parentIndex(index) >= 0; // return true if the parent index of the index is greater than or equal to 0, otherwise return false
    }

    public boolean hasLeftChild(int index) {
        return leftChildIndex(index) < size; // return true if the left child index of the index is less than the size of the heap tree, otherwise return false
    }

    public boolean hasRightChild(int index) {
        return rightChildIndex(index) < size; // return true if the right child index of the index is less than the size of the heap tree, otherwise return false
    }

    public int parentIndex(int index) {
        return (index - 1) / 2; // return the parent index of the index
    }

public int leftChildIndex(int index) {
        return index * 2 + 1; // return the left child index of the index
    }

public int rightChildIndex(int index) {
        return index * 2 + 2; // return the right child index of the index
    }

public Node parent(int index) {
        return heap[parentIndex(index)]; // return the parent of the index
    }

public Node leftChild(int index) {
        return heap[leftChildIndex(index)]; // return the left child of the index
    }

public Node rightChild(int index) {
        return heap[rightChildIndex(index)]; // return the right child of the index
    }

public boolean isEmpty() {
        return size == 0; // return true if the size of the heap tree is equal to 0, otherwise return false
    }

public int size() {
        return size; // return the size of the heap tree
    }

public void clear() {
        heap = new Node[10]; // assign a new Node array with the length of 10 to the heap tree
        size = 0; // assign 0 to the size of the heap tree
    }


    class Node {
        private int ID;
        private int serviceTime;
        private int year;
        private int timeOfOrder;
        private int priority;

        public Node(int ID, int serviceTime, int year, int minute) {
            this.ID = ID;
            this.serviceTime = serviceTime;
            this.year = 2022 - year;
            this.timeOfOrder = minute;
            this.priority = (this.year * 365 * 24 * 60) + (this.timeOfOrder); //botch solution to making a set priority value
        }

        public int getYear() {
            return year;
        }

        public int getTimeOfOrder() {
            return timeOfOrder;
        }

        public int getServiceTime() {
            return serviceTime;
        }

        public int getID() {
            return ID;
        }

        public int getPriority() {
            return priority;
        }
    }




}
