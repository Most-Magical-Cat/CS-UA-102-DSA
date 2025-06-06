// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable extractMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// 
// Edited by Max Sklar Nov 2024 Changes:
// - On Underflow - returns null
// - Changed to K&R paren style
// - A few stylistic edits

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
    /**
     * Construct the binary heap.
     */
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct the binary heap.
     * @param capacity the capacity of the binary heap.
     */
    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (AnyType[]) new Comparable[ capacity + 1 ];
    }
    
    /**
     * Construct the binary heap given an array of items.
      */
    public BinaryHeap(AnyType [] items) {
        this(items, items.length);
    }

    public BinaryHeap(AnyType [] items, int size) {
        currentSize = size;

        array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];
        
        // Copy over the values from items
        for(int i = 0; i < currentSize; i++) {
            array[i + 1] = items[i];
        }

        buildHeap();
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        if (currentSize == array.length - 1) {
          enlargeArray((array.length << 1) | 1 );
        }

        // Percolate up
        currentSize++;

        int hole = currentSize;

        while(hole > 1 && x.compareTo(array[ hole >> 1 ] ) < 0) {
            array[ hole ] = array[ hole >> 1 ];
            hole = hole >> 1;
        }
            
        array[hole] = x;
    }


    private void enlargeArray(int newSize) {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];   
    }
             
    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or null if empty.
     */
    public AnyType findMin() {
        return isEmpty()? null : array[1];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or null if empty.
     */
    public AnyType extractMin()
    {
        if(isEmpty()) return null;
        AnyType minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap()
    {
        for( int i = currentSize >> 1; i > 0; i-- )
            percolateDown( i );
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
    }

    public int getSize() {
        return currentSize;
    }

    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array

    public void print() {
        for(int i = 1; i <= currentSize; i++) {
            System.out.print(array[i] + ",");
        }
        System.out.println("");
    }

    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown(int hole) {
        int child;
        AnyType tmp = array[ hole ];

        for( ; hole * 2 <= currentSize; hole = child ) {
            child = hole << 1;
            if( child != currentSize &&
                    array[ child + 1 ].compareTo( array[ child ] ) < 0 )
                child++;
            if( array[ child ].compareTo( tmp ) < 0 )
                array[ hole ] = array[ child ];
            else
                break;
        }
        array[ hole ] = tmp;
    }

    // Test program
    public static void main(String [] args) {
        int numItems = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<Integer>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert(i);
        for( i = 1; i < numItems; i++ )
            if( h.extractMin( ) != i )
                System.out.println( "Oops! " + i );
    }
}