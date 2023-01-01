public class Heap {
    //-----------------------------------------------------
    // Title:
    // Author: Yüksel Çağlar Baypınar & Melisa Subaşı
    // ID: 43951623744 & 22829169256
    // Section: 02
    // Assignment: 4
    // Description: A class that implements the PriorityQueue interface using a max heap tree.
    //-----------------------------------------------

    private Node[] heap; // the heap tree
    private int size; // the number of elements in the heap tree

    public Heap(int capacity) {
        heap = new Node[capacity]; // create a new Node array with the given capacity
        size = 0; // set the size to 0
    }

    public Heap(Heap clone){
        this.size = clone.size;
        this.heap = new Node[clone.heap.length];
        for(int i = 0; i<clone.heap.length;i++){
            if(clone.heap[i]!=null) {
                this.heap[i] = new Node(clone.heap[i].ID, clone.heap[i].year, clone.heap[i].timeOfOrder, clone.heap[i].serviceTime);
            }
        }
    }
    public void add(int id, int year, int timeOfOrder, int serviceTime, int currentTime) {
        Node node = new Node(id, year, timeOfOrder, serviceTime); // create a new Node object called temp
        add(node, currentTime); // call the add method with a Node object
    }
    private void add(Node node, int currentTime){
        if (size == heap.length) { // if the size of the heap tree is equal to the length of the heap tree
            System.out.println("Heap is full"); // print "Heap is full"
        }
        heap[size] = node; // add the new Node object to the heap tree
        size++; // increase the size by 1
        bubbleUp(currentTime); // call the bubbleUp method
    }

   public Node remove(int currentTime) {
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

    public void restoreHeap(int currentTime){
        for(int i = size/2 - 1; i >= 0; i--){
            bubbleDown(i, currentTime);
        }
    }

    public void bubbleDown(int index, int currentTime){
        while(hasLeftChild(index)){
            int largerChildIndex = leftChildIndex(index);
            if(hasRightChild(index) && rightChild(index).compareTo(leftChild(index),currentTime) > 0){
                largerChildIndex = rightChildIndex(index);
            }
            if(heap[index].compareTo(heap[largerChildIndex],currentTime) > 0){
                break;
            }else{
                swap(index, largerChildIndex);
            }
            index = largerChildIndex;
        }
    }

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

    public void waitTime(int currentTime) {
        int i=0;
        while (i < size) {
            if(heap[i].timeOfOrder <= currentTime) heap[i].waitTime++;
            i++;
        }
    }



    public static class Node {
        private int ID;
        private int serviceTime;
        private int year;
        private int timeOfOrder;
        private int prioritySameMin;
        private int waitTime;

        public Node(int ID, int year, int timeOfOrder, int serviceTime) {
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

        public int getWaitTime() { return waitTime; }

        public String toString() {
            return "ID: " + ID + " Service Time: " + serviceTime + " Year: " + year + " Time of Order: " + timeOfOrder + " Priority: " + prioritySameMin;
        }
        public int compareTo(Node other, int currentTime) {
            if ((currentTime - this.timeOfOrder>=0 && currentTime - other.timeOfOrder>=0) ||(currentTime - this.timeOfOrder<0 && currentTime - other.timeOfOrder<0)) {
                if (this.prioritySameMin == other.prioritySameMin) {
                    if (this.timeOfOrder == other.timeOfOrder) {
                        return 0;
                    } else if (this.timeOfOrder < other.timeOfOrder) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (this.prioritySameMin < other.prioritySameMin) {
                    return -1;
                } else {
                    return 1;
                }
            }else if (currentTime - this.timeOfOrder >=0 && currentTime - other.timeOfOrder<0){
                return 1;
            } else if (currentTime - this.timeOfOrder <0 && currentTime - other.timeOfOrder>=0) {
                return -1;

            }
        return 0;
        }
    }
}

